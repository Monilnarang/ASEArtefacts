package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Deprecated
public class StrLookupTest_Parameterized {

    @Test
    public void testNoneLookup_1() {
        assertNull(StrLookup.noneLookup().lookup(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNoneLookup_2to3")
    public void testNoneLookup_2to3(String param1) {
        assertNull(StrLookup.noneLookup().lookup(param1));
    }

    static public Stream<Arguments> Provider_testNoneLookup_2to3() {
        return Stream.of(arguments(""), arguments("any"));
    }
}
