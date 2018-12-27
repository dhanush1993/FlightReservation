package com.flightres.constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CONSTANTS {
	
	public String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public String DB_URL = "jdbc:mysql://localhost/";
	public String USER = "root";
	private String PASS = "root";
	public String DB_NAME = "FlightsReservation";
	public String USER_TABLE_NAME = "Users";
	public String FLIGHTS_TABLE_NAME = "Flights";
	public CONSTANTS() {
		BufferedReader br;
		File file = new File(".conf");
		
		try {
			if(file.createNewFile()) {
				BufferedWriter wr = new BufferedWriter(new FileWriter(file));
				wr.write("JDBC_DRIVER = com.mysql.jdbc.Driver\n");
				wr.write("DB_URL = jdbc:mysql://localhost/\n");
				wr.write("USER = root\n");
				wr.write("PASS = root\n");
				wr.write("DB_NAME = FlightsReservation\n");
				wr.write("USER_TABLE_NAME = Users\n");
				wr.write("FLIGHTS_TABLE_NAME = Flights\n");
				wr.flush();
				wr.close();
			}else {
				br = new BufferedReader(new FileReader(file));
				String st;
				while ((st = br.readLine()) != null) {
					st = st.trim();
					String keyVal[] = st.split("=");
					this.setKeyVal(keyVal);
				}
				br.close();
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setKeyVal(String[] keyVal) {
		switch(keyVal[0].toUpperCase().trim()) {
			case "JDBC_DRIVER":this.JDBC_DRIVER = keyVal[1].trim();break;
			case "DB_URL":this.DB_URL = keyVal[1].trim();break;
			case "USER":this.USER = keyVal[1].trim();break;
			case "PASS":this.PASS = keyVal[1].trim();break;
			case "DB_NAME":this.DB_NAME = keyVal[1].trim();break;
			case "USER_TABLE_NAME":this.USER_TABLE_NAME  = keyVal[1].trim();break;
			case "FLIGHT_TABLE_NAME":this.FLIGHTS_TABLE_NAME   = keyVal[1].trim();break;
			default: break;
		}
		
	}
	
	public String getPassword() {
		return this.PASS;
	}
	 
}
