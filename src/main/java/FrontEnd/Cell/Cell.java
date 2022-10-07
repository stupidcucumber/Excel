package FrontEnd.Cell;

import BackEnd.Parser.Lexer;
import BackEnd.Parser.Parser;
import javafx.scene.control.TextField;

public class Cell {
    private String dataValue;
    private String dataFormula;
    private TextField textField;
    private TextField formulaView;
    private Spreadsheet spreadsheet;

    public void recalculate(){
        Cell[][] cells = spreadsheet.getCells();

        Parser parser = new Parser(new Lexer(), spreadsheet);
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                if(!cells[i][j].getDataFormula().equals("")){
                    cells[i][j].setDataValue(String.valueOf(parser.evaluate(cells[i][j].getDataFormula())));
                    TextField temp = cells[i][j].getTextField();
                    temp.setText(cells[i][j].getValue());
                }
            }
        }
    }

    public Cell(TextField formulaView, Spreadsheet parent){
        spreadsheet = parent;
        this.formulaView = formulaView;
        dataValue = "";
        dataFormula = "";
        textField = new TextField(dataValue);
        textField.setPrefWidth(80);
        textField.setStyle("-fx-border-width: 1px;-fx-border-color: rgb(73,73,73)");

        textField.setOnAction(e -> {
            Parser parser = new Parser(new Lexer(), spreadsheet);
            dataFormula = textField.getText();
            System.out.println(dataFormula);
            dataValue = String.valueOf(parser.evaluate(dataFormula));
            formulaView.setText(dataFormula);
            System.out.println("Formula: " + dataFormula);
            System.out.println("Value: " + dataValue);
            textField.setText(dataValue);

            recalculate();
        });

        textField.setOnMouseClicked(e -> {
            formulaView.setText(dataFormula);
            textField.setText(dataFormula);
        });
    }

    public Cell(String value, String formula){
        dataValue = value;
        dataFormula = formula;

        textField = new TextField(dataValue);
        textField.setPrefWidth(80);
        textField.setStyle("-fx-border-width: 1px;-fx-border-color: rgb(73,73,73)");

        textField.setOnAction(e ->{
            dataFormula = textField.getText();
            System.out.println(dataFormula);
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
}
