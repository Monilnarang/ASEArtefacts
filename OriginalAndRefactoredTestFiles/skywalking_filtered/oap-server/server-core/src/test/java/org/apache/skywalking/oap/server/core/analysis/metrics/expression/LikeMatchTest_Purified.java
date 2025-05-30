package org.apache.skywalking.oap.server.core.analysis.metrics.expression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LikeMatchTest_Purified {

    @Test
    public void testLike_1() {
        assertTrue(new LikeMatch().match("MaxBlack", "%Black"));
    }

    @Test
    public void testLike_2() {
        assertTrue(new LikeMatch().match("MaxBlack", "Max%"));
    }

    @Test
    public void testLike_3() {
        assertTrue(new LikeMatch().match("MaxBlack", "%axBl%"));
    }

    @Test
    public void testLike_4() {
        assertFalse(new LikeMatch().match("CarolineChanning", "Max%"));
    }

    @Test
    public void testLike_5() {
        assertFalse(new LikeMatch().match("CarolineChanning", "%Max"));
    }

    @Test
    public void testLike_6() {
        assertTrue(new LikeMatch().match("MaxBlack", "\"%Black\""));
    }

    @Test
    public void testLike_7() {
        assertFalse(new LikeMatch().match("CarolineChanning", "\"Max%\""));
    }
}
