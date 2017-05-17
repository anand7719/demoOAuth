package com.demo.security.oauth.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.security.oauth.filter.AuthorizationHeaderWrapper;
import com.demo.security.oauth.geode.GeodeAccessTokenRegionHelper;
import com.demo.security.oauth.geode.GeodeClientDetailsHelper;
import com.demo.security.oauth.geode.GeodeRefreshTokenRegionHelper;

@RestController
public class DemoOAuthController {
	private GeodeAccessTokenRegionHelper accessTokenRegionHelper;
	private GeodeRefreshTokenRegionHelper refreshTokenRegionHelper;
	@Autowired
	public DemoOAuthController(GeodeAccessTokenRegionHelper accessTokenRegionHelper, GeodeRefreshTokenRegionHelper refreshTokenRegionHelper) {
		this.accessTokenRegionHelper = accessTokenRegionHelper;
		this.refreshTokenRegionHelper = refreshTokenRegionHelper;
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoOAuthController.class);
	@Autowired
	private GeodeClientDetailsHelper clientDetailsHelper;
	@RequestMapping(value = "/api/oauth/token", method = RequestMethod.POST)
	public void postAccessToken(@RequestParam Map<String, String> parameters, AuthorizationHeaderWrapper request, HttpServletResponse response) throws IOException, ServletException {
		LOGGER.debug("Entering postAccessToken {}", parameters);
		request.setClientDetailsHelper(clientDetailsHelper);
		request.getRequestDispatcher("/oauth/token").forward(request, response);
		LOGGER.debug("Exiting postAccessToken {}", parameters);
	}
	@RequestMapping(value = "/api//oauth/tokeninfo", method = RequestMethod.GET)
	public void checkTokenInfo(@RequestParam Map<String, String> parameters, AuthorizationHeaderWrapper request, HttpServletResponse response) throws IOException, ServletException {
		LOGGER.debug("Entering checkTokenInfo {}", parameters);
		request.getRequestDispatcher("/oauth/check_token").forward(request, response);
		LOGGER.debug("Exiting checkTokenInfo {}", parameters);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/api/oauth/revoke", method = RequestMethod.POST, produces = "application/json")
	public String revokeToken(@RequestParam Map<String, String> parameters) throws Exception {
		final String accessToken = parameters.get("token");
		LOGGER.debug("Entering revokeToken {}", parameters);
		accessTokenRegionHelper.removeByAccessToken(accessToken);		
		LOGGER.debug("Exiting revokeToken {}", parameters);
		return "{}";
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/api/oauth/revoke/{userId}", method = RequestMethod.POST, produces = "application/json")
	public String revokeTokenForUser(@PathVariable("userId") String userId) throws Exception {
		LOGGER.debug("Entering revokeTokenForUser {}", userId);
		accessTokenRegionHelper.removeByUserId(userId);
		refreshTokenRegionHelper.removeByUserId(userId);
		LOGGER.debug("Exiting revokeTokenForUser {}", userId);
		return "{}";
	}
}
