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

public class AstBooleanTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstBoolean parseNode(String expression) {
        return (AstBoolean) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testEval_1() {
        assertEquals(true, parseNode("${true}").eval(bindings, null));
    }

    @Test
    public void testEval_2() {
        assertEquals(false, parseNode("${false}").eval(bindings, null));
    }

    @Test
    public void testGetValue_1() {
        assertEquals(true, parseNode("${true}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("true", parseNode("${true}").getValue(bindings, null, String.class));
    }
}
