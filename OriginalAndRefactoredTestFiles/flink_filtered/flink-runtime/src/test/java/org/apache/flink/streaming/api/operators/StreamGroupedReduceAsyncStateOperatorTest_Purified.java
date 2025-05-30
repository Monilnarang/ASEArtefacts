package org.apache.flink.streaming.api.operators;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.TestHarnessUtil;
import org.apache.flink.streaming.util.asyncprocessing.AsyncKeyedOneInputStreamOperatorTestHarness;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.assertj.core.api.Assertions.assertThat;

class StreamGroupedReduceAsyncStateOperatorTest_Purified {

    private static class TestOpenCloseReduceFunction extends RichReduceFunction<Integer> {

        private static final long serialVersionUID = 1L;

        public static boolean openCalled = false;

        public static boolean closeCalled = false;

        @Override
        public void open(OpenContext openContext) throws Exception {
            super.open(openContext);
            assertThat(closeCalled).as("Close called before open.").isFalse();
            openCalled = true;
        }

        @Override
        public void close() throws Exception {
            super.close();
            assertThat(openCalled).as("Open was not called before close.").isTrue();
            closeCalled = true;
        }

        @Override
        public Integer reduce(Integer in1, Integer in2) throws Exception {
            assertThat(openCalled).as("Open was not called before run.").isTrue();
            return in1 + in2;
        }
    }

    private static class MyReducer implements ReduceFunction<Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer reduce(Integer value1, Integer value2) throws Exception {
            return value1 + value2;
        }
    }

    private static class IntegerKeySelector implements KeySelector<Integer, Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer getKey(Integer value) throws Exception {
            return value;
        }
    }

    private static TypeInformation<Integer> typeInfo = BasicTypeInfo.INT_TYPE_INFO;

    @Test
    void testOpenClose_1() throws Exception {
    }

    @Test
    void testOpenClose_2() throws Exception {
        KeySelector<Integer, Integer> keySelector = new IntegerKeySelector();
        StreamGroupedReduceAsyncStateOperator<Integer> operator = new StreamGroupedReduceAsyncStateOperator<>(new TestOpenCloseReduceFunction(), IntSerializer.INSTANCE);
        AsyncKeyedOneInputStreamOperatorTestHarness<Integer, Integer, Integer> testHarness = AsyncKeyedOneInputStreamOperatorTestHarness.create(operator, keySelector, BasicTypeInfo.INT_TYPE_INFO);
        long initialTime = 0L;
        testHarness.open();
        testHarness.processElement(new StreamRecord<>(1, initialTime));
        testHarness.processElement(new StreamRecord<>(2, initialTime));
        testHarness.close();
    }
}
