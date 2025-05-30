package org.graylog2.indexer.datastream.policy.actions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BytesUnitTest_Purified {

    @Test
    public void testFormat_1() {
        assertEquals("10pb", BytesUnit.PEBIBYTES.format(10L));
    }

    @Test
    public void testFormat_2() {
        assertEquals("10tb", BytesUnit.TEBIBYTES.format(10L));
    }

    @Test
    public void testFormat_3() {
        assertEquals("10gb", BytesUnit.GIBIBYTES.format(10L));
    }

    @Test
    public void testFormat_4() {
        assertEquals("10mb", BytesUnit.MEBIBYTES.format(10L));
    }

    @Test
    public void testFormat_5() {
        assertEquals("10kb", BytesUnit.KIBIBYTES.format(10L));
    }

    @Test
    public void testFormat_6() {
        assertEquals("10b", BytesUnit.BYTES.format(10L));
    }
}
