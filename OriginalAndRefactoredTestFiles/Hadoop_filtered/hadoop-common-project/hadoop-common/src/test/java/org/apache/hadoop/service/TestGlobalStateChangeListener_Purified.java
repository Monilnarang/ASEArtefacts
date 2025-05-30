package org.apache.hadoop.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.service.AbstractService;
import org.apache.hadoop.service.LoggingStateChangeListener;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.service.ServiceStateChangeListener;
import org.junit.After;
import org.junit.Test;

public class TestGlobalStateChangeListener_Purified extends ServiceAssert {

    BreakableStateChangeListener listener = new BreakableStateChangeListener("listener");

    private void register() {
        register(listener);
    }

    private boolean unregister() {
        return unregister(listener);
    }

    private void register(ServiceStateChangeListener l) {
        AbstractService.registerGlobalListener(l);
    }

    private boolean unregister(ServiceStateChangeListener l) {
        return AbstractService.unregisterGlobalListener(l);
    }

    @After
    public void cleanup() {
        AbstractService.resetGlobalListeners();
    }

    public void assertListenerState(BreakableStateChangeListener breakable, Service.STATE state) {
        assertEquals("Wrong state in " + breakable, state, breakable.getLastState());
    }

    public void assertListenerEventCount(BreakableStateChangeListener breakable, int count) {
        assertEquals("Wrong event count in " + breakable, count, breakable.getEventCount());
    }

    @Test
    public void testRegisterListenerTwice_1() {
        assertTrue("listener not registered", unregister());
    }

    @Test
    public void testRegisterListenerTwice_2() {
        assertFalse("listener double registered", unregister());
    }

    @Test
    public void testEventHistory_1() {
        assertListenerState(listener, Service.STATE.NOTINITED);
    }

    @Test
    public void testEventHistory_2() {
        assertEquals(0, listener.getEventCount());
    }

    @Test
    public void testEventHistory_3() {
        assertListenerState(listener, Service.STATE.INITED);
    }

    @Test
    public void testEventHistory_4() {
        BreakableService service = new BreakableService();
        service.init(new Configuration());
        assertSame(service, listener.getLastService());
    }

    @Test
    public void testEventHistory_5() {
        assertListenerEventCount(listener, 1);
    }

    @Test
    public void testEventHistory_6() {
        assertListenerState(listener, Service.STATE.STARTED);
    }

    @Test
    public void testEventHistory_7() {
        assertListenerEventCount(listener, 2);
    }

    @Test
    public void testEventHistory_8() {
        assertListenerState(listener, Service.STATE.STOPPED);
    }

    @Test
    public void testEventHistory_9() {
        assertListenerEventCount(listener, 3);
    }
}
