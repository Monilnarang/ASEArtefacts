package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UrlStringLookupTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testHttpScheme_1to2")
    public void testHttpScheme_1to2(String param1) {
        Assertions.assertNotNull(UrlStringLookup.INSTANCE.lookup(param1));
    }

    static public Stream<Arguments> Provider_testHttpScheme_1to2() {
        return Stream.of(arguments("UTF-8:https://www.apache.org"), arguments("UTF-8:https://www.google.com"));
    }
}
