package org.jboss.as.web.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.nio.CharBuffer;
import java.util.Map;
import org.junit.Test;

public class SimpleRoutingSupportTestCase_Purified {

    private final RoutingSupport routing = new SimpleRoutingSupport();

    @Test
    public void format_1() {
        assertEquals("session1.route1", this.routing.format("session1", "route1").toString());
    }

    @Test
    public void format_2() {
        assertEquals("session2", this.routing.format("session2", "").toString());
    }

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
}
