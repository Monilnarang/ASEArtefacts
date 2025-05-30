package org.apache.hadoop.registry.server.dns;

import java.net.UnknownHostException;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestReverseZoneUtils_Parameterized {

    private static final String NET = "172.17.4.0";

    private static final int RANGE = 256;

    private static final int INDEX = 0;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @ParameterizedTest
    @MethodSource("Provider_testVariousRangeAndIndexValues_1to7")
    public void testVariousRangeAndIndexValues_1to7(String param1, int param2, int param3) throws Exception {
        assertEquals(param1, ReverseZoneUtils.getReverseZoneNetworkAddress(NET, param2, param3));
    }

    static public Stream<Arguments> Provider_testVariousRangeAndIndexValues_1to7() {
        return Stream.of(arguments("172.17.9.0", 256, 5), arguments("172.17.4.128", 128, 1), arguments("172.18.0.0", 256, 252), arguments("172.17.12.0", 1024, 2), arguments("172.17.4.0", 0, 1), arguments("172.17.4.0", 1, 0), arguments("172.17.4.1", 1, 1));
    }
}
