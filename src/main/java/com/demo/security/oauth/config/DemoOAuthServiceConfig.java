package com.demo.security.oauth.config;

import java.util.Map;

import javax.servlet.Filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.client.RestTemplate;

import com.demo.security.oauth.filter.ModifyAuthorizationHeaderRequestFilter;
import com.demo.security.oauth.geode.GeodeAccessTokenRegionHelper;
import com.demo.security.oauth.geode.GeodeRefreshTokenRegionHelper;
import com.demo.security.ouath.customimpl.GeodeTokenStore;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "demo.oauth")
public class DemoOAuthServiceConfig {
	private Map<String, Long> grantTypeClientTokenExpiration;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Filter modifyRequestObjectFilter() {
		return new ModifyAuthorizationHeaderRequestFilter();
	}

	@Bean
	public TokenStore tokenStore(GeodeAccessTokenRegionHelper geodeAccessTokenRegionHelper, GeodeRefreshTokenRegionHelper refreshTokenHelper) {
		return new GeodeTokenStore(geodeAccessTokenRegionHelper, refreshTokenHelper, "OLATHE", grantTypeClientTokenExpiration);
	}

	public Map<String, Long> getGrantTypeClientTokenExpiration() {
		return grantTypeClientTokenExpiration;
	}

	public void setGrantTypeClientTokenExpiration(Map<String, Long> grantTypeClientTokenExpiration) {
		this.grantTypeClientTokenExpiration = grantTypeClientTokenExpiration;
	}
}
