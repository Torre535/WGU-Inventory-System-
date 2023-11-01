package com.example.wguinventorysystem;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** THis class sets up the main interface the user will interact with *
 * @author David Torres
 * FUTURE Enhancement: I would breakdwon the complex methods into more simple ones. I would also create resualable error handling and alerts for the whole program.
 **/
public class MainFormGUI implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label mainLabel;

    @FXML
    private Pane mainPaneParts;

    @FXML
    private Label mainPartsLabel;

    @FXML
    private TextField mainPartsTextField;

    @FXML
    private TableView<Part> mainPartsTableView;

    @FXML
    private TableColumn mainPartsID;

    @FXML
    private TableColumn mainPartName;

    @FXML
    private TableColumn mainPartsInvLevel;

    @FXML
    private TableColumn mainPartsCostPerUnit;

    @FXML
    private Button mainPartsAddButton;

    @FXML
    private Button mainPartsModifyButton;

    @FXML
    private Button mainPartsDeleteButton;

    @FXML
    private Pane mainProductsPane;

    @FXML
    private Label mainProductsLabel;

    @FXML
    private TextField mainProductsTextField;

    @FXML
    private TableView<Product> mainProductsTableView;

    @FXML
    private TableColumn mainProductsID;

    @FXML
    private TableColumn mainProductName;

    @FXML
    private TableColumn mainProductsInvLevel;

    @FXML
    private TableColumn mainProductsPricePerUnit;

    @FXML
    private Button mainProductsAddButton;

    @FXML
    private Button mainProductsModifyButton;

    @FXML
    private Button mainProductDeleteButton;

    @FXML
    private Button mainExitButton;


//Imports the Stage and Scene for switching off the main pop up window
    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartsTableView.setItems(Inventory.getAllParts());
        mainProductsTableView.setItems(Inventory.getAllProducts());

   // Main Parts Table -> Setting Columns
       mainPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
       mainPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
       mainPartsInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
       mainPartsCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

    // Main Products Table -> Setting Columns
      mainProductsID.setCellValueFactory(new PropertyValueFactory<>("id"));
      mainProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
      mainProductsInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
      mainProductsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
/** on SearchByIDOrPartName method is a search function in which the user types in a String Part Name or An Int Part ID,  *
 * @param actionEvent
 **/
    public void onSearchByIdOrPartName(ActionEvent actionEvent) {
     //Gathers the users input from the text field on GUI via String
        String userSearchInput = mainPartsTextField.getText().trim();

        // Makes sure the data is repopulated when search field is empty
        if (userSearchInput.isEmpty()) {
            mainPartsTableView.setItems(Inventory.getAllParts());
            return;
        }

      // Uses the lookupPart method stored in the Inventory Class to search against users input
        ObservableList<Part> part = Inventory.lookupPart(userSearchInput);

      //Since the above only Searches String we need to have an ID (Int) look up.
        if (part.isEmpty()) {
            try {
                int partID = Integer.parseInt(userSearchInput);
                Part foundPartID = Inventory.lookupPart(partID);
                if (foundPartID != null) {
                    part.add(foundPartID);
                } else {
                   noPartFoundAlert();
                   return;

                }
            } catch (NumberFormatException e) {
                noPartFoundAlert();
                return;
            }

        }
        //if there is a search match it will set the item to the table as a result
        /** I placed mainPartsTableView.setItems(part) at the end of the first String Search so the results of the Integer Search never showed up. Has to be at the end for it to search both*/
        mainPartsTableView.setItems(part);
       }

/** This method is used as an alert to the user that their search was not successful*/
// this method is used when the search cant find a match, it gives the users an Alert.
    public void noPartFoundAlert () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Part Found");
        alert.setHeaderText("No Match was Found");
        alert.setContentText("Please Try Again, Search Is Case Sensitive!");
        alert.show();
    }
/**The onSearchProductByIDOrName method takes in users input via TextField and searches it in list of items. First takes in String and then an Int, and if nothing found will give alert*
 * @param actionEvent
 **/
    public void onSearchProductByIdOrName(ActionEvent actionEvent) {
       // Gathers the users input from the text field on GUI via String
        String userProductSearchInput = mainProductsTextField.getText().trim();

        // Makes sure the data is repopulated when search field is empty
        if (userProductSearchInput.isEmpty()) {
            mainProductsTableView.setItems(Inventory.getAllProducts());
            return;
        }

       //Uses the Lookup part Method stored in the Inventory Class to search against users input via String
        ObservableList<Product> product = Inventory.lookupProduct(userProductSearchInput);

      //Since the above only searches using a string we need to search via Int if no result found
        if (product.isEmpty()) {
            try {
                int productID = Integer.parseInt(userProductSearchInput);
                Product foundProductID = Inventory.lookupProduct(productID);
                if(foundProductID != null) {
                    product.add(foundProductID);
                } else {
                    noProductFoundAlert();
                }
            } catch (NumberFormatException e) {
                noProductFoundAlert();
            }
        }
        mainProductsTableView.setItems(product);
        }
/**This is an alert for when the search is not successful when looking for a product */
    //this method is used when the search cant find a match, it gives the users an Alert.
    public void noProductFoundAlert () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Product Found");
        alert.setHeaderText("No Match was Found");
        alert.setContentText("Please Try Again, Search Is Case Sensitive!");
        alert.show();
    }

    /**This method allows for a new part to be added to list and table to view*
     * @param actionEvent
     **/
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPartFormGUI.fxml"));
        Parent root = fxmlLoader.load();

     AddPartFormGUI addPartFormGUI = fxmlLoader.getController();

       Stage stage = new Stage();
       Scene scene = new Scene(root);

       stage.setScene(scene);
       stage.setTitle("Add Part");
       stage.show();

    }
/**This method allows for a new product to be added to the products table and list*
 * @param actionEvent
 **/
    public void onAddProducts(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProductFormGUI.fxml"));
        Parent root = fxmlLoader.load();

        AddProductFormGUI addProductFormGUI = fxmlLoader.getController();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Add Product");
        stage.show();

    }
    /**This method allows for a part to be modified, opens up the new GUI for mod*
     * @param actionEvent
     **/
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyPartFormGUI.fxml"));
        Parent root = fxmlLoader.load();

        ModifyPartFormGUI modifyPartFormGUI = fxmlLoader.getController();

        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();

        if (part == null) {
            DisplayErrorAlert("No Part or Parts Selected", "Try Selecting A Part Again!");
        } else {
            modifyPartFormGUI.dataFromMainController(part);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modify Part");
            stage.show();
        }
    }
    /**Method allows for a pop up for an error alert **/
    private void DisplayErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Modify a Part");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**This allows to modify products GUI to pop up and for the user to mod a product and save*
     * @param actionEvent
     **/
    public void onModifyProducts(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductFormGUI.fxml"));
        Parent root = loader.load();

        ModifyProductFormGUI modifyProductFormGUI = loader.getController();

        Product product = mainProductsTableView.getSelectionModel().getSelectedItem();

        if (product == null) {
            DisplayErrorAlert2("No Product or Products Selected", "Please try again!");
        } else {
            modifyProductFormGUI.dataFromMainControllerForProduct(product);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modify Product");
            stage.show();
        }
    }

    /**Method displays an error to the user**/
    private void DisplayErrorAlert2(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Modify a Product");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**Method Deletes a part from table and data*
     * @param actionEvent
     **/
    public void onDeletePart(ActionEvent actionEvent) {
        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Parts or Part Detected");
            alert.setContentText("Please retry selecting a part");
            alert.showAndWait();
        } else {
            Alert alertNumber2 = new Alert(Alert.AlertType.CONFIRMATION);
            alertNumber2.setTitle("User CONFIRMATION");
            alertNumber2.setContentText("Are you positive you want to delete this part?");

            Optional<ButtonType> result = alertNumber2.showAndWait();
            if(result.get()== ButtonType.OK)  {
                Inventory.deletePart(part);
            }
        }

    }
    /**This method deletes product from table and from data*
     * @param actionEvent
     **/
    public void onDeleteProducts(ActionEvent actionEvent) {
        Product product = mainProductsTableView.getSelectionModel().getSelectedItem();

        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Products or Product Detected");
            alert.setContentText("Please retry selecting a product");
            alert.showAndWait();
        } else {
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            alert3.setTitle("User CONFIRMATION");
            alert3.setContentText("Are you positive you want to delete this product?");

            Optional<ButtonType> result = alert3.showAndWait();
            if(result.get() == ButtonType.OK) {
                if (product.getAssociatedParts().size() == 0) {
                    Inventory.deleteProduct(product);
                }
                 else {
                     Alert alert4 = new Alert(Alert.AlertType.ERROR);
                     alert4.setTitle("Attempting to Delete Product");
                     alert4.setHeaderText("You cannot delete a product that has a part associated with it");
                     alert4.setContentText("Make sure you Modify product and remove associated part");
                     alert4.showAndWait();
                }
                }

            }
        }
    /** Closes the Application when the user hits the Exit Button*
     * @param actionEvent
     **/
    public void openExitApplicationButton(ActionEvent actionEvent) {
        System.exit(0);

    }


    }




























