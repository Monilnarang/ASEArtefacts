package org.apache.commons.configuration2.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDefaultConfigurationKey_Purified {

    private static final String TESTPROPS = "tables.table(0).fields.field(1)";

    private static final String TESTATTR = "[@dataType]";

    private static final String TESTKEY = TESTPROPS + TESTATTR;

    private DefaultExpressionEngine expressionEngine;

    private DefaultConfigurationKey key;

    private DefaultConfigurationKey key(final String k) {
        return new DefaultConfigurationKey(expressionEngine, k);
    }

    @BeforeEach
    public void setUp() throws Exception {
        expressionEngine = DefaultExpressionEngine.INSTANCE;
        key = new DefaultConfigurationKey(expressionEngine);
    }

    private DefaultExpressionEngineSymbols.Builder symbols() {
        return new DefaultExpressionEngineSymbols.Builder(expressionEngine.getSymbols());
    }

    @Test
    public void testAttributeName_1() {
        assertEquals("test", key.attributeName("test"));
    }

    @Test
    public void testAttributeName_2() {
        assertEquals("dataType", key.attributeName(TESTATTR));
    }

    @Test
    public void testAttributeName_3() {
        assertNull(key.attributeName(null));
    }

    @Test
    public void testConstructAttributeKey_1() {
        assertEquals(TESTATTR, key.constructAttributeKey("dataType"));
    }

    @Test
    public void testConstructAttributeKey_2() {
        assertEquals(TESTATTR, key.constructAttributeKey(TESTATTR));
    }

    @Test
    public void testConstructAttributeKey_3() {
        assertEquals("", key.constructAttributeKey(null));
    }

    @Test
    public void testIsAttributeKey_1() {
        assertTrue(key.isAttributeKey(TESTATTR));
    }

    @Test
    public void testIsAttributeKey_2() {
        assertFalse(key.isAttributeKey(TESTPROPS));
    }

    @Test
    public void testIsAttributeKey_3() {
        assertFalse(key.isAttributeKey(null));
    }

    @Test
    public void testTrim_1() {
        assertEquals("test", key.trim(".test."));
    }

    @Test
    public void testTrim_2() {
        assertEquals("", key.trim(null));
    }

    @Test
    public void testTrim_3() {
        assertEquals("", key.trim(DefaultExpressionEngineSymbols.DEFAULT_PROPERTY_DELIMITER));
    }

    @Test
    public void testTrimLeft_1() {
        assertEquals("test.", key.trimLeft(".test."));
    }

    @Test
    public void testTrimLeft_2() {
        assertEquals("..test.", key.trimLeft("..test."));
    }

    @Test
    public void testTrimRight_1() {
        assertEquals(".test", key.trimRight(".test."));
    }

    @Test
    public void testTrimRight_2() {
        assertEquals(".test..", key.trimRight(".test.."));
    }
}
