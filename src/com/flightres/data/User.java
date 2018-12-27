package com.flightres.data;

import java.util.Date;

import com.flightres.data.AccessType;

public class User {
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private AccessType accessType;
	private int age;
	private Gender gender;
	private String address;
	private Phone number;
	private String PassportNumber;
	private long dob;
	private int selectedSeat;
	public int getSelectedSeat() {
		return selectedSeat;
	}
	public void setSelectedSeat(int selectedSeat) {
		this.selectedSeat = selectedSeat;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AccessType getAccessType() {
		return accessType;
	}
	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Phone getNumber() {
		return number;
	}
	public void setNumber(Phone number) {
		this.number = number;
	}
	public String getPassportNumber() {
		return PassportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		PassportNumber = passportNumber;
	}
	public long getDob() {
		return dob;
	}
	public void setDob(long dob) {
		this.dob = dob;
	}
	public String toString() {
		String s = this.firstname +" ,"
				+this.lastname+", "
				+this.username+" ,"
				+this.password+", "
                +this.accessType.toString()+" ,"
                +this.address+" ," 
                +this.gender.toString()+" ,"
                +this.age+" ,"
                +new java.sql.Timestamp(this.dob)+" ,"
				+this.number.toString()+" ,"
				+this.PassportNumber+" ,";
		return s;
		
	}

}
