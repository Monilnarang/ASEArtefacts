package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnrecognizedOptionExceptionTest_Purified {

    @Test
    public void testConstructor_1() {
        assertEquals("a", new UnrecognizedOptionException("a").getMessage());
    }

    @Test
    public void testConstructor_2() {
        assertEquals("a", new UnrecognizedOptionException("a", "b").getMessage());
    }

    @Test
    public void testConstructor_3() {
        assertEquals("b", new UnrecognizedOptionException("a", "b").getOption());
    }
}
