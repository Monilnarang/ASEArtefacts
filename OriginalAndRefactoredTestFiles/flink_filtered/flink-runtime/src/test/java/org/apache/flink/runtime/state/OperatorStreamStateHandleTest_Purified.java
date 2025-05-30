package org.apache.flink.runtime.state;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OperatorStreamStateHandleTest_Purified {

    @Test
    void testFixedEnumOrder_1() {
        assertThat(OperatorStateHandle.Mode.SPLIT_DISTRIBUTE.ordinal()).isZero();
    }

    @Test
    void testFixedEnumOrder_2() {
        assertThat(OperatorStateHandle.Mode.UNION.ordinal()).isOne();
    }

    @Test
    void testFixedEnumOrder_3() {
        assertThat(OperatorStateHandle.Mode.BROADCAST.ordinal()).isEqualTo(2);
    }

    @Test
    void testFixedEnumOrder_4() {
        assertThat(OperatorStateHandle.Mode.values()).hasSize(3);
    }

    @Test
    void testFixedEnumOrder_5() {
        assertThat(OperatorStateHandle.Mode.values()).hasSizeLessThanOrEqualTo(Byte.MAX_VALUE);
    }
}
