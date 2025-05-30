package org.apache.hadoop.util;

import java.util.Locale;
import static org.apache.hadoop.util.StringUtils.TraditionalBinaryPrefix.long2String;
import static org.apache.hadoop.util.StringUtils.TraditionalBinaryPrefix.string2long;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.test.UnitTestcaseTimeLimit;
import org.apache.hadoop.util.StringUtils.TraditionalBinaryPrefix;
import org.junit.Test;

public class TestStringUtils_Purified extends UnitTestcaseTimeLimit {

    final private static String NULL_STR = null;

    final private static String EMPTY_STR = "";

    final private static String STR_WO_SPECIAL_CHARS = "AB";

    final private static String STR_WITH_COMMA = "A,B";

    final private static String ESCAPED_STR_WITH_COMMA = "A\\,B";

    final private static String STR_WITH_ESCAPE = "AB\\";

    final private static String ESCAPED_STR_WITH_ESCAPE = "AB\\\\";

    final private static String STR_WITH_BOTH2 = ",A\\,,B\\\\,";

    final private static String ESCAPED_STR_WITH_BOTH2 = "\\,A\\\\\\,\\,B\\\\\\\\\\,";

    final private static FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("d-MMM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        final String TO_SPLIT = "foo,bar,baz,blah,blah";
        for (boolean useOurs : new boolean[] { false, true }) {
            for (int outer = 0; outer < 10; outer++) {
                long st = System.nanoTime();
                int components = 0;
                for (int inner = 0; inner < 1000000; inner++) {
                    String[] res;
                    if (useOurs) {
                        res = StringUtils.split(TO_SPLIT, ',');
                    } else {
                        res = TO_SPLIT.split(",");
                    }
                    components += res.length;
                }
                long et = System.nanoTime();
                if (outer > 3) {
                    System.out.println((useOurs ? "StringUtils impl" : "Java impl") + " #" + outer + ":" + (et - st) / 1000000 + "ms, components=" + components);
                }
            }
        }
    }

    @Test(timeout = 30000)
    public void testEscapeString_1() throws Exception {
        assertEquals(NULL_STR, StringUtils.escapeString(NULL_STR));
    }

    @Test(timeout = 30000)
    public void testEscapeString_2() throws Exception {
        assertEquals(EMPTY_STR, StringUtils.escapeString(EMPTY_STR));
    }

    @Test(timeout = 30000)
    public void testEscapeString_3() throws Exception {
        assertEquals(STR_WO_SPECIAL_CHARS, StringUtils.escapeString(STR_WO_SPECIAL_CHARS));
    }

    @Test(timeout = 30000)
    public void testEscapeString_4() throws Exception {
        assertEquals(ESCAPED_STR_WITH_COMMA, StringUtils.escapeString(STR_WITH_COMMA));
    }

    @Test(timeout = 30000)
    public void testEscapeString_5() throws Exception {
        assertEquals(ESCAPED_STR_WITH_ESCAPE, StringUtils.escapeString(STR_WITH_ESCAPE));
    }

    @Test(timeout = 30000)
    public void testEscapeString_6() throws Exception {
        assertEquals(ESCAPED_STR_WITH_BOTH2, StringUtils.escapeString(STR_WITH_BOTH2));
    }

    @Test(timeout = 30000)
    public void testSplit_1() throws Exception {
        assertEquals(NULL_STR, StringUtils.split(NULL_STR));
    }

    @Test(timeout = 30000)
    public void testSplit_2_testMerged_2() throws Exception {
        String[] splits = StringUtils.split(EMPTY_STR);
        assertEquals(0, splits.length);
        splits = StringUtils.split(",,");
        splits = StringUtils.split(STR_WO_SPECIAL_CHARS);
        assertEquals(1, splits.length);
        assertEquals(STR_WO_SPECIAL_CHARS, splits[0]);
        splits = StringUtils.split(STR_WITH_COMMA);
        assertEquals(2, splits.length);
        assertEquals("A", splits[0]);
        assertEquals("B", splits[1]);
        splits = StringUtils.split(ESCAPED_STR_WITH_COMMA);
        assertEquals(ESCAPED_STR_WITH_COMMA, splits[0]);
        splits = StringUtils.split(STR_WITH_ESCAPE);
        assertEquals(STR_WITH_ESCAPE, splits[0]);
        splits = StringUtils.split(STR_WITH_BOTH2);
        assertEquals(3, splits.length);
        assertEquals(EMPTY_STR, splits[0]);
        assertEquals("A\\,", splits[1]);
        assertEquals("B\\\\", splits[2]);
        splits = StringUtils.split(ESCAPED_STR_WITH_BOTH2);
        assertEquals(ESCAPED_STR_WITH_BOTH2, splits[0]);
    }

    @Test(timeout = 30000)
    public void testCamelize_1() {
        assertEquals("Map", StringUtils.camelize("MAP"));
    }

    @Test(timeout = 30000)
    public void testCamelize_2() {
        assertEquals("JobSetup", StringUtils.camelize("JOB_SETUP"));
    }

    @Test(timeout = 30000)
    public void testCamelize_3() {
        assertEquals("SomeStuff", StringUtils.camelize("some_stuff"));
    }

    @Test(timeout = 30000)
    public void testCamelize_4() {
        assertEquals("Aa", StringUtils.camelize("aA"));
    }

    @Test(timeout = 30000)
    public void testCamelize_5() {
        assertEquals("Bb", StringUtils.camelize("bB"));
    }

    @Test(timeout = 30000)
    public void testCamelize_6() {
        assertEquals("Cc", StringUtils.camelize("cC"));
    }

    @Test(timeout = 30000)
    public void testCamelize_7() {
        assertEquals("Dd", StringUtils.camelize("dD"));
    }

    @Test(timeout = 30000)
    public void testCamelize_8() {
        assertEquals("Ee", StringUtils.camelize("eE"));
    }

    @Test(timeout = 30000)
    public void testCamelize_9() {
        assertEquals("Ff", StringUtils.camelize("fF"));
    }

    @Test(timeout = 30000)
    public void testCamelize_10() {
        assertEquals("Gg", StringUtils.camelize("gG"));
    }

    @Test(timeout = 30000)
    public void testCamelize_11() {
        assertEquals("Hh", StringUtils.camelize("hH"));
    }

    @Test(timeout = 30000)
    public void testCamelize_12() {
        assertEquals("Ii", StringUtils.camelize("iI"));
    }

    @Test(timeout = 30000)
    public void testCamelize_13() {
        assertEquals("Jj", StringUtils.camelize("jJ"));
    }

    @Test(timeout = 30000)
    public void testCamelize_14() {
        assertEquals("Kk", StringUtils.camelize("kK"));
    }

    @Test(timeout = 30000)
    public void testCamelize_15() {
        assertEquals("Ll", StringUtils.camelize("lL"));
    }

    @Test(timeout = 30000)
    public void testCamelize_16() {
        assertEquals("Mm", StringUtils.camelize("mM"));
    }

    @Test(timeout = 30000)
    public void testCamelize_17() {
        assertEquals("Nn", StringUtils.camelize("nN"));
    }

    @Test(timeout = 30000)
    public void testCamelize_18() {
        assertEquals("Oo", StringUtils.camelize("oO"));
    }

    @Test(timeout = 30000)
    public void testCamelize_19() {
        assertEquals("Pp", StringUtils.camelize("pP"));
    }

    @Test(timeout = 30000)
    public void testCamelize_20() {
        assertEquals("Qq", StringUtils.camelize("qQ"));
    }

    @Test(timeout = 30000)
    public void testCamelize_21() {
        assertEquals("Rr", StringUtils.camelize("rR"));
    }

    @Test(timeout = 30000)
    public void testCamelize_22() {
        assertEquals("Ss", StringUtils.camelize("sS"));
    }

    @Test(timeout = 30000)
    public void testCamelize_23() {
        assertEquals("Tt", StringUtils.camelize("tT"));
    }

    @Test(timeout = 30000)
    public void testCamelize_24() {
        assertEquals("Uu", StringUtils.camelize("uU"));
    }

    @Test(timeout = 30000)
    public void testCamelize_25() {
        assertEquals("Vv", StringUtils.camelize("vV"));
    }

    @Test(timeout = 30000)
    public void testCamelize_26() {
        assertEquals("Ww", StringUtils.camelize("wW"));
    }

    @Test(timeout = 30000)
    public void testCamelize_27() {
        assertEquals("Xx", StringUtils.camelize("xX"));
    }

    @Test(timeout = 30000)
    public void testCamelize_28() {
        assertEquals("Yy", StringUtils.camelize("yY"));
    }

    @Test(timeout = 30000)
    public void testCamelize_29() {
        assertEquals("Zz", StringUtils.camelize("zZ"));
    }

    @Test(timeout = 30000)
    public void testSimpleHostName_1() {
        assertEquals("Should return hostname when FQDN is specified", "hadoop01", StringUtils.simpleHostname("hadoop01.domain.com"));
    }

    @Test(timeout = 30000)
    public void testSimpleHostName_2() {
        assertEquals("Should return hostname when only hostname is specified", "hadoop01", StringUtils.simpleHostname("hadoop01"));
    }

    @Test(timeout = 30000)
    public void testSimpleHostName_3() {
        assertEquals("Should not truncate when IP address is passed", "10.10.5.68", StringUtils.simpleHostname("10.10.5.68"));
    }

    @Test
    public void testIsAlpha_1() {
        assertTrue("Reported hello as non-alpha string", StringUtils.isAlpha("hello"));
    }

    @Test
    public void testIsAlpha_2() {
        assertFalse("Reported hello1 as alpha string", StringUtils.isAlpha("hello1"));
    }
}
