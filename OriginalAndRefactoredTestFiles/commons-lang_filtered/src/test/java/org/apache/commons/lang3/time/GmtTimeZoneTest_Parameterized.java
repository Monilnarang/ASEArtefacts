package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GmtTimeZoneTest_Parameterized extends AbstractLangTest {

    @Test
    public void testGetOffset_1() {
        assertEquals(0, new GmtTimeZone(false, 0, 0).getOffset(234304));
    }

    @Test
    public void testGetOffset_2() {
        assertEquals(-(6 * 60 + 30) * 60 * 1000, new GmtTimeZone(true, 6, 30).getOffset(1, 1, 1, 1, 1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetID_1to5")
    public void testGetID_1to5(String param1, int param2, int param3) {
        assertEquals(param1, new GmtTimeZone(param2, param3, 0).getID());
    }

    static public Stream<Arguments> Provider_testGetID_1to5() {
        return Stream.of(arguments("GMT+00:00", 0, 0), arguments("GMT+01:02", 1, 2), arguments("GMT+11:22", 11, 22), arguments("GMT-01:02", 1, 2), arguments("GMT-11:22", 11, 22));
    }
}
