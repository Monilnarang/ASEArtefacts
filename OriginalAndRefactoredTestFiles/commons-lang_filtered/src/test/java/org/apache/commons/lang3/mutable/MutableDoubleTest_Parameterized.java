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

public class MutableDoubleTest_Parameterized extends AbstractLangTest {

    @Test
    public void testConstructors_3() {
        assertEquals(2d, new MutableDouble(Double.valueOf(2d)).doubleValue(), 0.0001d);
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3d, new MutableDouble(new MutableDouble(3d)).doubleValue(), 0.0001d);
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Double.valueOf(0), new MutableDouble().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableDouble mutNum = new MutableDouble(0d);
        mutNum.setValue(1);
        assertEquals(1d, mutNum.doubleValue(), 0.0001d);
        assertEquals(Double.valueOf(1d), mutNum.getValue());
        mutNum.setValue(Double.valueOf(2d));
        assertEquals(2d, mutNum.doubleValue(), 0.0001d);
        assertEquals(Double.valueOf(2d), mutNum.getValue());
        mutNum.setValue(new MutableDouble(3d));
        assertEquals(3d, mutNum.doubleValue(), 0.0001d);
        assertEquals(Double.valueOf(3d), mutNum.getValue());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123.0", new MutableDouble(-123d).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_1_1")
    public void testConstructors_1_1(double param1, double param2) {
        assertEquals(param1, new MutableDouble().doubleValue(), param2);
    }

    static public Stream<Arguments> Provider_testConstructors_1_1() {
        return Stream.of(arguments(0d, 0.0001d), arguments(0d, 0.0001d));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_2_5")
    public void testConstructors_2_5(double param1, double param2, double param3) {
        assertEquals(param1, new MutableDouble(param3).doubleValue(), param2);
    }

    static public Stream<Arguments> Provider_testConstructors_2_5() {
        return Stream.of(arguments(1d, 0.0001d, 1d), arguments(2d, 0.0001d, 2.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToDouble_1to2")
    public void testToDouble_1to2(double param1, double param2) {
        assertEquals(Double.valueOf(param1), new MutableDouble(param2).toDouble());
    }

    static public Stream<Arguments> Provider_testToDouble_1to2() {
        return Stream.of(arguments(0d, 0d), arguments(12.3d, 12.3d));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to2")
    public void testToString_1to2(double param1, double param2) {
        assertEquals(param1, new MutableDouble(param2).toString());
    }

    static public Stream<Arguments> Provider_testToString_1to2() {
        return Stream.of(arguments(0.0, 0d), arguments(10.0, 10d));
    }
}
