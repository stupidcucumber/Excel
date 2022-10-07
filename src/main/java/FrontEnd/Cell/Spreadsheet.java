package FrontEnd.Cell;

import javafx.scene.control.TextField;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spreadsheet {
    private final int DEFAULT_WIDTH = 10, DEFAULT_HEIGHT = 10;
    private List<List<Cell>> cells;
    private int width;
    private int height;
    private String name;

    private TextField formulaViewer;

    public Spreadsheet(String name, TextField formulaViewer){
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
        cells = new ArrayList<>();
        this.formulaViewer = formulaViewer;
        this.name = name;

        for(int i = 0; i < DEFAULT_HEIGHT; i++){
            List<Cell> cellList = new ArrayList<>();
            for(int j = 0; j < DEFAULT_WIDTH; j++){
                Cell cell = new Cell(formulaViewer, this);
                cellList.add(cell);
            }

            cells.add(cellList);
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
        return cells.get(i).get(j);
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

    public List<List<Cell>> getCells(){
        return cells;
    }

    public void recalculate(){

    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }
}
