package org.joda.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class TestCurrencyUnit_Purified {

    private static final Currency JDK_GBP = Currency.getInstance("GBP");

    @Test
    void test_constants_1() {
        assertThat(CurrencyUnit.of("USD")).isEqualTo(CurrencyUnit.USD);
    }

    @Test
    void test_constants_2() {
        assertThat(CurrencyUnit.of("EUR")).isEqualTo(CurrencyUnit.EUR);
    }

    @Test
    void test_constants_3() {
        assertThat(CurrencyUnit.of("JPY")).isEqualTo(CurrencyUnit.JPY);
    }

    @Test
    void test_constants_4() {
        assertThat(CurrencyUnit.of("GBP")).isEqualTo(CurrencyUnit.GBP);
    }

    @Test
    void test_constants_5() {
        assertThat(CurrencyUnit.of("CHF")).isEqualTo(CurrencyUnit.CHF);
    }

    @Test
    void test_constants_6() {
        assertThat(CurrencyUnit.of("AUD")).isEqualTo(CurrencyUnit.AUD);
    }

    @Test
    void test_constants_7() {
        assertThat(CurrencyUnit.of("CAD")).isEqualTo(CurrencyUnit.CAD);
    }
}
