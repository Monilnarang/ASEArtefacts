package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.ELException;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.tree.Bindings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AstUnaryTest_Parameterized extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstUnary parseNode(String expression) {
        return (AstUnary) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testEval_5() {
        assertEquals(-1l, parseNode("${-1}").eval(bindings, null));
    }

    @Test
    public void testGetValue_1() {
        assertEquals(Long.valueOf(-1l), parseNode("${-1}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("-1", parseNode("${-1}").getValue(bindings, null, String.class));
    }

    @Test
    public void testOperators_1() {
        assertFalse((Boolean) parseNode("${not true}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_2() {
        assertTrue((Boolean) parseNode("${not false}").getValue(bindings, null, Boolean.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEval_1to4")
    public void testEval_1to4(String param1) {
        assertEquals(param1, parseNode("${!false}").eval(bindings, null));
    }

    static public Stream<Arguments> Provider_testEval_1to4() {
        return Stream.of(arguments("${!false}"), arguments("${!true}"), arguments("${empty 1}"), arguments("${empty null}"));
    }
}
