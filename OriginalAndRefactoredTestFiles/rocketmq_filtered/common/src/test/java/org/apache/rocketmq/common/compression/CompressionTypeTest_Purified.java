package org.apache.rocketmq.common.compression;

import org.apache.rocketmq.common.sysflag.MessageSysFlag;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CompressionTypeTest_Purified {

    @Test
    public void testCompressionTypeValues_1() {
        assertEquals(1, CompressionType.LZ4.getValue());
    }

    @Test
    public void testCompressionTypeValues_2() {
        assertEquals(2, CompressionType.ZSTD.getValue());
    }

    @Test
    public void testCompressionTypeValues_3() {
        assertEquals(3, CompressionType.ZLIB.getValue());
    }

    @Test
    public void testCompressionFlag_1() {
        assertEquals(MessageSysFlag.COMPRESSION_LZ4_TYPE, CompressionType.LZ4.getCompressionFlag());
    }

    @Test
    public void testCompressionFlag_2() {
        assertEquals(MessageSysFlag.COMPRESSION_ZSTD_TYPE, CompressionType.ZSTD.getCompressionFlag());
    }

    @Test
    public void testCompressionFlag_3() {
        assertEquals(MessageSysFlag.COMPRESSION_ZLIB_TYPE, CompressionType.ZLIB.getCompressionFlag());
    }
}
