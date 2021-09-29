package com.centili.stepexe.exceptions;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotFoundException(String message) {
		this.message = message;
	}

	public static String getExceptionMessage(String message) {
		return new NotFoundException(message).getMessage();
	}
}
