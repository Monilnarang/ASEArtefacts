package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_Purified extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    public FileAlterationObserverTest() {
        listener = new CollectionFileListener(true);
    }

    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    public void testDirectory_1_testMerged_1() throws Exception {
        final File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.txt"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        assertTrue(listener.getCreatedDirectories().contains(testDirA), "B testDirA");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        testDirAFile4 = touch(testDirAFile4);
        assertTrue(listener.getChangedFiles().contains(testDirAFile4), "D testDirAFile4");
        assertTrue(listener.getDeletedDirectories().contains(testDirA), "E testDirA");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E testDirAFile1");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E testDirAFile2");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile4), "E testDirAFile4");
    }

    @Test
    public void testDirectory_2_testMerged_2() throws Exception {
        final File testDirB = new File(testDir, "test-dir-B");
        testDirB.mkdir();
        final File testDirBFile1 = touch(new File(testDirB, "B-file1.java"));
        assertTrue(listener.getCreatedDirectories().contains(testDirB), "B testDirB");
        assertTrue(listener.getCreatedFiles().contains(testDirBFile1), "B testDirBFile1");
        FileUtils.deleteDirectory(testDirB);
        assertTrue(listener.getDeletedDirectories().contains(testDirB), "D testDirB");
        assertTrue(listener.getDeletedFiles().contains(testDirBFile1), "D testDirBFile1");
    }

    @Test
    public void testDirectory_3() throws Exception {
        final File testDirC = new File(testDir, "test-dir-C");
        testDirC.mkdir();
        assertTrue(listener.getCreatedDirectories().contains(testDirC), "B testDirC");
    }
}
