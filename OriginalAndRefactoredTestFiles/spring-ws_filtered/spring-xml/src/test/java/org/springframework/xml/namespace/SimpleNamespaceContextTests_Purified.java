package org.springframework.xml.namespace;

import java.util.Collections;
import java.util.Iterator;
import javax.xml.XMLConstants;
import org.apache.commons.collections4.IteratorUtils;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SimpleNamespaceContextTests_Purified {

    private SimpleNamespaceContext context;

    @BeforeEach
    void setUp() {
        this.context = new SimpleNamespaceContext();
        this.context.bindNamespaceUri("prefix", "namespaceURI");
    }

    private void assertPrefixes(String namespaceUri, String prefix) {
        Iterator<String> iterator = this.context.getPrefixes(namespaceUri);
        assertThat(iterator).isNotNull();
        assertThat(iterator.hasNext()).isTrue();
        String result = iterator.next();
        assertThat(result).isEqualTo(prefix);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void testGetNamespaceURI_1() {
        assertThat(this.context.getNamespaceURI(XMLConstants.DEFAULT_NS_PREFIX)).isEmpty();
    }

    @Test
    void testGetNamespaceURI_2_testMerged_2() {
        String defaultNamespaceUri = "defaultNamespace";
        this.context.bindNamespaceUri(XMLConstants.DEFAULT_NS_PREFIX, defaultNamespaceUri);
        assertThat(this.context.getNamespaceURI(XMLConstants.DEFAULT_NS_PREFIX)).isEqualTo(defaultNamespaceUri);
        assertThat(this.context.getNamespaceURI(XMLConstants.XML_NS_PREFIX)).isEqualTo(XMLConstants.XML_NS_URI);
        assertThat(this.context.getNamespaceURI(XMLConstants.XMLNS_ATTRIBUTE)).isEqualTo(XMLConstants.XMLNS_ATTRIBUTE_NS_URI);
    }

    @Test
    void testGetNamespaceURI_3() {
        assertThat(this.context.getNamespaceURI("prefix")).isEqualTo("namespaceURI");
    }

    @Test
    void testGetNamespaceURI_4() {
        assertThat(this.context.getNamespaceURI("unbound")).isEmpty();
    }

    @Test
    void testGetPrefix_1() {
        assertThat(this.context.getPrefix("defaultNamespaceURI")).isEqualTo(XMLConstants.DEFAULT_NS_PREFIX);
    }

    @Test
    void testGetPrefix_2() {
        assertThat(this.context.getPrefix("namespaceURI")).isEqualTo("prefix");
    }

    @Test
    void testGetPrefix_3() {
        assertThat(this.context.getPrefix("unbound")).isNull();
    }

    @Test
    void testGetPrefix_4() {
        assertThat(this.context.getPrefix(XMLConstants.XML_NS_URI)).isEqualTo(XMLConstants.XML_NS_PREFIX);
    }

    @Test
    void testGetPrefix_5() {
        assertThat(this.context.getPrefix(XMLConstants.XMLNS_ATTRIBUTE_NS_URI)).isEqualTo(XMLConstants.XMLNS_ATTRIBUTE);
    }

    @Test
    void testGetPrefixes_1() {
        assertPrefixes("defaultNamespaceURI", XMLConstants.DEFAULT_NS_PREFIX);
    }

    @Test
    void testGetPrefixes_2() {
        assertPrefixes("namespaceURI", "prefix");
    }

    @Test
    void testGetPrefixes_3() {
        assertThat(this.context.getPrefixes("unbound").hasNext()).isFalse();
    }

    @Test
    void testGetPrefixes_4() {
        assertPrefixes(XMLConstants.XML_NS_URI, XMLConstants.XML_NS_PREFIX);
    }

    @Test
    void testGetPrefixes_5() {
        assertPrefixes(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, XMLConstants.XMLNS_ATTRIBUTE);
    }
}
