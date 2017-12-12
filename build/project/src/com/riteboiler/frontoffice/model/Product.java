package com.riteboiler.frontoffice.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class defines the Product model.
 * It contains getters and setters for fields
 * and properties.
 * @author Chad Meza
 */
public class Product {
	private final IntegerProperty id;
	private final IntegerProperty acknowledgmentId;
	private final IntegerProperty quantity;
	private final StringProperty description;
	
	/**
	 * This is a parameterless constructor
	 * which runs another constructor, 
	 * passing in "null" for all the parameters.
	 * This is called when creating a new Product.
	 */
	public Product() {
		this(0, 0, 0, null);
	}
	
	public Product(int id, int acknowledgmentId, int quantity, String description) {
		this.id = new SimpleIntegerProperty(id);
		this.acknowledgmentId = new SimpleIntegerProperty(acknowledgmentId);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.description = new SimpleStringProperty(description);
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
	
	public int getAcknowledgmentId() {
		return acknowledgmentId.get();
	}
	
	public void setAcknowledgmentId(int acknowledgmentId) {
		this.acknowledgmentId.set(acknowledgmentId);
	}
	
	public IntegerProperty acknowledgmentIdProperty() {
		return acknowledgmentId;
	}
	
	public int getQuantity() {
		return quantity.get();
	}
	
	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}
	
	public IntegerProperty quantityProperty() {
		return quantity;
	}
	
	public String getDescription() {
		return description.get();
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
}
