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
import java.util.ArrayList;
import java.util.List;

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

        Button addRowButton = new Button("Add Row");
        addRowButton.setOnMouseClicked(e -> {
            addRow(spreadsheet, table);
        });

        Button deleteRowButton = new Button("Delete row");
        deleteRowButton.setOnMouseClicked(e -> {
            if(spreadsheet.getHeight() > 1)
                deleteRow(spreadsheet, table);
        });

        top_1.getChildren().addAll(addRowButton, deleteRowButton);

        drawCells(spreadsheet, table);

        root.setCenter(table);
        root.setTop(top);

        Scene scene = new Scene(root);
        window.setScene(scene);

        window.show();
    }

    public static void addRow(Spreadsheet spreadsheet, VBox layout){
        List<List<Cell>> cells = spreadsheet.getCells();

        List<Cell> newRow = new ArrayList<>();
        for(int i = 0; i < spreadsheet.getWidth(); i++){
            newRow.add(new Cell(new TextField(), spreadsheet));
        }

        cells.add(newRow);
        layout.getChildren().clear();
        spreadsheet.setHeight(spreadsheet.getHeight() + 1);
        drawCells(spreadsheet, layout);
    }

    public static void addColumn(Spreadsheet spreadsheet, VBox layout){
    }

    public static void deleteRow(Spreadsheet spreadsheet, VBox layout){
        List<List<Cell>> cells = spreadsheet.getCells();


        layout.getChildren().clear();
        cells.remove(cells.size() - 1);
        spreadsheet.setHeight(spreadsheet.getHeight() - 1);
        drawCells(spreadsheet, layout);
    }

    public static void deleteColumn(Spreadsheet spreadsheet){

    }

    public static void drawCells(Spreadsheet spreadsheet, VBox layout){
        List<List<Cell>> cells = spreadsheet.getCells();

        for(int i = -1; i < spreadsheet.getHeight(); i++){
            HBox row = new HBox();
            if(i > -1){
                Label label = new Label((i + 1) + "");
                label.setStyle("-fx-pref-width: 20px;-fx-alignment: center;");
                row.getChildren().add(label);

                List<Cell> cellList = spreadsheet.getCells().get(i);
                for (Cell cell : cellList) {
                    row.getChildren().add(cell.getTextField());
                }
            }else{
                int width = (int) cells.get(0).get(0).getTextField().getPrefWidth();

                for(int j = 0; j < cells.get(0).size(); j++){
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
