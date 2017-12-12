package com.riteboiler.frontoffice.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class defines the Preference model.
 * It contains getters and setters for fields
 * and properties.
 * @author Chad Meza
 */
public class Preference {
	private final StringProperty username;
	private final StringProperty ackLocation;
	private final StringProperty billLocation;
	
	/**
	 * This is a parameterless constructor
	 * which runs another constructor, 
	 * passing in "null" for all the parameters.
	 * This is called when creating a new Preference.
	 */
	public Preference() {
		this(null, null, null);
	}
	
	public Preference(String username, String ackLocation, String billLocation) {
		this.username = new SimpleStringProperty(username);
		this.ackLocation = new SimpleStringProperty(ackLocation);
		this.billLocation = new SimpleStringProperty(billLocation);
	}
	
	public String getUsername() {
		return username.get();
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public StringProperty usernameProperty() {
		return username;
	}

	public String getAckLocation() {
		return ackLocation.get();
	}
	
	public void setAckLocation(String ackLocation) {
		this.ackLocation.set(ackLocation);
	}
	
	public StringProperty ackLocationProperty() {
		return ackLocation;
	}
	
	public String getBillLocation() {
		return billLocation.get();
	}
	
	public void setBillLocation(String billLocation) {
		this.billLocation.set(billLocation);
	}
	
	public StringProperty billLocationProperty() {
		return billLocation;
	}
}
