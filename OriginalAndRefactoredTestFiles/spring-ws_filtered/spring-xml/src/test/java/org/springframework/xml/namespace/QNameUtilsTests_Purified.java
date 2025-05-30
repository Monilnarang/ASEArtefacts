package org.springframework.xml.namespace;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.springframework.xml.DocumentBuilderFactoryUtils;
import static org.assertj.core.api.Assertions.assertThat;

class QNameUtilsTests_Purified {

    @Test
    void testValidQNames_1() {
        assertThat(QNameUtils.validateQName("{namespace}local")).isTrue();
    }

    @Test
    void testValidQNames_2() {
        assertThat(QNameUtils.validateQName("local")).isTrue();
    }

    @Test
    void testInvalidQNames_1() {
        assertThat(QNameUtils.validateQName(null)).isFalse();
    }

    @Test
    void testInvalidQNames_2() {
        assertThat(QNameUtils.validateQName("")).isFalse();
    }

    @Test
    void testInvalidQNames_3() {
        assertThat(QNameUtils.validateQName("{namespace}")).isFalse();
    }

    @Test
    void testInvalidQNames_4() {
        assertThat(QNameUtils.validateQName("{namespace")).isFalse();
    }
}
