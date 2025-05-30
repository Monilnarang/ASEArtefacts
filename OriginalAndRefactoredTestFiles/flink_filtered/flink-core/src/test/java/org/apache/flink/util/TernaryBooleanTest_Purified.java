package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import static org.apache.flink.util.TernaryBoolean.FALSE;
import static org.apache.flink.util.TernaryBoolean.TRUE;
import static org.apache.flink.util.TernaryBoolean.UNDEFINED;
import static org.assertj.core.api.Assertions.assertThat;

class TernaryBooleanTest_Purified {

    @Test
    void testWithDefault_1() {
        assertThat(TRUE.getOrDefault(true)).isTrue();
    }

    @Test
    void testWithDefault_2() {
        assertThat(TRUE.getOrDefault(false)).isTrue();
    }

    @Test
    void testWithDefault_3() {
        assertThat(FALSE.getOrDefault(true)).isFalse();
    }

    @Test
    void testWithDefault_4() {
        assertThat(FALSE.getOrDefault(false)).isFalse();
    }

    @Test
    void testWithDefault_5() {
        assertThat(UNDEFINED.getOrDefault(true)).isTrue();
    }

    @Test
    void testWithDefault_6() {
        assertThat(UNDEFINED.getOrDefault(false)).isFalse();
    }

    @Test
    void testResolveUndefined_1() {
        assertThat(TRUE.resolveUndefined(true)).isEqualTo(TRUE);
    }

    @Test
    void testResolveUndefined_2() {
        assertThat(TRUE.resolveUndefined(false)).isEqualTo(TRUE);
    }

    @Test
    void testResolveUndefined_3() {
        assertThat(FALSE.resolveUndefined(true)).isEqualTo(FALSE);
    }

    @Test
    void testResolveUndefined_4() {
        assertThat(FALSE.resolveUndefined(false)).isEqualTo(FALSE);
    }

    @Test
    void testResolveUndefined_5() {
        assertThat(UNDEFINED.resolveUndefined(true)).isEqualTo(TRUE);
    }

    @Test
    void testResolveUndefined_6() {
        assertThat(UNDEFINED.resolveUndefined(false)).isEqualTo(FALSE);
    }

    @Test
    void testToBoolean_1() {
        assertThat(TRUE.getAsBoolean()).isSameAs(Boolean.TRUE);
    }

    @Test
    void testToBoolean_2() {
        assertThat(FALSE.getAsBoolean()).isSameAs(Boolean.FALSE);
    }

    @Test
    void testToBoolean_3() {
        assertThat(UNDEFINED.getAsBoolean()).isNull();
    }

    @Test
    void testFromBoolean_1() {
        assertThat(TernaryBoolean.fromBoolean(true)).isEqualTo(TRUE);
    }

    @Test
    void testFromBoolean_2() {
        assertThat(TernaryBoolean.fromBoolean(false)).isEqualTo(FALSE);
    }

    @Test
    void testFromBoxedBoolean_1() {
        assertThat(TernaryBoolean.fromBoxedBoolean(Boolean.TRUE)).isEqualTo(TRUE);
    }

    @Test
    void testFromBoxedBoolean_2() {
        assertThat(TernaryBoolean.fromBoxedBoolean(Boolean.FALSE)).isEqualTo(FALSE);
    }

    @Test
    void testFromBoxedBoolean_3() {
        assertThat(TernaryBoolean.fromBoxedBoolean(null)).isEqualTo(UNDEFINED);
    }
}
