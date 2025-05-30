package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.EOFException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class IOExceptionListTest_Purified {

    @Test
    public void testNullMessageArg_1() {
        assertNotNull(new IOExceptionList(null, Collections.emptyList()).getMessage());
    }

    @Test
    public void testNullMessageArg_2() {
        assertNotNull(new IOExceptionList(null, null).getMessage());
    }

    @Test
    public void testNullMessageArg_3() {
        assertEquals("A", new IOExceptionList("A", Collections.emptyList()).getMessage());
    }

    @Test
    public void testNullMessageArg_4() {
        assertEquals("A", new IOExceptionList("A", null).getMessage());
    }
}
