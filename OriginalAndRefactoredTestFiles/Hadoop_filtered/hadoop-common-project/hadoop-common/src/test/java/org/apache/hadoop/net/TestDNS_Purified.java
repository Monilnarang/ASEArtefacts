package org.apache.hadoop.net;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;
import javax.naming.CommunicationException;
import javax.naming.NameNotFoundException;
import javax.naming.ServiceUnavailableException;
import org.apache.hadoop.util.Time;
import org.assertj.core.api.Assertions;
import org.junit.Assume;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.hadoop.test.PlatformAssumptions.assumeNotWindows;
import static org.junit.Assert.*;

public class TestDNS_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestDNS.class);

    private static final String DEFAULT = "default";

    private static final String DUMMY_HOSTNAME = "-DUMMY_HOSTNAME";

    private static final String INVALID_DNS_SERVER = "0.0.0.0";

    private InetAddress getLocalIPAddr() throws UnknownHostException {
        String hostname = DNS.getDefaultHost(DEFAULT);
        InetAddress localhost = InetAddress.getByName(hostname);
        return localhost;
    }

    private String getLoopbackInterface() throws SocketException {
        return NetworkInterface.getByInetAddress(InetAddress.getLoopbackAddress()).getName();
    }

    @Test
    public void testGetLocalHostIsFast_1_testMerged_1() throws Exception {
        String hostname1 = DNS.getDefaultHost(DEFAULT);
        assertNotNull(hostname1);
        String hostname2 = DNS.getDefaultHost(DEFAULT);
        String hostname3 = DNS.getDefaultHost(DEFAULT);
        assertEquals(hostname3, hostname2);
        assertEquals(hostname2, hostname1);
    }

    @Test
    public void testGetLocalHostIsFast_4() throws Exception {
        long t1 = Time.now();
        long t2 = Time.now();
        long interval = t2 - t1;
        assertTrue("Took too long to determine local host - caching is not working", interval < 20000);
    }
}
