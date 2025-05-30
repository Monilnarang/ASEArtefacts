package org.jboss.as.web.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.nio.CharBuffer;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SimpleRoutingSupportTestCase_Parameterized {

    private final RoutingSupport routing = new SimpleRoutingSupport();

    @Test
    public void format_3() {
        assertEquals("session3", this.routing.format("session3", null).toString());
    }

    @Test
    public void format_4() {
        assertEquals("session1.route1", this.routing.format(CharBuffer.wrap("session1"), CharBuffer.wrap("route1")).toString());
    }

    @Test
    public void format_5() {
        assertEquals("session1.route1", this.routing.format(new StringBuilder("session1"), new StringBuilder("route1")).toString());
    }

    @Test
    public void format_6() {
        assertNull(this.routing.format(null, null));
    }

    @ParameterizedTest
    @MethodSource("Provider_format_1to2")
    public void format_1to2(String param1, String param2, String param3) {
        assertEquals(param1, this.routing.format(param2, param3).toString());
    }

    static public Stream<Arguments> Provider_format_1to2() {
        return Stream.of(arguments("session1.route1", "session1", "route1"), arguments("session2", "session2", ""));
    }
}
