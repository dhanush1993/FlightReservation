package com.flightres.exceptions;

public class SeatBookedException extends Exception {
	
	private String message;
	public SeatBookedException(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return this.message;
	}

}
