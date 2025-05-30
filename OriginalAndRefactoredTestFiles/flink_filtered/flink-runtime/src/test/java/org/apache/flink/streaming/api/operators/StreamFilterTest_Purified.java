package org.apache.flink.streaming.api.operators;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.apache.flink.streaming.util.TestHarnessUtil;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.assertj.core.api.Assertions.assertThat;

class StreamFilterTest_Purified {

    static class MyFilter implements FilterFunction<Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean filter(Integer value) throws Exception {
            return value % 2 == 0;
        }
    }

    private static class TestOpenCloseFilterFunction extends RichFilterFunction<String> {

        private static final long serialVersionUID = 1L;

        public static boolean openCalled = false;

        public static boolean closeCalled = false;

        @Override
        public void open(OpenContext openContext) throws Exception {
            super.open(openContext);
            assertThat(openCalled).as("Open was called before.").isFalse();
            openCalled = true;
        }

        @Override
        public void close() throws Exception {
            super.close();
            assertThat(openCalled).as("Open was not called before close.").isTrue();
            closeCalled = true;
        }

        @Override
        public boolean filter(String value) throws Exception {
            assertThat(openCalled).as("Open was not called before run.").isTrue();
            return value.startsWith("foo");
        }
    }

    @Test
    void testOpenClose_1() throws Exception {
    }

    @Test
    void testOpenClose_2() throws Exception {
        StreamFilter<String> operator = new StreamFilter<String>(new TestOpenCloseFilterFunction());
        OneInputStreamOperatorTestHarness<String, String> testHarness = new OneInputStreamOperatorTestHarness<String, String>(operator);
        long initialTime = 0L;
        testHarness.open();
        testHarness.processElement(new StreamRecord<String>("fooHello", initialTime));
        testHarness.processElement(new StreamRecord<String>("bar", initialTime));
        testHarness.close();
    }
}
