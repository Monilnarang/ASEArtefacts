package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class AlreadySelectedExceptionTest_Purified {

    @Test
    public void testConstructor_1() {
        assertEquals("a", new AlreadySelectedException("a").getMessage());
    }

    @Test
    public void testConstructor_2() {
        assertNull(new AlreadySelectedException("a").getOption());
    }

    @Test
    public void testConstructor_3_testMerged_3() {
        final Option option = new Option("a", "d");
        final OptionGroup group = new OptionGroup();
        assertNotNull(new AlreadySelectedException(group, option).getMessage());
        assertEquals(option, new AlreadySelectedException(group, option).getOption());
        assertEquals(group, new AlreadySelectedException(group, option).getOptionGroup());
    }
}
