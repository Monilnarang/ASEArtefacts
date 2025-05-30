package org.jline.builtins;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NfaMatcherTest_Purified {

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
    public void testMultiplicity_1() {
        assertFalse(match("C5"));
    }

    @Test
    public void testMultiplicity_2() {
        assertTrue(match("C5", "arg"));
    }

    @Test
    public void testMultiplicity_3() {
        assertFalse(match("C5", "arg", "foo"));
    }

    @Test
    public void testMultiplicity_4() {
        assertTrue(match("C5?"));
    }

    @Test
    public void testMultiplicity_5() {
        assertTrue(match("C5?", "arg"));
    }

    @Test
    public void testMultiplicity_6() {
        assertFalse(match("C5?", "arg", "foo"));
    }

    @Test
    public void testMultiplicity_7() {
        assertFalse(match("C5+"));
    }

    @Test
    public void testMultiplicity_8() {
        assertTrue(match("C5+", "arg"));
    }

    @Test
    public void testMultiplicity_9() {
        assertTrue(match("C5+", "arg", "foo"));
    }

    @Test
    public void testMultiplicity_10() {
        assertTrue(match("C5*"));
    }

    @Test
    public void testMultiplicity_11() {
        assertTrue(match("C5*", "arg"));
    }

    @Test
    public void testMultiplicity_12() {
        assertTrue(match("C5*", "arg", "foo"));
    }

    @Test
    public void testWeird_1() {
        assertTrue(match("a? a? a? a a a", "a", "a", "a", "a"));
    }

    @Test
    public void testWeird_2() {
        assertTrue(match("a ? * +", "a", "a", "a", "a"));
    }

    @Test
    public void testConcat_1() {
        assertTrue(match("C4? C5+", "arg", "foo"));
    }

    @Test
    public void testConcat_2() {
        assertTrue(match("(C1 | C2 | C3)* C4? C5+", "arg", "foo"));
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
}
