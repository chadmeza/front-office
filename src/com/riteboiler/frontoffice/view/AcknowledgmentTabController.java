package com.riteboiler.frontoffice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Acknowledgment;
import com.riteboiler.frontoffice.model.Product;
import com.riteboiler.frontoffice.util.AcknowledgmentPDF;

/**
 * This class is the controller for 
 * AcknowledgmentTab. It connects the data
 * to the views, and handles user events.
 * @author Chad Meza
 */
public class AcknowledgmentTabController {
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button savePDFButton;
	@FXML
	private Label aNumberLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label soldToNameLabel;
	@FXML
	private Label soldToStreetLabel;
	@FXML
	private Label soldToComboLabel;
	@FXML
	private Label shipToNameLabel;
	@FXML
	private Label shipToStreetLabel;
	@FXML
	private Label shipToComboLabel;
	@FXML
	private Label notifyLabel;
	@FXML
	private Label tagLabel;
	@FXML
	private Label dateOrderReceivedLabel;
	@FXML
	private Label customerOrderNumberLabel;
	@FXML
	private Label approxShipDateLabel;
	@FXML
	private Label shippingCostLabel;
	@FXML
	private Label fobLabel;
	@FXML
	private Label shipViaLabel;
	@FXML
	private Label documentsLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private TableView<Acknowledgment> acknowledgmentTable;
	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<Acknowledgment, String> ackIdColumn;
	@FXML
	private TableColumn<Acknowledgment, String> ackSoldToColumn;
	@FXML
	private TableColumn<Acknowledgment, String> ackShipToColumn;
	@FXML
	private TableColumn<Product, Number> prodQuantityColumn;
	@FXML
	private TableColumn<Product, String> prodDescriptionColumn;
	
	private MainApp main;
	private Acknowledgment selectedAcknowledgment;
	
	/**
	 * This constructor is empty,
	 * and does nothing.
	 */
	public AcknowledgmentTabController() {
		
	}
	
	/**
	 * Associates the table view columns to the appropriate
	 * data properties, and sets the table view listener. 
	 * This is called automatically.
	 */
	@FXML
	private void initialize() {
		ackIdColumn.setCellValueFactory(cellData -> cellData.getValue().aNumberProperty());
		ackSoldToColumn.setCellValueFactory(cellData -> cellData.getValue().soldToNameProperty());
		ackShipToColumn.setCellValueFactory(cellData -> cellData.getValue().shipToNameProperty());
		
		ackIdColumn.setStyle( "-fx-alignment: CENTER;");
		ackSoldToColumn.setStyle( "-fx-alignment: CENTER;");
		ackShipToColumn.setStyle( "-fx-alignment: CENTER;");
		
		acknowledgmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showAcknowledgmentDetails(newValue));
	}
	
	/**
	 * Sets the labels for the preview section.
	 * If the passed in acknowledgment is "null", there is no 
	 * item selected, and the labels are set to be empty.
	 * @param acknowledgment Acknowledgment that is currently selected
	 */
	private void showAcknowledgmentDetails(Acknowledgment acknowledgment) {
		selectedAcknowledgment = acknowledgment;
		
		if (acknowledgment != null) {
			editButton.setDisable(false);
			deleteButton.setDisable(false);
			savePDFButton.setDisable(false);
			
			productTable.setItems(acknowledgment.getProductData());
			
			prodQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
			prodDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
			
			prodQuantityColumn.setStyle( "-fx-alignment: CENTER;");
			prodDescriptionColumn.setStyle( "-fx-alignment: CENTER;");
			
			aNumberLabel.setText(acknowledgment.getANumber());
			dateLabel.setText(acknowledgment.getDate());
			notifyLabel.setText(acknowledgment.getNotify());
			tagLabel.setText(acknowledgment.getTag());
			dateOrderReceivedLabel.setText(acknowledgment.getDateOrderReceived());
			customerOrderNumberLabel.setText(acknowledgment.getCustomerOrderNumber());
			approxShipDateLabel.setText(acknowledgment.getApproxShipDate());
			shippingCostLabel.setText(acknowledgment.getShippingCost());
			fobLabel.setText(acknowledgment.getFob());
			shipViaLabel.setText(acknowledgment.getShipVia());
			documentsLabel.setText(acknowledgment.getDocuments());
			priceLabel.setText(acknowledgment.getPrice());
			soldToNameLabel.setText("");
			soldToStreetLabel.setText("");
			soldToComboLabel.setText("");
			shipToNameLabel.setText("");
			shipToStreetLabel.setText("");
			shipToComboLabel.setText("");
			
			if (acknowledgment.getSoldToAddress() != null) {
				soldToNameLabel.setText(acknowledgment.getSoldToAddress().getName());
				soldToStreetLabel.setText(acknowledgment.getSoldToAddress().getStreet1());
				soldToComboLabel.setText(acknowledgment.getSoldToAddress().getCity() + ", " + 
											acknowledgment.getSoldToAddress().getState() + " " +
											acknowledgment.getSoldToAddress().getZip() + " " +
											acknowledgment.getSoldToAddress().getCountry());
			}
			
			if (acknowledgment.getShipToAddress() != null) {
				shipToNameLabel.setText(acknowledgment.getShipToAddress().getName());
				shipToStreetLabel.setText(acknowledgment.getShipToAddress().getStreet1());
				shipToComboLabel.setText(acknowledgment.getShipToAddress().getCity() + ", " + 
											acknowledgment.getShipToAddress().getState() + " " +
											acknowledgment.getShipToAddress().getZip() + " " +
											acknowledgment.getShipToAddress().getCountry());
			}
			
		} else {
			productTable.setItems(null);
			aNumberLabel.setText("");
			dateLabel.setText("");
			soldToNameLabel.setText("");
			soldToStreetLabel.setText("");
			soldToComboLabel.setText("");
			shipToNameLabel.setText("");
			shipToStreetLabel.setText("");
			shipToComboLabel.setText("");
			notifyLabel.setText("");
			tagLabel.setText("");
			dateOrderReceivedLabel.setText("");
			customerOrderNumberLabel.setText("");
			approxShipDateLabel.setText("");
			shippingCostLabel.setText("");
			fobLabel.setText("");
			shipViaLabel.setText("");
			documentsLabel.setText("");
			priceLabel.setText("");
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
		acknowledgmentTable.setItems(main.getAcknowledgmentData());
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Edit" button. The AcknowledgmentEdit
	 * dialog is started up with the selected acknowledgment,
	 * to edit the data.
	 */
	@FXML
	private void handleEditAcknowledgment() {
		if (selectedAcknowledgment != null) {
			boolean okClicked = main.showAcknowledgmentEditDialog(selectedAcknowledgment);
		} else {
			//showErrorAlert();
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Delete" button. The selected 
	 * Acknowledgment is deleted from the database,
	 * and the lists used for the table views are
	 * refreshed.
	 */
	@FXML
	private void handleDeleteAcknowledgment() {
		if (selectedAcknowledgment != null) {
			main.getDbHelper().deleteAcknowledgment(selectedAcknowledgment);
			main.getAcknowledgmentData().clear();
			main.getAcknowledgmentData().addAll(main.getDbHelper().retrieveAcknowledgmentList());	
		} else {
			
		}
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "New Acknowledgment" button. 
	 * It creates a new acknowledgment. The AcknowledgmentEdit
	 * dialog is started up with this new acknowledgment, 
	 * to edit the data.
	 */
	@FXML
	private void handleNewAcknowledgment() {
		Acknowledgment tempAcknowledgment = new Acknowledgment();
		main.getDbHelper().insertAcknowledgment(tempAcknowledgment);
		tempAcknowledgment = main.getDbHelper().getLastAcknowledgment();
		boolean okClicked = main.showAcknowledgmentEditDialog(tempAcknowledgment);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Save to PDF" button. 
	 * It saves the acknowledgment data to a PDF file
	 * ready for print.
	 */
	@FXML
	private void handleSaveToPDF() {
		if (selectedAcknowledgment != null) {
			AcknowledgmentPDF pdfHelper = new AcknowledgmentPDF(selectedAcknowledgment);
			pdfHelper.setMain(main);
			pdfHelper.savePDF();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
		    alert.setTitle("PDF Saved");
		    alert.setHeaderText("File successfully saved");
		    alert.setContentText("You can view your file at:\n" + 
		    					main.getPreferenceList().get(0).getAckLocation() + 
		    					"\\A" + selectedAcknowledgment.getId() + " Acknowledgment.pdf");

		    alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("There was an error!");
		    alert.setHeaderText("PDF could not be saved");
		    alert.setContentText("Please select an acknowledgment and try again.");

		    alert.showAndWait();
		}
	}
}
