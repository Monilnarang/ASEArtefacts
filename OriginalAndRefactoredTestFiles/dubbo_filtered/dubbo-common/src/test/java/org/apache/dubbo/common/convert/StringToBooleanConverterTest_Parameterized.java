package org.apache.dubbo.common.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringToBooleanConverterTest_Parameterized {

    private StringToBooleanConverter converter;

    @BeforeEach
    public void init() {
        converter = (StringToBooleanConverter) getExtensionLoader(Converter.class).getExtension("string-to-boolean");
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

    @ParameterizedTest
    @MethodSource("Provider_testConvert_1to3")
    void testConvert_1to3(boolean param1) {
        assertTrue(converter.convert(param1));
    }

    static public Stream<Arguments> Provider_testConvert_1to3() {
        return Stream.of(arguments(true), arguments(true), arguments(true));
    }
}
