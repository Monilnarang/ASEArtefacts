package org.apache.commons.io.file;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.function.Uncheck;
import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilesUncheckTest_Purified {

    private static final FileAttribute<?>[] EMPTY_FILE_ATTRIBUTES_ARRAY = {};

    private static final Path FILE_PATH_A = Paths.get("src/test/resources/org/apache/commons/io/dirs-1-file-size-1/file-size-1.bin");

    private static final Path FILE_PATH_EMPTY = Paths.get("src/test/resources/org/apache/commons/io/test-file-empty.bin");

    private static final Path NEW_DIR_PATH = Paths.get("target/newdir");

    private static final Path NEW_FILE_PATH = Paths.get("target/file.txt");

    private static final Path NEW_FILE_PATH_LINK = Paths.get("target/to_another_file.txt");

    private static final String PREFIX = "prefix";

    private static final String SUFFIX = "suffix";

    private static final Path TARGET_PATH = Paths.get("target");

    @BeforeEach
    @AfterEach
    public void deleteFixtures() throws IOException {
        Files.deleteIfExists(NEW_FILE_PATH);
        Files.deleteIfExists(NEW_DIR_PATH);
        Files.deleteIfExists(NEW_FILE_PATH_LINK);
    }

    @Test
    public void testReadAllLinesPath_1() {
        assertEquals(Collections.emptyList(), FilesUncheck.readAllLines(FILE_PATH_EMPTY));
    }

    @Test
    public void testReadAllLinesPath_2() {
        assertEquals(Arrays.asList("a"), FilesUncheck.readAllLines(FILE_PATH_A));
    }

    @Test
    public void testReadAllLinesPathCharset_1() {
        assertEquals(Collections.emptyList(), FilesUncheck.readAllLines(FILE_PATH_EMPTY, StandardCharsets.UTF_8));
    }

    @Test
    public void testReadAllLinesPathCharset_2() {
        assertEquals(Arrays.asList("a"), FilesUncheck.readAllLines(FILE_PATH_A, StandardCharsets.UTF_8));
    }

    @Test
    public void testSize_1() {
        assertEquals(0, FilesUncheck.size(FILE_PATH_EMPTY));
    }

    @Test
    public void testSize_2() {
        assertEquals(1, FilesUncheck.size(FILE_PATH_A));
    }
}
