package FrontEnd.Cell;

import BackEnd.Parser.Lexer;
import BackEnd.Parser.Parser;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private String dataValue;
    private String dataFormula;
    private final TextField textField;
    private final List<Cell> getAddressedBy;
    private static Spreadsheet spreadsheet;
    public Cell(){
        getAddressedBy = new ArrayList<>();

        dataValue = "";
        dataFormula = "";
        textField = new TextField(dataValue);
        textField.setPrefWidth(80);
        textField.setStyle("-fx-border-width: 1px;-fx-border-color: rgb(73,73,73)");

        TextField formulaView = spreadsheet.getFormulaViewer();
        textField.setOnAction(e -> {

        });

        textField.setOnMouseClicked(e -> {
            formulaView.setText(dataFormula);
            textField.setText(dataFormula);
        });
    }

    public Cell(String value, String formula){
        getAddressedBy = new ArrayList<>();

        dataValue = value;
        dataFormula = formula;

        textField = new TextField(dataValue);
        textField.setPrefWidth(80);
        textField.setStyle("-fx-border-width: 1px;-fx-border-color: rgb(73,73,73)");

        textField.setOnAction(e -> {

            Parser parser = new Parser(new Lexer(), spreadsheet, this);
            dataFormula = textField.getText();
            try{
                textField.setText(String.valueOf(parser.evaluate()));
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });


        textField.setOnMouseClicked(e -> {
            spreadsheet.getFormulaViewer().setText(dataFormula);
            textField.setText(dataFormula);
        });
    }

    public TextField getTextField(){
        return textField;
    }

    public String getValue(){
        return dataValue;
    }
    public String getDataFormula(){return dataFormula;}
    public void setDataValue(String dataValue){this.dataValue = dataValue;}
    public void setDataFormula(String dataFormula){this.dataFormula = dataFormula;}

    public static Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    public static void setSpreadsheet(Spreadsheet spreadsheet){
        Cell.spreadsheet = spreadsheet;
    }
    public List<Cell> getGetAddressedBy() {
        return getAddressedBy;
    }
}
