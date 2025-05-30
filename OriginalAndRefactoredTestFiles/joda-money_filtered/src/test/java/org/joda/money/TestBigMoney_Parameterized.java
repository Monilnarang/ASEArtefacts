package org.joda.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TestBigMoney_Parameterized {

    private static final CurrencyUnit GBP = CurrencyUnit.of("GBP");

    private static final CurrencyUnit EUR = CurrencyUnit.of("EUR");

    private static final CurrencyUnit USD = CurrencyUnit.of("USD");

    private static final CurrencyUnit JPY = CurrencyUnit.of("JPY");

    private static final BigDecimal BIGDEC_2_34 = new BigDecimal("2.34");

    private static final BigDecimal BIGDEC_2_345 = new BigDecimal("2.345");

    private static final BigDecimal BIGDEC_M5_78 = new BigDecimal("-5.78");

    private static final BigMoney GBP_0_00 = BigMoney.parse("GBP 0.00");

    private static final BigMoney GBP_1_23 = BigMoney.parse("GBP 1.23");

    private static final BigMoney GBP_2_33 = BigMoney.parse("GBP 2.33");

    private static final BigMoney GBP_2_34 = BigMoney.parse("GBP 2.34");

    private static final BigMoney GBP_2_35 = BigMoney.parse("GBP 2.35");

    private static final BigMoney GBP_2_36 = BigMoney.parse("GBP 2.36");

    private static final BigMoney GBP_5_78 = BigMoney.parse("GBP 5.78");

    private static final BigMoney GBP_M1_23 = BigMoney.parse("GBP -1.23");

    private static final BigMoney GBP_M5_78 = BigMoney.parse("GBP -5.78");

    private static final BigMoney GBP_INT_MAX_PLUS1 = BigMoney.ofMinor(GBP, ((long) Integer.MAX_VALUE) + 1);

    private static final BigMoney GBP_INT_MIN_MINUS1 = BigMoney.ofMinor(GBP, ((long) Integer.MIN_VALUE) - 1);

    private static final BigMoney GBP_INT_MAX_MAJOR_PLUS1 = BigMoney.ofMinor(GBP, (((long) Integer.MAX_VALUE) + 1) * 100);

    private static final BigMoney GBP_INT_MIN_MAJOR_MINUS1 = BigMoney.ofMinor(GBP, (((long) Integer.MIN_VALUE) - 1) * 100);

    private static final BigMoney GBP_LONG_MAX_PLUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE));

    private static final BigMoney GBP_LONG_MIN_MINUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE));

    private static final BigMoney GBP_LONG_MAX_MAJOR_PLUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)));

    private static final BigMoney GBP_LONG_MIN_MAJOR_MINUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)));

    private static final BigMoney JPY_423 = BigMoney.parse("JPY 423");

    private static final BigMoney USD_1_23 = BigMoney.parse("USD 1.23");

    private static final BigMoney USD_2_34 = BigMoney.parse("USD 2.34");

    private static final BigMoney USD_2_35 = BigMoney.parse("USD 2.35");

    private static final BigMoneyProvider BAD_PROVIDER = () -> null;

    private static BigDecimal bd(String str) {
        return new BigDecimal(str);
    }

    public static Object[][] data_parse() {
        return new Object[][] { { "GBP 2.43", GBP, "2.43", 2 }, { "GBP +12.57", GBP, "12.57", 2 }, { "GBP -5.87", GBP, "-5.87", 2 }, { "GBP 0.99", GBP, "0.99", 2 }, { "GBP .99", GBP, "0.99", 2 }, { "GBP +.99", GBP, "0.99", 2 }, { "GBP +0.99", GBP, "0.99", 2 }, { "GBP -.99", GBP, "-0.99", 2 }, { "GBP -0.99", GBP, "-0.99", 2 }, { "GBP 0", GBP, "0", 0 }, { "GBP 2", GBP, "2", 0 }, { "GBP 123.", GBP, "123", 0 }, { "GBP3", GBP, "3", 0 }, { "GBP3.10", GBP, "3.10", 2 }, { "GBP  3.10", GBP, "3.10", 2 }, { "GBP   3.10", GBP, "3.10", 2 }, { "GBP                           3.10", GBP, "3.10", 2 }, { "GBP 123.456789", GBP, "123.456789", 6 } };
    }

    @Test
    void test_isZero_1() {
        assertThat(GBP_0_00.isZero()).isTrue();
    }

    @Test
    void test_isZero_2() {
        assertThat(GBP_2_34.isZero()).isFalse();
    }

    @Test
    void test_isZero_3() {
        assertThat(GBP_M5_78.isZero()).isFalse();
    }

    @Test
    void test_isPositive_1() {
        assertThat(GBP_0_00.isPositive()).isFalse();
    }

    @Test
    void test_isPositive_2() {
        assertThat(GBP_2_34.isPositive()).isTrue();
    }

    @Test
    void test_isPositive_3() {
        assertThat(GBP_M5_78.isPositive()).isFalse();
    }

    @Test
    void test_isPositiveOrZero_1() {
        assertThat(GBP_0_00.isPositiveOrZero()).isTrue();
    }

    @Test
    void test_isPositiveOrZero_2() {
        assertThat(GBP_2_34.isPositiveOrZero()).isTrue();
    }

    @Test
    void test_isPositiveOrZero_3() {
        assertThat(GBP_M5_78.isPositiveOrZero()).isFalse();
    }

    @Test
    void test_isNegative_1() {
        assertThat(GBP_0_00.isNegative()).isFalse();
    }

    @Test
    void test_isNegative_2() {
        assertThat(GBP_2_34.isNegative()).isFalse();
    }

    @Test
    void test_isNegative_3() {
        assertThat(GBP_M5_78.isNegative()).isTrue();
    }

    @Test
    void test_isNegativeOrZero_1() {
        assertThat(GBP_0_00.isNegativeOrZero()).isTrue();
    }

    @Test
    void test_isNegativeOrZero_2() {
        assertThat(GBP_2_34.isNegativeOrZero()).isFalse();
    }

    @Test
    void test_isNegativeOrZero_3() {
        assertThat(GBP_M5_78.isNegativeOrZero()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("Provider_test_factory_of_Currency_double_zero_1_3to4")
    void test_factory_of_Currency_double_zero_1_3to4(double param1, long param2, int param3) {
        assertThat(BigMoney.of(GBP, param1)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(param2, param3)));
    }

    static public Stream<Arguments> Provider_test_factory_of_Currency_double_zero_1_3to4() {
        return Stream.of(arguments(0d, 0L, 0), arguments(0.0d, 0L, 0), arguments(0.00d, 0L, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_factory_of_Currency_double_zero_2_5")
    void test_factory_of_Currency_double_zero_2_5(long param1, int param2, double param3) {
        assertThat(BigMoney.of(GBP, -param3)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(param1, param2)));
    }

    static public Stream<Arguments> Provider_test_factory_of_Currency_double_zero_2_5() {
        return Stream.of(arguments(0L, 0, 0d), arguments(0L, 0, 0.0d));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_isCurrencyScale_GBP_1to2_2to4_4")
    void test_isCurrencyScale_GBP_1to2_2to4_4(String param1) {
        assertThat(BigMoney.parse(param1).isCurrencyScale()).isFalse();
    }

    static public Stream<Arguments> Provider_test_isCurrencyScale_GBP_1to2_2to4_4() {
        return Stream.of(arguments("GBP 2"), arguments("GBP 2.3"), arguments("GBP 2.345"), arguments("JPY 2.3"), arguments("JPY 2.34"), arguments("JPY 2.345"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_isCurrencyScale_GBP_1_3")
    void test_isCurrencyScale_GBP_1_3(String param1) {
        assertThat(BigMoney.parse(param1).isCurrencyScale()).isTrue();
    }

    static public Stream<Arguments> Provider_test_isCurrencyScale_GBP_1_3() {
        return Stream.of(arguments("GBP 2.34"), arguments("JPY 2"));
    }
}
