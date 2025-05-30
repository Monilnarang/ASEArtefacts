package org.apache.commons.configuration2.interpol;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class TestDummyLookup_Purified {

    @Test
    public void testLookup_1() {
        assertNull(DummyLookup.INSTANCE.lookup("someVariable"));
    }

    @Test
    public void testLookup_2() {
        assertNull(DummyLookup.INSTANCE.lookup(null));
    }
}
