package org.apache.storm.trident;

import java.util.concurrent.TimeUnit;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.trident.windowing.InMemoryWindowsStore;
import org.apache.storm.trident.windowing.config.SlidingCountWindow;
import org.apache.storm.trident.windowing.config.SlidingDurationWindow;
import org.apache.storm.trident.windowing.config.TumblingCountWindow;
import org.apache.storm.trident.windowing.config.TumblingDurationWindow;
import org.apache.storm.trident.windowing.strategy.SlidingCountWindowStrategy;
import org.apache.storm.trident.windowing.strategy.SlidingDurationWindowStrategy;
import org.apache.storm.trident.windowing.strategy.TumblingCountWindowStrategy;
import org.apache.storm.trident.windowing.strategy.TumblingDurationWindowStrategy;
import org.apache.storm.trident.windowing.strategy.WindowStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TridentWindowingTest_Purified {

    @Test
    public void testWindowStrategyInstances_1() {
        WindowStrategy<Object> tumblingCountStrategy = TumblingCountWindow.of(10).getWindowStrategy();
        assertTrue(tumblingCountStrategy instanceof TumblingCountWindowStrategy);
    }

    @Test
    public void testWindowStrategyInstances_2() {
        WindowStrategy<Object> slidingCountStrategy = SlidingCountWindow.of(100, 10).getWindowStrategy();
        assertTrue(slidingCountStrategy instanceof SlidingCountWindowStrategy);
    }

    @Test
    public void testWindowStrategyInstances_3_testMerged_3() {
        WindowStrategy<Object> tumblingDurationStrategy = TumblingDurationWindow.of(new BaseWindowedBolt.Duration(10, TimeUnit.SECONDS)).getWindowStrategy();
        assertTrue(tumblingDurationStrategy instanceof TumblingDurationWindowStrategy);
        WindowStrategy<Object> slidingDurationStrategy = SlidingDurationWindow.of(new BaseWindowedBolt.Duration(10, TimeUnit.SECONDS), new BaseWindowedBolt.Duration(2, TimeUnit.SECONDS)).getWindowStrategy();
        assertTrue(slidingDurationStrategy instanceof SlidingDurationWindowStrategy);
    }
}
