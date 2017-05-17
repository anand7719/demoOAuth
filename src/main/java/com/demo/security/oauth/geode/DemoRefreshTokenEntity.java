package com.demo.security.oauth.geode;

import java.util.Set;

public class DemoRefreshTokenEntity {
	private String id;
	private String refreshToken;
	private long expirationInSeconds;
	private String accessToken;
	private String userId;
	private long createdDateTime;
	private String issuedByDataCenter;
	private String clientId;
	private Set<String> scopes;

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}	
	public long getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
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
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Set<String> getScopes() {
		return scopes;
	}
	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
