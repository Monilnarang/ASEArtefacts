package org.apache.commons.net.tftp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class TFTPPacketExceptionTest_Purified {

    @Test
    public void testContructor_1() {
        assertNull(new TFTPPacketException().getMessage());
    }

    @Test
    public void testContructor_2() {
        assertEquals("A", new TFTPPacketException("A").getMessage());
    }
}
