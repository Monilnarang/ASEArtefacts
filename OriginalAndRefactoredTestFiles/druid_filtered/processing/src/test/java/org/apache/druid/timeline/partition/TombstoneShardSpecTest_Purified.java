package org.apache.druid.timeline.partition;

import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class TombstoneShardSpecTest_Purified {

    final TombstoneShardSpec tombstoneShardSpec = new TombstoneShardSpec();

    @Test
    public void equalsTest_1_testMerged_1() {
        TombstoneShardSpec tombstoneShardSpecOther = tombstoneShardSpec;
        Assert.assertTrue(tombstoneShardSpec.equals(tombstoneShardSpecOther));
        tombstoneShardSpecOther = null;
        Assert.assertFalse(tombstoneShardSpec.equals(tombstoneShardSpecOther));
    }

    @Test
    public void equalsTest_3() {
        TombstoneShardSpec newTombostoneShardSepc = new TombstoneShardSpec();
        Assert.assertTrue(tombstoneShardSpec.equals(newTombostoneShardSepc));
    }
}
