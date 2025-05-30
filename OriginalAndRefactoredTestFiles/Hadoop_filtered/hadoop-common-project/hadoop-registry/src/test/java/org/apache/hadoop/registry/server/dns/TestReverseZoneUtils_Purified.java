package org.apache.hadoop.registry.server.dns;

import java.net.UnknownHostException;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestReverseZoneUtils_Purified {

    private static final String NET = "172.17.4.0";

    private static final int RANGE = 256;

    private static final int INDEX = 0;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testVariousRangeAndIndexValues_1() throws Exception {
        assertEquals("172.17.9.0", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 256, 5));
    }

    @Test
    public void testVariousRangeAndIndexValues_2() throws Exception {
        assertEquals("172.17.4.128", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 128, 1));
    }

    @Test
    public void testVariousRangeAndIndexValues_3() throws Exception {
        assertEquals("172.18.0.0", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 256, 252));
    }

    @Test
    public void testVariousRangeAndIndexValues_4() throws Exception {
        assertEquals("172.17.12.0", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 1024, 2));
    }

    @Test
    public void testVariousRangeAndIndexValues_5() throws Exception {
        assertEquals("172.17.4.0", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 0, 1));
    }

    @Test
    public void testVariousRangeAndIndexValues_6() throws Exception {
        assertEquals("172.17.4.0", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 1, 0));
    }

    @Test
    public void testVariousRangeAndIndexValues_7() throws Exception {
        assertEquals("172.17.4.1", ReverseZoneUtils.getReverseZoneNetworkAddress(NET, 1, 1));
    }
}
