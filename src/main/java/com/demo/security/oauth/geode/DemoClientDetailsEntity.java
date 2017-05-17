package com.demo.security.oauth.geode;

import java.util.Map;
import java.util.Set;
public class DemoClientDetailsEntity {
	private String clientId;
	private String clientSecret;
	private Set<String> scopes;
	private Set<String> authorizedGrantTypes;
	private Integer accessTokenValidityInSeconds;
	private Integer refreshTokenValidityInSeconds;
	private Map<String, Object> additionalData;
	private Map<String, Long> grantTypeToAccessValidity;
	private long createdDateTime;
	private String createdBy;
	private long lastUpdatedDateTime;
	private String updatedBy;
	private String issuedByHost;
	private String issuedByDataCenter;
	private String status;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public Set<String> getScopes() {
		return scopes;
	}
	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}
	public Integer getAccessTokenValidityInSeconds() {
		return accessTokenValidityInSeconds;
	}
	public void setAccessTokenValidityInSeconds(Integer accessTokenValidityInSeconds) {
		this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
	}
	public Integer getRefreshTokenValidityInSeconds() {
		return refreshTokenValidityInSeconds;
	}
	public void setRefreshTokenValidityInSeconds(Integer refreshTokenValidityInSeconds) {
		this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
	}
	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(Map<String, Object> additionalData) {
		this.additionalData = additionalData;
	}
	public Map<String, Long> getGrantTypeToAccessValidity() {
		return grantTypeToAccessValidity;
	}
	public void setGrantTypeToAccessValidity(Map<String, Long> grantTypeToAccessValidity) {
		this.grantTypeToAccessValidity = grantTypeToAccessValidity;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public long getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}
	public void setLastUpdatedDateTime(long lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
