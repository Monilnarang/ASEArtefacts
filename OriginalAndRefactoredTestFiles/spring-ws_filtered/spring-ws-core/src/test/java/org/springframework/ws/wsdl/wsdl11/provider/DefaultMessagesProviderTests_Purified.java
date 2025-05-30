package org.springframework.ws.wsdl.wsdl11.provider;

import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.wsdl.Types;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.xml.DocumentBuilderFactoryUtils;
import org.springframework.xml.sax.SaxUtils;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultMessagesProviderTests_Purified {

    private DefaultMessagesProvider provider;

    private Definition definition;

    private DocumentBuilder documentBuilder;

    @BeforeEach
    void setUp() throws Exception {
        this.provider = new DefaultMessagesProvider();
        WSDLFactory factory = WSDLFactory.newInstance();
        this.definition = factory.newDefinition();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactoryUtils.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAddMessages_1() throws Exception {
        assertThat(this.definition.getMessages()).hasSize(3);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAddMessages_2_testMerged_2() throws Exception {
        String definitionNamespace = "http://springframework.org/spring-ws";
        this.definition.addNamespace("tns", definitionNamespace);
        this.definition.setTargetNamespace(definitionNamespace);
        String schemaNamespace = "http://www.springframework.org/spring-ws/schema";
        this.definition.addNamespace("schema", schemaNamespace);
        Message message = this.definition.getMessage(new QName(definitionNamespace, "GetOrderRequest"));
        assertThat(message).isNotNull();
        Part part = message.getPart("GetOrderRequest");
        assertThat(part).isNotNull();
        assertThat(part.getElementName()).isEqualTo(new QName(schemaNamespace, "GetOrderRequest"));
        message = this.definition.getMessage(new QName(definitionNamespace, "GetOrderResponse"));
        part = message.getPart("GetOrderResponse");
        assertThat(part.getElementName()).isEqualTo(new QName(schemaNamespace, "GetOrderResponse"));
        message = this.definition.getMessage(new QName(definitionNamespace, "GetOrderFault"));
        part = message.getPart("GetOrderFault");
        assertThat(part.getElementName()).isEqualTo(new QName(schemaNamespace, "GetOrderFault"));
    }
}
