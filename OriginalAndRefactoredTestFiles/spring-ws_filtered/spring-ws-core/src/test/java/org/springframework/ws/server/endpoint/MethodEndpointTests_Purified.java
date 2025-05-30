package org.springframework.ws.server.endpoint;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MethodEndpointTests_Purified {

    private MethodEndpoint endpoint;

    private boolean myMethodInvoked;

    private Method method;

    @BeforeEach
    void setUp() throws Exception {
        this.myMethodInvoked = false;
        this.method = getClass().getMethod("myMethod", String.class);
        this.endpoint = new MethodEndpoint(this, this.method);
    }

    public void myMethod(String arg) {
        assertThat(arg).isEqualTo("arg");
        this.myMethodInvoked = true;
    }

    @Test
    void testGetters_1() {
        assertThat(this.endpoint.getBean()).isEqualTo(this);
    }

    @Test
    void testGetters_2() {
        assertThat(this.endpoint.getMethod()).isEqualTo(this.method);
    }

    @Test
    void testInvoke_1() throws Exception {
        assertThat(this.myMethodInvoked).isFalse();
    }

    @Test
    void testInvoke_2() throws Exception {
        assertThat(this.myMethodInvoked).isTrue();
    }

    @Test
    void testEquals_1() throws Exception {
        assertThat(this.endpoint).isEqualTo(new MethodEndpoint(this, this.method));
    }

    @Test
    void testEquals_2() throws Exception {
        Method otherMethod = getClass().getDeclaredMethod("testEquals");
        assertThat(new MethodEndpoint(this, otherMethod).equals(this.endpoint)).isFalse();
    }

    @Test
    void testHashCode_1() throws Exception {
        assertThat(this.endpoint.hashCode()).isEqualTo(new MethodEndpoint(this, this.method).hashCode());
    }

    @Test
    void testHashCode_2() throws Exception {
        Method otherMethod = getClass().getDeclaredMethod("testEquals");
        assertThat(new MethodEndpoint(this, otherMethod).hashCode() == this.endpoint.hashCode()).isFalse();
    }
}
