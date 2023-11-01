package com.example.wguinventorysystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class allows for a product to be modified*
 * @author David Torres
 **/
public class ModifyProductFormGUI implements Initializable {
    @FXML
    private AnchorPane modifyProductAnchorPane;
    @FXML
    private Label modifyProductTableLabel;
    @FXML
    private Label modifyProductLabelID;
    @FXML
    private Label modifyProductNameLabel;
    @FXML
    private Label modifyProductInvLabel;
    @FXML
    private Label modifyProductMaxLabel;
    @FXML
    private Label modifyProductMinLabel;
    @FXML
    private Label modifyProductPriceLabel;
    @FXML
    private Label modifyProductAutoGenLabel;
    @FXML
    private TextField modifyProductNameTextField;
    @FXML
    private TextField modifyProductInvTextField;
    @FXML
    private TextField modifyProductPriceTextField;
    @FXML
    private TextField modifyProductMaxTextField;
    @FXML
    private TextField modifyProductMinTextField;
    @FXML
    private TableView<Part> modifyProductTableView;
    @FXML
    private TableColumn modifyProductTablePartsID;
    @FXML
    private TableColumn modifyProductTablePartName;
    @FXML
    private TableColumn modifyProductTableInvLevel;
    @FXML
    private TableColumn modifyProductTablePricePerUnit;
    @FXML
    private TextField modifyProductSearchTextField;
    @FXML
    private Button modifyProductAddButton;
    @FXML
    private TableView<Part> modifyProductAssociatedPartsTableView;
    @FXML
    private TableColumn modifyProductAssociatedPartsID;
    @FXML
    private TableColumn modifyProductAssociatedPartsName;
    @FXML
    private TableColumn modifyProductAssociatedPartsInvLevel;
    @FXML
    private TableColumn modifyProductAssociatedPartsPricePerUnit;
    @FXML
    private Button modifyProductRemoveButton;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button modifyProductCancelButton;
    @FXML
    private Label modifyProductLabel;
    @FXML
    private Label modifyProductAssociatedPartsTableLabel;
    @FXML
    private TextField modifyProductAutoGenIDLabel;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    /** Method transfers the data from the main controller *
     * @param product
     **/
    public void dataFromMainControllerForProduct(Product product) {

        modifyProductMaxTextField.setText(String.valueOf(product.getMax()));
        modifyProductMinTextField.setText(String.valueOf(product.getMin()));
        modifyProductPriceTextField.setText(String.valueOf(product.getPrice()));
        modifyProductInvTextField.setText(String.valueOf(product.getStock()));
        modifyProductNameTextField.setText(product.getName());
        modifyProductAutoGenIDLabel.setText(String.valueOf(product.getId()));


        // Clear associatedPartsList and add associated parts from the product
        associatedPartsList.clear();
        associatedPartsList.addAll(product.getAssociatedParts());

        // Populate associated parts table
        modifyProductAssociatedPartsTableView.setItems(associatedPartsList);

    }

    /**Method allows the user to search product using search bar, and has error alert for empty or not found *
 * @param actionEvent
 **/
    public void onSearchByProductNameOrId(ActionEvent actionEvent) {
        String userPartSearchInput = modifyProductSearchTextField.getText().trim();

        //This makes sure that when text field is empty that all parts repopulate.
        if (userPartSearchInput.isEmpty()) {
            modifyProductTableView.setItems(Inventory.getAllParts());
            return;
        }
        ObservableList<Part> part = Inventory.lookupPart(userPartSearchInput);

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
        modifyProductTableView.setItems(part);
    }

    /**This method gives the user a pop up if there is no match***/
    public void noPartFoundAlert () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Part Found");
        alert.setHeaderText("No Match was Found");
        alert.setContentText("Please Try Again, Search is case Sensitive!");
        alert.show();
    }
    /** initializes associatedPartsList to hold parts for updates **/
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();


    /**method adds associated part and gives user error if nothing is selected *
     * @param actionEvent
     **/
    public void onAddAssociatedParts(ActionEvent actionEvent) {
        Part part = (Part) modifyProductTableView.getSelectionModel().getSelectedItem();

        if (part == null) {
            showErrorAlert("No Part Selected or Highlighted", "Please try again!");
        } else {
            associatedPartsList.add(part);

            modifyProductAssociatedPartsTableView.setItems(associatedPartsList);

        }

    }
    /**Removes associated part and gives error if nothing was selected*
     * @param actionEvent
     **/
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part part = (Part) modifyProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            showErrorAlert("No Part Selected or Highlighted", "Please try again!");
        } else {
                boolean confirmed = ConfirmationPopUp("You are about to remove part", "Are you sure you want to proceed?");
                if (confirmed) {
                    associatedPartsList.remove(part);
                    modifyProductAssociatedPartsTableView.setItems(associatedPartsList);
                }

        }
    }

    /**Saves the new information and gives an alert for min and max as well if there is an empty field *
     * @param actionEvent
     **/
    public void onSaveButton(ActionEvent actionEvent) {

        String errorMessage = validateInput();

        if (errorMessage.isEmpty()) {
            try {

                int minQty = Integer.parseInt(modifyProductMinTextField.getText());

                int maxQty = Integer.parseInt(modifyProductMaxTextField.getText());

                if (minQty >= maxQty) {
                    showErrorAlert("Min should be less than max value", "Please fix and try again!");
                    return;
                } else {
                    Product selectedProduct = getHighlightedProduct();
                    updateProduct(selectedProduct);
                   if(selectedProduct != null) {returnBackMainGUI(actionEvent);}

                }
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid input in one or more text fields!", "Only enter numbers for (Inv,Price/Cost,Max/Min,Machine ID.");
            }
        } else {
            showErrorAlert("TextFields cannot be left empty!", errorMessage);
        }
    }

    private void returnBackMainGUI(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFormGUI.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Inventory Management System");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**Method gets product from inventory from ID**/
    private Product getHighlightedProduct(){
        int productId = Integer.parseInt(modifyProductAutoGenIDLabel.getText());
        return Inventory.lookupProduct(productId);
    }
    /**Method updates details on product values, as well as gives error alert fro min and max values *
     * @param productNewUpdate
     **/
    private void updateProduct(Product productNewUpdate) {
        int newUpdateStock = Integer.parseInt(modifyProductInvTextField.getText());
        int newUpdateMin = Integer.parseInt(modifyProductMinTextField.getText());
        int newUpdateMax = Integer.parseInt(modifyProductMaxTextField.getText());

        if (newUpdateStock < newUpdateMin || newUpdateStock > newUpdateMax) {
            showErrorAlert("Invalid Inventory Value", "Inventory should be between Min and Max values.");
            return;
        }

        productNewUpdate.setName(modifyProductNameTextField.getText());
        productNewUpdate.setPrice(Double.parseDouble(modifyProductPriceTextField.getText()));
        productNewUpdate.setStock(Integer.parseInt(modifyProductInvTextField.getText()));
        productNewUpdate.setMin(Integer.parseInt(modifyProductMinTextField.getText()));
        productNewUpdate.setMax(Integer.parseInt(modifyProductMaxTextField.getText()));

        // Updating the associated parts list of the products
        productNewUpdate.setAssociatedParts (associatedPartsList);
        // Update the product using the Inventory's updateProduct method
        Inventory.updateProduct(productNewUpdate.getId(), productNewUpdate);

    }

    /** Method makes sure that no fields are left empty and if so alerts user **/
    private String validateInput() {
        if (modifyProductNameTextField.getText().isEmpty() || modifyProductInvTextField.getText().isEmpty() ||
                modifyProductPriceTextField.getText().isEmpty() || modifyProductMinTextField.getText().isEmpty() ||
                modifyProductMaxTextField.getText().isEmpty()) {
            return "Please make sure all fields are selected and try again";
        }
        return "";
    }
    /**Method returns the user back the main GUI *
     * @param actionEvent
     **/
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormGUI.fxml")));
        Scene scene = new Scene(root);

        Stage stage = stageFromNode(actionEvent);
        settingSceneAndStage(stage, scene);
    }
    /**Method gets the stage with source node of an action event*
     * @param actionEvent
     **/
    private Stage stageFromNode (ActionEvent actionEvent){
        Node sourceNode = (Node) actionEvent.getSource();
        return (Stage) sourceNode.getScene().getWindow();
    }
    /** Method takes the stage and scene **/
    private void settingSceneAndStage (Stage stage, Scene scene){
        stage.setScene(scene);
        stage.setTitle("Inventory System");
        stage.show();
    }
    /**Method displays an Alert to the user **/
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attempting to Add Part Or Remove Part");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
    /**
     * Method displays a confirmation pop up for an action
     *
     * @return
     **/
    private boolean ConfirmationPopUp(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attempting to Add Part Or Remove Part");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result =alert.showAndWait();
        return result.isPresent() && result.get() ==ButtonType.OK;
    }

    /**This method is used to populate table with data*
     * @param resourceBundle
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Parts
        modifyProductTablePartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductTableInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductTablePricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Associated Parts

        modifyProductAssociatedPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAssociatedPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAssociatedPartsInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAssociatedPartsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Setting associated parts
        associatedPartsList = FXCollections.observableArrayList();

        // Setting parts table data
        modifyProductTableView.setItems(Inventory.getAllParts());



    }
}