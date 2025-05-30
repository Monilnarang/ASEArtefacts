package org.apache.commons.lang3.stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IntStreamsTest_Purified extends AbstractLangTest {

    @Test
    public void testOfArray_1() {
        assertEquals(0, IntStreams.of((int[]) null).count());
    }

    @Test
    public void testOfArray_2() {
        assertEquals(1, IntStreams.of(1).count());
    }

    @Test
    public void testOfArray_3() {
        assertEquals(2, IntStreams.of(1, 2).count());
    }
}
