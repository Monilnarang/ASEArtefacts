package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class B64Test_Purified {

    @Test
    public void testB64T_1() {
        assertNotNull(new B64());
    }

    @Test
    public void testB64T_2() {
        assertEquals(64, B64.B64T_ARRAY.length);
    }
}
