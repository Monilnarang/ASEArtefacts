package org.apache.commons.configuration2.convert;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestDefaultConversionHandler_Parameterized {

    private static final String VAR = "${test}";

    private static final String REPLACEMENT = "1";

    private static ConfigurationInterpolator createInterpolator() {
        return new ConfigurationInterpolator() {

            @Override
            public Object interpolate(final Object value) {
                if (VAR.equals(value)) {
                    return REPLACEMENT;
                }
                return value;
            }
        };
    }

    private DefaultConversionHandler handler;

    private void checkSingleValue(final Integer expResult) {
        assertEquals(Integer.parseInt(REPLACEMENT), expResult.intValue());
    }

    @BeforeEach
    public void setUp() {
        handler = new DefaultConversionHandler();
    }

    @Test
    public void testToCustomNumber_3() {
        assertEquals(new MyNumber(3), DefaultConversionHandler.INSTANCE.convertValue("3", MyNumber.class, null));
    }

    @Test
    public void testToCustomNumber_4() {
        assertNull(DefaultConversionHandler.INSTANCE.convertValue(null, MyNumber.class, null));
    }

    @Test
    public void testToCustomNumber_7() {
        assertEquals(new MyNumber(3), DefaultConversionHandler.INSTANCE.to("3", MyNumber.class, null));
    }

    @Test
    public void testToCustomNumber_8() {
        assertNull(DefaultConversionHandler.INSTANCE.to(null, MyNumber.class, null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCustomNumber_1to2")
    public void testToCustomNumber_1to2(int param1, int param2) {
        assertEquals(new MyNumber(param1), DefaultConversionHandler.INSTANCE.convertValue(new MyNumber(1), MyNumber.class, param2));
    }

    static public Stream<Arguments> Provider_testToCustomNumber_1to2() {
        return Stream.of(arguments(1, 1), arguments(2, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCustomNumber_5to6")
    public void testToCustomNumber_5to6(int param1, int param2) {
        assertEquals(new MyNumber(param1), DefaultConversionHandler.INSTANCE.to(new MyNumber(1), MyNumber.class, param2));
    }

    static public Stream<Arguments> Provider_testToCustomNumber_5to6() {
        return Stream.of(arguments(1, 1), arguments(2, 2));
    }
}
