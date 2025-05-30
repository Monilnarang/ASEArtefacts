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

public class AccurateMathTest_Purified {

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
    public void testPowSpecialCases_14() {
        assertEquals("pow(Inf, 0.5) should be Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(Double.POSITIVE_INFINITY, 0.5), 1.0);
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
    public void testPowSpecialCases_20() {
        assertEquals("pow(-Inf, 3.0) should be -Inf", Double.NEGATIVE_INFINITY, AccurateMath.pow(Double.NEGATIVE_INFINITY, 3.0), 1.0);
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
    public void testPowSpecialCases_23() {
        assertEquals("pow(Inf, 3.5) should be Inf", Double.POSITIVE_INFINITY, AccurateMath.pow(Double.POSITIVE_INFINITY, 3.5), 1.0);
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
    public void testPowSpecialCases_26() {
        assertTrue("pow(NaN, -Infinity) should be NaN", Double.isNaN(AccurateMath.pow(Double.NaN, Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testPowSpecialCases_27() {
        assertEquals("pow(NaN, 0.0) should be 1.0", 1.0, AccurateMath.pow(Double.NaN, 0.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_28() {
        assertEquals("pow(-Infinity, -Infinity) should be 0.0", 0.0, AccurateMath.pow(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_29() {
        assertEquals("pow(-huge, -huge) should be 0.0", 0.0, AccurateMath.pow(-Double.MAX_VALUE, -Double.MAX_VALUE), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_30() {
        assertTrue("pow(-huge,  huge) should be +Inf", Double.isInfinite(AccurateMath.pow(-Double.MAX_VALUE, Double.MAX_VALUE)));
    }

    @Test
    public void testPowSpecialCases_31() {
        assertTrue("pow(NaN, -Infinity) should be NaN", Double.isNaN(AccurateMath.pow(Double.NaN, Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testPowSpecialCases_32() {
        assertEquals("pow(NaN, -0.0) should be 1.0", 1.0, AccurateMath.pow(Double.NaN, -0.0), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_33() {
        assertEquals("pow(-Infinity, -Infinity) should be 0.0", 0.0, AccurateMath.pow(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), Precision.EPSILON);
    }

    @Test
    public void testPowSpecialCases_34() {
        assertEquals("pow(-huge, -huge) should be 0.0", 0.0, AccurateMath.pow(-Double.MAX_VALUE, -Double.MAX_VALUE), Precision.EPSILON);
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
    public void testPowSpecialCases_41() {
        assertEquals("pow(-Inf, 1.0) should be -Inf", Double.NEGATIVE_INFINITY, AccurateMath.pow(Double.NEGATIVE_INFINITY, 1.0), 1.0);
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
    public void testAtan2SpecialCases_3() {
        assertAtan2(+0.0, 0.0, 0.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_4() {
        assertAtan2(+0.0, -0.0, 1.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_5() {
        assertAtan2(+0.0, 0.1, 0.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_6() {
        assertAtan2(+0.0, -0.1, 1.0, 1.0);
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
    public void testAtan2SpecialCases_9() {
        assertAtan2(-0.0, 0.0, -0.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_10() {
        assertAtan2(-0.0, -0.0, -1.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_11() {
        assertAtan2(-0.0, 0.1, -0.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_12() {
        assertAtan2(-0.0, -0.1, -1.0, 1.0);
    }

    @Test
    public void testAtan2SpecialCases_15() {
        assertAtan2(+0.1, 0.0, 1.0, 2.0);
    }

    @Test
    public void testAtan2SpecialCases_16() {
        assertAtan2(+0.1, -0.0, 1.0, 2.0);
    }

    @Test
    public void testAtan2SpecialCases_17() {
        assertAtan2(+0.1, 0.1, 1.0, 4.0);
    }

    @Test
    public void testAtan2SpecialCases_18() {
        assertAtan2(+0.1, -0.1, 3.0, 4.0);
    }

    @Test
    public void testAtan2SpecialCases_21() {
        assertAtan2(-0.1, 0.0, -1.0, 2.0);
    }

    @Test
    public void testAtan2SpecialCases_22() {
        assertAtan2(-0.1, -0.0, -1.0, 2.0);
    }

    @Test
    public void testAtan2SpecialCases_23() {
        assertAtan2(-0.1, 0.1, -1.0, 4.0);
    }

    @Test
    public void testAtan2SpecialCases_24() {
        assertAtan2(-0.1, -0.1, -3.0, 4.0);
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
    public void testNextAfter_2() {
        assertEquals(-15.999999999999996, AccurateMath.nextAfter(-15.999999999999998, 34.27555555555555), 0.0);
    }

    @Test
    public void testNextAfter_3() {
        assertEquals(15.999999999999996, AccurateMath.nextDown(15.999999999999998), 0.0);
    }

    @Test
    public void testNextAfter_4() {
        assertEquals(-15.999999999999996, AccurateMath.nextAfter(-15.999999999999998, 2.142222222222222), 0.0);
    }

    @Test
    public void testNextAfter_5() {
        assertEquals(8.000000000000002, AccurateMath.nextAfter(8.0, 34.27555555555555), 0.0);
    }

    @Test
    public void testNextAfter_6() {
        assertEquals(-7.999999999999999, AccurateMath.nextAfter(-8.0, 34.27555555555555), 0.0);
    }

    @Test
    public void testNextAfter_7() {
        assertEquals(7.999999999999999, AccurateMath.nextAfter(8.0, 2.142222222222222), 0.0);
    }

    @Test
    public void testNextAfter_8() {
        assertEquals(-7.999999999999999, AccurateMath.nextAfter(-8.0, 2.142222222222222), 0.0);
    }

    @Test
    public void testNextAfter_9() {
        assertEquals(2.308922399667661E-4, AccurateMath.nextAfter(2.3089223996676606E-4, 2.308922399667661E-4), 0.0);
    }

    @Test
    public void testNextAfter_10() {
        assertEquals(2.3089223996676606E-4, AccurateMath.nextAfter(2.3089223996676606E-4, 2.3089223996676606E-4), 0.0);
    }

    @Test
    public void testNextAfter_11() {
        assertEquals(2.3089223996676603E-4, AccurateMath.nextAfter(2.3089223996676606E-4, 2.3089223996676603E-4), 0.0);
    }

    @Test
    public void testNextAfter_12() {
        assertEquals(2.3089223996676603E-4, AccurateMath.nextAfter(2.3089223996676606E-4, -2.308922399667661E-4), 0.0);
    }

    @Test
    public void testNextAfter_13() {
        assertEquals(2.3089223996676603E-4, AccurateMath.nextAfter(2.3089223996676606E-4, -2.3089223996676606E-4), 0.0);
    }

    @Test
    public void testNextAfter_14() {
        assertEquals(2.3089223996676603E-4, AccurateMath.nextAfter(2.3089223996676606E-4, -2.3089223996676603E-4), 0.0);
    }

    @Test
    public void testNextAfter_15() {
        assertEquals(-2.3089223996676603E-4, AccurateMath.nextAfter(-2.3089223996676606E-4, 2.308922399667661E-4), 0.0);
    }

    @Test
    public void testNextAfter_16() {
        assertEquals(-2.3089223996676603E-4, AccurateMath.nextAfter(-2.3089223996676606E-4, 2.3089223996676606E-4), 0.0);
    }

    @Test
    public void testNextAfter_17() {
        assertEquals(-2.3089223996676603E-4, AccurateMath.nextAfter(-2.3089223996676606E-4, 2.3089223996676603E-4), 0.0);
    }

    @Test
    public void testNextAfter_18() {
        assertEquals(-2.308922399667661E-4, AccurateMath.nextAfter(-2.3089223996676606E-4, -2.308922399667661E-4), 0.0);
    }

    @Test
    public void testNextAfter_19() {
        assertEquals(-2.3089223996676606E-4, AccurateMath.nextAfter(-2.3089223996676606E-4, -2.3089223996676606E-4), 0.0);
    }

    @Test
    public void testNextAfter_20() {
        assertEquals(-2.3089223996676603E-4, AccurateMath.nextAfter(-2.3089223996676606E-4, -2.3089223996676603E-4), 0.0);
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
    public void testDoubleScalbSpecialCases_1() {
        assertEquals(2.5269841324701218E-175, AccurateMath.scalb(2.2250738585072014E-308, 442), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_2() {
        assertEquals(1.307993905256674E297, AccurateMath.scalb(1.1102230246251565E-16, 1040), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_3() {
        assertEquals(7.2520887996488946E-217, AccurateMath.scalb(Double.MIN_VALUE, 356), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_4() {
        assertEquals(8.98846567431158E307, AccurateMath.scalb(Double.MIN_VALUE, 2097), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_5() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(Double.MIN_VALUE, 2098), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_6() {
        assertEquals(1.1125369292536007E-308, AccurateMath.scalb(2.225073858507201E-308, -1), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_7() {
        assertEquals(1.0E-323, AccurateMath.scalb(Double.MAX_VALUE, -2097), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_8() {
        assertEquals(Double.MIN_VALUE, AccurateMath.scalb(Double.MAX_VALUE, -2098), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_9() {
        assertEquals(0, AccurateMath.scalb(Double.MAX_VALUE, -2099), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_10() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(Double.POSITIVE_INFINITY, -1000000), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_11() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-1.1102230246251565E-16, 1078), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_12() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-1.1102230246251565E-16, 1079), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_13() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-2.2250738585072014E-308, 2047), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_14() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-2.2250738585072014E-308, 2048), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_15() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-1.7976931348623157E308, 2147483647), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_16() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(+1.7976931348623157E308, 2147483647), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_17() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-1.1102230246251565E-16, 2147483647), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_18() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(+1.1102230246251565E-16, 2147483647), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_19() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.scalb(-2.2250738585072014E-308, 2147483647), 0D);
    }

    @Test
    public void testDoubleScalbSpecialCases_20() {
        assertEquals(Double.POSITIVE_INFINITY, AccurateMath.scalb(+2.2250738585072014E-308, 2147483647), 0D);
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
    public void testFloatScalbSpecialCases_3() {
        assertEquals(7.555786e22f, AccurateMath.scalb(Float.MAX_VALUE, -52), 0F);
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
    public void testFloatScalbSpecialCases_6() {
        assertEquals(5.8774718e-39f, AccurateMath.scalb(1.1754944e-38f, -1), 0F);
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
    public void testFloatScalbSpecialCases_9() {
        assertEquals(0, AccurateMath.scalb(Float.MAX_VALUE, -278), 0F);
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
    public void testFloatScalbSpecialCases_12() {
        assertEquals(Float.NEGATIVE_INFINITY, AccurateMath.scalb(-1.1e-7f, 152), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_13() {
        assertEquals(Float.POSITIVE_INFINITY, AccurateMath.scalb(3.4028235E38f, 2147483647), 0F);
    }

    @Test
    public void testFloatScalbSpecialCases_14() {
        assertEquals(Float.NEGATIVE_INFINITY, AccurateMath.scalb(-3.4028235E38f, 2147483647), 0F);
    }

    @Test
    public void testSignumDouble_1_testMerged_1() {
        final double delta = 0.0;
        assertEquals(1.0, AccurateMath.signum(2.0), delta);
        assertEquals(0.0, AccurateMath.signum(0.0), delta);
        assertEquals(-1.0, AccurateMath.signum(-2.0), delta);
    }

    @Test
    public void testSignumDouble_4() {
        Assert.assertTrue(Double.isNaN(AccurateMath.signum(Double.NaN)));
    }

    @Test
    public void testSignumFloat_1_testMerged_1() {
        final float delta = 0.0F;
        assertEquals(1.0F, AccurateMath.signum(2.0F), delta);
        assertEquals(0.0F, AccurateMath.signum(0.0F), delta);
        assertEquals(-1.0F, AccurateMath.signum(-2.0F), delta);
    }

    @Test
    public void testSignumFloat_4() {
        Assert.assertTrue(Double.isNaN(AccurateMath.signum(Float.NaN)));
    }

    @Test
    public void testLogWithBase_1() {
        assertEquals(2.0, AccurateMath.log(2, 4), 0);
    }

    @Test
    public void testLogWithBase_2() {
        assertEquals(3.0, AccurateMath.log(2, 8), 0);
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
    public void testLogWithBase_6() {
        assertEquals(0, AccurateMath.log(0, 10), 0);
    }

    @Test
    public void testLogWithBase_7() {
        assertEquals(Double.NEGATIVE_INFINITY, AccurateMath.log(10, 0), 0);
    }
}
