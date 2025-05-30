package org.apache.druid.server.coordinator;

import com.google.common.collect.ImmutableMap;
import org.apache.druid.server.coordinator.stats.CoordinatorRunStats;
import org.apache.druid.server.coordinator.stats.CoordinatorStat;
import org.apache.druid.server.coordinator.stats.Dimension;
import org.apache.druid.server.coordinator.stats.RowKey;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class CoordinatorRunStatsTest_Purified {

    private CoordinatorRunStats stats;

    @Before
    public void setUp() {
        stats = new CoordinatorRunStats();
    }

    @After
    public void tearDown() {
        stats = null;
    }

    private static class Key {

        static final RowKey TIER_1 = RowKey.of(Dimension.TIER, "tier1");

        static final RowKey TIER_2 = RowKey.of(Dimension.TIER, "tier2");

        static final RowKey DUTY_1 = RowKey.of(Dimension.DUTY, "duty1");

        static final RowKey DUTY_2 = RowKey.of(Dimension.DUTY, "duty2");
    }

    private static class Stat {

        static final CoordinatorStat ERROR_1 = CoordinatorStat.toLogAndEmit("error1", "e1", CoordinatorStat.Level.ERROR);

        static final CoordinatorStat INFO_1 = CoordinatorStat.toLogAndEmit("info1", "i1", CoordinatorStat.Level.INFO);

        static final CoordinatorStat INFO_2 = CoordinatorStat.toLogAndEmit("info2", "i2", CoordinatorStat.Level.INFO);

        static final CoordinatorStat DEBUG_1 = CoordinatorStat.toDebugAndEmit("debug1", "d1");
    }

    @Test
    public void testAdd_1() {
        Assert.assertEquals(0, stats.get(Stat.ERROR_1));
    }

    @Test
    public void testAdd_2_testMerged_2() {
        stats.add(Stat.ERROR_1, 1);
        Assert.assertEquals(1, stats.get(Stat.ERROR_1));
        stats.add(Stat.ERROR_1, -11);
        Assert.assertEquals(-10, stats.get(Stat.ERROR_1));
    }
}
