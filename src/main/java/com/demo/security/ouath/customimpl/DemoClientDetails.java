package com.demo.security.ouath.customimpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
Docs goes here
TEST
TEST
sdsdsd
**/
public class DemoClientDetails implements ClientDetails {

	private static final long serialVersionUID = 1L;
	private String clientId;
	private byte[] secret;
	private Set<String> scope;
	private Set<String> authorizedGrantTypes;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private Map<String, Object> additionalInformation;
	private Set<GrantedAuthority> authorities;
	
	public DemoClientDetails(String clientId, byte[] secret, Set<String> scope, Set<String> authorizedGrantTypes, Integer accessTokenValiditySeconds, Integer refreshTokenValiditySeconds, Map<String, Object> additionalInformation) {
		super();
		this.clientId = clientId;
		this.secret = secret;
		this.scope = scope;
		this.authorizedGrantTypes = authorizedGrantTypes;
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
		this.additionalInformation = additionalInformation;
		authorities =  new HashSet<GrantedAuthority>(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", scope)));
		
	}
	@Override
	public String getClientId() {
		return clientId;
	}
	@Override
	public String getClientSecret() {
		return new String(secret);
	}
	@Override
	public Set<String> getScope() {
		return scope;
	}
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}
	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}
	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}
	@Override
	public Set<String> getResourceIds() {
		return null;
	}
	@Override
	public boolean isSecretRequired() {
		return true;
	}
	@Override
	public boolean isScoped() {
		return true;
	}
	@Override
	public Set<String> getRegisteredRedirectUri() {
		return null;
	}
	@Override
	public boolean isAutoApprove(String scope) {
		return false;

	}
}
