package com.account.exception;

public class AccountServiceException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public AccountServiceException(String message) {

		super(message);
	}

	public AccountServiceException() {

		super();
	}

}
