package calculator;

import calculatorException.CalculatorException;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private int current;
    private @Getter int value;
    private final Reader input;

    public Lexer(BufferedReader input) {
        this.value = 0;
        this.input = input;
        consume();
    }

    private void resetValue(){ this.value = 0; }

    public Token nextToken(){ // NUMBER, EOF, RPAR, LPAR, DIV, MUL, MINUS, PLUS
        if(this.current < 48 || this.current > 57){
            resetValue();
        }

        switch(this.current){
            case 48,49,50,51,52,53,54,55,56,57:
                if(this.value == 0) {
                    this.value = this.current-48;
                } else {
                    this.value *= 10;
                    this.value += this.current-48;
                }
                consume();
                return Token.NUMBER;
            case '(':
                consume();
                return Token.LPAR;
            case ')':
                consume();
                return Token.RPAR;
            case '/':
                consume();
                return Token.DIV;
            case '*':
                consume();
                return Token.MUL;
            case '+':
                consume();
                return Token.PLUS;
            case '-':
                consume();
                return Token.MINUS;
            case 10:
            case -1:
                return Token.EOF;
            default:
                throw new CalculatorException("Neplatny znak");
        }

    }

    private void skipSpaces() throws IOException {
        while(this.current == 32){
            this.current = input.read();
        }
    }

    private void consume(){
        try {
            this.current = input.read();
            skipSpaces();
        } catch(IOException e){
            throw new CalculatorException("Error while reading user input", e);
        }
    }
}
