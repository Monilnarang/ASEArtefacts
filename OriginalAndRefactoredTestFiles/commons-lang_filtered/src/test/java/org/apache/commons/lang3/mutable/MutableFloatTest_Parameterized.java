package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MutableFloatTest_Parameterized extends AbstractLangTest {

    @Test
    public void testConstructors_3() {
        assertEquals(2f, new MutableFloat(Float.valueOf(2f)).floatValue(), 0.0001f);
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3f, new MutableFloat(new MutableFloat(3f)).floatValue(), 0.0001f);
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Float.valueOf(0), new MutableFloat().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableFloat mutNum = new MutableFloat(0f);
        mutNum.setValue(1);
        assertEquals(1f, mutNum.floatValue(), 0.0001f);
        assertEquals(Float.valueOf(1f), mutNum.getValue());
        mutNum.setValue(Float.valueOf(2f));
        assertEquals(2f, mutNum.floatValue(), 0.0001f);
        assertEquals(Float.valueOf(2f), mutNum.getValue());
        mutNum.setValue(new MutableFloat(3f));
        assertEquals(3f, mutNum.floatValue(), 0.0001f);
        assertEquals(Float.valueOf(3f), mutNum.getValue());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123.0", new MutableFloat(-123f).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_1_1")
    public void testConstructors_1_1(double param1, double param2) {
        assertEquals(param1, new MutableFloat().floatValue(), param2);
    }

    static public Stream<Arguments> Provider_testConstructors_1_1() {
        return Stream.of(arguments(0f, 0.0001f), arguments(0f, 0.0001f));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_2_5")
    public void testConstructors_2_5(double param1, double param2, double param3) {
        assertEquals(param1, new MutableFloat(param3).floatValue(), param2);
    }

    static public Stream<Arguments> Provider_testConstructors_2_5() {
        return Stream.of(arguments(1f, 0.0001f, 1f), arguments(2f, 0.0001f, 2.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToFloat_1to2")
    public void testToFloat_1to2(double param1, double param2) {
        assertEquals(Float.valueOf(param1), new MutableFloat(param2).toFloat());
    }

    static public Stream<Arguments> Provider_testToFloat_1to2() {
        return Stream.of(arguments(0f, 0f), arguments(12.3f, 12.3f));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to2")
    public void testToString_1to2(double param1, double param2) {
        assertEquals(param1, new MutableFloat(param2).toString());
    }

    static public Stream<Arguments> Provider_testToString_1to2() {
        return Stream.of(arguments(0.0, 0f), arguments(10.0, 10f));
    }
}
