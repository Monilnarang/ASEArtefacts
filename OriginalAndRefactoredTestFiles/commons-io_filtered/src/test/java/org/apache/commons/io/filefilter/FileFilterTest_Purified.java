package org.apache.commons.io.filefilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.file.TempFile;
import org.apache.commons.io.test.TestUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

public class FileFilterTest_Purified extends AbstractFilterTest {

    @Test
    public void testDelegation_1() {
        assertNotNull(FileFilterUtils.asFileFilter((FileFilter) FalseFileFilter.INSTANCE));
    }

    @Test
    public void testDelegation_2() {
        assertNotNull(FileFilterUtils.asFileFilter((FilenameFilter) FalseFileFilter.INSTANCE).toString());
    }

    @Test
    public void testDirectory_1_testMerged_1() throws IOException {
        final IOFileFilter filter = new DirectoryFileFilter();
        assertFiltering(filter, new File("src/"), true);
        assertFiltering(filter, new File("src/").toPath(), true);
        assertFiltering(filter, new File("src/main/java/"), true);
        assertFiltering(filter, new File("src/main/java/").toPath(), true);
        assertFiltering(filter, new File("pom.xml"), false);
        assertFiltering(filter, new File("pom.xml").toPath(), false);
        assertFiltering(filter, new File("imaginary"), false);
        assertFiltering(filter, new File("imaginary").toPath(), false);
        assertFiltering(filter, new File("imaginary/"), false);
        assertFiltering(filter, new File("imaginary/").toPath(), false);
        assertFiltering(filter, new File("LICENSE.txt"), false);
        assertFiltering(filter, new File("LICENSE.txt").toPath(), false);
        assertFiltering(filter, (File) null, false);
        assertFiltering(filter, (Path) null, false);
    }

    @Test
    public void testDirectory_15() throws IOException {
        assertSame(DirectoryFileFilter.DIRECTORY, DirectoryFileFilter.INSTANCE);
    }

    @Test
    public void testFalse_1_testMerged_1() throws IOException {
        final IOFileFilter filter = FileFilterUtils.falseFileFilter();
        assertFiltering(filter, new File("foo.test"), false);
        assertFiltering(filter, new File("foo.test").toPath(), false);
        assertFiltering(filter, new File("foo"), false);
        assertFiltering(filter, new File("foo").toPath(), false);
        assertFiltering(filter, (File) null, false);
        assertFiltering(filter, (Path) null, false);
    }

    @Test
    public void testFalse_7() throws IOException {
        assertSame(FalseFileFilter.FALSE, FalseFileFilter.INSTANCE);
    }

    @Test
    public void testFalse_8() throws IOException {
        assertSame(TrueFileFilter.TRUE, FalseFileFilter.INSTANCE.negate());
    }

    @Test
    public void testFalse_9() throws IOException {
        assertSame(TrueFileFilter.INSTANCE, FalseFileFilter.INSTANCE.negate());
    }

    @Test
    public void testFalse_10() throws IOException {
        assertNotNull(FalseFileFilter.INSTANCE.toString());
    }

    @Test
    public void testTrue_1_testMerged_1() throws IOException {
        final IOFileFilter filter = FileFilterUtils.trueFileFilter();
        assertFiltering(filter, new File("foo.test"), true);
        assertFiltering(filter, new File("foo"), true);
        assertFiltering(filter, (File) null, true);
        assertFiltering(filter, new File("foo.test").toPath(), true);
        assertFiltering(filter, new File("foo").toPath(), true);
        assertFiltering(filter, (Path) null, true);
    }

    @Test
    public void testTrue_7() throws IOException {
        assertSame(TrueFileFilter.TRUE, TrueFileFilter.INSTANCE);
    }

    @Test
    public void testTrue_8() throws IOException {
        assertSame(FalseFileFilter.FALSE, TrueFileFilter.INSTANCE.negate());
    }

    @Test
    public void testTrue_9() throws IOException {
        assertSame(FalseFileFilter.INSTANCE, TrueFileFilter.INSTANCE.negate());
    }

    @Test
    public void testTrue_10() throws IOException {
        assertNotNull(TrueFileFilter.INSTANCE.toString());
    }
}
