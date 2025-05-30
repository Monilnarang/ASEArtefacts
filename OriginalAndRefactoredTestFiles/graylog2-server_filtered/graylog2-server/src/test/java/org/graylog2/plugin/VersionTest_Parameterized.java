package org.graylog2.plugin;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VersionTest_Parameterized {

    @Test
    public void testGetName_5() throws Exception {
        assertEquals("1.0.0-preview.1", Version.from(1, 0, 0, "preview.1").toString());
    }

    @Test
    public void testGetName_6() throws Exception {
        assertEquals("1.0.0-preview.1+deadbeef", Version.from(1, 0, 0, "preview.1", "deadbeef").toString());
    }

    @Test
    public void testEquals_2() throws Exception {
        assertTrue(Version.from(0, 20, 0, "preview.1").equals(Version.from(0, 20, 0, "preview.1")));
    }

    @Test
    public void testEquals_4_testMerged_4() throws Exception {
        Version v = Version.from(0, 20, 0);
        assertEquals(Version.from(0, 20, 0), v);
        assertFalse(Version.from(0, 20, 0).equals(Version.from(0, 20, 1)));
        assertFalse(Version.from(0, 20, 0, "preview.1").equals(Version.from(0, 20, 0, "preview.2")));
        assertFalse(Version.from(0, 20, 0).equals(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetName_1to4")
    public void testGetName_1to4(String param1, int param2, int param3, int param4) throws Exception {
        assertEquals(param1, Version.from(param2, param3, param4).toString());
    }

    static public Stream<Arguments> Provider_testGetName_1to4() {
        return Stream.of(arguments("0.20.0", 0, 20, 0), arguments("1.0.0", 1, 0, 0), arguments("1.2.3", 1, 2, 3), arguments("0.0.7", 0, 0, 7));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEquals_1_3")
    public void testEquals_1_3(int param1, int param2, int param3, int param4, int param5, int param6) throws Exception {
        assertTrue(Version.from(param1, param2, param3).equals(Version.from(param4, param5, param6)));
    }

    static public Stream<Arguments> Provider_testEquals_1_3() {
        return Stream.of(arguments(0, 20, 0, 0, 20, 0), arguments(1, 2, 3, 1, 2, 3));
    }
}
