package com.demo.security.oauth.exception;

import org.springframework.security.core.AuthenticationException;

public class BadServiceTicketException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public BadServiceTicketException(String msg) {
		super(msg);
	}

}
