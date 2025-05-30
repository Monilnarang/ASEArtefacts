package org.apache.dubbo.common.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringToBooleanConverterTest_Purified {

    private StringToBooleanConverter converter;

    @BeforeEach
    public void init() {
        converter = (StringToBooleanConverter) getExtensionLoader(Converter.class).getExtension("string-to-boolean");
    }

    @Test
    void testConvert_1() {
        assertTrue(converter.convert("true"));
    }

    @Test
    void testConvert_2() {
        assertTrue(converter.convert("true"));
    }

    @Test
    void testConvert_3() {
        assertTrue(converter.convert("True"));
    }

    @Test
    void testConvert_4() {
        assertFalse(converter.convert("a"));
    }

    @Test
    void testConvert_5() {
        assertNull(converter.convert(""));
    }

    @Test
    void testConvert_6() {
        assertNull(converter.convert(null));
    }
}
