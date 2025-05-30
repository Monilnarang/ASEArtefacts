package org.apache.hadoop.fs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.apache.hadoop.fs.HardLink.*;

public class TestHardLink_Purified {

    final static private File TEST_DIR = GenericTestUtils.getTestDir("test/hl");

    private static String DIR = "dir_";

    private static File src = new File(TEST_DIR, DIR + "src");

    private static File tgt_mult = new File(TEST_DIR, DIR + "tgt_mult");

    private static File tgt_one = new File(TEST_DIR, DIR + "tgt_one");

    private static File x1 = new File(src, "x1");

    private static File x2 = new File(src, "x2");

    private static File x3 = new File(src, "x3");

    private static File x1_one = new File(tgt_one, "x1");

    private static File y_one = new File(tgt_one, "y");

    private static File x3_one = new File(tgt_one, "x3");

    private static File x11_one = new File(tgt_one, "x11");

    private static File x1_mult = new File(tgt_mult, "x1");

    private static File x2_mult = new File(tgt_mult, "x2");

    private static File x3_mult = new File(tgt_mult, "x3");

    private static String str1 = "11111";

    private static String str2 = "22222";

    private static String str3 = "33333";

    @BeforeClass
    public static void setupClean() {
        FileUtil.fullyDelete(src);
        FileUtil.fullyDelete(tgt_one);
        FileUtil.fullyDelete(tgt_mult);
        assertFalse(src.exists());
        assertFalse(tgt_one.exists());
        assertFalse(tgt_mult.exists());
    }

    @Before
    public void setupDirs() throws IOException {
        assertFalse(src.exists());
        assertFalse(tgt_one.exists());
        assertFalse(tgt_mult.exists());
        src.mkdirs();
        tgt_one.mkdirs();
        tgt_mult.mkdirs();
        makeNonEmptyFile(x1, str1);
        makeNonEmptyFile(x2, str2);
        makeNonEmptyFile(x3, str3);
        validateSetup();
    }

    private void validateSetup() throws IOException {
        assertTrue(src.exists());
        assertEquals(3, src.list().length);
        assertTrue(x1.exists());
        assertTrue(x2.exists());
        assertTrue(x3.exists());
        assertTrue(fetchFileContents(x1).equals(str1));
        assertTrue(fetchFileContents(x2).equals(str2));
        assertTrue(fetchFileContents(x3).equals(str3));
        assertTrue(tgt_one.exists());
        assertTrue(tgt_mult.exists());
        assertEquals(0, tgt_one.list().length);
        assertEquals(0, tgt_mult.list().length);
    }

    private void validateTgtOne() throws IOException {
        assertTrue(tgt_one.exists());
        assertEquals(4, tgt_one.list().length);
        assertTrue(x1_one.exists());
        assertTrue(x11_one.exists());
        assertTrue(y_one.exists());
        assertTrue(x3_one.exists());
        assertTrue(fetchFileContents(x1_one).equals(str1));
        assertTrue(fetchFileContents(x11_one).equals(str1));
        assertTrue(fetchFileContents(y_one).equals(str2));
        assertTrue(fetchFileContents(x3_one).equals(str3));
    }

    private void validateTgtMult() throws IOException {
        assertTrue(tgt_mult.exists());
        assertEquals(3, tgt_mult.list().length);
        assertTrue(x1_mult.exists());
        assertTrue(x2_mult.exists());
        assertTrue(x3_mult.exists());
        assertTrue(fetchFileContents(x1_mult).equals(str1));
        assertTrue(fetchFileContents(x2_mult).equals(str2));
        assertTrue(fetchFileContents(x3_mult).equals(str3));
    }

    @After
    public void tearDown() throws IOException {
        setupClean();
    }

    private void makeNonEmptyFile(File file, String contents) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(contents);
        fw.close();
    }

    private void appendToFile(File file, String contents) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(contents);
        fw.close();
    }

    private String fetchFileContents(File file) throws IOException {
        char[] buf = new char[20];
        FileReader fr = new FileReader(file);
        int cnt = fr.read(buf);
        fr.close();
        char[] result = Arrays.copyOf(buf, cnt);
        return new String(result);
    }

    @Test
    public void testGetLinkCount_1() throws IOException {
        assertEquals(1, getLinkCount(x1));
    }

    @Test
    public void testGetLinkCount_2() throws IOException {
        assertEquals(1, getLinkCount(x2));
    }

    @Test
    public void testGetLinkCount_3() throws IOException {
        assertEquals(1, getLinkCount(x3));
    }

    @Test
    public void testCreateHardLink_1_testMerged_1() throws IOException {
        createHardLink(x1, x1_one);
        assertTrue(x1_one.exists());
        assertEquals(2, getLinkCount(x1));
        assertEquals(2, getLinkCount(x1_one));
        createHardLink(x1, x11_one);
        assertEquals(3, getLinkCount(x1));
        assertEquals(3, getLinkCount(x1_one));
        assertEquals(3, getLinkCount(x11_one));
        appendToFile(x1_one, str3);
        assertTrue(fetchFileContents(x1_one).equals(str1 + str3));
        assertTrue(fetchFileContents(x11_one).equals(str1 + str3));
        assertTrue(fetchFileContents(x1).equals(str1 + str3));
    }

    @Test
    public void testCreateHardLink_4() throws IOException {
        assertEquals(1, getLinkCount(x2));
    }

    @Test
    public void testCreateHardLink_5() throws IOException {
        createHardLink(x2, y_one);
        assertEquals(2, getLinkCount(x2));
    }

    @Test
    public void testCreateHardLink_6() throws IOException {
        createHardLink(x3, x3_one);
        assertEquals(2, getLinkCount(x3));
    }

    @Test
    public void testCreateHardLinkMult_1() throws IOException {
        assertEquals(2, getLinkCount(x1));
    }

    @Test
    public void testCreateHardLinkMult_2() throws IOException {
        assertEquals(2, getLinkCount(x2));
    }

    @Test
    public void testCreateHardLinkMult_3() throws IOException {
        assertEquals(2, getLinkCount(x3));
    }

    @Test
    public void testCreateHardLinkMult_4() throws IOException {
        assertEquals(2, getLinkCount(x1_mult));
    }

    @Test
    public void testCreateHardLinkMult_5() throws IOException {
        assertEquals(2, getLinkCount(x2_mult));
    }

    @Test
    public void testCreateHardLinkMult_6() throws IOException {
        assertEquals(2, getLinkCount(x3_mult));
    }

    @Test
    public void testCreateHardLinkMult_7_testMerged_7() throws IOException {
        appendToFile(x1_mult, str3);
        assertTrue(fetchFileContents(x1_mult).equals(str1 + str3));
        assertTrue(fetchFileContents(x1).equals(str1 + str3));
    }
}
