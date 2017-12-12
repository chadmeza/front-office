package com.riteboiler.frontoffice.view;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.BillOfLading;
import com.riteboiler.frontoffice.model.Boiler;
import com.riteboiler.frontoffice.model.Pallet;
import com.riteboiler.frontoffice.util.BillOfLadingPDF;

/**
 * This class is the controller for 
 * BillOfLadingTab. It connects the data
 * to the views, and handles user events.
 * @author Chad Meza
 */
public class BillOfLadingTabController {
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button savePDFButton;
	@FXML
	private Label idLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label shipToNameLabel;
	@FXML
	private Label shipToStreetLabel;
	@FXML
	private Label shipToComboLabel;
	@FXML
	private Label contactLabel;
	@FXML
	private Label carrierLabel;
	@FXML
	private Label agentNumberLabel;
	@FXML
	private Label notesLabel;
	@FXML
	private Label otherPaymentLabel;
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
	private TableView<BillOfLading> billOfLadingTable;
	@FXML
	private TableView<Boiler> boilerTable;
	@FXML
	private TableView<Pallet> palletTable;
	@FXML
	private TableColumn<BillOfLading, String> billIdColumn;
	@FXML
	private TableColumn<BillOfLading, String> billShipToColumn;
	@FXML
	private TableColumn<BillOfLading, String> billCarrierColumn;
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
	
	private MainApp main;
	private BillOfLading selectedBillOfLading;
	
	/**
	 * This constructor is empty,
	 * and does nothing.
	 */
	public BillOfLadingTabController() {
		
	}
	
	/**
	 * Associates the table view columns to the appropriate
	 * data properties, and sets the table view listener. 
	 * This is called automatically.
	 */
	@FXML
	private void initialize() {
		billIdColumn.setCellValueFactory(cellData -> cellData.getValue().acknowledgmentIdProperty());
		billShipToColumn.setCellValueFactory(cellData -> cellData.getValue().shipToNameProperty());
		billCarrierColumn.setCellValueFactory(cellData -> cellData.getValue().carrierProperty());
		
		billIdColumn.setStyle( "-fx-alignment: CENTER;");
		billShipToColumn.setStyle( "-fx-alignment: CENTER;");
		billCarrierColumn.setStyle( "-fx-alignment: CENTER;");
		
		billOfLadingTable.getSelectionModel().selectedItemProperty()
							.addListener((observable, oldValue, newValue) -> showBillOfLadingDetails(newValue));
	}
	
	/**
	 * Sets the labels for the preview section.
	 * If the passed in bill of lading is "null", there is no 
	 * item selected, and the labels are set to be empty.
	 * @param billOfLading BillOfLading that is currently selected
	 */
	private void showBillOfLadingDetails(BillOfLading billOfLading) {
		selectedBillOfLading = billOfLading;
		
		if (billOfLading != null) {
			editButton.setDisable(false);
			deleteButton.setDisable(false);
			savePDFButton.setDisable(false);
			
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
			
			idLabel.setText(billOfLading.getAcknowledgmentId());
			dateLabel.setText(billOfLading.getDate());
			shipToNameLabel.setText("");
			shipToStreetLabel.setText("");
			shipToComboLabel.setText("");
			
			if (billOfLading.getShipToAddress() != null) {
				shipToNameLabel.setText(billOfLading.getShipToAddress().getName());
				shipToStreetLabel.setText(billOfLading.getShipToAddress().getStreet1());
				shipToComboLabel.setText(billOfLading.getShipToAddress().getCity() + ", " + 
						billOfLading.getShipToAddress().getState() + " " +
						billOfLading.getShipToAddress().getZip() + " " +
						billOfLading.getShipToAddress().getCountry());
			}
			
			contactLabel.setText(billOfLading.getContact());
			carrierLabel.setText(billOfLading.getCarrier());
			agentNumberLabel.setText(billOfLading.getAgentNumber());
			notesLabel.setText(billOfLading.getNotes());
			isBoilerNoWheelsCheckBox.setSelected(billOfLading.getIsBoilerNoWheels()); 
			isBoilerPartCheckBox.setSelected(billOfLading.getIsBoilerPart()); 
			isAirRideVerifiedCheckBox.setSelected(billOfLading.getIsAirRideVerified()); 
			isTarpedCheckBox.setSelected(billOfLading.getIsTarped()); 
			isPrepaidCheckBox.setSelected(billOfLading.getIsPrepaid()); 
			isAtmosphericCheckBox.setSelected(billOfLading.getIsAtmospheric()); 
			
			if (billOfLading.getOtherPayment() != null) {
				otherPaymentLabel.setText(billOfLading.getOtherPayment());
			}
		} else {
			idLabel.setText("");
			dateLabel.setText("");
			shipToNameLabel.setText("");
			shipToStreetLabel.setText("");
			shipToComboLabel.setText("");
			contactLabel.setText("");
			carrierLabel.setText("");
			agentNumberLabel.setText("");
			notesLabel.setText("");
			isBoilerNoWheelsCheckBox.setSelected(false);
			isBoilerPartCheckBox.setSelected(false);
			isAirRideVerifiedCheckBox.setSelected(false);
			isTarpedCheckBox.setSelected(false);
			isPrepaidCheckBox.setSelected(false);
			isAtmosphericCheckBox.setSelected(false);
			otherPaymentLabel.setText("");
		}
	}
	
	/**
	 * Setter for reference to main application.
	 * This also sets the table view items to the
	 * list which is referenced in the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		billOfLadingTable.setItems(main.getBillOfLadingData());
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Edit" button. The BillOfLadingTabEdit
	 * dialog is started up with the selected bill of lading,
	 * to edit the data.
	 */
	@FXML
	private void handleEditBillOfLading() {
		if (selectedBillOfLading != null) {
			boolean okClicked = main.showBillOfLadingEditDialog(selectedBillOfLading);
		} else {
			//showErrorAlert();
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Delete" button. The selected 
	 * bill of lading is deleted from the database,
	 * and the lists used for the table views are
	 * refreshed.
	 */
	@FXML
	private void handleDeleteBillOfLading() {
		if (selectedBillOfLading != null) {
			main.getDbHelper().deleteBillOfLading(selectedBillOfLading);
			main.getBillOfLadingData().clear();
			main.getBillOfLadingData().addAll(main.getDbHelper().retrieveBillOfLadingList());	
		} else {
			
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "New Bill Of Lading" button. 
	 * It creates a new bill of lading. The BillOfLadingTabEdit
	 * dialog is started up with this new bill of lading, 
	 * to edit the data.
	 */
	@FXML
	private void handleNewBillOfLading() {
		BillOfLading tempBillOfLading = new BillOfLading();
		main.getDbHelper().insertBillOfLading(tempBillOfLading);
		tempBillOfLading = main.getDbHelper().getLastBillOfLading();
		boolean okClicked = main.showBillOfLadingEditDialog(tempBillOfLading);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Save to PDF" button. 
	 * It saves the bill of lading data to a PDF file
	 * ready for print.
	 */
	@FXML
	private void handleSaveToPDF() {
		if (selectedBillOfLading != null) {
			BillOfLadingPDF pdfHelper = new BillOfLadingPDF(selectedBillOfLading);
			pdfHelper.setMain(main);
			
			try {
				pdfHelper.savePDF();
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
			    alert.setTitle("PDF Saved");
			    alert.setHeaderText("File successfully saved");
			    alert.setContentText("You can view your file at:\n" + 
			    					main.getPreferenceList().get(0).getBillLocation() + 
			    					"\\A" + selectedBillOfLading.getAcknowledgmentId() + " Bill of Lading.pdf");

			    alert.showAndWait();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("There was an error!");
		    alert.setHeaderText("PDF could not be saved");
		    alert.setContentText("Please select a bill of lading, and make sure all the fields are complete - including A#, Date, and Carrier.");

		    alert.showAndWait();
		}
	}
}
