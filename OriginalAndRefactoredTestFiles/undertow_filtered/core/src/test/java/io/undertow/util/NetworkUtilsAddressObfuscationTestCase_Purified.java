package io.undertow.util;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtilsAddressObfuscationTestCase_Purified {

    private static String cvt(String input) throws UnknownHostException {
        return NetworkUtils.toObfuscatedString(InetAddress.getByName(input));
    }

    @Test
    public void testIpV4Address_1() throws IOException {
        Assert.assertEquals("1.123.255.", cvt("1.123.255.2"));
    }

    @Test
    public void testIpV4Address_2() throws IOException {
        Assert.assertEquals("127.0.0.", cvt("127.0.0.1"));
    }

    @Test
    public void testIpV4Address_3() throws IOException {
        Assert.assertEquals("0.0.0.", cvt("0.0.0.0"));
    }

    @Test
    public void testIpv6Address_1() throws IOException {
        Assert.assertEquals("2001:1db8:", cvt("2001:1db8:100:3:6:ff00:42:8329"));
    }

    @Test
    public void testIpv6Address_2() throws IOException {
        Assert.assertEquals("2001:1db8:", cvt("2001:1db8:100::6:ff00:42:8329"));
    }

    @Test
    public void testIpv6Address_3() throws IOException {
        Assert.assertEquals("0:0:", cvt("::1"));
    }
}
