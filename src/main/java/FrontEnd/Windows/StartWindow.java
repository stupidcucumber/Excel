package FrontEnd.Windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StartWindow {
    public static Stage window;

    public static void launch(){
        window = new Stage();
        window.setTitle("Start");

        FileChooser fileChooser = new FileChooser();

        Button newFile = new Button("New");
        Button openFile = new Button("Open");

        newFile.setStyle("-fx-graphic: url(new-file-big.png);-fx-content-display: bottom;");
        newFile.setOnAction(e -> {
            boolean created = false;
            try {
                String fileName = CreatingNewWindow.launch();
                if(!fileName.equals("")){
                    MainWindow.launch(new File(fileName + ".csv"), false);
                    created = true;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(created)
                window.close();
        });

        openFile.setStyle("-fx-graphic: url(documents-folder-big.png);-fx-content-display: bottom;");
        openFile.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(window);
            if(file != null){
                try {
                    MainWindow.launch(file, true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                window.close();
            }
        });

        BorderPane layout = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setStyle("-fx-background-color: rgb(51,51,51);");
        hBox.getChildren().addAll(newFile, openFile);
        layout.setCenter(hBox);

        Scene scene = new Scene(layout);

        window.setMinHeight(500);
        window.setMinWidth(1020);

        window.setScene(scene);
        window.show();
    }
}
