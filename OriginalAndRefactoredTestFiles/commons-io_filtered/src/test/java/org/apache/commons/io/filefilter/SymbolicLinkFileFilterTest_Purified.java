package org.apache.commons.io.filefilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.function.IOBiFunction;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SymbolicLinkFileFilterTest_Purified {

    public static final String TARGET_SHORT_NAME = "SLFF_Target";

    public static final String TARGET_EXT = ".txt";

    public static final String TARGET_NAME = TARGET_SHORT_NAME + TARGET_EXT;

    public static final String DIRECTORY_NAME = "SLFF_TargetDirectory";

    public static final String DIRECTORY_LINK_NAME = "SLFF_LinkDirectory";

    public static final String MISSING = "Missing";

    private static File testTargetFile;

    private static Path testTargetPath;

    private static File parentDirectoryFile;

    private static File testLinkFile;

    private static String linkName;

    private static Path testLinkPath;

    private static File targetDirFile;

    private static Path targetDirPath;

    private static Path testLinkDirPath;

    private static File testLinkDirFile;

    private static File missingFileFile;

    private static Path missingFilePath;

    private static SymbolicLinkFileFilter filter;

    private static SymbolicLinkFileFilter createMockFilter() {
        return new SymbolicLinkFileFilter() {

            private static final long serialVersionUID = 1L;

            @Override
            boolean isSymbolicLink(final Path filePath) {
                return filePath.toFile().exists() && filePath.toString().contains("Link");
            }
        };
    }

    private static Path createMockSymbolicLink(final Path link, final Path target) throws IOException {
        return Files.createFile(link);
    }

    private static Path createRealSymbolicLink(final Path link, final Path target) throws IOException {
        Files.deleteIfExists(link);
        return Files.createSymbolicLink(link, target);
    }

    @AfterAll
    static void tearDown() {
        testLinkDirFile.delete();
        targetDirFile.delete();
        testLinkFile.delete();
        testTargetFile.delete();
    }

    @Test
    public void testPathFilter_HardDirectory_1() {
        assertEquals(FileVisitResult.TERMINATE, filter.accept(targetDirPath, null));
    }

    @Test
    public void testPathFilter_HardDirectory_2() {
        assertFalse(filter.matches(targetDirPath));
    }

    @Test
    public void testPathFilter_HardFile_1() {
        assertEquals(FileVisitResult.TERMINATE, filter.accept(testTargetPath, null));
    }

    @Test
    public void testPathFilter_HardFile_2() {
        assertFalse(filter.matches(testTargetPath));
    }

    @Test
    public void testPathFilter_Link_1() {
        assertEquals(FileVisitResult.CONTINUE, filter.accept(testLinkPath, null));
    }

    @Test
    public void testPathFilter_Link_2() {
        assertTrue(filter.matches(testLinkPath));
    }

    @Test
    public void testPathFilter_missingFile_1() {
        assertEquals(FileVisitResult.TERMINATE, filter.accept(missingFilePath, null));
    }

    @Test
    public void testPathFilter_missingFile_2() {
        assertFalse(filter.matches(missingFilePath));
    }

    @Test
    public void testPathFilter_PathLink_1() {
        assertEquals(FileVisitResult.CONTINUE, filter.accept(testLinkDirPath, null));
    }

    @Test
    public void testPathFilter_PathLink_2() {
        assertTrue(filter.matches(testLinkDirPath));
    }

    @Test
    public void testSymbolicLinkFileFilter_1() {
        assertEquals(FileVisitResult.TERMINATE, SymbolicLinkFileFilter.INSTANCE.accept(PathUtils.current(), null));
    }

    @Test
    public void testSymbolicLinkFileFilter_2() {
        assertFalse(filter.matches(PathUtils.current()));
    }
}
