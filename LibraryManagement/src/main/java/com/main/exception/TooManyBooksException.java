package com.main.exception;

public class TooManyBooksException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public TooManyBooksException() {
		this.setMessage("You already have 3 book loans");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
