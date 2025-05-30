package org.springframework.ws.server.endpoint.adapter.method;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("Since15")
class StaxPayloadMethodArgumentResolverTests_Purified extends AbstractMethodArgumentResolverTests {

    private StaxPayloadMethodArgumentResolver resolver;

    private MethodParameter streamParameter;

    private MethodParameter eventParameter;

    private MethodParameter invalidParameter;

    @BeforeEach
    void setUp() throws Exception {
        this.resolver = new StaxPayloadMethodArgumentResolver();
        this.streamParameter = new MethodParameter(getClass().getMethod("streamReader", XMLStreamReader.class), 0);
        this.eventParameter = new MethodParameter(getClass().getMethod("eventReader", XMLEventReader.class), 0);
        this.invalidParameter = new MethodParameter(getClass().getMethod("invalid", XMLStreamReader.class), 0);
    }

    public void invalid(XMLStreamReader streamReader) {
    }

    public void streamReader(@RequestPayload XMLStreamReader streamReader) {
    }

    public void eventReader(@RequestPayload XMLEventReader streamReader) {
    }

    @Test
    void supportsParameter_1() {
        assertThat(this.resolver.supportsParameter(this.streamParameter)).isTrue();
    }

    @Test
    void supportsParameter_2() {
        assertThat(this.resolver.supportsParameter(this.eventParameter)).isTrue();
    }

    @Test
    void supportsParameter_3() {
        assertThat(this.resolver.supportsParameter(this.invalidParameter)).isFalse();
    }
}
