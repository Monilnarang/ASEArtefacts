package org.apache.druid.sql.calcite.planner;

import org.apache.druid.query.JoinAlgorithm;
import org.junit.Assert;
import org.junit.Test;

public class DruidHintTest_Purified {

    @Test
    public void testFromString_1() {
        Assert.assertEquals(DruidHint.DruidJoinHint.fromString("sort_merge").id(), DruidHint.DruidJoinHint.SortMergeJoinHint.SORT_MERGE_JOIN);
    }

    @Test
    public void testFromString_2() {
        Assert.assertEquals(DruidHint.DruidJoinHint.fromString("broadcast").id(), DruidHint.DruidJoinHint.BroadcastJoinHint.BROADCAST_JOIN);
    }

    @Test
    public void testFromString_3() {
        Assert.assertNull(DruidHint.DruidJoinHint.fromString("hash"));
    }

    @Test
    public void testFromString_4() {
        Assert.assertEquals(DruidHint.DruidJoinHint.fromString("sort_merge").asJoinAlgorithm(), JoinAlgorithm.SORT_MERGE);
    }

    @Test
    public void testFromString_5() {
        Assert.assertEquals(DruidHint.DruidJoinHint.fromString("broadcast").asJoinAlgorithm(), JoinAlgorithm.BROADCAST);
    }
}
