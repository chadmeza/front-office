package com.riteboiler.frontoffice.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;

/**
 * This class is the controller for the root
 * layout, RootLayout. It handles the main
 * menu and hosts the tab controllers.
 * @author Chad Meza
 */
public class RootLayoutController {
	private MainApp main;
	@FXML
	private AcknowledgmentTabController acknowledgmentTabController;
	@FXML
	private BillOfLadingTabController billOfLadingTabController;
	
	/**
	 * This constructor is empty,
	 * and does nothing.
	 */
	public RootLayoutController() {
		
	}
	
	/**
	 * This is called automatically.
	 */
	@FXML
	private void initialize() {
		
	}
	
	/**
	 * Setter for reference to main application.
	 * This also sets the reference to the main
	 * application for the tab controllers.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		acknowledgmentTabController.setMain(main);
		billOfLadingTabController.setMain(main);
	}
	
	@FXML
	private void handlePreferences() {
		boolean isOkClicked = showPreferences();
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking "Preferences". The Preferences dialog is 
	 * started up.
	 */
	private boolean showPreferences() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Preferences.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage preferencesStage = new Stage();
			preferencesStage.setTitle("Preferences");
			preferencesStage.getIcons().add(new Image("file:resources/images/logo.png"));
			preferencesStage.initModality(Modality.WINDOW_MODAL);
			preferencesStage.initOwner(main.getPrimaryStage());
			
			Scene scene = new Scene(layout);
			preferencesStage.setScene(scene);
			
			PreferencesController controller = loader.getController();
			controller.setPreferencesStage(preferencesStage);
			controller.setMain(main);
			
			preferencesStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
			return false;
		}
	}
	
	@FXML
	private void handleClose() {
		main.getPrimaryStage().close();
	}
	
	@FXML
	private void handleRefreshAllData() {
		main.refreshLists();
	}
	
	@FXML
	private void handleAbout() {
		showAbout();
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking "About". The About dialog is 
	 * started up.
	 */
	public void showAbout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/About.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage aboutStage = new Stage();
			aboutStage.setTitle("About Rite Boiler - Front Office");
			aboutStage.getIcons().add(new Image("file:resources/images/logo.png"));
			aboutStage.initModality(Modality.WINDOW_MODAL);
			aboutStage.initOwner(main.getPrimaryStage());
			
			Scene scene = new Scene(layout);
			aboutStage.setScene(scene);
			
			AboutController controller = loader.getController();
			controller.setAboutStage(aboutStage);
			controller.setMain(main);
			
			aboutStage.showAndWait();
			
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
		}
	}
}
