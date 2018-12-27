package com.flightres.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.flightres.data.AccessType;
import com.flightres.data.Flight;
import com.flightres.data.FlightRow;
import com.flightres.data.Gender;
import com.flightres.data.Phone;
import com.flightres.data.User;
import com.flightres.exceptions.FlightNotFoundException;
import com.flightres.exceptions.InvalidAccessTypeError;
import com.flightres.exceptions.NoSeatFoundException;
import com.flightres.exceptions.SeatBookedException;
import com.flightres.exceptions.UserNotFoundException;
import com.flightres.factory.Factory;
import com.flightres.gui.Login;

import javafx.application.Application;

public class Controller {
	public static void main(String[] args) throws SeatBookedException, NoSeatFoundException {
		Factory.getDao();
		try {
			firstTime(args);
		} catch (InvalidAccessTypeError | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void firstTime(String[] args) throws InvalidAccessTypeError, SQLException, SeatBookedException, NoSeatFoundException {
		Factory.getDao().init();
		Flight flight = new Flight();
		flight.setFlightname("Emirates");
		flight.setFlightnumber("EM001");
		flight.setEconomySeats(100);
		flight.setFirstClassSeats(50);
		flight.setArrival("Bangalore");
		flight.setDestination("Los Angeles");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 15);
		flight.setDepartureTime(c.getTime().getTime());
		c.add(Calendar.DATE, 18);
		flight.setArrivalTime(c.getTime().getTime());
		flight.setEconomyPrice(1200);
		flight.setFirstClassPrice(800);
		try {
		Factory.getDao().createFlight(flight);
		}catch(Exception e) {
			
		}
		flight.setFlightname("Emirates");
		flight.setFlightnumber("EM010");
		flight.setEconomySeats(85);
		flight.setFirstClassSeats(15);
		flight.setArrival("Delhi");
		flight.setDestination("Washington DC");
		c.setTime(new Date());
		c.add(Calendar.DATE, 5);
		flight.setDepartureTime(c.getTime().getTime());
		c.add(Calendar.DATE, 6);
		flight.setArrivalTime(c.getTime().getTime());
		flight.setEconomyPrice(1800);
		flight.setFirstClassPrice(1000);
		try {
			Factory.getDao().createFlight(flight);
			}catch(Exception e) {
				
			}
		flight.setFlightname("Luftansa");
		flight.setFlightnumber("LU001");
		flight.setEconomySeats(120);
		flight.setFirstClassSeats(30);
		flight.setArrival("Delhi");
		flight.setDestination("New York");
		c.setTime(new Date());
		c.add(Calendar.DATE, 3);
		flight.setDepartureTime(c.getTime().getTime());
		c.add(Calendar.DATE, 5);
		flight.setArrivalTime(c.getTime().getTime());
		flight.setEconomyPrice(2000);
		flight.setFirstClassPrice(1600);
		try {
			Factory.getDao().createFlight(flight);
			}catch(Exception e) {
				
			}
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = new User();
		user.setUsername("admin");
		user.setFirstname("Admin");
		user.setLastname("A");
		byte[] hash = digest.digest("admin".getBytes(StandardCharsets.UTF_8));
		String sha256hex = new String(hash);
		user.setPassword(sha256hex);
		c.setTime(new Date());
		c.add(Calendar.YEAR, -18);
		user.setDob(c.getTime().getTime());
		user.setAge(18);
		user.setAccessType(new AccessType(1));
		user.setAddress("4002 Canis Heights Drive");
		user.setGender(new Gender(1));
		user.setNumber(new Phone("1","3109480935"));
		user.setPassportNumber("LAZY");
		try {
		Factory.getDao().createUser(user);
		}catch(Exception e) {
			
		}
		
		user.setUsername("marieRocks");
		user.setFirstname("Marie");
		user.setLastname("Jane");
		hash = digest.digest("mariejane".getBytes(StandardCharsets.UTF_8));
		sha256hex = new String(hash);
		user.setPassword(sha256hex);
		c.setTime(new Date());
		c.add(Calendar.YEAR, -20);
		user.setDob(c.getTime().getTime());
		user.setAge(20);
		user.setAccessType(new AccessType(1));
		user.setAddress("377 Star Trek Drive");
		user.setGender(new Gender(0));
		user.setNumber(new Phone("1","4076199811"));
		user.setPassportNumber("ABCD");
		try {
		Factory.getDao().createUser(user);
		}catch(Exception e) {
			
		}
		
		user.setUsername("hearts");
		user.setFirstname("Steve");
		user.setLastname("Smith");
		hash = digest.digest("stevesmith".getBytes(StandardCharsets.UTF_8));
		sha256hex = new String(hash);
		user.setPassword(sha256hex);
		c.setTime(new Date());
		c.add(Calendar.YEAR, -65);
		user.setDob(c.getTime().getTime());
		user.setAge(65);
		user.setAccessType(new AccessType(1));
		user.setAddress("3712 Kennedy Court");
		user.setGender(new Gender(1));
		user.setNumber(new Phone("1","7742998394"));
		user.setPassportNumber("EFGH");
		try {
		Factory.getDao().createUser(user);
		}catch(Exception e) {
			
		}
		Application.launch(Login.class,args);
	}

	public User checkUser(User user) throws SQLException, UserNotFoundException {
		// TODO Auto-generated method stub
		try {
			User userFromDB = Factory.getDao().getUser(user);
			if(userFromDB.getPassword().equals(user.getPassword())) {
				user = userFromDB;
			}else {
				throw new UserNotFoundException("Invalid Password.");
			}
		} catch (InvalidAccessTypeError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public void LaunchApplication() {
		
	}

	public void createUser(User user) throws SQLException {
		Factory.getDao().createUser(user);
		
	}

	public ArrayList<Flight> getFlights(User user) throws SQLException, UserNotFoundException {
		this.checkUser(user);
		return Factory.getDao().getAllFlights();
	}

	public HashMap<Integer, Integer[]> getAllSeats(User user, Flight flight) throws SQLException, UserNotFoundException {
		// TODO Auto-generated method stub
		this.checkUser(user);
		return Factory.getDao().getAllSeats(flight);
	}

	public void addSeatToUser(Flight flight,User user) throws SQLException, UserNotFoundException, SeatBookedException, NoSeatFoundException, FlightNotFoundException {
		// TODO Auto-generated method stub
		this.checkUser(user);
		Factory.getDao().reserveFlight(flight, user);
	}

	public void createFlight(Flight flight, User user) throws SQLException, UserNotFoundException {
		// TODO Auto-generated method stub
		User adminUser = this.checkUser(user);
		if(adminUser.getAccessType().getAccessType() == 1) {
			Factory.getDao().createFlight(flight);
		}else {
			throw new UserNotFoundException("User has no permissions to create new flights.");
		}
	}

	public ArrayList<FlightRow> getFlightsofUser(User user) throws SQLException, UserNotFoundException {
		// TODO Auto-generated method stub
		this.checkUser(user);
		return Factory.getDao().getUserSeats(user);
	}

}
