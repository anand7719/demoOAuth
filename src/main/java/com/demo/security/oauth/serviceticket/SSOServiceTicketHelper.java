package com.demo.security.oauth.serviceticket;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.security.oauth.serviceticket.model.SSOTicketModel;

@Component
public class SSOServiceTicketHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOServiceTicketHelper.class);

	private RestTemplate restTemplate;
	private String serviceValidateURL;
	@Autowired
	public SSOServiceTicketHelper(RestTemplate restTemplate, @Value("${demo.sso.service-validate-url}") String serviceValidateURL) {
		this.restTemplate = new RestTemplate();
		this.serviceValidateURL = serviceValidateURL;
	}

	public SSOTicketModel getSSOTicketDetails(String serviceTicket, String serviceURL) {
		LOGGER.debug("Entering getSSOTicketDetails serviceTicket={}, serviceURL={}", serviceTicket, serviceURL);
		URI uri = UriComponentsBuilder.fromHttpUrl(serviceValidateURL).queryParam("ticket", serviceTicket).queryParam("service", serviceURL).build().toUri();
		SSOTicketModel ticketModel = null;
		try {
			LOGGER.trace("getSSOTicketDetails uri={}", uri.toString());
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<?> entity = new HttpEntity<>(headers);			
			
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			final String responseBodyStr = response.getBody();
			final String customerGuid = extractTagFromXMLStr(responseBodyStr, "cas:customerGUID");
			final String customerId = extractTagFromXMLStr(responseBodyStr, "cas:customerId");
			if (StringUtils.isNoneBlank(customerGuid) || StringUtils.isNoneBlank(customerId)) {
				ticketModel = new SSOTicketModel();
				ticketModel.setCustomerGUID(customerGuid);
				ticketModel.setCustomerId(customerId);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR Validating ServiceTicket={}, service={}, reason={}", serviceTicket, serviceURL, e);
		}
		LOGGER.debug("Exiting getSSOTicketDetails ticketModel={}", ticketModel.getCustomerGUID());

		return ticketModel;
	}

	private String extractTagFromXMLStr(final String responseBodyStr, String tag) {
		LOGGER.debug("getSSOTicketDetails SSO_RESPONSE={}", responseBodyStr);
		
		final Pattern pattern = Pattern.compile("<" + tag+ ">(.+?)</"+tag+">");
		final Matcher matcher = pattern.matcher(responseBodyStr);
		String value = null;
		if (matcher.find()) {
			value = matcher.group(1);
		}
		return value;
	}
}
