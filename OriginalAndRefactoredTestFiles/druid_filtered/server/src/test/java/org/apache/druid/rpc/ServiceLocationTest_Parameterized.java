package org.apache.druid.rpc;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.server.coordination.DruidServerMetadata;
import org.apache.druid.server.coordination.ServerType;
import org.junit.Assert;
import org.junit.Test;
import java.net.URI;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ServiceLocationTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_test_stripBrackets_1to3")
    public void test_stripBrackets_1to3(String param1, String param2) {
        Assert.assertEquals(param1, ServiceLocation.stripBrackets(param2));
    }

    static public Stream<Arguments> Provider_test_stripBrackets_1to3() {
        return Stream.of(arguments("1:2:3:4:5:6:7:8", "[1:2:3:4:5:6:7:8]"), arguments("1:2:3:4:5:6:7:8", "1:2:3:4:5:6:7:8"), arguments("1.2.3.4", "1.2.3.4"));
    }
}
