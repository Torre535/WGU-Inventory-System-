module com.example.wguinventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wguinventorysystem to javafx.fxml;
    exports com.example.wguinventorysystem;
}