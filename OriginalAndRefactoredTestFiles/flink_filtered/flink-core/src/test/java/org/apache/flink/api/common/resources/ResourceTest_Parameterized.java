package org.apache.flink.api.common.resources;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatComparable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("rawtypes")
class ResourceTest_Parameterized {

    private static void assertTestResourceValueEquals(final double value, final Resource resource) {
        assertThat(resource).isEqualTo(new TestResource(value));
    }

    @Test
    void testConstructorValid_2() {
        final Resource v2 = new TestResource(BigDecimal.valueOf(0.1));
        assertTestResourceValueEquals(0.1, v2);
    }

    @Test
    void testIsZero_1() {
        final Resource resource1 = new TestResource(0.0);
        assertThat(resource1.isZero()).isTrue();
    }

    @Test
    void testIsZero_2() {
        final Resource resource2 = new TestResource(1.0);
        assertThat(resource2.isZero()).isFalse();
    }

    @Test
    void testValueScaleLimited_2() {
        final Resource v2 = new TestResource(1.0).divide(3);
        assertTestResourceValueEquals(0.33333333, v2);
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructorValid_1_1")
    void testConstructorValid_1_1(double param1, double param2) {
        final Resource v1 = new TestResource(param2);
        assertTestResourceValueEquals(param1, v1);
    }

    static public Stream<Arguments> Provider_testConstructorValid_1_1() {
        return Stream.of(arguments(0.1, 0.1), arguments(0.100000001, 0.1));
    }
}
