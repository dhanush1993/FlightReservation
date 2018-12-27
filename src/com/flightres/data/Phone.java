package com.flightres.data;

public class Phone {
	
	String ext;
	String number;
	public Phone(String ext, String number) {
		// TODO Auto-generated constructor stub
		this.ext = ext;
		this.number = number;
	}
	public Phone(String number) {
		// TODO Auto-generated constructor stub
		String parts[] = number.split("-");
		if(parts.length > 1) {
			this.ext = number.split("-")[0];
			this.number = number.split("-")[1];
		}else {
			this.ext = "1";
			this.number = number.split("-")[0];
		}
		
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return this.ext+"-"+this.number;
	}
}
