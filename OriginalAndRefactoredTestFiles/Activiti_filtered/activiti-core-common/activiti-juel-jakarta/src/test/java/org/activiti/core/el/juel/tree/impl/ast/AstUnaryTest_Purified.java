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

public class AstUnaryTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstUnary parseNode(String expression) {
        return (AstUnary) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testEval_1() {
        assertEquals(true, parseNode("${!false}").eval(bindings, null));
    }

    @Test
    public void testEval_2() {
        assertEquals(false, parseNode("${!true}").eval(bindings, null));
    }

    @Test
    public void testEval_3() {
        assertEquals(false, parseNode("${empty 1}").eval(bindings, null));
    }

    @Test
    public void testEval_4() {
        assertEquals(true, parseNode("${empty null}").eval(bindings, null));
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
}
