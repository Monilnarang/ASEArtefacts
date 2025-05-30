package org.apache.hadoop.registry.client.binding;

import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestRegistryOperationUtils_Parameterized extends Assert {

    @ParameterizedTest
    @MethodSource("Provider_testShortenUsername_1to4")
    public void testShortenUsername_1to4(String param1, String param2) throws Throwable {
        assertEquals(param1, RegistryUtils.convertUsername(param2));
    }

    static public Stream<Arguments> Provider_testShortenUsername_1to4() {
        return Stream.of(arguments("hbase", "hbase@HADOOP.APACHE.ORG"), arguments("hbase", "hbase/localhost@HADOOP.APACHE.ORG"), arguments("hbase", "hbase"), arguments("hbase user", "hbase user"));
    }
}
