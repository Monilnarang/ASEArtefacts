package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.junit.jupiter.api.Test;

public class ParserTest_Purified extends TestCase {

    static Tree verifyLiteralExpression(String expression) {
        Tree tree = parse(expression);
        assertTrue(tree.getRoot().isLiteralText());
        assertEquals(expression, tree.getRoot().getStructuralId(null));
        return tree;
    }

    static Tree verifyEvalExpression(String canonical) {
        Tree tree = parse(canonical);
        assertFalse(tree.getRoot().isLiteralText());
        assertEquals(canonical, tree.getRoot().getStructuralId(null));
        return tree;
    }

    static Tree verifyEvalExpression(String canonical, String expression) {
        Tree tree = parse(expression);
        assertFalse(tree.getRoot().isLiteralText());
        assertEquals(canonical, tree.getRoot().getStructuralId(null));
        return verifyEvalExpression(canonical);
    }

    static Tree verifyEvalExpression(String canonical, String expression1, String expression2) {
        Tree tree = parse(expression2);
        assertFalse(tree.getRoot().isLiteralText());
        assertEquals(canonical, tree.getRoot().getStructuralId(null));
        return verifyEvalExpression(canonical, expression1);
    }

    static Tree verifyCompositeExpression(String canonical) {
        Tree tree = parse(canonical);
        assertFalse(tree.getRoot().isLiteralText());
        assertEquals(canonical, tree.getRoot().getStructuralId(null));
        return tree;
    }

    Tree verifyBinary(AstBinary.Operator op, String canonical) {
        Tree tree = verifyEvalExpression(canonical);
        assertTrue((tree.getRoot()).getChild(0) instanceof AstBinary);
        assertEquals(op, ((AstBinary) tree.getRoot().getChild(0)).getOperator());
        return tree;
    }

    @Test
    public void testProperty_1() {
        assertTrue(parse("${a.a}").getRoot().isLeftValue());
    }

    @Test
    public void testProperty_2() {
        assertFalse(parse("${1 . a}").getRoot().isLeftValue());
    }

    @Test
    public void testProperty_3() {
        assertTrue(parse("${(1).a}").getRoot().isLeftValue());
    }

    @Test
    public void testProperty_4() {
        assertTrue(parse("${a[a]}").getRoot().isLeftValue());
    }

    @Test
    public void testProperty_5() {
        assertFalse(parse("${1[a]}").getRoot().isLeftValue());
    }

    @Test
    public void testProperty_6() {
        assertTrue(parse("${(1)[a]}").getRoot().isLeftValue());
    }

    @Test
    public void testIsDeferred_1() {
        assertFalse(parse("foo").isDeferred());
    }

    @Test
    public void testIsDeferred_2() {
        assertFalse(parse("${foo}").isDeferred());
    }

    @Test
    public void testIsDeferred_3() {
        assertFalse(parse("${foo}bar${foo}").isDeferred());
    }

    @Test
    public void testIsDeferred_4() {
        assertTrue(parse("#{foo}").isDeferred());
    }

    @Test
    public void testIsDeferred_5() {
        assertTrue(parse("#{foo}bar#{foo}").isDeferred());
    }
}
