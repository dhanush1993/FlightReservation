package com.flightres.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.flightres.data.Flight;
import com.flightres.exceptions.UserNotFoundException;
import com.flightres.factory.Factory;
import com.flightres.gui.SeatsUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class SearchUIHandler implements Initializable {
	
	@FXML
	private VBox content;
	@FXML
	private TableView<Flight> searchTable;
	@FXML
	private TableColumn flightnumber;
	@FXML
	private TableColumn departure;
	@FXML
	private TableColumn arrival;
	@FXML
	private TableColumn from;
	@FXML
	private TableColumn to;
	@FXML
	private TableColumn economySeats;
	@FXML
	private TableColumn economyPrice;
	@FXML
	private TableColumn firstClassSeats;
	@FXML
	private TableColumn firstClassPrice;

	public AnchorPane contentAnchorPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		contentAnchorPane = (AnchorPane) content.getParent();
		try {
			ArrayList<Flight> flights = Factory.getController().getFlights(Factory.getUser());
			ObservableList<Flight> data = FXCollections.observableArrayList(flights);
			flightnumber.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightnumber"));
			departure.setCellValueFactory(new PropertyValueFactory<Flight, Long>("departureTimeD"));
			arrival.setCellValueFactory(new PropertyValueFactory<Flight, Long>("arrivalTimeD"));
			from.setCellValueFactory(new PropertyValueFactory<Flight, String>("arrival"));
			to.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
			economySeats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("economySeats"));
			economyPrice.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("economyPrice"));
			firstClassSeats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("firstClassSeats"));
			firstClassPrice.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("firstClassPrice"));
			//reserve.setCellValueFactory(new );
			searchTable.setItems(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void bookFirstClass(ActionEvent event) {
		SeatsUI ui = new SeatsUI();
		Flight flight = searchTable.getSelectionModel().getSelectedItem();
		Factory.setFlight(flight);
		try {
			HashMap<Integer, Integer[]> seats = null;
			try {
				seats = Factory.getController().getAllSeats(Factory.getUser(),flight);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			VBox node = ui.getFirstClassSeats(seats);
			node.getChildren().add(this.getButtons());
			node.setPrefHeight(content.getHeight());
			node.setPrefWidth(content.getWidth()-100);
			contentAnchorPane = (AnchorPane) content.getParent();
			contentAnchorPane.getChildren().clear();
			contentAnchorPane.getChildren().addAll(node);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void bookEconomy(ActionEvent event) {
		SeatsUI ui = new SeatsUI();
		Flight flight = searchTable.getSelectionModel().getSelectedItem();
		Factory.setFlight(flight);
		try {
			HashMap<Integer, Integer[]> seats = null;
			try {
				seats = Factory.getController().getAllSeats(Factory.getUser(),flight);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			VBox node = ui.getEconomySeats(seats);
			
			node.getChildren().add(this.getButtons());
			node.setPrefHeight(content.getHeight());
			node.setPrefWidth(content.getWidth()-100);
			contentAnchorPane = (AnchorPane)content.getParent();
			contentAnchorPane.getChildren().clear();
			contentAnchorPane.getChildren().addAll(node);
		}catch(Exception e) {
			
		}
	}
	
	private Node getButtons() {
		Button confirm = new Button();
		Button cancel = new Button();
		confirm.setText("Confirm");
		confirm.setStyle("-fx-background-color:gray");
		confirm.setTextFill(Paint.valueOf("white"));
		confirm.setPrefWidth(90);
		confirm.setPrefHeight(50);
		cancel.setText("Cancel");
		cancel.setOnAction(new cancelEventListener((AnchorPane)this.content.getParent()));
		confirm.setOnAction(new confirmEventListener((AnchorPane)this.content.getParent()));
		cancel.setStyle("-fx-background-color:gray");
		cancel.setTextFill(Paint.valueOf("white"));
		cancel.setPrefWidth(90);
		cancel.setPrefHeight(50);
		HBox box = new HBox();
		box.setSpacing(20);
		box.setMinHeight(80);
		box.setAlignment(Pos.TOP_RIGHT);
		box.getChildren().add(confirm);
		box.getChildren().add(cancel);
		box.setPadding(new Insets(20,20,20,20));
		return box;
	}

}
