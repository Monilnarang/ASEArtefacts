package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.io.test.TestUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FilenameUtilsTest_Purified {

    private static final String SEP = "" + File.separatorChar;

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @TempDir
    public Path temporaryFolder;

    private Path testFile1;

    private Path testFile2;

    private int testFile1Size;

    private int testFile2Size;

    @BeforeEach
    public void setUp() throws Exception {
        testFile1 = Files.createTempFile(temporaryFolder, "test", "1");
        testFile2 = Files.createTempFile(temporaryFolder, "test", "2");
        testFile1Size = (int) Files.size(testFile1);
        testFile2Size = (int) Files.size(testFile2);
        if (!Files.exists(testFile1.getParent())) {
            throw new IOException("Cannot create file " + testFile1 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output3 = new BufferedOutputStream(Files.newOutputStream(testFile1))) {
            TestUtils.generateTestData(output3, testFile1Size);
        }
        if (!Files.exists(testFile2.getParent())) {
            throw new IOException("Cannot create file " + testFile2 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output2 = new BufferedOutputStream(Files.newOutputStream(testFile2))) {
            TestUtils.generateTestData(output2, testFile2Size);
        }
        if (!Files.exists(testFile1.getParent())) {
            throw new IOException("Cannot create file " + testFile1 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output1 = new BufferedOutputStream(Files.newOutputStream(testFile1))) {
            TestUtils.generateTestData(output1, testFile1Size);
        }
        if (!Files.exists(testFile2.getParent())) {
            throw new IOException("Cannot create file " + testFile2 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(testFile2))) {
            TestUtils.generateTestData(output, testFile2Size);
        }
    }

    @Test
    public void testConcat_1() {
        assertNull(FilenameUtils.concat("", null));
    }

    @Test
    public void testConcat_2() {
        assertNull(FilenameUtils.concat(null, null));
    }

    @Test
    public void testConcat_3() {
        assertNull(FilenameUtils.concat(null, ""));
    }

    @Test
    public void testConcat_4() {
        assertNull(FilenameUtils.concat(null, "a"));
    }

    @Test
    public void testConcat_5() {
        assertEquals(SEP + "a", FilenameUtils.concat(null, "/a"));
    }

    @Test
    public void testConcat_6() {
        assertNull(FilenameUtils.concat("", ":"));
    }

    @Test
    public void testConcat_7() {
        assertNull(FilenameUtils.concat(":", ""));
    }

    @Test
    public void testConcat_8() {
        assertEquals("f" + SEP, FilenameUtils.concat("", "f/"));
    }

    @Test
    public void testConcat_9() {
        assertEquals("f", FilenameUtils.concat("", "f"));
    }

    @Test
    public void testConcat_10() {
        assertEquals("a" + SEP + "f" + SEP, FilenameUtils.concat("a/", "f/"));
    }

    @Test
    public void testConcat_11() {
        assertEquals("a" + SEP + "f", FilenameUtils.concat("a", "f"));
    }

    @Test
    public void testConcat_12() {
        assertEquals("a" + SEP + "b" + SEP + "f" + SEP, FilenameUtils.concat("a/b/", "f/"));
    }

    @Test
    public void testConcat_13() {
        assertEquals("a" + SEP + "b" + SEP + "f", FilenameUtils.concat("a/b", "f"));
    }

    @Test
    public void testConcat_14() {
        assertEquals("a" + SEP + "f" + SEP, FilenameUtils.concat("a/b/", "../f/"));
    }

    @Test
    public void testConcat_15() {
        assertEquals("a" + SEP + "f", FilenameUtils.concat("a/b", "../f"));
    }

    @Test
    public void testConcat_16() {
        assertEquals("a" + SEP + "c" + SEP + "g" + SEP, FilenameUtils.concat("a/b/../c/", "f/../g/"));
    }

    @Test
    public void testConcat_17() {
        assertEquals("a" + SEP + "c" + SEP + "g", FilenameUtils.concat("a/b/../c", "f/../g"));
    }

    @Test
    public void testConcat_18() {
        assertEquals("a" + SEP + "c.txt" + SEP + "f", FilenameUtils.concat("a/c.txt", "f"));
    }

    @Test
    public void testConcat_19() {
        assertEquals(SEP + "f" + SEP, FilenameUtils.concat("", "/f/"));
    }

    @Test
    public void testConcat_20() {
        assertEquals(SEP + "f", FilenameUtils.concat("", "/f"));
    }

    @Test
    public void testConcat_21() {
        assertEquals(SEP + "f" + SEP, FilenameUtils.concat("a/", "/f/"));
    }

    @Test
    public void testConcat_22() {
        assertEquals(SEP + "f", FilenameUtils.concat("a", "/f"));
    }

    @Test
    public void testConcat_23() {
        assertEquals(SEP + "c" + SEP + "d", FilenameUtils.concat("a/b/", "/c/d"));
    }

    @Test
    public void testConcat_24() {
        assertEquals("C:c" + SEP + "d", FilenameUtils.concat("a/b/", "C:c/d"));
    }

    @Test
    public void testConcat_25() {
        assertEquals("C:" + SEP + "c" + SEP + "d", FilenameUtils.concat("a/b/", "C:/c/d"));
    }

    @Test
    public void testConcat_26() {
        assertEquals("~" + SEP + "c" + SEP + "d", FilenameUtils.concat("a/b/", "~/c/d"));
    }

    @Test
    public void testConcat_27() {
        assertEquals("~user" + SEP + "c" + SEP + "d", FilenameUtils.concat("a/b/", "~user/c/d"));
    }

    @Test
    public void testConcat_28() {
        assertEquals("~" + SEP, FilenameUtils.concat("a/b/", "~"));
    }

    @Test
    public void testConcat_29() {
        assertEquals("~user" + SEP, FilenameUtils.concat("a/b/", "~user"));
    }

    @Test
    public void testDirectoryContains_1() {
        assertTrue(FilenameUtils.directoryContains("/foo", "/foo/bar"));
    }

    @Test
    public void testDirectoryContains_2() {
        assertTrue(FilenameUtils.directoryContains("/foo/", "/foo/bar"));
    }

    @Test
    public void testDirectoryContains_3() {
        assertTrue(FilenameUtils.directoryContains("C:\\foo", "C:\\foo\\bar"));
    }

    @Test
    public void testDirectoryContains_4() {
        assertTrue(FilenameUtils.directoryContains("C:\\foo\\", "C:\\foo\\bar"));
    }

    @Test
    public void testDirectoryContains_5() {
        assertFalse(FilenameUtils.directoryContains("/foo", "/foo"));
    }

    @Test
    public void testDirectoryContains_6() {
        assertFalse(FilenameUtils.directoryContains("/foo", "/foobar"));
    }

    @Test
    public void testDirectoryContains_7() {
        assertFalse(FilenameUtils.directoryContains("C:\\foo", "C:\\foobar"));
    }

    @Test
    public void testDirectoryContains_8() {
        assertFalse(FilenameUtils.directoryContains("/foo", null));
    }

    @Test
    public void testDirectoryContains_9() {
        assertFalse(FilenameUtils.directoryContains("", ""));
    }

    @Test
    public void testDirectoryContains_10() {
        assertFalse(FilenameUtils.directoryContains("", "/foo"));
    }

    @Test
    public void testDirectoryContains_11() {
        assertFalse(FilenameUtils.directoryContains("/foo", ""));
    }

    @Test
    public void testEquals_1() {
        assertTrue(FilenameUtils.equals(null, null));
    }

    @Test
    public void testEquals_2() {
        assertFalse(FilenameUtils.equals(null, ""));
    }

    @Test
    public void testEquals_3() {
        assertFalse(FilenameUtils.equals("", null));
    }

    @Test
    public void testEquals_4() {
        assertTrue(FilenameUtils.equals("", ""));
    }

    @Test
    public void testEquals_5() {
        assertTrue(FilenameUtils.equals("file.txt", "file.txt"));
    }

    @Test
    public void testEquals_6() {
        assertFalse(FilenameUtils.equals("file.txt", "FILE.TXT"));
    }

    @Test
    public void testEquals_7() {
        assertFalse(FilenameUtils.equals("a\\b\\file.txt", "a/b/file.txt"));
    }

    @Test
    public void testEquals_fullControl_1() {
        assertFalse(FilenameUtils.equals("file.txt", "FILE.TXT", true, IOCase.SENSITIVE));
    }

    @Test
    public void testEquals_fullControl_2() {
        assertTrue(FilenameUtils.equals("file.txt", "FILE.TXT", true, IOCase.INSENSITIVE));
    }

    @Test
    public void testEquals_fullControl_3() {
        assertEquals(WINDOWS, FilenameUtils.equals("file.txt", "FILE.TXT", true, IOCase.SYSTEM));
    }

    @Test
    public void testEquals_fullControl_4() {
        assertFalse(FilenameUtils.equals("file.txt", "FILE.TXT", true, null));
    }

    @Test
    public void testEqualsNormalized_1() {
        assertTrue(FilenameUtils.equalsNormalized(null, null));
    }

    @Test
    public void testEqualsNormalized_2() {
        assertFalse(FilenameUtils.equalsNormalized(null, ""));
    }

    @Test
    public void testEqualsNormalized_3() {
        assertFalse(FilenameUtils.equalsNormalized("", null));
    }

    @Test
    public void testEqualsNormalized_4() {
        assertTrue(FilenameUtils.equalsNormalized("", ""));
    }

    @Test
    public void testEqualsNormalized_5() {
        assertTrue(FilenameUtils.equalsNormalized("file.txt", "file.txt"));
    }

    @Test
    public void testEqualsNormalized_6() {
        assertFalse(FilenameUtils.equalsNormalized("file.txt", "FILE.TXT"));
    }

    @Test
    public void testEqualsNormalized_7() {
        assertTrue(FilenameUtils.equalsNormalized("a\\b\\file.txt", "a/b/file.txt"));
    }

    @Test
    public void testEqualsNormalized_8() {
        assertFalse(FilenameUtils.equalsNormalized("a/b/", "a/b"));
    }

    @Test
    public void testEqualsNormalizedError_IO_128_1() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem("//file.txt", "file.txt"));
    }

    @Test
    public void testEqualsNormalizedError_IO_128_2() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem("file.txt", "//file.txt"));
    }

    @Test
    public void testEqualsNormalizedError_IO_128_3() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem("//file.txt", "//file.txt"));
    }

    @Test
    public void testEqualsNormalizedOnSystem_1() {
        assertTrue(FilenameUtils.equalsNormalizedOnSystem(null, null));
    }

    @Test
    public void testEqualsNormalizedOnSystem_2() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem(null, ""));
    }

    @Test
    public void testEqualsNormalizedOnSystem_3() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem("", null));
    }

    @Test
    public void testEqualsNormalizedOnSystem_4() {
        assertTrue(FilenameUtils.equalsNormalizedOnSystem("", ""));
    }

    @Test
    public void testEqualsNormalizedOnSystem_5() {
        assertTrue(FilenameUtils.equalsNormalizedOnSystem("file.txt", "file.txt"));
    }

    @Test
    public void testEqualsNormalizedOnSystem_6() {
        assertEquals(WINDOWS, FilenameUtils.equalsNormalizedOnSystem("file.txt", "FILE.TXT"));
    }

    @Test
    public void testEqualsNormalizedOnSystem_7() {
        assertTrue(FilenameUtils.equalsNormalizedOnSystem("a\\b\\file.txt", "a/b/file.txt"));
    }

    @Test
    public void testEqualsNormalizedOnSystem_8() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem("a/b/", "a/b"));
    }

    @Test
    public void testEqualsNormalizedOnSystem_9() {
        assertFalse(FilenameUtils.equalsNormalizedOnSystem("//a.html", "//ab.html"));
    }

    @Test
    public void testEqualsOnSystem_1() {
        assertTrue(FilenameUtils.equalsOnSystem(null, null));
    }

    @Test
    public void testEqualsOnSystem_2() {
        assertFalse(FilenameUtils.equalsOnSystem(null, ""));
    }

    @Test
    public void testEqualsOnSystem_3() {
        assertFalse(FilenameUtils.equalsOnSystem("", null));
    }

    @Test
    public void testEqualsOnSystem_4() {
        assertTrue(FilenameUtils.equalsOnSystem("", ""));
    }

    @Test
    public void testEqualsOnSystem_5() {
        assertTrue(FilenameUtils.equalsOnSystem("file.txt", "file.txt"));
    }

    @Test
    public void testEqualsOnSystem_6() {
        assertEquals(WINDOWS, FilenameUtils.equalsOnSystem("file.txt", "FILE.TXT"));
    }

    @Test
    public void testEqualsOnSystem_7() {
        assertFalse(FilenameUtils.equalsOnSystem("a\\b\\file.txt", "a/b/file.txt"));
    }

    @Test
    public void testGetBaseName_1() {
        assertNull(FilenameUtils.getBaseName(null));
    }

    @Test
    public void testGetBaseName_2() {
        assertEquals("noseparator", FilenameUtils.getBaseName("noseparator.inthispath"));
    }

    @Test
    public void testGetBaseName_3() {
        assertEquals("c", FilenameUtils.getBaseName("a/b/c.txt"));
    }

    @Test
    public void testGetBaseName_4() {
        assertEquals("c", FilenameUtils.getBaseName("a/b/c"));
    }

    @Test
    public void testGetBaseName_5() {
        assertEquals("", FilenameUtils.getBaseName("a/b/c/"));
    }

    @Test
    public void testGetBaseName_6() {
        assertEquals("c", FilenameUtils.getBaseName("a\\b\\c"));
    }

    @Test
    public void testGetBaseName_7() {
        assertEquals("file.txt", FilenameUtils.getBaseName("file.txt.bak"));
    }

    @Test
    public void testGetFullPathNoEndSeparator_IO_248_1() {
        assertEquals("/", FilenameUtils.getFullPathNoEndSeparator("/"));
    }

    @Test
    public void testGetFullPathNoEndSeparator_IO_248_2() {
        assertEquals("\\", FilenameUtils.getFullPathNoEndSeparator("\\"));
    }

    @Test
    public void testGetFullPathNoEndSeparator_IO_248_3() {
        assertEquals("/", FilenameUtils.getFullPathNoEndSeparator("/abc"));
    }

    @Test
    public void testGetFullPathNoEndSeparator_IO_248_4() {
        assertEquals("\\", FilenameUtils.getFullPathNoEndSeparator("\\abc"));
    }

    @Test
    public void testGetFullPathNoEndSeparator_IO_248_5() {
        assertEquals("/abc", FilenameUtils.getFullPathNoEndSeparator("/abc/xyz"));
    }

    @Test
    public void testGetFullPathNoEndSeparator_IO_248_6() {
        assertEquals("\\abc", FilenameUtils.getFullPathNoEndSeparator("\\abc\\xyz"));
    }

    @Test
    public void testGetName_1() {
        assertNull(FilenameUtils.getName(null));
    }

    @Test
    public void testGetName_2() {
        assertEquals("noseparator.inthispath", FilenameUtils.getName("noseparator.inthispath"));
    }

    @Test
    public void testGetName_3() {
        assertEquals("c.txt", FilenameUtils.getName("a/b/c.txt"));
    }

    @Test
    public void testGetName_4() {
        assertEquals("c", FilenameUtils.getName("a/b/c"));
    }

    @Test
    public void testGetName_5() {
        assertEquals("", FilenameUtils.getName("a/b/c/"));
    }

    @Test
    public void testGetName_6() {
        assertEquals("c", FilenameUtils.getName("a\\b\\c"));
    }

    @Test
    public void testGetPath_1() {
        assertNull(FilenameUtils.getPath(null));
    }

    @Test
    public void testGetPath_2() {
        assertEquals("", FilenameUtils.getPath("noseparator.inthispath"));
    }

    @Test
    public void testGetPath_3() {
        assertEquals("", FilenameUtils.getPath("/noseparator.inthispath"));
    }

    @Test
    public void testGetPath_4() {
        assertEquals("", FilenameUtils.getPath("\\noseparator.inthispath"));
    }

    @Test
    public void testGetPath_5() {
        assertEquals("a/b/", FilenameUtils.getPath("a/b/c.txt"));
    }

    @Test
    public void testGetPath_6() {
        assertEquals("a/b/", FilenameUtils.getPath("a/b/c"));
    }

    @Test
    public void testGetPath_7() {
        assertEquals("a/b/c/", FilenameUtils.getPath("a/b/c/"));
    }

    @Test
    public void testGetPath_8() {
        assertEquals("a\\b\\", FilenameUtils.getPath("a\\b\\c"));
    }

    @Test
    public void testGetPath_9() {
        assertNull(FilenameUtils.getPath(":"));
    }

    @Test
    public void testGetPath_10() {
        assertNull(FilenameUtils.getPath("1:/a/b/c.txt"));
    }

    @Test
    public void testGetPath_11() {
        assertNull(FilenameUtils.getPath("1:"));
    }

    @Test
    public void testGetPath_12() {
        assertNull(FilenameUtils.getPath("1:a"));
    }

    @Test
    public void testGetPath_13() {
        assertNull(FilenameUtils.getPath("///a/b/c.txt"));
    }

    @Test
    public void testGetPath_14() {
        assertNull(FilenameUtils.getPath("//a"));
    }

    @Test
    public void testGetPath_15() {
        assertEquals("", FilenameUtils.getPath(""));
    }

    @Test
    public void testGetPath_16() {
        assertEquals("", FilenameUtils.getPath("C:"));
    }

    @Test
    public void testGetPath_17() {
        assertEquals("", FilenameUtils.getPath("C:/"));
    }

    @Test
    public void testGetPath_18() {
        assertEquals("", FilenameUtils.getPath("//server/"));
    }

    @Test
    public void testGetPath_19() {
        assertEquals("", FilenameUtils.getPath("~"));
    }

    @Test
    public void testGetPath_20() {
        assertEquals("", FilenameUtils.getPath("~/"));
    }

    @Test
    public void testGetPath_21() {
        assertEquals("", FilenameUtils.getPath("~user"));
    }

    @Test
    public void testGetPath_22() {
        assertEquals("", FilenameUtils.getPath("~user/"));
    }

    @Test
    public void testGetPath_23() {
        assertEquals("a/b/", FilenameUtils.getPath("a/b/c.txt"));
    }

    @Test
    public void testGetPath_24() {
        assertEquals("a/b/", FilenameUtils.getPath("/a/b/c.txt"));
    }

    @Test
    public void testGetPath_25() {
        assertEquals("", FilenameUtils.getPath("C:a"));
    }

    @Test
    public void testGetPath_26() {
        assertEquals("a/b/", FilenameUtils.getPath("C:a/b/c.txt"));
    }

    @Test
    public void testGetPath_27() {
        assertEquals("a/b/", FilenameUtils.getPath("C:/a/b/c.txt"));
    }

    @Test
    public void testGetPath_28() {
        assertEquals("a/b/", FilenameUtils.getPath("//server/a/b/c.txt"));
    }

    @Test
    public void testGetPath_29() {
        assertEquals("a/b/", FilenameUtils.getPath("~/a/b/c.txt"));
    }

    @Test
    public void testGetPath_30() {
        assertEquals("a/b/", FilenameUtils.getPath("~user/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_1() {
        assertNull(FilenameUtils.getPath(null));
    }

    @Test
    public void testGetPathNoEndSeparator_2() {
        assertEquals("", FilenameUtils.getPath("noseparator.inthispath"));
    }

    @Test
    public void testGetPathNoEndSeparator_3() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("/noseparator.inthispath"));
    }

    @Test
    public void testGetPathNoEndSeparator_4() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("\\noseparator.inthispath"));
    }

    @Test
    public void testGetPathNoEndSeparator_5() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_6() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("a/b/c"));
    }

    @Test
    public void testGetPathNoEndSeparator_7() {
        assertEquals("a/b/c", FilenameUtils.getPathNoEndSeparator("a/b/c/"));
    }

    @Test
    public void testGetPathNoEndSeparator_8() {
        assertEquals("a\\b", FilenameUtils.getPathNoEndSeparator("a\\b\\c"));
    }

    @Test
    public void testGetPathNoEndSeparator_9() {
        assertNull(FilenameUtils.getPathNoEndSeparator(":"));
    }

    @Test
    public void testGetPathNoEndSeparator_10() {
        assertNull(FilenameUtils.getPathNoEndSeparator("1:/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_11() {
        assertNull(FilenameUtils.getPathNoEndSeparator("1:"));
    }

    @Test
    public void testGetPathNoEndSeparator_12() {
        assertNull(FilenameUtils.getPathNoEndSeparator("1:a"));
    }

    @Test
    public void testGetPathNoEndSeparator_13() {
        assertNull(FilenameUtils.getPathNoEndSeparator("///a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_14() {
        assertNull(FilenameUtils.getPathNoEndSeparator("//a"));
    }

    @Test
    public void testGetPathNoEndSeparator_15() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator(""));
    }

    @Test
    public void testGetPathNoEndSeparator_16() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("C:"));
    }

    @Test
    public void testGetPathNoEndSeparator_17() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("C:/"));
    }

    @Test
    public void testGetPathNoEndSeparator_18() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("//server/"));
    }

    @Test
    public void testGetPathNoEndSeparator_19() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("~"));
    }

    @Test
    public void testGetPathNoEndSeparator_20() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("~/"));
    }

    @Test
    public void testGetPathNoEndSeparator_21() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("~user"));
    }

    @Test
    public void testGetPathNoEndSeparator_22() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("~user/"));
    }

    @Test
    public void testGetPathNoEndSeparator_23() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_24() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_25() {
        assertEquals("", FilenameUtils.getPathNoEndSeparator("C:a"));
    }

    @Test
    public void testGetPathNoEndSeparator_26() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("C:a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_27() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("C:/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_28() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("//server/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_29() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("~/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator_30() {
        assertEquals("a/b", FilenameUtils.getPathNoEndSeparator("~user/a/b/c.txt"));
    }

    @Test
    public void testIndexOfLastSeparator_1() {
        assertEquals(-1, FilenameUtils.indexOfLastSeparator(null));
    }

    @Test
    public void testIndexOfLastSeparator_2() {
        assertEquals(-1, FilenameUtils.indexOfLastSeparator("noseparator.inthispath"));
    }

    @Test
    public void testIndexOfLastSeparator_3() {
        assertEquals(3, FilenameUtils.indexOfLastSeparator("a/b/c"));
    }

    @Test
    public void testIndexOfLastSeparator_4() {
        assertEquals(3, FilenameUtils.indexOfLastSeparator("a\\b\\c"));
    }

    @Test
    public void testIsExtension_1() {
        assertFalse(FilenameUtils.isExtension(null, (String) null));
    }

    @Test
    public void testIsExtension_2() {
        assertFalse(FilenameUtils.isExtension("file.txt", (String) null));
    }

    @Test
    public void testIsExtension_3() {
        assertTrue(FilenameUtils.isExtension("file", (String) null));
    }

    @Test
    public void testIsExtension_4() {
        assertFalse(FilenameUtils.isExtension("file.txt", ""));
    }

    @Test
    public void testIsExtension_5() {
        assertTrue(FilenameUtils.isExtension("file", ""));
    }

    @Test
    public void testIsExtension_6() {
        assertTrue(FilenameUtils.isExtension("file.txt", "txt"));
    }

    @Test
    public void testIsExtension_7() {
        assertFalse(FilenameUtils.isExtension("file.txt", "rtf"));
    }

    @Test
    public void testIsExtension_8() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", (String) null));
    }

    @Test
    public void testIsExtension_9() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", ""));
    }

    @Test
    public void testIsExtension_10() {
        assertTrue(FilenameUtils.isExtension("a/b/file.txt", "txt"));
    }

    @Test
    public void testIsExtension_11() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", "rtf"));
    }

    @Test
    public void testIsExtension_12() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", (String) null));
    }

    @Test
    public void testIsExtension_13() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", ""));
    }

    @Test
    public void testIsExtension_14() {
        assertTrue(FilenameUtils.isExtension("a.b/file.txt", "txt"));
    }

    @Test
    public void testIsExtension_15() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", "rtf"));
    }

    @Test
    public void testIsExtension_16() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", (String) null));
    }

    @Test
    public void testIsExtension_17() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", ""));
    }

    @Test
    public void testIsExtension_18() {
        assertTrue(FilenameUtils.isExtension("a\\b\\file.txt", "txt"));
    }

    @Test
    public void testIsExtension_19() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", "rtf"));
    }

    @Test
    public void testIsExtension_20() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", (String) null));
    }

    @Test
    public void testIsExtension_21() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", ""));
    }

    @Test
    public void testIsExtension_22() {
        assertTrue(FilenameUtils.isExtension("a.b\\file.txt", "txt"));
    }

    @Test
    public void testIsExtension_23() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", "rtf"));
    }

    @Test
    public void testIsExtension_24() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", "TXT"));
    }

    @Test
    public void testIsExtensionCollection_1() {
        assertFalse(FilenameUtils.isExtension(null, (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_2() {
        assertFalse(FilenameUtils.isExtension("file.txt", (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_3() {
        assertTrue(FilenameUtils.isExtension("file", (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_4() {
        assertFalse(FilenameUtils.isExtension("file.txt", new ArrayList<>()));
    }

    @Test
    public void testIsExtensionCollection_5() {
        assertTrue(FilenameUtils.isExtension("file.txt", new ArrayList<>(Arrays.asList("txt"))));
    }

    @Test
    public void testIsExtensionCollection_6() {
        assertFalse(FilenameUtils.isExtension("file.txt", new ArrayList<>(Arrays.asList("rtf"))));
    }

    @Test
    public void testIsExtensionCollection_7() {
        assertTrue(FilenameUtils.isExtension("file", new ArrayList<>(Arrays.asList("rtf", ""))));
    }

    @Test
    public void testIsExtensionCollection_8() {
        assertTrue(FilenameUtils.isExtension("file.txt", new ArrayList<>(Arrays.asList("rtf", "txt"))));
    }

    @Test
    public void testIsExtensionCollection_9() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_10() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", new ArrayList<>()));
    }

    @Test
    public void testIsExtensionCollection_11() {
        assertTrue(FilenameUtils.isExtension("a/b/file.txt", new ArrayList<>(Arrays.asList("txt"))));
    }

    @Test
    public void testIsExtensionCollection_12() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", new ArrayList<>(Arrays.asList("rtf"))));
    }

    @Test
    public void testIsExtensionCollection_13() {
        assertTrue(FilenameUtils.isExtension("a/b/file.txt", new ArrayList<>(Arrays.asList("rtf", "txt"))));
    }

    @Test
    public void testIsExtensionCollection_14() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_15() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", new ArrayList<>()));
    }

    @Test
    public void testIsExtensionCollection_16() {
        assertTrue(FilenameUtils.isExtension("a.b/file.txt", new ArrayList<>(Arrays.asList("txt"))));
    }

    @Test
    public void testIsExtensionCollection_17() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", new ArrayList<>(Arrays.asList("rtf"))));
    }

    @Test
    public void testIsExtensionCollection_18() {
        assertTrue(FilenameUtils.isExtension("a.b/file.txt", new ArrayList<>(Arrays.asList("rtf", "txt"))));
    }

    @Test
    public void testIsExtensionCollection_19() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_20() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", new ArrayList<>()));
    }

    @Test
    public void testIsExtensionCollection_21() {
        assertTrue(FilenameUtils.isExtension("a\\b\\file.txt", new ArrayList<>(Arrays.asList("txt"))));
    }

    @Test
    public void testIsExtensionCollection_22() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", new ArrayList<>(Arrays.asList("rtf"))));
    }

    @Test
    public void testIsExtensionCollection_23() {
        assertTrue(FilenameUtils.isExtension("a\\b\\file.txt", new ArrayList<>(Arrays.asList("rtf", "txt"))));
    }

    @Test
    public void testIsExtensionCollection_24() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", (Collection<String>) null));
    }

    @Test
    public void testIsExtensionCollection_25() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", new ArrayList<>()));
    }

    @Test
    public void testIsExtensionCollection_26() {
        assertTrue(FilenameUtils.isExtension("a.b\\file.txt", new ArrayList<>(Arrays.asList("txt"))));
    }

    @Test
    public void testIsExtensionCollection_27() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", new ArrayList<>(Arrays.asList("rtf"))));
    }

    @Test
    public void testIsExtensionCollection_28() {
        assertTrue(FilenameUtils.isExtension("a.b\\file.txt", new ArrayList<>(Arrays.asList("rtf", "txt"))));
    }

    @Test
    public void testIsExtensionCollection_29() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", new ArrayList<>(Arrays.asList("TXT"))));
    }

    @Test
    public void testIsExtensionCollection_30() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", new ArrayList<>(Arrays.asList("TXT", "RTF"))));
    }

    @Test
    public void testIsExtensionVarArgs_1() {
        assertTrue(FilenameUtils.isExtension("file.txt", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_2() {
        assertFalse(FilenameUtils.isExtension("file.txt", "rtf"));
    }

    @Test
    public void testIsExtensionVarArgs_3() {
        assertTrue(FilenameUtils.isExtension("file", "rtf", ""));
    }

    @Test
    public void testIsExtensionVarArgs_4() {
        assertTrue(FilenameUtils.isExtension("file.txt", "rtf", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_5() {
        assertTrue(FilenameUtils.isExtension("a/b/file.txt", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_6() {
        assertFalse(FilenameUtils.isExtension("a/b/file.txt", "rtf"));
    }

    @Test
    public void testIsExtensionVarArgs_7() {
        assertTrue(FilenameUtils.isExtension("a/b/file.txt", "rtf", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_8() {
        assertTrue(FilenameUtils.isExtension("a.b/file.txt", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_9() {
        assertFalse(FilenameUtils.isExtension("a.b/file.txt", "rtf"));
    }

    @Test
    public void testIsExtensionVarArgs_10() {
        assertTrue(FilenameUtils.isExtension("a.b/file.txt", "rtf", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_11() {
        assertTrue(FilenameUtils.isExtension("a\\b\\file.txt", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_12() {
        assertFalse(FilenameUtils.isExtension("a\\b\\file.txt", "rtf"));
    }

    @Test
    public void testIsExtensionVarArgs_13() {
        assertTrue(FilenameUtils.isExtension("a\\b\\file.txt", "rtf", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_14() {
        assertTrue(FilenameUtils.isExtension("a.b\\file.txt", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_15() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", "rtf"));
    }

    @Test
    public void testIsExtensionVarArgs_16() {
        assertTrue(FilenameUtils.isExtension("a.b\\file.txt", "rtf", "txt"));
    }

    @Test
    public void testIsExtensionVarArgs_17() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", "TXT"));
    }

    @Test
    public void testIsExtensionVarArgs_18() {
        assertFalse(FilenameUtils.isExtension("a.b\\file.txt", "TXT", "RTF"));
    }

    @Test
    public void testNormalize_1() {
        assertNull(FilenameUtils.normalize(null));
    }

    @Test
    public void testNormalize_2() {
        assertNull(FilenameUtils.normalize(":"));
    }

    @Test
    public void testNormalize_3() {
        assertNull(FilenameUtils.normalize("1:\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_4() {
        assertNull(FilenameUtils.normalize("1:"));
    }

    @Test
    public void testNormalize_5() {
        assertNull(FilenameUtils.normalize("1:a"));
    }

    @Test
    public void testNormalize_6() {
        assertNull(FilenameUtils.normalize("\\\\\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_7() {
        assertNull(FilenameUtils.normalize("\\\\a"));
    }

    @Test
    public void testNormalize_8() {
        assertEquals("a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("a\\b/c.txt"));
    }

    @Test
    public void testNormalize_9() {
        assertEquals("" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\a\\b/c.txt"));
    }

    @Test
    public void testNormalize_10() {
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("C:\\a\\b/c.txt"));
    }

    @Test
    public void testNormalize_11() {
        assertEquals("" + SEP + "" + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\server\\a\\b/c.txt"));
    }

    @Test
    public void testNormalize_12() {
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("~\\a\\b/c.txt"));
    }

    @Test
    public void testNormalize_13() {
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("~user\\a\\b/c.txt"));
    }

    @Test
    public void testNormalize_14() {
        assertEquals("a" + SEP + "c", FilenameUtils.normalize("a/b/../c"));
    }

    @Test
    public void testNormalize_15() {
        assertEquals("c", FilenameUtils.normalize("a/b/../../c"));
    }

    @Test
    public void testNormalize_16() {
        assertEquals("c" + SEP, FilenameUtils.normalize("a/b/../../c/"));
    }

    @Test
    public void testNormalize_17() {
        assertNull(FilenameUtils.normalize("a/b/../../../c"));
    }

    @Test
    public void testNormalize_18() {
        assertEquals("a" + SEP, FilenameUtils.normalize("a/b/.."));
    }

    @Test
    public void testNormalize_19() {
        assertEquals("a" + SEP, FilenameUtils.normalize("a/b/../"));
    }

    @Test
    public void testNormalize_20() {
        assertEquals("", FilenameUtils.normalize("a/b/../.."));
    }

    @Test
    public void testNormalize_21() {
        assertEquals("", FilenameUtils.normalize("a/b/../../"));
    }

    @Test
    public void testNormalize_22() {
        assertNull(FilenameUtils.normalize("a/b/../../.."));
    }

    @Test
    public void testNormalize_23() {
        assertEquals("a" + SEP + "d", FilenameUtils.normalize("a/b/../c/../d"));
    }

    @Test
    public void testNormalize_24() {
        assertEquals("a" + SEP + "d" + SEP, FilenameUtils.normalize("a/b/../c/../d/"));
    }

    @Test
    public void testNormalize_25() {
        assertEquals("a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("a/b//d"));
    }

    @Test
    public void testNormalize_26() {
        assertEquals("a" + SEP + "b" + SEP, FilenameUtils.normalize("a/b/././."));
    }

    @Test
    public void testNormalize_27() {
        assertEquals("a" + SEP + "b" + SEP, FilenameUtils.normalize("a/b/./././"));
    }

    @Test
    public void testNormalize_28() {
        assertEquals("a" + SEP, FilenameUtils.normalize("./a/"));
    }

    @Test
    public void testNormalize_29() {
        assertEquals("a", FilenameUtils.normalize("./a"));
    }

    @Test
    public void testNormalize_30() {
        assertEquals("", FilenameUtils.normalize("./"));
    }

    @Test
    public void testNormalize_31() {
        assertEquals("", FilenameUtils.normalize("."));
    }

    @Test
    public void testNormalize_32() {
        assertNull(FilenameUtils.normalize("../a"));
    }

    @Test
    public void testNormalize_33() {
        assertNull(FilenameUtils.normalize(".."));
    }

    @Test
    public void testNormalize_34() {
        assertEquals("", FilenameUtils.normalize(""));
    }

    @Test
    public void testNormalize_35() {
        assertEquals(SEP + "a", FilenameUtils.normalize("/a"));
    }

    @Test
    public void testNormalize_36() {
        assertEquals(SEP + "a" + SEP, FilenameUtils.normalize("/a/"));
    }

    @Test
    public void testNormalize_37() {
        assertEquals(SEP + "a" + SEP + "c", FilenameUtils.normalize("/a/b/../c"));
    }

    @Test
    public void testNormalize_38() {
        assertEquals(SEP + "c", FilenameUtils.normalize("/a/b/../../c"));
    }

    @Test
    public void testNormalize_39() {
        assertNull(FilenameUtils.normalize("/a/b/../../../c"));
    }

    @Test
    public void testNormalize_40() {
        assertEquals(SEP + "a" + SEP, FilenameUtils.normalize("/a/b/.."));
    }

    @Test
    public void testNormalize_41() {
        assertEquals(SEP + "", FilenameUtils.normalize("/a/b/../.."));
    }

    @Test
    public void testNormalize_42() {
        assertNull(FilenameUtils.normalize("/a/b/../../.."));
    }

    @Test
    public void testNormalize_43() {
        assertEquals(SEP + "a" + SEP + "d", FilenameUtils.normalize("/a/b/../c/../d"));
    }

    @Test
    public void testNormalize_44() {
        assertEquals(SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("/a/b//d"));
    }

    @Test
    public void testNormalize_45() {
        assertEquals(SEP + "a" + SEP + "b" + SEP, FilenameUtils.normalize("/a/b/././."));
    }

    @Test
    public void testNormalize_46() {
        assertEquals(SEP + "a", FilenameUtils.normalize("/./a"));
    }

    @Test
    public void testNormalize_47() {
        assertEquals(SEP + "", FilenameUtils.normalize("/./"));
    }

    @Test
    public void testNormalize_48() {
        assertEquals(SEP + "", FilenameUtils.normalize("/."));
    }

    @Test
    public void testNormalize_49() {
        assertNull(FilenameUtils.normalize("/../a"));
    }

    @Test
    public void testNormalize_50() {
        assertNull(FilenameUtils.normalize("/.."));
    }

    @Test
    public void testNormalize_51() {
        assertEquals(SEP + "", FilenameUtils.normalize("/"));
    }

    @Test
    public void testNormalize_52() {
        assertEquals("~" + SEP + "a", FilenameUtils.normalize("~/a"));
    }

    @Test
    public void testNormalize_53() {
        assertEquals("~" + SEP + "a" + SEP, FilenameUtils.normalize("~/a/"));
    }

    @Test
    public void testNormalize_54() {
        assertEquals("~" + SEP + "a" + SEP + "c", FilenameUtils.normalize("~/a/b/../c"));
    }

    @Test
    public void testNormalize_55() {
        assertEquals("~" + SEP + "c", FilenameUtils.normalize("~/a/b/../../c"));
    }

    @Test
    public void testNormalize_56() {
        assertNull(FilenameUtils.normalize("~/a/b/../../../c"));
    }

    @Test
    public void testNormalize_57() {
        assertEquals("~" + SEP + "a" + SEP, FilenameUtils.normalize("~/a/b/.."));
    }

    @Test
    public void testNormalize_58() {
        assertEquals("~" + SEP + "", FilenameUtils.normalize("~/a/b/../.."));
    }

    @Test
    public void testNormalize_59() {
        assertNull(FilenameUtils.normalize("~/a/b/../../.."));
    }

    @Test
    public void testNormalize_60() {
        assertEquals("~" + SEP + "a" + SEP + "d", FilenameUtils.normalize("~/a/b/../c/../d"));
    }

    @Test
    public void testNormalize_61() {
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("~/a/b//d"));
    }

    @Test
    public void testNormalize_62() {
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP, FilenameUtils.normalize("~/a/b/././."));
    }

    @Test
    public void testNormalize_63() {
        assertEquals("~" + SEP + "a", FilenameUtils.normalize("~/./a"));
    }

    @Test
    public void testNormalize_64() {
        assertEquals("~" + SEP, FilenameUtils.normalize("~/./"));
    }

    @Test
    public void testNormalize_65() {
        assertEquals("~" + SEP, FilenameUtils.normalize("~/."));
    }

    @Test
    public void testNormalize_66() {
        assertNull(FilenameUtils.normalize("~/../a"));
    }

    @Test
    public void testNormalize_67() {
        assertNull(FilenameUtils.normalize("~/.."));
    }

    @Test
    public void testNormalize_68() {
        assertEquals("~" + SEP, FilenameUtils.normalize("~/"));
    }

    @Test
    public void testNormalize_69() {
        assertEquals("~" + SEP, FilenameUtils.normalize("~"));
    }

    @Test
    public void testNormalize_70() {
        assertEquals("~user" + SEP + "a", FilenameUtils.normalize("~user/a"));
    }

    @Test
    public void testNormalize_71() {
        assertEquals("~user" + SEP + "a" + SEP, FilenameUtils.normalize("~user/a/"));
    }

    @Test
    public void testNormalize_72() {
        assertEquals("~user" + SEP + "a" + SEP + "c", FilenameUtils.normalize("~user/a/b/../c"));
    }

    @Test
    public void testNormalize_73() {
        assertEquals("~user" + SEP + "c", FilenameUtils.normalize("~user/a/b/../../c"));
    }

    @Test
    public void testNormalize_74() {
        assertNull(FilenameUtils.normalize("~user/a/b/../../../c"));
    }

    @Test
    public void testNormalize_75() {
        assertEquals("~user" + SEP + "a" + SEP, FilenameUtils.normalize("~user/a/b/.."));
    }

    @Test
    public void testNormalize_76() {
        assertEquals("~user" + SEP + "", FilenameUtils.normalize("~user/a/b/../.."));
    }

    @Test
    public void testNormalize_77() {
        assertNull(FilenameUtils.normalize("~user/a/b/../../.."));
    }

    @Test
    public void testNormalize_78() {
        assertEquals("~user" + SEP + "a" + SEP + "d", FilenameUtils.normalize("~user/a/b/../c/../d"));
    }

    @Test
    public void testNormalize_79() {
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("~user/a/b//d"));
    }

    @Test
    public void testNormalize_80() {
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP, FilenameUtils.normalize("~user/a/b/././."));
    }

    @Test
    public void testNormalize_81() {
        assertEquals("~user" + SEP + "a", FilenameUtils.normalize("~user/./a"));
    }

    @Test
    public void testNormalize_82() {
        assertEquals("~user" + SEP + "", FilenameUtils.normalize("~user/./"));
    }

    @Test
    public void testNormalize_83() {
        assertEquals("~user" + SEP + "", FilenameUtils.normalize("~user/."));
    }

    @Test
    public void testNormalize_84() {
        assertNull(FilenameUtils.normalize("~user/../a"));
    }

    @Test
    public void testNormalize_85() {
        assertNull(FilenameUtils.normalize("~user/.."));
    }

    @Test
    public void testNormalize_86() {
        assertEquals("~user" + SEP, FilenameUtils.normalize("~user/"));
    }

    @Test
    public void testNormalize_87() {
        assertEquals("~user" + SEP, FilenameUtils.normalize("~user"));
    }

    @Test
    public void testNormalize_88() {
        assertEquals("C:" + SEP + "a", FilenameUtils.normalize("C:/a"));
    }

    @Test
    public void testNormalize_89() {
        assertEquals("C:" + SEP + "a" + SEP, FilenameUtils.normalize("C:/a/"));
    }

    @Test
    public void testNormalize_90() {
        assertEquals("C:" + SEP + "a" + SEP + "c", FilenameUtils.normalize("C:/a/b/../c"));
    }

    @Test
    public void testNormalize_91() {
        assertEquals("C:" + SEP + "c", FilenameUtils.normalize("C:/a/b/../../c"));
    }

    @Test
    public void testNormalize_92() {
        assertNull(FilenameUtils.normalize("C:/a/b/../../../c"));
    }

    @Test
    public void testNormalize_93() {
        assertEquals("C:" + SEP + "a" + SEP, FilenameUtils.normalize("C:/a/b/.."));
    }

    @Test
    public void testNormalize_94() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalize("C:/a/b/../.."));
    }

    @Test
    public void testNormalize_95() {
        assertNull(FilenameUtils.normalize("C:/a/b/../../.."));
    }

    @Test
    public void testNormalize_96() {
        assertEquals("C:" + SEP + "a" + SEP + "d", FilenameUtils.normalize("C:/a/b/../c/../d"));
    }

    @Test
    public void testNormalize_97() {
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("C:/a/b//d"));
    }

    @Test
    public void testNormalize_98() {
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP, FilenameUtils.normalize("C:/a/b/././."));
    }

    @Test
    public void testNormalize_99() {
        assertEquals("C:" + SEP + "a", FilenameUtils.normalize("C:/./a"));
    }

    @Test
    public void testNormalize_100() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalize("C:/./"));
    }

    @Test
    public void testNormalize_101() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalize("C:/."));
    }

    @Test
    public void testNormalize_102() {
        assertNull(FilenameUtils.normalize("C:/../a"));
    }

    @Test
    public void testNormalize_103() {
        assertNull(FilenameUtils.normalize("C:/.."));
    }

    @Test
    public void testNormalize_104() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalize("C:/"));
    }

    @Test
    public void testNormalize_105() {
        assertEquals("C:" + "a", FilenameUtils.normalize("C:a"));
    }

    @Test
    public void testNormalize_106() {
        assertEquals("C:" + "a" + SEP, FilenameUtils.normalize("C:a/"));
    }

    @Test
    public void testNormalize_107() {
        assertEquals("C:" + "a" + SEP + "c", FilenameUtils.normalize("C:a/b/../c"));
    }

    @Test
    public void testNormalize_108() {
        assertEquals("C:" + "c", FilenameUtils.normalize("C:a/b/../../c"));
    }

    @Test
    public void testNormalize_109() {
        assertNull(FilenameUtils.normalize("C:a/b/../../../c"));
    }

    @Test
    public void testNormalize_110() {
        assertEquals("C:" + "a" + SEP, FilenameUtils.normalize("C:a/b/.."));
    }

    @Test
    public void testNormalize_111() {
        assertEquals("C:" + "", FilenameUtils.normalize("C:a/b/../.."));
    }

    @Test
    public void testNormalize_112() {
        assertNull(FilenameUtils.normalize("C:a/b/../../.."));
    }

    @Test
    public void testNormalize_113() {
        assertEquals("C:" + "a" + SEP + "d", FilenameUtils.normalize("C:a/b/../c/../d"));
    }

    @Test
    public void testNormalize_114() {
        assertEquals("C:" + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("C:a/b//d"));
    }

    @Test
    public void testNormalize_115() {
        assertEquals("C:" + "a" + SEP + "b" + SEP, FilenameUtils.normalize("C:a/b/././."));
    }

    @Test
    public void testNormalize_116() {
        assertEquals("C:" + "a", FilenameUtils.normalize("C:./a"));
    }

    @Test
    public void testNormalize_117() {
        assertEquals("C:" + "", FilenameUtils.normalize("C:./"));
    }

    @Test
    public void testNormalize_118() {
        assertEquals("C:" + "", FilenameUtils.normalize("C:."));
    }

    @Test
    public void testNormalize_119() {
        assertNull(FilenameUtils.normalize("C:../a"));
    }

    @Test
    public void testNormalize_120() {
        assertNull(FilenameUtils.normalize("C:.."));
    }

    @Test
    public void testNormalize_121() {
        assertEquals("C:" + "", FilenameUtils.normalize("C:"));
    }

    @Test
    public void testNormalize_122() {
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtils.normalize("//server/a"));
    }

    @Test
    public void testNormalize_123() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP, FilenameUtils.normalize("//server/a/"));
    }

    @Test
    public void testNormalize_124() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "c", FilenameUtils.normalize("//server/a/b/../c"));
    }

    @Test
    public void testNormalize_125() {
        assertEquals(SEP + SEP + "server" + SEP + "c", FilenameUtils.normalize("//server/a/b/../../c"));
    }

    @Test
    public void testNormalize_126() {
        assertNull(FilenameUtils.normalize("//server/a/b/../../../c"));
    }

    @Test
    public void testNormalize_127() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP, FilenameUtils.normalize("//server/a/b/.."));
    }

    @Test
    public void testNormalize_128() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalize("//server/a/b/../.."));
    }

    @Test
    public void testNormalize_129() {
        assertNull(FilenameUtils.normalize("//server/a/b/../../.."));
    }

    @Test
    public void testNormalize_130() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "d", FilenameUtils.normalize("//server/a/b/../c/../d"));
    }

    @Test
    public void testNormalize_131() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalize("//server/a/b//d"));
    }

    @Test
    public void testNormalize_132() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP, FilenameUtils.normalize("//server/a/b/././."));
    }

    @Test
    public void testNormalize_133() {
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtils.normalize("//server/./a"));
    }

    @Test
    public void testNormalize_134() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalize("//server/./"));
    }

    @Test
    public void testNormalize_135() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalize("//server/."));
    }

    @Test
    public void testNormalize_136() {
        assertNull(FilenameUtils.normalize("//server/../a"));
    }

    @Test
    public void testNormalize_137() {
        assertNull(FilenameUtils.normalize("//server/.."));
    }

    @Test
    public void testNormalize_138() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalize("//server/"));
    }

    @Test
    public void testNormalize_139() {
        assertEquals(SEP + SEP + "127.0.0.1" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\127.0.0.1\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_140() {
        assertEquals(SEP + SEP + "::1" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\::1\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_141() {
        assertEquals(SEP + SEP + "1::" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\1::\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_142() {
        assertEquals(SEP + SEP + "server.example.org" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\server.example.org\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_143() {
        assertEquals(SEP + SEP + "server.sub.example.org" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\server.sub.example.org\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_144() {
        assertEquals(SEP + SEP + "server." + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\server.\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_145() {
        assertEquals(SEP + SEP + "1::127.0.0.1" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\1::127.0.0.1\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_146() {
        assertEquals(SEP + SEP + "127.0.0.256" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\127.0.0.256\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_147() {
        assertEquals(SEP + SEP + "127.0.0.01" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalize("\\\\127.0.0.01\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_148() {
        assertNull(FilenameUtils.normalize("\\\\-server\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_149() {
        assertNull(FilenameUtils.normalize("\\\\.\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_150() {
        assertNull(FilenameUtils.normalize("\\\\..\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_151() {
        assertNull(FilenameUtils.normalize("\\\\127.0..1\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_152() {
        assertNull(FilenameUtils.normalize("\\\\::1::2\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_153() {
        assertNull(FilenameUtils.normalize("\\\\:1\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_154() {
        assertNull(FilenameUtils.normalize("\\\\1:\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_155() {
        assertNull(FilenameUtils.normalize("\\\\1:2:3:4:5:6:7:8:9\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_156() {
        assertNull(FilenameUtils.normalize("\\\\g:2:3:4:5:6:7:8\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_157() {
        assertNull(FilenameUtils.normalize("\\\\1ffff:2:3:4:5:6:7:8\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_158() {
        assertNull(FilenameUtils.normalize("\\\\1:2\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalize_159() {
        assertNull(FilenameUtils.normalize("//../foo"));
    }

    @Test
    public void testNormalize_160() {
        assertNull(FilenameUtils.normalize("\\\\..\\foo"));
    }

    @Test
    public void testNormalizeFromJavaDoc_1() {
        assertEquals(SEP + "foo" + SEP, FilenameUtils.normalize("/foo//"));
    }

    @Test
    public void testNormalizeFromJavaDoc_2() {
        assertEquals(SEP + "foo" + SEP, FilenameUtils.normalize(SEP + "foo" + SEP + "." + SEP));
    }

    @Test
    public void testNormalizeFromJavaDoc_3() {
        assertEquals(SEP + "bar", FilenameUtils.normalize(SEP + "foo" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_4() {
        assertEquals(SEP + "bar" + SEP, FilenameUtils.normalize(SEP + "foo" + SEP + ".." + SEP + "bar" + SEP));
    }

    @Test
    public void testNormalizeFromJavaDoc_5() {
        assertEquals(SEP + "baz", FilenameUtils.normalize(SEP + "foo" + SEP + ".." + SEP + "bar" + SEP + ".." + SEP + "baz"));
    }

    @Test
    public void testNormalizeFromJavaDoc_6() {
        assertEquals(SEP + SEP + "foo" + SEP + "bar", FilenameUtils.normalize("//foo//./bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_7() {
        assertNull(FilenameUtils.normalize(SEP + ".." + SEP));
    }

    @Test
    public void testNormalizeFromJavaDoc_8() {
        assertNull(FilenameUtils.normalize(".." + SEP + "foo"));
    }

    @Test
    public void testNormalizeFromJavaDoc_9() {
        assertEquals("foo" + SEP, FilenameUtils.normalize("foo" + SEP + "bar" + SEP + ".."));
    }

    @Test
    public void testNormalizeFromJavaDoc_10() {
        assertNull(FilenameUtils.normalize("foo" + SEP + ".." + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_11() {
        assertEquals("bar", FilenameUtils.normalize("foo" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_12() {
        assertEquals(SEP + SEP + "server" + SEP + "bar", FilenameUtils.normalize(SEP + SEP + "server" + SEP + "foo" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_13() {
        assertNull(FilenameUtils.normalize(SEP + SEP + "server" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_14() {
        assertEquals("C:" + SEP + "bar", FilenameUtils.normalize("C:" + SEP + "foo" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_15() {
        assertNull(FilenameUtils.normalize("C:" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_16() {
        assertEquals("~" + SEP + "bar" + SEP, FilenameUtils.normalize("~" + SEP + "foo" + SEP + ".." + SEP + "bar" + SEP));
    }

    @Test
    public void testNormalizeFromJavaDoc_17() {
        assertNull(FilenameUtils.normalize("~" + SEP + ".." + SEP + "bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_18() {
        assertEquals(SEP + SEP + "foo" + SEP + "bar", FilenameUtils.normalize("//foo//./bar"));
    }

    @Test
    public void testNormalizeFromJavaDoc_19() {
        assertEquals(SEP + SEP + "foo" + SEP + "bar", FilenameUtils.normalize("\\\\foo\\\\.\\bar"));
    }

    @Test
    public void testNormalizeNoEndSeparator_1() {
        assertNull(FilenameUtils.normalizeNoEndSeparator(null));
    }

    @Test
    public void testNormalizeNoEndSeparator_2() {
        assertNull(FilenameUtils.normalizeNoEndSeparator(":"));
    }

    @Test
    public void testNormalizeNoEndSeparator_3() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("1:\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_4() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("1:"));
    }

    @Test
    public void testNormalizeNoEndSeparator_5() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("1:a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_6() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("\\\\\\a\\b\\c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_7() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("\\\\a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_8() {
        assertEquals("a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("a\\b/c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_9() {
        assertEquals("" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("\\a\\b/c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_10() {
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("C:\\a\\b/c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_11() {
        assertEquals("" + SEP + "" + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("\\\\server\\a\\b/c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_12() {
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("~\\a\\b/c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_13() {
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("~user\\a\\b/c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_14() {
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt", FilenameUtils.normalizeNoEndSeparator("C:\\\\a\\\\b\\\\c.txt"));
    }

    @Test
    public void testNormalizeNoEndSeparator_15() {
        assertEquals("a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_16() {
        assertEquals("c", FilenameUtils.normalizeNoEndSeparator("a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_17() {
        assertEquals("c", FilenameUtils.normalizeNoEndSeparator("a/b/../../c/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_18() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_19() {
        assertEquals("a", FilenameUtils.normalizeNoEndSeparator("a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_20() {
        assertEquals("a", FilenameUtils.normalizeNoEndSeparator("a/b/../"));
    }

    @Test
    public void testNormalizeNoEndSeparator_21() {
        assertEquals("", FilenameUtils.normalizeNoEndSeparator("a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_22() {
        assertEquals("", FilenameUtils.normalizeNoEndSeparator("a/b/../../"));
    }

    @Test
    public void testNormalizeNoEndSeparator_23() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_24() {
        assertEquals("a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_25() {
        assertEquals("a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("a/b/../c/../d/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_26() {
        assertEquals("a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_27() {
        assertEquals("a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_28() {
        assertEquals("a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("a/b/./././"));
    }

    @Test
    public void testNormalizeNoEndSeparator_29() {
        assertEquals("a", FilenameUtils.normalizeNoEndSeparator("./a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_30() {
        assertEquals("a", FilenameUtils.normalizeNoEndSeparator("./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_31() {
        assertEquals("", FilenameUtils.normalizeNoEndSeparator("./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_32() {
        assertEquals("", FilenameUtils.normalizeNoEndSeparator("."));
    }

    @Test
    public void testNormalizeNoEndSeparator_33() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_34() {
        assertNull(FilenameUtils.normalizeNoEndSeparator(".."));
    }

    @Test
    public void testNormalizeNoEndSeparator_35() {
        assertEquals("", FilenameUtils.normalizeNoEndSeparator(""));
    }

    @Test
    public void testNormalizeNoEndSeparator_36() {
        assertEquals(SEP + "a", FilenameUtils.normalizeNoEndSeparator("/a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_37() {
        assertEquals(SEP + "a", FilenameUtils.normalizeNoEndSeparator("/a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_38() {
        assertEquals(SEP + "a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("/a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_39() {
        assertEquals(SEP + "c", FilenameUtils.normalizeNoEndSeparator("/a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_40() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("/a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_41() {
        assertEquals(SEP + "a", FilenameUtils.normalizeNoEndSeparator("/a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_42() {
        assertEquals(SEP + "", FilenameUtils.normalizeNoEndSeparator("/a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_43() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("/a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_44() {
        assertEquals(SEP + "a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("/a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_45() {
        assertEquals(SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("/a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_46() {
        assertEquals(SEP + "a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("/a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_47() {
        assertEquals(SEP + "a", FilenameUtils.normalizeNoEndSeparator("/./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_48() {
        assertEquals(SEP + "", FilenameUtils.normalizeNoEndSeparator("/./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_49() {
        assertEquals(SEP + "", FilenameUtils.normalizeNoEndSeparator("/."));
    }

    @Test
    public void testNormalizeNoEndSeparator_50() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("/../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_51() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_52() {
        assertEquals(SEP + "", FilenameUtils.normalizeNoEndSeparator("/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_53() {
        assertEquals("~" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~/a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_54() {
        assertEquals("~" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~/a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_55() {
        assertEquals("~" + SEP + "a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("~/a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_56() {
        assertEquals("~" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("~/a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_57() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~/a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_58() {
        assertEquals("~" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~/a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_59() {
        assertEquals("~" + SEP + "", FilenameUtils.normalizeNoEndSeparator("~/a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_60() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~/a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_61() {
        assertEquals("~" + SEP + "a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("~/a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_62() {
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("~/a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_63() {
        assertEquals("~" + SEP + "a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("~/a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_64() {
        assertEquals("~" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~/./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_65() {
        assertEquals("~" + SEP, FilenameUtils.normalizeNoEndSeparator("~/./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_66() {
        assertEquals("~" + SEP, FilenameUtils.normalizeNoEndSeparator("~/."));
    }

    @Test
    public void testNormalizeNoEndSeparator_67() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~/../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_68() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_69() {
        assertEquals("~" + SEP, FilenameUtils.normalizeNoEndSeparator("~/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_70() {
        assertEquals("~" + SEP, FilenameUtils.normalizeNoEndSeparator("~"));
    }

    @Test
    public void testNormalizeNoEndSeparator_71() {
        assertEquals("~user" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~user/a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_72() {
        assertEquals("~user" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~user/a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_73() {
        assertEquals("~user" + SEP + "a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("~user/a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_74() {
        assertEquals("~user" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("~user/a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_75() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~user/a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_76() {
        assertEquals("~user" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~user/a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_77() {
        assertEquals("~user" + SEP + "", FilenameUtils.normalizeNoEndSeparator("~user/a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_78() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~user/a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_79() {
        assertEquals("~user" + SEP + "a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("~user/a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_80() {
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("~user/a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_81() {
        assertEquals("~user" + SEP + "a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("~user/a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_82() {
        assertEquals("~user" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("~user/./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_83() {
        assertEquals("~user" + SEP + "", FilenameUtils.normalizeNoEndSeparator("~user/./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_84() {
        assertEquals("~user" + SEP + "", FilenameUtils.normalizeNoEndSeparator("~user/."));
    }

    @Test
    public void testNormalizeNoEndSeparator_85() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~user/../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_86() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("~user/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_87() {
        assertEquals("~user" + SEP, FilenameUtils.normalizeNoEndSeparator("~user/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_88() {
        assertEquals("~user" + SEP, FilenameUtils.normalizeNoEndSeparator("~user"));
    }

    @Test
    public void testNormalizeNoEndSeparator_89() {
        assertEquals("C:" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("C:/a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_90() {
        assertEquals("C:" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("C:/a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_91() {
        assertEquals("C:" + SEP + "a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("C:/a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_92() {
        assertEquals("C:" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("C:/a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_93() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:/a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_94() {
        assertEquals("C:" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("C:/a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_95() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalizeNoEndSeparator("C:/a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_96() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:/a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_97() {
        assertEquals("C:" + SEP + "a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("C:/a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_98() {
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("C:/a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_99() {
        assertEquals("C:" + SEP + "a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("C:/a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_100() {
        assertEquals("C:" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("C:/./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_101() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalizeNoEndSeparator("C:/./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_102() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalizeNoEndSeparator("C:/."));
    }

    @Test
    public void testNormalizeNoEndSeparator_103() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:/../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_104() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_105() {
        assertEquals("C:" + SEP + "", FilenameUtils.normalizeNoEndSeparator("C:/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_106() {
        assertEquals("C:" + "a", FilenameUtils.normalizeNoEndSeparator("C:a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_107() {
        assertEquals("C:" + "a", FilenameUtils.normalizeNoEndSeparator("C:a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_108() {
        assertEquals("C:" + "a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("C:a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_109() {
        assertEquals("C:" + "c", FilenameUtils.normalizeNoEndSeparator("C:a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_110() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_111() {
        assertEquals("C:" + "a", FilenameUtils.normalizeNoEndSeparator("C:a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_112() {
        assertEquals("C:" + "", FilenameUtils.normalizeNoEndSeparator("C:a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_113() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_114() {
        assertEquals("C:" + "a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("C:a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_115() {
        assertEquals("C:" + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("C:a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_116() {
        assertEquals("C:" + "a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("C:a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_117() {
        assertEquals("C:" + "a", FilenameUtils.normalizeNoEndSeparator("C:./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_118() {
        assertEquals("C:" + "", FilenameUtils.normalizeNoEndSeparator("C:./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_119() {
        assertEquals("C:" + "", FilenameUtils.normalizeNoEndSeparator("C:."));
    }

    @Test
    public void testNormalizeNoEndSeparator_120() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_121() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("C:.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_122() {
        assertEquals("C:" + "", FilenameUtils.normalizeNoEndSeparator("C:"));
    }

    @Test
    public void testNormalizeNoEndSeparator_123() {
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("//server/a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_124() {
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("//server/a/"));
    }

    @Test
    public void testNormalizeNoEndSeparator_125() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("//server/a/b/../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_126() {
        assertEquals(SEP + SEP + "server" + SEP + "c", FilenameUtils.normalizeNoEndSeparator("//server/a/b/../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_127() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("//server/a/b/../../../c"));
    }

    @Test
    public void testNormalizeNoEndSeparator_128() {
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("//server/a/b/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_129() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalizeNoEndSeparator("//server/a/b/../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_130() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("//server/a/b/../../.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_131() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("//server/a/b/../c/../d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_132() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtils.normalizeNoEndSeparator("//server/a/b//d"));
    }

    @Test
    public void testNormalizeNoEndSeparator_133() {
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b", FilenameUtils.normalizeNoEndSeparator("//server/a/b/././."));
    }

    @Test
    public void testNormalizeNoEndSeparator_134() {
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtils.normalizeNoEndSeparator("//server/./a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_135() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalizeNoEndSeparator("//server/./"));
    }

    @Test
    public void testNormalizeNoEndSeparator_136() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalizeNoEndSeparator("//server/."));
    }

    @Test
    public void testNormalizeNoEndSeparator_137() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("//server/../a"));
    }

    @Test
    public void testNormalizeNoEndSeparator_138() {
        assertNull(FilenameUtils.normalizeNoEndSeparator("//server/.."));
    }

    @Test
    public void testNormalizeNoEndSeparator_139() {
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtils.normalizeNoEndSeparator("//server/"));
    }

    @Test
    public void testNormalizeNoEndSeparatorUnixWin_1() {
        assertEquals("/a/c", FilenameUtils.normalizeNoEndSeparator("/a/b/../c/", true));
    }

    @Test
    public void testNormalizeNoEndSeparatorUnixWin_2() {
        assertEquals("/a/c", FilenameUtils.normalizeNoEndSeparator("\\a\\b\\..\\c\\", true));
    }

    @Test
    public void testNormalizeNoEndSeparatorUnixWin_3() {
        assertEquals("\\a\\c", FilenameUtils.normalizeNoEndSeparator("/a/b/../c/", false));
    }

    @Test
    public void testNormalizeNoEndSeparatorUnixWin_4() {
        assertEquals("\\a\\c", FilenameUtils.normalizeNoEndSeparator("\\a\\b\\..\\c\\", false));
    }

    @Test
    public void testNormalizeUnixWin_1() {
        assertEquals("/a/c/", FilenameUtils.normalize("/a/b/../c/", true));
    }

    @Test
    public void testNormalizeUnixWin_2() {
        assertEquals("/a/c/", FilenameUtils.normalize("\\a\\b\\..\\c\\", true));
    }

    @Test
    public void testNormalizeUnixWin_3() {
        assertEquals("\\a\\c\\", FilenameUtils.normalize("/a/b/../c/", false));
    }

    @Test
    public void testNormalizeUnixWin_4() {
        assertEquals("\\a\\c\\", FilenameUtils.normalize("\\a\\b\\..\\c\\", false));
    }

    @Test
    public void testRemoveExtension_1() {
        assertNull(FilenameUtils.removeExtension(null));
    }

    @Test
    public void testRemoveExtension_2() {
        assertEquals("file", FilenameUtils.removeExtension("file.ext"));
    }

    @Test
    public void testRemoveExtension_3() {
        assertEquals("README", FilenameUtils.removeExtension("README"));
    }

    @Test
    public void testRemoveExtension_4() {
        assertEquals("domain.dot", FilenameUtils.removeExtension("domain.dot.com"));
    }

    @Test
    public void testRemoveExtension_5() {
        assertEquals("image", FilenameUtils.removeExtension("image.jpeg"));
    }

    @Test
    public void testRemoveExtension_6() {
        assertEquals("a.b/c", FilenameUtils.removeExtension("a.b/c"));
    }

    @Test
    public void testRemoveExtension_7() {
        assertEquals("a.b/c", FilenameUtils.removeExtension("a.b/c.txt"));
    }

    @Test
    public void testRemoveExtension_8() {
        assertEquals("a/b/c", FilenameUtils.removeExtension("a/b/c"));
    }

    @Test
    public void testRemoveExtension_9() {
        assertEquals("a.b\\c", FilenameUtils.removeExtension("a.b\\c"));
    }

    @Test
    public void testRemoveExtension_10() {
        assertEquals("a.b\\c", FilenameUtils.removeExtension("a.b\\c.txt"));
    }

    @Test
    public void testRemoveExtension_11() {
        assertEquals("a\\b\\c", FilenameUtils.removeExtension("a\\b\\c"));
    }

    @Test
    public void testRemoveExtension_12() {
        assertEquals("C:\\temp\\foo.bar\\README", FilenameUtils.removeExtension("C:\\temp\\foo.bar\\README"));
    }

    @Test
    public void testRemoveExtension_13() {
        assertEquals("../filename", FilenameUtils.removeExtension("../filename.ext"));
    }

    @Test
    public void testSeparatorsToUnix_1() {
        assertNull(FilenameUtils.separatorsToUnix(null));
    }

    @Test
    public void testSeparatorsToUnix_2() {
        assertEquals("/a/b/c", FilenameUtils.separatorsToUnix("/a/b/c"));
    }

    @Test
    public void testSeparatorsToUnix_3() {
        assertEquals("/a/b/c.txt", FilenameUtils.separatorsToUnix("/a/b/c.txt"));
    }

    @Test
    public void testSeparatorsToUnix_4() {
        assertEquals("/a/b/c", FilenameUtils.separatorsToUnix("/a/b\\c"));
    }

    @Test
    public void testSeparatorsToUnix_5() {
        assertEquals("/a/b/c", FilenameUtils.separatorsToUnix("\\a\\b\\c"));
    }

    @Test
    public void testSeparatorsToUnix_6() {
        assertEquals("D:/a/b/c", FilenameUtils.separatorsToUnix("D:\\a\\b\\c"));
    }

    @Test
    public void testSeparatorsToWindows_1() {
        assertNull(FilenameUtils.separatorsToWindows(null));
    }

    @Test
    public void testSeparatorsToWindows_2() {
        assertEquals("\\a\\b\\c", FilenameUtils.separatorsToWindows("\\a\\b\\c"));
    }

    @Test
    public void testSeparatorsToWindows_3() {
        assertEquals("\\a\\b\\c.txt", FilenameUtils.separatorsToWindows("\\a\\b\\c.txt"));
    }

    @Test
    public void testSeparatorsToWindows_4() {
        assertEquals("\\a\\b\\c", FilenameUtils.separatorsToWindows("\\a\\b/c"));
    }

    @Test
    public void testSeparatorsToWindows_5() {
        assertEquals("\\a\\b\\c", FilenameUtils.separatorsToWindows("/a/b/c"));
    }

    @Test
    public void testSeparatorsToWindows_6() {
        assertEquals("D:\\a\\b\\c", FilenameUtils.separatorsToWindows("D:/a/b/c"));
    }
}
