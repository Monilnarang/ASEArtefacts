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

public class AstStringTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstString parseNode(String expression) {
        return (AstString) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testGetValue_1() {
        assertEquals("1", parseNode("${'1'}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals(1, parseNode("${'1'}").getValue(bindings, null, Integer.class));
    }
}
