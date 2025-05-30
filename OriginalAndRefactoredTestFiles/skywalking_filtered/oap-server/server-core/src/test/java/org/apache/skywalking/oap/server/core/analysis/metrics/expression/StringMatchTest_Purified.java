package org.apache.skywalking.oap.server.core.analysis.metrics.expression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringMatchTest_Purified {

    @Test
    public void stringShouldEqual_1() {
        assertTrue(new StringMatch().match("\"a\"", "a"));
    }

    @Test
    public void stringShouldEqual_2() {
        assertTrue(new StringMatch().match("a", "a"));
    }

    @Test
    public void stringShouldEqual_3() {
        assertFalse(new StringMatch().match("\"a\"", "ab"));
    }
}
