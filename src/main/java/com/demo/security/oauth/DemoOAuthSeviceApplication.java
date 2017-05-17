package com.demo.security.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class DemoOAuthSeviceApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder p = new BCryptPasswordEncoder();
		SpringApplication.run(DemoOAuthSeviceApplication.class, args);
		
	}	
}
