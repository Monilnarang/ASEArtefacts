package org.apache.seata.core.compressor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

class CompressorFactoryTest_Purified {

    @Test
    void testCompressorMapInitialization_1() {
        assertTrue(CompressorFactory.COMPRESSOR_MAP.containsKey(CompressorType.NONE));
    }

    @Test
    void testCompressorMapInitialization_2() {
        assertTrue(CompressorFactory.COMPRESSOR_MAP.get(CompressorType.NONE) instanceof CompressorFactory.NoneCompressor);
    }
}
