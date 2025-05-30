package org.activiti.core.el.juel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import jakarta.el.BeanELResolver;
import jakarta.el.MethodInfo;
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

public class TreeMethodExpressionTest_Parameterized extends TestCase {

    public int foo() {
        return 0;
    }

    public int bar() {
        return 0;
    }

    SimpleContext context;

    TreeStore store = new TreeStore(new Builder(Builder.Feature.METHOD_INVOCATIONS), null);

    @BeforeEach
    protected void setUp() {
        context = new SimpleContext(new SimpleResolver(new BeanELResolver()));
        context.getELResolver().setValue(context, null, "base", this);
    }

    @Test
    public void testIsLiteralText_1() {
        assertFalse(new TreeMethodExpression(store, null, null, null, "${base.foo}", null, new Class[0]).isLiteralText());
    }

    @Test
    public void testIsLiteralText_2() {
        assertTrue(new TreeMethodExpression(store, null, null, null, "base.foo", null, new Class[0]).isLiteralText());
    }

    @Test
    public void testIsDeferred_3() {
        assertTrue(new TreeMethodExpression(store, null, null, null, "#{foo}", null, new Class[0]).isDeferred());
    }

    @Test
    public void testInvoke_1() {
        assertEquals(0, new TreeMethodExpression(store, null, null, null, "${base.foo}", null, new Class[0]).invoke(context, null));
    }

    @Test
    public void testInvoke_2() {
        assertEquals(0, new TreeMethodExpression(store, null, null, null, "${base.foo()}", null, null).invoke(context, null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsDeferred_1to2")
    public void testIsDeferred_1to2(int param1, int param2) {
        assertFalse(new TreeMethodExpression(store, null, null, null, "foo", null, new Class[param1]).isDeferred());
    }

    static public Stream<Arguments> Provider_testIsDeferred_1to2() {
        return Stream.of(arguments(0, "foo"), arguments(0, "${foo}"));
    }
}
