package org.apache.commons.io.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.test.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SizeFileComparatorTest_Purified extends ComparatorAbstractTest {

    private File smallerDir;

    private File largerDir;

    private File smallerFile;

    private File largerFile;

    @BeforeEach
    public void setUp() throws Exception {
        comparator = (AbstractFileComparator) SizeFileComparator.SIZE_COMPARATOR;
        reverse = SizeFileComparator.SIZE_REVERSE;
        smallerDir = new File(dir, "smallerdir");
        largerDir = new File(dir, "largerdir");
        smallerFile = new File(smallerDir, "smaller.txt");
        final File equalFile = new File(dir, "equal.txt");
        largerFile = new File(largerDir, "larger.txt");
        smallerDir.mkdir();
        largerDir.mkdir();
        if (!smallerFile.getParentFile().exists()) {
            throw new IOException("Cannot create file " + smallerFile + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output2 = new BufferedOutputStream(Files.newOutputStream(smallerFile.toPath()))) {
            TestUtils.generateTestData(output2, 32);
        }
        if (!equalFile.getParentFile().exists()) {
            throw new IOException("Cannot create file " + equalFile + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output1 = new BufferedOutputStream(Files.newOutputStream(equalFile.toPath()))) {
            TestUtils.generateTestData(output1, 48);
        }
        if (!largerFile.getParentFile().exists()) {
            throw new IOException("Cannot create file " + largerFile + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(largerFile.toPath()))) {
            TestUtils.generateTestData(output, 64);
        }
        equalFile1 = equalFile;
        equalFile2 = equalFile;
        lessFile = smallerFile;
        moreFile = largerFile;
    }

    @Test
    public void testCompareDirectorySizes_1() {
        assertEquals(0, comparator.compare(smallerDir, largerDir), "sumDirectoryContents=false");
    }

    @Test
    public void testCompareDirectorySizes_2() {
        assertEquals(-1, SizeFileComparator.SIZE_SUMDIR_COMPARATOR.compare(smallerDir, largerDir), "less");
    }

    @Test
    public void testCompareDirectorySizes_3() {
        assertEquals(1, SizeFileComparator.SIZE_SUMDIR_REVERSE.compare(smallerDir, largerDir), "less");
    }
}
