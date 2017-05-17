package com.demo.security.oauth.geode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import com.demo.security.ouath.customimpl.DemoClientDetails;

@Component
public class DemoGeodeClientDetailsService implements ClientDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoGeodeClientDetailsService.class);

	private GeodeClientDetailsHelper clientDetailsHelper;
	@Autowired
	public DemoGeodeClientDetailsService(GeodeClientDetailsHelper clientDetailsHelper) {
		this.clientDetailsHelper = clientDetailsHelper;
		
	}
	@Override
	public ClientDetails loadClientByClientId(String clientId) {
		LOGGER.debug("Entering loadClientByClientId clientId={}", clientId);
		ClientDetails clientDetails = null;
		try {
			DemoClientDetailsEntity clientDetailsEntity = clientDetailsHelper.getByClientId(clientId);
			if (clientDetailsEntity == null) {
				throw new NoSuchClientException("ClientId " + clientId + " not found");
			}
			DemoClientDetails demoClientDetails = new DemoClientDetails(clientDetailsEntity.getClientId(), clientDetailsEntity.getClientSecret().getBytes(), clientDetailsEntity.getScopes(), clientDetailsEntity.getAuthorizedGrantTypes(), clientDetailsEntity.getAccessTokenValidityInSeconds(), clientDetailsEntity.getRefreshTokenValidityInSeconds(), clientDetailsEntity.getAdditionalData()); 
			clientDetails = new BaseClientDetails(demoClientDetails);
			
			LOGGER.trace("clientDetails {}", ToStringBuilder.reflectionToString(clientDetails) );
		} catch (Exception e) {
			LOGGER.error("Error loading clientDetails ", e);
			throw new ClientRegistrationException(e.getMessage(), e);
		}
		LOGGER.debug("Exiting loadClientByClientId clientId={}", clientId);
		return clientDetails;
	}

}
