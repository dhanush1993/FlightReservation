package com.flightres.factory;

import com.flightres.constants.CONSTANTS;
import com.flightres.controller.Controller;
import com.flightres.controller.Dao;
import com.flightres.data.Flight;
import com.flightres.data.User;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Factory {
	
	private static Dao dao;
	private static CONSTANTS constants;
	private static Controller controller;
	private static User user;
	public static ObservableList<Node> initUI;
	private static Flight flight;
	
	public static Dao getDao() {
		if(dao == null) {
			dao = new Dao();
		}
		return dao;
		
	}
	
	public static CONSTANTS getConstants() {
		if(constants == null) {
			constants = new CONSTANTS();
		}
		return constants;
		
	}

	public static Controller getController() {
		// TODO Auto-generated method stub
		if(controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	public static User getUser() {
		// TODO Auto-generated method stub
		return user;
	}
	
	public static void setUser(User user) {
		Factory.user = user;
	}

	public static void setFlight(Flight flight) {
		// TODO Auto-generated method stub
		Factory.flight = flight;
	}

	public static Flight getFlight() {
		// TODO Auto-generated method stub
		return Factory.flight;
	}

}
