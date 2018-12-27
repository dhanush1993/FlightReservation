package com.flightres.exceptions;

public class UserNotFoundException extends Exception {
	private String message;
	
	public UserNotFoundException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
