package calculator;

import calculatorException.CalculatorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            Lexer lexer = new Lexer(reader);
            int result = new Parser(lexer).statement();

            System.out.println("\n Result " + result + "\n");
        } catch (Exception e){
            throw new CalculatorException("Error while calculating result.", e);
        }
    }
}
