package org.joda.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import org.junit.jupiter.api.Test;

class TestMoneyUtils_Money_Purified {

    private static final Money GBP_0 = Money.parse("GBP 0");

    private static final Money GBP_20 = Money.parse("GBP 20");

    private static final Money GBP_30 = Money.parse("GBP 30");

    private static final Money GBP_50 = Money.parse("GBP 50");

    private static final Money GBP_M10 = Money.parse("GBP -10");

    private static final Money GBP_M30 = Money.parse("GBP -30");

    private static final Money EUR_30 = Money.parse("EUR 30");

    @Test
    void test_isZero_1() {
        assertThat(MoneyUtils.isZero(null)).isTrue();
    }

    @Test
    void test_isZero_2() {
        assertThat(MoneyUtils.isZero(GBP_0)).isTrue();
    }

    @Test
    void test_isZero_3() {
        assertThat(MoneyUtils.isZero(GBP_30)).isFalse();
    }

    @Test
    void test_isZero_4() {
        assertThat(MoneyUtils.isZero(GBP_M30)).isFalse();
    }

    @Test
    void test_isPositive_1() {
        assertThat(MoneyUtils.isPositive(null)).isFalse();
    }

    @Test
    void test_isPositive_2() {
        assertThat(MoneyUtils.isPositive(GBP_0)).isFalse();
    }

    @Test
    void test_isPositive_3() {
        assertThat(MoneyUtils.isPositive(GBP_30)).isTrue();
    }

    @Test
    void test_isPositive_4() {
        assertThat(MoneyUtils.isPositive(GBP_M30)).isFalse();
    }

    @Test
    void test_isPositiveOrZero_1() {
        assertThat(MoneyUtils.isPositiveOrZero(null)).isTrue();
    }

    @Test
    void test_isPositiveOrZero_2() {
        assertThat(MoneyUtils.isPositiveOrZero(GBP_0)).isTrue();
    }

    @Test
    void test_isPositiveOrZero_3() {
        assertThat(MoneyUtils.isPositiveOrZero(GBP_30)).isTrue();
    }

    @Test
    void test_isPositiveOrZero_4() {
        assertThat(MoneyUtils.isPositiveOrZero(GBP_M30)).isFalse();
    }

    @Test
    void test_isNegative_1() {
        assertThat(MoneyUtils.isNegative(null)).isFalse();
    }

    @Test
    void test_isNegative_2() {
        assertThat(MoneyUtils.isNegative(GBP_0)).isFalse();
    }

    @Test
    void test_isNegative_3() {
        assertThat(MoneyUtils.isNegative(GBP_30)).isFalse();
    }

    @Test
    void test_isNegative_4() {
        assertThat(MoneyUtils.isNegative(GBP_M30)).isTrue();
    }

    @Test
    void test_isNegativeOrZero_1() {
        assertThat(MoneyUtils.isNegativeOrZero(null)).isTrue();
    }

    @Test
    void test_isNegativeOrZero_2() {
        assertThat(MoneyUtils.isNegativeOrZero(GBP_0)).isTrue();
    }

    @Test
    void test_isNegativeOrZero_3() {
        assertThat(MoneyUtils.isNegativeOrZero(GBP_30)).isFalse();
    }

    @Test
    void test_isNegativeOrZero_4() {
        assertThat(MoneyUtils.isNegativeOrZero(GBP_M30)).isTrue();
    }
}
