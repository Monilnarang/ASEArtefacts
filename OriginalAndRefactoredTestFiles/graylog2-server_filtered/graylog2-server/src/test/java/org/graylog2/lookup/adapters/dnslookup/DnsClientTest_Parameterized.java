package org.graylog2.lookup.adapters.dnslookup;

import com.google.common.net.InternetDomainName;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DnsClientTest_Parameterized {

    private PtrDnsAnswer buildReverseLookupDomainTest(String hostname) {
        PtrDnsAnswer.Builder builder = PtrDnsAnswer.builder();
        builder.dnsTTL(1L);
        DnsClient.parseReverseLookupDomain(builder, hostname);
        return builder.build();
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidIp4Address_1to2")
    public void testValidIp4Address_1to2(String param1) {
        assertTrue(DnsClient.isIp4Address(param1));
    }

    static public Stream<Arguments> Provider_testValidIp4Address_1to2() {
        return Stream.of(arguments("8.8.8.8"), arguments("127.0.0.1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidIp4Address_3to4")
    public void testValidIp4Address_3to4(String param1) {
        assertFalse(DnsClient.isIp4Address(param1));
    }

    static public Stream<Arguments> Provider_testValidIp4Address_3to4() {
        return Stream.of(arguments("t127.0.0.1"), arguments("google.com"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidIp6Address_1to2")
    public void testValidIp6Address_1to2(String param1) {
        assertTrue(DnsClient.isIp6Address(param1));
    }

    static public Stream<Arguments> Provider_testValidIp6Address_1to2() {
        return Stream.of(arguments("2607:f8b0:4000:812::200e"), arguments("2606:2800:220:1:248:1893:25c8:1946"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidIp6Address_3to4")
    public void testValidIp6Address_3to4(String param1) {
        assertFalse(DnsClient.isIp6Address(param1));
    }

    static public Stream<Arguments> Provider_testValidIp6Address_3to4() {
        return Stream.of(arguments("t2606:2800:220:1:248:1893:25c8:1946"), arguments("google.com"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCommaSeparatedIps_1to3")
    public void testValidCommaSeparatedIps_1to3(String param1) {
        assertTrue(DnsClient.allIpAddressesValid(param1));
    }

    static public Stream<Arguments> Provider_testValidCommaSeparatedIps_1to3() {
        return Stream.of(arguments("8.8.4.4:53, 8.8.8.8"), arguments("8.8.4.4, "), arguments("8.8.4.4"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCommaSeparatedIps_4to6")
    public void testValidCommaSeparatedIps_4to6(String param1) {
        assertFalse(DnsClient.allIpAddressesValid(param1));
    }

    static public Stream<Arguments> Provider_testValidCommaSeparatedIps_4to6() {
        return Stream.of(arguments("8.8.4.4dfs:53, 8.8.4.4:59"), arguments("8.8.4.4 8.8.8.8"), arguments("8.8.4.4, google.com"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidHostname_1to2")
    public void testValidHostname_1to2(String param1) {
        assertTrue(DnsClient.isHostName(param1));
    }

    static public Stream<Arguments> Provider_testValidHostname_1to2() {
        return Stream.of(arguments("google.com"), arguments("api.graylog.com"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidHostname_3to5")
    public void testValidHostname_3to5(String param1) {
        assertFalse(DnsClient.isHostName(param1));
    }

    static public Stream<Arguments> Provider_testValidHostname_3to5() {
        return Stream.of(arguments("http://api.graylog.com"), arguments("api.graylog.com/10"), arguments("api.graylog.com?name=dano"));
    }
}
