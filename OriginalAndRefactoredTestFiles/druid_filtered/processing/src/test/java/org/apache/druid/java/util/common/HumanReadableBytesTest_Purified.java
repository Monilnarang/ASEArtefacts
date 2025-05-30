package org.apache.druid.java.util.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.utils.CollectionUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HumanReadableBytesTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    static class ExceptionMatcher implements Matcher {

        static ExceptionMatcher INVALIDFORMAT = new ExceptionMatcher("Invalid format");

        static ExceptionMatcher OVERFLOW = new ExceptionMatcher("Number overflow");

        private String prefix;

        public ExceptionMatcher(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public boolean matches(Object item) {
            if (!(item instanceof IAE)) {
                return false;
            }
            return ((IAE) item).getMessage().startsWith(prefix);
        }

        @Override
        public void describeMismatch(Object item, Description mismatchDescription) {
        }

        @Override
        public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        }

        @Override
        public void describeTo(Description description) {
        }
    }

    static class TestBytesRange {

        @HumanReadableBytesRange(min = 0, max = 5)
        HumanReadableBytes bytes;

        public TestBytesRange(HumanReadableBytes bytes) {
            this.bytes = bytes;
        }
    }

    private static <T> String validate(T obj) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Map<String, StringBuilder> errorMap = new HashMap<>();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        return CollectionUtils.isNullOrEmpty(set) ? null : set.stream().findFirst().get().getMessage();
    }

    @Test
    public void testNumberString_1() {
        Assert.assertEquals(0, HumanReadableBytes.parse("0"));
    }

    @Test
    public void testNumberString_2() {
        Assert.assertEquals(1, HumanReadableBytes.parse("1"));
    }

    @Test
    public void testNumberString_3() {
        Assert.assertEquals(10000000, HumanReadableBytes.parse("10000000"));
    }

    @Test
    public void testWithWhiteSpace_1() {
        Assert.assertEquals(12345, HumanReadableBytes.parse(" 12345 "));
    }

    @Test
    public void testWithWhiteSpace_2() {
        Assert.assertEquals(12345, HumanReadableBytes.parse("\t12345\t"));
    }

    @Test
    public void testK_1() {
        Assert.assertEquals(1000, HumanReadableBytes.parse("1k"));
    }

    @Test
    public void testK_2() {
        Assert.assertEquals(1000, HumanReadableBytes.parse("1K"));
    }

    @Test
    public void testM_1() {
        Assert.assertEquals(1000_000, HumanReadableBytes.parse("1m"));
    }

    @Test
    public void testM_2() {
        Assert.assertEquals(1000_000, HumanReadableBytes.parse("1M"));
    }

    @Test
    public void testG_1() {
        Assert.assertEquals(1000_000_000, HumanReadableBytes.parse("1g"));
    }

    @Test
    public void testG_2() {
        Assert.assertEquals(1000_000_000, HumanReadableBytes.parse("1G"));
    }

    @Test
    public void testT_1() {
        Assert.assertEquals(1000_000_000_000L, HumanReadableBytes.parse("1t"));
    }

    @Test
    public void testT_2() {
        Assert.assertEquals(1000_000_000_000L, HumanReadableBytes.parse("1T"));
    }

    @Test
    public void testKiB_1() {
        Assert.assertEquals(1024, HumanReadableBytes.parse("1kib"));
    }

    @Test
    public void testKiB_2() {
        Assert.assertEquals(9 * 1024, HumanReadableBytes.parse("9KiB"));
    }

    @Test
    public void testKiB_3() {
        Assert.assertEquals(9 * 1024, HumanReadableBytes.parse("9Kib"));
    }

    @Test
    public void testKiB_4() {
        Assert.assertEquals(9 * 1024, HumanReadableBytes.parse("9Ki"));
    }

    @Test
    public void testMiB_1() {
        Assert.assertEquals(1024 * 1024, HumanReadableBytes.parse("1mib"));
    }

    @Test
    public void testMiB_2() {
        Assert.assertEquals(9 * 1024 * 1024, HumanReadableBytes.parse("9MiB"));
    }

    @Test
    public void testMiB_3() {
        Assert.assertEquals(9 * 1024 * 1024, HumanReadableBytes.parse("9Mib"));
    }

    @Test
    public void testMiB_4() {
        Assert.assertEquals(9 * 1024 * 1024, HumanReadableBytes.parse("9Mi"));
    }

    @Test
    public void testGiB_1() {
        Assert.assertEquals(1024 * 1024 * 1024, HumanReadableBytes.parse("1gib"));
    }

    @Test
    public void testGiB_2() {
        Assert.assertEquals(1024 * 1024 * 1024, HumanReadableBytes.parse("1GiB"));
    }

    @Test
    public void testGiB_3() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024, HumanReadableBytes.parse("9Gib"));
    }

    @Test
    public void testGiB_4() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024, HumanReadableBytes.parse("9Gi"));
    }

    @Test
    public void testTiB_1() {
        Assert.assertEquals(1024L * 1024 * 1024 * 1024, HumanReadableBytes.parse("1tib"));
    }

    @Test
    public void testTiB_2() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("9TiB"));
    }

    @Test
    public void testTiB_3() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("9Tib"));
    }

    @Test
    public void testTiB_4() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("9Ti"));
    }

    @Test
    public void testPiB_1() {
        Assert.assertEquals(1024L * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("1pib"));
    }

    @Test
    public void testPiB_2() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("9PiB"));
    }

    @Test
    public void testPiB_3() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("9Pib"));
    }

    @Test
    public void testPiB_4() {
        Assert.assertEquals(9L * 1024 * 1024 * 1024 * 1024 * 1024, HumanReadableBytes.parse("9Pi"));
    }

    @Test
    public void testDefault_1() {
        Assert.assertEquals(-123, HumanReadableBytes.parse(" ", -123));
    }

    @Test
    public void testDefault_2() {
        Assert.assertEquals(-456, HumanReadableBytes.parse(null, -456));
    }

    @Test
    public void testDefault_3() {
        Assert.assertEquals(-789, HumanReadableBytes.parse("\t", -789));
    }

    @Test
    public void testFormatInBinaryByte_1() {
        Assert.assertEquals("-8.00 EiB", HumanReadableBytes.format(Long.MIN_VALUE, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_2() {
        Assert.assertEquals("-8.000 EiB", HumanReadableBytes.format(Long.MIN_VALUE, 3, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_3() {
        Assert.assertEquals("-2.00 GiB", HumanReadableBytes.format(Integer.MIN_VALUE, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_4() {
        Assert.assertEquals("-32.00 KiB", HumanReadableBytes.format(Short.MIN_VALUE, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_5() {
        Assert.assertEquals("-128 B", HumanReadableBytes.format(Byte.MIN_VALUE, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_6() {
        Assert.assertEquals("-1 B", HumanReadableBytes.format(-1, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_7() {
        Assert.assertEquals("0 B", HumanReadableBytes.format(0, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_8() {
        Assert.assertEquals("1 B", HumanReadableBytes.format(1, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_9() {
        Assert.assertEquals("1.00 KiB", HumanReadableBytes.format(1024L, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_10() {
        Assert.assertEquals("1.00 MiB", HumanReadableBytes.format(1024L * 1024, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_11() {
        Assert.assertEquals("1.00 GiB", HumanReadableBytes.format(1024L * 1024 * 1024, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_12() {
        Assert.assertEquals("1.00 TiB", HumanReadableBytes.format(1024L * 1024 * 1024 * 1024, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_13() {
        Assert.assertEquals("1.00 PiB", HumanReadableBytes.format(1024L * 1024 * 1024 * 1024 * 1024, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testFormatInBinaryByte_14() {
        Assert.assertEquals("8.00 EiB", HumanReadableBytes.format(Long.MAX_VALUE, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testPrecisionInBinaryFormat_1() {
        Assert.assertEquals("1 KiB", HumanReadableBytes.format(1500, 0, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testPrecisionInBinaryFormat_2() {
        Assert.assertEquals("1.5 KiB", HumanReadableBytes.format(1500, 1, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testPrecisionInBinaryFormat_3() {
        Assert.assertEquals("1.46 KiB", HumanReadableBytes.format(1500, 2, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testPrecisionInBinaryFormat_4() {
        Assert.assertEquals("1.465 KiB", HumanReadableBytes.format(1500, 3, HumanReadableBytes.UnitSystem.BINARY_BYTE));
    }

    @Test
    public void testPrecisionInDecimalFormat_1() {
        Assert.assertEquals("1 KB", HumanReadableBytes.format(1456, 0, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testPrecisionInDecimalFormat_2() {
        Assert.assertEquals("1.5 KB", HumanReadableBytes.format(1456, 1, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testPrecisionInDecimalFormat_3() {
        Assert.assertEquals("1.46 KB", HumanReadableBytes.format(1456, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testPrecisionInDecimalFormat_4() {
        Assert.assertEquals("1.456 KB", HumanReadableBytes.format(1456, 3, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_1() {
        Assert.assertEquals("1 B", HumanReadableBytes.format(1, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_2() {
        Assert.assertEquals("1.00 KB", HumanReadableBytes.format(1000L, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_3() {
        Assert.assertEquals("1.00 MB", HumanReadableBytes.format(1000L * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_4() {
        Assert.assertEquals("1.00 GB", HumanReadableBytes.format(1000L * 1000 * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_5() {
        Assert.assertEquals("1.00 TB", HumanReadableBytes.format(1000L * 1000 * 1000 * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_6() {
        Assert.assertEquals("1.00 PB", HumanReadableBytes.format(1000L * 1000 * 1000 * 1000 * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_7() {
        Assert.assertEquals("9.22 EB", HumanReadableBytes.format(Long.MAX_VALUE, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_8() {
        Assert.assertEquals("100.00 KB", HumanReadableBytes.format(99999, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_9() {
        Assert.assertEquals("99.999 KB", HumanReadableBytes.format(99999, 3, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_10() {
        Assert.assertEquals("999.9 PB", HumanReadableBytes.format(999_949_999_999_999_999L, 1, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_11() {
        Assert.assertEquals("999.95 PB", HumanReadableBytes.format(999_949_999_999_999_999L, 2, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimalByte_12() {
        Assert.assertEquals("999.949 PB", HumanReadableBytes.format(999_949_999_999_999_999L, 3, HumanReadableBytes.UnitSystem.DECIMAL_BYTE));
    }

    @Test
    public void testFormatInDecimal_1() {
        Assert.assertEquals("1", HumanReadableBytes.format(1, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_2() {
        Assert.assertEquals("999", HumanReadableBytes.format(999, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_3() {
        Assert.assertEquals("-999", HumanReadableBytes.format(-999, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_4() {
        Assert.assertEquals("-1.00 K", HumanReadableBytes.format(-1000, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_5() {
        Assert.assertEquals("1.00 K", HumanReadableBytes.format(1000L, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_6() {
        Assert.assertEquals("1.00 M", HumanReadableBytes.format(1000L * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_7() {
        Assert.assertEquals("1.00 G", HumanReadableBytes.format(1000L * 1000 * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_8() {
        Assert.assertEquals("1.00 T", HumanReadableBytes.format(1000L * 1000 * 1000 * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_9() {
        Assert.assertEquals("1.00 P", HumanReadableBytes.format(1000L * 1000 * 1000 * 1000 * 1000, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_10() {
        Assert.assertEquals("-9.22 E", HumanReadableBytes.format(Long.MIN_VALUE, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testFormatInDecimal_11() {
        Assert.assertEquals("9.22 E", HumanReadableBytes.format(Long.MAX_VALUE, 2, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testInvalidPrecisionArgumentUpperBound_1() {
        Assert.assertEquals("1", HumanReadableBytes.format(1, 3, HumanReadableBytes.UnitSystem.DECIMAL));
    }

    @Test
    public void testInvalidPrecisionArgumentUpperBound_2() {
        Assert.assertEquals("1", HumanReadableBytes.format(1, 4, HumanReadableBytes.UnitSystem.DECIMAL));
    }
}
