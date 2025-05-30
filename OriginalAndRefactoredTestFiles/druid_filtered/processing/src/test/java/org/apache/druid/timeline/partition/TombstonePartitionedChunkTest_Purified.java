package org.apache.druid.timeline.partition;

import org.junit.Assert;
import org.junit.Test;

public class TombstonePartitionedChunkTest_Purified {

    final TombstoneShardSpec tombstoneShardSpec = new TombstoneShardSpec();

    final TombstonePartitionedChunk tombstonePartitionedChunk = TombstonePartitionedChunk.make(tombstoneShardSpec);

    @Test
    public void equalsTest_1() {
        TombstonePartitionedChunk aCopy = tombstonePartitionedChunk;
        Assert.assertTrue(tombstonePartitionedChunk.equals(aCopy));
    }

    @Test
    public void equalsTest_2() {
        Assert.assertFalse(tombstonePartitionedChunk.equals(null));
    }

    @Test
    public void equalsTest_3() {
        Assert.assertFalse(tombstonePartitionedChunk.equals(new Object()));
    }

    @Test
    public void equalsTest_4() {
        Assert.assertTrue(tombstonePartitionedChunk.equals(TombstonePartitionedChunk.make(new Object())));
    }

    @Test
    public void minutia_1() {
        Assert.assertTrue(tombstonePartitionedChunk.hashCode() > 0);
    }

    @Test
    public void minutia_2() {
        Assert.assertNotNull(tombstonePartitionedChunk.toString());
    }
}
