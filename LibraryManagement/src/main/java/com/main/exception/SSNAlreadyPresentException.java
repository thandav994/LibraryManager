package com.main.exception;

public class SSNAlreadyPresentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public SSNAlreadyPresentException() {
		this.setMessage("SSN already registered with another borrower");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
