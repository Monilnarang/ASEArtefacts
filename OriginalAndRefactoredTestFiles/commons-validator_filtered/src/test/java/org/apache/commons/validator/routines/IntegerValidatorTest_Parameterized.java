package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IntegerValidatorTest_Parameterized extends AbstractNumberValidatorTest {

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

    @ParameterizedTest
    @MethodSource("Provider_testMinMaxValues_1_3")
    public void testMinMaxValues_1_3(String param1, int param2) {
        assertTrue(validator.isValid(param2), param1);
    }

    static public Stream<Arguments> Provider_testMinMaxValues_1_3() {
        return Stream.of(arguments("2147483647 is max integer", 2147483647), arguments("-2147483648 is min integer", -2147483648));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMinMaxValues_2_4")
    public void testMinMaxValues_2_4(String param1, double param2) {
        assertFalse(validator.isValid(param2), param1);
    }

    static public Stream<Arguments> Provider_testMinMaxValues_2_4() {
        return Stream.of(arguments("2147483648 > max integer", 2147483648), arguments("-2147483649 < min integer", -2147483649));
    }
}
