package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.ELException;
import jakarta.el.MethodInfo;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.ValueExpression;
import java.util.Arrays;
import java.util.HashMap;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.test.TestClass;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.impl.Builder;
import org.activiti.core.el.juel.util.SimpleContext;
import org.activiti.core.el.juel.util.SimpleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AstBracketTest_Purified extends TestCase {

    AstBracket parseNode(String expression) {
        return (AstBracket) parse(expression).getRoot().getChild(0);
    }

    SimpleContext context;

    Bindings bindings;

    long foo = 1l;

    public long getFoo() {
        return foo;
    }

    public void setFoo(long value) {
        foo = value;
    }

    public long bar() {
        return 1l;
    }

    public long bar(long value) {
        return value;
    }

    public TestClass getTestClass() {
        return new TestClass();
    }

    public Object getNullObject() {
        return null;
    }

    @BeforeEach
    protected void setUp() throws Exception {
        context = new SimpleContext(new SimpleResolver());
        context.getELResolver().setValue(context, null, "base", this);
        HashMap<Object, String> nullmap = new HashMap<Object, String>();
        nullmap.put(null, "foo");
        context.getELResolver().setValue(context, null, "nullmap", nullmap);
        bindings = new Bindings(null, new ValueExpression[2]);
    }

    @Test
    public void testIsLeftValue_1() {
        assertFalse(parseNode("${'foo'[bar]}").isLeftValue());
    }

    @Test
    public void testIsLeftValue_2() {
        assertTrue(parseNode("${foo[bar]}").isLeftValue());
    }

    @Test
    public void testGetValueReference_1() {
        assertEquals(this, parseNode("${base['foo']}").getValueReference(bindings, context).getBase());
    }

    @Test
    public void testGetValueReference_2() {
        assertEquals("foo", parseNode("${base['foo']}").getValueReference(bindings, context).getProperty());
    }
}
