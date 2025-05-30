package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ConversionUtilTest_Purified {

    @Test
    public void testBinaryMemoryToBytes_1() throws Exception {
        assertEquals(2.0, ConversionUtil.memoryToBytes(2, "B"), 0.01);
    }

    @Test
    public void testBinaryMemoryToBytes_2() throws Exception {
        assertEquals(2048.0, ConversionUtil.memoryToBytes(2, "KiB"), 0.01);
    }

    @Test
    public void testBinaryMemoryToBytes_3() throws Exception {
        assertEquals(2097152.0, ConversionUtil.memoryToBytes(2, "MiB"), 0.01);
    }

    @Test
    public void testBinaryMemoryToBytes_4() throws Exception {
        assertEquals(2147483648.0, ConversionUtil.memoryToBytes(2, "GiB"), 0.01);
    }

    @Test
    public void testBinaryMemoryToBytes_5() throws Exception {
        assertEquals(2199023255552.0, ConversionUtil.memoryToBytes(2, "TiB"), 0.01);
    }

    @Test
    public void testBinaryMemoryToBytes_6() throws Exception {
        assertEquals(2251799813685248.0, ConversionUtil.memoryToBytes(2, "PiB"), 0.01);
    }

    @Test
    public void testDecimalMemoryToBytes_1() throws Exception {
        assertEquals(2.0, ConversionUtil.memoryToBytes(2, "B"), 0.01);
    }

    @Test
    public void testDecimalMemoryToBytes_2() throws Exception {
        assertEquals(2000.0, ConversionUtil.memoryToBytes(2, "KB"), 0.01);
    }

    @Test
    public void testDecimalMemoryToBytes_3() throws Exception {
        assertEquals(2000000.0, ConversionUtil.memoryToBytes(2, "MB"), 0.01);
    }

    @Test
    public void testDecimalMemoryToBytes_4() throws Exception {
        assertEquals(2000000000.0, ConversionUtil.memoryToBytes(2, "GB"), 0.01);
    }

    @Test
    public void testDecimalMemoryToBytes_5() throws Exception {
        assertEquals(2000000000000.0, ConversionUtil.memoryToBytes(2, "TB"), 0.01);
    }

    @Test
    public void testDecimalMemoryToBytes_6() throws Exception {
        assertEquals(2000000000000000.0, ConversionUtil.memoryToBytes(2, "PB"), 0.01);
    }

    @Test
    public void testBinaryMemory_1() throws Exception {
        assertEquals(2.0, ConversionUtil.memoryBinaryConversion(2, "B", "B"), 0.01);
    }

    @Test
    public void testBinaryMemory_2() throws Exception {
        assertEquals(2048.0, ConversionUtil.memoryBinaryConversion(2, "KiB", "B"), 0.01);
    }

    @Test
    public void testBinaryMemory_3() throws Exception {
        assertEquals(2097152.0, ConversionUtil.memoryBinaryConversion(2, "MiB", "B"), 0.01);
    }

    @Test
    public void testBinaryMemory_4() throws Exception {
        assertEquals(2147483648.0, ConversionUtil.memoryBinaryConversion(2, "GiB", "B"), 0.01);
    }

    @Test
    public void testBinaryMemory_5() throws Exception {
        assertEquals(2199023255552.0, ConversionUtil.memoryBinaryConversion(2, "TiB", "B"), 0.01);
    }

    @Test
    public void testBinaryMemory_6() throws Exception {
        assertEquals(2251799813685248.0, ConversionUtil.memoryBinaryConversion(2, "PiB", "B"), 0.01);
    }

    @Test
    public void testDecimalMemory_1() throws Exception {
        assertEquals(2.0, ConversionUtil.memoryDecimalConversion(2, "B", "B"), 0.01);
    }

    @Test
    public void testDecimalMemory_2() throws Exception {
        assertEquals(2000.0, ConversionUtil.memoryDecimalConversion(2, "KB", "B"), 0.01);
    }

    @Test
    public void testDecimalMemory_3() throws Exception {
        assertEquals(2000000.0, ConversionUtil.memoryDecimalConversion(2, "MB", "B"), 0.01);
    }

    @Test
    public void testDecimalMemory_4() throws Exception {
        assertEquals(2000000000.0, ConversionUtil.memoryDecimalConversion(2, "GB", "B"), 0.01);
    }

    @Test
    public void testDecimalMemory_5() throws Exception {
        assertEquals(2000000000000.0, ConversionUtil.memoryDecimalConversion(2, "TB", "B"), 0.01);
    }

    @Test
    public void testDecimalMemory_6() throws Exception {
        assertEquals(2000000000000000.0, ConversionUtil.memoryDecimalConversion(2, "PB", "B"), 0.01);
    }

    @Test
    public void testCpuToHertz_1() throws Exception {
        assertEquals(2, ConversionUtil.cpuToHertz(2, "Hz"));
    }

    @Test
    public void testCpuToHertz_2() throws Exception {
        assertEquals(2000, ConversionUtil.cpuToHertz(2, "KHz"));
    }

    @Test
    public void testCpuToHertz_3() throws Exception {
        assertEquals(2000000, ConversionUtil.cpuToHertz(2, "MHz"));
    }

    @Test
    public void testCpuToHertz_4() throws Exception {
        assertEquals(2000000000L, ConversionUtil.cpuToHertz(2, "GHz"));
    }

    @Test
    public void testCpuToHertz_5() throws Exception {
        assertEquals(2000000000000L, ConversionUtil.cpuToHertz(2, "THz"));
    }

    @Test
    public void testCpuToHertz_6() throws Exception {
        assertEquals(2000000000000000L, ConversionUtil.cpuToHertz(2, "PHz"));
    }

    @Test
    public void testCpuToHertz_7() throws Exception {
        assertEquals(0, ConversionUtil.cpuToHertz(0, "NHz"));
    }

    @Test
    public void testMemoryConversion_1() throws Exception {
        assertEquals(2147483648.0, ConversionUtil.memoryBinaryConversion(2.0, "GiB", "B"), 0.01);
    }

    @Test
    public void testMemoryConversion_2() throws Exception {
        assertEquals(2097152.0, ConversionUtil.memoryBinaryConversion(2.0, "GiB", "kiB"), 0.01);
    }

    @Test
    public void testMemoryConversion_3() throws Exception {
        assertEquals(2048.0, ConversionUtil.memoryBinaryConversion(2.0, "GiB", "MiB"), 0.01);
    }

    @Test
    public void testMemoryConversion_4() throws Exception {
        assertEquals(2.0, ConversionUtil.memoryBinaryConversion(2.0, "GiB", "GiB"), 0.01);
    }

    @Test
    public void testMemoryConversion_5() throws Exception {
        assertEquals(2097152.0, ConversionUtil.memoryBinaryConversion(2.0, "MiB", "B"), 0.01);
    }

    @Test
    public void testMemoryConversion_6() throws Exception {
        assertEquals(2048.0, ConversionUtil.memoryBinaryConversion(2.0, "MiB", "kiB"), 0.01);
    }

    @Test
    public void testMemoryConversion_7() throws Exception {
        assertEquals(2.0, ConversionUtil.memoryBinaryConversion(2.0, "MiB", "MiB"), 0.01);
    }

    @Test
    public void testMemoryConversion_8() throws Exception {
        assertEquals(0.00195, ConversionUtil.memoryBinaryConversion(2.0, "MiB", "GiB"), 0.00001);
    }
}
