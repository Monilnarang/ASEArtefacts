package org.springframework.xml.namespace;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.springframework.xml.DocumentBuilderFactoryUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class QNameUtilsTests_Parameterized {

    @Test
    void testInvalidQNames_1() {
        assertThat(QNameUtils.validateQName(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidQNames_1to2")
    void testValidQNames_1to2(String param1) {
        assertThat(QNameUtils.validateQName(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testValidQNames_1to2() {
        return Stream.of(arguments("{namespace}local"), arguments("local"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidQNames_2to4")
    void testInvalidQNames_2to4(String param1) {
        assertThat(QNameUtils.validateQName(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testInvalidQNames_2to4() {
        return Stream.of(arguments(""), arguments("{namespace}"), arguments("{namespace"));
    }
}
