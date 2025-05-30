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

public class AstNullTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstNull parseNode(String expression) {
        return (AstNull) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testGetValue_1() {
        assertNull(parseNode("${null}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("", parseNode("${null}").getValue(bindings, null, String.class));
    }
}
