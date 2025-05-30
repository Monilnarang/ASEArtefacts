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

public class AstBinaryTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstBinary parseNode(String expression) {
        return (AstBinary) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testEval_1() {
        assertEquals(6l, parseNode("${4+2}").eval(bindings, null));
    }

    @Test
    public void testEval_2() {
        assertEquals(8l, parseNode("${4*2}").eval(bindings, null));
    }

    @Test
    public void testEval_3() {
        assertEquals(2d, parseNode("${4/2}").eval(bindings, null));
    }

    @Test
    public void testEval_4() {
        assertEquals(0l, parseNode("${4%2}").eval(bindings, null));
    }

    @Test
    public void testEval_5() {
        assertEquals(false, parseNode("${true && false}").eval(bindings, null));
    }

    @Test
    public void testEval_6() {
        assertEquals(true, parseNode("${true || false}").eval(bindings, null));
    }

    @Test
    public void testEval_7() {
        assertEquals(true, parseNode("${1 == 1}").eval(bindings, null));
    }

    @Test
    public void testEval_8() {
        assertEquals(false, parseNode("${1 == 2}").eval(bindings, null));
    }

    @Test
    public void testEval_9() {
        assertEquals(false, parseNode("${2 == 1}").eval(bindings, null));
    }

    @Test
    public void testEval_10() {
        assertEquals(false, parseNode("${1 != 1}").eval(bindings, null));
    }

    @Test
    public void testEval_11() {
        assertEquals(true, parseNode("${1 != 2}").eval(bindings, null));
    }

    @Test
    public void testEval_12() {
        assertEquals(false, parseNode("${2 == 1}").eval(bindings, null));
    }

    @Test
    public void testEval_13() {
        assertEquals(false, parseNode("${1 < 1}").eval(bindings, null));
    }

    @Test
    public void testEval_14() {
        assertEquals(true, parseNode("${1 < 2}").eval(bindings, null));
    }

    @Test
    public void testEval_15() {
        assertEquals(false, parseNode("${2 < 1}").eval(bindings, null));
    }

    @Test
    public void testEval_16() {
        assertEquals(false, parseNode("${1 > 1}").eval(bindings, null));
    }

    @Test
    public void testEval_17() {
        assertEquals(false, parseNode("${1 > 2}").eval(bindings, null));
    }

    @Test
    public void testEval_18() {
        assertEquals(true, parseNode("${2 > 1}").eval(bindings, null));
    }

    @Test
    public void testEval_19() {
        assertEquals(true, parseNode("${1 <= 1}").eval(bindings, null));
    }

    @Test
    public void testEval_20() {
        assertEquals(true, parseNode("${1 <= 2}").eval(bindings, null));
    }

    @Test
    public void testEval_21() {
        assertEquals(false, parseNode("${2 <= 1}").eval(bindings, null));
    }

    @Test
    public void testEval_22() {
        assertEquals(true, parseNode("${1 >= 1}").eval(bindings, null));
    }

    @Test
    public void testEval_23() {
        assertEquals(false, parseNode("${1 >= 2}").eval(bindings, null));
    }

    @Test
    public void testEval_24() {
        assertEquals(true, parseNode("${2 >= 1}").eval(bindings, null));
    }

    @Test
    public void testGetValue_1() {
        assertEquals(Long.valueOf(2l), parseNode("${1+1}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("2", parseNode("${1+1}").getValue(bindings, null, String.class));
    }

    @Test
    public void testOperators_1() {
        assertTrue((Boolean) parseNode("${true and true}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_2() {
        assertFalse((Boolean) parseNode("${true and false}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_3() {
        assertFalse((Boolean) parseNode("${false and true}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_4() {
        assertFalse((Boolean) parseNode("${false and false}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_5() {
        assertTrue((Boolean) parseNode("${true or true}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_6() {
        assertTrue((Boolean) parseNode("${true or false}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_7() {
        assertTrue((Boolean) parseNode("${false or true}").getValue(bindings, null, Boolean.class));
    }

    @Test
    public void testOperators_8() {
        assertFalse((Boolean) parseNode("${false or false}").getValue(bindings, null, Boolean.class));
    }
}
