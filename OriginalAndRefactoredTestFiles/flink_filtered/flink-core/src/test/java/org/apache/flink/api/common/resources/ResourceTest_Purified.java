package org.apache.flink.api.common.resources;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatComparable;

@SuppressWarnings("rawtypes")
class ResourceTest_Purified {

    private static void assertTestResourceValueEquals(final double value, final Resource resource) {
        assertThat(resource).isEqualTo(new TestResource(value));
    }

    @Test
    void testConstructorValid_1() {
        final Resource v1 = new TestResource(0.1);
        assertTestResourceValueEquals(0.1, v1);
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
    void testValueScaleLimited_1() {
        final Resource v1 = new TestResource(0.100000001);
        assertTestResourceValueEquals(0.1, v1);
    }

    @Test
    void testValueScaleLimited_2() {
        final Resource v2 = new TestResource(1.0).divide(3);
        assertTestResourceValueEquals(0.33333333, v2);
    }
}
