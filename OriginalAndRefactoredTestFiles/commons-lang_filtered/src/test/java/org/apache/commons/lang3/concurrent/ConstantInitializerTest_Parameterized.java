package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.regex.Pattern;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConstantInitializerTest_Parameterized extends AbstractLangTest {

    private static final Integer VALUE = 42;

    private ConstantInitializer<Integer> init;

    private void checkEquals(final Object obj, final boolean expected) {
        assertEquals(expected, init.equals(obj), "Wrong result of equals");
        if (obj != null) {
            assertEquals(expected, obj.equals(init), "Not symmetric");
            if (expected) {
                assertEquals(init.hashCode(), obj.hashCode(), "Different hash codes");
            }
        }
    }

    @BeforeEach
    public void setUp() {
        init = new ConstantInitializer<>(VALUE);
    }

    @Test
    public void testisInitialized_2() {
        assertEquals(VALUE, init.getObject(), "Wrong object");
    }

    @ParameterizedTest
    @MethodSource("Provider_testisInitialized_1_3")
    public void testisInitialized_1_3(String param1) {
        assertTrue(init.isInitialized(), param1);
    }

    static public Stream<Arguments> Provider_testisInitialized_1_3() {
        return Stream.of(arguments("was not initialized before get()"), arguments("was not initialized after get()"));
    }
}
