package org.asteriskjava.fastagi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.asteriskjava.fastagi.ScriptEngineMappingStrategy.getExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ScriptEngineMappingStrategyTest_Parameterized {

    private ScriptEngineMappingStrategy scriptEngineMappingStrategy;

    @BeforeEach
    void setUp() {
        this.scriptEngineMappingStrategy = new ScriptEngineMappingStrategy();
    }

    @Test
    void testGetExtension_7() {
        assertEquals(null, getExtension(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetExtension_1to6")
    void testGetExtension_1to6(String param1, String param2) {
        assertEquals(param1, getExtension(param2));
    }

    static public Stream<Arguments> Provider_testGetExtension_1to6() {
        return Stream.of(arguments("txt", "hello.txt"), arguments("txt", "/some/path/hello.txt"), arguments("txt", "C:\\some\\path\\hello.txt"), arguments("txt", "C:\\some\\path\\hel.lo.txt"), arguments("txt", "C:\\some\\pa.th\\hel.lo.txt"), arguments("txt", ".txt"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetExtension_8to15")
    void testGetExtension_8to15(String param1) {
        assertEquals(param1, getExtension(""));
    }

    static public Stream<Arguments> Provider_testGetExtension_8to15() {
        return Stream.of(arguments(""), arguments("hello"), arguments("/some/path/hello"), arguments("/some/pa.th/hello"), arguments("C:\\some\\path\\hello"), arguments("C:\\some\\pa.th\\hello"), arguments("/some/pa.th\\hello"), arguments("C:\\some\\pa.th/hello"));
    }
}
