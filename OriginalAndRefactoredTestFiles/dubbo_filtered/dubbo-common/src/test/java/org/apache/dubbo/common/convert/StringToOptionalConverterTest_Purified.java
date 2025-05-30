package org.apache.dubbo.common.convert;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringToOptionalConverterTest_Purified {

    private StringToOptionalConverter converter;

    @BeforeEach
    public void init() {
        converter = (StringToOptionalConverter) getExtensionLoader(Converter.class).getExtension("string-to-optional");
    }

    @Test
    void testConvert_1() {
        assertEquals(Optional.of("1"), converter.convert("1"));
    }

    @Test
    void testConvert_2() {
        assertEquals(Optional.empty(), converter.convert(null));
    }
}
