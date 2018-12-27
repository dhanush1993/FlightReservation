package com.flightres.data;

import java.util.Date;

public class Flight {
	private String flightname;
	private String flightnumber;
	private int economySeats;
	private int firstClassSeats;
	private String status;
	private String arrival;
	private String destination;
	private long arrivalTime;
	private String arrivalTimeD;
	private String departureTimeD;
	private long departureTime;
	private int economyPrice;
	private int firstClassPrice;
	private int totalseats;
	
	public String getArrivalTimeD() {
		return new Date(arrivalTime).toString();
	}
	public void setArrivalTimeD(String arrivalTimeD) {
		this.arrivalTimeD = arrivalTimeD;
	}
	public String getDepartureTimeD() {
		return new Date(departureTime).toString();
	}
	public void setDepartureTimeD(String departureTimeD) {
		this.departureTimeD = departureTimeD;
	}
	public int getTotalseats() {
		return economySeats+firstClassSeats;
	}
	public void setTotalseats(int totalseats) {
		this.totalseats = economySeats+firstClassSeats;
	}
	public int getEconomyPrice() {
		return economyPrice;
	}
	public void setEconomyPrice(int economyPrice) {
		this.economyPrice = economyPrice;
	}
	public int getFirstClassPrice() {
		return firstClassPrice;
	}
	public void setFirstClassPrice(int firstClassPrice) {
		this.firstClassPrice = firstClassPrice;
	}
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFlightname() {
		return flightname;
	}
	public void setFlightname(String flightname) {
		this.flightname = flightname;
	}
	public String getFlightnumber() {
		return flightnumber;
	}
	public void setFlightnumber(String flightnumber) {
		this.flightnumber = flightnumber;
	}
	public int getEconomySeats() {
		return economySeats;
	}
	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public long getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public long getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(long departureTime) {
		this.departureTime = departureTime;
	}
}
