package com.flightres.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.flightres.data.Flight;
import com.flightres.data.FlightRow;
import com.flightres.exceptions.UserNotFoundException;
import com.flightres.factory.Factory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReservationsUIHandler implements Initializable{
	
	@FXML
	private TableColumn flightNumber;
	@FXML
	private TableColumn from;
	@FXML
	private TableColumn to;
	@FXML
	private TableColumn arrivalTime;
	@FXML
	private TableColumn departureTime;
	@FXML
	private TableColumn seat;
	@FXML
	private TableView reservationsTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.loadUI();
	}

	private void loadUI() {
		// TODO Auto-generated method stub
		ArrayList<FlightRow> flights = null;
		try {
			flights = Factory.getController().getFlightsofUser(Factory.getUser());
		} catch (SQLException | UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<FlightRow> data = FXCollections.observableArrayList(flights);
		flightNumber.setCellValueFactory(new PropertyValueFactory<FlightRow, String>("flightNumber"));
		departureTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureTime"));
		arrivalTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("arrivalTime"));
		from.setCellValueFactory(new PropertyValueFactory<Flight, String>("from"));
		to.setCellValueFactory(new PropertyValueFactory<Flight, String>("to"));
		seat.setCellValueFactory(new PropertyValueFactory<Flight, String>("seat"));
		reservationsTable.setItems(data);
	}
	

}
