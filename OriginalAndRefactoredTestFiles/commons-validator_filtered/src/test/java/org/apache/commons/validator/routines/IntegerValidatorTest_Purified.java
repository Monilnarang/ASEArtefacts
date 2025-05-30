package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerValidatorTest_Purified extends AbstractNumberValidatorTest {

    private static final Integer INT_MIN_VAL = Integer.valueOf(Integer.MIN_VALUE);

    private static final Integer INT_MAX_VAL = Integer.valueOf(Integer.MAX_VALUE);

    private static final String INT_MAX = "2147483647";

    private static final String INT_MAX_0 = "2147483647.99999999999999999999999";

    private static final String INT_MAX_1 = "2147483648";

    private static final String INT_MIN = "-2147483648";

    private static final String INT_MIN_0 = "-2147483648.99999999999999999999999";

    private static final String INT_MIN_1 = "-2147483649";

    @BeforeEach
    protected void setUp() {
        validator = new IntegerValidator(false, 0);
        strictValidator = new IntegerValidator();
        testPattern = "#,###";
        max = Integer.valueOf(Integer.MAX_VALUE);
        maxPlusOne = Long.valueOf(max.longValue() + 1);
        min = Integer.valueOf(Integer.MIN_VALUE);
        minMinusOne = Long.valueOf(min.longValue() - 1);
        invalidStrict = new String[] { null, "", "X", "X12", "12X", "1X2", "1.2", INT_MAX_1, INT_MIN_1 };
        invalid = new String[] { null, "", "X", "X12", INT_MAX_1, INT_MIN_1 };
        testNumber = Integer.valueOf(1234);
        testZero = Integer.valueOf(0);
        validStrict = new String[] { "0", "1234", "1,234", INT_MAX, INT_MIN };
        validStrictCompare = new Number[] { testZero, testNumber, testNumber, INT_MAX_VAL, INT_MIN_VAL };
        valid = new String[] { "0", "1234", "1,234", "1,234.5", "1234X", INT_MAX, INT_MIN, INT_MAX_0, INT_MIN_0 };
        validCompare = new Number[] { testZero, testNumber, testNumber, testNumber, testNumber, INT_MAX_VAL, INT_MIN_VAL, INT_MAX_VAL, INT_MIN_VAL };
        testStringUS = "1,234";
        testStringDE = "1.234";
        localeValue = testStringDE;
        localePattern = "#.###";
        testLocale = Locale.GERMANY;
        localeExpected = testNumber;
    }

    @Test
    public void testMinMaxValues_1() {
        assertTrue(validator.isValid("2147483647"), "2147483647 is max integer");
    }

    @Test
    public void testMinMaxValues_2() {
        assertFalse(validator.isValid("2147483648"), "2147483648 > max integer");
    }

    @Test
    public void testMinMaxValues_3() {
        assertTrue(validator.isValid("-2147483648"), "-2147483648 is min integer");
    }

    @Test
    public void testMinMaxValues_4() {
        assertFalse(validator.isValid("-2147483649"), "-2147483649 < min integer");
    }
}
