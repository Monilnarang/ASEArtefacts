package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import org.apache.commons.io.file.AbstractTempDirTest;
import org.apache.commons.io.file.Counters.PathCounters;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.file.TempDirectory;
import org.apache.commons.io.file.TempFile;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.test.TestUtils;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({ "deprecation", "ResultOfMethodCallIgnored" })
public class FileUtilsTest_Purified extends AbstractTempDirTest {

    static class ListDirectoryWalker extends DirectoryWalker<File> {

        ListDirectoryWalker() {
        }

        @Override
        protected void handleDirectoryStart(final File directory, final int depth, final Collection<File> results) throws IOException {
            if (depth > 0) {
                results.add(directory);
            }
        }

        @Override
        protected void handleFile(final File file, final int depth, final Collection<File> results) throws IOException {
            results.add(file);
        }

        List<File> list(final File startDirectory) throws IOException {
            final ArrayList<File> files = new ArrayList<>();
            walk(startDirectory, files);
            return files;
        }
    }

    private static final Path DIR_SIZE_1 = Paths.get("src/test/resources/org/apache/commons/io/dirs-1-file-size-1");

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private static final long DATE3 = 1_000_000_002_000L;

    private static final long DATE2 = 1_000_000_001_000L;

    private static final long DATE1 = 1_000_000_000_000L;

    private static final int TEST_DIRECTORY_SIZE = 0;

    private static final BigInteger TEST_DIRECTORY_SIZE_BI = BigInteger.ZERO;

    private static final BigInteger TEST_DIRECTORY_SIZE_GT_ZERO_BI = BigInteger.valueOf(100);

    private static final ListDirectoryWalker LIST_WALKER = new ListDirectoryWalker();

    private File testFile1;

    private File testFile2;

    private long testFile1Size;

    private long testFile2Size;

    private void assertContentMatchesAfterCopyURLToFileFor(final String resourceName, final File destination) throws IOException {
        FileUtils.copyURLToFile(getClass().getResource(resourceName), destination);
        try (InputStream fis = Files.newInputStream(destination.toPath());
            InputStream expected = getClass().getResourceAsStream(resourceName)) {
            assertTrue(IOUtils.contentEquals(expected, fis), "Content is not equal.");
        }
    }

    private void backDateFile10Minutes(final File testFile) throws IOException {
        final long mins10 = 1000 * 60 * 10;
        final long lastModified1 = getLastModifiedMillis(testFile);
        assertTrue(setLastModifiedMillis(testFile, lastModified1 - mins10));
        assertNotEquals(getLastModifiedMillis(testFile), lastModified1, "Should have changed source date");
    }

    private void consumeRemaining(final Iterator<File> iterator) {
        if (iterator != null) {
            iterator.forEachRemaining(e -> {
            });
        }
    }

    private Path createCircularOsSymbolicLink(final String linkName, final String targetName) throws IOException {
        return Files.createSymbolicLink(Paths.get(linkName), Paths.get(targetName));
    }

    private void createCircularSymbolicLink(final File file) throws IOException {
        assertTrue(file.exists());
        final String linkName = file + "/cycle";
        final String targetName = file + "/..";
        assertTrue(file.exists());
        final Path linkPath = Paths.get(linkName);
        assertFalse(Files.exists(linkPath));
        final Path targetPath = Paths.get(targetName);
        assertTrue(Files.exists(targetPath));
        try {
            Files.createSymbolicLink(linkPath, targetPath);
        } catch (final UnsupportedOperationException e) {
            createCircularOsSymbolicLink(linkName, targetName);
        }
        assertTrue(Files.isSymbolicLink(linkPath), () -> "Expected a symbolic link here: " + linkName);
    }

    private void createFilesForTestCopyDirectory(final File grandParentDir, final File parentDir, final File childDir) throws IOException {
        final File childDir2 = new File(parentDir, "child2");
        final File grandChildDir = new File(childDir, "grandChild");
        final File grandChild2Dir = new File(childDir2, "grandChild2");
        final File file1 = new File(grandParentDir, "file1.txt");
        final File file2 = new File(parentDir, "file2.txt");
        final File file3 = new File(childDir, "file3.txt");
        final File file4 = new File(childDir2, "file4.txt");
        final File file5 = new File(grandChildDir, "file5.txt");
        final File file6 = new File(grandChild2Dir, "file6.txt");
        FileUtils.deleteDirectory(grandParentDir);
        grandChildDir.mkdirs();
        grandChild2Dir.mkdirs();
        FileUtils.writeStringToFile(file1, "File 1 in grandparent", "UTF8");
        FileUtils.writeStringToFile(file2, "File 2 in parent", "UTF8");
        FileUtils.writeStringToFile(file3, "File 3 in child", "UTF8");
        FileUtils.writeStringToFile(file4, "File 4 in child2", "UTF8");
        FileUtils.writeStringToFile(file5, "File 5 in grandChild", "UTF8");
        FileUtils.writeStringToFile(file6, "File 6 in grandChild2", "UTF8");
    }

    private ImmutablePair<Path, Path> createTempSymbolicLinkedRelativeDir() throws IOException {
        final Path targetDir = tempDirPath.resolve("subdir");
        final Path symLinkedDir = tempDirPath.resolve("symlinked-dir");
        Files.createDirectory(targetDir);
        Files.createSymbolicLink(symLinkedDir, targetDir);
        return ImmutablePair.of(symLinkedDir, targetDir);
    }

    private Set<String> getFilePathSet(final List<File> files) {
        return files.stream().map(f -> {
            try {
                return f.getCanonicalPath();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());
    }

    private long getLastModifiedMillis(final File file) throws IOException {
        return FileUtils.lastModified(file);
    }

    private String getName() {
        return this.getClass().getSimpleName();
    }

    private void iterateFilesAndDirs(final File dir, final IOFileFilter fileFilter, final IOFileFilter dirFilter, final Collection<File> expectedFilesAndDirs) {
        final Iterator<File> iterator = FileUtils.iterateFilesAndDirs(dir, fileFilter, dirFilter);
        int filesCount = 0;
        try {
            final List<File> actualFiles = new ArrayList<>();
            while (iterator.hasNext()) {
                filesCount++;
                final File file = iterator.next();
                actualFiles.add(file);
                assertTrue(expectedFilesAndDirs.contains(file), () -> "Unexpected directory/file " + file + ", expected one of " + expectedFilesAndDirs);
            }
            assertEquals(expectedFilesAndDirs.size(), filesCount, actualFiles::toString);
        } finally {
            consumeRemaining(iterator);
        }
    }

    private void openOutputStream_noParent(final boolean createFile) throws Exception {
        final File file = new File("test.txt");
        assertNull(file.getParentFile());
        try {
            if (createFile) {
                TestUtils.createLineFileUtf8(file, new String[] { "Hello" });
            }
            try (FileOutputStream out = FileUtils.openOutputStream(file)) {
                out.write(0);
            }
            assertTrue(file.exists());
        } finally {
            if (!file.delete()) {
                file.deleteOnExit();
            }
        }
    }

    private boolean setLastModifiedMillis(final File testFile, final long millis) {
        return testFile.setLastModified(millis);
    }

    @BeforeEach
    public void setUp() throws Exception {
        testFile1 = new File(tempDirFile, "file1-test.txt");
        testFile2 = new File(tempDirFile, "file1a-test.txt");
        testFile1Size = testFile1.length();
        testFile2Size = testFile2.length();
        if (!testFile1.getParentFile().exists()) {
            fail("Cannot create file " + testFile1 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output3 = new BufferedOutputStream(Files.newOutputStream(testFile1.toPath()))) {
            TestUtils.generateTestData(output3, testFile1Size);
        }
        if (!testFile2.getParentFile().exists()) {
            fail("Cannot create file " + testFile2 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output2 = new BufferedOutputStream(Files.newOutputStream(testFile2.toPath()))) {
            TestUtils.generateTestData(output2, testFile2Size);
        }
        FileUtils.deleteDirectory(tempDirFile);
        tempDirFile.mkdirs();
        if (!testFile1.getParentFile().exists()) {
            fail("Cannot create file " + testFile1 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output1 = new BufferedOutputStream(Files.newOutputStream(testFile1.toPath()))) {
            TestUtils.generateTestData(output1, testFile1Size);
        }
        if (!testFile2.getParentFile().exists()) {
            fail("Cannot create file " + testFile2 + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(testFile2.toPath()))) {
            TestUtils.generateTestData(output, testFile2Size);
        }
    }

    @Test
    public void testByteCountToDisplaySizeBigInteger_17() {
        assertEquals("7 EB", FileUtils.byteCountToDisplaySize(Long.MAX_VALUE));
    }

    @Test
    public void testByteCountToDisplaySizeBigInteger_1_testMerged_2() {
        final BigInteger b1023 = BigInteger.valueOf(1023);
        final BigInteger b1025 = BigInteger.valueOf(1025);
        final BigInteger KB1 = BigInteger.valueOf(1024);
        final BigInteger MB1 = KB1.multiply(KB1);
        final BigInteger GB1 = MB1.multiply(KB1);
        final BigInteger GB2 = GB1.add(GB1);
        final BigInteger TB1 = GB1.multiply(KB1);
        final BigInteger PB1 = TB1.multiply(KB1);
        final BigInteger EB1 = PB1.multiply(KB1);
        assertEquals("0 bytes", FileUtils.byteCountToDisplaySize(BigInteger.ZERO));
        assertEquals("1 bytes", FileUtils.byteCountToDisplaySize(BigInteger.ONE));
        assertEquals("1023 bytes", FileUtils.byteCountToDisplaySize(b1023));
        assertEquals("1 KB", FileUtils.byteCountToDisplaySize(KB1));
        assertEquals("1 KB", FileUtils.byteCountToDisplaySize(b1025));
        assertEquals("1023 KB", FileUtils.byteCountToDisplaySize(MB1.subtract(BigInteger.ONE)));
        assertEquals("1 MB", FileUtils.byteCountToDisplaySize(MB1));
        assertEquals("1 MB", FileUtils.byteCountToDisplaySize(MB1.add(BigInteger.ONE)));
        assertEquals("1023 MB", FileUtils.byteCountToDisplaySize(GB1.subtract(BigInteger.ONE)));
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(GB1));
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(GB1.add(BigInteger.ONE)));
        assertEquals("2 GB", FileUtils.byteCountToDisplaySize(GB2));
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(GB2.subtract(BigInteger.ONE)));
        assertEquals("1 TB", FileUtils.byteCountToDisplaySize(TB1));
        assertEquals("1 PB", FileUtils.byteCountToDisplaySize(PB1));
        assertEquals("1 EB", FileUtils.byteCountToDisplaySize(EB1));
        assertEquals("63 KB", FileUtils.byteCountToDisplaySize(BigInteger.valueOf(Character.MAX_VALUE)));
        assertEquals("31 KB", FileUtils.byteCountToDisplaySize(BigInteger.valueOf(Short.MAX_VALUE)));
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(BigInteger.valueOf(Integer.MAX_VALUE)));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_1() {
        assertEquals("0 bytes", FileUtils.byteCountToDisplaySize(0));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_2() {
        assertEquals("1 bytes", FileUtils.byteCountToDisplaySize(1));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_3() {
        assertEquals("1023 bytes", FileUtils.byteCountToDisplaySize(1023));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_4() {
        assertEquals("1 KB", FileUtils.byteCountToDisplaySize(1024));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_5() {
        assertEquals("1 KB", FileUtils.byteCountToDisplaySize(1025));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_6() {
        assertEquals("1023 KB", FileUtils.byteCountToDisplaySize(1024 * 1023));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_7() {
        assertEquals("1 MB", FileUtils.byteCountToDisplaySize(1024 * 1024));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_8() {
        assertEquals("1 MB", FileUtils.byteCountToDisplaySize(1024 * 1025));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_9() {
        assertEquals("1023 MB", FileUtils.byteCountToDisplaySize(1024 * 1024 * 1023));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_10() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(1024 * 1024 * 1024));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_11() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(1024 * 1024 * 1025));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_12() {
        assertEquals("2 GB", FileUtils.byteCountToDisplaySize(1024L * 1024 * 1024 * 2));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_13() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(1024L * 1024 * 1024 * 2 - 1));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_14() {
        assertEquals("1 TB", FileUtils.byteCountToDisplaySize(1024L * 1024 * 1024 * 1024));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_15() {
        assertEquals("1 PB", FileUtils.byteCountToDisplaySize(1024L * 1024 * 1024 * 1024 * 1024));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_16() {
        assertEquals("1 EB", FileUtils.byteCountToDisplaySize(1024L * 1024 * 1024 * 1024 * 1024 * 1024));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_17() {
        assertEquals("7 EB", FileUtils.byteCountToDisplaySize(Long.MAX_VALUE));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_18() {
        assertEquals("63 KB", FileUtils.byteCountToDisplaySize(Character.MAX_VALUE));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_19() {
        assertEquals("31 KB", FileUtils.byteCountToDisplaySize(Short.MAX_VALUE));
    }

    @SuppressWarnings("NumericOverflow")
    @Test
    public void testByteCountToDisplaySizeLong_20() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(Integer.MAX_VALUE));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_1() {
        assertEquals("0 bytes", FileUtils.byteCountToDisplaySize(Integer.valueOf(0)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_2() {
        assertEquals("1 bytes", FileUtils.byteCountToDisplaySize(Integer.valueOf(1)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_3() {
        assertEquals("1023 bytes", FileUtils.byteCountToDisplaySize(Integer.valueOf(1023)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_4() {
        assertEquals("1 KB", FileUtils.byteCountToDisplaySize(Integer.valueOf(1024)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_5() {
        assertEquals("1 KB", FileUtils.byteCountToDisplaySize(Integer.valueOf(1025)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_6() {
        assertEquals("1023 KB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024 * 1023)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_7() {
        assertEquals("1 MB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024 * 1024)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_8() {
        assertEquals("1 MB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024 * 1025)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_9() {
        assertEquals("1023 MB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024 * 1024 * 1023)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_10() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024 * 1024 * 1024)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_11() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024 * 1024 * 1025)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_12() {
        assertEquals("2 GB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024L * 1024 * 1024 * 2)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_13() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024L * 1024 * 1024 * 2 - 1)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_14() {
        assertEquals("1 TB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024L * 1024 * 1024 * 1024)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_15() {
        assertEquals("1 PB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024L * 1024 * 1024 * 1024 * 1024)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_16() {
        assertEquals("1 EB", FileUtils.byteCountToDisplaySize(Long.valueOf(1024L * 1024 * 1024 * 1024 * 1024 * 1024)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_17() {
        assertEquals("7 EB", FileUtils.byteCountToDisplaySize(Long.valueOf(Long.MAX_VALUE)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_18() {
        assertEquals("63 KB", FileUtils.byteCountToDisplaySize(Integer.valueOf(Character.MAX_VALUE)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_19() {
        assertEquals("31 KB", FileUtils.byteCountToDisplaySize(Short.valueOf(Short.MAX_VALUE)));
    }

    @Test
    public void testByteCountToDisplaySizeNumber_20() {
        assertEquals("1 GB", FileUtils.byteCountToDisplaySize(Integer.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    public void testDecodeUrl_1() {
        assertEquals("", FileUtils.decodeUrl(""));
    }

    @Test
    public void testDecodeUrl_2() {
        assertEquals("foo", FileUtils.decodeUrl("foo"));
    }

    @Test
    public void testDecodeUrl_3() {
        assertEquals("+", FileUtils.decodeUrl("+"));
    }

    @Test
    public void testDecodeUrl_4() {
        assertEquals("% ", FileUtils.decodeUrl("%25%20"));
    }

    @Test
    public void testDecodeUrl_5() {
        assertEquals("%20", FileUtils.decodeUrl("%2520"));
    }

    @Test
    public void testDecodeUrl_6() {
        assertEquals("jar:file:/C:/dir/sub dir/1.0/foo-1.0.jar!/org/Bar.class", FileUtils.decodeUrl("jar:file:/C:/dir/sub%20dir/1.0/foo-1.0.jar!/org/Bar.class"));
    }

    @Test
    public void testDecodeUrlLenient_1() {
        assertEquals(" ", FileUtils.decodeUrl(" "));
    }

    @Test
    public void testDecodeUrlLenient_2() {
        assertEquals("\u00E4\u00F6\u00FC\u00DF", FileUtils.decodeUrl("\u00E4\u00F6\u00FC\u00DF"));
    }

    @Test
    public void testDecodeUrlLenient_3() {
        assertEquals("%", FileUtils.decodeUrl("%"));
    }

    @Test
    public void testDecodeUrlLenient_4() {
        assertEquals("% ", FileUtils.decodeUrl("%%20"));
    }

    @Test
    public void testDecodeUrlLenient_5() {
        assertEquals("%2", FileUtils.decodeUrl("%2"));
    }

    @Test
    public void testDecodeUrlLenient_6() {
        assertEquals("%2G", FileUtils.decodeUrl("%2G"));
    }

    @Test
    public void testForceMkdirParent_1() throws Exception {
        assertTrue(tempDirFile.exists());
    }

    @Test
    public void testForceMkdirParent_2_testMerged_2() throws Exception {
        final File testParentDir = new File(tempDirFile, "testForceMkdirParent");
        testParentDir.delete();
        assertFalse(testParentDir.exists());
        final File testFile = new File(testParentDir, "test.txt");
        assertFalse(testFile.exists());
        assertTrue(testParentDir.exists());
    }

    @Test
    public void testIsRegularFile_1() throws IOException {
        assertFalse(FileUtils.isRegularFile(null));
    }

    @Test
    public void testIsRegularFile_2() throws IOException {
        assertFalse(FileUtils.isRegularFile(tempDirFile));
    }

    @Test
    public void testIsRegularFile_3() throws IOException {
        assertTrue(FileUtils.isRegularFile(testFile1));
    }

    @Test
    public void testIsRegularFile_4() throws IOException {
        Files.delete(testFile1.toPath());
        assertFalse(FileUtils.isRegularFile(testFile1));
    }

    @Test
    public void testToFile3_1() throws Exception {
        assertNull(FileUtils.toFile(null));
    }

    @Test
    public void testToFile3_2() throws Exception {
        assertNull(FileUtils.toFile(new URL("http://jakarta.apache.org")));
    }
}
