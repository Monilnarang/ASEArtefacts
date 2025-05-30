package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.io.test.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileDeleteStrategyTest_Purified {

    @TempDir
    public File temporaryFolder;

    @Test
    public void testToString_1() {
        assertEquals("FileDeleteStrategy[Normal]", FileDeleteStrategy.NORMAL.toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("FileDeleteStrategy[Force]", FileDeleteStrategy.FORCE.toString());
    }
}
