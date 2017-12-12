package com.riteboiler.frontoffice.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Address;

/**
 * This class is the controller for the 
 * AddAddresses stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class AddAddressesController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField zipField;
	@FXML
	private TextField countryField;
	
	boolean okClicked = false;
	
	private Stage addAddressStage;
	private MainApp main;
	private Address passedInAddress;
	
	/**
	 * This is automatically called.
	 */
	@FXML
	private void initialize() {
		
	}
	
	/**
	 * Setter for the reference to the AddAddresses dialog stage.
	 * @param addAddressStage Instance of addAddressStage Stage
	 */
	public void setAddAddressStage(Stage addAddressStage) {
		this.addAddressStage = addAddressStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
	}
	
	/**
	 * Setter for reference to the selected address to edit.
	 * It also inserts the address into the fields for editing.
	 * @param passedInAddress Address selected to edit
	 */
	public void setAddress(Address passedInAddress) {
		this.passedInAddress = passedInAddress;
		
		nameField.setText(passedInAddress.getName());
		streetField.setText(passedInAddress.getStreet1());
		cityField.setText(passedInAddress.getCity());
		stateField.setText(passedInAddress.getState());
		zipField.setText(passedInAddress.getZip());
		countryField.setText(passedInAddress.getCountry());
	}
	
	/**
	 * Checks whether the "OK" button has been clicked.
	 * @return True if "OK" is clicked, False otherwise
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Save" button. The address is
	 * validated, and then inserted into the database.
	 * Once complete, the stage is closed, and the 
	 * user returns to the previous stage.
	 */
	@FXML
	private void handleSave() {
		if (passedInAddress != null) {
			passedInAddress.setName(nameField.getText());
			passedInAddress.setStreet1(streetField.getText());
			passedInAddress.setCity(cityField.getText());
			passedInAddress.setState(stateField.getText());
			passedInAddress.setZip(zipField.getText());
			passedInAddress.setCountry(countryField.getText());
			
			main.getDbHelper().updateAddress(passedInAddress);
			
			okClicked = true;
			addAddressStage.close();
		} else {
			if (isValidInput()) {
				Address address = new Address();
				address.setName(nameField.getText());
				address.setStreet1(streetField.getText());
				address.setCity(cityField.getText());
				address.setState(stateField.getText());
				address.setZip(zipField.getText());
				address.setCountry(countryField.getText());
				
				main.getDbHelper().insertAddress(address);
				
				okClicked = true;
				addAddressStage.close();
			}
		}
	}

	/**
	 * Validates input received by the user.
	 * @return True
	 */
	private boolean isValidInput() {
		if (stateField.getText().length() == 2) {
			
			return true;
		} 
		
		return false;
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Cancel" button. The stage is
	 * closed, and the user returns to the previous stage.
	 */
	@FXML
	private void handleCancel() {
		addAddressStage.close();
	}
}
