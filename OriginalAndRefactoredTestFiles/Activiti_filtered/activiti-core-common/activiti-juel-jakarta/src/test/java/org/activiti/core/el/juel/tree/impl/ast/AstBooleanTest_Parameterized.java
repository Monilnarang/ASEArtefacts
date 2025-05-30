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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AstBooleanTest_Parameterized extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstBoolean parseNode(String expression) {
        return (AstBoolean) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testGetValue_1() {
        assertEquals(true, parseNode("${true}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("true", parseNode("${true}").getValue(bindings, null, String.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEval_1to2")
    public void testEval_1to2(String param1) {
        assertEquals(param1, parseNode("${true}").eval(bindings, null));
    }

    static public Stream<Arguments> Provider_testEval_1to2() {
        return Stream.of(arguments("${true}"), arguments("${false}"));
    }
}
