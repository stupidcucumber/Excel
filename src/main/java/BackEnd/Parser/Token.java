package BackEnd.Parser;

import java.util.Objects;

public class Token {
    private TokenType tokenType;
    private String expression;
    public Token(){
        this.tokenType = TokenType.WHITESPACE;
        this.expression = "";
    }

    public Token(TokenType tokenType, String expression){
        this.tokenType = tokenType;
        this.expression = expression;
    }

    public String getExpression(){
        return expression;
    }

    public void setExpression(String string){
        this.expression = string;
    }

    public void addChar(char ch){
        StringBuilder stringBuilder = new StringBuilder(expression);
        stringBuilder.append(ch);
        this.expression = stringBuilder.toString();
    }

    public TokenType getTokenType(){
        return tokenType;
    }

    public void setTokenType(TokenType tokenType){
        this.tokenType = tokenType;
    }

    @Override
    public String toString(){
        return expression + "/" + tokenType.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return tokenType == token.tokenType && Objects.equals(expression, token.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, expression);
    }
}
