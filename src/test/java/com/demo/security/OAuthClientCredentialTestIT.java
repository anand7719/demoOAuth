package com.demo.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@Import({RevokeTokenHelper.class})
@TestPropertySource("classpath:/test.properties")
public class OAuthClientCredentialTestIT {
	
	private static final String GRANT_TYPE_CLIENT_CREDENTIAL = "client_credentials";

	@Value("${clientId}")
	private String clientId;
	@Value("${clientSecret}")
	private String clientSecret;
	@Value("${token-endpoint}")
	private String tokenEndpoint;
	
	private String basicAuthString;
	@Autowired
	private RevokeTokenHelper revokeHelper;

	@Before
	public void setup() {
		String credential = clientId + ":" + clientSecret;
		basicAuthString = "Basic " + Base64.encodeBase64String(credential.getBytes());
	}
	
	@Test
	public void test_SingleScope() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("grant_type", GRANT_TYPE_CLIENT_CREDENTIAL);
		requestParams.add("scope", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", basicAuthString);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(requestParams, headers);
		ResponseEntity<TestTokenModel> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, httpEntity, TestTokenModel.class);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getScope());
		assertEquals(response.getBody().getScope(), "ADMIN");
		revokeHelper.revokeAccessToken(response.getBody().getAccess_token());
	}
}
