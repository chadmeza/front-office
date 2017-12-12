package com.riteboiler.frontoffice.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Preference;

/**
 * This class is the controller for the 
 * Preferences stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class PreferencesController {
	@FXML
	private TextField usernameField;
	@FXML
	private Label acknowledgmentLocationLabel;
	@FXML
	private Label billOfLadingLocationLabel;
	@FXML
	private Button acknowledgmentLocationButton;
	@FXML
	private Button billOfLadingLocationButton;
	
	boolean okClicked = false;
	
	private Stage preferencesStage;
	private MainApp main;
	private Preference preference = new Preference();
	
	/**
	 * This is called automatically.
	 */
	@FXML
	private void initialize() {
		
	}
	
	/**
	 * Setter for the reference to the Preferences dialog stage.
	 * @param preferencesStage Instance of preferencesStage Stage
	 */
	public void setPreferencesStage(Stage preferencesStage) {
		this.preferencesStage = preferencesStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		preference.setUsername(main.getPreferenceList().get(0).getUsername());
		preference.setAckLocation(main.getPreferenceList().get(0).getAckLocation());
		preference.setBillLocation(main.getPreferenceList().get(0).getBillLocation());
		
		usernameField.setText(preference.getUsername());
		acknowledgmentLocationLabel.setText(preference.getAckLocation());
		billOfLadingLocationLabel.setText(preference.getBillLocation());
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
	 * clicking the acknowledgment location button.
	 * Presents a dialog to allow the user to select
	 * a directory for saving acknowledgments.
	 */
	@FXML
	private void handleAckLocationButton() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select a location to save Acknowledgment PDFs");
		
		File selectedDirectory = directoryChooser.showDialog(preferencesStage);
		
		if (selectedDirectory != null) {
			preference.setAckLocation(selectedDirectory.getPath());
			acknowledgmentLocationLabel.setText(selectedDirectory.getPath());
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the bill of lading location button.
	 * Presents a dialog to allow the user to select
	 * a directory for saving bills of lading. 
	 */
	@FXML
	private void handleBillLocationButton() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select a location to save Bill of Lading PDFs");
		
		File selectedDirectory = directoryChooser.showDialog(preferencesStage);
		
		if (selectedDirectory != null) {
			preference.setBillLocation(selectedDirectory.getPath());
			billOfLadingLocationLabel.setText(selectedDirectory.getPath());
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "OK" button. All preferences listed
	 * are inserted into the XML file.
	 */
	@FXML
	private void handleOk() {
		if (isValidInput()) {
			preference.setUsername(usernameField.getText());
			preference.setAckLocation(acknowledgmentLocationLabel.getText());
			preference.setBillLocation(billOfLadingLocationLabel.getText());
			
			main.getPreferenceList().clear();
			main.getPreferenceList().add(preference);
			
			main.savePreferenceDataToFile(main.getXmlFile());
			
			okClicked = true;
			preferencesStage.close();
		}
	}

	/**
	 * Validates input received by the user.
	 * @return True
	 */
	private boolean isValidInput() {
		return true;
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Cancel" button. The stage is
	 * closed, and the user returns to the previous stage.
	 */
	@FXML
	private void handleCancel() {
		preferencesStage.close();
	}
}
