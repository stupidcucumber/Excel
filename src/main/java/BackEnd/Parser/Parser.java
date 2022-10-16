package BackEnd.Parser;

import FrontEnd.Cell.Cell;
import FrontEnd.Cell.Spreadsheet;
import javafx.scene.control.TextField;

import java.util.*;

public class Parser {
    private final Lexer lexer;
    private final Cell cell;
    private int pointer;
    private static final Deque<Token> deque = new ArrayDeque<>();

    Spreadsheet spreadsheet;

    public Parser(Lexer lexer, Spreadsheet spreadsheet, Cell cell){
        this.cell = cell;
        this.spreadsheet = spreadsheet;
        this.lexer = lexer;
    }

    public double evaluate(){
        String expression = cell.getDataFormula();
        pointer = 0;
        lexer.consumeExpression(expression);

        double result = -1;
        try{
            result = addition();
        }catch (Exception e){
            System.out.println(e.getMessage());
            cell.getTextField().setText("#ERROR");
            cell.setDataValue("#ERROR");
        }

        for(Cell cell : cell.getGetAddressedBy()){
            Parser parser = new Parser(new Lexer(), Cell.getSpreadsheet(), cell);
            cell.setDataValue(String.valueOf(evaluate()));
            cell.getTextField().setText(cell.getValue());
        }
        return result;
    }

    public double addition(){
        double result = multiplication();
        Token tempToken = lexer.getToken(pointer);

        if(tempToken.getTokenType() == TokenType.ADDITION){
            pointer++;
            result = result + addition();
        }else if(tempToken.getTokenType() == TokenType.SUBTRACTION){
            pointer++;
            result = result - addition();
        }

        return result;
    }

    public double multiplication(){
        double result = power();

        Token tempToken = lexer.getToken(pointer);
        if(tempToken.getTokenType() == TokenType.MULTIPLY){

            pointer++;
            result *= multiplication();
        }else if(tempToken.getTokenType() == TokenType.DIVIDE){

            pointer++;
            result /= multiplication();
        }

        return result;
    }

    public double power(){
        double result = unary();
        Token tempToken = lexer.getToken(pointer);
        if(tempToken.getTokenType() == TokenType.POWER){
            pointer++;
            return Math.pow(result, unary());
        }

        return result;
    }

    public double unary(){
        Token tempToken = lexer.getToken(pointer);
        if(tempToken.getTokenType() == TokenType.ADDITION){
            pointer++;
            return priorityChanger();
        }else if(tempToken.getTokenType() == TokenType.SUBTRACTION){
            pointer++;
            return (-1) * priorityChanger();
        }

        return priorityChanger();
    }

    public double calculateCell(Cell cell){
        Parser parser = new Parser(new Lexer(), spreadsheet, cell);
        return parser.evaluate();
    }

    public double priorityChanger(){
        Token token = lexer.getToken(pointer);

        if(token.getTokenType() == TokenType.INTEGER){
            pointer++;
            return Integer.parseInt(token.getExpression());
        }else if(token.getTokenType() == TokenType.BRACKET) {
            pointer++;
            double result = addition();
            pointer++;
            return result;
        }else if(token.getTokenType() == TokenType.DECREMENT) {
            pointer++;
            return addition() - 1;
        }else if(token.getTokenType() == TokenType.INCREMENT){
            pointer++;
            return addition() + 1;
        }else if(token.getTokenType() == TokenType.CELL){
            if(deque.contains(token))
                throw new RuntimeException("Parser addresses the cell which is under an evaluation");

            deque.push(token);
            pointer++;
            System.out.println(lexer);
            double result = calculateCell(spreadsheet.getCell(token.getExpression()));
            spreadsheet.getCell(token.getExpression()).getGetAddressedBy().add(cell);
            deque.pop();

            return result;
        }else{
            System.out.println(lexer);
                throw new RuntimeException("Expression is invalid! " + token.getTokenType());
        }
    }

    public static void clearDeque(){
        deque.clear();
    }
}