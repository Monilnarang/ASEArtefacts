package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;

public class AstEvalTest_Purified extends TestCase {

    AstEval parseNode(String expression) {
        return (AstEval) parse(expression).getRoot();
    }

    @Test
    public void testIsLeftValue_1() {
        assertFalse(parseNode("${1}").isLeftValue());
    }

    @Test
    public void testIsLeftValue_2() {
        assertTrue(parseNode("${foo.bar}").isLeftValue());
    }

    @Test
    public void testIsDeferred_1() {
        assertTrue(parseNode("#{1}").isDeferred());
    }

    @Test
    public void testIsDeferred_2() {
        assertFalse(parseNode("${1}").isDeferred());
    }
}
