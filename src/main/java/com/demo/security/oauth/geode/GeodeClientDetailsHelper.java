package com.demo.security.oauth.geode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GeodeClientDetailsHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeodeClientDetailsHelper.class);

	
	public DemoClientDetailsEntity getByClientId(String clientId) throws Exception {
		LOGGER.debug("Enterting getByClientId clientId={}", clientId);
		DemoClientDetailsEntity e = new DemoClientDetailsEntity();
		e.setAccessTokenValidityInSeconds(7600);

		String[] strArray = {"client_credentials"};
		Set<String> grantSet = Arrays.stream(strArray).collect(Collectors.toSet());
		e.setAuthorizedGrantTypes(grantSet);
		
		e.setClientId("TEST");
		//e.setClientSecret("$2a$10$ja0qvmrEQn/Bmz3RXTvOyu3qfB12hcXyEkRxnGWHUAys45z5ldTfu"); //password
		e.setClientSecret("password");
		e.setCreatedDateTime(Calendar.getInstance().getTimeInMillis());
		e.setRefreshTokenValidityInSeconds(10000);
		
		String[] scopesArray = {"ADMIN", "READ"};
		Set<String> scopes = Arrays.stream(scopesArray).collect(Collectors.toSet());
		e.setScopes(scopes);

		return e;
	}
}
