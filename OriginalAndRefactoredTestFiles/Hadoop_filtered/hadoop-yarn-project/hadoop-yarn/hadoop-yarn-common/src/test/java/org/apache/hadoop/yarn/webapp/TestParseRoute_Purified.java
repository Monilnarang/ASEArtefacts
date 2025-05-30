package org.apache.hadoop.yarn.webapp;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestParseRoute_Purified {

    @Test
    void testDefaultAction_1() {
        assertEquals(Arrays.asList("/foo", "foo", "index"), WebApp.parseRoute("/foo"));
    }

    @Test
    void testDefaultAction_2() {
        assertEquals(Arrays.asList("/foo", "foo", "index"), WebApp.parseRoute("/foo/"));
    }

    @Test
    void testTrailingPaddings_1() {
        assertEquals(Arrays.asList("/foo/action", "foo", "action", ":a"), WebApp.parseRoute("/foo/action//:a / "));
    }

    @Test
    void testTrailingPaddings_2() {
        assertEquals(Arrays.asList("/foo/action", "foo", "action"), WebApp.parseRoute("/foo/action / "));
    }
}
