package com.riteboiler.frontoffice.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Acknowledgment;
import com.riteboiler.frontoffice.model.Address;
import com.riteboiler.frontoffice.model.BillOfLading;

/**
 * This class is the controller for the 
 * Addresses stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class AddressesController {
	@FXML
	private TableView<Address> addressTable;
	@FXML
	private TableColumn<Address, String> addressNameColumn;
	@FXML
	private TableColumn<Address, String> addressStreetColumn;
	@FXML
	private TableColumn<Address, String> addressCityColumn;
	@FXML
	private TableColumn<Address, String> addressStateColumn;
	@FXML
	private TableColumn<Address, String> addressZipColumn;
	@FXML
	private TableColumn<Address, String> addressCountryColumn;
	
	boolean okClicked = false;
	
	private Address selectedAddress;
	
	private Stage addressStage;
	private String role;
	private MainApp main;
	private Acknowledgment acknowledgment;
	private BillOfLading billOfLading;
	private int calledBy;
	
	/**
	 * Associates the table view columns to the appropriate
	 * data properties, and sets the table view listener. 
	 * This is called automatically.
	 */
	@FXML
	private void initialize() {
		addressNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		addressStreetColumn.setCellValueFactory(cellData -> cellData.getValue().street1Property());
		addressCityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
		addressStateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
		addressZipColumn.setCellValueFactory(cellData -> cellData.getValue().zipProperty());
		addressCountryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
		
		addressNameColumn.setStyle( "-fx-alignment: CENTER;");
		addressStreetColumn.setStyle( "-fx-alignment: CENTER;");
		addressCityColumn.setStyle( "-fx-alignment: CENTER;");
		addressStateColumn.setStyle( "-fx-alignment: CENTER;");
		addressZipColumn.setStyle( "-fx-alignment: CENTER;");
		addressCountryColumn.setStyle( "-fx-alignment: CENTER;");
				
		addressTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setSelectedAddress(newValue));
	}
	
	/**
	 * Sets the address field to the currently selected
	 * address.
	 * @param address Address that is currently selected
	 */
	public void setSelectedAddress(Address address) {
		selectedAddress = address;
	}
	
	/**
	 * Setter for the reference to the Addresses dialog stage.
	 * @param addressStage Instance of addressStage Stage
	 */
	public void setAddressStage(Stage addressStage) {
		this.addressStage = addressStage;
	}
	
	/**
	 * Setter for the role field.
	 * @param role Role string to determine ship to or sold to
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		addressTable.setItems(main.getAddressData());
	}
	
	/**
	 * Setter for the reference to the acknowledgment
	 * to load into the dialog. 
	 * @param acknowledgment Acknowledgment to edit
	 */
	public void setAcknowledgment(Acknowledgment acknowledgment) {
		this.acknowledgment = acknowledgment;
		calledBy = 1;
	}
	
	/**
	 * Setter for the reference to the bill of lading
	 * to load into billOfLading Bill Of Lading to edit
	 */
	public void setBillOfLading(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
		calledBy = 2;
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
	 * clicking the "Select" button. The acknowledgment 
	 * is updated, and the dialog is closed.
	 */
	@FXML
	private void handleSelect() {
		switch (calledBy) {
		case 1:
			if (role == "SOLD TO") {
				acknowledgment.setSoldTo(selectedAddress.getId());
				acknowledgment.setSoldToName(selectedAddress.getName());
				acknowledgment.setSoldToAddress(selectedAddress);
			} else {
				acknowledgment.setShipTo(selectedAddress.getId());
				acknowledgment.setShipToName(selectedAddress.getName());
				acknowledgment.setShipToAddress(selectedAddress);
			}
			break;
		case 2:
			billOfLading.setShipTo(selectedAddress.getId());
			billOfLading.setShipToName(selectedAddress.getName());
			billOfLading.setShipToAddress(selectedAddress);
			break;
		default:
			break;
		}
		
		okClicked = true;
		addressStage.close();
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Cancel" button. The stage is
	 * closed, and the user returns to the previous stage.
	 */
	@FXML
	private void handleCancel() {
		addressStage.close();
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Add New Address" button. 
	 */
	@FXML
	private void handleAddAddress() {
		boolean okClicked = showAddAddresses();
		main.getAddressData().clear();
		main.getAddressData().addAll(main.getDbHelper().retrieveAddressList());	
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Edit" button. It passes the 
	 * selected address to the AddAddressesController
	 * to be edited.
	 */
	@FXML
	private void handleEditAddress() {
		if (selectedAddress != null) {
			boolean okClicked = showEditAddresses();
			main.getAddressData().clear();
			main.getAddressData().addAll(main.getDbHelper().retrieveAddressList());	
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * editing an address. The AddAddresses dialog is 
	 * started up with the selected address.
	 */
	public boolean showEditAddresses() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddAddresses.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage addAddressStage = new Stage();
			addAddressStage.setTitle("Edit New Address");
			addAddressStage.getIcons().add(new Image("file:resources/images/logo.png"));
			addAddressStage.initModality(Modality.WINDOW_MODAL);
			addAddressStage.initOwner(addressStage);
			
			Scene scene = new Scene(layout);
			addAddressStage.setScene(scene);
			
			AddAddressesController controller = loader.getController();
			controller.setAddAddressStage(addAddressStage);
			controller.setMain(main);
			controller.setAddress(selectedAddress);
			
			addAddressStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
			return false;
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * adding an address. The AddAddresses dialog is 
	 * started up.
	 */
	public boolean showAddAddresses() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddAddresses.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage addAddressStage = new Stage();
			addAddressStage.setTitle("Add New Address");
			addAddressStage.getIcons().add(new Image("file:resources/images/logo.png"));
			addAddressStage.initModality(Modality.WINDOW_MODAL);
			addAddressStage.initOwner(addressStage);
			
			Scene scene = new Scene(layout);
			addAddressStage.setScene(scene);
			
			AddAddressesController controller = loader.getController();
			controller.setAddAddressStage(addAddressStage);
			controller.setMain(main);
			
			addAddressStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
			return false;
		}
	}
}
