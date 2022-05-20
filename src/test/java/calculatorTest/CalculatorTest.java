package calculatorTest;

import calculator.Lexer;
import calculator.Parser;
import calculatorException.CalculatorException;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class CalculatorTest {

    private int calculateUserInput(final String s) {
        final BufferedReader reader = new BufferedReader(new StringReader(s));
        final Lexer lexer = new Lexer(reader);
        final Parser parser = new Parser(lexer);
        return parser.statement();
    }

    @Test
    public void testDoublePlusException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2+(+2)"));
    }

    @Test
    public void testRParWithoutLParException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2)+2"));
    }

    @Test
    public void testLParAfterNumberException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2(*2"));
    }

    @Test
    public void testLParAfterRParException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2*(2)("));
    }

    @Test
    public void testRParAfterNumberException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2)+2"));
    }

    @Test
    public void testDoubleMinusException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("--5+3"));
    }

    @Test
    public void testMultiplyDivisionException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2*/5"));
    }

    @Test
    public void testMinusPlusException(){
        assertThrows(CalculatorException.class, () -> this.calculateUserInput("2-+5"));
    }

    @Test
    public void testMultiplyMinus_valid(){
        assertEquals(this.calculateUserInput("5*(-2)"), -10);
    }

    @Test
    public void testWhiteSpaces_valid(){
        assertEquals(this.calculateUserInput("(2 + 2 ) *   (  2)"), 8);
    }

    @Test
    public void testLongExample_valid(){
        assertEquals(this.calculateUserInput("(10+(6 *4-(10 /2)/5-( 12/(7+5) ) ) ) /2"), 16);
    }
}