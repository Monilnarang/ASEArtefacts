package org.springframework.ws.server.endpoint.mapping;

import java.lang.reflect.Method;
import java.util.Collections;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.MessageDispatcher;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.PayloadRoots;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("payloadRootAnnotationMethodEndpointMapping.xml")
class PayloadRootAnnotationMethodEndpointMappingTests_Purified {

    @Autowired
    private PayloadRootAnnotationMethodEndpointMapping mapping;

    @Autowired
    private ApplicationContext applicationContext;

    @Endpoint
    public static class MyEndpoint {

        private static final org.apache.commons.logging.Log logger = LogFactory.getLog(MyEndpoint.class);

        private boolean doItInvoked = false;

        public boolean isDoItInvoked() {
            return this.doItInvoked;
        }

        @PayloadRoot(localPart = "Request", namespace = "http://springframework.org/spring-ws")
        @Log
        public void doIt(@RequestPayload Source payload) {
            this.doItInvoked = true;
            logger.info("In doIt()");
        }

        @PayloadRoots({ @PayloadRoot(localPart = "Request1", namespace = "http://springframework.org/spring-ws"), @PayloadRoot(localPart = "Request2", namespace = "http://springframework.org/spring-ws") })
        public void doItMultiple() {
        }

        @PayloadRoot(localPart = "Request3", namespace = "http://springframework.org/spring-ws")
        @PayloadRoot(localPart = "Request4", namespace = "http://springframework.org/spring-ws")
        public void doItRepeatable() {
        }
    }

    static class OtherBean {

        @PayloadRoot(localPart = "Invalid", namespace = "http://springframework.org/spring-ws")
        public void doIt() {
        }
    }

    @Test
    void invoke_1() throws Exception {
        MyEndpoint endpoint = this.applicationContext.getBean("endpoint", MyEndpoint.class);
        assertThat(endpoint.isDoItInvoked()).isTrue();
    }

    @Test
    void invoke_2() throws Exception {
        LogAspect aspect = (LogAspect) this.applicationContext.getBean("logAspect");
        assertThat(aspect.isLogInvoked()).isTrue();
    }
}
