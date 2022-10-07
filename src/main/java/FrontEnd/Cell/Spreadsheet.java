package FrontEnd.Cell;

import FrontEnd.Cell.Cell;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spreadsheet {
    private final int DEFAULT_WIDTH = 10, DEFAULT_HEIGHT = 10;
    private Cell[][] cells;
    private String name;

    private TextField formulaViewer;

    public Spreadsheet(String name, TextField formulaViewer){
        this.formulaViewer = formulaViewer;
        this.name = name;
        cells = new Cell[DEFAULT_WIDTH][DEFAULT_HEIGHT];

        for(int i = 0; i < DEFAULT_HEIGHT; i++){
            for(int j = 0; j < DEFAULT_WIDTH; j++){
                cells[i][j] = new Cell(formulaViewer, this);
            }
        }
    }

    public Spreadsheet(File file){

    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    /**
     *
     * @param i – row
     * @param j – column
     * @return Cell
     */
    public Cell getCell(int i, int j){
        return cells[i][j];
    }

    public Cell getCell(String cell){
        Cell currentCell = null;

        Pattern literalPart = Pattern.compile("[A-B]*");
        Pattern digitPart = Pattern.compile("\\d+");

        Matcher literal = literalPart.matcher(cell);
        Matcher number = digitPart.matcher(cell);

        if(number.find() && literal.find()){
            String y_coordinate = number.group();
            String x_coordinate = literal.group();
            System.out.println(x_coordinate);
            System.out.println(y_coordinate);

            currentCell = getCell(Integer.parseInt(y_coordinate) - 1, toInteger(x_coordinate));
        }

        return currentCell;
    }

    public static int toInteger(String literal){
        int result = 0;
        int literalLength = literal.length();

        for(int i = 0; i < literalLength; i++){
            result += (literal.charAt(i) - 'A' + 1) * Math.pow(26, literalLength - i - 1);
        }

        return result - 1;
    }

    public static String toLiteral(int index){
        StringBuilder sb = new StringBuilder();
        index++;
        while(index > 0){
            index--;
            sb.append( (char) (index % 26 + 'A'));
            index /= 26;
        }

        return sb.reverse().toString();
    }

    public Cell[][] getCells(){
        return cells;
    }
}
