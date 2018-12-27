package com.flightres.exceptions;

public class InvalidAccessTypeError extends Exception {
	
	private String message;
	public InvalidAccessTypeError(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return this.message;
	}

}
