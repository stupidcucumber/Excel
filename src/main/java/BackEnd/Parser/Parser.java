package BackEnd.Parser;

import FrontEnd.Cell.Cell;
import FrontEnd.Cell.Spreadsheet;

import java.util.List;

public class Parser {
    private Lexer lexer;
    private int pointer;

    Spreadsheet spreadsheet;

    public Parser(){
        pointer = 0;
    }

    public Parser(Lexer lexer, Spreadsheet spreadsheet){
        this.spreadsheet = spreadsheet;
        this.lexer = lexer;
    }

    public double evaluate(String expression){
        pointer = 0;
        lexer.consumeExpression(expression);
        List<Token> tokens = lexer.defineTokens();
        return addition();
    }

    public double addition(){
        double result = multiplication();
        Token tempToken = lexer.getToken(pointer);

        if(tempToken.getTokenType() == TokenType.ADDITION){
            pointer++;
            result = result + multiplication();
        }else if(tempToken.getTokenType() == TokenType.SUBTRACTION){
            pointer++;
            result = result - multiplication();
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
        Parser parser = new Parser(new Lexer(), spreadsheet);
        return parser.evaluate(cell.getDataFormula());
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
            pointer++;
            System.out.println(lexer);
            return calculateCell(spreadsheet.getCell(token.getExpression()));
        }else{
            System.out.println(lexer);
                throw new RuntimeException("Expression is invalid! " + token.getTokenType());
        }
    }
}