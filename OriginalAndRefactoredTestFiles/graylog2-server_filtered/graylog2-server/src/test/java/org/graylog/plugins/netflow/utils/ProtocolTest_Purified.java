package org.graylog.plugins.netflow.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProtocolTest_Purified {

    @Test
    public void testGetByNumber_1() throws Exception {
        assertEquals(Protocol.TCP, Protocol.getByNumber(6));
    }

    @Test
    public void testGetByNumber_2() throws Exception {
        assertEquals(Protocol.VRRP, Protocol.getByNumber(112));
    }
}
