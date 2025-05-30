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

public class AstChoiceTest_Parameterized extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstChoice parseNode(String expression) {
        return (AstChoice) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testGetValue_1() {
        assertEquals(1l, parseNode("${true?1:2}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("1", parseNode("${true?1:2}").getValue(bindings, null, String.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEval_1to2")
    public void testEval_1to2(long param1, String param2) {
        assertEquals(param1, parseNode("${true?1:2}").eval(bindings, param2));
    }

    static public Stream<Arguments> Provider_testEval_1to2() {
        return Stream.of(arguments(1l, "${true?1:2}"), arguments(2l, "${false?1:2}"));
    }
}
