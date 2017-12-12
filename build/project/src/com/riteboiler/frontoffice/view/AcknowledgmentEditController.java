package com.riteboiler.frontoffice.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import com.riteboiler.frontoffice.model.Product;
import com.riteboiler.frontoffice.util.DateUtil;

/**
 * This class is the controller for the 
 * AcknowledgmentEdit stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class AcknowledgmentEditController {
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
	private DatePicker dateField;
	@FXML
	private TextField aNumberField;
	@FXML
	private TextField notifyField;
	@FXML
	private TextField tagField;
	@FXML
	private DatePicker dateOrderReceivedField;
	@FXML
	private TextField customerOrderNumberField;
	@FXML
	private DatePicker approxShipDateField;
	@FXML
	private TextField shippingCostField;
	@FXML
	private TextField fobField;
	@FXML
	private TextField shipViaField;
	@FXML
	private TextArea documentsField;
	@FXML
	private TextField priceField;
	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<Product, Number> prodQuantityColumn;
	@FXML
	private TableColumn<Product, String> prodDescriptionColumn;
	
	boolean okClicked = false;
	boolean isNew = false;
	
	private Stage dialogStage;
	private MainApp main;
	private Acknowledgment acknowledgment;
	
	/**
	 * This is automatically called.
	 */
	@FXML
	private void initialize() {
		
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
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
	}
	
	/**
	 * Setter for the reference to the acknowledgment
	 * to load into the AcknowledgmentEdit dialog. The 
	 * fields are set to the property values of the 
	 * passed in acknowledgment.
	 * @param acknowledgment Acknowledgment to edit
	 */
	public void setAcknowledgment(Acknowledgment acknowledgment) {
		this.acknowledgment = acknowledgment;
		
		if (this.acknowledgment.getANumber() == null) {
//			isNew = true;
			aNumberField.setText("0");
		} else {
			aNumberField.setText(acknowledgment.getANumber());
		}
		
		if (acknowledgment.getSoldToAddress() != null) {
			soldToNameLabel.setText(acknowledgment.getSoldToAddress().getName());
			soldToStreetLabel.setText(acknowledgment.getSoldToAddress().getStreet1());
			soldToComboLabel.setText(acknowledgment.getSoldToAddress().getCity() + ", " + 
										acknowledgment.getSoldToAddress().getState() + " " +
										acknowledgment.getSoldToAddress().getZip() + " " +
										acknowledgment.getSoldToAddress().getCountry());
		} else {
			soldToNameLabel.setText("");
			soldToStreetLabel.setText("");
			soldToComboLabel.setText("");
		}
		
		if (acknowledgment.getShipToAddress() != null) {
			shipToNameLabel.setText(acknowledgment.getShipToAddress().getName());
			shipToStreetLabel.setText(acknowledgment.getShipToAddress().getStreet1());
			shipToComboLabel.setText(acknowledgment.getShipToAddress().getCity() + ", " + 
										acknowledgment.getShipToAddress().getState() + " " +
										acknowledgment.getShipToAddress().getZip() + " " +
										acknowledgment.getShipToAddress().getCountry());
		} else {
			shipToNameLabel.setText("");
			shipToStreetLabel.setText("");
			shipToComboLabel.setText("");
		}
		
		if (acknowledgment.getDate() != null) {
			dateField.setValue(DateUtil.formatToLocalDate(acknowledgment.getDate()));
		}
		
		notifyField.setText(acknowledgment.getNotify());
		tagField.setText(acknowledgment.getTag());
		
		if (acknowledgment.getDateOrderReceived() != null) {
			dateOrderReceivedField.setValue(DateUtil.formatToLocalDate(acknowledgment.getDateOrderReceived()));
		}
		
		customerOrderNumberField.setText(acknowledgment.getCustomerOrderNumber());
		
		if (acknowledgment.getApproxShipDate() != null) {
			approxShipDateField.setValue(DateUtil.formatToLocalDate(acknowledgment.getApproxShipDate()));
		}
		
		shippingCostField.setText(acknowledgment.getShippingCost());
		fobField.setText(acknowledgment.getFob());
		shipViaField.setText(acknowledgment.getShipVia());
		documentsField.setText(acknowledgment.getDocuments());
		priceField.setText(acknowledgment.getPrice());
		
		productTable.setItems(acknowledgment.getProductData());
		
		prodQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
		prodDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		
		prodQuantityColumn.setStyle( "-fx-alignment: CENTER;");
		prodDescriptionColumn.setStyle( "-fx-alignment: CENTER;");
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
	 * clicking the "OK" button. If the acknowledgment 
	 * is new, then it is inserted into the database.
	 * If the acknowledgment is not new, its data is 
	 * updated in the database. Then the stage is closed, 
	 * and the user returns to the main stage.
	 */
	@FXML
	private void handleOk() {
		if (isValidInput()) {
			saveCurrentAcknowledgment();
			
			if (isNew) {
				main.getDbHelper().insertAcknowledgment(acknowledgment);
				main.getAcknowledgmentData().clear();
				main.getAcknowledgmentData().addAll(main.getDbHelper().retrieveAcknowledgmentList());
			} else {
				main.getDbHelper().updateAcknowledgment(acknowledgment);
				main.getAcknowledgmentData().clear();
				main.getAcknowledgmentData().addAll(main.getDbHelper().retrieveAcknowledgmentList());	
			}
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	/**
	 * Saves the currently filled in fields to the acknowledgment.
	 */
	private void saveCurrentAcknowledgment() {
		acknowledgment.setANumber(aNumberField.getText());
		acknowledgment.setDate(DateUtil.formatToString(dateField.getValue()));
		acknowledgment.setNotify(notifyField.getText());
		acknowledgment.setTag(tagField.getText());
		acknowledgment.setDateOrderReceived(DateUtil.formatToString(dateOrderReceivedField.getValue()));
		acknowledgment.setCustomerOrderNumber(customerOrderNumberField.getText());
		acknowledgment.setApproxShipDate(DateUtil.formatToString(approxShipDateField.getValue()));
		acknowledgment.setShippingCost(shippingCostField.getText());
		acknowledgment.setFob(fobField.getText());
		acknowledgment.setShipVia(shipViaField.getText());
		acknowledgment.setDocuments(documentsField.getText());
		acknowledgment.setPrice(priceField.getText());
		acknowledgment.setUsername(main.getPreferenceList().get(0).getUsername());
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Cancel" button. The stage is
	 * closed, and the user returns to the main stage.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
		
		if (acknowledgment.getANumber() == null) {
			main.getDbHelper().deleteAcknowledgment(acknowledgment);
		}
		
		main.getAcknowledgmentData().clear();
		main.getAcknowledgmentData().addAll(main.getDbHelper().retrieveAcknowledgmentList());
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Select" button for the sold to
	 * address. A new dialog is opened, and any changes
	 * to the acknowledgment are loaded into this dialog.
	 */
	@FXML
	private void handleSelectSoldTo() {
		boolean okClicked = showAddresses("SOLD TO");
		saveCurrentAcknowledgment();
		setAcknowledgment(acknowledgment);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * editing the ship to or sold to addresses. 
	 * The Addresses dialog is started up with the selected 
	 * acknowledgment, to edit the data.
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
			controller.setAcknowledgment(acknowledgment);
			
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
	 * to the acknowledgment are loaded into this dialog.
	 */
	@FXML
	private void handleSelectShipTo() {
		boolean okClicked = showAddresses("SHIP TO");
		saveCurrentAcknowledgment();
		setAcknowledgment(acknowledgment);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * clicking the "Edit" button for the products. 
	 * A new dialog is opened, and any changes
	 * to the acknowledgment are loaded into this dialog.
	 */
	@FXML
	private void handleEditProducts() {
		boolean okClicked = showProducts();
		saveCurrentAcknowledgment();
		setAcknowledgment(acknowledgment);
	}
	
	/**
	 * Responds to the event triggered by the user
	 * editing the products. The Products dialog is 
	 * started up with the selected acknowledgment, 
	 * to edit the data.
	 */
	public boolean showProducts() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Products.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			Stage productStage = new Stage();
			productStage.setTitle("Add / Edit Products");
			productStage.getIcons().add(new Image("file:resources/images/logo.png"));
			productStage.initModality(Modality.WINDOW_MODAL);
			productStage.initOwner(dialogStage);
			
			Scene scene = new Scene(layout);
			productStage.setScene(scene);
			
			ProductsController controller = loader.getController();
			controller.setProductStage(productStage);
			controller.setMain(main);
			controller.setAcknowledgment(acknowledgment);
			
			productStage.showAndWait();
			
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
