package com.demo.security.oauth.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requiresChannel().anyRequest().requiresSecure();
		http
			.requestMatchers().antMatchers("/api/oauth/revoke", "/api/oauth/token", "/oauth/check_token")
			.and()
			.authorizeRequests()
				.antMatchers("/oauth/check_token**").permitAll()
				.antMatchers("/api/oauth/token**").permitAll()
				.anyRequest().fullyAuthenticated();
	}
}