package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConversionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBaseNullConfiguration_Purified {

    protected BaseConfiguration config;

    @BeforeEach
    public void setUp() throws Exception {
        config = new BaseConfiguration();
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        config.setThrowExceptionOnMissing(false);
    }

    @Test
    public void testGetProperty_1() {
        assertNull(config.getProperty("foo"));
    }

    @Test
    public void testGetProperty_2_testMerged_2() {
        config.setProperty("number", "1");
        assertEquals("1", config.getProperty("number"));
        assertEquals("1", config.getString("number"));
    }
}
