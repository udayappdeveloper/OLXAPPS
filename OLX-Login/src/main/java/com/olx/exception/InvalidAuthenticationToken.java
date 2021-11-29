package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAuthenticationToken extends RuntimeException {
	private String message;

	public InvalidAuthenticationToken() {
		this.message = "";
	}

	public InvalidAuthenticationToken(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "Invalid stock id " + this.message;
	}
}
