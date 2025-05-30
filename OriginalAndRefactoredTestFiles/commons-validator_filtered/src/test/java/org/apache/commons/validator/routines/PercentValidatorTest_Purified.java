package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

public class PercentValidatorTest_Purified {

    protected PercentValidator validator;

    private Locale originalLocale;

    @BeforeEach
    protected void setUp() {
        originalLocale = Locale.getDefault();
        validator = new PercentValidator();
    }

    @AfterEach
    protected void tearDown() {
        Locale.setDefault(originalLocale);
        validator = null;
    }

    @Test
    public void testFormatType_1() {
        assertEquals(2, PercentValidator.getInstance().getFormatType(), "Format Type A");
    }

    @Test
    public void testFormatType_2() {
        assertEquals(AbstractNumberValidator.PERCENT_FORMAT, PercentValidator.getInstance().getFormatType(), "Format Type B");
    }
}
