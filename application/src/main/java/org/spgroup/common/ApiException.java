package org.spgroup.common;

import lombok.Data;

import org.springframework.http.HttpStatus;

@Data
public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	private final HttpStatus status;
	private final String message;

	public ApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
