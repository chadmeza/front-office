package com.riteboiler.frontoffice.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class defines the Pallet model.
 * It contains getters and setters for fields
 * and properties.
 * @author Chad Meza
 */
public class Pallet {
	private final IntegerProperty id;
	private final IntegerProperty billOfLadingId;
	private final IntegerProperty quantity;
	private final StringProperty description;
	private final StringProperty weight;
	private final StringProperty dimensions;
	
	/**
	 * This is a parameterless constructor
	 * which runs another constructor, 
	 * passing in "null" for all the parameters.
	 * This is called when creating a new Pallet.
	 */
	public Pallet() {
		this(0, 0, 0, null, null, null);
	}
	
	public Pallet(int id, int billOfLadingId, int quantity, String description, String weight, String dimensions) {
		this.id = new SimpleIntegerProperty(id);
		this.billOfLadingId = new SimpleIntegerProperty(billOfLadingId);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.description = new SimpleStringProperty(description);
		this.weight = new SimpleStringProperty(weight);
		this.dimensions = new SimpleStringProperty(dimensions);
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
	
	public int getBillOfLadingId() {
		return billOfLadingId.get();
	}
	
	public void setBillOfLadingId(int billOfLadingId) {
		this.billOfLadingId.set(billOfLadingId);
	}
	
	public IntegerProperty billOfLadingIdProperty() {
		return billOfLadingId;
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
	
	public String getWeight() {
		return weight.get();
	}
	
	public void setWeight(String weight) {
		this.weight.set(weight);
	}
	
	public StringProperty weightProperty() {
		return weight;
	}
	
	public String getDimensions() {
		return dimensions.get();
	}
	
	public void setDimensions(String dimensions) {
		this.dimensions.set(dimensions);
	}
	
	public StringProperty dimensionsProperty() {
		return dimensions;
	}
}
