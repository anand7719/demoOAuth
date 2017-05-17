package com.demo.security.oauth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/api/oauth/token")
public class ModifyAuthorizationHeaderRequestFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyAuthorizationHeaderRequestFilter.class);

	@Override
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			request = new AuthorizationHeaderWrapper((HttpServletRequest) request);
			chain.doFilter(request, response);
		} catch (Exception e) {
			LOGGER.error("Error in filter ", e);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
