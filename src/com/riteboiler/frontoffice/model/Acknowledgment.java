package com.riteboiler.frontoffice.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class defines the Acknowledgment model.
 * It contains getters and setters for fields
 * and properties.
 * @author Chad Meza
 */
public class Acknowledgment {
	private final IntegerProperty id;
	private final StringProperty aNumber;
	private final StringProperty date;
	private final IntegerProperty soldTo;
	private final IntegerProperty shipTo;
	private final StringProperty notify;
	private final StringProperty tag;
	private final StringProperty dateOrderReceived;
	private final StringProperty customerOrderNumber;
	private final StringProperty approxShipDate;
	private final StringProperty shippingCost;
	private final StringProperty fob;
	private final StringProperty shipVia;
	private final StringProperty documents;
	private final StringProperty price;
	private final StringProperty username;
	private final StringProperty soldToName;
	private final StringProperty shipToName;
	private Address soldToAddress;
	private Address shipToAddress;
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	
	/**
	 * This is a parameterless constructor
	 * which runs another constructor, 
	 * passing in "null" for all the parameters.
	 * This is called when creating a new Acknowledgment.
	 */
	public Acknowledgment() {
		this(0, null, null, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}
	
	public Acknowledgment(int id, String aNumber, String date, int soldTo, int shipTo, String notify, String tag,
			String dateOrderReceived, String customerOrderNumber, String approxShipDate, String shippingCost, String fob, String shipVia,
			String documents, String price, String username, String soldToName, String shipToName) {
		this.id = new SimpleIntegerProperty(id);
		this.aNumber = new SimpleStringProperty(aNumber);
		this.date = new SimpleStringProperty(date);
		this.soldTo = new SimpleIntegerProperty(soldTo);
		this.shipTo = new SimpleIntegerProperty(shipTo);
		this.notify = new SimpleStringProperty(notify);
		this.tag = new SimpleStringProperty(tag);
		this.dateOrderReceived = new SimpleStringProperty(dateOrderReceived);
		this.customerOrderNumber = new SimpleStringProperty(customerOrderNumber);
		this.approxShipDate = new SimpleStringProperty(approxShipDate);
		this.shippingCost = new SimpleStringProperty(shippingCost);
		this.fob = new SimpleStringProperty(fob);
		this.shipVia = new SimpleStringProperty(shipVia);
		this.documents = new SimpleStringProperty(documents);
		this.price = new SimpleStringProperty(price);
		this.username = new SimpleStringProperty(username);
		this.soldToName = new SimpleStringProperty(soldToName);
		this.shipToName = new SimpleStringProperty(shipToName);
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
	
	public String getANumber() {
		return aNumber.get();
	}
	
	public void setANumber(String aNumber) {
		this.aNumber.set(aNumber);
	}
	
	public StringProperty aNumberProperty() {
		return aNumber;
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

	public int getSoldTo() {
		return soldTo.get();
	}
	
	public void setSoldTo(int soldTo) {
		this.soldTo.set(soldTo);
	}
	
	public IntegerProperty soldToProperty() {
		return soldTo;
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
	
	public String getNotify() {
		return notify.get();
	}
	
	public void setNotify(String notify) {
		this.notify.set(notify);
	}
	
	public StringProperty notifyProperty() {
		return notify;
	}
	
	public String getTag() {
		return tag.get();
	}
	
	public void setTag(String tag) {
		this.tag.set(tag);
	}
	
	public StringProperty tagProperty() {
		return tag;
	}
	
	public String getDateOrderReceived() {
		return dateOrderReceived.get();
	}
	
	public void setDateOrderReceived(String dateOrderReceived) {
		this.dateOrderReceived.set(dateOrderReceived);
	}
	
	public StringProperty dateOrderReceivedProperty() {
		return dateOrderReceived;
	}
	
	public String getCustomerOrderNumber() {
		return customerOrderNumber.get();
	}
	
	public void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber.set(customerOrderNumber);
	}
	
	public StringProperty customerOrderNumberProperty() {
		return customerOrderNumber;
	}
	
	public String getApproxShipDate() {
		return approxShipDate.get();
	}
	
	public void setApproxShipDate(String approxShipDate) {
		this.approxShipDate.set(approxShipDate);
	}
	
	public StringProperty approxShipDateProperty() {
		return approxShipDate;
	}
	
	public String getShippingCost() {
		return shippingCost.get();
	}
	
	public void setShippingCost(String shippingCost) {
		this.shippingCost.set(shippingCost);
	}
	
	public StringProperty shippingCostProperty() {
		return shippingCost;
	}
	
	public String getFob() {
		return fob.get();
	}
	
	public void setFob(String fob) {
		this.fob.set(fob);
	}
	
	public StringProperty fobProperty() {
		return fob;
	}
	
	public String getShipVia() {
		return shipVia.get();
	}
	
	public void setShipVia(String shipVia) {
		this.shipVia.set(shipVia);
	}
	
	public StringProperty shipViaProperty() {
		return shipVia;
	}
	
	public String getDocuments() {
		return documents.get();
	}
	
	public void setDocuments(String documents) {
		this.documents.set(documents);
	}
	
	public StringProperty documentsProperty() {
		return documents;
	}
	
	public String getPrice() {
		return price.get();
	}
	
	public void setPrice(String price) {
		this.price.set(price);
	}
	
	public StringProperty priceProperty() {
		return price;
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
	
	public String getSoldToName() {
		return soldToName.get();
	}
	
	public void setSoldToName(String soldToName) {
		this.soldToName.set(soldToName);
	}
	
	public StringProperty soldToNameProperty() {
		return soldToName;
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

	public Address getSoldToAddress() {
		return soldToAddress;
	}
	
	public void setSoldToAddress(Address address) {
		this.soldToAddress = address;
	}

	public Address getShipToAddress() {
		return shipToAddress;
	}
	
	public void setShipToAddress(Address address) {
		this.shipToAddress = address;
	}

	public ObservableList<Product> getProductData() {
		return productData;
	}
}
