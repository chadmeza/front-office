package com.riteboiler.frontoffice.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Acknowledgment;
import com.riteboiler.frontoffice.model.BillOfLading;
import com.riteboiler.frontoffice.model.Boiler;
import com.riteboiler.frontoffice.model.Pallet;
import com.riteboiler.frontoffice.util.DateUtil;

/**
 * This class is the controller for the 
 * BillOfLadingEdit stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class BillOfLadingEditController {
//	@FXML
//	private ChoiceBox<String> idChoiceBox;
	@FXML
	private Label shipToNameLabel;
	@FXML
	private Label shipToStreetLabel;
	@FXML
	private Label shipToComboLabel;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField aNumberField;
	@FXML
	private TextField contactField;
	@FXML
	private TextField carrierField;
	@FXML
	private TextField agentNumberField;
	@FXML
	private TextField otherPaymentField;
	@FXML
	private TextArea notesField;
	@FXML
	private CheckBox isBoilerNoWheelsCheckBox;
	@FXML
	private CheckBox isBoilerPartCheckBox;
	@FXML
	private CheckBox isAirRideVerifiedCheckBox;
	@FXML
	private CheckBox isTarpedCheckBox;
	@FXML
	private CheckBox isPrepaidCheckBox;
	@FXML
	private CheckBox isAtmosphericCheckBox;
	@FXML
	private TableView<Boiler> boilerTable;
	@FXML
	private TableView<Pallet> palletTable;
	@FXML
	private TableColumn<Boiler, Number> boilerQuantityColumn;
	@FXML
	private TableColumn<Boiler, String> boilerModelColumn;
	@FXML
	private TableColumn<Boiler, String> boilerSerialColumn;
	@FXML
	private TableColumn<Boiler, String> boilerWeightColumn;
	@FXML
	private TableColumn<Boiler, String> boilerDimensionsColumn;
	@FXML
	private TableColumn<Pallet, Number> palletQuantityColumn;
	@FXML
	private TableColumn<Pallet, String> palletDescriptionColumn;
	@FXML
	private TableColumn<Pallet, String> palletWeightColumn;
	@FXML
	private TableColumn<Pallet, String> palletDimensionsColumn;
	
	boolean okClicked = false;
	boolean isNew = false;
	
	private String selectedAcknowledgmentId;
	
	private Stage dialogStage;
	private MainApp main;
	private BillOfLading billOfLading;
	
	/**
	 * This is automatically called.
	 */
	@FXML
	private void initialize() {
//		idChoiceBox.getItems().clear();
		notesField.setWrapText(true);
	}
	
	/**
	 * Sets the selected acknowledgment ID. When a new ID is
	 * selected, then the ship to address associated with the
	 * acknowledgment is brought over to the bill of lading.
	 * @param number Acknowledgment ID Number that is currently selected
	 */
	private void setAcknowledgmentId(String id) {
//		selectedAcknowledgmentId = idChoiceBox.getItems().get((int) number);
//		
//		billOfLading.setAcknowledgmentId(selectedAcknowledgmentId);
//		billOfLading.setShipTo(main.getAcknowledgmentData().get((int) number).getShipTo());
//		billOfLading.setShipToName(main.getAcknowledgmentData().get((int) number).getShipToName());
//		billOfLading.setShipToAddress(main.getAcknowledgmentData().get((int) number).getShipToAddress());
//		
//		setBillOfLading(billOfLading);
		
		selectedAcknowledgmentId = id;
		Acknowledgment acknowledgment = main.getDbHelper().loadAcknowledgment(selectedAcknowledgmentId);
		
		billOfLading.setAcknowledgmentId(selectedAcknowledgmentId);
		billOfLading.setShipTo(acknowledgment.getShipTo());
		billOfLading.setShipToName(acknowledgment.getShipToName());
		billOfLading.setShipToAddress(acknowledgment.getShipToAddress());
		
		setBillOfLading(billOfLading);
	}
	
	/**
	 * Setter for the reference to the dialog stage.
	 * @param dialogStage Instance of dialogStage Stage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * It also adds the data for the acknowledgment ID
	 * choice box.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		
//		for (int i = 0; i < main.getAcknowledgmentData().size(); i++) {
//			idChoiceBox.getItems().add(main.getAcknowledgmentData().get(i).getANumber());
//		}
//		
//		idChoiceBox.getSelectionModel().selectedIndexProperty()
//					.addListener((observable, oldValue, newValue) -> setAcknowledgmentId(newValue));
		
		aNumberField.textProperty().addListener((observable, oldValue, newValue) -> setAcknowledgmentId(newValue));
	}
	
	/**
	 * Setter for the reference to the bill of lading
	 * to load into the BillOfLadingEdit dialog. The 
	 * fields are set to the property values of the 
	 * passed in bill of lading.
	 * @param billOfLading Bill Of Lading to edit
	 */
	public void setBillOfLading(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
		
		if (this.billOfLading.getId() == 0) {
//			isNew = true;
		} else {
//			idChoiceBox.setValue(billOfLading.getAcknowledgmentId());
			aNumberField.setText(billOfLading.getAcknowledgmentId());
		}
		
		if (billOfLading.getShipToAddress() != null) {
			shipToNameLabel.setText(billOfLading.getShipToAddress().getName());
			shipToStreetLabel.setText(billOfLading.getShipToAddress().getStreet1());
			shipToComboLabel.setText(billOfLading.getShipToAddress().getCity() + ", " + 
					billOfLading.getShipToAddress().getState() + " " +
					billOfLading.getShipToAddress().getZip() + " " +
					billOfLading.getShipToAddress().getCountry());
		} else {
			shipToNameLabel.setText("");
			shipToStreetLabel.setText("");
			shipToComboLabel.setText("");
		}
		
		if (billOfLading.getDate() != null) {
			dateField.setValue(DateUtil.formatToLocalDate(billOfLading.getDate()));
		}
		
		contactField.setText(billOfLading.getContact());
		carrierField.setText(billOfLading.getCarrier());
		agentNumberField.setText(billOfLading.getAgentNumber());
		notesField.setText(billOfLading.getNotes());
		isBoilerNoWheelsCheckBox.setSelected(billOfLading.getIsBoilerNoWheels()); 
		isBoilerPartCheckBox.setSelected(billOfLading.getIsBoilerPart()); 
		isAirRideVerifiedCheckBox.setSelected(billOfLading.getIsAirRideVerified()); 
		isTarpedCheckBox.setSelected(billOfLading.getIsTarped()); 
		isPrepaidCheckBox.setSelected(billOfLading.getIsPrepaid()); 
		isAtmosphericCheckBox.setSelected(billOfLading.getIsAtmospheric()); 
		
		if (billOfLading.getOtherPayment() != null) {
			otherPaymentField.setText(billOfLading.getOtherPayment());
		}

		boilerTable.setItems(billOfLading.getBoilerData());
		
		boilerQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
		boilerModelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
		boilerSerialColumn.setCellValueFactory(cellData -> cellData.getValue().serialProperty());
		boilerWeightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty());
		boilerDimensionsColumn.setCellValueFactory(cellData -> cellData.getValue().dimensionsProperty());
		
		boilerQuantityColumn.setStyle( "-fx-alignment: CENTER;");
		boilerModelColumn.setStyle( "-fx-alignment: CENTER;");
		boilerSerialColumn.setStyle( "-fx-alignment: CENTER;");
		boilerWeightColumn.setStyle( "-fx-alignment: CENTER;");
		boilerDimensionsColumn.setStyle( "-fx-alignment: CENTER;");
		
		palletTable.setItems(billOfLading.getPalletData());
		
		palletQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
		palletDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		palletWeightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty());
		palletDimensionsColumn.setCellValueFactory(cellData -> cellData.getValue().dimensionsProperty());
		
		palletQuantityColumn.setStyle( "-fx-alignment: CENTER;");
		palletDescriptionColumn.setStyle( "-fx-alignment: CENTER;");
		palletWeightColumn.setStyle( "-fx-alignment: CENTER;");
		palletDimensionsColumn.setStyle( "-fx-alignment: CENTER;");
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
	 * clicking the "OK" button. If the bill of lading 
	 * is new, then it is inserted into the database.
	 * If the bill of lading is not new, its data is 
	 * updated in the database. Then the stage is closed, 
	 * and the user returns to the main stage.
	 */
	@FXML
	private void handleOk() {
		if (isValidInput()) {
			saveCurrentBillOfLading();
			
			if (isNew) {
				main.getDbHelper().insertBillOfLading(billOfLading);
				main.getBillOfLadingData().clear();
				main.getBillOfLadingData().addAll(main.getDbHelper().retrieveBillOfLadingList());
			} else {
				main.getDbHelper().updateBillOfLading(billOfLading);
				main.getBillOfLadingData().clear();
				main.getBillOfLadingData().addAll(main.getDbHelper().retrieveBillOfLadingList());	
			}
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	/**
	 * Saves the currently filled in fields to the bill of lading.
	 */
	private void saveCurrentBillOfLading() {
		billOfLading.setAcknowledgmentId(selectedAcknowledgmentId);
		billOfLading.setDate(DateUtil.formatToString(dateField.getValue()));
		billOfLading.setContact(contactField.getText());
		billOfLading.setCarrier(carrierField.getText());
		billOfLading.setAgentNumber(agentNumberField.getText());
		billOfLading.setIsPrepaid(isPrepaidCheckBox.isSelected() == true ? 1 : 0);
		billOfLading.setIsBoilerNoWheels(isBoilerNoWheelsCheckBox.isSelected() == true ? 1 : 0);
		billOfLading.setIsBoilerPart(isBoilerPartCheckBox.isSelected() == true ? 1 : 0);
		billOfLading.setIsAirRideVerified(isAirRideVerifiedCheckBox.isSelected() == true ? 1 : 0);
		billOfLading.setIsTarped(isTarpedCheckBox.isSelected() == true ? 1 : 0);
		billOfLading.setNotes(notesField.getText());
		billOfLading.setUsername(main.getPreferenceList().get(0).getUsername());
		billOfLading.setOtherPayment(otherPaymentField.getText());
		billOfLading.setIsAtmospheric(isAtmosphericCheckBox.isSelected());
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Cancel" button. The stage is
	 * closed, and the user returns to the main stage.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
		
		if (billOfLading.getAcknowledgmentId() == null) {
			main.getDbHelper().deleteBillOfLading(billOfLading);
		}
		
		main.getBillOfLadingData().clear();
		main.getBillOfLadingData().addAll(main.getDbHelper().retrieveBillOfLadingList());	
	}
	
	/**
	 * Responds to the event triggered by the user
	 * editing the ship to address. The Addresses 
	 * dialog is started up with the selected 
	 * bill of lading, to edit the data.
	 */
	public boolean showAddresses(String role) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Addresses.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage addressStage = new Stage();
			addressStage.setTitle("Select " + role + " Address");
			addressStage.getIcons().add(new Image("file:resources/images/logo.png"));
			addressStage.initModality(Modality.WINDOW_MODAL);
			addressStage.initOwner(dialogStage);
			
			Scene scene = new Scene(layout);
			addressStage.setScene(scene);
			
			AddressesController controller = loader.getController();
			controller.setAddressStage(addressStage);
			controller.setRole(role);
			controller.setMain(main);
			controller.setBillOfLading(billOfLading);
			
			addressStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
			return false;
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Select" button for the ship to
	 * address. A new dialog is opened, and any changes
	 * to the bill of lading are loaded into this dialog.
	 */
	@FXML
	private void handleSelectShipTo() {
		boolean okClicked = showAddresses("SHIP TO");
		saveCurrentBillOfLading();
		setBillOfLading(billOfLading);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Edit" button for the boilers. 
	 * A new dialog is opened, and any changes
	 * to the bill of lading are loaded into this dialog.
	 */
	@FXML
	private void handleEditBoilers() {
		boolean okClicked = showBoilers();
		saveCurrentBillOfLading();
		setBillOfLading(billOfLading);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * editing the boilers. The Boilers dialog is 
	 * started up with the selected bill of lading, 
	 * to edit the data.
	 */
	public boolean showBoilers() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Boilers.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage boilerStage = new Stage();
			boilerStage.setTitle("Add / Edit Boilers");
			boilerStage.getIcons().add(new Image("file:resources/images/logo.png"));
			boilerStage.initModality(Modality.WINDOW_MODAL);
			boilerStage.initOwner(dialogStage);
			
			Scene scene = new Scene(layout);
			boilerStage.setScene(scene);
			
			BoilersController controller = loader.getController();
			controller.setBoilerStage(boilerStage);
			controller.setMain(main);
			controller.setBillOfLading(billOfLading);
			
			boilerStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
			return false;
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Edit" button for the pallets. 
	 * A new dialog is opened, and any changes
	 * to the bill of lading are loaded into this dialog.
	 */
	@FXML
	private void handleEditPallets() {
		boolean okClicked = showPallets();
		saveCurrentBillOfLading();
		setBillOfLading(billOfLading);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * editing the pallets. The Pallets dialog is 
	 * started up with the selected bill of lading, 
	 * to edit the data.
	 */
	public boolean showPallets() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Pallets.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage palletStage = new Stage();
			palletStage.setTitle("Add / Edit Pallets");
			palletStage.getIcons().add(new Image("file:resources/images/logo.png"));
			palletStage.initModality(Modality.WINDOW_MODAL);
			palletStage.initOwner(dialogStage);
			
			Scene scene = new Scene(layout);
			palletStage.setScene(scene);
			
			PalletsController controller = loader.getController();
			controller.setPalletStage(palletStage);
			controller.setMain(main);
			controller.setBillOfLading(billOfLading);
			
			palletStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (IOException e) {
			main.getDbHelper().insertError(e, main.getPreferenceList().get(0).getUsername());
			return false;
		}
	}
	
	/**
	 * Validates input received by the user.
	 * @return True
	 */
	private boolean isValidInput() {
		return true;
	}
}
