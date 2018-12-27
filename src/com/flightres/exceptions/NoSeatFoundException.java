package com.flightres.exceptions;

public class NoSeatFoundException extends Exception {
	private String message;
	public NoSeatFoundException(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return this.message;
	}
}
