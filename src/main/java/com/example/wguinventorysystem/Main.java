package com.example.wguinventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/** Class allows for the Main Form GUI to open up to the user *
 * @author David Torres
 **/
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainFormGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 500);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();
    }
       /** This is the main method that hold sample data for the Inventory System**/
    public static void main(String[] args) {

          // Testing Data for table
            InHouse inHouse = new InHouse(Inventory.partIndex, "Brakes", 32,23,1,25,1);
            Inventory.addPart(inHouse);

            InHouse inHouse2 = new InHouse(Inventory.partIndex, "Wheel", 23,2,1,3,2);
            Inventory.addPart(inHouse2);

            Outsourced outsourced = new Outsourced(Inventory.partIndex,"Seat", 24,43,2,50,"Hot Wheels");
            Inventory.addPart(outsourced);

          // Testing Data for table

            Product product = new Product(Inventory.productIndex,"Giant Bike", 232.32, 2, 1, 3);
            Inventory.addProduct(product);

            Product product2 = new Product(Inventory.productIndex, "Tricycle", 65,6,1,10);
            Inventory.addProduct(product2);


        launch(args);
    }
}