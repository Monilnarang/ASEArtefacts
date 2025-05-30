package org.apache.dubbo.common.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringToStringConverterTest_Purified {

    private StringToStringConverter converter;

    @BeforeEach
    public void init() {
        converter = (StringToStringConverter) getExtensionLoader(Converter.class).getExtension("string-to-string");
    }

    @Test
    void testConvert_1() {
        assertEquals("1", converter.convert("1"));
    }

    @Test
    void testConvert_2() {
        assertNull(converter.convert(null));
    }
}
