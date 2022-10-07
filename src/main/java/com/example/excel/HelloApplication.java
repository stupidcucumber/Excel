package com.example.excel;

import FrontEnd.Windows.MainWindow;
import FrontEnd.Windows.StartWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StartWindow.launch();
    }

    public static void main(String[] args) {
        launch();
    }
}