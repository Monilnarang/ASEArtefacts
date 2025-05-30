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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AstMethodTest_Parameterized extends TestCase {

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
    public void testGetValue_3() {
        assertNull(parseNode("${base.nullObject.toString()}").getValue(bindings, context, Object.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetValue_1to2")
    public void testGetValue_1to2(int param1, String param2) {
        assertEquals(param1, parseNode(param2).getValue(bindings, context, String.class));
    }

    static public Stream<Arguments> Provider_testGetValue_1to2() {
        return Stream.of(arguments(1, "${base.bar()}"), arguments(3, "${base.bar(3)}"));
    }
}
