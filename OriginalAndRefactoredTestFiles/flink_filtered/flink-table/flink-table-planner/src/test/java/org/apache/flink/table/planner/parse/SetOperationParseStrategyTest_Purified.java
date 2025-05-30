package org.apache.flink.table.planner.parse;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SetOperationParseStrategyTest_Purified {

    @Test
    void testMatches_1() {
        assertThat(SetOperationParseStrategy.INSTANCE.match("SET")).isTrue();
    }

    @Test
    void testMatches_2() {
        assertThat(SetOperationParseStrategy.INSTANCE.match("SET table.local-time-zone = Europe/Berlin")).isTrue();
    }

    @Test
    void testMatches_3() {
        assertThat(SetOperationParseStrategy.INSTANCE.match("SET table.local-time-zone = 'Europe/Berlin'")).isTrue();
    }

    @Test
    void testMatches_4() {
        assertThat(SetOperationParseStrategy.INSTANCE.match("SET;")).isTrue();
    }

    @Test
    void testMatches_5() {
        assertThat(SetOperationParseStrategy.INSTANCE.match("SET table.local-time-zone = Europe/Berlin;")).isTrue();
    }

    @Test
    void testMatches_6() {
        assertThat(SetOperationParseStrategy.INSTANCE.match("SET table.local-time-zone = 'Europe/Berlin';")).isTrue();
    }
}
