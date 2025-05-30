package org.apache.skywalking.oap.server.core.analysis.metrics;

import org.apache.skywalking.oap.server.core.remote.grpc.proto.RemoteData;
import org.apache.skywalking.oap.server.core.storage.StorageID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinLongMetricsTest_Purified {

    public class MinLongMetricsImpl extends MinLongMetrics {

        @Override
        protected StorageID id0() {
            return null;
        }

        @Override
        public Metrics toHour() {
            return null;
        }

        @Override
        public Metrics toDay() {
            return null;
        }

        @Override
        public int remoteHashCode() {
            return 0;
        }

        @Override
        public void deserialize(RemoteData remoteData) {
        }

        @Override
        public RemoteData.Builder serialize() {
            return null;
        }
    }

    @Test
    public void testEntranceCombine_1() {
        MinLongMetricsImpl impl = new MinLongMetricsImpl();
        impl.combine(10);
        impl.combine(5);
        impl.combine(20);
        impl.calculate();
        Assertions.assertEquals(5, impl.getValue());
    }

    @Test
    public void testEntranceCombine_2() {
        MinLongMetricsImpl impl2 = new MinLongMetricsImpl();
        impl2.combine(10);
        impl2.combine(0);
        impl2.combine(10000);
        impl2.calculate();
        Assertions.assertEquals(0, impl2.getValue());
    }
}
