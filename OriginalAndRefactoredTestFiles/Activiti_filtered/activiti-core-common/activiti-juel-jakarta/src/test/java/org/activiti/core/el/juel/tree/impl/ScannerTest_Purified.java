package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.Arrays;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;

public class ScannerTest_Purified extends TestCase {

    void assertEquals(Object[] a1, Object[] a2) {
        assertTrue(Arrays.equals(a1, a2));
    }

    Scanner.Symbol[] symbols(String expression) throws Scanner.ScanException {
        ArrayList<Scanner.Symbol> list = new ArrayList<Scanner.Symbol>();
        Scanner scanner = new Scanner(expression);
        Scanner.Token token = scanner.next();
        while (token.getSymbol() != Scanner.Symbol.EOF) {
            list.add(token.getSymbol());
            token = scanner.next();
        }
        return list.toArray(new Scanner.Symbol[list.size()]);
    }

    @Test
    public void testKeywords_1() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.NULL, Scanner.Symbol.END_EVAL }, symbols("${null}"));
    }

    @Test
    public void testKeywords_2() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.TRUE, Scanner.Symbol.END_EVAL }, symbols("${true}"));
    }

    @Test
    public void testKeywords_3() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.FALSE, Scanner.Symbol.END_EVAL }, symbols("${false}"));
    }

    @Test
    public void testKeywords_4() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.EMPTY, Scanner.Symbol.END_EVAL }, symbols("${empty}"));
    }

    @Test
    public void testKeywords_5() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.DIV, Scanner.Symbol.END_EVAL }, symbols("${div}"));
    }

    @Test
    public void testKeywords_6() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.MOD, Scanner.Symbol.END_EVAL }, symbols("${mod}"));
    }

    @Test
    public void testKeywords_7() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.NOT, Scanner.Symbol.END_EVAL }, symbols("${not}"));
    }

    @Test
    public void testKeywords_8() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.AND, Scanner.Symbol.END_EVAL }, symbols("${and}"));
    }

    @Test
    public void testKeywords_9() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.OR, Scanner.Symbol.END_EVAL }, symbols("${or}"));
    }

    @Test
    public void testKeywords_10() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.LE, Scanner.Symbol.END_EVAL }, symbols("${le}"));
    }

    @Test
    public void testKeywords_11() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.LT, Scanner.Symbol.END_EVAL }, symbols("${lt}"));
    }

    @Test
    public void testKeywords_12() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.EQ, Scanner.Symbol.END_EVAL }, symbols("${eq}"));
    }

    @Test
    public void testKeywords_13() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.NE, Scanner.Symbol.END_EVAL }, symbols("${ne}"));
    }

    @Test
    public void testKeywords_14() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.GE, Scanner.Symbol.END_EVAL }, symbols("${ge}"));
    }

    @Test
    public void testKeywords_15() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.GT, Scanner.Symbol.END_EVAL }, symbols("${gt}"));
    }

    @Test
    public void testKeywords_16() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.INSTANCEOF, Scanner.Symbol.END_EVAL }, symbols("${instanceof}"));
    }

    @Test
    public void testKeywords_17() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.IDENTIFIER, Scanner.Symbol.END_EVAL }, symbols("${xnull}"));
    }

    @Test
    public void testKeywords_18() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.IDENTIFIER, Scanner.Symbol.END_EVAL }, symbols("${nullx}"));
    }

    @Test
    public void testMixed_1() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.TEXT, Scanner.Symbol.START_EVAL_DYNAMIC }, symbols("foo${"));
    }

    @Test
    public void testMixed_2() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.IDENTIFIER }, symbols("${bar"));
    }

    @Test
    public void testMixed_3() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.END_EVAL, Scanner.Symbol.TEXT }, symbols("${}bar"));
    }

    @Test
    public void testMixed_4() throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.TEXT, Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.END_EVAL, Scanner.Symbol.TEXT }, symbols("foo${}bar"));
    }
}
