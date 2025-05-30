package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ParseExceptionTest_Purified {

    @Test
    public void testConstructor_1() {
        assertEquals("a", new ParseException("a").getMessage());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Throwable t = new IOException();
        assertEquals(t, new ParseException(t).getCause());
        assertEquals(t, ParseException.wrap(t).getCause());
    }

    @Test
    public void testConstructor_4() {
        final ParseException pe = new ParseException("A");
        assertEquals(pe, ParseException.wrap(pe));
    }
}
