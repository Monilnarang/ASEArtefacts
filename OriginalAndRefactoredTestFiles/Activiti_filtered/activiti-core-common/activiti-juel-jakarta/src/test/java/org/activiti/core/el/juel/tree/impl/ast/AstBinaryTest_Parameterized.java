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

public class AstBinaryTest_Parameterized extends TestCase {

    private Bindings bindings = new Bindings(null, null, null);

    AstBinary parseNode(String expression) {
        return (AstBinary) parse(expression).getRoot().getChild(0);
    }

    @Test
    public void testGetValue_1() {
        assertEquals(Long.valueOf(2l), parseNode("${1+1}").getValue(bindings, null, null));
    }

    @Test
    public void testGetValue_2() {
        assertEquals("2", parseNode("${1+1}").getValue(bindings, null, String.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEval_1to4")
    public void testEval_1to4(long param1, String param2) {
        assertEquals(param1, parseNode("${4+2}").eval(bindings, param2));
    }

    static public Stream<Arguments> Provider_testEval_1to4() {
        return Stream.of(arguments(6l, "${4+2}"), arguments(8l, "${4*2}"), arguments(2d, "${4/2}"), arguments(0l, "${4%2}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEval_5to24")
    public void testEval_5to24(String param1) {
        assertEquals(param1, parseNode("${true && false}").eval(bindings, null));
    }

    static public Stream<Arguments> Provider_testEval_5to24() {
        return Stream.of(arguments("${true && false}"), arguments("${true || false}"), arguments("${1 == 1}"), arguments("${1 == 2}"), arguments("${2 == 1}"), arguments("${1 != 1}"), arguments("${1 != 2}"), arguments("${2 == 1}"), arguments("${1 < 1}"), arguments("${1 < 2}"), arguments("${2 < 1}"), arguments("${1 > 1}"), arguments("${1 > 2}"), arguments("${2 > 1}"), arguments("${1 <= 1}"), arguments("${1 <= 2}"), arguments("${2 <= 1}"), arguments("${1 >= 1}"), arguments("${1 >= 2}"), arguments("${2 >= 1}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOperators_1_5to7")
    public void testOperators_1_5to7(String param1) {
        assertTrue((Boolean) parseNode("${true and true}").getValue(bindings, param1, Boolean.class));
    }

    static public Stream<Arguments> Provider_testOperators_1_5to7() {
        return Stream.of(arguments("${true and true}"), arguments("${true or true}"), arguments("${true or false}"), arguments("${false or true}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOperators_2to4_8")
    public void testOperators_2to4_8(String param1) {
        assertFalse((Boolean) parseNode("${true and false}").getValue(bindings, param1, Boolean.class));
    }

    static public Stream<Arguments> Provider_testOperators_2to4_8() {
        return Stream.of(arguments("${true and false}"), arguments("${false and true}"), arguments("${false and false}"), arguments("${false or false}"));
    }
}
