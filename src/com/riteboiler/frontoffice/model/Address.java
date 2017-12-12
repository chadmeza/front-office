package com.riteboiler.frontoffice.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class defines the Address model.
 * It contains getters and setters for fields
 * and properties.
 * @author Chad Meza
 */
public class Address {
	private final IntegerProperty id;
	private final StringProperty name;
	private final StringProperty street1;
	private final StringProperty city;
	private final StringProperty state;
	private final StringProperty zip;
	private final StringProperty country;
	
	/**
	 * This is a parameterless constructor
	 * which runs another constructor, 
	 * passing in "null" for all the parameters.
	 * This is called when creating a new Address.
	 */
	public Address() {
		this(0, null, null, null, null, null, null);
	}
	
	public Address(int id, String name, String street1, String city, String state, String zip, String country) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.street1 = new SimpleStringProperty(street1);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zip = new SimpleStringProperty(zip);
		this.country = new SimpleStringProperty(country);
	}
	
	public int getId() {
		return id.get();
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public IntegerProperty idProperty() {
		return id;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public String getStreet1() {
		return street1.get();
	}
	
	public void setStreet1(String street1) {
		this.street1.set(street1);
	}
	
	public StringProperty street1Property() {
		return street1;
	}
	
	public String getCity() {
		return city.get();
	}
	
	public void setCity(String city) {
		this.city.set(city);
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	public String getState() {
		return state.get();
	}
	
	public void setState(String state) {
		this.state.set(state);
	}
	
	public StringProperty stateProperty() {
		return state;
	}
	
	public String getZip() {
		return zip.get();
	}
	
	public void setZip(String zip) {
		this.zip.set(zip);
	}
	
	public StringProperty zipProperty() {
		return zip;
	}
	
	public String getCountry() {
		return country.get();
	}
	
	public void setCountry(String country) {
		this.country.set(country);
	}
	
	public StringProperty countryProperty() {
		return country;
	}
}
