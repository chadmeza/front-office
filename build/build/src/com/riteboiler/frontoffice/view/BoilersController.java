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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.BillOfLading;
import com.riteboiler.frontoffice.model.Boiler;

/**
 * This class is the controller for the 
 * Boilers stage and scene. It 
 * connects the data to the views, and
 * handles user events.
 * @author Chad Meza
 */
public class BoilersController {
	@FXML
	private TextField quantityField1;
	@FXML
	private TextField modelField1;
	@FXML
	private TextField serialField1;
	@FXML
	private TextField weightField1;
	@FXML
	private TextField dimensionsField1;
	@FXML
	private TextField quantityField2;
	@FXML
	private TextField modelField2;
	@FXML
	private TextField serialField2;
	@FXML
	private TextField weightField2;
	@FXML
	private TextField dimensionsField2;
	@FXML
	private TextField quantityField3;
	@FXML
	private TextField modelField3;
	@FXML
	private TextField serialField3;
	@FXML
	private TextField weightField3;
	@FXML
	private TextField dimensionsField3;
	@FXML
	private TextField quantityField4;
	@FXML
	private TextField modelField4;
	@FXML
	private TextField serialField4;
	@FXML
	private TextField weightField4;
	@FXML
	private TextField dimensionsField4;
	@FXML
	private TextField quantityField5;
	@FXML
	private TextField modelField5;
	@FXML
	private TextField serialField5;
	@FXML
	private TextField weightField5;
	@FXML
	private TextField dimensionsField5;
	@FXML
	private TextField quantityField6;
	@FXML
	private TextField modelField6;
	@FXML
	private TextField serialField6;
	@FXML
	private TextField weightField6;
	@FXML
	private TextField dimensionsField6;
	@FXML
	private TextField quantityField7;
	@FXML
	private TextField modelField7;
	@FXML
	private TextField serialField7;
	@FXML
	private TextField weightField7;
	@FXML
	private TextField dimensionsField7;
	@FXML
	private TextField quantityField8;
	@FXML
	private TextField modelField8;
	@FXML
	private TextField serialField8;
	@FXML
	private TextField weightField8;
	@FXML
	private TextField dimensionsField8;
	@FXML
	private TextField quantityField9;
	@FXML
	private TextField modelField9;
	@FXML
	private TextField serialField9;
	@FXML
	private TextField weightField9;
	@FXML
	private TextField dimensionsField9;
	@FXML
	private TextField quantityField10;
	@FXML
	private TextField modelField10;
	@FXML
	private TextField serialField10;
	@FXML
	private TextField weightField10;
	@FXML
	private TextField dimensionsField10;
	
	private List<TextField> quantityList = new ArrayList<TextField>();
	private List<TextField> modelList = new ArrayList<TextField>();
	private List<TextField> serialList = new ArrayList<TextField>();
	private List<TextField> weightList = new ArrayList<TextField>();
	private List<TextField> dimensionsList = new ArrayList<TextField>();
	
	boolean okClicked = false;
	
	private Stage boilerStage;
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
		
		modelList.add(modelField1);
		modelList.add(modelField2);
		modelList.add(modelField3);
		modelList.add(modelField4);
		modelList.add(modelField5);
		modelList.add(modelField6);
		modelList.add(modelField7);
		modelList.add(modelField8);
		modelList.add(modelField9);
		modelList.add(modelField10);
		
		serialList.add(serialField1);
		serialList.add(serialField2);
		serialList.add(serialField3);
		serialList.add(serialField4);
		serialList.add(serialField5);
		serialList.add(serialField6);
		serialList.add(serialField7);
		serialList.add(serialField8);
		serialList.add(serialField9);
		serialList.add(serialField10);
		
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
	 * Setter for the reference to the Boilers dialog stage.
	 * @param boilerStage Instance of boilerStage Stage
	 */
	public void setBoilerStage(Stage boilerStage) {
		this.boilerStage = boilerStage;
	}
	
	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
		
		autocompleteSet = main.getDbHelper().retrieveBoilerModelList();
		autocompleteMenu = new ContextMenu();

		setModelListeners();
	}
	
	/**
	 * Sets the listener for the model text fields,
	 * giving them the auto-complete functionality.
	 */
	private void setModelListeners() {
		modelField1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField1, weightField1, dimensionsField1);
			}
		});
		modelField1.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField2.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField2, weightField2, dimensionsField2);
			}
		});
		modelField2.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField3.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField3, weightField3, dimensionsField3);
			}
		});
		modelField3.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField4.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField4, weightField4, dimensionsField4);
			}
		});
		modelField4.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField5.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField5, weightField5, dimensionsField5);
			}
		});
		modelField5.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField6.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField6, weightField6, dimensionsField6);
			}
		});
		modelField6.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField7.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField7, weightField7, dimensionsField7);
			}
		});
		modelField7.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField8.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField8, weightField8, dimensionsField8);
			}
		});
		modelField8.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField9.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField9, weightField9, dimensionsField9);
			}
		});
		modelField9.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				autocompleteMenu.hide();
			}
		});
		
		modelField10.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setAutocompleteTextField(newValue, modelField10, weightField10, dimensionsField10);
			}
		});
		modelField10.focusedProperty().addListener(new ChangeListener<Boolean>() {

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
	 * @param modelField Current model field.
	 * @param weightField Current weight field.
	 * @param dimensionsField Current dimensions field.
	 */
	private void setAutocompleteTextField(String currentValue, TextField modelField, 
			TextField weightField, TextField dimenesionsField) {
		if (currentValue.length() == 0) {
			autocompleteMenu.hide();
		} else {
			LinkedList<String> searchResult = new LinkedList<>();
			searchResult.addAll(autocompleteSet.subSet(currentValue, 
					currentValue + Character.MAX_VALUE));
			
			if (searchResult.size() > 0) {
				populateAutocompleteMenu(searchResult, modelField, weightField, dimenesionsField);
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
	 * @param modelField Current model field.
	 * @param weightField Current weight field.
	 * @param dimensionsField Current dimensions field.
	 */
	private void populateAutocompleteMenu(List<String> searchResult, TextField modelField, 
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
					modelField.setText(fullResult);
					
					Boiler boiler = main.getDbHelper().retrieveBoilerFromModel(fullResult);
					
					if (boiler.getWeight() != null) {
						weightField.setText(boiler.getWeight());
					}
					
					if (boiler.getDimensions() != null) {
						dimenesionsField.setText(boiler.getDimensions());
					}
					
					autocompleteMenu.hide();
				}
			});
			
			menuItems.add(item);
		}
		
		autocompleteMenu.getItems().clear();
		autocompleteMenu.getItems().addAll(menuItems);
		
		if (!autocompleteMenu.isShowing()) {
			autocompleteMenu.show(modelField, Side.BOTTOM, 0, 0);
		}
	}
	
	/**
	 * Setter for the reference to the bill of lading
	 * to load into the dialog. 
	 * @param billOfLading Bill Of Lading to edit
	 */
	public void setBillOfLading(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
		
		if (billOfLading.getBoilerData().size() > 0) {
			for (int i = 0; i < billOfLading.getBoilerData().size(); i++) {
				quantityList.get(i).setText(Integer.toString(billOfLading.getBoilerData().get(i).getQuantity()));
				modelList.get(i).setText(billOfLading.getBoilerData().get(i).getModel());
				serialList.get(i).setText(billOfLading.getBoilerData().get(i).getSerial());
				weightList.get(i).setText(billOfLading.getBoilerData().get(i).getWeight());
				dimensionsList.get(i).setText(billOfLading.getBoilerData().get(i).getDimensions());
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
	 * clicking the "OK" button. All boilers listed
	 * are inserted into the database, and added to the
	 * bill of lading. If the fields are empty, no 
	 * boilers are saved to the bill of lading.
	 * 
	 */
	@FXML
	private void handleOk() {
		if (isValidInput()) {
			main.getDbHelper().removeBoilers(billOfLading.getId());
			
			for (int i = 0; i < 10; i++) {
				if ((quantityList.get(i).getText() != null && !quantityList.get(i).getText().isEmpty()) && 
						(modelList.get(i).getText() != null && !modelList.get(i).getText().isEmpty())) {
					Boiler boiler = new Boiler();
					boiler.setQuantity(Integer.parseInt(quantityList.get(i).getText()));
					boiler.setModel(modelList.get(i).getText());
					boiler.setSerial(serialList.get(i).getText());
					boiler.setWeight(weightList.get(i).getText());
					boiler.setDimensions(dimensionsList.get(i).getText());
					boiler.setBillOfLadingId(billOfLading.getId());
					main.getDbHelper().insertBoiler(boiler);
				}
			}
			
			billOfLading.getBoilerData().clear();
			billOfLading.getBoilerData().addAll(main.getDbHelper().retrieveBoilerList(billOfLading.getId()));
			
			okClicked = true;
			boilerStage.close();
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
		boilerStage.close();
	}
}
