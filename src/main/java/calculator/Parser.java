package calculator;

import calculatorException.CalculatorException;

public class Parser {
    private Token symbol;
    private final Lexer lexer;
    private int lParCount = 0;
    private int rParCount = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int statement(){
        consume();
        return expr();
    }

    private void testForIllegalRPar(){
        if(this.symbol == Token.RPAR){
            this.rParCount++;
            if(this.lParCount < this.rParCount) {
                throw new CalculatorException("Error you can't enter closing parenthesis without the opening one");
            }
        }
    }

    private void testForIllegalLPar(){
        if(this.symbol == Token.LPAR){
            throw new CalculatorException("Error you can't enter '(' like this");
        }
    }

    private int extractNumber(){
        int num = this.lexer.getValue();
        while(this.symbol == Token.NUMBER){
            match(Token.NUMBER);
            num = this.lexer.getValue();
            consume();
            testForIllegalLPar();
            testForIllegalRPar();
        }
        return num;
    }

    private int en(){   //      En -> number | "("Expr")"
        int val;
        switch (this.symbol){
            case NUMBER:
                return extractNumber();
            case LPAR:
                this.lParCount++;
                consume();
                val = expr();
                match(Token.RPAR);
                consume();
                testForIllegalLPar();
                testForIllegalRPar();
                return  val;
            case RPAR:
                throw new CalculatorException("Error you can't enter closing parenthesis without the opening one");
            case EOF:
                return this.lexer.getValue();
            default:
                throw new CalculatorException("Error wrong input");
        }
    }

    private int umin(){ //    Umin -> ["-"] En
        if(this.symbol == Token.MINUS){
            consume();
            return en() * (-1);
        } else {
            return en();
        }
    }

    private int mul(){  //      Mul-> Umin{"*" | "/" Umin}
        int value = umin();

        while(this.symbol == Token.MUL || this.symbol == Token.DIV) {
            switch (symbol){
                case MUL:
                    consume();
                    value *= umin();
                    break;
                case DIV:
                    consume();
                    value /= umin();
                    break;
            }
        }
        return value;
    }

    private int expr(){ //    Expr -> Mul {"+" | "-" Mul}
        int value = mul();
        while(this.symbol == Token.PLUS || this.symbol == Token.MINUS){
            switch (symbol){
                case PLUS:
                    consume();
                    value += mul();
                    break;
                case MINUS:
                    consume();
                    value -= mul();
                    break;
            }
        }
        return value;
    }

    private void match(Token expectedSymbol){
        if(this.symbol != expectedSymbol){
            throw new CalculatorException("Error symbol mismatch! Expected: " + expectedSymbol);
        }
    }

    private void consume(){
        this.symbol = this.lexer.nextToken();
    }
}