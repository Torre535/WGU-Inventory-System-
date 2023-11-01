package com.example.wguinventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**Class allows the user to add parts to database/list*
 * @author David Torres
 **/
public class AddPartFormGUI implements Initializable {

    @FXML
    private TextField addIDAutoGenTextField;
    @FXML
    private AnchorPane addPartAnchorPlane;
    @FXML
    private Label addPartLabel;
    @FXML
    private RadioButton addPartInHouseButton;
    @FXML
    private ToggleGroup newPart;
    @FXML
    private RadioButton addPartOutsourcedButton;
    @FXML
    private Label addPartIDLabel;
    @FXML
    private Label addPartNameLabel;
    @FXML
    private Label addPartPriceLabel;
    @FXML
    private Label addPartInvLabel;
    @FXML
    private Label addPartMaxLabel;
    @FXML
    private Label addPartHBoxLabel;
    @FXML
    private TextField addPartNameTextField;
    @FXML
    private TextField addPartInvTextField;
    @FXML
    private TextField addPartPriceTextField;
    @FXML
    private TextField addPartMaxTextField;
    @FXML
    private TextField addPartMinTextField;
    @FXML
    private Label addPartMinLabel;
    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;
    @FXML
    private GridPane addPartGridPane;
    @FXML
    private Label addPartMachineIDLabel;
    @FXML
    private TextField addPartMachineIDTextField;
    @FXML
    private TextField addPartCompanyNumberTextField;
    @FXML
    private Label addPartCompanyNameLabel;
    @FXML
    private TextField addMachineIDTextField;

    @FXML
    private Stage stage;

    private Scene scene;

    /**This method is used for setting up the toggle button to go back and forth from Inhouse and Outsourced*
     * @param resourceBundle
     **/
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ToggleGroup toggleGroup = new ToggleGroup();
        addPartInHouseButton.setToggleGroup(toggleGroup);
        addPartOutsourcedButton.setToggleGroup(toggleGroup);

    }
    /**This method sets the text on the GUI when In house Radio button is selected *
     * @param actionEvent
     **/
    public void onInHouseButton(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Machine ID");
    }


    /**This method sets the text on the GUI when the outsourced button is selected *
     * @param actionEvent
     **/
    public void onOutsourcedButton(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Company Name");
    }



    /**This method is called when the user clicks save, it does data validation and adds it to List/database*
     * @param actionEvent
     **/
    public void onSaveButton(ActionEvent actionEvent)  {
        String errorMessage = validateInput();

        if (errorMessage.isEmpty()) {

            try {
                int minQty = Integer.parseInt(addPartMinTextField.getText());
                int maxQty = Integer.parseInt(addPartMaxTextField.getText());

                if (minQty >= maxQty) {
                    showErrorAlert("Min should be less than max value", "Please fix and try again!");

                } else {
                    Part newPart = createPart();
                    Inventory.addPart(newPart);

                    if (newPart != null) {closeStage(actionEvent);}

                }
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid input one or more text fields!", "Only enter numbers for (Inv,Price/Cost,Max/Min,Machine ID.");
            }
        } else {
            showErrorAlert("TextFields cannot be left empty!", errorMessage);
        }
    }

    /**This method is used to make sure that no textfields are empty and the user gets an error**/
    private String validateInput() {
        if (addPartNameTextField.getText().isEmpty() || addPartInvTextField.getText().isEmpty() ||
                addPartPriceTextField.getText().isEmpty() || addPartMinTextField.getText().isEmpty() ||
                addPartMaxTextField.getText().isEmpty() || addMachineIDTextField.getText().isEmpty()) {
            return "Please make sure a field is selected and try again";
        }
        return "";
    }
    /**This method creates the part and adds to data, it also does min and max validation**/
    private Part createPart() {
        int partIndex = Inventory.partIndex;
        String name = addPartNameTextField.getText().trim();
        double price = Double.parseDouble(addPartPriceTextField.getText());
        int stock = Integer.parseInt(addPartInvTextField.getText());
        int max = Integer.parseInt(addPartMaxTextField.getText());
        int min = Integer.parseInt(addPartMinTextField.getText());


        if (stock < min || stock >max) {
            showErrorAlert("Invalid value for Inventory", "Inventory needs to be between Min and Max, Please try again!");
            return null;
        }

        if (addPartOutsourcedButton.isSelected()) {
            String companyName = addMachineIDTextField.getText();
            return new Outsourced(partIndex, name, price, stock, min, max, companyName);

        } else {
            int machineID = Integer.parseInt(addMachineIDTextField.getText());
            return new InHouse(partIndex, name, price, stock, min, max, machineID);
        }
    }
    /**This method allows the stage to be closed after an action event such as save *
     * @param actionEvent
     **/
    private void closeStage(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    /**This method displays an error to the user **/
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attempting to Add Part");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**This method closes the current add part GUI on button click of cancel and returns to main GUI*
     * @param actionEvent
     **/
    public void onCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
        stage.close();
    }
}













