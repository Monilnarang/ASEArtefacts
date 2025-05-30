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

public class AstCompositeTest_Purified extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstComposite parseNode(String expression) {
        return (AstComposite) parse(expression).getRoot();
    }

    @Test
    public void testGetValue_1() {
        assertEquals("101", parseNode("${1}0${1}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals(101l, parseNode("${1}0${1}").getValue(bindings, null, Long.class));
    }
}
