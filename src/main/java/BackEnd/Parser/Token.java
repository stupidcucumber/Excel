package BackEnd.Parser;

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
}
