package org.graylog2.lookup.adapters.dnslookup;

import com.google.common.net.InternetDomainName;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DnsClientTest_Purified {

    private PtrDnsAnswer buildReverseLookupDomainTest(String hostname) {
        PtrDnsAnswer.Builder builder = PtrDnsAnswer.builder();
        builder.dnsTTL(1L);
        DnsClient.parseReverseLookupDomain(builder, hostname);
        return builder.build();
    }

    @Test
    public void testValidIp4Address_1() {
        assertTrue(DnsClient.isIp4Address("8.8.8.8"));
    }

    @Test
    public void testValidIp4Address_2() {
        assertTrue(DnsClient.isIp4Address("127.0.0.1"));
    }

    @Test
    public void testValidIp4Address_3() {
        assertFalse(DnsClient.isIp4Address("t127.0.0.1"));
    }

    @Test
    public void testValidIp4Address_4() {
        assertFalse(DnsClient.isIp4Address("google.com"));
    }

    @Test
    public void testValidIp6Address_1() {
        assertTrue(DnsClient.isIp6Address("2607:f8b0:4000:812::200e"));
    }

    @Test
    public void testValidIp6Address_2() {
        assertTrue(DnsClient.isIp6Address("2606:2800:220:1:248:1893:25c8:1946"));
    }

    @Test
    public void testValidIp6Address_3() {
        assertFalse(DnsClient.isIp6Address("t2606:2800:220:1:248:1893:25c8:1946"));
    }

    @Test
    public void testValidIp6Address_4() {
        assertFalse(DnsClient.isIp6Address("google.com"));
    }

    @Test
    public void testValidCommaSeparatedIps_1() {
        assertTrue(DnsClient.allIpAddressesValid("8.8.4.4:53, 8.8.8.8"));
    }

    @Test
    public void testValidCommaSeparatedIps_2() {
        assertTrue(DnsClient.allIpAddressesValid("8.8.4.4, "));
    }

    @Test
    public void testValidCommaSeparatedIps_3() {
        assertTrue(DnsClient.allIpAddressesValid("8.8.4.4"));
    }

    @Test
    public void testValidCommaSeparatedIps_4() {
        assertFalse(DnsClient.allIpAddressesValid("8.8.4.4dfs:53, 8.8.4.4:59"));
    }

    @Test
    public void testValidCommaSeparatedIps_5() {
        assertFalse(DnsClient.allIpAddressesValid("8.8.4.4 8.8.8.8"));
    }

    @Test
    public void testValidCommaSeparatedIps_6() {
        assertFalse(DnsClient.allIpAddressesValid("8.8.4.4, google.com"));
    }

    @Test
    public void testValidHostname_1() {
        assertTrue(DnsClient.isHostName("google.com"));
    }

    @Test
    public void testValidHostname_2() {
        assertTrue(DnsClient.isHostName("api.graylog.com"));
    }

    @Test
    public void testValidHostname_3() {
        assertFalse(DnsClient.isHostName("http://api.graylog.com"));
    }

    @Test
    public void testValidHostname_4() {
        assertFalse(DnsClient.isHostName("api.graylog.com/10"));
    }

    @Test
    public void testValidHostname_5() {
        assertFalse(DnsClient.isHostName("api.graylog.com?name=dano"));
    }
}
