package com.riteboiler.frontoffice.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.BillOfLading;
import com.riteboiler.frontoffice.model.Boiler;
import com.riteboiler.frontoffice.model.Pallet;

/**
 * This class is the controller for the 
 * Pallets stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class PalletsController {
	@FXML
	private TextField quantityField1;
	@FXML
	private TextField descriptionField1;
	@FXML
	private TextField weightField1;
	@FXML
	private TextField dimensionsField1;
	@FXML
	private TextField quantityField2;
	@FXML
	private TextField descriptionField2;
	@FXML
	private TextField weightField2;
	@FXML
	private TextField dimensionsField2;
	@FXML
	private TextField quantityField3;
	@FXML
	private TextField descriptionField3;
	@FXML
	private TextField weightField3;
	@FXML
	private TextField dimensionsField3;
	@FXML
	private TextField quantityField4;
	@FXML
	private TextField descriptionField4;
	@FXML
	private TextField weightField4;
	@FXML
	private TextField dimensionsField4;
	@FXML
	private TextField quantityField5;
	@FXML
	private TextField descriptionField5;
	@FXML
	private TextField weightField5;
	@FXML
	private TextField dimensionsField5;
	@FXML
	private TextField quantityField6;
	@FXML
	private TextField descriptionField6;
	@FXML
	private TextField weightField6;
	@FXML
	private TextField dimensionsField6;
	@FXML
	private TextField quantityField7;
	@FXML
	private TextField descriptionField7;
	@FXML
	private TextField weightField7;
	@FXML
	private TextField dimensionsField7;
	@FXML
	private TextField quantityField8;
	@FXML
	private TextField descriptionField8;
	@FXML
	private TextField weightField8;
	@FXML
	private TextField dimensionsField8;
	@FXML
	private TextField quantityField9;
	@FXML
	private TextField descriptionField9;
	@FXML
	private TextField weightField9;
	@FXML
	private TextField dimensionsField9;
	@FXML
	private TextField quantityField10;
	@FXML
	private TextField descriptionField10;
	@FXML
	private TextField weightField10;
	@FXML
	private TextField dimensionsField10;
	
	private List<TextField> quantityList = new ArrayList<TextField>();
	private List<TextField> descriptionList = new ArrayList<TextField>();
	private List<TextField> weightList = new ArrayList<TextField>();
	private List<TextField> dimensionsList = new ArrayList<TextField>();
	
	boolean okClicked = false;
	
	private Stage palletStage;
	private BillOfLading billOfLading;
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
		
		weightList.add(weightField1);
		weightList.add(weightField2);
		weightList.add(weightField3);
		weightList.add(weightField4);
		weightList.add(weightField5);
		weightList.add(weightField6);
		weightList.add(weightField7);
		weightList.add(weightField8);
		weightList.add(weightField9);
		weightList.add(weightField10);
		
		dimensionsList.add(dimensionsField1);
		dimensionsList.add(dimensionsField2);
		dimensionsList.add(dimensionsField3);
		dimensionsList.add(dimensionsField4);
		dimensionsList.add(dimensionsField5);
		dimensionsList.add(dimensionsField6);
		dimensionsList.add(dimensionsField7);
		dimensionsList.add(dimensionsField8);
		dimensionsList.add(dimensionsField9);
		dimensionsList.add(dimensionsField10);
	}
	
	/**
	 * Setter for the reference to the Pallets dialog stage.
	 * @param palletStage Instance of palletStage Stage
	 */
	public void setPalletStage(Stage palletStage) {
		this.palletStage = palletStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		
		autocompleteSet = main.getDbHelper().retrievePalletDescriptionList();
		autocompleteMenu = new ContextMenu();

		setDescriptionListeners();
	}
	
	/**
	 * Sets the listener for the description text fields,
	 * giving them the auto-complete functionality.
	 */
	private void setDescriptionListeners() {
		descriptionField1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, descriptionField1, weightField1, dimensionsField1);
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
				setAutocompleteTextField(newValue, descriptionField2, weightField2, dimensionsField2);
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
				setAutocompleteTextField(newValue, descriptionField3, weightField3, dimensionsField3);
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
				setAutocompleteTextField(newValue, descriptionField4, weightField4, dimensionsField4);
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
				setAutocompleteTextField(newValue, descriptionField5, weightField5, dimensionsField5);
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
				setAutocompleteTextField(newValue, descriptionField6, weightField6, dimensionsField6);
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
				setAutocompleteTextField(newValue, descriptionField7, weightField7, dimensionsField7);
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
				setAutocompleteTextField(newValue, descriptionField8, weightField8, dimensionsField8);
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
				setAutocompleteTextField(newValue, descriptionField9, weightField9, dimensionsField9);
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
				setAutocompleteTextField(newValue, descriptionField10, weightField10, dimensionsField10);
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
	 * @param weightField Current weight field.
	 * @param dimensionsField Current dimensions field.
	 */
	private void setAutocompleteTextField(String currentValue, TextField descriptionField, 
			TextField weightField, TextField dimenesionsField) {
		if (currentValue.length() == 0) {
			autocompleteMenu.hide();
		} else {
			LinkedList<String> searchResult = new LinkedList<>();
			searchResult.addAll(autocompleteSet.subSet(currentValue, 
					currentValue + Character.MAX_VALUE));
			
			if (searchResult.size() > 0) {
				populateAutocompleteMenu(searchResult, descriptionField, weightField, dimenesionsField);
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
	 * @param weightField Current weight field.
	 * @param dimensionsField Current dimensions field.
	 */
	private void populateAutocompleteMenu(List<String> searchResult, TextField descriptionField, 
			TextField weightField, TextField dimenesionsField) {
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
					
					Pallet pallet = main.getDbHelper().retrievePalletFromDescription(fullResult);
					
					if (pallet.getWeight() != null) {
						weightField.setText(pallet.getWeight());
					}
					
					if (pallet.getDimensions() != null) {
						dimenesionsField.setText(pallet.getDimensions());
					}
					
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
	 * Setter for the reference to the bill of lading
	 * to load into the dialog. 
	 * @param billOfLading Bill Of Lading to edit
	 */
	public void setBillOfLading(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
		
		if (billOfLading.getPalletData().size() > 0) {
			for (int i = 0; i < billOfLading.getPalletData().size(); i++) {
				quantityList.get(i).setText(Integer.toString(billOfLading.getPalletData().get(i).getQuantity()));
				descriptionList.get(i).setText(billOfLading.getPalletData().get(i).getDescription());
				weightList.get(i).setText(billOfLading.getPalletData().get(i).getWeight());
				dimensionsList.get(i).setText(billOfLading.getPalletData().get(i).getDimensions());
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
	 * clicking the "OK" button. All pallets listed
	 * are inserted into the database, and added to the
	 * bill of lading. If the fields are empty, no 
	 * pallets are saved to the bill of lading.
	 * 
	 */
	@FXML
	private void handleOk() {
		if (isValidInput()) {
			main.getDbHelper().removePallets(billOfLading.getId());
			
			for (int i = 0; i < 10; i++) {
				if ((quantityList.get(i).getText() != null && !quantityList.get(i).getText().isEmpty()) && 
						(descriptionList.get(i).getText() != null && !descriptionList.get(i).getText().isEmpty())) {
					Pallet pallet = new Pallet();
					pallet.setQuantity(Integer.parseInt(quantityList.get(i).getText()));
					pallet.setDescription(descriptionList.get(i).getText());
					pallet.setWeight(weightList.get(i).getText());
					pallet.setDimensions(dimensionsList.get(i).getText());
					pallet.setBillOfLadingId(billOfLading.getId());
					main.getDbHelper().insertPallet(pallet);
				}
			}
			
			billOfLading.getPalletData().clear();
			billOfLading.getPalletData().addAll(main.getDbHelper().retrievePalletList(billOfLading.getId()));
			
			okClicked = true;
			palletStage.close();
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
		palletStage.close();
	}
}
