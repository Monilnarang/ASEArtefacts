package org.apache.commons.lang3.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContextedRuntimeExceptionTest_Purified extends AbstractExceptionContextTest<ContextedRuntimeException> {

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        exceptionContext = new ContextedRuntimeException(new Exception(TEST_MESSAGE));
        super.setUp();
    }

    @Test
    public void testRawMessage_1() {
        assertEquals(Exception.class.getName() + ": " + TEST_MESSAGE, exceptionContext.getRawMessage());
    }

    @Test
    public void testRawMessage_2_testMerged_2() {
        exceptionContext = new ContextedRuntimeException(TEST_MESSAGE_2, new Exception(TEST_MESSAGE), new DefaultExceptionContext());
        assertEquals(TEST_MESSAGE_2, exceptionContext.getRawMessage());
        exceptionContext = new ContextedRuntimeException(null, new Exception(TEST_MESSAGE), new DefaultExceptionContext());
        assertNull(exceptionContext.getRawMessage());
    }
}
