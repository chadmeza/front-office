package com.riteboiler.frontoffice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;

/**
 * This class is the controller for the 
 * About stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class AboutController {
	@FXML
	private Label versionLabel;
	
	private Stage aboutStage;
	private MainApp main;
	
	/**
	 * This is called automatically.
	 */
	@FXML
	private void initialize() {
		
	}
	
	/**
	 * Setter for the reference to the About dialog stage.
	 * @param aboutStage Instance of aboutStage Stage
	 */
	public void setAboutStage(Stage aboutStage) {
		this.aboutStage = aboutStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		versionLabel.setText(main.getVERSION());
	}
}
