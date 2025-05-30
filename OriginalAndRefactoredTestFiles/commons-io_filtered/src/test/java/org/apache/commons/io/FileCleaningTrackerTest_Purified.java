package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.ReferenceQueue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.file.AbstractTempDirTest;
import org.apache.commons.io.test.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileCleaningTrackerTest_Purified extends AbstractTempDirTest {

    private File testFile;

    private Path testPath;

    private FileCleaningTracker fileCleaningTracker;

    RandomAccessFile createRandomAccessFile() throws FileNotFoundException {
        return RandomAccessFileMode.READ_WRITE.create(testFile);
    }

    protected FileCleaningTracker newInstance() {
        return new FileCleaningTracker();
    }

    private void pauseForDeleteToComplete(File file) {
        int count = 0;
        while (file.exists() && count++ < 40) {
            TestUtils.sleepQuietly(500L);
            file = new File(file.getPath());
        }
    }

    private void pauseForDeleteToComplete(Path file) {
        int count = 0;
        while (Files.exists(file) && count++ < 40) {
            TestUtils.sleepQuietly(500L);
            file = Paths.get(file.toAbsolutePath().toString());
        }
    }

    @BeforeEach
    public void setUp() {
        testFile = new File(tempDirFile, "file-test.txt");
        testPath = testFile.toPath();
        fileCleaningTracker = newInstance();
    }

    private String showFailures() {
        if (fileCleaningTracker.deleteFailures.size() == 1) {
            return "[Delete Failed: " + fileCleaningTracker.deleteFailures.get(0) + "]";
        }
        return "[Delete Failures: " + fileCleaningTracker.deleteFailures.size() + "]";
    }

    @AfterEach
    public void tearDown() {
        {
            if (fileCleaningTracker != null) {
                fileCleaningTracker.q = new ReferenceQueue<>();
                fileCleaningTracker.trackers.clear();
                fileCleaningTracker.deleteFailures.clear();
                fileCleaningTracker.exitWhenFinished = false;
                fileCleaningTracker.reaper = null;
            }
        }
        fileCleaningTracker = null;
    }

    private void waitUntilTrackCount() throws Exception {
        System.gc();
        TestUtils.sleep(500);
        int count = 0;
        while (fileCleaningTracker.getTrackCount() != 0 && count++ < 5) {
            List<String> list = new ArrayList<>();
            try {
                long i = 0;
                while (fileCleaningTracker.getTrackCount() != 0) {
                    list.add("A Big String A Big String A Big String A Big String A Big String A Big String A Big String A Big String A Big String A Big String " + i++);
                }
            } catch (final Throwable ignored) {
            }
            list = null;
            System.gc();
            TestUtils.sleep(1000);
        }
        if (fileCleaningTracker.getTrackCount() != 0) {
            throw new IllegalStateException("Your JVM is not releasing References, try running the test with less memory (-Xmx)");
        }
    }

    @Test
    public void testFileCleanerDirectory_NullStrategy_1_testMerged_1() throws Exception {
        TestUtils.createFile(testFile, 100);
        assertTrue(testFile.exists());
        assertTrue(tempDirFile.exists());
        assertTrue(testFile.getParentFile().exists());
    }

    @Test
    public void testFileCleanerDirectory_NullStrategy_3() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectory_NullStrategy_4() throws Exception {
        Object obj = new Object();
        fileCleaningTracker.track(tempDirFile, obj, null);
        assertEquals(1, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectory_NullStrategy_5() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectoryFileSource_1_testMerged_1() throws Exception {
        TestUtils.createFile(testFile, 100);
        assertTrue(testFile.exists());
        assertTrue(tempDirFile.exists());
        assertTrue(testFile.getParentFile().exists());
    }

    @Test
    public void testFileCleanerDirectoryFileSource_3() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectoryFileSource_4() throws Exception {
        Object obj = new Object();
        fileCleaningTracker.track(tempDirFile, obj);
        assertEquals(1, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectoryFileSource_5() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectoryPathSource_1_testMerged_1() throws Exception {
        TestUtils.createFile(testPath, 100);
        assertTrue(Files.exists(testPath));
        assertTrue(Files.exists(testPath.getParent()));
    }

    @Test
    public void testFileCleanerDirectoryPathSource_2() throws Exception {
        assertTrue(Files.exists(tempDirPath));
    }

    @Test
    public void testFileCleanerDirectoryPathSource_3() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectoryPathSource_4() throws Exception {
        Object obj = new Object();
        fileCleaningTracker.track(tempDirPath, obj);
        assertEquals(1, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerDirectoryPathSource_5() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerExitWhenFinished1_1_testMerged_1() throws Exception {
        final String path = testFile.getPath();
        assertFalse(testFile.exists(), "1-testFile exists: " + testFile);
        RandomAccessFile raf = createRandomAccessFile();
        assertTrue(testFile.exists(), "2-testFile exists");
        fileCleaningTracker.track(path, raf);
        assertEquals(1, fileCleaningTracker.getTrackCount(), "4-Track Count");
        assertFalse(fileCleaningTracker.exitWhenFinished, "5-exitWhenFinished");
        assertTrue(fileCleaningTracker.reaper.isAlive(), "6-reaper.isAlive");
        assertFalse(fileCleaningTracker.exitWhenFinished, "7-exitWhenFinished");
        fileCleaningTracker.exitWhenFinished();
        assertTrue(fileCleaningTracker.exitWhenFinished, "8-exitWhenFinished");
        assertTrue(fileCleaningTracker.reaper.isAlive(), "9-reaper.isAlive");
        pauseForDeleteToComplete(new File(path));
        assertEquals(0, fileCleaningTracker.getTrackCount(), "10-Track Count");
        assertFalse(new File(path).exists(), "11-testFile exists " + showFailures());
        assertTrue(fileCleaningTracker.exitWhenFinished, "12-exitWhenFinished");
        assertFalse(fileCleaningTracker.reaper.isAlive(), "13-reaper.isAlive");
    }

    @Test
    public void testFileCleanerExitWhenFinished1_3() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount(), "3-Track Count");
    }

    @Test
    public void testFileCleanerExitWhenFinishedFirst_1() throws Exception {
        assertFalse(fileCleaningTracker.exitWhenFinished);
    }

    @Test
    public void testFileCleanerExitWhenFinishedFirst_2_testMerged_2() throws Exception {
        fileCleaningTracker.exitWhenFinished();
        assertTrue(fileCleaningTracker.exitWhenFinished);
        assertNull(fileCleaningTracker.reaper);
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerFile_1_testMerged_1() throws Exception {
        final String path = testFile.getPath();
        assertFalse(testFile.exists());
        RandomAccessFile raf = createRandomAccessFile();
        assertTrue(testFile.exists());
        fileCleaningTracker.track(path, raf);
        assertEquals(1, fileCleaningTracker.getTrackCount());
        pauseForDeleteToComplete(new File(path));
        assertFalse(new File(path).exists(), showFailures());
    }

    @Test
    public void testFileCleanerFile_3() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }

    @Test
    public void testFileCleanerFile_5() throws Exception {
        assertEquals(0, fileCleaningTracker.getTrackCount());
    }
}
