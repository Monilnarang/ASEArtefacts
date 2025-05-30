package io.undertow.util;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NetworkUtilsAddressObfuscationTestCase_Parameterized {

    private static String cvt(String input) throws UnknownHostException {
        return NetworkUtils.toObfuscatedString(InetAddress.getByName(input));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIpV4Address_1_1to2_2to3_3")
    public void testIpV4Address_1_1to2_2to3_3(String param1, String param2) throws IOException {
        Assert.assertEquals(param1, cvt(param2));
    }

    static public Stream<Arguments> Provider_testIpV4Address_1_1to2_2to3_3() {
        return Stream.of(arguments("1.123.255.", "1.123.255.2"), arguments("127.0.0.", "127.0.0.1"), arguments("0.0.0.", "0.0.0.0"), arguments("2001:1db8:", "2001:1db8:100:3:6:ff00:42:8329"), arguments("2001:1db8:", "2001:1db8:100::6:ff00:42:8329"), arguments("0:0:", "::1"));
    }
}
