package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.regex.Pattern;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConstantInitializerTest_Purified extends AbstractLangTest {

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
    public void testisInitialized_1() {
        assertTrue(init.isInitialized(), "was not initialized before get()");
    }

    @Test
    public void testisInitialized_2() {
        assertEquals(VALUE, init.getObject(), "Wrong object");
    }

    @Test
    public void testisInitialized_3() {
        assertTrue(init.isInitialized(), "was not initialized after get()");
    }
}
