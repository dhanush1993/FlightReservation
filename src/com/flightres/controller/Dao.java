package com.flightres.controller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Dao {
	Factory factory;
	Connection conn = null;
	Statement stmt = null;
	public Dao() {
	}
	public void init() {
		try{
		      Class.forName("com.mysql.jdbc.Driver");
		      System.out.println("Connecting to database...");
		      this.conn = (Connection) DriverManager.getConnection(Factory.getConstants().DB_URL, Factory.getConstants().USER, Factory.getConstants().getPassword());
		      System.out.println("Creating database...");
		      this.stmt = (Statement) conn.createStatement();
		      String sql = "CREATE DATABASE "+Factory.getConstants().DB_NAME;
		      this.stmt.executeUpdate(sql);
		      System.out.println("Database "+Factory.getConstants().DB_NAME+" created successfully...");
		   }catch(SQLException se){
		      
		   } catch (ClassNotFoundException e) {
			
		}finally {
			try {
				this.conn = (Connection) DriverManager.getConnection(Factory.getConstants().DB_URL+Factory.getConstants().DB_NAME, Factory.getConstants().USER, Factory.getConstants().getPassword());
				System.out.println("Connecting to database...");
			    this.stmt = (Statement) conn.createStatement();
			} catch (SQLException e1) {
			}
		    
			try {
				this.createUsersTable();
			} catch (SQLException e) {
			}
		    try {
				this.createFlightsTable();
			} catch (SQLException e) {
				
			}
		}
		
	}
	private void createUsersTable() throws SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CREATE TABLE ");
		stringBuilder.append(Factory.getConstants().USER_TABLE_NAME);
		stringBuilder.append(" (id INTEGER not NULL AUTO_INCREMENT, ");
		stringBuilder.append(" firstname VARCHAR(255), ");
		stringBuilder.append(" lastname VARCHAR(255), ");
		stringBuilder.append(" username VARCHAR(255) UNIQUE, ");
		stringBuilder.append(" password VARCHAR(255), ");
		stringBuilder.append(" accesstype INTEGER, ");
		stringBuilder.append(" address VARCHAR(512), ");
		stringBuilder.append(" gender VARCHAR(10), ");
		stringBuilder.append(" age INTEGER, ");
		stringBuilder.append(" dob VARCHAR(100), ");
		stringBuilder.append(" phonenumber VARCHAR(20), ");
		stringBuilder.append(" passportnumber VARCHAR(10), PRIMARY KEY(id))");
		String sql = stringBuilder.toString();
		System.out.println(sql);
		this.stmt.executeUpdate(sql);
		
	}
	
	private void createFlightsTable() throws SQLException {
		String sql = "CREATE TABLE " +Factory.getConstants().FLIGHTS_TABLE_NAME+
                " (id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT, " +
                " flightname VARCHAR(255), " + 
                " flightnumber VARCHAR(255) UNIQUE, " + 
                " economyseats INTEGER not NULL, " +
                " firstclassseats INTEGER not NULL, " +
                " economyprice INTEGER not NULL, " +
                " firstclassprice INTEGER not NULL, " +
                " status VARCHAR(255), "+
                " arrival VARCHAR(255), " + 
                " destination VARCHAR(255), "+
                " arrivalTime VARCHAR(100), "+
                " departureTime VARCHAR(100)) ";

		this.stmt.executeUpdate(sql);
		
	}
	
	protected void createTableForUser(String name) throws SQLException {
		String sql = "CREATE TABLE " +name+
                " (id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT, " +
                " seat INTEGER, " + 
                " fid INTEGER, " + 
                " foreign key (fid) references "+Factory.getConstants().FLIGHTS_TABLE_NAME+"(id))";

		this.stmt.executeUpdate(sql);
	}
	
	protected void createTableForFlight(String name, int economySeats, int firstClassSeats) throws SQLException {
		String sql = "CREATE TABLE " +name+
                " (id INTEGER not NULL PRIMARY KEY AUTO_INCREMENT, " +
                " seat INTEGER, type INTEGER, available INTEGER)";
		this.stmt.executeUpdate(sql);
		for(int i=1;i<=economySeats;i++) {
			sql = "INSERT INTO " +name+
	                " (seat, type, available)"+
					" VALUES(?,?,?)";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1,i);
			ps.setInt(2,1);
			ps.setInt(3,1);
			ps.executeUpdate();   
		}
		for(int i=economySeats+1;i<=firstClassSeats+economySeats;i++) {
			sql = "INSERT INTO " +name+
	                " (seat, type, available)"+
					" VALUES(?,?,?)";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1,i);
			ps.setInt(2,0);
			ps.setInt(3,1);
			ps.executeUpdate();   
		}
		
	}
	
	protected void createUser(User user) throws SQLException {
		try {
			this.createTableForUser(user.getUsername());
		}catch(Exception e) {
			
		}
		String sql = "INSERT INTO " +Factory.getConstants().USER_TABLE_NAME+
                " (firstname,lastname, username, password, accesstype, address, gender, age, dob, phonenumber, passportnumber)"+
				" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		
		ps.setString(1,user.getFirstname());
		ps.setString(2,user.getLastname());
		ps.setString(3,user.getUsername());
		ps.setString(4,user.getPassword());
		ps.setInt(5,user.getAccessType().getAccessType());
		ps.setString(6,user.getAddress());
		ps.setString(7,user.getGender().toString());
		ps.setInt(8,user.getAge());
		ps.setString(9,Long.toString(user.getDob()));
		ps.setString(10,user.getNumber().toString());
		ps.setString(11,user.getPassportNumber());
		ps.executeUpdate();   
	}
	
	protected void createFlight(Flight flight) throws SQLException {
		String sql = "INSERT INTO " +Factory.getConstants().FLIGHTS_TABLE_NAME+
                " (flightname ,flightnumber ,economyseats, firstclassseats, economyprice, firstclassprice ,status ,arrival ,destination ,arrivalTime ,departureTime)"+
				" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		
		ps.setString(1,flight.getFlightname());
		ps.setString(2,flight.getFlightnumber());
		ps.setInt(3,flight.getEconomySeats());
		ps.setInt(4,flight.getFirstClassSeats());
		ps.setInt(5,flight.getEconomyPrice());
		ps.setInt(6,flight.getFirstClassPrice());
		ps.setString(7,flight.getStatus());
		ps.setString(8,flight.getArrival());
		ps.setString(9,flight.getDestination());
		ps.setString(10,Long.toString(flight.getArrivalTime()));
		ps.setString(11,Long.toString(flight.getDepartureTime()));
		ps.executeUpdate();  
		createTableForFlight(flight.getFlightnumber(), flight.getEconomySeats(), flight.getFirstClassSeats());
		
	}
	
	protected void addFlightToUser(Flight flight, User user) throws SQLException, FlightNotFoundException {
		String sql = "INSERT INTO " +user.getUsername()+
                " (fid, seat)"+
				" VALUES(?,?)";
		
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		flight = this.getFlightFromNumber(flight.getFlightnumber());
		ps.setInt(1,flight.getId());
		ps.setInt(2,user.getSelectedSeat());
		ps.executeUpdate();
	}
	
	protected void reserveFlight(Flight flight, User user) throws SQLException, SeatBookedException, NoSeatFoundException, FlightNotFoundException {
		String sql = "SELECT * FROM "+flight.getFlightnumber()+" WHERE seat="+user.getSelectedSeat();
		
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			int avail = rs.getInt("available");
			if(avail == 1) {
				addFlightToUser(flight,user);
				rs.updateInt("available", 0);
				rs.updateRow();
			}else {
				throw new SeatBookedException("Seat already booked.");
			}
		}else {
			throw new NoSeatFoundException("Sorry, the seat is not in the records.");
		}
		
	}
	public User getUser(User user) throws SQLException, UserNotFoundException, InvalidAccessTypeError {
		String sql = "SELECT * FROM "+Factory.getConstants().USER_TABLE_NAME+" WHERE username=?";
		
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			newUser.setFirstname(rs.getString("firstname"));
			newUser.setLastname(rs.getString("lastname"));
			newUser.setAddress(rs.getString("address"));
			newUser.setAge(rs.getInt("age"));
			newUser.setGender(new Gender(rs.getString("gender")));
			newUser.setAccessType(new AccessType(rs.getInt("accesstype")));
			newUser.setDob(Long.parseLong(rs.getString("dob")));
			newUser.setNumber(new Phone(rs.getString("phonenumber")));
			newUser.setPassportNumber(rs.getString("passportnumber"));
			newUser.setPassword(rs.getString("password"));
		}else
			throw new UserNotFoundException("User is not present.");
		return newUser;
	}
	public ArrayList<Flight> getAllFlights() throws SQLException {
		String sql = "SELECT * FROM "+Factory.getConstants().FLIGHTS_TABLE_NAME;
		
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		while(rs.next()) {
			Flight flight = new Flight();
			flight.setFlightname(rs.getString("flightname"));
			flight.setFlightnumber(rs.getString("flightnumber"));
			flight.setEconomySeats(rs.getInt("economyseats"));
			flight.setFirstClassSeats(rs.getInt("firstclassseats"));
			flight.setEconomyPrice(rs.getInt("economyprice"));
			flight.setFirstClassPrice(rs.getInt("firstclassprice"));
			flight.setStatus(rs.getString("status"));
			flight.setArrival(rs.getString("arrival"));
			flight.setDestination(rs.getString("destination"));
			flight.setArrivalTime(Long.parseLong(rs.getString("arrivalTime")));
			flight.setDepartureTime(Long.parseLong(rs.getString("departureTime")));
			flights.add(flight);
		}
		
		return flights;
		
	}
	public HashMap<Integer, Integer[]> getAllSeats(Flight flight) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM "+flight.getFlightnumber();
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		HashMap<Integer,Integer[]> seats = new HashMap<Integer,Integer[]>();
		while(rs.next()) {
			Integer[] s = {rs.getInt("type"),rs.getInt("available")};
			seats.put(rs.getInt("seat"), s);
		}
		
		return seats;
	}
	
	private Flight getFlightFromNumber(String flightnumber) throws SQLException, FlightNotFoundException {
		String sql = "SELECT * FROM "+Factory.getConstants().FLIGHTS_TABLE_NAME+" WHERE flightnumber=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ps.setString(1, flightnumber);
		ResultSet rs = ps.executeQuery();
		Flight flight = new Flight();
		if(rs.next()) {
			flight.setFlightname(rs.getString("flightname"));
			flight.setFlightnumber(rs.getString("flightnumber"));
			flight.setEconomySeats(rs.getInt("economyseats"));
			flight.setFirstClassSeats(rs.getInt("firstclassseats"));
			flight.setEconomyPrice(rs.getInt("economyprice"));
			flight.setFirstClassPrice(rs.getInt("firstclassprice"));
			flight.setStatus(rs.getString("status"));
			flight.setArrival(rs.getString("arrival"));
			flight.setDestination(rs.getString("destination"));
			flight.setArrivalTime(Long.parseLong(rs.getString("arrivalTime")));
			flight.setDepartureTime(Long.parseLong(rs.getString("departureTime")));
			flight.setId(rs.getInt("id"));
		}else {
			throw new FlightNotFoundException("No flight with the number was found.");
		}
		return flight;
	}
	public ArrayList<FlightRow> getUserSeats(User user) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM "+user.getUsername();
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<FlightRow> rows = new ArrayList<FlightRow>();
		while(rs.next()) {
			try {
				Flight flight = this.getFlight(rs.getInt("fid"));
				LocalDate dep = Instant.ofEpochMilli(flight.getDepartureTime()).atZone(ZoneId.systemDefault()).toLocalDate();
				if(!Period.between(LocalDate.now(), dep).isNegative()) {
					FlightRow row = new FlightRow();
					row.setFlightNumber(flight.getFlightnumber());
					row.setFrom(flight.getArrival());
					row.setTo(flight.getDestination());
					row.setArrivalTime(flight.getArrivalTimeD());
					row.setDepartureTime(flight.getDepartureTimeD());
					row.setSeat(Integer.toString(rs.getInt("seat")));
					rows.add(row);
				}
			} catch (FlightNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rows;
	}
	private Flight getFlight(int id) throws SQLException, FlightNotFoundException {
		String sql = "SELECT * FROM "+Factory.getConstants().FLIGHTS_TABLE_NAME+" WHERE id=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Flight flight = new Flight();
		if(rs.next()) {
			flight.setFlightname(rs.getString("flightname"));
			flight.setFlightnumber(rs.getString("flightnumber"));
			flight.setEconomySeats(rs.getInt("economyseats"));
			flight.setFirstClassSeats(rs.getInt("firstclassseats"));
			flight.setEconomyPrice(rs.getInt("economyprice"));
			flight.setFirstClassPrice(rs.getInt("firstclassprice"));
			flight.setStatus(rs.getString("status"));
			flight.setArrival(rs.getString("arrival"));
			flight.setDestination(rs.getString("destination"));
			flight.setArrivalTime(Long.parseLong(rs.getString("arrivalTime")));
			flight.setDepartureTime(Long.parseLong(rs.getString("departureTime")));
			flight.setId(rs.getInt("id"));
		}else {
			throw new FlightNotFoundException("No flight with the number was found.");
		}
		return flight;
		// TODO Auto-generated method stub
		
	}
	
}
