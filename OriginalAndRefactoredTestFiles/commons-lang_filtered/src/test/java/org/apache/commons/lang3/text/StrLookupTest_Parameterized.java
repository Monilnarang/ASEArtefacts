package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Deprecated
public class StrLookupTest_Parameterized extends AbstractLangTest {

    @Test
    public void testNoneLookup_1() {
        assertNull(StrLookup.noneLookup().lookup(null));
    }

    @Test
    public void testSystemPropertiesLookup_1() {
        assertEquals(System.getProperty("os.name"), StrLookup.systemPropertiesLookup().lookup("os.name"));
    }

    @Test
    public void testSystemPropertiesLookup_4() {
        assertNull(StrLookup.systemPropertiesLookup().lookup(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNoneLookup_2to3")
    public void testNoneLookup_2to3(String param1) {
        assertNull(StrLookup.noneLookup().lookup(param1));
    }

    static public Stream<Arguments> Provider_testNoneLookup_2to3() {
        return Stream.of(arguments(""), arguments("any"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSystemPropertiesLookup_2to3")
    public void testSystemPropertiesLookup_2to3(String param1) {
        assertNull(StrLookup.systemPropertiesLookup().lookup(param1));
    }

    static public Stream<Arguments> Provider_testSystemPropertiesLookup_2to3() {
        return Stream.of(arguments(""), arguments("other"));
    }
}
