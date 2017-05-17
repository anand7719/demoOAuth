package com.demo.security.oauth.serviceticket;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.demo.security.oauth.exception.BadServiceTicketException;
import com.demo.security.oauth.serviceticket.model.SSOTicketModel;

public class SSOServiceTicketGranter extends AbstractTokenGranter {
	private static final Logger LOG = LoggerFactory.getLogger(SSOServiceTicketGranter.class);
	private SSOServiceTicketHelper ssoHelper;

	public SSOServiceTicketGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, SSOServiceTicketHelper ssoHelper) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.ssoHelper = ssoHelper;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		LOG.debug("Entering getOAuth2Authentication {}", tokenRequest.getRequestParameters());
		final String serviceTicket = tokenRequest.getRequestParameters().get("service_ticket");
		final String serviceURL = tokenRequest.getRequestParameters().get("service_url");
		if (StringUtils.isBlank(serviceTicket) || StringUtils.isBlank(serviceURL)) {
			LOG.error("getOAuth2Authentication both service_ticket and service_url are required.");
			return null;
		}
		SSOTicketModel ticket = ssoHelper.getSSOTicketDetails(serviceTicket, serviceURL);
		if (ticket == null) {
			throw new BadServiceTicketException("Invalid service ticket");
		}
		LOG.trace("SSO Ticket customerId={}", ticket.getCustomerGUID());
		Map<String, String> params = tokenRequest.getRequestParameters();
		final String username = StringUtils.isNotBlank(ticket.getCustomerGUID()) ? ticket.getCustomerGUID() : ticket.getCustomerId();
		LOG.debug("username={}", username);
		List<GrantedAuthority> authorities = params.containsKey("authorities") ? AuthorityUtils.createAuthorityList(OAuth2Utils.parseParameterList(params.get("authorities")).toArray(new String[0])) : AuthorityUtils.NO_AUTHORITIES;
		Authentication user = new UsernamePasswordAuthenticationToken(username, "N/A", authorities);
		OAuth2Authentication authentication = new OAuth2Authentication(tokenRequest.createOAuth2Request(client), user);
		return authentication;
	}
}