package com.flightres.data;

import com.flightres.exceptions.InvalidAccessTypeError;

public class AccessType  {
	
	private int accessType;
	
	public AccessType() {
		this.accessType = 0;
	}
	
	public AccessType(int type) throws InvalidAccessTypeError {
		if(type == 0 || type ==1)
			this.accessType = type;
		else
			throw new InvalidAccessTypeError(type+" is not a valid accesstype.");
	}
	
	public AccessType(String type) throws InvalidAccessTypeError {
		if(type.equalsIgnoreCase("user"))
			this.accessType = 0;
		else if(type.equalsIgnoreCase("admin"))
			this.accessType = 1;
		else
			throw new InvalidAccessTypeError(type+" is not a valid accesstype.");
	}
	
	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	public String toString() {
		
		return Integer.toString(this.accessType);
	}

}
