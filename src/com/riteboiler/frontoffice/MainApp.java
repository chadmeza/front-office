package com.riteboiler.frontoffice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.riteboiler.frontoffice.model.Acknowledgment;
import com.riteboiler.frontoffice.model.Address;
import com.riteboiler.frontoffice.model.BillOfLading;
import com.riteboiler.frontoffice.model.Preference;
import com.riteboiler.frontoffice.model.RiteDBHelper;
import com.riteboiler.frontoffice.util.PreferenceWrapper;
import com.riteboiler.frontoffice.view.AcknowledgmentEditController;
import com.riteboiler.frontoffice.view.BillOfLadingEditController;
import com.riteboiler.frontoffice.view.RootLayoutController;

/**
 * This class is the main application.
 * It manages the main Stages and Scenes.
 * It also sets up and refreshes the
 * main ObservableList objects. 
 * @author Chad Meza
 * @VERSION 1.0
 */
public class MainApp extends Application {

	private final String VERSION = "1.0";
	private final String XML_PREFERENCES = "Preferences.xml";
	
	private File xmlFile;
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private RiteDBHelper dbHelper;
	
	private ObservableList<Acknowledgment> acknowledgmentData = FXCollections.observableArrayList();
	private ObservableList<BillOfLading> billOfLadingData = FXCollections.observableArrayList();
	private ObservableList<Address> addressData = FXCollections.observableArrayList();
	
	private List<Preference> preferenceList = new ArrayList<Preference>();
	
	/**
	 * This class constructor instantiates the
	 * reference to the database wrapper so that
	 * the database is ready to be used.
	 */
	public MainApp() {
		dbHelper = new RiteDBHelper();
		xmlFile = new File(XML_PREFERENCES);
		loadPreferenceDataFromFile(xmlFile);
	}
	
	/**
	 * Clears the lists, and retrieves updated
	 * data from the database in a new thread.
	 */
	public void refreshLists() {
		acknowledgmentData.clear();
		billOfLadingData.clear();
		addressData.clear();
		acknowledgmentData.addAll(dbHelper.retrieveAcknowledgmentList());
		billOfLadingData.addAll(dbHelper.retrieveBillOfLadingList());
		addressData.addAll(dbHelper.retrieveAddressList());
	}
	
	/**
	 * This method starts up the application.
	 * It sets up the Stage and calls
	 * initRootLayout() to load the scene.
	 * @param primaryStage Stage for application.
	 */
	@Override
	public void start(Stage primaryStage) {
		refreshLists();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Rite Boiler - Front Office");
		
		this.primaryStage.getIcons().add(new Image("file:resources/images/logo.png"));
		
		initRootLayout();	
	}
	
	/**
	 * Creates the main scene, loads the FXML,
	 * and attaches the controller to the FXML.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			controller.setMain(this);
			
			primaryStage.show();
		} catch (IOException e) {
			dbHelper.insertError(e, preferenceList.get(0).getUsername());
		}
	}
	
	/**
	 * Creates a pop-up dialog when a user
	 * clicks "New Acknowledgment" or "Edit". 
	 * This method creates the stage, creates the scene, 
	 * loads the FXML, and attaches the controller.
	 * @param acknowledgment Acknowledgment to edit/create
	 * @return True if OK is clicked, False if not
	 */
	public boolean showAcknowledgmentEditDialog(Acknowledgment acknowledgment) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AcknowledgmentEdit.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Acknowledgment Details");
			dialogStage.getIcons().add(new Image("file:resources/images/logo.png"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(layout);
			dialogStage.setScene(scene);
			
			AcknowledgmentEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMain(this);
			controller.setAcknowledgment(acknowledgment);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			dbHelper.insertError(e, preferenceList.get(0).getUsername());
			return false;
		}
	}
	
	/**
	 * Creates a pop-up dialog when a user
	 * clicks "New Bill Of Lading" or "Edit". 
	 * This method creates the stage, creates the scene, 
	 * loads the FXML, and attaches the controller.
	 * @param billOfLading Bill Of Lading to edit/create
	 * @return True if OK is clicked, False if not
	 */
	public boolean showBillOfLadingEditDialog(BillOfLading billOfLading) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BillOfLadingEdit.fxml"));
		    
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Bill of Lading Details");
			dialogStage.getIcons().add(new Image("file:resources/images/logo.png"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(layout);
			dialogStage.setScene(scene);
			
			BillOfLadingEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMain(this);
			controller.setBillOfLading(billOfLading);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			dbHelper.insertError(e, preferenceList.get(0).getUsername());
			return false;
		}
	}
	
	/**
	 * Getter for the database wrapper.
	 * @return dbHelper
	 */
	public RiteDBHelper getDbHelper() {
		return dbHelper;
	}

	/**
	 * Getter for the acknowledgment list.
	 * @return acknowledgmentData
	 */
	public ObservableList<Acknowledgment> getAcknowledgmentData() {
		return acknowledgmentData;
	}
	
	/**
	 * Getter for the bill of lading list.
	 * @return billOfLadingData
	 */
	public ObservableList<BillOfLading> getBillOfLadingData() {
		return billOfLadingData;
	}
	
	/**
	 * Getter for the address list.
	 * @return addressData
	 */
	public ObservableList<Address> getAddressData() {
		return addressData;
	}
	
	/**
	 * Getter for main application Stage.
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Start-up method. If run, launches the
	 * start() method.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Getter for the application version.
	 * @return VERSION
	 */
	public String getVERSION() {
		return VERSION;
	}
	
	/**
	 * Loads preference data from the specified file. 
	 * The current preference data will be replaced.
	 * @param file XML file to load
	 */
	public void loadPreferenceDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(PreferenceWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        PreferenceWrapper wrapper = (PreferenceWrapper) um.unmarshal(file);

	        preferenceList.clear();
	        preferenceList.addAll(wrapper.getPreferences());
	    } catch (Exception e) { 
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load preferences");
	        alert.setContentText("Could not load preferences from file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	/**
	 * Saves the current preference data to the specified file.
	 * @param file XML file to be saved to
	 */
	public void savePreferenceDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(PreferenceWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        PreferenceWrapper wrapper = new PreferenceWrapper();
	        wrapper.setPreferences(preferenceList);

	        m.marshal(wrapper, file);
	    } catch (Exception e) { 
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save preferences");
	        alert.setContentText("Could not save preferences to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	/**
	 * Getter for the preferences list.
	 * @return preferenceList
	 */
	public List<Preference> getPreferenceList() {
		return preferenceList;
	}

	/**
	 * Getter for the preferences XML file path.
	 * @return XML_PREFERENCES
	 */
	public String getXML_PREFERENCES() {
		return XML_PREFERENCES;
	}
	
	/**
	 * Getter for the preferences XML file.
	 * @return xmlFile
	 */
	public File getXmlFile() {
		return xmlFile;
	}
}
