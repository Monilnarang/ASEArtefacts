package org.apache.flink.table.planner.parse;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ResetOperationParseStrategyTest_Purified {

    @Test
    void testMatches_1() {
        assertThat(ResetOperationParseStrategy.INSTANCE.match("RESET")).isTrue();
    }

    @Test
    void testMatches_2() {
        assertThat(ResetOperationParseStrategy.INSTANCE.match("RESET table.planner")).isTrue();
    }

    @Test
    void testMatches_3() {
        assertThat(ResetOperationParseStrategy.INSTANCE.match("RESET;")).isTrue();
    }

    @Test
    void testMatches_4() {
        assertThat(ResetOperationParseStrategy.INSTANCE.match("RESET table.planner;")).isTrue();
    }
}
