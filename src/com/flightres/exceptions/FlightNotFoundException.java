package com.flightres.exceptions;

public class FlightNotFoundException extends Exception {
	
	private String message;

	public FlightNotFoundException(String msg) {
		// TODO Auto-generated constructor stub
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
