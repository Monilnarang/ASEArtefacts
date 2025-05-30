package org.apache.seata.common.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NetAddressValidatorUtilTest_Parameterized {

    @Test
    public void isIPv6Address_4() {
        assertThat(NetAddressValidatorUtil.isIPv6Address("2001:DB8::8:800:200C141aA")).isFalse();
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

    @ParameterizedTest
    @MethodSource("Provider_isIPv6Address_1to3_5")
    public void isIPv6Address_1to3_5(String param1) {
        assertThat(NetAddressValidatorUtil.isIPv6Address(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_isIPv6Address_1to3_5() {
        return Stream.of(arguments("2000:0000:0000:0000:0001:2345:6789:abcd"), arguments("2001:DB8:0:0:8:800:200C:417A"), arguments("2001:DB8::8:800:200C:417A"), arguments("::"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isIPv4Address_1to2")
    public void isIPv4Address_1to2(String param1) {
        assertThat(NetAddressValidatorUtil.isIPv4Address(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_isIPv4Address_1to2() {
        return Stream.of(arguments("192.168.1.2"), arguments("127.0.0.1"));
    }
}
