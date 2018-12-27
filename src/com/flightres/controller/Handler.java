package com.flightres.controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.ResourceBundle;

import com.flightres.data.AccessType;
import com.flightres.data.Gender;
import com.flightres.data.Phone;
import com.flightres.data.User;
import com.flightres.exceptions.InvalidAccessTypeError;
import com.flightres.exceptions.UserNotFoundException;
import com.flightres.factory.Factory;
import com.flightres.gui.Login;
import com.flightres.gui.SignUp;
public class Handler  implements Initializable
{
	@FXML
	private TextField username;
	@FXML
	private PasswordField pass;
	@FXML
    private TextField firstname;
	@FXML
    private TextField lastname;
	@FXML
    private TextField username_signup;
	@FXML
    private TextField address;
	@FXML
    private TextField ext;
	@FXML
    private TextField number;
	@FXML
    private TextField passportnumber;
	@FXML
	private DatePicker dob;
	@FXML
	private PasswordField pass_signup;
	@FXML
	private PasswordField conf_pass;
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private Label errorMsg;
	@FXML
	private Label loginMsg;
	
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    @FXML
    private void loginHandler(ActionEvent event)
    {
    	User user = new User();
    	user.setUsername(username.getText());
    	MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] hash = digest.digest(pass.getText().trim().getBytes(StandardCharsets.UTF_8));
		String sha256hex = new String(hash);
		user.setPassword(sha256hex);
    	try {
			user = Factory.getController().checkUser(user);
			Factory.setUser(user);
			loadUI(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			loginMsg.setText("Invalid username or password.");;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void loadUI(User user) throws IOException {
		// TODO Auto-generated method stub
    	Login.stage.close();
    	Parent root;
    	if(user.getAccessType().getAccessType() == 1) {
    		root = FXMLLoader.load(getClass().getResource("/resources/adminUI.fxml"));
    	}else {
    		root = FXMLLoader.load(getClass().getResource("/resources/userUI.fxml"));
    	}
	    Scene scene = new Scene(root);
	    Login.stage.setScene(scene);
	    //Login.stage.setMaximized(false);
	    Login.stage.setResizable(true);
	    Login.stage.show();
//	    Node node = FXMLLoader.load(getClass().getResource("/resources/search.fxml"));
//	    contentAnchorPane.getChildren().setAll(node);
		
	}
	@FXML
    private void openSignUp(ActionEvent event) throws IOException {
    	Login.stage.close();
    	Parent root;
	    root = FXMLLoader.load(getClass().getResource("/resources/signUp.fxml"));
	    Scene scene = new Scene(root);
	    Login.stage.setScene(scene);
	    Login.stage.show();
    }
    
    @FXML
    private void signUpHandler(ActionEvent event) throws IOException {
    	StringBuilder error = new StringBuilder();
    	if(firstname.getText().trim().length() <= 0)
    		error.append("Please enter valid First Name.\n");
    	if(lastname.getText().trim().length() <= 0)
    		error.append("Please enter valid Last Name.\n");
    	if(username_signup.getText().length() <= 0)
    		error.append("Please enter valid Username.\n");
    	if(pass_signup.getText().trim().length() <= 0)
    		error.append("Please enter valid Password.\n");
    	else if(pass_signup.getText().trim().length()<8 || pass_signup.getText().trim().length()>12)
    		error.append("Password length should be between 8 to 12 characters\n");
    	if(!pass_signup.getText().trim().equals(conf_pass.getText().trim()))
    		error.append("Passwords should match.\n");
    	try {
    		Timestamp dobT = Timestamp.valueOf(dob.getValue().atStartOfDay());
    	}catch(Exception e) {
    		error.append("Please select your date of birth.\n");
    	}	
    	if(address.getText().trim().length() <= 0)
    		error.append("Please enter valid Address.\n");
    	if(ext.getText().trim().length() <= 0)
    		error.append("Please enter the extension for your phone number.\n");
    	if(number.getText().trim().length() <= 0)
    		error.append("Please enter your phone number.\n");
    	RadioButton gender = (RadioButton)genderGroup.getSelectedToggle();
    	try {
	    	if(gender.getText()==null)
	    		error.append("Please select your gender.\n");
    	}catch(Exception e) {
    		error.append("Please select your gender.\n");
    	}
    	if(passportnumber.getText().trim().length() <= 0)
    		error.append("Please enter your passport number.\n");
    	if(Period.between(dob.getValue(), LocalDate.now()).isNegative()) {
    		error.append("Please enter valid dob.\n");
    	}
    	
    	if(!error.toString().equals(""))
    		errorMsg.setText(error.toString());
    	else {
    		User user = new User();
    		user.setUsername(username_signup.getText().trim());
    		user.setFirstname(firstname.getText().trim());
    		user.setLastname(lastname.getText().trim());
    		MessageDigest digest = null;
			try {
				digest = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		byte[] hash = digest.digest(pass_signup.getText().trim().getBytes(StandardCharsets.UTF_8));
    		String sha256hex = new String(hash);
    		user.setPassword(sha256hex);
    		try {
				user.setAccessType(new AccessType(0));
			} catch (InvalidAccessTypeError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		user.setDob(Timestamp.valueOf(dob.getValue().atStartOfDay()).getTime());
    		user.setAddress(address.getText().trim());
    		user.setAge(Period.between(dob.getValue(), LocalDate.now()).getYears());
    		user.setNumber(new Phone(ext.getText(),number.getText()));
    		user.setPassportNumber(passportnumber.getText());
    		user.setGender(new Gender(((RadioButton)genderGroup.getSelectedToggle()).getText().toUpperCase()));
    		try {
				Factory.getController().createUser(user);
				this.username = this.username_signup;
				this.pass = this.pass_signup;
				this.loginHandler(new ActionEvent());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				errorMsg.setText("Username already exists.");
			}
    	}
    }
}
