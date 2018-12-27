package com.flightres.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.flightres.data.Flight;
import com.flightres.exceptions.UserNotFoundException;
import com.flightres.factory.Factory;
import com.sun.prism.impl.BaseMesh.FaceMembers;
import com.sun.xml.internal.bind.v2.runtime.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CreateFlightUIHandler implements Initializable {
	
	@FXML
	private Button createFlightBtn;
	@FXML
	private TextField flightnumber;
	@FXML
	private TextField flightname;
	@FXML
	private TextField economyseats;
	@FXML
	private TextField firstclassseats;
	@FXML
	private TextField economyprice;
	@FXML
	private TextField firstclassprice;
	@FXML
	private TextField from;
	@FXML
	private TextField to;
	@FXML
	private DatePicker arrivaltime;
	@FXML
	private DatePicker departuretime;
	@FXML
	private Label errorLabel;
	@FXML
	private Button cancelBtn;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void checkFlight(ActionEvent event) throws IOException {
		StringBuilder errorMsg = new StringBuilder();
		if(this.flightnumber.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid flightnumber.\n");
		}
		if(this.flightname.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid flightname.\n");
		}
		if(this.economyseats.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid number of economy seats.\n");
		}
		if(this.firstclassseats.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid number of first class seats.\n");
		}
		if(this.economyprice.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid economy ticket price (In Dollars, No fractions).\n");
		}
		if(this.firstclassprice.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid first class ticket price (In Dollars, No fractions).\n");
		}
		if(this.from.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid from Airport.\n");
		}
		if(this.to.getText().trim().length() <= 0) {
			errorMsg.append("Please enter valid destination Airport.\n");
		}
		boolean arrivalExc = false;
		try {
    		Timestamp ar = Timestamp.valueOf(this.arrivaltime.getValue().atStartOfDay());
    		}catch(Exception e) {
    			arrivalExc = true;
    		errorMsg.append("Please select valid arrival time.\n");
    	}
		try {
    		Timestamp dobT = Timestamp.valueOf(this.departuretime.getValue().atStartOfDay());
    		if(Period.between(LocalDate.now(), this.departuretime.getValue()).isNegative())
    			errorMsg.append("Departure date should be greater than today date.\n");
    		if(arrivalExc == false)
	    		if(Period.between(this.departuretime.getValue(), this.arrivaltime.getValue()).isNegative()){
	    			errorMsg.append("Arrival date should be greater than departure date.\n");
	    		}
    	}catch(Exception e) {
    		errorMsg.append("Please select valid departure time.\n");
    	}
		int economySeats = 0, firstClassSeats = 0, economyPrice = 0, firstClassPrice = 0;
		try {
			economySeats = Integer.parseInt(this.economyseats.getText());
		}catch(Exception e) {
			errorMsg.append("Economy seats has to be a number.\n");
		}
		try {
			firstClassSeats = Integer.parseInt(this.firstclassseats.getText());
		}catch(Exception e) {
			errorMsg.append("First class seats has to be a number.\n");
		}
		try {
			economyPrice = Integer.parseInt(this.economyprice.getText());
		}catch(Exception e) {
			errorMsg.append("Economy price has to be a number.\n");
		}
		try {
			firstClassPrice = Integer.parseInt(this.firstclassprice.getText());
		}catch(Exception e) {
			errorMsg.append("First class price has to be a number.\n");
		}
		
		
		if(!errorMsg.toString().equals("")) {
    		this.errorLabel.setText(errorMsg.toString());
			HBox box = ((HBox)this.errorLabel.getParent());
			box.setPrefHeight(errorMsg.toString().split("\n").length*22);
			box.setMinHeight(errorMsg.toString().split("\n").length*22);
			}else {
    		Flight flight = new Flight();
    		flight.setFlightnumber(this.flightnumber.getText());
    		flight.setFlightname(this.flightname.getText());
    		flight.setEconomySeats(economySeats);
    		flight.setFirstClassSeats(firstClassSeats);
    		flight.setEconomyPrice(economyPrice);
    		flight.setFirstClassPrice(firstClassPrice);
    		flight.setArrival(this.from.getText());
    		flight.setDestination(this.to.getText());
    		flight.setArrivalTime(Timestamp.valueOf(this.arrivaltime.getValue().atStartOfDay()).getTime());
    		flight.setDepartureTime(Timestamp.valueOf(this.departuretime.getValue().atStartOfDay()).getTime());
    		try {
				Factory.getController().createFlight(flight,Factory.getUser());
				FXMLLoader loader = new FXMLLoader();
				Node node = loader.load(getClass().getResource("/resources/searchUI.fxml"));
				AnchorPane pane = (AnchorPane) this.createFlightBtn.getParent().getParent().getParent();
				pane.getChildren().clear();
				pane.getChildren().addAll(node);
			} catch (SQLException | UserNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
    	}
	}
	
	@FXML
	private void cancelBtnListener(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		//loader.setController(this);
		Node node = null;
		try {
			node = loader.load(getClass().getResource("/resources/searchUI.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//loader.setController(this);
		Factory.getUser().setSelectedSeat(0);
		AnchorPane pane = (AnchorPane) this.cancelBtn.getParent().getParent().getParent();
		pane.getChildren().clear();
		pane.getChildren().addAll(node);
	}

}
