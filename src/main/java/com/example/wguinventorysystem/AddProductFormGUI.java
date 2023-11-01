package com.example.wguinventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**This class main function is to add a product to data/list *
 * @author David Torres
 **/

public class AddProductFormGUI implements Initializable {
    @FXML
    private AnchorPane addProductAnchorPane;
    @FXML
    public Label addProductLabel;
    @FXML
    private Label addProductIDLabel;
    @FXML
    private Label addProductNameLabel;
    @FXML
    private Label addProductInvLabel;
    @FXML
    private Label addProductMaxLabel;
    @FXML
    private Label addProductMinLabel;
    @FXML
    private Label addProductPriceLabel;
    @FXML
    private Label addProductAutoGenLabel;
    @FXML
    private TextField addProductNameTextField;
    @FXML
    private TextField addProductInvTextBox;
    @FXML
    private TextField addProductPriceTextBox;
    @FXML
    private TextField addProductMaxTextField;
    @FXML
    private TextField addProductMinTextField;
    @FXML
    private TableView<Part> addProductPartsTableView;
    @FXML
    private TableColumn addProductPartsID;
    @FXML
    private TableColumn addProductPartsPartName;
    @FXML
    private TableColumn addProductPartsInvLevel;
    @FXML
    private TableColumn addProductPartsPricePerUnit;
    @FXML
    private TextField addProductSearchTextField;
    @FXML
    private Button addProductAddButton;
    @FXML
    private TableView<Part> addProductAssociatedPartsTableView;
    @FXML
    private TableColumn addProductAssociatedPartsID;
    @FXML
    private TableColumn addProductAssociatedPartsName;
    @FXML
    private TableColumn addProductAssociatedPartsInvLevel;
    @FXML
    private TableColumn addProductAssociatedPartsPricePerUnit;
    @FXML
    private Button addProductRemoveAssociatedPartButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private Label addProductPartsLabel;
    @FXML
    private Label addProductAssociatedPartsLabel;
    @FXML
    private TextField addProductAutoGenTextBox;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    /**This allows for the data to be shown when the user opens the Add Product GUI, transfers data to display *
     * @param resourceBundle
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Parts
        addProductPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartsInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Associated Parts

        addProductAssociatedPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAssociatedPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAssociatedPartsInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAssociatedPartsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductPartsTableView.setItems(Inventory.getAllParts());

    }
    /**This method is used to search the current data within list. It will give an error if no part is found and if it is found it will display it alone*
     * @param actionEvent
     **/
    public void onPartsSearchBar(ActionEvent actionEvent) {
        String userPartSearchInput = addProductSearchTextField.getText().trim();

        if (userPartSearchInput.isEmpty()) {
            addProductPartsTableView.setItems(Inventory.getAllParts());
            return;
        }

        //Uses the Lookup part Method stored in the Inventory Class to search against users input via String
        ObservableList<Part> part = Inventory.lookupPart(userPartSearchInput);

        //Since the above only searches using a string we need to search via Int if no result found
        if (part.isEmpty()) {
            try {
                int partID = Integer.parseInt(userPartSearchInput);
                Part foundPartID = Inventory.lookupPart(partID);
                if(foundPartID != null) {
                    part.add(foundPartID);
                } else {
                    noPartFoundAlert();
                }
            } catch (NumberFormatException e) {
                noPartFoundAlert();
            }
        }
        addProductPartsTableView.setItems(part);
    }
    /**This method is used as a template for an alert to the user **/
    public void noPartFoundAlert () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Part Found");
        alert.setHeaderText("No Match was Found");
        alert.setContentText("Please Try Again, Search Is Case Sensitive!");
        alert.show();
    }

    /**initializes associatedPartsList to hold parts for updates**/
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    /**Part from the part table is added to the associated table, it displays an error if no part is selected/highlighted*
     * @param actionEvent
     **/
    public void onAddAssociatedPartButton(ActionEvent actionEvent) {
        Part part = addProductPartsTableView.getSelectionModel().getSelectedItem();

        if (part == null) {
            showErrorAlert("No Part Selected or Highlighted", "Please try again!");
        } else {
            associatedPartsList.add(part);

            addProductAssociatedPartsTableView.setItems(associatedPartsList);

        }

    }
    /**Part from the associated table is removed, gives an alert if nothing is selected or highlighted*
     * @param actionEvent
     **/
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {

        Part part =  addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            showErrorAlert("No Part Selected or Highlighted", "Please try again!");
        } else ConfirmationPopUp("You are about to remove part", "Are you sure you want to proceed?");
        associatedPartsList.remove(part);
        addProductAssociatedPartsTableView.setItems(associatedPartsList);

    }

    /**Method adds or updates product, gives a user error for min and max values as well as if fields are left empty*
     * @param actionEvent
     **/
    public void onSaveAssociatedPartButton(ActionEvent actionEvent) {

        String errorMessage = validateInput();

        if (errorMessage.isEmpty()) {

            try {
                int minQty = Integer.parseInt(addProductMinTextField.getText());
                int maxQty = Integer.parseInt(addProductMaxTextField.getText());

                if (minQty >= maxQty) {
                    showErrorAlert("Min should be less than max value", "Please fix and try again!");
                } else {
                    Product newProduct = createProduct();
                    if (newProduct != null) {
                        Inventory.addProduct(newProduct);
                        closeStage(actionEvent);
                    }

                }
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid input in one or more text fields!", "Only enter numbers for (Inv,Price/Cost,Max/Min,Machine ID.");
            }
        } else {
            showErrorAlert("TextFields cannot be left empty!", errorMessage);
        }
    }
    /**Method creates a new product, gives a user error for min and max values **/
    private Product createProduct() {
        int productIndex = Inventory.productIndex;
        String name = addProductNameTextField.getText();
        double price = Double.parseDouble(addProductPriceTextBox.getText());
        int stock = Integer.parseInt(addProductInvTextBox.getText());
        int max = Integer.parseInt(addProductMaxTextField.getText());
        int min = Integer.parseInt(addProductMinTextField.getText());

        if (stock < min || stock > max) {
            showErrorAlert("Invalid value for Inventory", "Inventory needs to be between Min and Max, Please try again!");
            return null;
        }

        Product newProduct = new Product(productIndex, name, price, stock, min,max);

        newProduct.getAssociatedParts().addAll(associatedPartsList);

        return newProduct;
    }

    /**This method makes sure that no fields are left empty and warns the user with a pop up to try again**/
    private String validateInput() {
        if (addProductNameTextField.getText().isEmpty() || addProductInvTextBox.getText().isEmpty() ||
                addProductPriceTextBox.getText().isEmpty() || addProductMinTextField.getText().isEmpty() ||
                addProductMaxTextField.getText().isEmpty()) {
            return "Please make sure a field is selected and try again";
        }
        return "";
    }
    /**Method to display and error to the user **/
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attempting to Add Part Or Remove Part");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**Method that pops up a confirmation box after a user attempts to do an activity**/
    private void ConfirmationPopUp(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attempting to Add Part Or Remove Part");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
    /**This closes the stage and returns to main GUI*
     * @param actionEvent
     **/
    private void closeStage(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();


    }
    /** returns to the main GUI after users clicks the cancel button*
     * @param actionEvent
     **/
    public void onCancelAssociatedPart(ActionEvent actionEvent) {
            Stage stage = (Stage) addProductCancelButton.getScene().getWindow();
            stage.close();
    }


}

