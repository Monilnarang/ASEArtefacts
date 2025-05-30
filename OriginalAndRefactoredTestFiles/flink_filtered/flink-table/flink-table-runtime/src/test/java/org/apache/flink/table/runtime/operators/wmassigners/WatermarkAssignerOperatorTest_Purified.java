package org.apache.flink.table.runtime.operators.wmassigners;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.metrics.groups.TaskIOMetricGroup;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.runtime.watermarkstatus.WatermarkStatus;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.runtime.generated.GeneratedWatermarkGenerator;
import org.apache.flink.table.runtime.generated.WatermarkGenerator;
import org.junit.jupiter.api.Test;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.apache.flink.table.runtime.operators.wmassigners.WatermarkAssignerOperator.calculateProcessingTimeTimerInterval;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class WatermarkAssignerOperatorTest_Purified extends WatermarkAssignerOperatorTestBase {

    private static final WatermarkGenerator WATERMARK_GENERATOR = new BoundedOutOfOrderWatermarkGenerator(0, 1);

    private void stepProcessingTime(OneInputStreamOperatorTestHarness<?, ?> testHarness, long fromInclusive, long toInclusive, long step) throws Exception {
        for (long time = fromInclusive; time < toInclusive; time += step) {
            testHarness.setProcessingTime(time);
        }
        testHarness.setProcessingTime(toInclusive);
    }

    private static OneInputStreamOperatorTestHarness<RowData, RowData> createTestHarness(int rowtimeFieldIndex, WatermarkGenerator watermarkGenerator, long idleTimeout) throws Exception {
        return new OneInputStreamOperatorTestHarness<>(new WatermarkAssignerOperatorFactory(rowtimeFieldIndex, idleTimeout, new GeneratedWatermarkGenerator(watermarkGenerator.getClass().getName(), "", new Object[] {}) {

            @Override
            public WatermarkGenerator newInstance(ClassLoader classLoader) {
                return watermarkGenerator;
            }

            public WatermarkGenerator newInstance(ClassLoader classLoader, Object... args) {
                return watermarkGenerator;
            }
        }));
    }

    private static final class MyWatermarkGenerator extends WatermarkGenerator {

        private static final long serialVersionUID = 1L;

        private static boolean openCalled = false;

        private static boolean closeCalled = false;

        private final int watermarkFieldIndex;

        private MyWatermarkGenerator(int watermarkFieldIndex) {
            this.watermarkFieldIndex = watermarkFieldIndex;
        }

        @Override
        public void open(OpenContext openContext) throws Exception {
            super.open(openContext);
            if (closeCalled) {
                fail("Close called before open.");
            }
            openCalled = true;
        }

        @Nullable
        @Override
        public Long currentWatermark(RowData row) throws Exception {
            if (!openCalled) {
                fail("Open was not called before run.");
            }
            if (row.isNullAt(watermarkFieldIndex)) {
                return null;
            } else {
                return row.getLong(watermarkFieldIndex);
            }
        }

        @Override
        public void close() throws Exception {
            super.close();
            if (!openCalled) {
                fail("Open was not called before close.");
            }
            closeCalled = true;
        }
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_1() {
        assertThat(calculateProcessingTimeTimerInterval(5, 0)).isEqualTo(5);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_2() {
        assertThat(calculateProcessingTimeTimerInterval(5, -1)).isEqualTo(5);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_3() {
        assertThat(calculateProcessingTimeTimerInterval(0, 5)).isEqualTo(5);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_4() {
        assertThat(calculateProcessingTimeTimerInterval(-1, 5)).isEqualTo(5);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_5() {
        assertThat(calculateProcessingTimeTimerInterval(5, 42)).isEqualTo(5);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_6() {
        assertThat(calculateProcessingTimeTimerInterval(42, 5)).isEqualTo(5);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_7() {
        assertThat(calculateProcessingTimeTimerInterval(2, 4)).isEqualTo(1);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_8() {
        assertThat(calculateProcessingTimeTimerInterval(4, 2)).isEqualTo(1);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_9() {
        assertThat(calculateProcessingTimeTimerInterval(100, 110)).isEqualTo(20);
    }

    @Test
    void testCalculateProcessingTimeTimerInterval_10() {
        assertThat(calculateProcessingTimeTimerInterval(110, 100)).isEqualTo(20);
    }
}
