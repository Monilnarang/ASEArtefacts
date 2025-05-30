package org.apache.druid.curator;

import org.apache.curator.framework.state.ConnectionState;
import org.apache.druid.java.util.emitter.service.AlertEvent;
import org.apache.druid.java.util.metrics.StubServiceEmitter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DruidConnectionStateListenerTest_Purified {

    private StubServiceEmitter emitter;

    private DruidConnectionStateListener listener;

    @Before
    public void setUp() {
        emitter = new StubServiceEmitter("DruidConnectionStateListenerTest", "localhost");
        listener = new DruidConnectionStateListener(emitter);
    }

    @Test
    public void test_isConnected_1_testMerged_1() {
        Assert.assertFalse(listener.isConnected());
    }

    @Test
    public void test_isConnected_2_testMerged_2() {
        listener.stateChanged(null, ConnectionState.CONNECTED);
        Assert.assertTrue(listener.isConnected());
    }

    @Test
    public void test_suspendedAlert_1() {
        listener.stateChanged(null, ConnectionState.SUSPENDED);
        Assert.assertEquals(1, emitter.getEvents().size());
    }

    @Test
    public void test_suspendedAlert_2_testMerged_2() {
        final AlertEvent alert = emitter.getAlerts().get(0);
        Assert.assertEquals("alerts", alert.getFeed());
        Assert.assertEquals("ZooKeeper connection[SUSPENDED]", alert.getDescription());
    }

    @Test
    public void test_reconnectedMetric_1_testMerged_1() {
        listener.stateChanged(null, ConnectionState.SUSPENDED);
        Assert.assertEquals(1, emitter.getEvents().size());
        listener.stateChanged(null, ConnectionState.RECONNECTED);
        Assert.assertEquals(2, emitter.getEvents().size());
    }

    @Test
    public void test_reconnectedMetric_3() {
        long observedReconnectTime = emitter.getValue("zk/reconnect/time", null).longValue();
        Assert.assertTrue(observedReconnectTime >= 0);
    }
}
