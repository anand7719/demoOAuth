package com.demo.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
@TestPropertySource("classpath:/test.properties")
public class RevokeTokenHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(RevokeTokenHelper.class);

	@Value("${clientId}")
	private String clientId;
	@Value("${clientSecret}")
	private String clientSecret;
	@Value("${token-revoke-endpoint}")
	private String tokenRevokeEndpoint;
	
	
	public void revokeAccessToken(final String tokenToBeRevoked) {
		LOGGER.info("revokeAccessToken {}", tokenToBeRevoked);
		HttpHeaders headers = new HttpHeaders();
		String authString = clientId + ":" + clientSecret;
		String basicAuthString = "Basic " + Base64.encodeBase64String(authString.getBytes());
		headers.add("Authorization", basicAuthString);
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("token", tokenToBeRevoked);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(requestParams, headers);
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.exchange(tokenRevokeEndpoint, HttpMethod.POST, httpEntity, String.class);
		} catch (HttpClientErrorException he) {
			LOGGER.error("Error Revoking token", he);
		}
	}
}
