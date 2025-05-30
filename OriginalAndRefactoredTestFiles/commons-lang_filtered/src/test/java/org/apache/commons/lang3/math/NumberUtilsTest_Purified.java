package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.function.Function;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class NumberUtilsTest_Purified extends AbstractLangTest {

    private static void assertCreateNumberZero(final String number, final Object zero, final Object negativeZero) {
        assertEquals(zero, NumberUtils.createNumber(number), () -> "Input: " + number);
        assertEquals(zero, NumberUtils.createNumber("+" + number), () -> "Input: +" + number);
        assertEquals(negativeZero, NumberUtils.createNumber("-" + number), () -> "Input: -" + number);
    }

    private boolean checkCreateNumber(final String val) {
        try {
            final Object obj = NumberUtils.createNumber(val);
            return obj != null;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private void compareIsCreatableWithCreateNumber(final String val, final boolean expected) {
        final boolean isValid = NumberUtils.isCreatable(val);
        final boolean canCreate = checkCreateNumber(val);
        assertTrue(isValid == expected && canCreate == expected, "Expecting " + expected + " for isCreatable/createNumber using \"" + val + "\" but got " + isValid + " and " + canCreate);
    }

    @SuppressWarnings("deprecation")
    private void compareIsNumberWithCreateNumber(final String val, final boolean expected) {
        final boolean isValid = NumberUtils.isNumber(val);
        final boolean canCreate = checkCreateNumber(val);
        assertTrue(isValid == expected && canCreate == expected, "Expecting " + expected + " for isNumber/createNumber using \"" + val + "\" but got " + isValid + " and " + canCreate);
    }

    private boolean isApplyNonNull(final String s, final Function<String, ?> function) {
        try {
            assertNotNull(function.apply(s));
            return true;
        } catch (final Exception e) {
            if (!s.matches(".*\\s.*")) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private boolean isNumberFormatParsable(final String s) {
        final NumberFormat instance = NumberFormat.getInstance();
        try {
            assertNotNull(instance.parse(s));
            return true;
        } catch (final ParseException e) {
            return false;
        }
    }

    private boolean isNumberIntegerOnlyFormatParsable(final String s) {
        final NumberFormat instance = NumberFormat.getInstance();
        instance.setParseIntegerOnly(true);
        try {
            assertNotNull(instance.parse(s));
            return true;
        } catch (final ParseException e) {
            return false;
        }
    }

    private boolean isParsableByte(final String s) {
        final boolean parsable = NumberUtils.isParsable(s);
        assertTrue(isNumberFormatParsable(s), s);
        assertTrue(isNumberIntegerOnlyFormatParsable(s), s);
        assertEquals(parsable, isApplyNonNull(s, Byte::parseByte), s);
        return parsable;
    }

    private boolean isParsableDouble(final String s) {
        final boolean parsable = NumberUtils.isParsable(s);
        assertTrue(isNumberFormatParsable(s), s);
        assertTrue(isNumberIntegerOnlyFormatParsable(s), s);
        assertEquals(parsable, isApplyNonNull(s, Double::parseDouble), s);
        return parsable;
    }

    private boolean isParsableFloat(final String s) {
        final boolean parsable = NumberUtils.isParsable(s);
        assertTrue(isNumberFormatParsable(s), s);
        assertTrue(isNumberIntegerOnlyFormatParsable(s), s);
        assertEquals(parsable, isApplyNonNull(s, Float::parseFloat), s);
        return parsable;
    }

    private boolean isParsableInteger(final String s) {
        final boolean parsable = NumberUtils.isParsable(s);
        assertTrue(isNumberFormatParsable(s), s);
        assertTrue(isNumberIntegerOnlyFormatParsable(s), s);
        assertEquals(parsable, isApplyNonNull(s, Integer::parseInt), s);
        return parsable;
    }

    private boolean isParsableLong(final String s) {
        final boolean parsable = NumberUtils.isParsable(s);
        assertTrue(isNumberFormatParsable(s), s);
        assertTrue(isNumberIntegerOnlyFormatParsable(s), s);
        assertEquals(parsable, isApplyNonNull(s, Long::parseLong), s);
        return parsable;
    }

    private boolean isParsableShort(final String s) {
        final boolean parsable = NumberUtils.isParsable(s);
        assertTrue(isNumberFormatParsable(s), s);
        assertTrue(isNumberIntegerOnlyFormatParsable(s), s);
        assertEquals(parsable, isApplyNonNull(s, Short::parseShort), s);
        return parsable;
    }

    @Test
    public void compareByte_1() {
        assertTrue(NumberUtils.compare((byte) -3, (byte) 0) < 0);
    }

    @Test
    public void compareByte_2() {
        assertEquals(0, NumberUtils.compare((byte) 113, (byte) 113));
    }

    @Test
    public void compareByte_3() {
        assertTrue(NumberUtils.compare((byte) 123, (byte) 32) > 0);
    }

    @Test
    public void compareInt_1() {
        assertTrue(NumberUtils.compare(-3, 0) < 0);
    }

    @Test
    public void compareInt_2() {
        assertEquals(0, NumberUtils.compare(113, 113));
    }

    @Test
    public void compareInt_3() {
        assertTrue(NumberUtils.compare(213, 32) > 0);
    }

    @Test
    public void compareLong_1() {
        assertTrue(NumberUtils.compare(-3L, 0L) < 0);
    }

    @Test
    public void compareLong_2() {
        assertEquals(0, NumberUtils.compare(113L, 113L));
    }

    @Test
    public void compareLong_3() {
        assertTrue(NumberUtils.compare(213L, 32L) > 0);
    }

    @Test
    public void compareShort_1() {
        assertTrue(NumberUtils.compare((short) -3, (short) 0) < 0);
    }

    @Test
    public void compareShort_2() {
        assertEquals(0, NumberUtils.compare((short) 113, (short) 113));
    }

    @Test
    public void compareShort_3() {
        assertTrue(NumberUtils.compare((short) 213, (short) 32) > 0);
    }

    @Test
    public void testBigIntegerToDoubleBigInteger_1() {
        assertEquals(0.0d, NumberUtils.toDouble((BigDecimal) null), "toDouble(BigInteger) 1 failed");
    }

    @Test
    public void testBigIntegerToDoubleBigInteger_2() {
        assertEquals(8.5d, NumberUtils.toDouble(BigDecimal.valueOf(8.5d)), "toDouble(BigInteger) 2 failed");
    }

    @Test
    public void testBigIntegerToDoubleBigIntegerD_1() {
        assertEquals(1.1d, NumberUtils.toDouble((BigDecimal) null, 1.1d), "toDouble(BigInteger) 1 failed");
    }

    @Test
    public void testBigIntegerToDoubleBigIntegerD_2() {
        assertEquals(8.5d, NumberUtils.toDouble(BigDecimal.valueOf(8.5d), 1.1d), "toDouble(BigInteger) 2 failed");
    }

    @Test
    public void testCompareDouble_1() {
        assertEquals(0, Double.compare(Double.NaN, Double.NaN));
    }

    @Test
    public void testCompareDouble_2() {
        assertEquals(Double.compare(Double.NaN, Double.POSITIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_3() {
        assertEquals(Double.compare(Double.NaN, Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_4() {
        assertEquals(Double.compare(Double.NaN, 1.2d), +1);
    }

    @Test
    public void testCompareDouble_5() {
        assertEquals(Double.compare(Double.NaN, 0.0d), +1);
    }

    @Test
    public void testCompareDouble_6() {
        assertEquals(Double.compare(Double.NaN, -0.0d), +1);
    }

    @Test
    public void testCompareDouble_7() {
        assertEquals(Double.compare(Double.NaN, -1.2d), +1);
    }

    @Test
    public void testCompareDouble_8() {
        assertEquals(Double.compare(Double.NaN, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_9() {
        assertEquals(Double.compare(Double.NaN, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_10() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_11() {
        assertEquals(0, Double.compare(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }

    @Test
    public void testCompareDouble_12() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_13() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, 1.2d), +1);
    }

    @Test
    public void testCompareDouble_14() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, 0.0d), +1);
    }

    @Test
    public void testCompareDouble_15() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, -0.0d), +1);
    }

    @Test
    public void testCompareDouble_16() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, -1.2d), +1);
    }

    @Test
    public void testCompareDouble_17() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_18() {
        assertEquals(Double.compare(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_19() {
        assertEquals(Double.compare(Double.MAX_VALUE, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_20() {
        assertEquals(Double.compare(Double.MAX_VALUE, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_21() {
        assertEquals(0, Double.compare(Double.MAX_VALUE, Double.MAX_VALUE));
    }

    @Test
    public void testCompareDouble_22() {
        assertEquals(Double.compare(Double.MAX_VALUE, 1.2d), +1);
    }

    @Test
    public void testCompareDouble_23() {
        assertEquals(Double.compare(Double.MAX_VALUE, 0.0d), +1);
    }

    @Test
    public void testCompareDouble_24() {
        assertEquals(Double.compare(Double.MAX_VALUE, -0.0d), +1);
    }

    @Test
    public void testCompareDouble_25() {
        assertEquals(Double.compare(Double.MAX_VALUE, -1.2d), +1);
    }

    @Test
    public void testCompareDouble_26() {
        assertEquals(Double.compare(Double.MAX_VALUE, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_27() {
        assertEquals(Double.compare(Double.MAX_VALUE, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_28() {
        assertEquals(Double.compare(1.2d, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_29() {
        assertEquals(Double.compare(1.2d, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_30() {
        assertEquals(Double.compare(1.2d, Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_31() {
        assertEquals(0, Double.compare(1.2d, 1.2d));
    }

    @Test
    public void testCompareDouble_32() {
        assertEquals(Double.compare(1.2d, 0.0d), +1);
    }

    @Test
    public void testCompareDouble_33() {
        assertEquals(Double.compare(1.2d, -0.0d), +1);
    }

    @Test
    public void testCompareDouble_34() {
        assertEquals(Double.compare(1.2d, -1.2d), +1);
    }

    @Test
    public void testCompareDouble_35() {
        assertEquals(Double.compare(1.2d, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_36() {
        assertEquals(Double.compare(1.2d, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_37() {
        assertEquals(Double.compare(0.0d, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_38() {
        assertEquals(Double.compare(0.0d, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_39() {
        assertEquals(Double.compare(0.0d, Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_40() {
        assertEquals(Double.compare(0.0d, 1.2d), -1);
    }

    @Test
    public void testCompareDouble_41() {
        assertEquals(0, Double.compare(0.0d, 0.0d));
    }

    @Test
    public void testCompareDouble_42() {
        assertEquals(Double.compare(0.0d, -0.0d), +1);
    }

    @Test
    public void testCompareDouble_43() {
        assertEquals(Double.compare(0.0d, -1.2d), +1);
    }

    @Test
    public void testCompareDouble_44() {
        assertEquals(Double.compare(0.0d, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_45() {
        assertEquals(Double.compare(0.0d, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_46() {
        assertEquals(Double.compare(-0.0d, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_47() {
        assertEquals(Double.compare(-0.0d, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_48() {
        assertEquals(Double.compare(-0.0d, Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_49() {
        assertEquals(Double.compare(-0.0d, 1.2d), -1);
    }

    @Test
    public void testCompareDouble_50() {
        assertEquals(Double.compare(-0.0d, 0.0d), -1);
    }

    @Test
    public void testCompareDouble_51() {
        assertEquals(0, Double.compare(-0.0d, -0.0d));
    }

    @Test
    public void testCompareDouble_52() {
        assertEquals(Double.compare(-0.0d, -1.2d), +1);
    }

    @Test
    public void testCompareDouble_53() {
        assertEquals(Double.compare(-0.0d, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_54() {
        assertEquals(Double.compare(-0.0d, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_55() {
        assertEquals(Double.compare(-1.2d, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_56() {
        assertEquals(Double.compare(-1.2d, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_57() {
        assertEquals(Double.compare(-1.2d, Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_58() {
        assertEquals(Double.compare(-1.2d, 1.2d), -1);
    }

    @Test
    public void testCompareDouble_59() {
        assertEquals(Double.compare(-1.2d, 0.0d), -1);
    }

    @Test
    public void testCompareDouble_60() {
        assertEquals(Double.compare(-1.2d, -0.0d), -1);
    }

    @Test
    public void testCompareDouble_61() {
        assertEquals(0, Double.compare(-1.2d, -1.2d));
    }

    @Test
    public void testCompareDouble_62() {
        assertEquals(Double.compare(-1.2d, -Double.MAX_VALUE), +1);
    }

    @Test
    public void testCompareDouble_63() {
        assertEquals(Double.compare(-1.2d, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_64() {
        assertEquals(Double.compare(-Double.MAX_VALUE, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_65() {
        assertEquals(Double.compare(-Double.MAX_VALUE, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_66() {
        assertEquals(Double.compare(-Double.MAX_VALUE, Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_67() {
        assertEquals(Double.compare(-Double.MAX_VALUE, 1.2d), -1);
    }

    @Test
    public void testCompareDouble_68() {
        assertEquals(Double.compare(-Double.MAX_VALUE, 0.0d), -1);
    }

    @Test
    public void testCompareDouble_69() {
        assertEquals(Double.compare(-Double.MAX_VALUE, -0.0d), -1);
    }

    @Test
    public void testCompareDouble_70() {
        assertEquals(Double.compare(-Double.MAX_VALUE, -1.2d), -1);
    }

    @Test
    public void testCompareDouble_71() {
        assertEquals(0, Double.compare(-Double.MAX_VALUE, -Double.MAX_VALUE));
    }

    @Test
    public void testCompareDouble_72() {
        assertEquals(Double.compare(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareDouble_73() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, Double.NaN), -1);
    }

    @Test
    public void testCompareDouble_74() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareDouble_75() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_76() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, 1.2d), -1);
    }

    @Test
    public void testCompareDouble_77() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, 0.0d), -1);
    }

    @Test
    public void testCompareDouble_78() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, -0.0d), -1);
    }

    @Test
    public void testCompareDouble_79() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, -1.2d), -1);
    }

    @Test
    public void testCompareDouble_80() {
        assertEquals(Double.compare(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE), -1);
    }

    @Test
    public void testCompareDouble_81() {
        assertEquals(0, Double.compare(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testCompareFloat_1() {
        assertEquals(0, Float.compare(Float.NaN, Float.NaN));
    }

    @Test
    public void testCompareFloat_2() {
        assertEquals(Float.compare(Float.NaN, Float.POSITIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_3() {
        assertEquals(Float.compare(Float.NaN, Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_4() {
        assertEquals(Float.compare(Float.NaN, 1.2f), +1);
    }

    @Test
    public void testCompareFloat_5() {
        assertEquals(Float.compare(Float.NaN, 0.0f), +1);
    }

    @Test
    public void testCompareFloat_6() {
        assertEquals(Float.compare(Float.NaN, -0.0f), +1);
    }

    @Test
    public void testCompareFloat_7() {
        assertEquals(Float.compare(Float.NaN, -1.2f), +1);
    }

    @Test
    public void testCompareFloat_8() {
        assertEquals(Float.compare(Float.NaN, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_9() {
        assertEquals(Float.compare(Float.NaN, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_10() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_11() {
        assertEquals(0, Float.compare(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
    }

    @Test
    public void testCompareFloat_12() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_13() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, 1.2f), +1);
    }

    @Test
    public void testCompareFloat_14() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, 0.0f), +1);
    }

    @Test
    public void testCompareFloat_15() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, -0.0f), +1);
    }

    @Test
    public void testCompareFloat_16() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, -1.2f), +1);
    }

    @Test
    public void testCompareFloat_17() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_18() {
        assertEquals(Float.compare(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_19() {
        assertEquals(Float.compare(Float.MAX_VALUE, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_20() {
        assertEquals(Float.compare(Float.MAX_VALUE, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_21() {
        assertEquals(0, Float.compare(Float.MAX_VALUE, Float.MAX_VALUE));
    }

    @Test
    public void testCompareFloat_22() {
        assertEquals(Float.compare(Float.MAX_VALUE, 1.2f), +1);
    }

    @Test
    public void testCompareFloat_23() {
        assertEquals(Float.compare(Float.MAX_VALUE, 0.0f), +1);
    }

    @Test
    public void testCompareFloat_24() {
        assertEquals(Float.compare(Float.MAX_VALUE, -0.0f), +1);
    }

    @Test
    public void testCompareFloat_25() {
        assertEquals(Float.compare(Float.MAX_VALUE, -1.2f), +1);
    }

    @Test
    public void testCompareFloat_26() {
        assertEquals(Float.compare(Float.MAX_VALUE, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_27() {
        assertEquals(Float.compare(Float.MAX_VALUE, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_28() {
        assertEquals(Float.compare(1.2f, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_29() {
        assertEquals(Float.compare(1.2f, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_30() {
        assertEquals(Float.compare(1.2f, Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_31() {
        assertEquals(0, Float.compare(1.2f, 1.2f));
    }

    @Test
    public void testCompareFloat_32() {
        assertEquals(Float.compare(1.2f, 0.0f), +1);
    }

    @Test
    public void testCompareFloat_33() {
        assertEquals(Float.compare(1.2f, -0.0f), +1);
    }

    @Test
    public void testCompareFloat_34() {
        assertEquals(Float.compare(1.2f, -1.2f), +1);
    }

    @Test
    public void testCompareFloat_35() {
        assertEquals(Float.compare(1.2f, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_36() {
        assertEquals(Float.compare(1.2f, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_37() {
        assertEquals(Float.compare(0.0f, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_38() {
        assertEquals(Float.compare(0.0f, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_39() {
        assertEquals(Float.compare(0.0f, Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_40() {
        assertEquals(Float.compare(0.0f, 1.2f), -1);
    }

    @Test
    public void testCompareFloat_41() {
        assertEquals(0, Float.compare(0.0f, 0.0f));
    }

    @Test
    public void testCompareFloat_42() {
        assertEquals(Float.compare(0.0f, -0.0f), +1);
    }

    @Test
    public void testCompareFloat_43() {
        assertEquals(Float.compare(0.0f, -1.2f), +1);
    }

    @Test
    public void testCompareFloat_44() {
        assertEquals(Float.compare(0.0f, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_45() {
        assertEquals(Float.compare(0.0f, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_46() {
        assertEquals(Float.compare(-0.0f, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_47() {
        assertEquals(Float.compare(-0.0f, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_48() {
        assertEquals(Float.compare(-0.0f, Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_49() {
        assertEquals(Float.compare(-0.0f, 1.2f), -1);
    }

    @Test
    public void testCompareFloat_50() {
        assertEquals(Float.compare(-0.0f, 0.0f), -1);
    }

    @Test
    public void testCompareFloat_51() {
        assertEquals(0, Float.compare(-0.0f, -0.0f));
    }

    @Test
    public void testCompareFloat_52() {
        assertEquals(Float.compare(-0.0f, -1.2f), +1);
    }

    @Test
    public void testCompareFloat_53() {
        assertEquals(Float.compare(-0.0f, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_54() {
        assertEquals(Float.compare(-0.0f, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_55() {
        assertEquals(Float.compare(-1.2f, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_56() {
        assertEquals(Float.compare(-1.2f, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_57() {
        assertEquals(Float.compare(-1.2f, Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_58() {
        assertEquals(Float.compare(-1.2f, 1.2f), -1);
    }

    @Test
    public void testCompareFloat_59() {
        assertEquals(Float.compare(-1.2f, 0.0f), -1);
    }

    @Test
    public void testCompareFloat_60() {
        assertEquals(Float.compare(-1.2f, -0.0f), -1);
    }

    @Test
    public void testCompareFloat_61() {
        assertEquals(0, Float.compare(-1.2f, -1.2f));
    }

    @Test
    public void testCompareFloat_62() {
        assertEquals(Float.compare(-1.2f, -Float.MAX_VALUE), +1);
    }

    @Test
    public void testCompareFloat_63() {
        assertEquals(Float.compare(-1.2f, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_64() {
        assertEquals(Float.compare(-Float.MAX_VALUE, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_65() {
        assertEquals(Float.compare(-Float.MAX_VALUE, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_66() {
        assertEquals(Float.compare(-Float.MAX_VALUE, Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_67() {
        assertEquals(Float.compare(-Float.MAX_VALUE, 1.2f), -1);
    }

    @Test
    public void testCompareFloat_68() {
        assertEquals(Float.compare(-Float.MAX_VALUE, 0.0f), -1);
    }

    @Test
    public void testCompareFloat_69() {
        assertEquals(Float.compare(-Float.MAX_VALUE, -0.0f), -1);
    }

    @Test
    public void testCompareFloat_70() {
        assertEquals(Float.compare(-Float.MAX_VALUE, -1.2f), -1);
    }

    @Test
    public void testCompareFloat_71() {
        assertEquals(0, Float.compare(-Float.MAX_VALUE, -Float.MAX_VALUE));
    }

    @Test
    public void testCompareFloat_72() {
        assertEquals(Float.compare(-Float.MAX_VALUE, Float.NEGATIVE_INFINITY), +1);
    }

    @Test
    public void testCompareFloat_73() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, Float.NaN), -1);
    }

    @Test
    public void testCompareFloat_74() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY), -1);
    }

    @Test
    public void testCompareFloat_75() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_76() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, 1.2f), -1);
    }

    @Test
    public void testCompareFloat_77() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, 0.0f), -1);
    }

    @Test
    public void testCompareFloat_78() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, -0.0f), -1);
    }

    @Test
    public void testCompareFloat_79() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, -1.2f), -1);
    }

    @Test
    public void testCompareFloat_80() {
        assertEquals(Float.compare(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE), -1);
    }

    @Test
    public void testCompareFloat_81() {
        assertEquals(0, Float.compare(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_1() {
        assertInstanceOf(Long.class, NumberUtils.LONG_ZERO);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_2() {
        assertInstanceOf(Long.class, NumberUtils.LONG_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_3() {
        assertInstanceOf(Long.class, NumberUtils.LONG_MINUS_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_4() {
        assertInstanceOf(Integer.class, NumberUtils.INTEGER_ZERO);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_5() {
        assertInstanceOf(Integer.class, NumberUtils.INTEGER_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_6() {
        assertInstanceOf(Integer.class, NumberUtils.INTEGER_MINUS_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_7() {
        assertInstanceOf(Short.class, NumberUtils.SHORT_ZERO);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_8() {
        assertInstanceOf(Short.class, NumberUtils.SHORT_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_9() {
        assertInstanceOf(Short.class, NumberUtils.SHORT_MINUS_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_10() {
        assertInstanceOf(Byte.class, NumberUtils.BYTE_ZERO);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_11() {
        assertInstanceOf(Byte.class, NumberUtils.BYTE_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_12() {
        assertInstanceOf(Byte.class, NumberUtils.BYTE_MINUS_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_13() {
        assertInstanceOf(Double.class, NumberUtils.DOUBLE_ZERO);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_14() {
        assertInstanceOf(Double.class, NumberUtils.DOUBLE_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_15() {
        assertInstanceOf(Double.class, NumberUtils.DOUBLE_MINUS_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_16() {
        assertInstanceOf(Float.class, NumberUtils.FLOAT_ZERO);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_17() {
        assertInstanceOf(Float.class, NumberUtils.FLOAT_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_18() {
        assertInstanceOf(Float.class, NumberUtils.FLOAT_MINUS_ONE);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_19() {
        assertEquals(0, NumberUtils.LONG_ZERO.longValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_20() {
        assertEquals(1, NumberUtils.LONG_ONE.longValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_21() {
        assertEquals(NumberUtils.LONG_MINUS_ONE.longValue(), -1);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_22() {
        assertEquals(0, NumberUtils.INTEGER_ZERO.intValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_23() {
        assertEquals(1, NumberUtils.INTEGER_ONE.intValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_24() {
        assertEquals(NumberUtils.INTEGER_MINUS_ONE.intValue(), -1);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_25() {
        assertEquals(0, NumberUtils.SHORT_ZERO.shortValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_26() {
        assertEquals(1, NumberUtils.SHORT_ONE.shortValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_27() {
        assertEquals(NumberUtils.SHORT_MINUS_ONE.shortValue(), -1);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_28() {
        assertEquals(0, NumberUtils.BYTE_ZERO.byteValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_29() {
        assertEquals(1, NumberUtils.BYTE_ONE.byteValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_30() {
        assertEquals(NumberUtils.BYTE_MINUS_ONE.byteValue(), -1);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_31() {
        assertEquals(0.0d, NumberUtils.DOUBLE_ZERO.doubleValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_32() {
        assertEquals(1.0d, NumberUtils.DOUBLE_ONE.doubleValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_33() {
        assertEquals(NumberUtils.DOUBLE_MINUS_ONE.doubleValue(), -1.0d);
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_34() {
        assertEquals(0.0f, NumberUtils.FLOAT_ZERO.floatValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_35() {
        assertEquals(1.0f, NumberUtils.FLOAT_ONE.floatValue());
    }

    @SuppressWarnings("cast")
    @Test
    public void testConstants_36() {
        assertEquals(NumberUtils.FLOAT_MINUS_ONE.floatValue(), -1.0f);
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new NumberUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = NumberUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(NumberUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(NumberUtils.class.getModifiers()));
    }

    @Test
    public void testCreateBigDecimal_1() {
        assertEquals(new BigDecimal("1234.5"), NumberUtils.createBigDecimal("1234.5"), "createBigDecimal(String) failed");
    }

    @Test
    public void testCreateBigDecimal_2() {
        assertNull(NumberUtils.createBigDecimal(null), "createBigDecimal(null) failed");
    }

    @Test
    public void testCreateBigInteger_1() {
        assertEquals(new BigInteger("12345"), NumberUtils.createBigInteger("12345"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_2() {
        assertNull(NumberUtils.createBigInteger(null), "createBigInteger(null) failed");
    }

    @Test
    public void testCreateBigInteger_3() {
        assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("0xff"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_4() {
        assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("0Xff"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_5() {
        assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("#ff"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_6() {
        assertEquals(new BigInteger("-255"), NumberUtils.createBigInteger("-0xff"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_7() {
        assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("0377"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_8() {
        assertEquals(new BigInteger("-255"), NumberUtils.createBigInteger("-0377"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_9() {
        assertEquals(new BigInteger("-255"), NumberUtils.createBigInteger("-0377"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_10() {
        assertEquals(new BigInteger("-0"), NumberUtils.createBigInteger("-0"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_11() {
        assertEquals(new BigInteger("0"), NumberUtils.createBigInteger("0"), "createBigInteger(String) failed");
    }

    @Test
    public void testCreateBigInteger_12() {
        assertEquals(new BigInteger("+FFFFFFFFFFFFFFFF", 16), NumberUtils.createBigInteger("+0xFFFFFFFFFFFFFFFF"));
    }

    @Test
    public void testCreateBigInteger_13() {
        assertEquals(new BigInteger("+FFFFFFFFFFFFFFFF", 16), NumberUtils.createBigInteger("+#FFFFFFFFFFFFFFFF"));
    }

    @Test
    public void testCreateBigInteger_14() {
        assertEquals(new BigInteger("+1234567", 8), NumberUtils.createBigInteger("+01234567"));
    }

    @Test
    public void testCreateDouble_1() {
        assertEquals(Double.valueOf("1234.5"), NumberUtils.createDouble("1234.5"), "createDouble(String) failed");
    }

    @Test
    public void testCreateDouble_2() {
        assertNull(NumberUtils.createDouble(null), "createDouble(null) failed");
    }

    @Test
    public void testCreateFloat_1() {
        assertEquals(Float.valueOf("1234.5"), NumberUtils.createFloat("1234.5"), "createFloat(String) failed");
    }

    @Test
    public void testCreateFloat_2() {
        assertNull(NumberUtils.createFloat(null), "createFloat(null) failed");
    }

    @Test
    public void testCreateInteger_1() {
        assertEquals(Integer.valueOf("12345"), NumberUtils.createInteger("12345"), "createInteger(String) failed");
    }

    @Test
    public void testCreateInteger_2() {
        assertNull(NumberUtils.createInteger(null), "createInteger(null) failed");
    }

    @Test
    public void testCreateInteger_3() {
        assertEquals(Integer.decode("+0xF"), NumberUtils.createInteger("+0xF"));
    }

    @Test
    public void testCreateLong_1() {
        assertEquals(Long.valueOf("12345"), NumberUtils.createLong("12345"), "createLong(String) failed");
    }

    @Test
    public void testCreateLong_2() {
        assertNull(NumberUtils.createLong(null), "createLong(null) failed");
    }

    @Test
    public void testCreateLong_3() {
        assertEquals(Long.decode("+0xFFFFFFFF"), NumberUtils.createLong("+0xFFFFFFFF"));
    }

    @Test
    public void testCreateNumber_1() {
        assertEquals(Float.valueOf("1234.5"), NumberUtils.createNumber("1234.5"), "createNumber(String) 1 failed");
    }

    @Test
    public void testCreateNumber_2() {
        assertEquals(Integer.valueOf("12345"), NumberUtils.createNumber("12345"), "createNumber(String) 2 failed");
    }

    @Test
    public void testCreateNumber_3() {
        assertEquals(Double.valueOf("1234.5"), NumberUtils.createNumber("1234.5D"), "createNumber(String) 3 failed");
    }

    @Test
    public void testCreateNumber_4() {
        assertEquals(Double.valueOf("1234.5"), NumberUtils.createNumber("1234.5d"), "createNumber(String) 3 failed");
    }

    @Test
    public void testCreateNumber_5() {
        assertEquals(Float.valueOf("1234.5"), NumberUtils.createNumber("1234.5F"), "createNumber(String) 4 failed");
    }

    @Test
    public void testCreateNumber_6() {
        assertEquals(Float.valueOf("1234.5"), NumberUtils.createNumber("1234.5f"), "createNumber(String) 4 failed");
    }

    @Test
    public void testCreateNumber_7() {
        assertEquals(Long.valueOf(Integer.MAX_VALUE + 1L), NumberUtils.createNumber("" + (Integer.MAX_VALUE + 1L)), "createNumber(String) 5 failed");
    }

    @Test
    public void testCreateNumber_8() {
        assertEquals(Long.valueOf(12345), NumberUtils.createNumber("12345L"), "createNumber(String) 6 failed");
    }

    @Test
    public void testCreateNumber_9() {
        assertEquals(Long.valueOf(12345), NumberUtils.createNumber("12345l"), "createNumber(String) 6 failed");
    }

    @Test
    public void testCreateNumber_10() {
        assertEquals(Float.valueOf("-1234.5"), NumberUtils.createNumber("-1234.5"), "createNumber(String) 7 failed");
    }

    @Test
    public void testCreateNumber_11() {
        assertEquals(Integer.valueOf("-12345"), NumberUtils.createNumber("-12345"), "createNumber(String) 8 failed");
    }

    @Test
    public void testCreateNumber_12() {
        assertEquals(0xFADE, NumberUtils.createNumber("0xFADE").intValue(), "createNumber(String) 9a failed");
    }

    @Test
    public void testCreateNumber_13() {
        assertEquals(0xFADE, NumberUtils.createNumber("0Xfade").intValue(), "createNumber(String) 9b failed");
    }

    @Test
    public void testCreateNumber_14() {
        assertEquals(-0xFADE, NumberUtils.createNumber("-0xFADE").intValue(), "createNumber(String) 10a failed");
    }

    @Test
    public void testCreateNumber_15() {
        assertEquals(-0xFADE, NumberUtils.createNumber("-0Xfade").intValue(), "createNumber(String) 10b failed");
    }

    @Test
    public void testCreateNumber_16() {
        assertEquals(Double.valueOf("1.1E200"), NumberUtils.createNumber("1.1E200"), "createNumber(String) 11 failed");
    }

    @Test
    public void testCreateNumber_17() {
        assertEquals(Float.valueOf("1.1E20"), NumberUtils.createNumber("1.1E20"), "createNumber(String) 12 failed");
    }

    @Test
    public void testCreateNumber_18() {
        assertEquals(Double.valueOf("-1.1E200"), NumberUtils.createNumber("-1.1E200"), "createNumber(String) 13 failed");
    }

    @Test
    public void testCreateNumber_19() {
        assertEquals(Double.valueOf("1.1E-200"), NumberUtils.createNumber("1.1E-200"), "createNumber(String) 14 failed");
    }

    @Test
    public void testCreateNumber_20() {
        assertNull(NumberUtils.createNumber(null), "createNumber(null) failed");
    }

    @Test
    public void testCreateNumber_21() {
        assertEquals(new BigInteger("12345678901234567890"), NumberUtils.createNumber("12345678901234567890L"), "createNumber(String) failed");
    }

    @Test
    public void testCreateNumber_22() {
        assertEquals(new BigDecimal("1.1E-700"), NumberUtils.createNumber("1.1E-700F"), "createNumber(String) 15 failed");
    }

    @Test
    public void testCreateNumber_23() {
        assertEquals(Long.valueOf("10" + Integer.MAX_VALUE), NumberUtils.createNumber("10" + Integer.MAX_VALUE + "L"), "createNumber(String) 16 failed");
    }

    @Test
    public void testCreateNumber_24() {
        assertEquals(Long.valueOf("10" + Integer.MAX_VALUE), NumberUtils.createNumber("10" + Integer.MAX_VALUE), "createNumber(String) 17 failed");
    }

    @Test
    public void testCreateNumber_25() {
        assertEquals(new BigInteger("10" + Long.MAX_VALUE), NumberUtils.createNumber("10" + Long.MAX_VALUE), "createNumber(String) 18 failed");
    }

    @Test
    public void testCreateNumber_26() {
        assertEquals(Float.valueOf("2."), NumberUtils.createNumber("2."), "createNumber(String) LANG-521 failed");
    }

    @Test
    public void testCreateNumber_27() {
        assertFalse(checkCreateNumber("1eE"), "createNumber(String) succeeded");
    }

    @Test
    public void testCreateNumber_28() {
        assertEquals(Double.valueOf(Double.MAX_VALUE), NumberUtils.createNumber("" + Double.MAX_VALUE), "createNumber(String) LANG-693 failed");
    }

    @Test
    public void testCreateNumber_29_testMerged_29() {
        final Number bigNum = NumberUtils.createNumber("-1.1E-700F");
        assertNotNull(bigNum);
        assertEquals(BigDecimal.class, bigNum.getClass());
        assertEquals(Double.valueOf("-160952.54"), NumberUtils.createNumber("-160952.54"), "createNumber(String) LANG-1018 failed");
        assertEquals(Double.valueOf("6264583.33"), NumberUtils.createNumber("6264583.33"), "createNumber(String) LANG-1187 failed");
        assertEquals(Double.valueOf("193343.82"), NumberUtils.createNumber("193343.82"), "createNumber(String) LANG-1215 failed");
        assertEquals(Double.valueOf("001234.5678"), NumberUtils.createNumber("001234.5678"), "createNumber(String) LANG-1060a failed");
        assertEquals(Double.valueOf("+001234.5678"), NumberUtils.createNumber("+001234.5678"), "createNumber(String) LANG-1060b failed");
        assertEquals(Double.valueOf("-001234.5678"), NumberUtils.createNumber("-001234.5678"), "createNumber(String) LANG-1060c failed");
        assertEquals(Double.valueOf("0000.00000"), NumberUtils.createNumber("0000.00000d"), "createNumber(String) LANG-1060d failed");
        assertEquals(Float.valueOf("001234.56"), NumberUtils.createNumber("001234.56"), "createNumber(String) LANG-1060e failed");
        assertEquals(Float.valueOf("+001234.56"), NumberUtils.createNumber("+001234.56"), "createNumber(String) LANG-1060f failed");
        assertEquals(Float.valueOf("-001234.56"), NumberUtils.createNumber("-001234.56"), "createNumber(String) LANG-1060g failed");
        assertEquals(Float.valueOf("0000.10"), NumberUtils.createNumber("0000.10"), "createNumber(String) LANG-1060h failed");
        assertEquals(Float.valueOf("001.1E20"), NumberUtils.createNumber("001.1E20"), "createNumber(String) LANG-1060i failed");
        assertEquals(Float.valueOf("+001.1E20"), NumberUtils.createNumber("+001.1E20"), "createNumber(String) LANG-1060j failed");
        assertEquals(Float.valueOf("-001.1E20"), NumberUtils.createNumber("-001.1E20"), "createNumber(String) LANG-1060k failed");
        assertEquals(Double.valueOf("001.1E200"), NumberUtils.createNumber("001.1E200"), "createNumber(String) LANG-1060l failed");
        assertEquals(Double.valueOf("+001.1E200"), NumberUtils.createNumber("+001.1E200"), "createNumber(String) LANG-1060m failed");
        assertEquals(Double.valueOf("-001.1E200"), NumberUtils.createNumber("-001.1E200"), "createNumber(String) LANG-1060n failed");
        assertEquals(Integer.decode("+0xF"), NumberUtils.createNumber("+0xF"), "createNumber(String) LANG-1645a failed");
        assertEquals(Long.decode("+0xFFFFFFFF"), NumberUtils.createNumber("+0xFFFFFFFF"), "createNumber(String) LANG-1645b failed");
        assertEquals(new BigInteger("+FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("+0xFFFFFFFFFFFFFFFF"), "createNumber(String) LANG-1645c failed");
    }

    @Test
    public void testCreateNumberMagnitude_1() {
        assertEquals(Float.valueOf(Float.MAX_VALUE), NumberUtils.createNumber("3.4028235e+38"));
    }

    @Test
    public void testCreateNumberMagnitude_2() {
        assertEquals(Double.valueOf(3.4028236e+38), NumberUtils.createNumber("3.4028236e+38"));
    }

    @Test
    public void testCreateNumberMagnitude_3() {
        assertEquals(Double.valueOf(Double.MAX_VALUE), NumberUtils.createNumber("1.7976931348623157e+308"));
    }

    @Test
    public void testCreateNumberMagnitude_4() {
        assertEquals(new BigDecimal("1.7976931348623159e+308"), NumberUtils.createNumber("1.7976931348623159e+308"));
    }

    @Test
    public void testCreateNumberMagnitude_5_testMerged_5() {
        final Double nonZero1 = Double.valueOf((double) Float.MIN_VALUE / 2);
        assertEquals(nonZero1, NumberUtils.createNumber(nonZero1.toString()));
        assertEquals(nonZero1, NumberUtils.createNumber(nonZero1 + "F"));
    }

    @Test
    public void testCreateNumberMagnitude_7_testMerged_6() {
        final BigDecimal nonZero2 = new BigDecimal("4.9e-325");
        assertEquals(nonZero2, NumberUtils.createNumber("4.9e-325"));
        assertEquals(nonZero2, NumberUtils.createNumber("4.9e-325D"));
    }

    @Test
    public void testCreateNumberMagnitude_9_testMerged_7() {
        final BigDecimal nonZero3 = new BigDecimal("1e-325");
        assertEquals(nonZero3, NumberUtils.createNumber("1e-325"));
        assertEquals(nonZero3, NumberUtils.createNumber("1e-325D"));
    }

    @Test
    public void testCreateNumberMagnitude_11_testMerged_8() {
        final BigDecimal nonZero4 = new BigDecimal("0.1e-325");
        assertEquals(nonZero4, NumberUtils.createNumber("0.1e-325"));
        assertEquals(nonZero4, NumberUtils.createNumber("0.1e-325D"));
    }

    @Test
    public void testCreateNumberMagnitude_13() {
        assertEquals(Integer.valueOf(0x12345678), NumberUtils.createNumber("0x12345678"));
    }

    @Test
    public void testCreateNumberMagnitude_14() {
        assertEquals(Long.valueOf(0x123456789L), NumberUtils.createNumber("0x123456789"));
    }

    @Test
    public void testCreateNumberMagnitude_15() {
        assertEquals(Long.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("0x7fffffffffffffff"));
    }

    @Test
    public void testCreateNumberMagnitude_16() {
        assertEquals(new BigInteger("7fffffffffffffff0", 16), NumberUtils.createNumber("0x7fffffffffffffff0"));
    }

    @Test
    public void testCreateNumberMagnitude_17() {
        assertEquals(Long.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("#7fffffffffffffff"));
    }

    @Test
    public void testCreateNumberMagnitude_18() {
        assertEquals(new BigInteger("7fffffffffffffff0", 16), NumberUtils.createNumber("#7fffffffffffffff0"));
    }

    @Test
    public void testCreateNumberMagnitude_19() {
        assertEquals(Integer.valueOf(017777777777), NumberUtils.createNumber("017777777777"));
    }

    @Test
    public void testCreateNumberMagnitude_20() {
        assertEquals(Long.valueOf(037777777777L), NumberUtils.createNumber("037777777777"));
    }

    @Test
    public void testCreateNumberMagnitude_21() {
        assertEquals(Long.valueOf(0777777777777777777777L), NumberUtils.createNumber("0777777777777777777777"));
    }

    @Test
    public void testCreateNumberMagnitude_22() {
        assertEquals(new BigInteger("1777777777777777777777", 8), NumberUtils.createNumber("01777777777777777777777"));
    }

    @Test
    public void testIsDigits_1() {
        assertFalse(NumberUtils.isDigits(null), "isDigits(null) failed");
    }

    @Test
    public void testIsDigits_2() {
        assertFalse(NumberUtils.isDigits(""), "isDigits('') failed");
    }

    @Test
    public void testIsDigits_3() {
        assertTrue(NumberUtils.isDigits("12345"), "isDigits(String) failed");
    }

    @Test
    public void testIsDigits_4() {
        assertFalse(NumberUtils.isDigits("1234.5"), "isDigits(String) neg 1 failed");
    }

    @Test
    public void testIsDigits_5() {
        assertFalse(NumberUtils.isDigits("1ab"), "isDigits(String) neg 3 failed");
    }

    @Test
    public void testIsDigits_6() {
        assertFalse(NumberUtils.isDigits("abc"), "isDigits(String) neg 4 failed");
    }

    @Test
    public void testIsParsable_1() {
        assertFalse(NumberUtils.isParsable(null));
    }

    @Test
    public void testIsParsable_2() {
        assertFalse(NumberUtils.isParsable(""));
    }

    @Test
    public void testIsParsable_3() {
        assertFalse(NumberUtils.isParsable("0xC1AB"));
    }

    @Test
    public void testIsParsable_4() {
        assertFalse(NumberUtils.isParsable("65CBA2"));
    }

    @Test
    public void testIsParsable_5() {
        assertFalse(NumberUtils.isParsable("pendro"));
    }

    @Test
    public void testIsParsable_6() {
        assertFalse(NumberUtils.isParsable("64, 2"));
    }

    @Test
    public void testIsParsable_7() {
        assertFalse(NumberUtils.isParsable("64.2.2"));
    }

    @Test
    public void testIsParsable_8() {
        assertFalse(NumberUtils.isParsable("64."));
    }

    @Test
    public void testIsParsable_9() {
        assertFalse(NumberUtils.isParsable("64L"));
    }

    @Test
    public void testIsParsable_10() {
        assertFalse(NumberUtils.isParsable("-"));
    }

    @Test
    public void testIsParsable_11() {
        assertFalse(NumberUtils.isParsable("--2"));
    }

    @Test
    public void testIsParsable_12() {
        assertTrue(NumberUtils.isParsable("64.2"));
    }

    @Test
    public void testIsParsable_13() {
        assertTrue(NumberUtils.isParsable("64"));
    }

    @Test
    public void testIsParsable_14() {
        assertTrue(NumberUtils.isParsable("018"));
    }

    @Test
    public void testIsParsable_15() {
        assertTrue(NumberUtils.isParsable(".18"));
    }

    @Test
    public void testIsParsable_16() {
        assertTrue(NumberUtils.isParsable("-65"));
    }

    @Test
    public void testIsParsable_17() {
        assertTrue(NumberUtils.isParsable("-018"));
    }

    @Test
    public void testIsParsable_18() {
        assertTrue(NumberUtils.isParsable("-018.2"));
    }

    @Test
    public void testIsParsable_19() {
        assertTrue(NumberUtils.isParsable("-.236"));
    }

    @Test
    public void testLang1087_1() {
        assertEquals(Float.class, NumberUtils.createNumber("0.0").getClass());
    }

    @Test
    public void testLang1087_2() {
        assertEquals(Float.valueOf("0.0"), NumberUtils.createNumber("0.0"));
    }

    @Test
    public void testLang1087_3() {
        assertEquals(Float.class, NumberUtils.createNumber("+0.0").getClass());
    }

    @Test
    public void testLang1087_4() {
        assertEquals(Float.valueOf("+0.0"), NumberUtils.createNumber("+0.0"));
    }

    @Test
    public void testLang1087_5() {
        assertEquals(Float.class, NumberUtils.createNumber("-0.0").getClass());
    }

    @Test
    public void testLang1087_6() {
        assertEquals(Float.valueOf("-0.0"), NumberUtils.createNumber("-0.0"));
    }

    @Test
    public void testLang1729IsParsableByte_1() {
        assertTrue(isParsableByte("1"));
    }

    @Test
    public void testLang1729IsParsableByte_2() {
        assertFalse(isParsableByte("1 2 3"));
    }

    @Test
    public void testLang1729IsParsableByte_3() {
        assertTrue(isParsableByte("１２３"));
    }

    @Test
    public void testLang1729IsParsableByte_4() {
        assertFalse(isParsableByte("１ ２ ３"));
    }

    @Test
    public void testLang1729IsParsableDouble_1() {
        assertTrue(isParsableDouble("1"));
    }

    @Test
    public void testLang1729IsParsableDouble_2() {
        assertFalse(isParsableDouble("1 2 3"));
    }

    @Test
    public void testLang1729IsParsableDouble_3() {
        assertFalse(isParsableDouble("１ ２ ３"));
    }

    @Test
    public void testLang1729IsParsableFloat_1() {
        assertTrue(isParsableFloat("1"));
    }

    @Test
    public void testLang1729IsParsableFloat_2() {
        assertFalse(isParsableFloat("1 2 3"));
    }

    @Test
    public void testLang1729IsParsableFloat_3() {
        assertFalse(isParsableFloat("１ ２ ３"));
    }

    @Test
    public void testLang1729IsParsableInteger_1() {
        assertTrue(isParsableInteger("1"));
    }

    @Test
    public void testLang1729IsParsableInteger_2() {
        assertFalse(isParsableInteger("1 2 3"));
    }

    @Test
    public void testLang1729IsParsableInteger_3() {
        assertTrue(isParsableInteger("１２３"));
    }

    @Test
    public void testLang1729IsParsableInteger_4() {
        assertFalse(isParsableInteger("１ ２ ３"));
    }

    @Test
    public void testLang1729IsParsableLong_1() {
        assertTrue(isParsableLong("1"));
    }

    @Test
    public void testLang1729IsParsableLong_2() {
        assertFalse(isParsableLong("1 2 3"));
    }

    @Test
    public void testLang1729IsParsableLong_3() {
        assertTrue(isParsableLong("１２３"));
    }

    @Test
    public void testLang1729IsParsableLong_4() {
        assertFalse(isParsableLong("１ ２ ３"));
    }

    @Test
    public void testLang1729IsParsableShort_1() {
        assertTrue(isParsableShort("1"));
    }

    @Test
    public void testLang1729IsParsableShort_2() {
        assertFalse(isParsableShort("1 2 3"));
    }

    @Test
    public void testLang1729IsParsableShort_3() {
        assertTrue(isParsableShort("１２３"));
    }

    @Test
    public void testLang1729IsParsableShort_4() {
        assertFalse(isParsableShort("１ ２ ３"));
    }

    @Test
    public void TestLang747_1() {
        assertEquals(Integer.valueOf(0x8000), NumberUtils.createNumber("0x8000"));
    }

    @Test
    public void TestLang747_2() {
        assertEquals(Integer.valueOf(0x80000), NumberUtils.createNumber("0x80000"));
    }

    @Test
    public void TestLang747_3() {
        assertEquals(Integer.valueOf(0x800000), NumberUtils.createNumber("0x800000"));
    }

    @Test
    public void TestLang747_4() {
        assertEquals(Integer.valueOf(0x8000000), NumberUtils.createNumber("0x8000000"));
    }

    @Test
    public void TestLang747_5() {
        assertEquals(Integer.valueOf(0x7FFFFFFF), NumberUtils.createNumber("0x7FFFFFFF"));
    }

    @Test
    public void TestLang747_6() {
        assertEquals(Long.valueOf(0x80000000L), NumberUtils.createNumber("0x80000000"));
    }

    @Test
    public void TestLang747_7() {
        assertEquals(Long.valueOf(0xFFFFFFFFL), NumberUtils.createNumber("0xFFFFFFFF"));
    }

    @Test
    public void TestLang747_8() {
        assertEquals(Integer.valueOf(0x8000000), NumberUtils.createNumber("0x08000000"));
    }

    @Test
    public void TestLang747_9() {
        assertEquals(Integer.valueOf(0x7FFFFFFF), NumberUtils.createNumber("0x007FFFFFFF"));
    }

    @Test
    public void TestLang747_10() {
        assertEquals(Long.valueOf(0x80000000L), NumberUtils.createNumber("0x080000000"));
    }

    @Test
    public void TestLang747_11() {
        assertEquals(Long.valueOf(0xFFFFFFFFL), NumberUtils.createNumber("0x00FFFFFFFF"));
    }

    @Test
    public void TestLang747_12() {
        assertEquals(Long.valueOf(0x800000000L), NumberUtils.createNumber("0x800000000"));
    }

    @Test
    public void TestLang747_13() {
        assertEquals(Long.valueOf(0x8000000000L), NumberUtils.createNumber("0x8000000000"));
    }

    @Test
    public void TestLang747_14() {
        assertEquals(Long.valueOf(0x80000000000L), NumberUtils.createNumber("0x80000000000"));
    }

    @Test
    public void TestLang747_15() {
        assertEquals(Long.valueOf(0x800000000000L), NumberUtils.createNumber("0x800000000000"));
    }

    @Test
    public void TestLang747_16() {
        assertEquals(Long.valueOf(0x8000000000000L), NumberUtils.createNumber("0x8000000000000"));
    }

    @Test
    public void TestLang747_17() {
        assertEquals(Long.valueOf(0x80000000000000L), NumberUtils.createNumber("0x80000000000000"));
    }

    @Test
    public void TestLang747_18() {
        assertEquals(Long.valueOf(0x800000000000000L), NumberUtils.createNumber("0x800000000000000"));
    }

    @Test
    public void TestLang747_19() {
        assertEquals(Long.valueOf(0x7FFFFFFFFFFFFFFFL), NumberUtils.createNumber("0x7FFFFFFFFFFFFFFF"));
    }

    @Test
    public void TestLang747_20() {
        assertEquals(new BigInteger("8000000000000000", 16), NumberUtils.createNumber("0x8000000000000000"));
    }

    @Test
    public void TestLang747_21() {
        assertEquals(new BigInteger("FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("0xFFFFFFFFFFFFFFFF"));
    }

    @Test
    public void TestLang747_22() {
        assertEquals(Long.valueOf(0x80000000000000L), NumberUtils.createNumber("0x00080000000000000"));
    }

    @Test
    public void TestLang747_23() {
        assertEquals(Long.valueOf(0x800000000000000L), NumberUtils.createNumber("0x0800000000000000"));
    }

    @Test
    public void TestLang747_24() {
        assertEquals(Long.valueOf(0x7FFFFFFFFFFFFFFFL), NumberUtils.createNumber("0x07FFFFFFFFFFFFFFF"));
    }

    @Test
    public void TestLang747_25() {
        assertEquals(new BigInteger("8000000000000000", 16), NumberUtils.createNumber("0x00008000000000000000"));
    }

    @Test
    public void TestLang747_26() {
        assertEquals(new BigInteger("FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("0x0FFFFFFFFFFFFFFFF"));
    }

    @Test
    public void testMaxByte_1() {
        assertEquals((byte) 5, NumberUtils.max((byte) 5), "max(byte[]) failed for array length 1");
    }

    @Test
    public void testMaxByte_2() {
        assertEquals((byte) 9, NumberUtils.max((byte) 6, (byte) 9), "max(byte[]) failed for array length 2");
    }

    @Test
    public void testMaxByte_3() {
        assertEquals((byte) 10, NumberUtils.max((byte) -10, (byte) -5, (byte) 0, (byte) 5, (byte) 10), "max(byte[]) failed for array length 5");
    }

    @Test
    public void testMaxByte_4() {
        assertEquals((byte) 10, NumberUtils.max((byte) -10, (byte) -5, (byte) 0, (byte) 5, (byte) 10));
    }

    @Test
    public void testMaxByte_5() {
        assertEquals((byte) 10, NumberUtils.max((byte) -5, (byte) 0, (byte) 10, (byte) 5, (byte) -10));
    }

    @Test
    public void testMaxFloat_1() {
        assertEquals(5.1f, NumberUtils.max(5.1f), "max(float[]) failed for array length 1");
    }

    @Test
    public void testMaxFloat_2() {
        assertEquals(9.2f, NumberUtils.max(6.3f, 9.2f), "max(float[]) failed for array length 2");
    }

    @Test
    public void testMaxFloat_3() {
        assertEquals(10.4f, NumberUtils.max(-10.5f, -5.6f, 0, 5.7f, 10.4f), "max(float[]) failed for float length 5");
    }

    @Test
    public void testMaxFloat_4() {
        assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), 0.0001f);
    }

    @Test
    public void testMaxFloat_5() {
        assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10), 0.0001f);
    }

    @Test
    public void testMaximumInt_1() {
        assertEquals(12345, NumberUtils.max(12345, 12345 - 1, 12345 - 2), "maximum(int, int, int) 1 failed");
    }

    @Test
    public void testMaximumInt_2() {
        assertEquals(12345, NumberUtils.max(12345 - 1, 12345, 12345 - 2), "maximum(int, int, int) 2 failed");
    }

    @Test
    public void testMaximumInt_3() {
        assertEquals(12345, NumberUtils.max(12345 - 1, 12345 - 2, 12345), "maximum(int, int, int) 3 failed");
    }

    @Test
    public void testMaximumInt_4() {
        assertEquals(12345, NumberUtils.max(12345 - 1, 12345, 12345), "maximum(int, int, int) 4 failed");
    }

    @Test
    public void testMaximumInt_5() {
        assertEquals(12345, NumberUtils.max(12345, 12345, 12345), "maximum(int, int, int) 5 failed");
    }

    @Test
    public void testMaximumLong_1() {
        assertEquals(12345L, NumberUtils.max(12345L, 12345L - 1L, 12345L - 2L), "maximum(long, long, long) 1 failed");
    }

    @Test
    public void testMaximumLong_2() {
        assertEquals(12345L, NumberUtils.max(12345L - 1L, 12345L, 12345L - 2L), "maximum(long, long, long) 2 failed");
    }

    @Test
    public void testMaximumLong_3() {
        assertEquals(12345L, NumberUtils.max(12345L - 1L, 12345L - 2L, 12345L), "maximum(long, long, long) 3 failed");
    }

    @Test
    public void testMaximumLong_4() {
        assertEquals(12345L, NumberUtils.max(12345L - 1L, 12345L, 12345L), "maximum(long, long, long) 4 failed");
    }

    @Test
    public void testMaximumLong_5() {
        assertEquals(12345L, NumberUtils.max(12345L, 12345L, 12345L), "maximum(long, long, long) 5 failed");
    }

    @Test
    public void testMaxInt_1() {
        assertEquals(5, NumberUtils.max(5), "max(int[]) failed for array length 1");
    }

    @Test
    public void testMaxInt_2() {
        assertEquals(9, NumberUtils.max(6, 9), "max(int[]) failed for array length 2");
    }

    @Test
    public void testMaxInt_3() {
        assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), "max(int[]) failed for array length 5");
    }

    @Test
    public void testMaxInt_4() {
        assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10));
    }

    @Test
    public void testMaxInt_5() {
        assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10));
    }

    @Test
    public void testMaxLong_1() {
        assertEquals(5L, NumberUtils.max(5L), "max(long[]) failed for array length 1");
    }

    @Test
    public void testMaxLong_2() {
        assertEquals(9L, NumberUtils.max(6L, 9L), "max(long[]) failed for array length 2");
    }

    @Test
    public void testMaxLong_3() {
        assertEquals(10L, NumberUtils.max(-10L, -5L, 0L, 5L, 10L), "max(long[]) failed for array length 5");
    }

    @Test
    public void testMaxLong_4() {
        assertEquals(10L, NumberUtils.max(-10L, -5L, 0L, 5L, 10L));
    }

    @Test
    public void testMaxLong_5() {
        assertEquals(10L, NumberUtils.max(-5L, 0L, 10L, 5L, -10L));
    }

    @Test
    public void testMaxShort_1() {
        assertEquals((short) 5, NumberUtils.max((short) 5), "max(short[]) failed for array length 1");
    }

    @Test
    public void testMaxShort_2() {
        assertEquals((short) 9, NumberUtils.max((short) 6, (short) 9), "max(short[]) failed for array length 2");
    }

    @Test
    public void testMaxShort_3() {
        assertEquals((short) 10, NumberUtils.max((short) -10, (short) -5, (short) 0, (short) 5, (short) 10), "max(short[]) failed for array length 5");
    }

    @Test
    public void testMaxShort_4() {
        assertEquals((short) 10, NumberUtils.max((short) -10, (short) -5, (short) 0, (short) 5, (short) 10));
    }

    @Test
    public void testMaxShort_5() {
        assertEquals((short) 10, NumberUtils.max((short) -5, (short) 0, (short) 10, (short) 5, (short) -10));
    }

    @Test
    public void testMinByte_1() {
        assertEquals((byte) 5, NumberUtils.min((byte) 5), "min(byte[]) failed for array length 1");
    }

    @Test
    public void testMinByte_2() {
        assertEquals((byte) 6, NumberUtils.min((byte) 6, (byte) 9), "min(byte[]) failed for array length 2");
    }

    @Test
    public void testMinByte_3() {
        assertEquals((byte) -10, NumberUtils.min((byte) -10, (byte) -5, (byte) 0, (byte) 5, (byte) 10));
    }

    @Test
    public void testMinByte_4() {
        assertEquals((byte) -10, NumberUtils.min((byte) -5, (byte) 0, (byte) -10, (byte) 5, (byte) 10));
    }

    @Test
    public void testMinDouble_1() {
        assertEquals(5.12, NumberUtils.min(5.12), "min(double[]) failed for array length 1");
    }

    @Test
    public void testMinDouble_2() {
        assertEquals(6.23, NumberUtils.min(6.23, 9.34), "min(double[]) failed for array length 2");
    }

    @Test
    public void testMinDouble_3() {
        assertEquals(-10.45, NumberUtils.min(-10.45, -5.56, 0, 5.67, 10.78), "min(double[]) failed for array length 5");
    }

    @Test
    public void testMinDouble_4() {
        assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10), 0.0001);
    }

    @Test
    public void testMinDouble_5() {
        assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10), 0.0001);
    }

    @Test
    public void testMinDouble_6() {
        assertEquals(5.12, NumberUtils.min(6.11, 5.12));
    }

    @Test
    public void testMinFloat_1() {
        assertEquals(5.9f, NumberUtils.min(5.9f), "min(float[]) failed for array length 1");
    }

    @Test
    public void testMinFloat_2() {
        assertEquals(6.8f, NumberUtils.min(6.8f, 9.7f), "min(float[]) failed for array length 2");
    }

    @Test
    public void testMinFloat_3() {
        assertEquals(-10.6f, NumberUtils.min(-10.6f, -5.5f, 0, 5.4f, 10.3f), "min(float[]) failed for array length 5");
    }

    @Test
    public void testMinFloat_4() {
        assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10), 0.0001f);
    }

    @Test
    public void testMinFloat_5() {
        assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10), 0.0001f);
    }

    @Test
    public void testMinFloat_6() {
        assertEquals(Float.NaN, NumberUtils.min(6.8f, Float.NaN));
    }

    @Test
    public void testMinFloat_7() {
        assertEquals(3.7f, NumberUtils.min(6.8f, 3.7f));
    }

    @Test
    public void testMinimumInt_1() {
        assertEquals(12345, NumberUtils.min(12345, 12345 + 1, 12345 + 2), "minimum(int, int, int) 1 failed");
    }

    @Test
    public void testMinimumInt_2() {
        assertEquals(12345, NumberUtils.min(12345 + 1, 12345, 12345 + 2), "minimum(int, int, int) 2 failed");
    }

    @Test
    public void testMinimumInt_3() {
        assertEquals(12345, NumberUtils.min(12345 + 1, 12345 + 2, 12345), "minimum(int, int, int) 3 failed");
    }

    @Test
    public void testMinimumInt_4() {
        assertEquals(12345, NumberUtils.min(12345 + 1, 12345, 12345), "minimum(int, int, int) 4 failed");
    }

    @Test
    public void testMinimumInt_5() {
        assertEquals(12345, NumberUtils.min(12345, 12345, 12345), "minimum(int, int, int) 5 failed");
    }

    @Test
    public void testMinimumLong_1() {
        assertEquals(12345L, NumberUtils.min(12345L, 12345L + 1L, 12345L + 2L), "minimum(long, long, long) 1 failed");
    }

    @Test
    public void testMinimumLong_2() {
        assertEquals(12345L, NumberUtils.min(12345L + 1L, 12345L, 12345 + 2L), "minimum(long, long, long) 2 failed");
    }

    @Test
    public void testMinimumLong_3() {
        assertEquals(12345L, NumberUtils.min(12345L + 1L, 12345L + 2L, 12345L), "minimum(long, long, long) 3 failed");
    }

    @Test
    public void testMinimumLong_4() {
        assertEquals(12345L, NumberUtils.min(12345L + 1L, 12345L, 12345L), "minimum(long, long, long) 4 failed");
    }

    @Test
    public void testMinimumLong_5() {
        assertEquals(12345L, NumberUtils.min(12345L, 12345L, 12345L), "minimum(long, long, long) 5 failed");
    }

    @Test
    public void testMinInt_1() {
        assertEquals(5, NumberUtils.min(5), "min(int[]) failed for array length 1");
    }

    @Test
    public void testMinInt_2() {
        assertEquals(6, NumberUtils.min(6, 9), "min(int[]) failed for array length 2");
    }

    @Test
    public void testMinInt_3() {
        assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10));
    }

    @Test
    public void testMinInt_4() {
        assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10));
    }

    @Test
    public void testMinLong_1() {
        assertEquals(5L, NumberUtils.min(5L), "min(long[]) failed for array length 1");
    }

    @Test
    public void testMinLong_2() {
        assertEquals(6L, NumberUtils.min(6L, 9L), "min(long[]) failed for array length 2");
    }

    @Test
    public void testMinLong_3() {
        assertEquals(-10L, NumberUtils.min(-10L, -5L, 0L, 5L, 10L));
    }

    @Test
    public void testMinLong_4() {
        assertEquals(-10L, NumberUtils.min(-5L, 0L, -10L, 5L, 10L));
    }

    @Test
    public void testMinShort_1() {
        assertEquals((short) 5, NumberUtils.min((short) 5), "min(short[]) failed for array length 1");
    }

    @Test
    public void testMinShort_2() {
        assertEquals((short) 6, NumberUtils.min((short) 6, (short) 9), "min(short[]) failed for array length 2");
    }

    @Test
    public void testMinShort_3() {
        assertEquals((short) -10, NumberUtils.min((short) -10, (short) -5, (short) 0, (short) 5, (short) 10));
    }

    @Test
    public void testMinShort_4() {
        assertEquals((short) -10, NumberUtils.min((short) -5, (short) 0, (short) -10, (short) 5, (short) 10));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_1() {
        assertInstanceOf(Float.class, NumberUtils.createNumber("1.23"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_2() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("3.40282354e+38"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_3() {
        assertInstanceOf(BigDecimal.class, NumberUtils.createNumber("1.797693134862315759e+308"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_4() {
        assertInstanceOf(Float.class, NumberUtils.createNumber("001.12"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_5() {
        assertInstanceOf(Float.class, NumberUtils.createNumber("-001.12"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_6() {
        assertInstanceOf(Float.class, NumberUtils.createNumber("+001.12"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_7() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("003.40282354e+38"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_8() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("-003.40282354e+38"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_9() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("+003.40282354e+38"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_10() {
        assertInstanceOf(BigDecimal.class, NumberUtils.createNumber("0001.797693134862315759e+308"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_11() {
        assertInstanceOf(BigDecimal.class, NumberUtils.createNumber("-001.797693134862315759e+308"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_12() {
        assertInstanceOf(BigDecimal.class, NumberUtils.createNumber("+001.797693134862315759e+308"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_13() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("2.2250738585072014E-308"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_14() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("2.2250738585072014E-308D"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_15() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("2.2250738585072014E-308F"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_16() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("4.9E-324"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_17() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("4.9E-324D"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_18() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("4.9E-324F"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_19() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("1.7976931348623157E308"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_20() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("1.7976931348623157E308D"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_21() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("1.7976931348623157E308F"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_22() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("4.9e-324D"));
    }

    @Test
    public void testStringCreateNumberEnsureNoPrecisionLoss_23() {
        assertInstanceOf(Double.class, NumberUtils.createNumber("4.9e-324F"));
    }

    @Test
    public void testStringToDoubleString_1() {
        assertEquals(NumberUtils.toDouble("-1.2345"), -1.2345d, "toDouble(String) 1 failed");
    }

    @Test
    public void testStringToDoubleString_2() {
        assertEquals(1.2345d, NumberUtils.toDouble("1.2345"), "toDouble(String) 2 failed");
    }

    @Test
    public void testStringToDoubleString_3() {
        assertEquals(0.0d, NumberUtils.toDouble("abc"), "toDouble(String) 3 failed");
    }

    @Test
    public void testStringToDoubleString_4() {
        assertEquals(NumberUtils.toDouble("-001.2345"), -1.2345d, "toDouble(String) 4 failed");
    }

    @Test
    public void testStringToDoubleString_5() {
        assertEquals(1.2345d, NumberUtils.toDouble("+001.2345"), "toDouble(String) 5 failed");
    }

    @Test
    public void testStringToDoubleString_6() {
        assertEquals(1.2345d, NumberUtils.toDouble("001.2345"), "toDouble(String) 6 failed");
    }

    @Test
    public void testStringToDoubleString_7() {
        assertEquals(0d, NumberUtils.toDouble("000.00000"), "toDouble(String) 7 failed");
    }

    @Test
    public void testStringToDoubleString_8() {
        assertEquals(NumberUtils.toDouble(Double.MAX_VALUE + ""), Double.MAX_VALUE, "toDouble(Double.MAX_VALUE) failed");
    }

    @Test
    public void testStringToDoubleString_9() {
        assertEquals(NumberUtils.toDouble(Double.MIN_VALUE + ""), Double.MIN_VALUE, "toDouble(Double.MIN_VALUE) failed");
    }

    @Test
    public void testStringToDoubleString_10() {
        assertEquals(0.0d, NumberUtils.toDouble(""), "toDouble(empty) failed");
    }

    @Test
    public void testStringToDoubleString_11() {
        assertEquals(0.0d, NumberUtils.toDouble((String) null), "toDouble(null) failed");
    }

    @Test
    public void testStringToDoubleStringD_1() {
        assertEquals(1.2345d, NumberUtils.toDouble("1.2345", 5.1d), "toDouble(String, int) 1 failed");
    }

    @Test
    public void testStringToDoubleStringD_2() {
        assertEquals(5.0d, NumberUtils.toDouble("a", 5.0d), "toDouble(String, int) 2 failed");
    }

    @Test
    public void testStringToDoubleStringD_3() {
        assertEquals(1.2345d, NumberUtils.toDouble("001.2345", 5.1d), "toDouble(String, int) 3 failed");
    }

    @Test
    public void testStringToDoubleStringD_4() {
        assertEquals(NumberUtils.toDouble("-001.2345", 5.1d), -1.2345d, "toDouble(String, int) 4 failed");
    }

    @Test
    public void testStringToDoubleStringD_5() {
        assertEquals(1.2345d, NumberUtils.toDouble("+001.2345", 5.1d), "toDouble(String, int) 5 failed");
    }

    @Test
    public void testStringToDoubleStringD_6() {
        assertEquals(0d, NumberUtils.toDouble("000.00", 5.1d), "toDouble(String, int) 7 failed");
    }

    @Test
    public void testStringToDoubleStringD_7() {
        assertEquals(5.1d, NumberUtils.toDouble("", 5.1d));
    }

    @Test
    public void testStringToDoubleStringD_8() {
        assertEquals(5.1d, NumberUtils.toDouble((String) null, 5.1d));
    }

    @Test
    public void testToByteString_1() {
        assertEquals(123, NumberUtils.toByte("123"), "toByte(String) 1 failed");
    }

    @Test
    public void testToByteString_2() {
        assertEquals(0, NumberUtils.toByte("abc"), "toByte(String) 2 failed");
    }

    @Test
    public void testToByteString_3() {
        assertEquals(0, NumberUtils.toByte(""), "toByte(empty) failed");
    }

    @Test
    public void testToByteString_4() {
        assertEquals(0, NumberUtils.toByte(null), "toByte(null) failed");
    }

    @Test
    public void testToByteStringI_1() {
        assertEquals(123, NumberUtils.toByte("123", (byte) 5), "toByte(String, byte) 1 failed");
    }

    @Test
    public void testToByteStringI_2() {
        assertEquals(5, NumberUtils.toByte("12.3", (byte) 5), "toByte(String, byte) 2 failed");
    }

    @Test
    public void testToByteStringI_3() {
        assertEquals(5, NumberUtils.toByte("", (byte) 5));
    }

    @Test
    public void testToByteStringI_4() {
        assertEquals(5, NumberUtils.toByte(null, (byte) 5));
    }

    @Test
    public void testToFloatString_1() {
        assertEquals(NumberUtils.toFloat("-1.2345"), -1.2345f, "toFloat(String) 1 failed");
    }

    @Test
    public void testToFloatString_2() {
        assertEquals(1.2345f, NumberUtils.toFloat("1.2345"), "toFloat(String) 2 failed");
    }

    @Test
    public void testToFloatString_3() {
        assertEquals(0.0f, NumberUtils.toFloat("abc"), "toFloat(String) 3 failed");
    }

    @Test
    public void testToFloatString_4() {
        assertEquals(NumberUtils.toFloat("-001.2345"), -1.2345f, "toFloat(String) 4 failed");
    }

    @Test
    public void testToFloatString_5() {
        assertEquals(1.2345f, NumberUtils.toFloat("+001.2345"), "toFloat(String) 5 failed");
    }

    @Test
    public void testToFloatString_6() {
        assertEquals(1.2345f, NumberUtils.toFloat("001.2345"), "toFloat(String) 6 failed");
    }

    @Test
    public void testToFloatString_7() {
        assertEquals(0f, NumberUtils.toFloat("000.00"), "toFloat(String) 7 failed");
    }

    @Test
    public void testToFloatString_8() {
        assertEquals(NumberUtils.toFloat(Float.MAX_VALUE + ""), Float.MAX_VALUE, "toFloat(Float.MAX_VALUE) failed");
    }

    @Test
    public void testToFloatString_9() {
        assertEquals(NumberUtils.toFloat(Float.MIN_VALUE + ""), Float.MIN_VALUE, "toFloat(Float.MIN_VALUE) failed");
    }

    @Test
    public void testToFloatString_10() {
        assertEquals(0.0f, NumberUtils.toFloat(""), "toFloat(empty) failed");
    }

    @Test
    public void testToFloatString_11() {
        assertEquals(0.0f, NumberUtils.toFloat(null), "toFloat(null) failed");
    }

    @Test
    public void testToFloatStringF_1() {
        assertEquals(1.2345f, NumberUtils.toFloat("1.2345", 5.1f), "toFloat(String, int) 1 failed");
    }

    @Test
    public void testToFloatStringF_2() {
        assertEquals(5.0f, NumberUtils.toFloat("a", 5.0f), "toFloat(String, int) 2 failed");
    }

    @Test
    public void testToFloatStringF_3() {
        assertEquals(5.0f, NumberUtils.toFloat("-001Z.2345", 5.0f), "toFloat(String, int) 3 failed");
    }

    @Test
    public void testToFloatStringF_4() {
        assertEquals(5.0f, NumberUtils.toFloat("+001AB.2345", 5.0f), "toFloat(String, int) 4 failed");
    }

    @Test
    public void testToFloatStringF_5() {
        assertEquals(5.0f, NumberUtils.toFloat("001Z.2345", 5.0f), "toFloat(String, int) 5 failed");
    }

    @Test
    public void testToFloatStringF_6() {
        assertEquals(5.0f, NumberUtils.toFloat("", 5.0f));
    }

    @Test
    public void testToFloatStringF_7() {
        assertEquals(5.0f, NumberUtils.toFloat(null, 5.0f));
    }

    @Test
    public void testToIntString_1() {
        assertEquals(12345, NumberUtils.toInt("12345"), "toInt(String) 1 failed");
    }

    @Test
    public void testToIntString_2() {
        assertEquals(0, NumberUtils.toInt("abc"), "toInt(String) 2 failed");
    }

    @Test
    public void testToIntString_3() {
        assertEquals(0, NumberUtils.toInt(""), "toInt(empty) failed");
    }

    @Test
    public void testToIntString_4() {
        assertEquals(0, NumberUtils.toInt(null), "toInt(null) failed");
    }

    @Test
    public void testToIntStringI_1() {
        assertEquals(12345, NumberUtils.toInt("12345", 5), "toInt(String, int) 1 failed");
    }

    @Test
    public void testToIntStringI_2() {
        assertEquals(5, NumberUtils.toInt("1234.5", 5), "toInt(String, int) 2 failed");
    }

    @Test
    public void testToIntStringI_3() {
        assertEquals(5, NumberUtils.toInt("", 5));
    }

    @Test
    public void testToIntStringI_4() {
        assertEquals(5, NumberUtils.toInt(null, 5));
    }

    @Test
    public void testToLongString_1() {
        assertEquals(12345L, NumberUtils.toLong("12345"), "toLong(String) 1 failed");
    }

    @Test
    public void testToLongString_2() {
        assertEquals(0L, NumberUtils.toLong("abc"), "toLong(String) 2 failed");
    }

    @Test
    public void testToLongString_3() {
        assertEquals(0L, NumberUtils.toLong("1L"), "toLong(String) 3 failed");
    }

    @Test
    public void testToLongString_4() {
        assertEquals(0L, NumberUtils.toLong("1l"), "toLong(String) 4 failed");
    }

    @Test
    public void testToLongString_5() {
        assertEquals(NumberUtils.toLong(Long.MAX_VALUE + ""), Long.MAX_VALUE, "toLong(Long.MAX_VALUE) failed");
    }

    @Test
    public void testToLongString_6() {
        assertEquals(NumberUtils.toLong(Long.MIN_VALUE + ""), Long.MIN_VALUE, "toLong(Long.MIN_VALUE) failed");
    }

    @Test
    public void testToLongString_7() {
        assertEquals(0L, NumberUtils.toLong(""), "toLong(empty) failed");
    }

    @Test
    public void testToLongString_8() {
        assertEquals(0L, NumberUtils.toLong(null), "toLong(null) failed");
    }

    @Test
    public void testToLongStringL_1() {
        assertEquals(12345L, NumberUtils.toLong("12345", 5L), "toLong(String, long) 1 failed");
    }

    @Test
    public void testToLongStringL_2() {
        assertEquals(5L, NumberUtils.toLong("1234.5", 5L), "toLong(String, long) 2 failed");
    }

    @Test
    public void testToLongStringL_3() {
        assertEquals(5L, NumberUtils.toLong("", 5L));
    }

    @Test
    public void testToLongStringL_4() {
        assertEquals(5L, NumberUtils.toLong(null, 5L));
    }

    @Test
    public void testToScaledBigDecimalBigDecimal_1() {
        assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(123.456)), BigDecimal.valueOf(123.46), "toScaledBigDecimal(BigDecimal) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimal_2() {
        assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.515)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(BigDecimal) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimal_3() {
        assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(BigDecimal) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimal_4() {
        assertEquals("2352.00", NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525)).multiply(BigDecimal.valueOf(100)).toString(), "toScaledBigDecimal(BigDecimal) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimal_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((BigDecimal) null), BigDecimal.ZERO, "toScaledBigDecimal(BigDecimal) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimalIRM_1() {
        assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(123.456), 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(BigDecimal, int, RoundingMode) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimalIRM_2() {
        assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.5159), 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(BigDecimal, int, RoundingMode) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimalIRM_3() {
        assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525), 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.53), "toScaledBigDecimal(BigDecimal, int, RoundingMode) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimalIRM_4() {
        assertEquals("23521.0000", NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.521), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString(), "toScaledBigDecimal(BigDecimal, int, RoundingMode) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalBigDecimalIRM_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((BigDecimal) null, 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(BigDecimal, int, RoundingMode) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalDouble_1() {
        assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(123.456d)), BigDecimal.valueOf(123.46), "toScaledBigDecimal(Double) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalDouble_2() {
        assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.515d)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Double) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalDouble_3() {
        assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.525d)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Double) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalDouble_4() {
        assertEquals("2352.00", NumberUtils.toScaledBigDecimal(Double.valueOf(23.525d)).multiply(BigDecimal.valueOf(100)).toString(), "toScaledBigDecimal(Double) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalDouble_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((Double) null), BigDecimal.ZERO, "toScaledBigDecimal(Double) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalDoubleIRM_1() {
        assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(123.456d), 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(Double, int, RoundingMode) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalDoubleIRM_2() {
        assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.5159d), 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(Double, int, RoundingMode) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalDoubleIRM_3() {
        assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.525d), 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.53), "toScaledBigDecimal(Double, int, RoundingMode) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalDoubleIRM_4() {
        assertEquals("23521.0000", NumberUtils.toScaledBigDecimal(Double.valueOf(23.521d), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString(), "toScaledBigDecimal(Double, int, RoundingMode) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalDoubleIRM_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((Double) null, 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(Double, int, RoundingMode) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalFloat_1() {
        assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(123.456f)), BigDecimal.valueOf(123.46), "toScaledBigDecimal(Float) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalFloat_2() {
        assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.515f)), BigDecimal.valueOf(23.51), "toScaledBigDecimal(Float) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalFloat_3() {
        assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.525f)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Float) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalFloat_4() {
        assertEquals("2352.00", NumberUtils.toScaledBigDecimal(Float.valueOf(23.525f)).multiply(BigDecimal.valueOf(100)).toString(), "toScaledBigDecimal(Float) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalFloat_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((Float) null), BigDecimal.ZERO, "toScaledBigDecimal(Float) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalFloatIRM_1() {
        assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(123.456f), 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(Float, int, RoundingMode) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalFloatIRM_2() {
        assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.5159f), 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(Float, int, RoundingMode) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalFloatIRM_3() {
        assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.525f), 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Float, int, RoundingMode) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalFloatIRM_4() {
        assertEquals("23521.0000", NumberUtils.toScaledBigDecimal(Float.valueOf(23.521f), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString(), "toScaledBigDecimal(Float, int, RoundingMode) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalFloatIRM_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((Float) null, 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(Float, int, RoundingMode) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalString_1() {
        assertEquals(NumberUtils.toScaledBigDecimal("123.456"), BigDecimal.valueOf(123.46), "toScaledBigDecimal(String) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalString_2() {
        assertEquals(NumberUtils.toScaledBigDecimal("23.515"), BigDecimal.valueOf(23.52), "toScaledBigDecimal(String) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalString_3() {
        assertEquals(NumberUtils.toScaledBigDecimal("23.525"), BigDecimal.valueOf(23.52), "toScaledBigDecimal(String) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalString_4() {
        assertEquals("2352.00", NumberUtils.toScaledBigDecimal("23.525").multiply(BigDecimal.valueOf(100)).toString(), "toScaledBigDecimal(String) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalString_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((String) null), BigDecimal.ZERO, "toScaledBigDecimal(String) 5 failed");
    }

    @Test
    public void testToScaledBigDecimalStringIRM_1() {
        assertEquals(NumberUtils.toScaledBigDecimal("123.456", 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(String, int, RoundingMode) 1 failed");
    }

    @Test
    public void testToScaledBigDecimalStringIRM_2() {
        assertEquals(NumberUtils.toScaledBigDecimal("23.5159", 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(String, int, RoundingMode) 2 failed");
    }

    @Test
    public void testToScaledBigDecimalStringIRM_3() {
        assertEquals(NumberUtils.toScaledBigDecimal("23.525", 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.53), "toScaledBigDecimal(String, int, RoundingMode) 3 failed");
    }

    @Test
    public void testToScaledBigDecimalStringIRM_4() {
        assertEquals("23521.0000", NumberUtils.toScaledBigDecimal("23.521", 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString(), "toScaledBigDecimal(String, int, RoundingMode) 4 failed");
    }

    @Test
    public void testToScaledBigDecimalStringIRM_5() {
        assertEquals(NumberUtils.toScaledBigDecimal((String) null, 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(String, int, RoundingMode) 5 failed");
    }

    @Test
    public void testToShortString_1() {
        assertEquals(12345, NumberUtils.toShort("12345"), "toShort(String) 1 failed");
    }

    @Test
    public void testToShortString_2() {
        assertEquals(0, NumberUtils.toShort("abc"), "toShort(String) 2 failed");
    }

    @Test
    public void testToShortString_3() {
        assertEquals(0, NumberUtils.toShort(""), "toShort(empty) failed");
    }

    @Test
    public void testToShortString_4() {
        assertEquals(0, NumberUtils.toShort(null), "toShort(null) failed");
    }

    @Test
    public void testToShortStringI_1() {
        assertEquals(12345, NumberUtils.toShort("12345", (short) 5), "toShort(String, short) 1 failed");
    }

    @Test
    public void testToShortStringI_2() {
        assertEquals(5, NumberUtils.toShort("1234.5", (short) 5), "toShort(String, short) 2 failed");
    }

    @Test
    public void testToShortStringI_3() {
        assertEquals(5, NumberUtils.toShort("", (short) 5));
    }

    @Test
    public void testToShortStringI_4() {
        assertEquals(5, NumberUtils.toShort(null, (short) 5));
    }
}
