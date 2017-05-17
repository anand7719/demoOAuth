package com.demo.security.ouath.customimpl;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.demo.security.oauth.geode.DemoAccessTokenEntity;
import com.demo.security.oauth.geode.DemoRefreshTokenEntity;
import com.demo.security.oauth.geode.GeodeAccessTokenRegionHelper;
import com.demo.security.oauth.geode.GeodeRefreshTokenRegionHelper;

public class GeodeTokenStore implements TokenStore {
	private static final Logger LOG = LoggerFactory.getLogger(GeodeTokenStore.class);
	private static final Long DEFAULT_EXPIRATION_TIME = 7200l;
	private GeodeAccessTokenRegionHelper accessTokenRepository;
	private GeodeRefreshTokenRegionHelper refreshTokenRepository;
	private String dataCenter;
	private Map<String, Long> typeExpirationMap;
	private DefaultAuthenticationKeyGenerator authKeyGenerator = new DefaultAuthenticationKeyGenerator();

	public GeodeTokenStore(GeodeAccessTokenRegionHelper accessTokenRepository, GeodeRefreshTokenRegionHelper refreshTokenRepository, String dataCenter, Map<String, Long> typeExpirationMap) {
		this.accessTokenRepository = accessTokenRepository;
		this.dataCenter = dataCenter;
		this.typeExpirationMap = typeExpirationMap;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		try {
			
			DemoAccessTokenEntity tokenEntity = accessTokenRepository.getByAccessToken(token.getValue());
			if (tokenEntity == null) {
				throw new InvalidTokenException("Token is invalid");
			}
			OAuth2Request request = new OAuth2Request(Collections.emptyMap(), tokenEntity.getClientId(), null, true, tokenEntity.getScopes(), null, null, null, null);
			return  new OAuth2Authentication(request, null);
		} catch (Exception e) {
			LOG.error("Error reading AccessToken ", e);
		}

		return null;
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		return null;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		final String key = authKeyGenerator.extractKey(authentication);
		
		DemoAccessTokenEntity tokenEntity = new DemoAccessTokenEntity();
		tokenEntity.setAccessToken(token.getValue());
		if(token.getRefreshToken() != null) {
			tokenEntity.setRefreshToken(token.getRefreshToken().getValue());
		}
		tokenEntity.setClientId(authentication.getOAuth2Request().getClientId());
		tokenEntity.setCreatedDateTime(Instant.now().toEpochMilli());
		Long expirationTimeByGrantType = typeExpirationMap.get(authentication.getOAuth2Request().getGrantType());
		Long expirationTimeByClientId = typeExpirationMap.get(authentication.getOAuth2Request().getClientId() + "-" + authentication.getOAuth2Request().getGrantType());
		long expirationTime = expirationTimeByClientId != null ? expirationTimeByClientId : (expirationTimeByGrantType != null) ? expirationTimeByGrantType : DEFAULT_EXPIRATION_TIME;
		
		tokenEntity.setExpirationInSeconds(expirationTime);
		tokenEntity.setIssuedByDataCenter(dataCenter);

		tokenEntity.setUserId((String) authentication.getPrincipal());
		tokenEntity.setScopes(token.getScope());
		
		tokenEntity.setKey(key);
		accessTokenRepository.save(tokenEntity);
		
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		LOG.debug("Entering readAccessToken {}", tokenValue);
		OAuth2AccessToken accessToken = null;
		try {
			DemoAccessTokenEntity accessTokenEntity = accessTokenRepository.getByAccessToken(tokenValue);
			accessToken = convertAccessEntityToOAuth2AccessToken(tokenValue, accessTokenEntity);
			
		} catch (Exception e) {
			LOG.error("Error Reading AccessToken ",e);
		}
		LOG.debug("Exiting readAccessToken {}", tokenValue);
		return accessToken;
	}

	private OAuth2AccessToken convertAccessEntityToOAuth2AccessToken(String tokenValue, DemoAccessTokenEntity accessTokenEntity) {
		DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(tokenValue);
		Date expiresAt = new Date(accessTokenEntity.getCreatedDateTime() + (accessTokenEntity.getExpirationInSeconds() * 1000));
		long expirationInMs = expiresAt.getTime() - System.currentTimeMillis();
		accessToken.setScope(accessTokenEntity.getScopes());
		accessToken.setRefreshToken(readRefreshToken(accessTokenEntity.getRefreshToken()));
		Map<String, Object> additionalInformation = new HashMap<String, Object>();
		additionalInformation.put("audience", accessTokenEntity.getClientId());
		final String scopeString = accessTokenEntity.getScopes().stream()
			     .collect(Collectors.joining(" "));
		additionalInformation.put("scope", scopeString);
		additionalInformation.put("user_id", accessTokenEntity.getUserId());
		additionalInformation.put("expires_in", (expirationInMs/1000));
		accessToken.setAdditionalInformation(additionalInformation);
		return accessToken;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		LOG.debug("Entering removeAccessToken"); 
		try {
			accessTokenRepository.removeByAccessToken(token.getValue());
		} catch (Exception e) {
			LOG.error("Error removing token ", e);
		}
		LOG.debug("Exiting removeAccessToken"); 
	}
	
	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		DemoRefreshTokenEntity tokenEntiry = new DemoRefreshTokenEntity();

		tokenEntiry.setClientId(authentication.getOAuth2Request().getClientId());
		if (authentication.getUserAuthentication() != null) {
			tokenEntiry.setUserId(authentication.getUserAuthentication().getName());
		} else {
			LOG.warn("UserAuthorization is null for " + authentication.getOAuth2Request().getGrantType());
		}
		tokenEntiry.setRefreshToken(refreshToken.getValue());

		tokenEntiry.setScopes(authentication.getOAuth2Request().getScope());
		tokenEntiry.setCreatedDateTime(Instant.now().toEpochMilli());

		Long expirationTimeByGrantType = typeExpirationMap.get(authentication.getOAuth2Request().getGrantType());
		Long expirationTimeByClientId = typeExpirationMap.get(authentication.getOAuth2Request().getClientId() + "-" + authentication.getOAuth2Request().getGrantType());
		long expirationTime = expirationTimeByClientId != null ? expirationTimeByClientId : (expirationTimeByGrantType != null) ? expirationTimeByGrantType : DEFAULT_EXPIRATION_TIME;
		
		tokenEntiry.setExpirationInSeconds(expirationTime);
		tokenEntiry.setIssuedByDataCenter(dataCenter);

		try {
			refreshTokenRepository.save(tokenEntiry);
		} catch (Exception e) {
			LOG.error("ERROR Storing AccessToken={}", refreshToken.getValue(), e);
		}
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		LOG.debug("Entering readRefreshToken {}", tokenValue);
		DefaultOAuth2RefreshToken refreshToken = null;
		try {
			DemoRefreshTokenEntity refreshTokenEntity = refreshTokenRepository.get(tokenValue);
			if (refreshTokenEntity != null) {
				refreshToken = new DefaultOAuth2RefreshToken(refreshTokenEntity.getRefreshToken());
			}
			
		} catch (Exception e) {
			LOG.error("Error reading refresh token ", e);
		}
		LOG.debug("Exiting readRefreshToken {}", tokenValue);
		return refreshToken;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		try {
			DemoRefreshTokenEntity tokenEntity = refreshTokenRepository.get(token.getValue());
			if (tokenEntity != null) {
				OAuth2Request request = new OAuth2Request(Collections.emptyMap(), tokenEntity.getClientId(), null, true, tokenEntity.getScopes(), null, null, null, null);
				OAuth2Authentication accessToken = new OAuth2Authentication(request, null);
				return accessToken;
			}
		} catch (Exception e) {
			LOG.error("Error reading AccessToken ", e);
		}
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		refreshTokenRepository.delete(token.getValue());
		
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		final String key = authKeyGenerator.extractKey(authentication);
		OAuth2AccessToken accessToken = null;
		try {
			DemoAccessTokenEntity accessTokenEntity = accessTokenRepository.getByKey(key);
			
			if (accessTokenEntity != null) {
				accessToken = convertAccessEntityToOAuth2AccessToken(accessTokenEntity.getAccessToken(), accessTokenEntity);
			}
		} catch (Exception e) {
			LOG.error("Error Reading access token ", e);
		}
		return accessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return null;
	}

}
