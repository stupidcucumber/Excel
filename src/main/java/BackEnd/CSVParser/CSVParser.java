package BackEnd.CSVParser;

import FrontEnd.Cell.Cell;
import FrontEnd.Cell.Spreadsheet;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private final File file;

    public CSVParser(File file) throws IOException {
        this.file = file;
    }

    public Spreadsheet csvToSpreadsheet(TextField formula) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file));
        Spreadsheet spreadsheet = new Spreadsheet(file.getName(), formula);
        TextField formulaView = spreadsheet.getFormulaViewer();
        List<List<Cell>> cells = spreadsheet.getCells();
        cells.clear();
        String[] row = reader.readNext();
        spreadsheet.setWidth(row.length);
        spreadsheet.setHeight(1);
        while(row != null){
            List<Cell> cellList = new ArrayList<>();
            for(String token : row){
                cellList.add(cellFromToken(token, formulaView));
            }
            cells.add(cellList);
            row = reader.readNext();
            if(row != null)
                spreadsheet.setHeight(spreadsheet.getHeight() + 1);
        }

        reader.close();

        return spreadsheet;
    }


    public void spreadsheetToCSV(Spreadsheet spreadsheet) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(file, false));
        for(List<Cell> cellList : spreadsheet.getCells()){
            String[] row = new String[cellList.size()];
            for(int i = 0; i < cellList.size(); i++){
                row[i] = tokenFromCell(cellList.get(i));
            }

            writer.writeNext(row);
        }

        writer.close();
    }

    public String tokenFromCell(Cell cell){
        return cell.getDataFormula() + "=" + cell.getValue();
    }

    public Cell cellFromToken(String token, TextField formulaView){
        if(token.strip().equals("=")){
            return new Cell("", "");
        }
        String[] data = token.split("="); // "dataFormula"="dataValue"
        return new Cell(data[1], data[0]);
    }
}
