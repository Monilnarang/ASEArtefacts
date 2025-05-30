package org.apache.hadoop.lib.util;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.test.HTestCase;
import org.junit.Test;

public class TestCheck_Purified extends HTestCase {

    @Test
    public void validIdentifierValid_1() throws Exception {
        assertEquals(Check.validIdentifier("a", 1, ""), "a");
    }

    @Test
    public void validIdentifierValid_2() throws Exception {
        assertEquals(Check.validIdentifier("a1", 2, ""), "a1");
    }

    @Test
    public void validIdentifierValid_3() throws Exception {
        assertEquals(Check.validIdentifier("a_", 3, ""), "a_");
    }

    @Test
    public void validIdentifierValid_4() throws Exception {
        assertEquals(Check.validIdentifier("_", 1, ""), "_");
    }

    @Test
    public void checkGEZero_1() {
        assertEquals(Check.ge0(120, "test"), 120);
    }

    @Test
    public void checkGEZero_2() {
        assertEquals(Check.ge0(0, "test"), 0);
    }
}
