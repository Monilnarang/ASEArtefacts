package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.BeanELResolver;
import jakarta.el.ELException;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.ValueExpression;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.activiti.core.el.juel.util.SimpleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AstMethodTest_Purified extends TestCase {

    AstMethod parseNode(String expression) {
        return (AstMethod) parse(expression).getRoot().getChild(0);
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
    public void testGetValue_1() {
        assertEquals("1", parseNode("${base.bar()}").getValue(bindings, context, String.class));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("3", parseNode("${base.bar(3)}").getValue(bindings, context, String.class));
    }

    @Test
    public void testGetValue_3() {
        assertNull(parseNode("${base.nullObject.toString()}").getValue(bindings, context, Object.class));
    }
}
