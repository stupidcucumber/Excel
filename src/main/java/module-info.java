module com.example.excel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.excel to javafx.fxml;
    exports com.example.excel;
}