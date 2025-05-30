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

class TestBigMoney_Purified {

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
    void test_factory_of_Currency_double_zero_1() {
        assertThat(BigMoney.of(GBP, 0d)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(0L, 0)));
    }

    @Test
    void test_factory_of_Currency_double_zero_2() {
        assertThat(BigMoney.of(GBP, -0d)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(0L, 0)));
    }

    @Test
    void test_factory_of_Currency_double_zero_3() {
        assertThat(BigMoney.of(GBP, 0.0d)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(0L, 0)));
    }

    @Test
    void test_factory_of_Currency_double_zero_4() {
        assertThat(BigMoney.of(GBP, 0.00d)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(0L, 0)));
    }

    @Test
    void test_factory_of_Currency_double_zero_5() {
        assertThat(BigMoney.of(GBP, -0.0d)).isEqualTo(BigMoney.of(GBP, BigDecimal.valueOf(0L, 0)));
    }

    @Test
    void test_isCurrencyScale_GBP_1() {
        assertThat(BigMoney.parse("GBP 2").isCurrencyScale()).isFalse();
    }

    @Test
    void test_isCurrencyScale_GBP_2() {
        assertThat(BigMoney.parse("GBP 2.3").isCurrencyScale()).isFalse();
    }

    @Test
    void test_isCurrencyScale_GBP_3() {
        assertThat(BigMoney.parse("GBP 2.34").isCurrencyScale()).isTrue();
    }

    @Test
    void test_isCurrencyScale_GBP_4() {
        assertThat(BigMoney.parse("GBP 2.345").isCurrencyScale()).isFalse();
    }

    @Test
    void test_isCurrencyScale_JPY_1() {
        assertThat(BigMoney.parse("JPY 2").isCurrencyScale()).isTrue();
    }

    @Test
    void test_isCurrencyScale_JPY_2() {
        assertThat(BigMoney.parse("JPY 2.3").isCurrencyScale()).isFalse();
    }

    @Test
    void test_isCurrencyScale_JPY_3() {
        assertThat(BigMoney.parse("JPY 2.34").isCurrencyScale()).isFalse();
    }

    @Test
    void test_isCurrencyScale_JPY_4() {
        assertThat(BigMoney.parse("JPY 2.345").isCurrencyScale()).isFalse();
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
}
