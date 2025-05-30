package org.apache.flink.streaming.runtime.watermarkstatus;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WatermarkStatusTest_Purified {

    @Test
    void testEquals_1_testMerged_1() {
        WatermarkStatus idleStatus = new WatermarkStatus(WatermarkStatus.IDLE_STATUS);
        assertThat(idleStatus).isEqualTo(WatermarkStatus.IDLE);
        assertThat(idleStatus.isIdle()).isTrue();
        assertThat(idleStatus.isActive()).isFalse();
    }

    @Test
    void testEquals_4_testMerged_2() {
        WatermarkStatus activeStatus = new WatermarkStatus(WatermarkStatus.ACTIVE_STATUS);
        assertThat(activeStatus).isEqualTo(WatermarkStatus.ACTIVE);
        assertThat(activeStatus.isActive()).isTrue();
        assertThat(activeStatus.isIdle()).isFalse();
    }
}
