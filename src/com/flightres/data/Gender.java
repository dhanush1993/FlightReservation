package com.flightres.data;

public class Gender {
	
	private String g;
	
	public Gender(int g) {
		if(g==0) {
			this.g = "FEMALE";
		}else if(g==1){
			this.g = "MALE";
		}else {
			this.g = "OTHER";
		}
	}
	
	public Gender(String g) {
		this.g = g;
	}
	
	public Gender(char g) {
		if(g=='f' || g=='F') {
			this.g = "FEMALE";
		}else if(g=='m' || g=='M'){
			this.g = "MALE";
		}else{
			this.g = "OTHER";
		}
	}

	public String getGender() {
		return g;
	}

	public void setGender(String g) {
		this.g = g;
	}
	
public String toString() {
		return this.g;
	}
	

}
