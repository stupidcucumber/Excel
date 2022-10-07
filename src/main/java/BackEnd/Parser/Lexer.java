package BackEnd.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Lexer {
    private List<Token> tokens = new ArrayList<>();
    private Token currentToken;
    //private Pattern cell_pattern = Pattern.compile("[A-B]+");
    public Lexer(){

    }

    private void divideToUT(String expression){ // Divide to undefined tokens

        if(!tokens.isEmpty())
            tokens.clear();

        int braces = 0;

        currentToken = new Token();
        currentToken.setTokenType(TokenType.WHITESPACE);

        for(char temp : expression.toCharArray()){
            if(Character.isDigit(temp)){
                if(currentToken.getTokenType() == TokenType.INTEGER){
                    currentToken.addChar(temp);
                }else if(currentToken.getTokenType() == TokenType.CHARACTER
                        || currentToken.getTokenType() == TokenType.CELL){
                    currentToken.addChar(temp);
                    currentToken.setTokenType(TokenType.CELL);
                }else{
                    EOT();
                    currentToken.setTokenType(TokenType.INTEGER);
                    currentToken.addChar(temp);
                }
            }else if(isOperator(temp)){
                EOT();
                currentToken.addChar(temp);
                currentToken.setTokenType(TokenType.OPERATOR);
                EOT();
            }else if(temp == ')' || temp == '('){
                if(temp == '(')
                    braces++;
                else if(braces > 0)
                    braces--;
                else
                    throw new RuntimeException("Braces are");

                EOT();
                currentToken.setTokenType(TokenType.BRACKET);
                currentToken.addChar(temp);
                EOT();
            }else{
                if(Character.isLetter(temp)){
                    if(currentToken.getTokenType() != TokenType.WHITESPACE &&
                            currentToken.getTokenType() != TokenType.CHARACTER)
                        throw new RuntimeException("That is not right");

                    currentToken.setTokenType(TokenType.CHARACTER);
                    currentToken.addChar(temp);
                }else if(temp != ' ' && temp != '\t' && temp != '\n'){
                    throw new RuntimeException("That is not right");
                }
            }
        }
        EOT();

        if(braces > 0)
            throw new RuntimeException("Expression is invalid!");
    }
    private void defineTokenOperator(Token token){
        switch (token.getExpression()) {
            case "+" -> token.setTokenType(TokenType.ADDITION);
            case "-" -> token.setTokenType(TokenType.SUBTRACTION);
            case "/" -> token.setTokenType(TokenType.DIVIDE);
            case "^" -> token.setTokenType(TokenType.POWER);
            default -> token.setTokenType(TokenType.MULTIPLY);
        }
    }
    private void defineTokenCharacter(Token token){
        if ("inc".equals(token.getExpression())) {
            token.setTokenType(TokenType.INCREMENT);
        }else if("dec".equals(token.getExpression())){
            token.setTokenType(TokenType.DECREMENT);
        }else if(token.getExpression().matches("[A-Z]*[0-9]*")) {
            token.setTokenType(TokenType.CELL);
        }else{
                throw new RuntimeException("Unknown expression!");
        }
    }
    public List<Token> defineTokens(){
        for (Token token : tokens) {
            if (token.getTokenType() == TokenType.OPERATOR) {
                defineTokenOperator(token);
            }else if(token.getTokenType() == TokenType.CHARACTER){
                defineTokenCharacter(token);
            }
        }

        return tokens;
    }
    public void consumeExpression(String expression){
            divideToUT(expression);
            defineTokens();
            tokens.add(new Token(TokenType.WHITESPACE, ""));
    }
    private boolean isOperator(char ch){
        char[] operators = {'-', '+', '/', '*', '^'};

        for (char operator : operators)
            if (ch == operator)
                return true;

        return false;
    }
    private void EOT(){ // end of token
        if(currentToken.getTokenType() != TokenType.WHITESPACE){
            tokens.add(new Token(currentToken.getTokenType(), currentToken.getExpression()));
        }

        currentToken.setExpression("");
        currentToken.setTokenType(TokenType.WHITESPACE);
    }

    public Token getToken(int index){
        return tokens.get(index);
    }

    @Override
    public String toString(){
        return tokens.toString();
    }
}
