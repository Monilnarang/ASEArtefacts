package org.apache.commons.pool3.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

public class TestCallStackUtils_Purified {

    private static final String MESSAGE_FORMAT = "'Timestamp:' yyyy-MM-dd HH:mm:ss Z";

    private void assertNewCallStack(final CallStack callStack) {
        callStack.fillInStackTrace();
        final StringWriter out = new StringWriter();
        callStack.printStackTrace(new PrintWriter(out));
        assertFalse(out.toString().isEmpty());
        callStack.clear();
        out.getBuffer().setLength(0);
        callStack.printStackTrace(new PrintWriter(out));
        assertTrue(out.toString().isEmpty());
    }

    @Test
    public void testNewCallStack3_1() {
        assertNewCallStack(CallStackUtils.newCallStack(MESSAGE_FORMAT, false, false));
    }

    @Test
    public void testNewCallStack3_2() {
        assertNewCallStack(CallStackUtils.newCallStack(MESSAGE_FORMAT, false, true));
    }
}
