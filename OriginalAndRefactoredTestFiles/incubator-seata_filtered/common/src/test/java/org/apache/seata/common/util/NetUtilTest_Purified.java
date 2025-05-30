package org.apache.seata.common.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NetUtilTest_Purified {

    private InetSocketAddress ipv4 = new InetSocketAddress(Inet4Address.getLocalHost().getHostName(), 3902);

    private InetSocketAddress ipv6 = new InetSocketAddress(Inet6Address.getLocalHost().getHostName(), 3904);

    public NetUtilTest() throws UnknownHostException {
    }

    @Test
    public void testToStringAddress1_1() {
        assertThat(NetUtil.toStringAddress((SocketAddress) ipv4)).isEqualTo(ipv4.getAddress().getHostAddress() + ":" + ipv4.getPort());
    }

    @Test
    public void testToStringAddress1_2() {
        assertThat(NetUtil.toStringAddress((SocketAddress) ipv6)).isEqualTo(ipv6.getAddress().getHostAddress() + ":" + ipv6.getPort());
    }

    @Test
    public void testToStringAddress2_1() {
        assertThat(NetUtil.toStringAddress(ipv4)).isEqualTo(ipv4.getAddress().getHostAddress() + ":" + ipv4.getPort());
    }

    @Test
    public void testToStringAddress2_2() {
        assertThat(NetUtil.toStringAddress(ipv6)).isEqualTo(ipv6.getAddress().getHostAddress() + ":" + ipv6.getPort());
    }

    @Test
    public void testToIpAddress_1() throws UnknownHostException {
        assertThat(NetUtil.toIpAddress(ipv4)).isEqualTo(ipv4.getAddress().getHostAddress());
    }

    @Test
    public void testToIpAddress_2() throws UnknownHostException {
        assertThat(NetUtil.toIpAddress(ipv6)).isEqualTo(ipv6.getAddress().getHostAddress());
    }
}
