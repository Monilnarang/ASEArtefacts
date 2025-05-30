package org.apache.commons.math4.legacy.core.dfp;

import org.apache.commons.math4.legacy.core.ExtendedFieldElementAbstractTest;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.numbers.core.Precision;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DfpTest_Parameterized extends ExtendedFieldElementAbstractTest<Dfp> {

    private DfpField field;

    private Dfp pinf;

    private Dfp ninf;

    private Dfp nan;

    private Dfp snan;

    private Dfp qnan;

    @Override
    protected Dfp build(final double x) {
        return field.newDfp(x);
    }

    @Before
    public void setUp() {
        field = new DfpField(20);
        pinf = field.newDfp("1").divide(field.newDfp("0"));
        ninf = field.newDfp("-1").divide(field.newDfp("0"));
        nan = field.newDfp("0").divide(field.newDfp("0"));
        snan = field.newDfp((byte) 1, Dfp.SNAN);
        qnan = field.newDfp((byte) 1, Dfp.QNAN);
        ninf.getField().clearIEEEFlags();
    }

    @After
    public void tearDown() {
        field = null;
        pinf = null;
        ninf = null;
        nan = null;
        snan = null;
        qnan = null;
    }

    private void cmptst(Dfp a, Dfp b, String op, boolean result, double num) {
        if (op.equals("equal")) {
            if (a.equals(b) != result) {
                assertionFailOpNum(op, num);
            }
        }
        if (op.equals("unequal")) {
            if (a.unequal(b) != result) {
                assertionFailOpNum(op, num);
            }
        }
        if (op.equals("lessThan")) {
            if (a.lessThan(b) != result) {
                assertionFailOpNum(op, num);
            }
        }
        if (op.equals("greaterThan")) {
            if (a.greaterThan(b) != result) {
                assertionFailOpNum(op, num);
            }
        }
    }

    private static void assertionFail(String content) {
        Assert.fail("assertion failed: " + content);
    }

    private static void assertionFailOpNum(String op, double num) {
        assertionFail(op + " compare #" + num);
    }

    private static void assertionFailDfpField(DfpField field) {
        assertionFail("compare flags = " + field.getIEEEFlags());
    }

    @Test
    public void testByteConstructor_3() {
        Assert.assertEquals("-1.", new Dfp(field, (byte) -1).toString());
    }

    @Test
    public void testByteConstructor_4() {
        Assert.assertEquals("-128.", new Dfp(field, Byte.MIN_VALUE).toString());
    }

    @Test
    public void testByteConstructor_5() {
        Assert.assertEquals("127.", new Dfp(field, Byte.MAX_VALUE).toString());
    }

    @Test
    public void testIntConstructor_6() {
        Assert.assertEquals("-2147483648.", new Dfp(field, Integer.MIN_VALUE).toString());
    }

    @Test
    public void testIntConstructor_7() {
        Assert.assertEquals("2147483647.", new Dfp(field, Integer.MAX_VALUE).toString());
    }

    @Test
    public void testLongConstructor_6() {
        Assert.assertEquals("-9223372036854775808.", new Dfp(field, Long.MIN_VALUE).toString());
    }

    @Test
    public void testLongConstructor_7() {
        Assert.assertEquals("9223372036854775807.", new Dfp(field, Long.MAX_VALUE).toString());
    }

    @Test
    public void testToString_1() {
        Assert.assertEquals("toString #1", "Infinity", pinf.toString());
    }

    @Test
    public void testToString_2() {
        Assert.assertEquals("toString #2", "-Infinity", ninf.toString());
    }

    @Test
    public void testToString_3() {
        Assert.assertEquals("toString #3", "NaN", nan.toString());
    }

    @Test
    public void testToString_4() {
        Assert.assertEquals("toString #4", "NaN", field.newDfp((byte) 1, Dfp.QNAN).toString());
    }

    @Test
    public void testToString_5() {
        Assert.assertEquals("toString #5", "NaN", field.newDfp((byte) 1, Dfp.SNAN).toString());
    }

    @Test
    public void testLog10K_5() {
        Assert.assertEquals("log10K #4", -1, field.newDfp("0.1").log10K());
    }

    @Test
    public void testIsZero_1() {
        Assert.assertTrue(field.getZero().isZero());
    }

    @Test
    public void testIsZero_2() {
        Assert.assertTrue(field.getZero().negate().isZero());
    }

    @Test
    public void testIsZero_5() {
        Assert.assertFalse(field.newDfp(1.0e-90).isZero());
    }

    @Test
    public void testIsZero_6() {
        Assert.assertFalse(nan.isZero());
    }

    @Test
    public void testIsZero_7() {
        Assert.assertFalse(nan.negate().isZero());
    }

    @Test
    public void testIsZero_8() {
        Assert.assertFalse(pinf.isZero());
    }

    @Test
    public void testIsZero_9() {
        Assert.assertFalse(pinf.negate().isZero());
    }

    @Test
    public void testIsZero_10() {
        Assert.assertFalse(ninf.isZero());
    }

    @Test
    public void testIsZero_11() {
        Assert.assertFalse(ninf.negate().isZero());
    }

    @Test
    public void testSignPredicates_1() {
        Assert.assertTrue(field.getZero().negativeOrNull());
    }

    @Test
    public void testSignPredicates_2() {
        Assert.assertTrue(field.getZero().positiveOrNull());
    }

    @Test
    public void testSignPredicates_3() {
        Assert.assertFalse(field.getZero().strictlyNegative());
    }

    @Test
    public void testSignPredicates_4() {
        Assert.assertFalse(field.getZero().strictlyPositive());
    }

    @Test
    public void testSignPredicates_5() {
        Assert.assertTrue(field.getZero().negate().negativeOrNull());
    }

    @Test
    public void testSignPredicates_6() {
        Assert.assertTrue(field.getZero().negate().positiveOrNull());
    }

    @Test
    public void testSignPredicates_7() {
        Assert.assertFalse(field.getZero().negate().strictlyNegative());
    }

    @Test
    public void testSignPredicates_8() {
        Assert.assertFalse(field.getZero().negate().strictlyPositive());
    }

    @Test
    public void testSignPredicates_9() {
        Assert.assertFalse(field.getOne().negativeOrNull());
    }

    @Test
    public void testSignPredicates_10() {
        Assert.assertTrue(field.getOne().positiveOrNull());
    }

    @Test
    public void testSignPredicates_11() {
        Assert.assertFalse(field.getOne().strictlyNegative());
    }

    @Test
    public void testSignPredicates_12() {
        Assert.assertTrue(field.getOne().strictlyPositive());
    }

    @Test
    public void testSignPredicates_13() {
        Assert.assertTrue(field.getOne().negate().negativeOrNull());
    }

    @Test
    public void testSignPredicates_14() {
        Assert.assertFalse(field.getOne().negate().positiveOrNull());
    }

    @Test
    public void testSignPredicates_15() {
        Assert.assertTrue(field.getOne().negate().strictlyNegative());
    }

    @Test
    public void testSignPredicates_16() {
        Assert.assertFalse(field.getOne().negate().strictlyPositive());
    }

    @Test
    public void testSignPredicates_17() {
        Assert.assertFalse(nan.negativeOrNull());
    }

    @Test
    public void testSignPredicates_18() {
        Assert.assertFalse(nan.positiveOrNull());
    }

    @Test
    public void testSignPredicates_19() {
        Assert.assertFalse(nan.strictlyNegative());
    }

    @Test
    public void testSignPredicates_20() {
        Assert.assertFalse(nan.strictlyPositive());
    }

    @Test
    public void testSignPredicates_21() {
        Assert.assertFalse(nan.negate().negativeOrNull());
    }

    @Test
    public void testSignPredicates_22() {
        Assert.assertFalse(nan.negate().positiveOrNull());
    }

    @Test
    public void testSignPredicates_23() {
        Assert.assertFalse(nan.negate().strictlyNegative());
    }

    @Test
    public void testSignPredicates_24() {
        Assert.assertFalse(nan.negate().strictlyPositive());
    }

    @Test
    public void testSignPredicates_25() {
        Assert.assertFalse(pinf.negativeOrNull());
    }

    @Test
    public void testSignPredicates_26() {
        Assert.assertTrue(pinf.positiveOrNull());
    }

    @Test
    public void testSignPredicates_27() {
        Assert.assertFalse(pinf.strictlyNegative());
    }

    @Test
    public void testSignPredicates_28() {
        Assert.assertTrue(pinf.strictlyPositive());
    }

    @Test
    public void testSignPredicates_29() {
        Assert.assertTrue(pinf.negate().negativeOrNull());
    }

    @Test
    public void testSignPredicates_30() {
        Assert.assertFalse(pinf.negate().positiveOrNull());
    }

    @Test
    public void testSignPredicates_31() {
        Assert.assertTrue(pinf.negate().strictlyNegative());
    }

    @Test
    public void testSignPredicates_32() {
        Assert.assertFalse(pinf.negate().strictlyPositive());
    }

    @Test
    public void testSignPredicates_33() {
        Assert.assertTrue(ninf.negativeOrNull());
    }

    @Test
    public void testSignPredicates_34() {
        Assert.assertFalse(ninf.positiveOrNull());
    }

    @Test
    public void testSignPredicates_35() {
        Assert.assertTrue(ninf.strictlyNegative());
    }

    @Test
    public void testSignPredicates_36() {
        Assert.assertFalse(ninf.strictlyPositive());
    }

    @Test
    public void testSignPredicates_37() {
        Assert.assertFalse(ninf.negate().negativeOrNull());
    }

    @Test
    public void testSignPredicates_38() {
        Assert.assertTrue(ninf.negate().positiveOrNull());
    }

    @Test
    public void testSignPredicates_39() {
        Assert.assertFalse(ninf.negate().strictlyNegative());
    }

    @Test
    public void testSignPredicates_40() {
        Assert.assertTrue(ninf.negate().strictlyPositive());
    }

    @Test
    public void testSpecialConstructors_1() {
        Assert.assertEquals(ninf, field.newDfp(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testSpecialConstructors_2() {
        Assert.assertEquals(ninf, field.newDfp("-Infinity"));
    }

    @Test
    public void testSpecialConstructors_3() {
        Assert.assertEquals(pinf, field.newDfp(Double.POSITIVE_INFINITY));
    }

    @Test
    public void testSpecialConstructors_4() {
        Assert.assertEquals(pinf, field.newDfp("Infinity"));
    }

    @Test
    public void testSpecialConstructors_5() {
        Assert.assertTrue(field.newDfp(Double.NaN).isNaN());
    }

    @Test
    public void testSpecialConstructors_6() {
        Assert.assertTrue(field.newDfp("NaN").isNaN());
    }

    @ParameterizedTest
    @MethodSource("Provider_testByteConstructor_1to2")
    public void testByteConstructor_1to2(double param1, int param2) {
        Assert.assertEquals(param1, new Dfp(field, (byte) param2).toString());
    }

    static public Stream<Arguments> Provider_testByteConstructor_1to2() {
        return Stream.of(arguments(0., 0), arguments(1., 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntConstructor_1_1to2_2_4_4")
    public void testIntConstructor_1_1to2_2_4_4(double param1, int param2) {
        Assert.assertEquals(param1, new Dfp(field, param2).toString());
    }

    static public Stream<Arguments> Provider_testIntConstructor_1_1to2_2_4_4() {
        return Stream.of(arguments(0., 0), arguments(1., 1), arguments(1234567890., 1234567890), arguments(0., 0L), arguments(1., 1L), arguments(1234567890., 1234567890L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntConstructor_3_3_5_5")
    public void testIntConstructor_3_3_5_5(double param1, int param2) {
        Assert.assertEquals(param1, new Dfp(field, -param2).toString());
    }

    static public Stream<Arguments> Provider_testIntConstructor_3_3_5_5() {
        return Stream.of(arguments(-1., 1), arguments(-1234567890., 1234567890), arguments(-1., 1L), arguments(-1234567890., 1234567890L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_6to9")
    public void testToString_6to9(String param1, double param2, double param3) {
        Assert.assertEquals(param1, param2, field.newDfp(param3).toString());
    }

    static public Stream<Arguments> Provider_testToString_6to9() {
        return Stream.of(arguments("toString #6", 1.2300000000000000e100, 1.23e100), arguments("toString #7", -1.2300000000000000e100, -1.23e100), arguments("toString #8", 12345678.1234, 12345678.1234), arguments("toString #9", 0.00001234, 0.00001234));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntValue_1_3to5")
    public void testIntValue_1_3to5(String param1, int param2, int param3) {
        Assert.assertEquals(param1, param2, field.newDfp(param3).intValue());
    }

    static public Stream<Arguments> Provider_testIntValue_1_3to5() {
        return Stream.of(arguments("intValue #1", 1234, 1234), arguments("intValue #3", 1234, 1234.5), arguments("intValue #4", 1235, 1234.500001), arguments("intValue #5", 2147483647, 1e1000));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntValue_2_6")
    public void testIntValue_2_6(String param1, int param2, int param3) {
        Assert.assertEquals(param1, -param2, field.newDfp(param3).intValue());
    }

    static public Stream<Arguments> Provider_testIntValue_2_6() {
        return Stream.of(arguments("intValue #2", 1234, -1234), arguments("intValue #6", 2147483648, -1e1000));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLog10K_1to4")
    public void testLog10K_1to4(String param1, int param2, int param3) {
        Assert.assertEquals(param1, param2, field.newDfp(param3).log10K());
    }

    static public Stream<Arguments> Provider_testLog10K_1to4() {
        return Stream.of(arguments("log10K #1", 1, 123456), arguments("log10K #2", 2, 123456789), arguments("log10K #3", 0, 2), arguments("log10K #3", 0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLog10_1to14")
    public void testLog10_1to14(String param1, int param2, int param3) {
        Assert.assertEquals(param1, param2, field.newDfp(param3).intLog10());
    }

    static public Stream<Arguments> Provider_testLog10_1to14() {
        return Stream.of(arguments("log10 #1", 1, 12), arguments("log10 #2", 2, 123), arguments("log10 #3", 3, 1234), arguments("log10 #4", 4, 12345), arguments("log10 #5", 5, 123456), arguments("log10 #6", 6, 1234567), arguments("log10 #6", 7, 12345678), arguments("log10 #7", 8, 123456789), arguments("log10 #8", 9, 1234567890), arguments("log10 #9", 10, 12345678901), arguments("log10 #10", 11, 123456789012), arguments("log10 #11", 12, 1234567890123), arguments("log10 #12", 0, 2), arguments("log10 #13", 0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLog10_15to16")
    public void testLog10_15to16(String param1, int param2, double param3) {
        Assert.assertEquals(param1, -param2, field.newDfp(param3).intLog10());
    }

    static public Stream<Arguments> Provider_testLog10_15to16() {
        return Stream.of(arguments("log10 #14", 1, 0.12), arguments("log10 #15", 2, 0.012));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsZero_3to4")
    public void testIsZero_3to4(double param1) {
        Assert.assertTrue(field.newDfp(+param1).isZero());
    }

    static public Stream<Arguments> Provider_testIsZero_3to4() {
        return Stream.of(arguments(0.0), arguments(0.0));
    }
}
