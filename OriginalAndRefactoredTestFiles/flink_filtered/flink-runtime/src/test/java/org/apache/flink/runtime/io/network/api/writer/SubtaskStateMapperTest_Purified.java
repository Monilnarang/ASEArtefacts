package org.apache.flink.runtime.io.network.api.writer;

import org.junit.jupiter.api.Test;
import static org.apache.flink.runtime.checkpoint.InflightDataRescalingDescriptorUtil.mappings;
import static org.apache.flink.runtime.checkpoint.InflightDataRescalingDescriptorUtil.to;
import static org.assertj.core.api.Assertions.assertThat;

class SubtaskStateMapperTest_Purified {

    @Test
    void testRangeSelectorTaskMappingOnScaleDown_1() {
        assertThat(SubtaskStateMapper.RANGE.getNewToOldSubtasksMapping(3, 2)).isEqualTo(mappings(to(0, 1), to(1, 2)));
    }

    @Test
    void testRangeSelectorTaskMappingOnScaleDown_2() {
        assertThat(SubtaskStateMapper.RANGE.getNewToOldSubtasksMapping(10, 2)).isEqualTo(mappings(to(0, 1, 2, 3, 4), to(5, 6, 7, 8, 9)));
    }

    @Test
    void testRangeSelectorTaskMappingOnScaleDown_3() {
        assertThat(SubtaskStateMapper.RANGE.getNewToOldSubtasksMapping(10, 1)).isEqualTo(mappings(to(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }

    @Test
    void testRangeSelectorTaskMappingOnScaleUp_1() {
        assertThat(SubtaskStateMapper.RANGE.getNewToOldSubtasksMapping(3, 4)).isEqualTo(mappings(to(0), to(0, 1), to(1, 2), to(2)));
    }

    @Test
    void testRangeSelectorTaskMappingOnScaleUp_2() {
        assertThat(SubtaskStateMapper.RANGE.getNewToOldSubtasksMapping(3, 7)).isEqualTo(mappings(to(0), to(0), to(0, 1), to(1), to(1, 2), to(2), to(2)));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleDown_1() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(10, 4)).isEqualTo(mappings(to(0, 4, 8), to(1, 5, 9), to(2, 6), to(3, 7)));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleDown_2() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(5, 4)).isEqualTo(mappings(to(0, 4), to(1), to(2), to(3)));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleDown_3() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(5, 2)).isEqualTo(mappings(to(0, 2, 4), to(1, 3)));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleDown_4() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(5, 1)).isEqualTo(mappings(to(0, 1, 2, 3, 4)));
    }

    @Test
    void testRoundRobinTaskMappingOnNoScale_1() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(10, 10)).isEqualTo(mappings(to(0), to(1), to(2), to(3), to(4), to(5), to(6), to(7), to(8), to(9)));
    }

    @Test
    void testRoundRobinTaskMappingOnNoScale_2() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(5, 5)).isEqualTo(mappings(to(0), to(1), to(2), to(3), to(4)));
    }

    @Test
    void testRoundRobinTaskMappingOnNoScale_3() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(1, 1)).isEqualTo(mappings(to(0)));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleUp_1() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(4, 10)).isEqualTo(mappings(to(0), to(1), to(2), to(3), to(), to(), to(), to(), to(), to()));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleUp_2() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(4, 5)).isEqualTo(mappings(to(0), to(1), to(2), to(3), to()));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleUp_3() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(2, 5)).isEqualTo(mappings(to(0), to(1), to(), to(), to()));
    }

    @Test
    void testRoundRobinTaskMappingOnScaleUp_4() {
        assertThat(SubtaskStateMapper.ROUND_ROBIN.getNewToOldSubtasksMapping(1, 5)).isEqualTo(mappings(to(0), to(), to(), to(), to()));
    }
}
