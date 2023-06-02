package com.account.exception;

public class AccountException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public AccountException(String message) {

		super(message);
	}

	public AccountException() {

		super();
	}

}
