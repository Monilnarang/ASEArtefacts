package org.jline.builtins;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NfaMatcherTest_Parameterized {

    boolean match(String regexp, String... args) {
        return new NfaMatcher<>(regexp, this::matchArg).match(Arrays.asList(args));
    }

    Set<String> matchPartial(String regexp, String... args) {
        return new NfaMatcher<>(regexp, this::matchArg).matchPartial(Arrays.asList(args));
    }

    boolean matchArg(String arg, String name) {
        switch(name) {
            case "C1":
                return arg.startsWith("--opt1");
            case "C2":
                return arg.startsWith("--opt2");
            case "C3":
                return arg.startsWith("--opt3");
            case "C4":
                return arg.startsWith("--myopt");
            case "C5":
                return true;
            case "a":
                return arg.equals("a");
            default:
                throw new IllegalStateException("Unsupported: " + name);
        }
    }

    static Set<String> asSet(String... ts) {
        Set<String> s = new HashSet<>();
        for (String t : ts) {
            s.add(t);
        }
        return s;
    }

    @Test
    public void testConcat_3() {
        assertTrue(match("(C1 | C2 | C3)* C4? C5+", "--opt1=a", "--opt2=b", "--myopt", "arg", "foo"));
    }

    @Test
    public void testPartial_1() {
        assertEquals(asSet("C1", "C2", "C3", "C4", "C5"), matchPartial("(C1 | C2 | C3)* C4? C5+", "--opt1=a"));
    }

    @Test
    public void testPartial_2() {
        assertEquals(asSet("C5"), matchPartial("(C1 | C2 | C3)* C4? C5+", "--opt1=a", "--myopt"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultiplicity_1_7")
    public void testMultiplicity_1_7(String param1) {
        assertFalse(match(param1));
    }

    static public Stream<Arguments> Provider_testMultiplicity_1_7() {
        return Stream.of(arguments("C5"), arguments("C5+"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultiplicity_2_5_8_11")
    public void testMultiplicity_2_5_8_11(String param1, String param2) {
        assertTrue(match(param1, param2));
    }

    static public Stream<Arguments> Provider_testMultiplicity_2_5_8_11() {
        return Stream.of(arguments("C5", "arg"), arguments("C5?", "arg"), arguments("C5+", "arg"), arguments("C5*", "arg"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultiplicity_3_6")
    public void testMultiplicity_3_6(String param1, String param2, String param3) {
        assertFalse(match(param1, param2, param3));
    }

    static public Stream<Arguments> Provider_testMultiplicity_3_6() {
        return Stream.of(arguments("C5", "arg", "foo"), arguments("C5?", "arg", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultiplicity_4_10")
    public void testMultiplicity_4_10(String param1) {
        assertTrue(match(param1));
    }

    static public Stream<Arguments> Provider_testMultiplicity_4_10() {
        return Stream.of(arguments("C5?"), arguments("C5*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultiplicity_1to2_9_12")
    public void testMultiplicity_1to2_9_12(String param1, String param2, String param3) {
        assertTrue(match(param1, param2, param3));
    }

    static public Stream<Arguments> Provider_testMultiplicity_1to2_9_12() {
        return Stream.of(arguments("C5+", "arg", "foo"), arguments("C5*", "arg", "foo"), arguments("C4? C5+", "arg", "foo"), arguments("(C1 | C2 | C3)* C4? C5+", "arg", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testWeird_1to2")
    public void testWeird_1to2(String param1, String param2, String param3, String param4, String param5) {
        assertTrue(match(param1, param2, param3, param4, param5));
    }

    static public Stream<Arguments> Provider_testWeird_1to2() {
        return Stream.of(arguments("a? a? a? a a a", "a", "a", "a", "a"), arguments("a ? * +", "a", "a", "a", "a"));
    }
}
