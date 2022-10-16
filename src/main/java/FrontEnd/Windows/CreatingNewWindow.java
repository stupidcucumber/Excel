package FrontEnd.Windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreatingNewWindow {
    public static String launch(){
        Stage stage = new Stage();
        stage.setTitle("Creating new Spreadsheet");

        HBox pane = new HBox();
        Label label = new Label("Enter file name: ");
        label.setStyle("-fx-text-fill: rgb(200,200,200);");
        TextField textField = new TextField();
        pane.getChildren().addAll(label, textField);
        pane.setStyle("-fx-background-color: rgb(51,51,51)");
        pane.setAlignment(Pos.CENTER);

        Button next = new Button("Create");
        next.setOnMouseClicked(e -> {
            stage.close();
        });

        pane.getChildren().add(next);

        Scene scene = new Scene(pane);
        stage.setMinWidth(600);
        stage.setMinHeight(200);

        stage.setScene(scene);
        stage.showAndWait();

        return textField.getText();
    }
}
