package com.demo.security.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			httpBasic()
			.and()
			.userDetailsService(new ClientDetailsUserDetailsService(clientDetailsService));
		http
				.requestMatchers().antMatchers("/api/oauth/revoke", "/api/oauth/token", "/oauth/check_token")
			.and()
		      .sessionManagement()
		      .sessionCreationPolicy(SessionCreationPolicy.NEVER)
		    .and()
		      .exceptionHandling()
		    .and()
		      .authorizeRequests()
		      	.antMatchers("/api/oauth/revoke").fullyAuthenticated()
		      	.antMatchers("/oauth/check_token").permitAll()
		        .antMatchers("/api/oauth/token**").permitAll()
	         .and()
		       .csrf().disable();
	}
}
