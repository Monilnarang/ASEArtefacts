package org.apache.commons.io.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathUtilsIsEmptyTest_Purified {

    public static final Path DIR_SIZE_1 = Paths.get("src/test/resources/org/apache/commons/io/dirs-1-file-size-1");

    private static final Path FILE_SIZE_0 = Paths.get("src/test/resources/org/apache/commons/io/dirs-1-file-size-0/file-size-0.bin");

    private static final Path FILE_SIZE_1 = Paths.get("src/test/resources/org/apache/commons/io/dirs-1-file-size-1/file-size-1.bin");

    @Test
    public void testisEmptyFile_1() throws IOException {
        Assertions.assertTrue(PathUtils.isEmptyFile(FILE_SIZE_0));
    }

    @Test
    public void testisEmptyFile_2() throws IOException {
        Assertions.assertFalse(PathUtils.isEmptyFile(FILE_SIZE_1));
    }
}
