package org.apache.hadoop.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;
import org.slf4j.event.Level;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestGenericTestUtils_Purified extends GenericTestUtils {

    private static class BrokenException extends Exception {

        public BrokenException() {
        }

        @Override
        public String toString() {
            return null;
        }
    }

    @Test
    public void testToLevel_1() throws Throwable {
        assertEquals(Level.INFO, toLevel("INFO"));
    }

    @Test
    public void testToLevel_2() throws Throwable {
        assertEquals(Level.DEBUG, toLevel("NonExistLevel"));
    }

    @Test
    public void testToLevel_3() throws Throwable {
        assertEquals(Level.INFO, toLevel("INFO", Level.TRACE));
    }

    @Test
    public void testToLevel_4() throws Throwable {
        assertEquals(Level.TRACE, toLevel("NonExistLevel", Level.TRACE));
    }
}
