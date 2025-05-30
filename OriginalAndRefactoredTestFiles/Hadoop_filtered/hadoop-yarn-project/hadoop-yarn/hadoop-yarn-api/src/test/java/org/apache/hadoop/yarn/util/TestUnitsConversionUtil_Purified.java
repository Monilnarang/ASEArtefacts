package org.apache.hadoop.yarn.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TestUnitsConversionUtil_Purified {

    @Test
    void testUnitsConversion_1_testMerged_1() {
        int value = 5;
        String fromUnit = "";
        long test = value;
        assertEquals(value * 1000L * 1000L * 1000L * 1000L, UnitsConversionUtil.convert(fromUnit, "p", test), "pico test failed");
        assertEquals(value * 1000L * 1000L * 1000L, UnitsConversionUtil.convert(fromUnit, "n", test), "nano test failed");
        assertEquals(value * 1000L * 1000L, UnitsConversionUtil.convert(fromUnit, "u", test), "micro test failed");
        assertEquals(value * 1000L, UnitsConversionUtil.convert(fromUnit, "m", test), "milli test failed");
        test = value * 1000L * 1000L * 1000L * 1000L * 1000L;
        fromUnit = "";
        assertEquals(test / 1000L, UnitsConversionUtil.convert(fromUnit, "k", test), "kilo test failed");
        assertEquals(test / (1000L * 1000L), UnitsConversionUtil.convert(fromUnit, "M", test), "mega test failed");
        assertEquals(test / (1000L * 1000L * 1000L), UnitsConversionUtil.convert(fromUnit, "G", test), "giga test failed");
        assertEquals(test / (1000L * 1000L * 1000L * 1000L), UnitsConversionUtil.convert(fromUnit, "T", test), "tera test failed");
        assertEquals(test / (1000L * 1000L * 1000L * 1000L * 1000L), UnitsConversionUtil.convert(fromUnit, "P", test), "peta test failed");
        assertEquals(value * 1000L, UnitsConversionUtil.convert("n", "p", value), "nano to pico test failed");
        assertEquals(value, UnitsConversionUtil.convert("M", "G", value * 1000L), "mega to giga test failed");
        assertEquals(value, UnitsConversionUtil.convert("Mi", "Gi", value * 1024L), "Mi to Gi test failed");
        assertEquals(value * 1024, UnitsConversionUtil.convert("Mi", "Ki", value), "Mi to Ki test failed");
    }

    @Test
    void testUnitsConversion_14() {
        assertEquals(5 * 1024, UnitsConversionUtil.convert("Ki", "", 5), "Ki to base units test failed");
    }

    @Test
    void testUnitsConversion_15() {
        assertEquals(1073741, UnitsConversionUtil.convert("Mi", "k", 1024), "Mi to k test failed");
    }

    @Test
    void testUnitsConversion_16() {
        assertEquals(953, UnitsConversionUtil.convert("M", "Mi", 1000), "M to Mi test failed");
    }
}
