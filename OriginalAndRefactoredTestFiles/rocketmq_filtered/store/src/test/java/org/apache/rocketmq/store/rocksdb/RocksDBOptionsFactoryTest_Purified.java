package org.apache.rocketmq.store.rocksdb;

import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.junit.Assert;
import org.junit.Test;
import org.rocksdb.CompressionType;

public class RocksDBOptionsFactoryTest_Purified {

    @Test
    public void testBottomMostCompressionType_1() {
        MessageStoreConfig config = new MessageStoreConfig();
        Assert.assertEquals(CompressionType.ZSTD_COMPRESSION, CompressionType.getCompressionType(config.getBottomMostCompressionTypeForConsumeQueueStore()));
    }

    @Test
    public void testBottomMostCompressionType_2() {
        Assert.assertEquals(CompressionType.LZ4_COMPRESSION, CompressionType.getCompressionType("lz4"));
    }
}
