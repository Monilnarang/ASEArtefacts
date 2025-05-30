package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.ELException;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.tree.Bindings;
import org.junit.jupiter.api.Test;

public class AstNumberTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstNumber parseNode(String expression) {
        return (AstNumber) parse(expression).getRoot().getChild(0);
    }

    private void assertTrue(boolean readOnly) {
    }

    @Test
    public void testEval_1() {
        assertEquals(1l, parseNode("${1}").eval(bindings, null));
    }

    @Test
    public void testEval_2() {
        assertEquals(1d, parseNode("${1.0}").eval(bindings, null));
    }

    @Test
    public void testGetValue_1() {
        assertEquals(1l, parseNode("${1}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals(1d, parseNode("${1}").getValue(bindings, null, Double.class));
    }
}
