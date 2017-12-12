package com.riteboiler.frontoffice.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Acknowledgment;
import com.riteboiler.frontoffice.model.Product;

/**
 * This class is the controller for the 
 * Products stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class ProductsController {
	@FXML
	private TextField quantityField1;
	@FXML
	private TextArea descriptionField1;
	@FXML
	private TextField quantityField2;
	@FXML
	private TextArea descriptionField2;
	@FXML
	private TextField quantityField3;
	@FXML
	private TextArea descriptionField3;
	@FXML
	private TextField quantityField4;
	@FXML
	private TextArea descriptionField4;
	@FXML
	private TextField quantityField5;
	@FXML
	private TextArea descriptionField5;
	@FXML
	private TextField quantityField6;
	@FXML
	private TextArea descriptionField6;
	@FXML
	private TextField quantityField7;
	@FXML
	private TextArea descriptionField7;
	@FXML
	private TextField quantityField8;
	@FXML
	private TextArea descriptionField8;
	@FXML
	private TextField quantityField9;
	@FXML
	private TextArea descriptionField9;
	@FXML
	private TextField quantityField10;
	@FXML
	private TextArea descriptionField10;
	
	private List<TextField> quantityList = new ArrayList<TextField>();
	private List<TextArea> descriptionList = new ArrayList<TextArea>();
	
	boolean okClicked = false;
	
	private Stage productStage;
	private Acknowledgment acknowledgment;
	private MainApp main;
	
	private SortedSet<String> autocompleteSet;
	private ContextMenu autocompleteMenu;
	
	/**
	 * Adds the fields to lists, to help manage
	 * inserting new products. This is called
	 * automatically.
	 */
	@FXML
	private void initialize() {
		quantityList.add(quantityField1);
		quantityList.add(quantityField2);
		quantityList.add(quantityField3);
		quantityList.add(quantityField4);
		quantityList.add(quantityField5);
		quantityList.add(quantityField6);
		quantityList.add(quantityField7);
		quantityList.add(quantityField8);
		quantityList.add(quantityField9);
		quantityList.add(quantityField10);
		
		descriptionList.add(descriptionField1);
		descriptionList.add(descriptionField2);
		descriptionList.add(descriptionField3);
		descriptionList.add(descriptionField4);
		descriptionList.add(descriptionField5);
		descriptionList.add(descriptionField6);
		descriptionList.add(descriptionField7);
		descriptionList.add(descriptionField8);
		descriptionList.add(descriptionField9);
		descriptionList.add(descriptionField10);
		
		descriptionField1.setWrapText(true);
		descriptionField2.setWrapText(true);
		descriptionField3.setWrapText(true);
		descriptionField4.setWrapText(true);
		descriptionField5.setWrapText(true);
		descriptionField6.setWrapText(true);
		descriptionField7.setWrapText(true);
		descriptionField8.setWrapText(true);
		descriptionField9.setWrapText(true);
		descriptionField10.setWrapText(true);
	}
	
	/**
	 * Setter for the reference to the Products dialog stage.
	 * @param productStage Instance of productStage Stage
	 */
	public void setProductStage(Stage productStage) {
		this.productStage = productStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * It also initializes the auto-complete functionality.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		autocompleteSet = main.getDbHelper().retrieveProductDescriptionList();
		autocompleteMenu = new ContextMenu();

		setDescriptionListeners();
	}
	
	/**
	 * Sets the listener for the description text areas,
	 * giving them the auto-complete functionality.
	 */
	private void setDescriptionListeners() {
		descriptionField1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField1);
			}
		});
		descriptionField1.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField2.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField2);
			}
		});
		descriptionField2.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField3.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField3);
			}
		});
		descriptionField3.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField4.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField4);
			}
		});
		descriptionField4.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField5.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField5);
			}
		});
		descriptionField5.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField6.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField6);
			}
		});
		descriptionField6.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField7.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField7);
			}
		});
		descriptionField7.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField8.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField8);
			}
		});
		descriptionField8.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField9.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField9);
			}
		});
		descriptionField9.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		descriptionField10.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextArea(newValue, descriptionField10);
			}
		});
		descriptionField10.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
	}
	
	/**
	 * Determine if current value can be auto-completed.
	 * If so, populate auto-complete context menu.
	 * @param currentValue Current string value of text area.
	 * @param descriptionField Current description field.
	 */
	private void setAutocompleteTextArea(String currentValue, TextArea descriptionField) {
		if (currentValue.length() == 0) {
			autocompleteMenu.hide();
		} else {
			LinkedList<String> searchResult = new LinkedList<>();
			searchResult.addAll(autocompleteSet.subSet(currentValue, 
					currentValue + Character.MAX_VALUE));
			
			if (searchResult.size() > 0) {
				populateAutocompleteMenu(searchResult, descriptionField);
			} else {
				autocompleteMenu.hide();
			}
		}
	}
	
	/**
	 * Populate the auto-complete context menu
	 * with the given search results. Display is
	 * limited to 3 entries for performance.
	 * @param searchResult The set of matching strings.
	 * @param descriptionField Current description field.
	 */
	private void populateAutocompleteMenu(List<String> searchResult, TextArea descriptionField) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		int maxEntries = 3;
		int count = Math.min(searchResult.size(), maxEntries);
		for (int i = 0; i < count; i++) {
			final String fullResult = searchResult.get(i);
			String result = fullResult;
			
			if (result.length() > 50) {
				result = result.substring(0, 50);
			} 
			
			Label entryLabel = new Label(result);
			
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					descriptionField.setText(fullResult);
					autocompleteMenu.hide();
				}
			});
			
			menuItems.add(item);
		}
		
		autocompleteMenu.getItems().clear();
		autocompleteMenu.getItems().addAll(menuItems);
		
		if (!autocompleteMenu.isShowing()) {
			autocompleteMenu.show(descriptionField, Side.BOTTOM, 0, 0);
		}
	}
	
	/**
	 * Setter for the reference to the acknowledgment
	 * to load into the dialog. 
	 * @param acknowledgment Acknowledgment to edit
	 */
	public void setAcknowledgment(Acknowledgment acknowledgment) {
		this.acknowledgment = acknowledgment;
		
		if (acknowledgment.getProductData().size() > 0) {
			for (int i = 0; i < acknowledgment.getProductData().size(); i++) {
				quantityList.get(i).setText(Integer.toString(acknowledgment.getProductData().get(i).getQuantity()));
				descriptionList.get(i).setText(acknowledgment.getProductData().get(i).getDescription());
			}
		}
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
	 * clicking the "OK" button. All products listed
	 * are inserted into the database, and added to the
	 * acknowledgment. If the fields are empty, no 
	 * products are saved to the acknowledgment.
	 * 
	 */
	@FXML
	private void handleOk() {
		if (isValidInput()) {
			main.getDbHelper().removeProducts(acknowledgment.getId());
			
			for (int i = 0; i < 10; i++) {
				if ((quantityList.get(i).getText() != null && !quantityList.get(i).getText().isEmpty()) && 
						(descriptionList.get(i).getText() != null && !descriptionList.get(i).getText().isEmpty())) {
					Product product = new Product();
					product.setQuantity(Integer.parseInt(quantityList.get(i).getText()));
					product.setDescription(descriptionList.get(i).getText());
					product.setAcknowledgmentId(acknowledgment.getId());
					main.getDbHelper().insertProduct(product);
				}
			}
			
			acknowledgment.getProductData().clear();
			acknowledgment.getProductData().addAll(main.getDbHelper().retrieveProductList(acknowledgment.getId()));
			
			okClicked = true;
			productStage.close();
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
		productStage.close();
	}
}
