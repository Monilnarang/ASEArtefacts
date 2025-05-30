package org.apache.druid.server.coordination;

import org.junit.Assert;
import org.junit.Test;

public class ServerTypeTest_Purified {

    @Test
    public void testAssignable_1() {
        Assert.assertTrue(ServerType.HISTORICAL.isSegmentReplicationTarget());
    }

    @Test
    public void testAssignable_2() {
        Assert.assertTrue(ServerType.BRIDGE.isSegmentReplicationTarget());
    }

    @Test
    public void testAssignable_3() {
        Assert.assertFalse(ServerType.REALTIME.isSegmentReplicationTarget());
    }

    @Test
    public void testAssignable_4() {
        Assert.assertFalse(ServerType.INDEXER_EXECUTOR.isSegmentReplicationTarget());
    }

    @Test
    public void testFromString_1() {
        Assert.assertEquals(ServerType.HISTORICAL, ServerType.fromString("historical"));
    }

    @Test
    public void testFromString_2() {
        Assert.assertEquals(ServerType.BRIDGE, ServerType.fromString("bridge"));
    }

    @Test
    public void testFromString_3() {
        Assert.assertEquals(ServerType.REALTIME, ServerType.fromString("realtime"));
    }

    @Test
    public void testFromString_4() {
        Assert.assertEquals(ServerType.INDEXER_EXECUTOR, ServerType.fromString("indexer-executor"));
    }

    @Test
    public void testToString_1() {
        Assert.assertEquals(ServerType.HISTORICAL.toString(), "historical");
    }

    @Test
    public void testToString_2() {
        Assert.assertEquals(ServerType.BRIDGE.toString(), "bridge");
    }

    @Test
    public void testToString_3() {
        Assert.assertEquals(ServerType.REALTIME.toString(), "realtime");
    }

    @Test
    public void testToString_4() {
        Assert.assertEquals(ServerType.INDEXER_EXECUTOR.toString(), "indexer-executor");
    }
}
