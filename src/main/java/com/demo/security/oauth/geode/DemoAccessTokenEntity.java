package com.demo.security.oauth.geode;

import java.util.Map;
import java.util.Set;
public class DemoAccessTokenEntity {
	private String key;
	private String clientId;
	private long expirationInSeconds;
	private String accessToken;
	private String refreshToken;
	private Set<String> scopes;
	private String userId;
	private Map<String, String> additionalData;
	private long createdDateTime;
	private String issuedByHost;
	private String issuedByDataCenter;
	
	public DemoAccessTokenEntity() {
		
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Set<String> getScopes() {
		return scopes;
	}
	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, String> getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(Map<String, String> additionalData) {
		this.additionalData = additionalData;
	}
	public long getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getIssuedByHost() {
		return issuedByHost;
	}
	public void setIssuedByHost(String issuedByHost) {
		this.issuedByHost = issuedByHost;
	}
	public String getIssuedByDataCenter() {
		return issuedByDataCenter;
	}
	public void setIssuedByDataCenter(String issuedByDataCenter) {
		this.issuedByDataCenter = issuedByDataCenter;
	}
	public long getExpirationInSeconds() {
		return expirationInSeconds;
	}
	public void setExpirationInSeconds(long expirationInSeconds) {
		this.expirationInSeconds = expirationInSeconds;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
