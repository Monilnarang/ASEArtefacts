package org.apache.commons.math4.legacy.core.jdkmath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.apache.commons.numbers.core.ArithmeticUtils;
import org.apache.commons.numbers.core.Precision;
import org.apache.commons.math4.legacy.core.dfp.Dfp;
import org.apache.commons.math4.legacy.core.dfp.DfpField;
import org.apache.commons.math4.legacy.core.dfp.DfpMath;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.math4.core.jdkmath.AccurateMath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AccurateMathTest_Parameterized {

    private static final double MAX_ERROR_ULP = 0.51;

    private static final int NUMBER_OF_TRIALS = 1000;

    private DfpField field;

    private UniformRandomProvider generator;

    @Before
    public void setUp() {
        field = new DfpField(40);
        generator = RandomSource.MT.create(6176597458463500194L);
    }

    private static void assertAtan2(double y, double x, double numerator, double denominator) {
        final double v = AccurateMath.atan2(y, x);
        if (numerator == 0) {
            Assertions.assertEquals(numerator, v, () -> String.format("atan2(%s, %s) should be %s but was %s", y, x, numerator, v));
        } else {
            final double expected = AccurateMath.PI * numerator / denominator;
            Assertions.assertEquals(expected, v, Precision.EPSILON, () -> String.format("atan2(%s, %s) should be pi * %s / %s", y, x, numerator, denominator));
        }
    }

    private Dfp cosh(Dfp x) {
        return DfpMath.exp(x).add(DfpMath.exp(x.negate())).divide(2);
    }

    private Dfp sinh(Dfp x) {
        return DfpMath.exp(x).subtract(DfpMath.exp(x.negate())).divide(2);
    }

    private Dfp tanh(Dfp x) {
        return sinh(x).divide(cosh(x));
    }

    private Dfp cbrt(Dfp x) {
        boolean negative = false;
        if (x.lessThan(field.getZero())) {
            negative = true;
            x = x.negate();
        }
        Dfp y = DfpMath.pow(x, field.getOne().divide(3));
        if (negative) {
            y = y.negate();
        }
        return y;
    }

    private long poorManFloorDiv(long a, long b) {
        BigInteger q0 = BigInteger.valueOf(a / b);
        BigInteger r0 = BigInteger.valueOf(a % b);
        BigInteger fd = BigInteger.valueOf(Integer.MIN_VALUE);
        BigInteger bigB = BigInteger.valueOf(b);
        for (int k = -2; k < 2; ++k) {
            BigInteger bigK = BigInteger.valueOf(k);
            BigInteger q = q0.subtract(bigK);
            BigInteger r = r0.add(bigK.multiply(bigB));
            if (r.abs().compareTo(bigB.abs()) < 0 && (r.longValue() == 0L || ((r.longValue() ^ b) & 0x8000000000000000L) == 0)) {
                if (fd.compareTo(q) < 0) {
                    fd = q;
                }
            }
        }
        return fd.longValue();
    }

    private long poorManFloorMod(long a, long b) {
        return a - b * poorManFloorDiv(a, b);
    }

    @Test
    public void testConstants_1() {
        assertEquals(Math.PI, AccurateMath.PI, 1.0e-20);
    }

    @Test
    public void testConstants_2() {
        assertEquals(Math.E, AccurateMath.E, 1.0e-20);
    }

    @Test
    public void testAtan2_1() {
        double y1 = 1.2713504628280707e10;
        double x1 = -5.674940885228782e-10;
        assertEquals(Math.atan2(y1, x1), AccurateMath.atan2(y1, x1), 2 * Precision.EPSILON);
    }

    @Test
    public void testAtan2_2() {
        double y2 = 0.0;
        double x2 = Double.POSITIVE_INFINITY;
        assertEquals(Math.atan2(y2, x2), AccurateMath.atan2(y2, x2), Precision.SAFE_MIN);
    }

    @Test
    public void testLogSpecialCases_1() {
        assertEquals("Log of zero should be -Inf", Double.NEGATIVE_INFINITY, AccurateMath.log(0.0), 1.0);
    }

    @Test
    public void testLogSpecialCases_2() {
        assertEquals("Log of -zero should be -Inf", Double.NEGATIVE_INFINITY, AccurateMath.log(-0.0), 1.0);
    }

    @Test
    public void testLogSpecialCases_3() {
        assertTrue("Log of NaN should be NaN", Double.isNaN(AccurateMath.log(Double.NaN)));
    }

    @Test
    public void testLogSpecialCases_4() {
        assertTrue("Log of negative number should be NaN", Double.isNaN(AccurateMath.log(-1.0)));
    }

    @Test
    public void testLogSpecialCases_5() {
        assertEquals("Log of Double.MIN_VALUE should be -744.4400719213812", -744.4400719213812, AccurateMath.log(Double.MIN_VALUE), Precision.EPSILON);
    }

    @Test
    public void testLogSpecialCases_6() {
        assertEquals("Log of infinity should be infinity", Double.POSITIVE_INFINITY, AccurateMath.log(Double.POSITIVE_INFINITY), 1.0);
    }

    @Test
    public void testExpSpecialCases_1() {
        assertEquals(Double.MIN_VALUE, AccurateMath.exp(-745.1332191019411), Precision.EPSILON);
    }

    @Test
    public void testExpSpecialCases_2() {
        assertEquals("exp(-745.1332191019412) should be 0.0", 0.0, AccurateMath.exp(-745.1332191019412), Precision.EPSILON);
    }

    @Test
    public void testExpSpecialCases_3() {
        assertTrue("exp of NaN should be NaN", Double.isNaN(AccurateMath.exp(Double.NaN)));
    }

    @Test
    public void testExpSpecialCases_4() {
        assertEquals("exp of infinity should be infinity", Double.POSITIVE_INFINITY, AccurateMath.exp(Double.POSITIVE_INFINITY), 1.0);
    }

    @Test
    public void testExpSpecialCases_5() {
        assertEquals("exp of -infinity should be 0.0", 0.0, AccurateMath.exp(Double.NEGATIVE_INFINITY), Precision.EPSILON);
    }

    @Test
    public void testExpSpecialCases_6() {
        assertEquals("exp(1) should be Math.E", Math.E, AccurateMath.exp(1.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_1() {
        assertEquals("pow(-1, 0) should be 1.0", 1.0, AccurateMath.pow(-1.0, 0.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_2() {
        assertEquals("pow(-1, -0) should be 1.0", 1.0, AccurateMath.pow(-1.0, -0.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_3() {
        assertEquals("pow(PI, 1.0) should be PI", AccurateMath.PI, AccurateMath.pow(AccurateMath.PI, 1.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_4() {
        assertEquals("pow(-PI, 1.0) should be -PI", -AccurateMath.PI, AccurateMath.pow(-AccurateMath.PI, 1.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_5() {
        assertTrue("pow(PI, NaN) should be NaN", Double.isNaN(AccurateMath.pow(Math.PI, Double.NaN)));
    }

    @Test
    public void testPowSpecialCases_6() {
        assertTrue("pow(NaN, PI) should be NaN", Double.isNaN(AccurateMath.pow(Double.NaN, Math.PI)));
    }

    @Test
    public void testPowSpecialCases_7() {
        assertEquals("pow(2.0, Infinity) should be Infinity", Double.POSITIVE_INFINITY, AccurateMath.pow(2.0, Double.POSITIVE_INFINITY), 1.0);
    }

    @Test
    public void testPowSpecialCases_8() {
        assertEquals("pow(0.5, -Infinity) should be Infinity", Double.POSITIVE_INFINITY, AccurateMath.pow(0.5, Double.NEGATIVE_INFINITY), 1.0);
    }

    @Test
    public void testPowSpecialCases_9() {
        assertEquals("pow(0.5, Infinity) should be 0.0", 0.0, AccurateMath.pow(0.5, Double.POSITIVE_INFINITY), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_10() {
        assertEquals("pow(2.0, -Infinity) should be 0.0", 0.0, AccurateMath.pow(2.0, Double.NEGATIVE_INFINITY), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_11() {
        assertEquals("pow(0.0, 0.5) should be 0.0", 0.0, AccurateMath.pow(0.0, 0.5), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_12() {
        assertEquals("pow(Infinity, -0.5) should be 0.0", 0.0, AccurateMath.pow(Double.POSITIVE_INFINITY, -0.5), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_13() {
        assertEquals("pow(0.0, -0.5) should be Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(0.0, -0.5), 1.0);
    }

    @Test
    public void testPowSpecialCases_15() {
        assertEquals("pow(-0.0, -3.0) should be -Inf", Double.NEGATIVE_INFINITY, AccurateMath.pow(-0.0, -3.0), 1.0);
    }

    @Test
    public void testPowSpecialCases_16() {
        assertEquals("pow(-0.0, Infinity) should be 0.0", 0.0, AccurateMath.pow(-0.0, Double.POSITIVE_INFINITY), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_17() {
        assertTrue("pow(-0.0, NaN) should be NaN", Double.isNaN(AccurateMath.pow(-0.0, Double.NaN)));
    }

    @Test
    public void testPowSpecialCases_18() {
        assertEquals("pow(-0.0, -tiny) should be Infinity", Double.POSITIVE_INFINITY, AccurateMath.pow(-0.0, -Double.MIN_VALUE), 1.0);
    }

    @Test
    public void testPowSpecialCases_19() {
        assertEquals("pow(-0.0, -huge) should be Infinity", Double.POSITIVE_INFINITY, AccurateMath.pow(-0.0, -Double.MAX_VALUE), 1.0);
    }

    @Test
    public void testPowSpecialCases_21_testMerged_21() {
        final double EXACT = -1.0;
        assertEquals("pow(-Inf, -3.0) should be -0.0", -0.0, AccurateMath.pow(Double.NEGATIVE_INFINITY, -3.0), EXACT);
        assertEquals("pow(-Inf, -1.0) should be -0.0", -0.0, AccurateMath.pow(Double.NEGATIVE_INFINITY, -1.0), EXACT);
        assertEquals("pow(-Inf, -2.0) should be 0.0", 0.0, AccurateMath.pow(Double.NEGATIVE_INFINITY, -2.0), EXACT);
        assertEquals("pow(-0.0, 1.0) should be -0.0", -0.0, AccurateMath.pow(-0.0, 1.0), EXACT);
        assertEquals("pow(0.0, 1.0) should be 0.0", 0.0, AccurateMath.pow(0.0, 1.0), EXACT);
        assertEquals("pow(0.0, +Inf) should be 0.0", 0.0, AccurateMath.pow(0.0, Double.POSITIVE_INFINITY), EXACT);
        assertEquals("pow(-0.0, even) should be 0.0", 0.0, AccurateMath.pow(-0.0, 6.0), EXACT);
        assertEquals("pow(-0.0, odd) should be -0.0", -0.0, AccurateMath.pow(-0.0, 13.0), EXACT);
        assertEquals("pow(-0.0, -even) should be +Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(-0.0, -6.0), EXACT);
        assertEquals("pow(-0.0, -odd) should be -Inf", Double.NEGATIVE_INFINITY, AccurateMath.pow(-0.0, -13.0), EXACT);
        assertEquals("pow(-2.0, 4.0) should be 16.0", 16.0, AccurateMath.pow(-2.0, 4.0), EXACT);
        assertEquals("pow(-2.0, 4.5) should be NaN", Double.NaN, AccurateMath.pow(-2.0, 4.5), EXACT);
        assertEquals("pow(-0.0, -0.0) should be 1.0", 1.0, AccurateMath.pow(-0.0, -0.0), EXACT);
        assertEquals("pow(-0.0, 0.0) should be 1.0", 1.0, AccurateMath.pow(-0.0, 0.0), EXACT);
        assertEquals("pow(0.0, -0.0) should be 1.0", 1.0, AccurateMath.pow(0.0, -0.0), EXACT);
        assertEquals("pow(0.0, 0.0) should be 1.0", 1.0, AccurateMath.pow(0.0, 0.0), EXACT);
    }

    @Test
    public void testPowSpecialCases_22() {
        assertEquals("pow(-0.0, -3.5) should be Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(-0.0, -3.5), 1.0);
    }

    @Test
    public void testPowSpecialCases_24() {
        assertEquals("pow(-2.0, 3.0) should be -8.0", -8.0, AccurateMath.pow(-2.0, 3.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_25() {
        assertTrue("pow(-2.0, 3.5) should be NaN", Double.isNaN(AccurateMath.pow(-2.0, 3.5)));
    }

    @Test
    public void testPowSpecialCases_27() {
        assertEquals("pow(NaN, 0.0) should be 1.0", 1.0, AccurateMath.pow(Double.NaN, 0.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_30() {
        assertTrue("pow(-huge,  huge) should be +Inf", Double.isInfinite(AccurateMath.pow(-Double.MAX_VALUE, Double.MAX_VALUE)));
    }

    @Test
    public void testPowSpecialCases_32() {
        assertEquals("pow(NaN, -0.0) should be 1.0", 1.0, AccurateMath.pow(Double.NaN, -0.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_35() {
        assertEquals("pow(-huge,  huge) should be +Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(-Double.MAX_VALUE, Double.MAX_VALUE), 1.0);
    }

    @Test
    public void testPowSpecialCases_36() {
        assertTrue("pow(+Inf, NaN) should be NaN", Double.isNaN(AccurateMath.pow(Double.POSITIVE_INFINITY, Double.NaN)));
    }

    @Test
    public void testPowSpecialCases_37() {
        assertTrue("pow(1.0, +Inf) should be NaN", Double.isNaN(AccurateMath.pow(1.0, Double.POSITIVE_INFINITY)));
    }

    @Test
    public void testPowSpecialCases_38() {
        assertTrue("pow(-Inf, NaN) should be NaN", Double.isNaN(AccurateMath.pow(Double.NEGATIVE_INFINITY, Double.NaN)));
    }

    @Test
    public void testPowSpecialCases_42() {
        assertEquals("pow(-Inf, 2.0) should be +Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(Double.NEGATIVE_INFINITY, 2.0), 1.0);
    }

    @Test
    public void testPowSpecialCases_43() {
        assertTrue("pow(1.0, -Inf) should be NaN", Double.isNaN(AccurateMath.pow(1.0, Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testAtan2SpecialCases_1() {
        assertTrue("atan2(NaN, 0.0) should be NaN", Double.isNaN(AccurateMath.atan2(Double.NaN, 0.0)));
    }

    @Test
    public void testAtan2SpecialCases_2() {
        assertTrue("atan2(0.0, NaN) should be NaN", Double.isNaN(AccurateMath.atan2(0.0, Double.NaN)));
    }

    @Test
    public void testAtan2SpecialCases_7_testMerged_7() {
        final double pinf = Double.POSITIVE_INFINITY;
        final double ninf = Double.NEGATIVE_INFINITY;
        assertAtan2(+0.0, pinf, 0.0, 1.0);
        assertAtan2(+0.0, ninf, 1.0, 1.0);
        assertAtan2(-0.0, pinf, -0.0, 1.0);
        assertAtan2(-0.0, ninf, -1.0, 1.0);
        assertAtan2(+0.1, pinf, 0.0, 1.0);
        assertAtan2(+0.1, ninf, 1.0, 1.0);
        assertAtan2(-0.1, pinf, -0.0, 1.0);
        assertAtan2(-0.1, ninf, -1.0, 1.0);
        assertAtan2(pinf, 0.0, 1.0, 2.0);
        assertAtan2(pinf, -0.0, 1.0, 2.0);
        assertAtan2(pinf, 0.1, 1.0, 2.0);
        assertAtan2(pinf, -0.1, 1.0, 2.0);
        assertAtan2(pinf, pinf, 1.0, 4.0);
        assertAtan2(pinf, ninf, 3.0, 4.0);
        assertAtan2(ninf, 0.0, -1.0, 2.0);
        assertAtan2(ninf, -0.0, -1.0, 2.0);
        assertAtan2(ninf, 0.1, -1.0, 2.0);
        assertAtan2(ninf, -0.1, -1.0, 2.0);
        assertAtan2(ninf, pinf, -1.0, 4.0);
        assertAtan2(ninf, ninf, -3.0, 4.0);
    }

    @Test
    public void testAcosSpecialCases_1() {
        assertTrue("acos(NaN) should be NaN", Double.isNaN(AccurateMath.acos(Double.NaN)));
    }

    @Test
    public void testAcosSpecialCases_2() {
        assertTrue("acos(-1.1) should be NaN", Double.isNaN(AccurateMath.acos(-1.1)));
    }

    @Test
    public void testAcosSpecialCases_3() {
        assertTrue("acos(-1.1) should be NaN", Double.isNaN(AccurateMath.acos(1.1)));
    }

    @Test
    public void testAcosSpecialCases_4() {
        assertEquals("acos(-1.0) should be PI", AccurateMath.acos(-1.0), AccurateMath.PI, Precision.EPSILON);
    }

    @Test
    public void testAcosSpecialCases_5() {
        assertEquals("acos(1.0) should be 0.0", AccurateMath.acos(1.0), 0.0, Precision.EPSILON);
    }

    @Test
    public void testAcosSpecialCases_6() {
        assertEquals("acos(0.0) should be PI/2", AccurateMath.acos(0.0), AccurateMath.PI / 2.0, Precision.EPSILON);
    }

    @Test
    public void testAsinSpecialCases_1() {
        assertTrue("asin(NaN) should be NaN", Double.isNaN(AccurateMath.asin(Double.NaN)));
    }

    @Test
    public void testAsinSpecialCases_2() {
        assertTrue("asin(1.1) should be NaN", Double.isNaN(AccurateMath.asin(1.1)));
    }

    @Test
    public void testAsinSpecialCases_3() {
        assertTrue("asin(-1.1) should be NaN", Double.isNaN(AccurateMath.asin(-1.1)));
    }

    @Test
    public void testAsinSpecialCases_4() {
        assertEquals("asin(1.0) should be PI/2", AccurateMath.asin(1.0), AccurateMath.PI / 2.0, Precision.EPSILON);
    }

    @Test
    public void testAsinSpecialCases_5() {
        assertEquals("asin(-1.0) should be -PI/2", AccurateMath.asin(-1.0), -AccurateMath.PI / 2.0, Precision.EPSILON);
    }

    @Test
    public void testAsinSpecialCases_6() {
        assertEquals("asin(0.0) should be 0.0", AccurateMath.asin(0.0), 0.0, Precision.EPSILON);
    }

    @Test
    public void testNextAfter_1() {
        assertEquals(16.0, AccurateMath.nextUp(15.999999999999998), 0.0);
    }

    @Test
    public void testNextAfter_3() {
        assertEquals(15.999999999999996, AccurateMath.nextDown(15.999999999999998), 0.0);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_1() {
        assertEquals(-Double.MAX_VALUE, AccurateMath.nextAfter(Double.NEGATIVE_INFINITY, 0D), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_2() {
        assertEquals(Double.MAX_VALUE, AccurateMath.nextAfter(Double.POSITIVE_INFINITY, 0D), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_3() {
        assertEquals(Double.NaN, AccurateMath.nextAfter(Double.NaN, 0D), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_4() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.nextAfter(Double.MAX_VALUE, Double.POSITIVE_INFINITY), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_5() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.nextAfter(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_6() {
        assertEquals(Double.MIN_VALUE, AccurateMath.nextAfter(0D, 1D), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_7() {
        assertEquals(-Double.MIN_VALUE, AccurateMath.nextAfter(0D, -1D), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_8() {
        assertEquals(0D, AccurateMath.nextAfter(Double.MIN_VALUE, -1), 0D);
    }

    @Test
    public void testDoubleNextAfterSpecialCases_9() {
        assertEquals(0D, AccurateMath.nextAfter(-Double.MIN_VALUE, 1), 0D);
    }

    @Test
    public void testFloatNextAfterSpecialCases_1() {
        assertEquals(-Float.MAX_VALUE, AccurateMath.nextAfter(Float.NEGATIVE_INFINITY, 0F), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_2() {
        assertEquals(Float.MAX_VALUE, AccurateMath.nextAfter(Float.POSITIVE_INFINITY, 0F), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_3() {
        assertEquals(Float.NaN, AccurateMath.nextAfter(Float.NaN, 0F), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_4() {
        assertEquals(Float.POSITIVE_INFINITY, AccurateMath.nextUp(Float.MAX_VALUE), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_5() {
        assertEquals(Float.NEGATIVE_INFINITY, AccurateMath.nextDown(-Float.MAX_VALUE), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_6() {
        assertEquals(Float.MIN_VALUE, AccurateMath.nextAfter(0F, 1F), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_7() {
        assertEquals(-Float.MIN_VALUE, AccurateMath.nextAfter(0F, -1F), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_8() {
        assertEquals(0F, AccurateMath.nextAfter(Float.MIN_VALUE, -1F), 0F);
    }

    @Test
    public void testFloatNextAfterSpecialCases_9() {
        assertEquals(0F, AccurateMath.nextAfter(-Float.MIN_VALUE, 1F), 0F);
    }

    @Test
    public void testDoubleScalbSpecialCases_5() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(Double.MIN_VALUE, 2098), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_8() {
        assertEquals(Double.MIN_VALUE, AccurateMath.scalb(Double.MAX_VALUE, -2098), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_10() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(Double.POSITIVE_INFINITY, -1000000), 0D);
    }

    @Test
    public void testFloatScalbSpecialCases_1() {
        assertEquals(0f, AccurateMath.scalb(Float.MIN_VALUE, -30), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_2() {
        assertEquals(2 * Float.MIN_VALUE, AccurateMath.scalb(Float.MIN_VALUE, 1), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_4() {
        assertEquals(1.7014118e38f, AccurateMath.scalb(Float.MIN_VALUE, 276), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_5() {
        assertEquals(Float.POSITIVE_INFINITY, AccurateMath.scalb(Float.MIN_VALUE, 277), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_7() {
        assertEquals(2 * Float.MIN_VALUE, AccurateMath.scalb(Float.MAX_VALUE, -276), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_8() {
        assertEquals(Float.MIN_VALUE, AccurateMath.scalb(Float.MAX_VALUE, -277), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_10() {
        assertEquals(Float.POSITIVE_INFINITY, AccurateMath.scalb(Float.POSITIVE_INFINITY, -1000000), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_11() {
        assertEquals(-3.13994498e38f, AccurateMath.scalb(-1.1e-7f, 151), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_13() {
        assertEquals(Float.POSITIVE_INFINITY, AccurateMath.scalb(3.4028235E38f, 2147483647), 0F);
    }

    @Test
    public void testSignumDouble_4() {
        Assert.assertTrue(Double.isNaN(AccurateMath.signum(Double.NaN)));
    }

    @Test
    public void testSignumFloat_4() {
        Assert.assertTrue(Double.isNaN(AccurateMath.signum(Float.NaN)));
    }

    @Test
    public void testLogWithBase_3() {
        assertTrue(Double.isNaN(AccurateMath.log(-1, 1)));
    }

    @Test
    public void testLogWithBase_4() {
        assertTrue(Double.isNaN(AccurateMath.log(1, -1)));
    }

    @Test
    public void testLogWithBase_5() {
        assertTrue(Double.isNaN(AccurateMath.log(0, 0)));
    }

    @Test
    public void testLogWithBase_7() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.log(10, 0), 0);
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowSpecialCases_14_23")
    public void testPowSpecialCases_14_23(String param1, double param2, double param3) {
        assertEquals(param1, Double.POSITIVE_INFINITY, AccurateMath.pow(Double.POSITIVE_INFINITY, param3), param2);
    }

    static public Stream<Arguments> Provider_testPowSpecialCases_14_23() {
        return Stream.of(arguments("pow(Inf, 0.5) should be Inf", 1.0, 0.5), arguments("pow(Inf, 3.5) should be Inf", 1.0, 3.5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowSpecialCases_20_41")
    public void testPowSpecialCases_20_41(String param1, double param2, double param3) {
        assertEquals(param1, Double.NEGATIVE_INFINITY, AccurateMath.pow(Double.NEGATIVE_INFINITY, param3), param2);
    }

    static public Stream<Arguments> Provider_testPowSpecialCases_20_41() {
        return Stream.of(arguments("pow(-Inf, 3.0) should be -Inf", 1.0, 3.0), arguments("pow(-Inf, 1.0) should be -Inf", 1.0, 1.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowSpecialCases_26_31")
    public void testPowSpecialCases_26_31(String param1) {
        assertTrue(param1, Double.isNaN(AccurateMath.pow(Double.NaN, Double.NEGATIVE_INFINITY)));
    }

    static public Stream<Arguments> Provider_testPowSpecialCases_26_31() {
        return Stream.of(arguments("pow(NaN, -Infinity) should be NaN"), arguments("pow(NaN, -Infinity) should be NaN"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowSpecialCases_28_33")
    public void testPowSpecialCases_28_33(String param1, double param2) {
        assertEquals(param1, param2, AccurateMath.pow(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), Precision.EPSILON);
    }

    static public Stream<Arguments> Provider_testPowSpecialCases_28_33() {
        return Stream.of(arguments("pow(-Infinity, -Infinity) should be 0.0", 0.0), arguments("pow(-Infinity, -Infinity) should be 0.0", 0.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowSpecialCases_29_34")
    public void testPowSpecialCases_29_34(String param1, double param2) {
        assertEquals(param1, param2, AccurateMath.pow(-Double.MAX_VALUE, -Double.MAX_VALUE), Precision.EPSILON);
    }

    static public Stream<Arguments> Provider_testPowSpecialCases_29_34() {
        return Stream.of(arguments("pow(-huge, -huge) should be 0.0", 0.0), arguments("pow(-huge, -huge) should be 0.0", 0.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAtan2SpecialCases_3_5_15_17")
    public void testAtan2SpecialCases_3_5_15_17(double param1, double param2, double param3, double param4) {
        assertAtan2(+param4, param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testAtan2SpecialCases_3_5_15_17() {
        return Stream.of(arguments(0.0, 0.0, 1.0, 0.0), arguments(0.1, 0.0, 1.0, 0.0), arguments(0.0, 1.0, 2.0, 0.1), arguments(0.1, 1.0, 4.0, 0.1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAtan2SpecialCases_4_6_16_18")
    public void testAtan2SpecialCases_4_6_16_18(double param1, double param2, double param3, double param4) {
        assertAtan2(+param3, -param4, param1, param2);
    }

    static public Stream<Arguments> Provider_testAtan2SpecialCases_4_6_16_18() {
        return Stream.of(arguments(1.0, 1.0, 0.0, 0.0), arguments(1.0, 1.0, 0.0, 0.1), arguments(1.0, 2.0, 0.1, 0.0), arguments(3.0, 4.0, 0.1, 0.1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAtan2SpecialCases_9_11_21_23")
    public void testAtan2SpecialCases_9_11_21_23(double param1, double param2, double param3, double param4) {
        assertAtan2(-param3, param1, -param4, param2);
    }

    static public Stream<Arguments> Provider_testAtan2SpecialCases_9_11_21_23() {
        return Stream.of(arguments(0.0, 1.0, 0.0, 0.0), arguments(0.1, 1.0, 0.0, 0.0), arguments(0.0, 2.0, 0.1, 1.0), arguments(0.1, 4.0, 0.1, 1.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAtan2SpecialCases_10_12_22_24")
    public void testAtan2SpecialCases_10_12_22_24(double param1, double param2, double param3, double param4) {
        assertAtan2(-param2, -param3, -param4, param1);
    }

    static public Stream<Arguments> Provider_testAtan2SpecialCases_10_12_22_24() {
        return Stream.of(arguments(1.0, 0.0, 0.0, 1.0), arguments(1.0, 0.0, 0.1, 1.0), arguments(2.0, 0.1, 0.0, 1.0), arguments(4.0, 0.1, 0.1, 3.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNextAfter_2_4_6_8_15to17")
    public void testNextAfter_2_4_6_8_15to17(double param1, double param2, double param3, double param4) {
        assertEquals(-param2, AccurateMath.nextAfter(-param4, param3), param1);
    }

    static public Stream<Arguments> Provider_testNextAfter_2_4_6_8_15to17() {
        return Stream.of(arguments(0.0, 15.999999999999996, 34.27555555555555, 15.999999999999998), arguments(0.0, 15.999999999999996, 2.142222222222222, 15.999999999999998), arguments(0.0, 7.999999999999999, 34.27555555555555, 8.0), arguments(0.0, 7.999999999999999, 2.142222222222222, 8.0), arguments(0.0, 2.3089223996676603E-4, 2.308922399667661E-4, 2.3089223996676606E-4), arguments(0.0, 2.3089223996676603E-4, 2.3089223996676606E-4, 2.3089223996676606E-4), arguments(0.0, 2.3089223996676603E-4, 2.3089223996676603E-4, 2.3089223996676606E-4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNextAfter_5_7_9to11")
    public void testNextAfter_5_7_9to11(double param1, double param2, double param3, double param4) {
        assertEquals(param1, AccurateMath.nextAfter(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testNextAfter_5_7_9to11() {
        return Stream.of(arguments(8.000000000000002, 0.0, 8.0, 34.27555555555555), arguments(7.999999999999999, 0.0, 8.0, 2.142222222222222), arguments(2.308922399667661E-4, 0.0, 2.3089223996676606E-4, 2.308922399667661E-4), arguments(2.3089223996676606E-4, 0.0, 2.3089223996676606E-4, 2.3089223996676606E-4), arguments(2.3089223996676603E-4, 0.0, 2.3089223996676606E-4, 2.3089223996676603E-4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNextAfter_12to14")
    public void testNextAfter_12to14(double param1, double param2, double param3, double param4) {
        assertEquals(param1, AccurateMath.nextAfter(param3, -param4), param2);
    }

    static public Stream<Arguments> Provider_testNextAfter_12to14() {
        return Stream.of(arguments(2.3089223996676603E-4, 0.0, 2.3089223996676606E-4, 2.308922399667661E-4), arguments(2.3089223996676603E-4, 0.0, 2.3089223996676606E-4, 2.3089223996676606E-4), arguments(2.3089223996676603E-4, 0.0, 2.3089223996676606E-4, 2.3089223996676603E-4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNextAfter_18to20")
    public void testNextAfter_18to20(double param1, double param2, double param3, double param4) {
        assertEquals(-param2, AccurateMath.nextAfter(-param3, -param4), param1);
    }

    static public Stream<Arguments> Provider_testNextAfter_18to20() {
        return Stream.of(arguments(0.0, 2.308922399667661E-4, 2.3089223996676606E-4, 2.308922399667661E-4), arguments(0.0, 2.3089223996676606E-4, 2.3089223996676606E-4, 2.3089223996676606E-4), arguments(0.0, 2.3089223996676603E-4, 2.3089223996676606E-4, 2.3089223996676603E-4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDoubleScalbSpecialCases_1to2")
    public void testDoubleScalbSpecialCases_1to2(double param1, double param2, double param3, int param4) {
        assertEquals(param1, AccurateMath.scalb(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testDoubleScalbSpecialCases_1to2() {
        return Stream.of(arguments(2.5269841324701218E-175, 0D, 2.2250738585072014E-308, 442), arguments(1.307993905256674E297, 0D, 1.1102230246251565E-16, 1040));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDoubleScalbSpecialCases_3to4")
    public void testDoubleScalbSpecialCases_3to4(double param1, double param2, int param3) {
        assertEquals(param1, AccurateMath.scalb(Double.MIN_VALUE, param3), param2);
    }

    static public Stream<Arguments> Provider_testDoubleScalbSpecialCases_3to4() {
        return Stream.of(arguments(7.2520887996488946E-217, 0D, 356), arguments(8.98846567431158E307, 0D, 2097));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDoubleScalbSpecialCases_6_6")
    public void testDoubleScalbSpecialCases_6_6(double param1, double param2, double param3, int param4) {
        assertEquals(param1, AccurateMath.scalb(param3, -param4), param2);
    }

    static public Stream<Arguments> Provider_testDoubleScalbSpecialCases_6_6() {
        return Stream.of(arguments(1.1125369292536007E-308, 0D, 2.225073858507201E-308, 1), arguments(5.8774718e-39f, 0F, 1.1754944e-38f, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDoubleScalbSpecialCases_7_9")
    public void testDoubleScalbSpecialCases_7_9(double param1, double param2, int param3) {
        assertEquals(param1, AccurateMath.scalb(Double.MAX_VALUE, -param3), param2);
    }

    static public Stream<Arguments> Provider_testDoubleScalbSpecialCases_7_9() {
        return Stream.of(arguments(1.0E-323, 0D, 2097), arguments(0, 0D, 2099));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDoubleScalbSpecialCases_11to15_17_19")
    public void testDoubleScalbSpecialCases_11to15_17_19(double param1, int param2, double param3) {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-param3, param2), param1);
    }

    static public Stream<Arguments> Provider_testDoubleScalbSpecialCases_11to15_17_19() {
        return Stream.of(arguments(0D, 1078, 1.1102230246251565E-16), arguments(0D, 1079, 1.1102230246251565E-16), arguments(0D, 2047, 2.2250738585072014E-308), arguments(0D, 2048, 2.2250738585072014E-308), arguments(0D, 2147483647, 1.7976931348623157E308), arguments(0D, 2147483647, 1.1102230246251565E-16), arguments(0D, 2147483647, 2.2250738585072014E-308));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDoubleScalbSpecialCases_16_18_20")
    public void testDoubleScalbSpecialCases_16_18_20(double param1, int param2, double param3) {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(+param3, param2), param1);
    }

    static public Stream<Arguments> Provider_testDoubleScalbSpecialCases_16_18_20() {
        return Stream.of(arguments(0D, 2147483647, 1.7976931348623157E308), arguments(0D, 2147483647, 1.1102230246251565E-16), arguments(0D, 2147483647, 2.2250738585072014E-308));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFloatScalbSpecialCases_3_9")
    public void testFloatScalbSpecialCases_3_9(double param1, double param2, int param3) {
        assertEquals(param1, AccurateMath.scalb(Float.MAX_VALUE, -param3), param2);
    }

    static public Stream<Arguments> Provider_testFloatScalbSpecialCases_3_9() {
        return Stream.of(arguments(7.555786e22f, 0F, 52), arguments(0, 0F, 278));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFloatScalbSpecialCases_12_14")
    public void testFloatScalbSpecialCases_12_14(double param1, int param2, double param3) {
        assertEquals(Float.NEGATIVE_INFINITY, AccurateMath.scalb(-param3, param2), param1);
    }

    static public Stream<Arguments> Provider_testFloatScalbSpecialCases_12_14() {
        return Stream.of(arguments(0F, 152, 1.1e-7f), arguments(0F, 2147483647, 3.4028235E38f));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSignumDouble_1_testMerged_1_1")
    public void testSignumDouble_1_testMerged_1_1(double param1, double param2, double param3, double param4, double param5, double param6, double param7) {
        final double delta = param3;
        assertEquals(param1, AccurateMath.signum(param4), delta);
        assertEquals(param2, AccurateMath.signum(param5), delta);
        assertEquals(-param6, AccurateMath.signum(-param7), delta);
    }

    static public Stream<Arguments> Provider_testSignumDouble_1_testMerged_1_1() {
        return Stream.of(arguments(0.0, 1.0, 2.0, 0.0, 0.0, 1.0, 2.0), arguments(0.0F, 1.0F, 2.0F, 0.0F, 0.0F, 1.0F, 2.0F));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLogWithBase_1to2_6")
    public void testLogWithBase_1to2_6(double param1, int param2, int param3, int param4) {
        assertEquals(param1, AccurateMath.log(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testLogWithBase_1to2_6() {
        return Stream.of(arguments(2.0, 0, 2, 4), arguments(3.0, 0, 2, 8), arguments(0, 0, 0, 10));
    }
}
