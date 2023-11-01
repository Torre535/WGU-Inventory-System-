package com.example.wguinventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**Class allows the user to modify a part they selected and add it to list/data *
 * @author David Torres
 **/
public class ModifyPartFormGUI implements Initializable {

    @FXML
    private Label modifyPartMachineIDLabel;
    @FXML
    private TextField modifyPartMachineIDTextField;
    @FXML
    private AnchorPane modifyPartAnchorPane;
    @FXML
    private Label modifyPartLabel;
    @FXML
    private RadioButton modifyPartInHouseButton;
    @FXML
    private RadioButton modifyPartOutsourcedButton;
    @FXML
    private Label modifyPartIDLabel;
    @FXML
    private Label modifyPartLabelName;
    @FXML
    private Label modifyPartInvLabel;
    @FXML
    private Label modifyPartMaxLabel;
    @FXML
    private Label modifyPartPriceLabel;
    @FXML
    private Label modifyPartMinLabel;
    @FXML
    private TextField modifyPartIDTextField;
    @FXML
    private TextField modifyPartNameTextField;
    @FXML
    private TextField modifyPartInvTestField;
    @FXML
    private TextField modifyPartPriceTextField;
    @FXML
    private TextField modifyPartMaxTextField;
    @FXML
    private TextField modifyPartMinTextField;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;
    @FXML
    private Label modifyPartMachineIdDLabel;
    @FXML
    private Label modifyPartCompanyNameLabel;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    /**Method is used in Initialize this controller. Toggle group as well for the user to switch between 2 types**/
    // Toggle is used to make sure user can only select 1 button.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        modifyPartInHouseButton.setToggleGroup(toggleGroup);
        modifyPartOutsourcedButton.setToggleGroup(toggleGroup);

    }


    /**Method changes the text of label when In house is selected for the user*
     * @param actionEvent
     **/
    public void onModInHouseButton(ActionEvent actionEvent) {
        modifyPartMachineIDLabel.setText("Machine ID");
    }

    /** Method changes the text of label when outsourced is selected. *
     * @param actionEvent
     **/
    public void onModOutsourcedButton(ActionEvent actionEvent) {
        modifyPartMachineIDLabel.setText("Company Name");
    }

    /**Method creates a New Part ID for Mod parts**/
    private int createNewPartID() { int highestExistingID = 0; return highestExistingID + 1; }

    /** This method allows for data to be passed from main controller and set the information in mod *
     * MY ERROR: I was creating a new ID for modified parts, so I kept getting a new part instead of an updated one,
     * I fixed this by setting the existing ID of the part!
     **/
    public void dataFromMainController(Part part) {


        modifyPartIDTextField.setText(String.valueOf(part.getId()));
        modifyPartIDTextField.setDisable(true);


        modifyPartMachineIDTextField.setText(String.valueOf(part.getId()));
        modifyPartNameTextField.setText(part.getName());
        modifyPartPriceTextField.setText(String.valueOf(part.getPrice()));
        modifyPartMaxTextField.setText(String.valueOf(part.getMax()));
        modifyPartInvTestField.setText(String.valueOf(part.getStock()));
        modifyPartMinTextField.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            modifyPartInHouseButton.setSelected(true);
            modifyPartMachineIDLabel.setText("Machine ID");
            modifyPartMachineIDTextField.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            modifyPartOutsourcedButton.setSelected(true);
            modifyPartMachineIDLabel.setText("Company Name");
            modifyPartMachineIDTextField.setText(((Outsourced) part).getCompanyName());
        }

    }

    /** Method saves the new modified information back to main table *
     * @param actionEvent
     **/
    public void onSaveButton(ActionEvent actionEvent) {
        String errorMessage = validateInput();

        if (errorMessage.isEmpty()) {
            try {
                int minQty = Integer.parseInt(modifyPartMinTextField.getText());
                int maxQty = Integer.parseInt(modifyPartMaxTextField.getText());

                if (minQty >= maxQty) {
                    showErrorAlertMod("Min should be less than max value", "Please fix and try again!");
                } else {
                    Part modPart = createModPart();
                    if (modPart != null) { goBackToMain(actionEvent);}
                }
            } catch (NumberFormatException e) {
                showErrorAlertMod("Invalid input in one or more text fields!", "Only enter numbers for (Inv,Price/Cost,Max/Min,Machine ID.");
            }
        } else {
            showErrorAlertMod("TextFields cannot be left empty!", errorMessage);
        }
    }
/** Method takes the user back to the Main GUI*
 * @param actionEvent
 **/
    private void goBackToMain(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainFormGUI.fxml"));
            Scene scene = new Scene(root);

            Stage stage = stageFromNode(actionEvent);
            settingSceneAndStage(stage, scene);
        } catch (IOException e) {
            e.printStackTrace();
    }

    }
/**Method creates a modified part depending on what button is selected and updates it to data/list**/
    private Part createModPart() {
        int partIndex =  Integer.parseInt(modifyPartIDTextField.getText());
        String name = modifyPartNameTextField.getText().trim();
        double price = Double.parseDouble(modifyPartPriceTextField.getText());
        int stock = Integer.parseInt(modifyPartInvTestField.getText());
        int max = Integer.parseInt(modifyPartMaxTextField.getText());
        int min = Integer.parseInt(modifyPartMinTextField.getText());

        if (stock < min || stock >max) {
            showErrorAlertMod("Invalid value for Inventory", "Inventory needs to be between Min and Max, Please try again!");
            return null;
        }

        if (modifyPartInHouseButton.isSelected()) {
            InHouse inHouse = new InHouse(
                    Integer.parseInt(modifyPartIDTextField.getText()), name,
                    Double.parseDouble(modifyPartPriceTextField.getText()),
                    Integer.parseInt(modifyPartInvTestField.getText()),
                    Integer.parseInt(modifyPartMinTextField.getText()),
                    Integer.parseInt(modifyPartMaxTextField.getText()),
                    Integer.parseInt(modifyPartMachineIDTextField.getText())
            );
            Inventory.updatePart(Integer.parseInt(modifyPartIDTextField.getText()), inHouse);
        } else {
            if (modifyPartOutsourcedButton.isSelected()) {
                String companyName = modifyPartMachineIDTextField.getText();

                Outsourced outsourced = new Outsourced(
                        Integer.parseInt(modifyPartIDTextField.getText()), name,
                        Double.parseDouble(modifyPartPriceTextField.getText()),
                        Integer.parseInt(modifyPartInvTestField.getText()),
                        Integer.parseInt(modifyPartMinTextField.getText()),
                        Integer.parseInt(modifyPartMaxTextField.getText()), companyName

                );
                Inventory.updatePart(Integer.parseInt(modifyPartIDTextField.getText()), outsourced);
            }

            return null;
        }
        return null;
    }
/**This method makes sure that no field is left empty and gives user an Alert**/
    private String validateInput () {
                if (modifyPartNameTextField.getText().isEmpty() || modifyPartInvTestField.getText().isEmpty() ||
                        modifyPartPriceTextField.getText().isEmpty() || modifyPartMinTextField.getText().isEmpty() ||
                        modifyPartMaxTextField.getText().isEmpty() || modifyPartMachineIDTextField.getText().isEmpty()) {
                    return "Please make sure a field is selected and try again";
                }
                return "";
            }
/**This method gives the user an error alert message*/
            private void showErrorAlertMod (String header, String content){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attempting to Mod a  Part");
                alert.setHeaderText(header);
                alert.setContentText(content);
                alert.showAndWait();
            }

            /** Method is called when the user clicks cancel and gives the path back to main controller GUI *
             * @param actionEvent
             **/
            public void onCancelButton (ActionEvent actionEvent) throws IOException {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFormGUI.fxml")));
                Scene scene = new Scene(root);

                Stage stage = stageFromNode(actionEvent);
                settingSceneAndStage(stage, scene);

            }
/** Method takes actionEvent from button click and returns stage *
 * @param actionEvent
 **/
            private Stage stageFromNode (ActionEvent actionEvent){
                Node sourceNode = (Node) actionEvent.getSource();
                return (Stage) sourceNode.getScene().getWindow();
            }
/** Method takes the stage and scene**/
            private void settingSceneAndStage (Stage stage, Scene scene){
                stage.setScene(scene);
                stage.setTitle("Inventory System");
                stage.show();
            }
        }













