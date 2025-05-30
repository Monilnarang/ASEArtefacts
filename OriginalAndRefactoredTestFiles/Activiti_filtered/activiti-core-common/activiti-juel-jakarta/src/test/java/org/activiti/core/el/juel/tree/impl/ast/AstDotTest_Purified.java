package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.BeanELResolver;
import jakarta.el.ELException;
import jakarta.el.MethodInfo;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.ValueExpression;
import java.util.Arrays;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.test.TestClass;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.activiti.core.el.juel.util.SimpleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AstDotTest_Purified extends TestCase {

    AstDot parseNode(String expression) {
        return (AstDot) parse(expression).getRoot().getChild(0);
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
        context = new SimpleContext(new SimpleResolver(new BeanELResolver()));
        context.getELResolver().setValue(context, null, "base", this);
        bindings = new Bindings(null, new ValueExpression[1]);
    }

    @Test
    public void testIsLeftValue_1() {
        assertFalse(parseNode("${'foo'.bar}").isLeftValue());
    }

    @Test
    public void testIsLeftValue_2() {
        assertTrue(parseNode("${foo.bar}").isLeftValue());
    }

    @Test
    public void testIsReadOnly_1() {
        assertFalse(parseNode("${base.foo}").isReadOnly(bindings, context));
    }

    @Test
    public void testIsReadOnly_2() {
        assertTrue(parseNode("${'base'.foo}").isReadOnly(bindings, context));
    }

    @Test
    public void testGetValue_1() {
        assertEquals(1l, parseNode("${base.foo}").getValue(bindings, context, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("1", parseNode("${base.foo}").getValue(bindings, context, String.class));
    }

    @Test
    public void testGetValue_3() {
        assertNull(parseNode("${base.nullObject.class}").getValue(bindings, context, Object.class));
    }

    @Test
    public void testGetValueReference_1() {
        assertEquals(this, parseNode("${base.foo}").getValueReference(bindings, context).getBase());
    }

    @Test
    public void testGetValueReference_2() {
        assertEquals("foo", parseNode("${base.foo}").getValueReference(bindings, context).getProperty());
    }
}
