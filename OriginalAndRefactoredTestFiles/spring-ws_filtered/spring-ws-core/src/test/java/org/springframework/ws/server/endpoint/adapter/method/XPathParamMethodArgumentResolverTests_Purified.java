package org.springframework.ws.server.endpoint.adapter.method;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.springframework.core.MethodParameter;
import org.springframework.ws.MockWebServiceMessage;
import org.springframework.ws.MockWebServiceMessageFactory;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.Namespaces;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import static org.assertj.core.api.Assertions.assertThat;

@Namespaces(@Namespace(prefix = "tns", uri = "http://springframework.org/spring-ws"))
class XPathParamMethodArgumentResolverTests_Purified {

    private static final String CONTENTS = "<root><child><text>text</text><number>42</number></child></root>";

    private XPathParamMethodArgumentResolver resolver;

    private MethodParameter booleanParameter;

    private MethodParameter doubleParameter;

    private MethodParameter nodeParameter;

    private MethodParameter nodeListParameter;

    private MethodParameter stringParameter;

    private MethodParameter convertedParameter;

    private MethodParameter unsupportedParameter;

    private MethodParameter namespaceMethodParameter;

    private MethodParameter namespaceClassParameter;

    @BeforeEach
    void setUp() throws Exception {
        this.resolver = new XPathParamMethodArgumentResolver();
        Method supportedTypes = getClass().getMethod("supportedTypes", Boolean.TYPE, Double.TYPE, Node.class, NodeList.class, String.class);
        this.booleanParameter = new MethodParameter(supportedTypes, 0);
        this.doubleParameter = new MethodParameter(supportedTypes, 1);
        this.nodeParameter = new MethodParameter(supportedTypes, 2);
        this.nodeListParameter = new MethodParameter(supportedTypes, 3);
        this.stringParameter = new MethodParameter(supportedTypes, 4);
        this.convertedParameter = new MethodParameter(getClass().getMethod("convertedType", Integer.TYPE), 0);
        this.unsupportedParameter = new MethodParameter(getClass().getMethod("unsupported", String.class), 0);
        this.namespaceMethodParameter = new MethodParameter(getClass().getMethod("namespacesMethod", String.class), 0);
        this.namespaceClassParameter = new MethodParameter(getClass().getMethod("namespacesClass", String.class), 0);
    }

    public void unsupported(String s) {
    }

    public void supportedTypes(@XPathParam("/root/child") boolean param1, @XPathParam("/root/child/number") double param2, @XPathParam("/root/child") Node param3, @XPathParam("/root/*") NodeList param4, @XPathParam("/root/child/text") String param5) {
    }

    public void convertedType(@XPathParam("/root/child/number") int param) {
    }

    @Namespaces(@Namespace(prefix = "tns", uri = "http://springframework.org/spring-ws"))
    public void namespacesMethod(@XPathParam("/tns:root") String s) {
    }

    public void namespacesClass(@XPathParam("/tns:root") String s) {
    }

    @Test
    void supportsParameter_1() {
        assertThat(this.resolver.supportsParameter(this.booleanParameter)).isTrue();
    }

    @Test
    void supportsParameter_2() {
        assertThat(this.resolver.supportsParameter(this.doubleParameter)).isTrue();
    }

    @Test
    void supportsParameter_3() {
        assertThat(this.resolver.supportsParameter(this.nodeParameter)).isTrue();
    }

    @Test
    void supportsParameter_4() {
        assertThat(this.resolver.supportsParameter(this.nodeListParameter)).isTrue();
    }

    @Test
    void supportsParameter_5() {
        assertThat(this.resolver.supportsParameter(this.stringParameter)).isTrue();
    }

    @Test
    void supportsParameter_6() {
        assertThat(this.resolver.supportsParameter(this.convertedParameter)).isTrue();
    }

    @Test
    void supportsParameter_7() {
        assertThat(this.resolver.supportsParameter(this.unsupportedParameter)).isFalse();
    }
}
