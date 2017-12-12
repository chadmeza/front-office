package com.riteboiler.frontoffice.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class defines the Bill Of Lading model.
 * It contains getters and setters for fields
 * and properties.
 * @author Chad Meza
 */
public class BillOfLading {
	private final IntegerProperty id;
	private final StringProperty acknowledgmentId;
	private final StringProperty date;
	private final IntegerProperty shipTo;
	private final StringProperty contact;
	private final StringProperty carrier;
	private final StringProperty agentNumber;
	private final IntegerProperty isPrepaid;
	private final IntegerProperty isBoilerNoWheels;
	private final IntegerProperty isBoilerPart;
	private final IntegerProperty isAirRideVerified;
	private final IntegerProperty isTarped;
	private final StringProperty notes;
	private final StringProperty username;
	private final StringProperty shipToName;
	private final StringProperty otherPayment;
	private final BooleanProperty isAtmospheric;
	private Address shipToAddress;
	private ObservableList<Boiler> boilerData = FXCollections.observableArrayList();
	private ObservableList<Pallet> palletData = FXCollections.observableArrayList();
	
	/**
	 * This is a parameterless constructor
	 * which runs another constructor, 
	 * passing in "null" for all the parameters.
	 * This is called when creating a new Bill Of Lading.
	 */
	public BillOfLading() {
		this(0, null, null, 0, null, null, null, 0, 0, 0, 0, 0, null, null, null, null, false);
	}
	
	public BillOfLading(int id, String acknowledgmentId, String date, int shipTo, String contact, String carrier,
			String agentNumber, int isPrepaid, int isBoilerNoWheels, int isBoilerPart, int isAirRideVerified, int isTarped,
			String notes, String username, String shipToName, String otherPayment, boolean isAtmospheric) {
		this.id = new SimpleIntegerProperty(id);
		this.acknowledgmentId = new SimpleStringProperty(acknowledgmentId);
		this.date = new SimpleStringProperty(date);
		this.shipTo = new SimpleIntegerProperty(shipTo);
		this.contact = new SimpleStringProperty(contact);
		this.carrier = new SimpleStringProperty(carrier);
		this.agentNumber = new SimpleStringProperty(agentNumber);
		this.isPrepaid = new SimpleIntegerProperty(isPrepaid);
		this.isBoilerNoWheels = new SimpleIntegerProperty(isBoilerNoWheels);
		this.isBoilerPart = new SimpleIntegerProperty(isBoilerPart);
		this.isAirRideVerified = new SimpleIntegerProperty(isAirRideVerified);
		this.isTarped = new SimpleIntegerProperty(isTarped);
		this.notes = new SimpleStringProperty(notes);
		this.username = new SimpleStringProperty(username);
		this.shipToName = new SimpleStringProperty(shipToName);
		this.otherPayment = new SimpleStringProperty(otherPayment);
		this.isAtmospheric = new SimpleBooleanProperty(isAtmospheric);
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
	
	public String getAcknowledgmentId() {
		return acknowledgmentId.get();
	}
	
	public void setAcknowledgmentId(String acknowledgmentId) {
		this.acknowledgmentId.set(acknowledgmentId);
	}
	
	public StringProperty acknowledgmentIdProperty() {
		return acknowledgmentId;
	}
	
	public String getDate() {
		return date.get();
	}
	
	public void setDate(String date) {
		this.date.set(date);
	}
	
	public StringProperty dateProperty() {
		return date;
	}

	public int getShipTo() {
		return shipTo.get();
	}
	
	public void setShipTo(int shipTo) {
		this.shipTo.set(shipTo);
	}
	
	public IntegerProperty shipToProperty() {
		return shipTo;
	}
	
	public String getContact() {
		return contact.get();
	}
	
	public void setContact(String contact) {
		this.contact.set(contact);
	}
	
	public StringProperty contactProperty() {
		return contact;
	}
	
	public String getCarrier() {
		return carrier.get();
	}
	
	public void setCarrier(String carrier) {
		this.carrier.set(carrier);
	}
	
	public StringProperty carrierProperty() {
		return carrier;
	}
	
	public String getAgentNumber() {
		return agentNumber.get();
	}
	
	public void setAgentNumber(String agentNumber) {
		this.agentNumber.set(agentNumber);
	}
	
	public StringProperty agentNumberProperty() {
		return agentNumber;
	}
	
	public boolean getIsPrepaid() {
		if (isPrepaid.get() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setIsPrepaid(int isPrepaid) {
		this.isPrepaid.set(isPrepaid);
	}
	
	public IntegerProperty isPrepaidProperty() {
		return isPrepaid;
	}
	
	public boolean getIsBoilerNoWheels() {
		if (isBoilerNoWheels.get() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setIsBoilerNoWheels(int isBoilerNoWheels) {
		this.isBoilerNoWheels.set(isBoilerNoWheels);
	}
	
	public IntegerProperty isBoilerNoWheelsProperty() {
		return isBoilerNoWheels;
	}
	
	public boolean getIsBoilerPart() {
		if (isBoilerPart.get() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setIsBoilerPart(int isBoilerPart) {
		this.isBoilerPart.set(isBoilerPart);
	}
	
	public IntegerProperty isBoilerPartProperty() {
		return isBoilerPart;
	}
	
	public boolean getIsAirRideVerified() {
		if (isAirRideVerified.get() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setIsAirRideVerified(int isAirRideVerified) {
		this.isAirRideVerified.set(isAirRideVerified);
	}
	
	public IntegerProperty isAirRideVerifiedProperty() {
		return isAirRideVerified;
	}
	
	public boolean getIsTarped() {
		if (isTarped.get() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setIsTarped(int isTarped) {
		this.isTarped.set(isTarped);
	}
	
	public IntegerProperty isTarpedProperty() {
		return isTarped;
	}
	
	public String getNotes() {
		return notes.get();
	}
	
	public void setNotes(String notes) {
		this.notes.set(notes);
	}
	
	public StringProperty notesProperty() {
		return notes;
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
	
	public String getShipToName() {
		return shipToName.get();
	}
	
	public void setShipToName(String shipToName) {
		this.shipToName.set(shipToName);
	}
	
	public StringProperty shipToNameProperty() {
		return shipToName;
	}
	
	public String getOtherPayment() {
		return otherPayment.get();
	}
	
	public void setOtherPayment(String otherPayment) {
		this.otherPayment.set(otherPayment);
	}
	
	public StringProperty otherPaymentProperty() {
		return otherPayment;
	}
	
	public boolean getIsAtmospheric() {
		return isAtmospheric.get();
	}
	
	public void setIsAtmospheric(boolean isAtmospheric) {
		this.isAtmospheric.set(isAtmospheric);
	}
	
	public BooleanProperty isAtmosphericProperty() {
		return isAtmospheric;
	}
	
	public Address getShipToAddress() {
		return shipToAddress;
	}
	
	public void setShipToAddress(Address address) {
		this.shipToAddress = address;
	}
	
	public ObservableList<Boiler> getBoilerData() {
		return boilerData;
	}

	public ObservableList<Pallet> getPalletData() {
		return palletData;
	}
}
