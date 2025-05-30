package org.graylog.plugins.threatintel.tools;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DomainTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testPrepareDomain_1to6")
    public void testPrepareDomain_1to6(String param1, String param2) throws Exception {
        assertEquals(param1, Domain.prepareDomain(param2));
    }

    static public Stream<Arguments> Provider_testPrepareDomain_1to6() {
        return Stream.of(arguments("example.org", "example.org "), arguments("example.org", " example.org"), arguments("example.org", " example.org "), arguments("example.org", "example.org. "), arguments("example.org", " example.org."), arguments("example.org", " example.org. "));
    }
}
