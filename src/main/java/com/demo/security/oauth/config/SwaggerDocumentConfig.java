package com.demo.security.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerDocumentConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Old")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.demo.security.oauth.controller"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				;
	}
	@Bean
	public Docket defaultApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Default")
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				;
	}
}