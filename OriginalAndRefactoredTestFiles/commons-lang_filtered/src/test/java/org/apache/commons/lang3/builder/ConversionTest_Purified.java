package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.Conversion;
import org.junit.jupiter.api.Test;

class ConversionTest_Purified {

    @Test
    void testHexToByte_1() {
        assertEquals((byte) 0, Conversion.hexToByte("00", 0, (byte) 0, 0, 0));
    }

    @Test
    void testHexToByte_2() {
        assertEquals((byte) 0, Conversion.hexToByte("00", 0, (byte) 0, 0, 2));
    }
}
