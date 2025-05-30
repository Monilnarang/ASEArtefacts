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

public class AstTextTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstText parseNode(String expression) {
        return (AstText) parse(expression).getRoot();
    }

    @Test
    public void testGetValue_1() {
        assertEquals("1", parseNode("1").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals(1l, parseNode("1").getValue(bindings, null, Long.class));
    }

    @Test
    public void testInvoke_1() {
        assertEquals("1", parseNode("1").invoke(bindings, null, null, null, null));
    }

    @Test
    public void testInvoke_2() {
        assertEquals(1l, parseNode("1").invoke(bindings, null, Long.class, null, null));
    }
}
