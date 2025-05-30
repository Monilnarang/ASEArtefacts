package org.apache.hadoop.security.alias;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.ProviderUtils;
import org.junit.Test;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestCredentialProvider_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testUnnestUri_1to4")
    public void testUnnestUri_1to4(String param1, String param2) throws Exception {
        assertEquals(new Path(param1), ProviderUtils.unnestUri(new URI(param2)));
    }

    static public Stream<Arguments> Provider_testUnnestUri_1to4() {
        return Stream.of(arguments("hdfs://nn.example.com/my/path", "myscheme://hdfs@nn.example.com/my/path"), arguments("hdfs://nn/my/path?foo=bar&baz=bat#yyy", "myscheme://hdfs@nn/my/path?foo=bar&baz=bat#yyy"), arguments("inner://hdfs@nn1.example.com/my/path", "outer://inner@hdfs@nn1.example.com/my/path"), arguments("user:///", "outer://user/"));
    }
}
