package org.apache.commons.io.file;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.file.Counters.PathCounters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

public class PathUtilsContentEqualsTest_Purified {

    @TempDir
    public File temporaryFolder;

    private void assertContentEquals(final FileSystem fileSystem1, final FileSystem fileSystem2) throws IOException {
        assertTrue(PathUtils.contentEquals(fileSystem1, fileSystem2));
        assertTrue(PathUtils.contentEquals(fileSystem2, fileSystem1));
        assertTrue(PathUtils.contentEquals(fileSystem1, fileSystem1));
        assertTrue(PathUtils.contentEquals(fileSystem2, fileSystem2));
    }

    private void assertContentNotEquals(final FileSystem fileSystem1, final FileSystem fileSystem2) throws IOException {
        assertFalse(PathUtils.contentEquals(fileSystem1, fileSystem2));
        assertFalse(PathUtils.contentEquals(fileSystem2, fileSystem1));
        assertTrue(PathUtils.contentEquals(fileSystem1, fileSystem1));
        assertTrue(PathUtils.contentEquals(fileSystem2, fileSystem2));
    }

    private void assertDirectoryAndFileContentEquals(final Path dir1, final Path dir2) throws IOException {
        assertTrue(PathUtils.directoryAndFileContentEquals(dir1, dir2));
        assertTrue(PathUtils.directoryAndFileContentEquals(dir2, dir2));
        assertTrue(PathUtils.directoryAndFileContentEquals(dir1, dir1));
        assertTrue(PathUtils.directoryAndFileContentEquals(dir2, dir2));
    }

    private void assertDirectoryAndFileContentNotEquals(final Path dir1, final Path dir2) throws IOException {
        assertFalse(PathUtils.directoryAndFileContentEquals(dir1, dir2));
        assertFalse(PathUtils.directoryAndFileContentEquals(dir2, dir1));
        assertTrue(PathUtils.directoryAndFileContentEquals(dir1, dir1));
        assertTrue(PathUtils.directoryAndFileContentEquals(dir2, dir2));
    }

    private void assertFileContentEquals(final Path path1, final Path path2) throws IOException {
        assertTrue(PathUtils.fileContentEquals(path1, path1));
        assertTrue(PathUtils.fileContentEquals(path1, path2));
        assertTrue(PathUtils.fileContentEquals(path2, path2));
        assertTrue(PathUtils.fileContentEquals(path2, path1));
    }

    private void assertFileContentNotEquals(final Path path1, final Path path2) throws IOException {
        assertFalse(PathUtils.fileContentEquals(path1, path2));
        assertFalse(PathUtils.fileContentEquals(path2, path1));
        assertTrue(PathUtils.fileContentEquals(path1, path1));
        assertTrue(PathUtils.fileContentEquals(path2, path2));
    }

    private String getName() {
        return this.getClass().getSimpleName();
    }

    @Test
    public void testDirectoryAndFileContentEquals_1() throws Exception {
        assertDirectoryAndFileContentEquals(null, null);
    }

    @Test
    public void testDirectoryAndFileContentEquals_2_testMerged_2() throws Exception {
        final Path path1 = new File(temporaryFolder, getName()).toPath();
        final Path path2 = new File(temporaryFolder, getName() + "2").toPath();
        assertDirectoryAndFileContentNotEquals(path1, null);
        assertDirectoryAndFileContentEquals(path1, path2);
    }

    @Test
    public void testDirectoryAndFileContentEquals_4_testMerged_3() throws Exception {
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-files-only/directory-files-only1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-files-only/directory-files-only2");
        assertDirectoryAndFileContentEquals(dir1, dir2);
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-then-files/dir1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-then-files/dir2");
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1");
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1/directory-files-only1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1/");
        assertDirectoryAndFileContentNotEquals(dir1, dir2);
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-then-files");
    }

    @Test
    public void testDirectoryContentEquals_1() throws Exception {
        assertTrue(PathUtils.directoryContentEquals(null, null));
    }

    @Test
    public void testDirectoryContentEquals_2_testMerged_2() throws Exception {
        final Path path1 = new File(temporaryFolder, getName()).toPath();
        final Path path2 = new File(temporaryFolder, getName() + "2").toPath();
        assertFalse(PathUtils.directoryContentEquals(null, path1));
        assertFalse(PathUtils.directoryContentEquals(path1, null));
        assertTrue(PathUtils.directoryContentEquals(path1, path1));
        assertTrue(PathUtils.directoryContentEquals(path1, path2));
        assertTrue(PathUtils.directoryContentEquals(path2, path2));
        assertTrue(PathUtils.directoryContentEquals(path2, path1));
    }

    @Test
    public void testDirectoryContentEquals_8_testMerged_3() throws Exception {
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-files-only/directory-files-only1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-files-only/directory-files-only2");
        assertTrue(PathUtils.directoryContentEquals(dir1, dir2));
        assertTrue(PathUtils.directoryContentEquals(dir2, dir2));
        assertTrue(PathUtils.directoryContentEquals(dir1, dir1));
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-then-files/dir1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-then-files/dir2");
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1");
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1/directory-files-only1");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files/dirs-and-files1/");
        assertFalse(PathUtils.directoryContentEquals(dir1, dir2));
        assertFalse(PathUtils.directoryContentEquals(dir2, dir1));
        final Path dir1 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-and-files");
        final Path dir2 = Paths.get("src/test/resources/dir-equals-tests/dir-equals-dirs-then-files");
    }
}
