package org.apache.seata.common.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NetAddressValidatorUtilTest_Purified {

    @Test
    public void isIPv6Address_1() {
        assertThat(NetAddressValidatorUtil.isIPv6Address("2000:0000:0000:0000:0001:2345:6789:abcd")).isTrue();
    }

    @Test
    public void isIPv6Address_2() {
        assertThat(NetAddressValidatorUtil.isIPv6Address("2001:DB8:0:0:8:800:200C:417A")).isTrue();
    }

    @Test
    public void isIPv6Address_3() {
        assertThat(NetAddressValidatorUtil.isIPv6Address("2001:DB8::8:800:200C:417A")).isTrue();
    }

    @Test
    public void isIPv6Address_4() {
        assertThat(NetAddressValidatorUtil.isIPv6Address("2001:DB8::8:800:200C141aA")).isFalse();
    }

    @Test
    public void isIPv6Address_5() {
        assertThat(NetAddressValidatorUtil.isIPv6Address("::")).isTrue();
    }

    @Test
    public void isIPv6MixedAddress_1() {
        assertThat(NetAddressValidatorUtil.isIPv6MixedAddress("1:0:0:0:0:0:172.12.55.18")).isTrue();
    }

    @Test
    public void isIPv6MixedAddress_2() {
        assertThat(NetAddressValidatorUtil.isIPv6MixedAddress("2001:DB8::8:800:200C141aA")).isFalse();
    }

    @Test
    public void isIPv6IPv4MappedAddress_1() {
        assertThat(NetAddressValidatorUtil.isIPv6IPv4MappedAddress(":ffff:1.1.1.1")).isFalse();
    }

    @Test
    public void isIPv6IPv4MappedAddress_2() {
        assertThat(NetAddressValidatorUtil.isIPv6IPv4MappedAddress("::FFFF:192.168.1.2")).isTrue();
    }

    @Test
    public void isIPv4Address_1() {
        assertThat(NetAddressValidatorUtil.isIPv4Address("192.168.1.2")).isTrue();
    }

    @Test
    public void isIPv4Address_2() {
        assertThat(NetAddressValidatorUtil.isIPv4Address("127.0.0.1")).isTrue();
    }

    @Test
    public void isIPv4Address_3() {
        assertThat(NetAddressValidatorUtil.isIPv4Address("999.999.999.999")).isFalse();
    }

    @Test
    public void isLinkLocalIPv6WithZoneIndex_1() {
        assertThat(NetAddressValidatorUtil.isLinkLocalIPv6WithZoneIndex("2409:8a5c:6730:4490:f0e8:b9ad:3b3d:e739%br0")).isTrue();
    }

    @Test
    public void isLinkLocalIPv6WithZoneIndex_2() {
        assertThat(NetAddressValidatorUtil.isLinkLocalIPv6WithZoneIndex("2409:8a5c:6730:4490:f0e8:b9ad:3b3d:e739%")).isFalse();
    }
}
