package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

@DefaultLocale(country = "US", language = "en")
public abstract class AbstractNumberValidatorTest_Purified {

    protected AbstractNumberValidator validator;

    protected AbstractNumberValidator strictValidator;

    protected Number max;

    protected Number maxPlusOne;

    protected Number min;

    protected Number minMinusOne;

    protected String[] invalid;

    protected String[] valid;

    protected Number[] validCompare;

    protected String[] invalidStrict;

    protected String[] validStrict;

    protected Number[] validStrictCompare;

    protected String testPattern;

    protected Number testNumber;

    protected Number testZero;

    protected String testStringUS;

    protected String testStringDE;

    protected String localeValue;

    protected String localePattern;

    protected Locale testLocale;

    protected Number localeExpected;

    @AfterEach
    protected void tearDown() {
        validator = null;
        strictValidator = null;
    }

    @Test
    public void testFormatType_1() {
        assertEquals(0, validator.getFormatType(), "Format Type A");
    }

    @Test
    public void testFormatType_2() {
        assertEquals(AbstractNumberValidator.STANDARD_FORMAT, validator.getFormatType(), "Format Type B");
    }

    @Test
    public void testValidateLocale_1() {
        assertEquals(testNumber, strictValidator.parse(testStringUS, null, Locale.US), "US Locale, US Format");
    }

    @Test
    public void testValidateLocale_2() {
        assertNull(strictValidator.parse(testStringDE, null, Locale.US), "US Locale, DE Format");
    }

    @Test
    public void testValidateLocale_3() {
        assertEquals(testNumber, strictValidator.parse(testStringDE, null, Locale.GERMAN), "DE Locale, DE Format");
    }

    @Test
    public void testValidateLocale_4() {
        assertNull(strictValidator.parse(testStringUS, null, Locale.GERMAN), "DE Locale, US Format");
    }

    @Test
    public void testValidateLocale_5() {
        assertEquals(testNumber, strictValidator.parse(testStringUS, null, null), "Default Locale, US Format");
    }

    @Test
    public void testValidateLocale_6() {
        assertNull(strictValidator.parse(testStringDE, null, null), "Default Locale, DE Format");
    }
}
