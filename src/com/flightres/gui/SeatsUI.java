package com.flightres.gui;

import java.util.HashMap;

import com.flightres.factory.Factory;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SeatsUI {
	
	public VBox getEconomySeats(HashMap<Integer, Integer[]> seats) {
		VBox root = new VBox();
		AnchorPane.setLeftAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		AnchorPane.setTopAnchor(root, 0.0);
		AnchorPane.setBottomAnchor(root, 0.0);
		HBox seatLabelBox = new HBox();
		seatLabelBox.setAlignment(Pos.TOP_CENTER);
		Label seatLabel = new Label("Please select a seat");
		seatLabel.setFont(new Font(20));
		seatLabelBox.getChildren().add(seatLabel);
		root.getChildren().add(seatLabelBox);
		for(int i=1;i<=seats.size();i=i++) {
			root.setFillWidth(true);
			HBox box = new HBox();
			box.setSpacing(10);
			box.setAlignment(Pos.TOP_CENTER);
			box.setPadding(new Insets(20, 0, 0, 0));
			for(int j=0;j<2 && i<=seats.size();j++,i++) {
				Button btn = new Button();
				if(seats.get(i)[0] == 1){
					btn.setText(Integer.toString(i));
					btn.setPrefWidth(75);
					btn.setPrefHeight(75);
					box.getChildren().add(btn);
				}
				if(seats.get(i)[1] == 0) {
					btn.setStyle("-fx-background-color:red");
				}else {
					btn.setStyle("-fx-background-color:green");
					btn.setOnAction(new SelectedSeatHandler(seatLabel));
				}
			}
			Separator separator = new Separator();
			separator.setOrientation(Orientation.HORIZONTAL);
			box.getChildren().add(separator);
			for(int j=0;j<2 && i<=seats.size();j++,i++) {
				Button btn = new Button();
				if(seats.get(i)[0] == 1){
					btn.setText(Integer.toString(i));
					btn.setPrefWidth(75);
					btn.setPrefHeight(75);
					box.getChildren().add(btn);
				}
				if(seats.get(i)[1] == 0) {
					btn.setStyle("-fx-background-color:red");
				}else {
					btn.setStyle("-fx-background-color:green");
					btn.setOnAction(new SelectedSeatHandler(seatLabel));
				}
			}
			root.getChildren().add(box);
		}
		return root;
	}
	
	public VBox getFirstClassSeats(HashMap<Integer, Integer[]> seats) {
		VBox root = new VBox();
		AnchorPane.setLeftAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		AnchorPane.setTopAnchor(root, 0.0);
		AnchorPane.setBottomAnchor(root, 0.0);
		HBox seatLabelBox = new HBox();
		seatLabelBox.setAlignment(Pos.TOP_CENTER);
		Label seatLabel = new Label("Please select a seat");
		seatLabel.setFont(new Font(20));
		seatLabelBox.getChildren().add(seatLabel);
		seatLabel.setId("seatstatus");
		root.getChildren().add(seatLabelBox);
		for(int i=1;i<=seats.size();i=i++) {
			boolean taken = false;
			root.setFillWidth(true);
			HBox box = new HBox();
			box.setSpacing(10);
			box.setAlignment(Pos.TOP_CENTER);
			box.setPadding(new Insets(20, 0, 0, 0));
			for(int j=0;j<2 && i<=seats.size();j++,i++) {
				Button btn = new Button();
				if(seats.get(i)[0] == 0){
					btn.setText(Integer.toString(i));
					btn.setPrefWidth(75);
					btn.setPrefHeight(75);
					box.getChildren().add(btn);
					taken = true;
				}
				if(seats.get(i)[1] == 0) {
					btn.setStyle("-fx-background-color:red");
				}else {
					btn.setStyle("-fx-background-color:green");
					btn.setOnAction(new SelectedSeatHandler(seatLabel));
					btn.setId(Integer.toString(i));
				}
			}
			Separator separator = new Separator();
			separator.setOrientation(Orientation.HORIZONTAL);
			box.getChildren().add(separator);
			for(int j=0;j<2 && i<=seats.size();j++,i++) {
				Button btn = new Button();
				if(seats.get(i)[0] == 0){
					btn.setText(Integer.toString(i));
					btn.setPrefWidth(75);
					btn.setPrefHeight(75);
					box.getChildren().add(btn);
					taken = true;
				}
				if(seats.get(i)[1] == 0) {
					btn.setStyle("-fx-background-color:red");
				}else {
					btn.setStyle("-fx-background-color:green");
					btn.setOnAction(new SelectedSeatHandler(seatLabel));
					btn.setId(Integer.toString(i));
				}
			}
			if(taken)
				root.getChildren().add(box);
		}
		return root;
	}
}

class SelectedSeatHandler implements EventHandler{
	
	Label label;
	public SelectedSeatHandler(Label label) {
		// TODO Auto-generated constructor stub
		this.label = label;
	}

	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		Button btn = (Button) event.getSource();
		int seatNumber = Integer.parseInt(btn.getText());
		if(seatNumber == Factory.getUser().getSelectedSeat()) {
			this.label.setText("Please select a seat.");
			btn.setStyle("-fx-background-color:green");
		}
		else {
			try {
				(btn.getParent().getParent().lookup("#"+Factory.getUser().getSelectedSeat())).setStyle("-fx-background-color:green");;
			}catch(Exception e) {
				
			}
			Factory.getUser().setSelectedSeat(seatNumber);
			this.label.setText("You selected seat number "+seatNumber);
			btn.setStyle("-fx-background-color:blue");
		}
		
	}
	
}
