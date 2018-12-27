package com.flightres.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.flightres.data.Flight;
import com.flightres.data.User;
import com.flightres.exceptions.FlightNotFoundException;
import com.flightres.exceptions.NoSeatFoundException;
import com.flightres.exceptions.SeatBookedException;
import com.flightres.exceptions.UserNotFoundException;
import com.flightres.factory.Factory;
import com.flightres.gui.Login;
import com.flightres.gui.SeatsUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserUIHandler implements Initializable {
	
	@FXML
	private AnchorPane contentAnchorPane;
	
	@FXML
	private Button createFlightBtn;
	
	@FXML
	private Button searchBtn;
	
	@FXML
	private Label username;
	
	@FXML
	private Label name;
	
	@FXML
	private Label gender;
	
	@FXML
	private Label dob;
	
	@FXML
	private Label address;
	
	@FXML
	private Label phone;
	
	@FXML
	private Label passport;
	
	@FXML
	private Button reservationsBtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//contentAnchorPane.setPrefWidth(((SplitPane)contentAnchorPane.getParent()).getWidth());
		Login.stage.setMaximized(true);
		User user = Factory.getUser();
		this.username.setText(user.getUsername());
		this.name.setText(user.getFirstname()+" "+user.getLastname());
		this.gender.setText(user.getGender().toString());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		this.dob.setText(simpleDateFormat.format(new Date(user.getDob())));
		this.address.setText(user.getAddress());
		this.phone.setText(user.getNumber().toString());
		this.passport.setText(user.getPassportNumber());
		try {
			this.searchUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void reservationsBtnListener(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Node node = loader.load(getClass().getResource("/resources/reservationsUI.fxml"));
		contentAnchorPane.getChildren().clear();
		contentAnchorPane.getChildren().addAll(node);
	}
	
	private void searchUI() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		//loader.setController(this);
		Node node = loader.load(getClass().getResource("/resources/searchUI.fxml"));
		//loader.setController(this);
		contentAnchorPane.getChildren().clear();
		contentAnchorPane.getChildren().addAll(node);
	}
	
	@FXML
	private void changePane2CreateFlight(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Node node = loader.load(getClass().getResource("/resources/createFlightUI.fxml"));
		contentAnchorPane.getChildren().clear();
		contentAnchorPane.getChildren().addAll(node);
	}
	
	@FXML
	private void searchBtnListener(ActionEvent event) {
		try {
			this.searchUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}

class cancelEventListener implements EventHandler<ActionEvent>{
	
	private AnchorPane contentPane;

	public cancelEventListener(AnchorPane contentPane) {
		// TODO Auto-generated constructor stub
		this.contentPane = contentPane;
	}

	@Override
	public void handle(ActionEvent event) {
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
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().addAll(node);
		
	}
	
	
	
}


class confirmEventListener implements EventHandler<ActionEvent>{
	
	private AnchorPane contentPane;

	public confirmEventListener(AnchorPane contentPane) {
		// TODO Auto-generated constructor stub
		this.contentPane = contentPane;
	}

	@Override
	public void handle(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		Node node = null;
		try {
			node = loader.load(getClass().getResource("/resources/searchUI.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Factory.getUser().getSelectedSeat() != 0) {
			try {
				Factory.getController().addSeatToUser(Factory.getFlight(), Factory.getUser());
				this.contentPane.getChildren().clear();
				this.contentPane.getChildren().addAll(node);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			} catch (SeatBookedException e) {
				Label seatLabel = (Label)this.contentPane.lookup("#seatstatus");
				seatLabel.setText("Sorry! the seat is already booked.");
				Button btn = (Button)this.contentPane.lookup("#"+Factory.getUser().getSelectedSeat());
				btn.setStyle("-fx-background-color:red");
				btn.setOnAction(null);
			} catch (NoSeatFoundException e) {
				e.printStackTrace();
			} catch (FlightNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
}
