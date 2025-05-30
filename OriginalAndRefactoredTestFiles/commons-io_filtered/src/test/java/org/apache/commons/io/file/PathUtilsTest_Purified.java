package org.apache.commons.io.file;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.test.TestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

public class PathUtilsTest_Purified extends AbstractTempDirTest {

    private static final String STRING_FIXTURE = "Hello World";

    private static final byte[] BYTE_ARRAY_FIXTURE = STRING_FIXTURE.getBytes(StandardCharsets.UTF_8);

    private static final String TEST_JAR_NAME = "test.jar";

    private static final String TEST_JAR_PATH = "src/test/resources/org/apache/commons/io/test.jar";

    private static final String PATH_FIXTURE = "NOTICE.txt";

    private Path current() {
        return PathUtils.current();
    }

    private Long getLastModifiedMillis(final Path file) throws IOException {
        return Files.getLastModifiedTime(file).toMillis();
    }

    private Path getNonExistentPath() {
        return Paths.get("/does not exist/for/certain");
    }

    private FileSystem openArchive(final Path p, final boolean createNew) throws IOException {
        if (createNew) {
            final Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            final URI fileUri = p.toAbsolutePath().toUri();
            final URI uri = URI.create("jar:" + fileUri.toASCIIString());
            return FileSystems.newFileSystem(uri, env, null);
        }
        return FileSystems.newFileSystem(p, (ClassLoader) null);
    }

    private void setLastModifiedMillis(final Path file, final long millis) throws IOException {
        Files.setLastModifiedTime(file, FileTime.fromMillis(millis));
    }

    private Path writeToNewOutputStream(final boolean append) throws IOException {
        final Path file = tempDirPath.resolve("test1.txt");
        try (OutputStream os = PathUtils.newOutputStream(file, append)) {
            os.write(BYTE_ARRAY_FIXTURE);
        }
        return file;
    }

    @Test
    public void testGetBaseNamePathCornerCases_1() {
        assertNull(PathUtils.getBaseName((Path) null));
    }

    @Test
    public void testGetBaseNamePathCornerCases_2() {
        assertEquals("foo", PathUtils.getBaseName(Paths.get("foo.")));
    }

    @Test
    public void testGetBaseNamePathCornerCases_3() {
        assertEquals("", PathUtils.getBaseName(Paths.get("bar/.foo")));
    }

    @Test
    public void testGetFileNameString_1() {
        assertNull(PathUtils.getFileNameString(Paths.get("/")));
    }

    @Test
    public void testGetFileNameString_2() {
        assertEquals("", PathUtils.getFileNameString(Paths.get("")));
    }

    @Test
    public void testGetFileNameString_3() {
        assertEquals("a", PathUtils.getFileNameString(Paths.get("a")));
    }

    @Test
    public void testGetFileNameString_4() {
        assertEquals("a", PathUtils.getFileNameString(Paths.get("p", "a")));
    }

    @Test
    public void testIsPosixAbsentFile_1() {
        assertFalse(PathUtils.isPosix(Paths.get("ImNotHereAtAllEver.never")));
    }

    @Test
    public void testIsPosixAbsentFile_2() {
        assertFalse(PathUtils.isPosix(null));
    }
}
