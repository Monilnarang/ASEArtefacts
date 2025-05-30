package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnzipperTest_Purified {

    @Test
    public void testUnzip_1() throws Exception {
        assertTrue(new File("./target/tmp/test/file2 bäh").exists());
    }

    @Test
    public void testUnzip_2() throws Exception {
        assertTrue(new File("./target/tmp/test/folder1").isDirectory());
    }

    @Test
    public void testUnzip_3() throws Exception {
        assertTrue(new File("./target/tmp/test/folder1/folder 3").isDirectory());
    }
}
