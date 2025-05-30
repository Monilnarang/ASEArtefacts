package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.file.AbstractTempDirTest;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FileUtilsCleanDirectoryTest_Purified extends AbstractTempDirTest {

    private boolean chmod(final File file, final int mode, final boolean recurse) throws InterruptedException {
        final List<String> args = new ArrayList<>();
        args.add("chmod");
        if (recurse) {
            args.add("-R");
        }
        args.add(Integer.toString(mode));
        args.add(file.getAbsolutePath());
        final Process proc;
        try {
            proc = Runtime.getRuntime().exec(args.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
        } catch (final IOException e) {
            return false;
        }
        return proc.waitFor() == 0;
    }

    @Test
    public void testCleanEmpty_1() throws Exception {
        assertEquals(0, tempDirFile.list().length);
    }

    @Test
    public void testCleanEmpty_2() throws Exception {
        assertEquals(0, tempDirFile.list().length);
    }

    @Test
    public void testDeletesNested_1() throws Exception {
        final File nested = new File(tempDirFile, "nested");
        assertTrue(nested.mkdirs());
    }

    @Test
    public void testDeletesNested_2() throws Exception {
        assertEquals(1, tempDirFile.list().length);
    }

    @Test
    public void testDeletesNested_3() throws Exception {
        FileUtils.cleanDirectory(tempDirFile);
        assertEquals(0, tempDirFile.list().length);
    }
}
