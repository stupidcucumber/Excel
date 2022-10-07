package FrontEnd.Windows;

import FrontEnd.Cell.Cell;
import FrontEnd.Cell.Spreadsheet;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class MainWindow {
    private static Stage window;

    public static void launch(File file){
        window = new Stage();
        window.setMinWidth(900);
        window.setMinHeight(600);

        BorderPane root = new BorderPane();
        VBox table = new VBox();
        VBox top = new VBox();
        HBox top_1 = new HBox();
        HBox top_2 = new HBox();

        top.setStyle("-fx-padding: 5px;-fx-background-color: rgb(93,93,93);");
        TextField formulaViewer = new TextField();
        formulaViewer.setStyle("-fx-min-width: 400px;-fx-padding: 3px;");
        top_2.getChildren().add(formulaViewer);

        Button save = new Button();
        save.setStyle("-fx-graphic: url(save-file.png); -fx-background-color: transparent;");
        save.setOnMouseEntered(e -> {
            save.setStyle("-fx-graphic: url(save-file.png);-fx-background-color: rgb(173,173,173);");
        });
        save.setOnMouseExited(e -> {
            save.setStyle("-fx-graphic: url(save-file.png);-fx-background-color: transparent;");
        });
        top_1.getChildren().add(save);

        top.getChildren().addAll(top_1, top_2);

        Spreadsheet spreadsheet = new Spreadsheet("Test", formulaViewer);
        window.setTitle(spreadsheet.getName());

        drawCells(spreadsheet, table);

        root.setCenter(table);
        root.setTop(top);

        Scene scene = new Scene(root);
        window.setScene(scene);

        window.show();
    }

    public static void drawCells(Spreadsheet spreadsheet, VBox layout){
        Cell[][] cells = spreadsheet.getCells();

        for(int i = -1; i < 10; i++){
            HBox row = new HBox();
            if(i > -1){
                Label label = new Label((i + 1) + "");
                label.setStyle("-fx-pref-width: 20px;-fx-alignment: center;");
                row.getChildren().add(label);
                for(int j = 0; j < cells[0].length; j++){
                    row.getChildren().add(cells[i][j].getTextField());
                }
            }else{
                int width = (int) cells[0][0].getTextField().getPrefWidth();

                for(int j = 0; j < cells[0].length; j++){
                    Label label = new Label(Spreadsheet.toLiteral(j));
                    label.setMinWidth(width);
                    row.getChildren().add(label);
                }

                row.setStyle("-fx-alignment: center");
            }


            layout.getChildren().add(row);
        }


    }
}
