package org.graylog2.utilities;

import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProxyHostsPatternTest_Purified {

    private AbstractBooleanAssert assertPattern(String pattern, String hostOrIp) {
        return assertThat(ProxyHostsPattern.create(pattern).matches(hostOrIp));
    }

    @Test
    public void matches_1() {
        assertPattern(null, "127.0.0.1").isFalse();
    }

    @Test
    public void matches_2() {
        assertPattern("", "127.0.0.1").isFalse();
    }

    @Test
    public void matches_3() {
        assertPattern(",,", "127.0.0.1").isFalse();
    }

    @Test
    public void matches_4() {
        assertPattern("127.0.0.1", "127.0.0.1").isTrue();
    }

    @Test
    public void matches_5() {
        assertPattern("127.0.0.1", "127.0.0.2").isFalse();
    }

    @Test
    public void matches_6() {
        assertPattern("127.0.0.*", "127.0.0.1").isTrue();
    }

    @Test
    public void matches_7() {
        assertPattern("127.0.*", "127.0.0.1").isTrue();
    }

    @Test
    public void matches_8() {
        assertPattern("127.0.*,10.0.0.*", "127.0.0.1").isTrue();
    }

    @Test
    public void matches_9() {
        assertPattern("node0.graylog.example.com", "node0.graylog.example.com").isTrue();
    }

    @Test
    public void matches_10() {
        assertPattern("node0.graylog.example.com", "node1.graylog.example.com").isFalse();
    }

    @Test
    public void matches_11() {
        assertPattern("*.graylog.example.com", "node0.graylog.example.com").isTrue();
    }

    @Test
    public void matches_12() {
        assertPattern("*.graylog.example.com", "node1.graylog.example.com").isTrue();
    }

    @Test
    public void matches_13() {
        assertPattern("node0.graylog.example.*", "node0.GRAYLOG.example.com").isTrue();
    }

    @Test
    public void matches_14() {
        assertPattern("node0.graylog.example.*,127.0.0.1,*.graylog.example.com", "node1.graylog.example.com").isTrue();
    }

    @Test
    public void matches_15() {
        assertPattern("127.0.*.1", "127.0.0.1").isFalse();
    }

    @Test
    public void matches_16() {
        assertPattern("node0.*.example.com", "node0.graylog.example.com").isFalse();
    }

    @Test
    public void matches_17() {
        assertPattern("*.0.0.*", "127.0.0.1").isFalse();
    }
}
