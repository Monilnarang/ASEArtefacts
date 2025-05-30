package org.activiti.core.el.juel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.BeanELResolver;
import jakarta.el.PropertyNotFoundException;
import org.activiti.core.el.juel.test.TestCase;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Builder;
import org.activiti.core.el.juel.util.SimpleContext;
import org.activiti.core.el.juel.util.SimpleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TreeValueExpressionTest_Parameterized extends TestCase {

    public static int foo() {
        return 0;
    }

    public static int bar() {
        return 0;
    }

    int foobar;

    public void setFoobar(int value) {
        foobar = value;
    }

    SimpleContext context;

    TreeStore store = new TreeStore(new Builder(), null);

    @BeforeEach
    protected void setUp() throws Exception {
        context = new SimpleContext(new SimpleResolver(new BeanELResolver()));
        context.getELResolver().setValue(context, null, "base", this);
        context.setVariable("var_long_1", new TreeValueExpression(store, null, null, null, "${1}", long.class));
        context.setVariable("var_long_2", new TreeValueExpression(store, null, null, null, "${1}", long.class));
        context.setVariable("var_var_long_1", new TreeValueExpression(store, null, context.getVariableMapper(), null, "${var_long_1}", long.class));
        context.setVariable("var_var_long_2", new TreeValueExpression(store, null, context.getVariableMapper(), null, "${var_long_2}", long.class));
        context.setFunction("", "foo", getClass().getMethod("foo"));
        context.setFunction("ns", "foo_1", getClass().getMethod("foo"));
        context.setFunction("ns", "foo_2", getClass().getMethod("foo"));
        context.setVariable("var_foo_1", new TreeValueExpression(store, context.getFunctionMapper(), null, null, "${ns:foo_1()}", long.class));
        context.setVariable("var_foo_2", new TreeValueExpression(store, context.getFunctionMapper(), null, null, "${ns:foo_2()}", long.class));
        context.setVariable("var_foobar", new TreeValueExpression(store, null, context.getVariableMapper(), null, "${base.foobar}", int.class));
        context.getELResolver().setValue(context, null, "property_foo", "foo");
    }

    @Test
    public void testIsLiteralText_1() {
        assertTrue(new TreeValueExpression(store, null, null, null, "foo", Object.class).isLiteralText());
    }

    @Test
    public void testIsLiteralText_2() {
        assertFalse(new TreeValueExpression(store, null, null, null, "${foo}", Object.class).isLiteralText());
    }

    @Test
    public void testIsDeferred_3() {
        assertTrue(new TreeValueExpression(store, null, null, null, "#{foo}", Object.class).isDeferred());
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsDeferred_1to2")
    public void testIsDeferred_1to2(String param1) {
        assertFalse(new TreeValueExpression(store, param1, null, null, "foo", Object.class).isDeferred());
    }

    static public Stream<Arguments> Provider_testIsDeferred_1to2() {
        return Stream.of(arguments("foo"), arguments("${foo}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetExpectedType_1to2")
    public void testGetExpectedType_1to2(String param1) {
        assertEquals(Object.class, new TreeValueExpression(store, param1, null, null, "${foo}", Object.class).getExpectedType());
    }

    static public Stream<Arguments> Provider_testGetExpectedType_1to2() {
        return Stream.of(arguments("${foo}"), arguments("${foo}"));
    }
}
