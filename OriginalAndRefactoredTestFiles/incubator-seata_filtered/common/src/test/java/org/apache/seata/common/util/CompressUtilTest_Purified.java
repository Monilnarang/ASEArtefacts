package org.apache.seata.common.util;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

public class CompressUtilTest_Purified {

    final byte[] originBytes = new byte[] { 1, 2, 3 };

    final byte[] compressedBytes1 = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0, 99, 100, 98, 6, 0, 29, -128, -68, 85, 3, 0, 0, 0 };

    final byte[] compressedBytes2 = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, -1, 99, 100, 98, 6, 0, 29, -128, -68, 85, 3, 0, 0, 0 };

    @Test
    public void testUncompress_1() throws IOException {
        Assertions.assertArrayEquals(originBytes, CompressUtil.uncompress(compressedBytes1));
    }

    @Test
    public void testUncompress_2() throws IOException {
        Assertions.assertArrayEquals(originBytes, CompressUtil.uncompress(compressedBytes2));
    }
}
