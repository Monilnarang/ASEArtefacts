package org.joda.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TestMoney_Purified {

    private static final CurrencyUnit GBP = CurrencyUnit.of("GBP");

    private static final CurrencyUnit EUR = CurrencyUnit.of("EUR");

    private static final CurrencyUnit USD = CurrencyUnit.of("USD");

    private static final CurrencyUnit JPY = CurrencyUnit.of("JPY");

    private static final BigDecimal BIGDEC_2_3 = new BigDecimal("2.3");

    private static final BigDecimal BIGDEC_2_34 = new BigDecimal("2.34");

    private static final BigDecimal BIGDEC_2_345 = new BigDecimal("2.345");

    private static final BigDecimal BIGDEC_M5_78 = new BigDecimal("-5.78");

    private static final Money GBP_0_00 = Money.parse("GBP 0.00");

    private static final Money GBP_1_23 = Money.parse("GBP 1.23");

    private static final Money GBP_2_33 = Money.parse("GBP 2.33");

    private static final Money GBP_2_34 = Money.parse("GBP 2.34");

    private static final Money GBP_2_35 = Money.parse("GBP 2.35");

    private static final Money GBP_2_36 = Money.parse("GBP 2.36");

    private static final Money GBP_5_78 = Money.parse("GBP 5.78");

    private static final Money GBP_M1_23 = Money.parse("GBP -1.23");

    private static final Money GBP_M5_78 = Money.parse("GBP -5.78");

    private static final Money GBP_INT_MAX_PLUS1 = Money.ofMinor(GBP, ((long) Integer.MAX_VALUE) + 1);

    private static final Money GBP_INT_MIN_MINUS1 = Money.ofMinor(GBP, ((long) Integer.MIN_VALUE) - 1);

    private static final Money GBP_INT_MAX_MAJOR_PLUS1 = Money.ofMinor(GBP, (((long) Integer.MAX_VALUE) + 1) * 100);

    private static final Money GBP_INT_MIN_MAJOR_MINUS1 = Money.ofMinor(GBP, (((long) Integer.MIN_VALUE) - 1) * 100);

    private static final Money GBP_LONG_MAX_PLUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE));

    private static final Money GBP_LONG_MIN_MINUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE));

    private static final Money GBP_LONG_MAX_MAJOR_PLUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)));

    private static final Money GBP_LONG_MIN_MAJOR_MINUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)));

    private static final Money JPY_423 = Money.parse("JPY 423");

    private static final Money USD_1_23 = Money.parse("USD 1.23");

    private static final Money USD_2_34 = Money.parse("USD 2.34");

    private static final Money USD_2_35 = Money.parse("USD 2.35");

    public static Object[][] data_parse() {
        return new Object[][] { { "GBP 2.43", GBP, 243 }, { "GBP +12.57", GBP, 1257 }, { "GBP -5.87", GBP, -587 }, { "GBP 0.99", GBP, 99 }, { "GBP .99", GBP, 99 }, { "GBP +.99", GBP, 99 }, { "GBP +0.99", GBP, 99 }, { "GBP -.99", GBP, -99 }, { "GBP -0.99", GBP, -99 }, { "GBP 0", GBP, 0 }, { "GBP 2", GBP, 200 }, { "GBP 123.", GBP, 12300 }, { "GBP3", GBP, 300 }, { "GBP3.10", GBP, 310 }, { "GBP  3.10", GBP, 310 }, { "GBP   3.10", GBP, 310 }, { "GBP                           3.10", GBP, 310 } };
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
