package com.flightres.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import resources.Resources;
import sun.applet.Main;

public class Login extends Application{
	
		
		public static Stage stage;
		@Override
		public void start(Stage stage) throws Exception {
		    stage.getIcons().add(new Image(getClass().getResource("/resources/img_289866.png").toString()));
	        stage.setTitle("Flights Reservation");
	        stage.setResizable(false);
		    Parent root;
		    root = FXMLLoader.load(getClass().getResource("/resources/login.fxml"));
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    this.stage = stage;
		    stage.show();
		}

}
