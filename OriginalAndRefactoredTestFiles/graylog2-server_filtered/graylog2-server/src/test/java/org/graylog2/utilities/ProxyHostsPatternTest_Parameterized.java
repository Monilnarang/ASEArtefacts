package org.graylog2.utilities;

import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ProxyHostsPatternTest_Parameterized {

    private AbstractBooleanAssert assertPattern(String pattern, String hostOrIp) {
        return assertThat(ProxyHostsPattern.create(pattern).matches(hostOrIp));
    }

    @Test
    public void matches_1() {
        assertPattern(null, "127.0.0.1").isFalse();
    }

    @ParameterizedTest
    @MethodSource("Provider_matches_2to3_5_10_15to17")
    public void matches_2to3_5_10_15to17(String param1, String param2) {
        assertPattern(param1, param2).isFalse();
    }

    static public Stream<Arguments> Provider_matches_2to3_5_10_15to17() {
        return Stream.of(arguments("", "127.0.0.1"), arguments(",,", "127.0.0.1"), arguments("127.0.0.1", "127.0.0.2"), arguments("node0.graylog.example.com", "node1.graylog.example.com"), arguments("127.0.*.1", "127.0.0.1"), arguments("node0.*.example.com", "node0.graylog.example.com"), arguments("*.0.0.*", "127.0.0.1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_matches_4_6to9_11to14")
    public void matches_4_6to9_11to14(String param1, String param2) {
        assertPattern(param1, param2).isTrue();
    }

    static public Stream<Arguments> Provider_matches_4_6to9_11to14() {
        return Stream.of(arguments("127.0.0.1", "127.0.0.1"), arguments("127.0.0.*", "127.0.0.1"), arguments("127.0.*", "127.0.0.1"), arguments("127.0.*,10.0.0.*", "127.0.0.1"), arguments("node0.graylog.example.com", "node0.graylog.example.com"), arguments("*.graylog.example.com", "node0.graylog.example.com"), arguments("*.graylog.example.com", "node1.graylog.example.com"), arguments("node0.graylog.example.*", "node0.GRAYLOG.example.com"), arguments("node0.graylog.example.*,127.0.0.1,*.graylog.example.com", "node1.graylog.example.com"));
    }
}
