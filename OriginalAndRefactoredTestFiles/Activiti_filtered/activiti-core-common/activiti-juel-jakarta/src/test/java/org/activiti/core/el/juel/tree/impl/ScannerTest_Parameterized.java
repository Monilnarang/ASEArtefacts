package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.Arrays;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ScannerTest_Parameterized extends TestCase {

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

    @ParameterizedTest
    @MethodSource("Provider_testKeywords_1_1to2_2to3_3to4_4to18")
    public void testKeywords_1_1to2_2to3_3to4_4to18(String param1, String param2) throws Scanner.ScanException {
        assertEquals(new Scanner.Symbol[] { Scanner.Symbol.START_EVAL_DYNAMIC, Scanner.Symbol.NULL, Scanner.Symbol.END_EVAL }, symbols("${null}"));
    }

    static public Stream<Arguments> Provider_testKeywords_1_1to2_2to3_3to4_4to18() {
        return Stream.of(arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.NULL', 'Scanner.Symbol.END_EVAL'}", "${null}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.TRUE', 'Scanner.Symbol.END_EVAL'}", "${true}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.FALSE', 'Scanner.Symbol.END_EVAL'}", "${false}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.EMPTY', 'Scanner.Symbol.END_EVAL'}", "${empty}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.DIV', 'Scanner.Symbol.END_EVAL'}", "${div}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.MOD', 'Scanner.Symbol.END_EVAL'}", "${mod}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.NOT', 'Scanner.Symbol.END_EVAL'}", "${not}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.AND', 'Scanner.Symbol.END_EVAL'}", "${and}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.OR', 'Scanner.Symbol.END_EVAL'}", "${or}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.LE', 'Scanner.Symbol.END_EVAL'}", "${le}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.LT', 'Scanner.Symbol.END_EVAL'}", "${lt}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.EQ', 'Scanner.Symbol.END_EVAL'}", "${eq}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.NE', 'Scanner.Symbol.END_EVAL'}", "${ne}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.GE', 'Scanner.Symbol.END_EVAL'}", "${ge}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.GT', 'Scanner.Symbol.END_EVAL'}", "${gt}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.INSTANCEOF', 'Scanner.Symbol.END_EVAL'}", "${instanceof}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.IDENTIFIER', 'Scanner.Symbol.END_EVAL'}", "${xnull}"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.IDENTIFIER', 'Scanner.Symbol.END_EVAL'}", "${nullx}"), arguments("{'Scanner.Symbol.TEXT', 'Scanner.Symbol.START_EVAL_DYNAMIC'}", "foo${"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.IDENTIFIER'}", "${bar"), arguments("{'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.END_EVAL', 'Scanner.Symbol.TEXT'}", "${}bar"), arguments("{'Scanner.Symbol.TEXT', 'Scanner.Symbol.START_EVAL_DYNAMIC', 'Scanner.Symbol.END_EVAL', 'Scanner.Symbol.TEXT'}", "foo${}bar"));
    }
}
