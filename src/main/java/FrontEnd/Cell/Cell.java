package FrontEnd.Cell;

import javafx.scene.control.TextField;

public class Cell {
    private String data;
    private TextField textField;

    public Cell(){
        data = "";
        textField = new TextField(data);
    }

    public void setData(){

    }

    public String getData(){
        return data;
    }
}
