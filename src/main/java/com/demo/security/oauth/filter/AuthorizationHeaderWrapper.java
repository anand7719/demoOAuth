package com.demo.security.oauth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.security.oauth.geode.GeodeClientDetailsHelper;

public class AuthorizationHeaderWrapper extends HttpServletRequestWrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationHeaderWrapper.class);
	private HttpServletRequest request;
	private GeodeClientDetailsHelper clientDetailsHelper;
	public AuthorizationHeaderWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	@Override
	public String getHeader(String name) {
		
		if (StringUtils.equalsIgnoreCase(name, "Authorization")) {
			String headerAuthValue = request.getHeader("Authorization");
			if(StringUtils.isNotBlank(headerAuthValue)) {
				return headerAuthValue;
			}
			
			final String clientId = request.getParameter("client_id");
			String secret = request.getParameter("client_secret");
			if (StringUtils.equalsIgnoreCase(request.getParameter("grant_type"), "service_ticket")) {
				try {
					secret = clientDetailsHelper.getByClientId(clientId).getClientSecret();
				} catch (Exception e) {
					return null;
				}
			}
			LOGGER.debug("{} {}", clientId, secret);
			if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(secret)) {
				return null;
			}
			String auth = clientId + ":" + secret;
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
			return "Basic " + new String(encodedAuth);
		} else {
			return super.getHeader(name);
		}

	}
	public void setClientDetailsHelper(GeodeClientDetailsHelper clientDetailsHelper) {
		this.clientDetailsHelper = clientDetailsHelper;
	}

}
