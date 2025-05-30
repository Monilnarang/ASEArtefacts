package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileUtilsDirectoryContainsTest_Purified {

    private File directory1;

    private File directory2;

    private File directory3;

    private File file1;

    private File file1ByRelativeDirectory2;

    private File file2;

    private File file2ByRelativeDirectory1;

    private File file3;

    @TempDir
    public File top;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @BeforeEach
    public void setUp() throws Exception {
        directory1 = new File(top, "directory1");
        directory2 = new File(top, "directory2");
        directory3 = new File(directory2, "directory3");
        directory1.mkdir();
        directory2.mkdir();
        directory3.mkdir();
        file1 = new File(directory1, "file1");
        file2 = new File(directory2, "file2");
        file3 = new File(top, "file3");
        file1ByRelativeDirectory2 = new File(top, "directory2/../directory1/file1");
        file2ByRelativeDirectory1 = new File(top, "directory1/../directory2/file2");
        FileUtils.touch(file1);
        FileUtils.touch(file2);
        FileUtils.touch(file3);
    }

    @Test
    public void testCanonicalPath_1() throws IOException {
        assertTrue(FileUtils.directoryContains(directory1, file1ByRelativeDirectory2));
    }

    @Test
    public void testCanonicalPath_2() throws IOException {
        assertTrue(FileUtils.directoryContains(directory2, file2ByRelativeDirectory1));
    }

    @Test
    public void testCanonicalPath_3() throws IOException {
        assertFalse(FileUtils.directoryContains(directory1, file2ByRelativeDirectory1));
    }

    @Test
    public void testCanonicalPath_4() throws IOException {
        assertFalse(FileUtils.directoryContains(directory2, file1ByRelativeDirectory2));
    }

    @Test
    public void testDirectoryContainsDirectory_1() throws IOException {
        assertTrue(FileUtils.directoryContains(top, directory1));
    }

    @Test
    public void testDirectoryContainsDirectory_2() throws IOException {
        assertTrue(FileUtils.directoryContains(top, directory2));
    }

    @Test
    public void testDirectoryContainsDirectory_3() throws IOException {
        assertTrue(FileUtils.directoryContains(top, directory3));
    }

    @Test
    public void testDirectoryContainsDirectory_4() throws IOException {
        assertTrue(FileUtils.directoryContains(directory2, directory3));
    }

    @Test
    public void testDirectoryContainsFile_1() throws IOException {
        assertTrue(FileUtils.directoryContains(directory1, file1));
    }

    @Test
    public void testDirectoryContainsFile_2() throws IOException {
        assertTrue(FileUtils.directoryContains(directory2, file2));
    }

    @Test
    public void testDirectoryDoesNotContainFile_1() throws IOException {
        assertFalse(FileUtils.directoryContains(directory1, file2));
    }

    @Test
    public void testDirectoryDoesNotContainFile_2() throws IOException {
        assertFalse(FileUtils.directoryContains(directory2, file1));
    }

    @Test
    public void testDirectoryDoesNotContainFile_3() throws IOException {
        assertFalse(FileUtils.directoryContains(directory1, file3));
    }

    @Test
    public void testDirectoryDoesNotContainFile_4() throws IOException {
        assertFalse(FileUtils.directoryContains(directory2, file3));
    }

    @Test
    public void testDirectoryDoesNotContainsDirectory_1() throws IOException {
        assertFalse(FileUtils.directoryContains(directory1, top));
    }

    @Test
    public void testDirectoryDoesNotContainsDirectory_2() throws IOException {
        assertFalse(FileUtils.directoryContains(directory2, top));
    }

    @Test
    public void testDirectoryDoesNotContainsDirectory_3() throws IOException {
        assertFalse(FileUtils.directoryContains(directory3, top));
    }

    @Test
    public void testDirectoryDoesNotContainsDirectory_4() throws IOException {
        assertFalse(FileUtils.directoryContains(directory3, directory2));
    }

    @Test
    public void testFileDoesNotExist_1() throws IOException {
        assertFalse(FileUtils.directoryContains(top, null));
    }

    @Test
    public void testFileDoesNotExist_2_testMerged_2() throws IOException {
        final File file = new File("DOESNOTEXIST");
        assertFalse(file.exists());
        assertFalse(FileUtils.directoryContains(top, file));
    }

    @Test
    public void testFileDoesNotExistBug_1() throws IOException {
        assertTrue(top.exists(), "Check directory exists");
    }

    @Test
    public void testFileDoesNotExistBug_2_testMerged_2() throws IOException {
        final File file = new File(top, "DOESNOTEXIST");
        assertFalse(file.exists(), "Check file does not exist");
        assertFalse(FileUtils.directoryContains(top, file), "Directory does not contain unrealized file");
    }
}
