module com.example.excel {
    requires javafx.controls;
    requires javafx.fxml;
    requires antlr;


    opens com.example.excel to javafx.fxml;
    exports com.example.excel;
}