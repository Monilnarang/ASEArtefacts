package org.jline.jansi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.jline.jansi.Ansi.*;
import static org.jline.jansi.Ansi.Attribute.*;
import static org.jline.jansi.Ansi.Color.*;
import static org.jline.jansi.AnsiRenderer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AnsiRendererTest_Parameterized {

    @BeforeAll
    static void setUp() {
        Ansi.setEnabled(true);
    }

    @Test
    public void testTest_1() throws Exception {
        assertFalse(test("foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTest_2to3")
    public void testTest_2to3(String param1) throws Exception {
        assertTrue(test(param1));
    }

    static public Stream<Arguments> Provider_testTest_2to3() {
        return Stream.of(arguments("@|foo|"), arguments("@|foo"));
    }
}
