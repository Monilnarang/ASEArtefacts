package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

public class CurrencyValidatorTest_Purified {

    private static final char CURRENCY_SYMBOL = '\u00A4';

    private String usDollar;

    private String ukPound;

    @BeforeEach
    protected void setUp() {
        usDollar = new DecimalFormatSymbols(Locale.US).getCurrencySymbol();
        ukPound = new DecimalFormatSymbols(Locale.UK).getCurrencySymbol();
    }

    @Test
    public void testFormatType_1() {
        assertEquals(1, CurrencyValidator.getInstance().getFormatType(), "Format Type A");
    }

    @Test
    public void testFormatType_2() {
        assertEquals(AbstractNumberValidator.CURRENCY_FORMAT, CurrencyValidator.getInstance().getFormatType(), "Format Type B");
    }
}
