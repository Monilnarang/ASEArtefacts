package org.apache.dubbo.common.profiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProfilerTest_Purified {

    @Test
    void testBizProfiler_1_testMerged_1() {
        Assertions.assertNull(Profiler.getBizProfiler());
    }

    @Test
    void testBizProfiler_2_testMerged_2() {
        ProfilerEntry one = Profiler.start("1");
        Profiler.setToBizProfiler(one);
        Profiler.release(Profiler.enter(Profiler.getBizProfiler(), "1-2"));
        Assertions.assertEquals(one, Profiler.getBizProfiler());
        Assertions.assertEquals(1, one.getSub().size());
    }
}
