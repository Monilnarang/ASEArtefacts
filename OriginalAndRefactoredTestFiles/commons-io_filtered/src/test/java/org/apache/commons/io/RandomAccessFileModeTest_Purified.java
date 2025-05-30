package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class RandomAccessFileModeTest_Purified {

    private static final byte[] BYTES_FIXTURE = "Foo".getBytes(StandardCharsets.US_ASCII);

    private static final String FIXTURE = "test.txt";

    @TempDir
    public Path tempDir;

    private byte[] read(final RandomAccessFile randomAccessFile) throws IOException {
        return RandomAccessFiles.read(randomAccessFile, 0, (int) randomAccessFile.length());
    }

    private Path writeFixture(final byte[] bytes) throws IOException {
        return Files.write(tempDir.resolve(FIXTURE), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Test
    public void testGetMode_1() {
        assertEquals("r", RandomAccessFileMode.READ_ONLY.getMode());
    }

    @Test
    public void testGetMode_2() {
        assertEquals("rw", RandomAccessFileMode.READ_WRITE.getMode());
    }

    @Test
    public void testGetMode_3() {
        assertEquals("rwd", RandomAccessFileMode.READ_WRITE_SYNC_CONTENT.getMode());
    }

    @Test
    public void testGetMode_4() {
        assertEquals("rws", RandomAccessFileMode.READ_WRITE_SYNC_ALL.getMode());
    }

    @Test
    public void testImplies_1() {
        assertTrue(RandomAccessFileMode.READ_WRITE_SYNC_ALL.implies(RandomAccessFileMode.READ_WRITE_SYNC_CONTENT));
    }

    @Test
    public void testImplies_2() {
        assertTrue(RandomAccessFileMode.READ_WRITE_SYNC_CONTENT.implies(RandomAccessFileMode.READ_WRITE));
    }

    @Test
    public void testImplies_3() {
        assertTrue(RandomAccessFileMode.READ_WRITE.implies(RandomAccessFileMode.READ_ONLY));
    }

    @Test
    public void testImplies_4() {
        assertFalse(RandomAccessFileMode.READ_ONLY.implies(RandomAccessFileMode.READ_WRITE_SYNC_ALL));
    }

    @Test
    public void testToString_1() {
        assertEquals("READ_ONLY", RandomAccessFileMode.READ_ONLY.toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("READ_WRITE", RandomAccessFileMode.READ_WRITE.toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("READ_WRITE_SYNC_ALL", RandomAccessFileMode.READ_WRITE_SYNC_ALL.toString());
    }

    @Test
    public void testToString_4() {
        assertEquals("READ_WRITE_SYNC_CONTENT", RandomAccessFileMode.READ_WRITE_SYNC_CONTENT.toString());
    }

    @Test
    public void testValueOfMode_1() {
        assertEquals(RandomAccessFileMode.READ_ONLY, RandomAccessFileMode.valueOfMode("r"));
    }

    @Test
    public void testValueOfMode_2() {
        assertEquals(RandomAccessFileMode.READ_WRITE, RandomAccessFileMode.valueOfMode("rw"));
    }

    @Test
    public void testValueOfMode_3() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_CONTENT, RandomAccessFileMode.valueOfMode("rwd"));
    }

    @Test
    public void testValueOfMode_4() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_ALL, RandomAccessFileMode.valueOfMode("rws"));
    }

    @Test
    public void testValueOfOpenOptions_1() {
        assertEquals(RandomAccessFileMode.READ_ONLY, RandomAccessFileMode.valueOf(StandardOpenOption.READ));
    }

    @Test
    public void testValueOfOpenOptions_2() {
        assertEquals(RandomAccessFileMode.READ_WRITE, RandomAccessFileMode.valueOf(StandardOpenOption.WRITE));
    }

    @Test
    public void testValueOfOpenOptions_3() {
        assertEquals(RandomAccessFileMode.READ_WRITE, RandomAccessFileMode.valueOf(StandardOpenOption.READ, StandardOpenOption.WRITE));
    }

    @Test
    public void testValueOfOpenOptions_4() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_CONTENT, RandomAccessFileMode.valueOf(StandardOpenOption.DSYNC));
    }

    @Test
    public void testValueOfOpenOptions_5() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_CONTENT, RandomAccessFileMode.valueOf(StandardOpenOption.WRITE, StandardOpenOption.DSYNC));
    }

    @Test
    public void testValueOfOpenOptions_6() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_CONTENT, RandomAccessFileMode.valueOf(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC));
    }

    @Test
    public void testValueOfOpenOptions_7() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_ALL, RandomAccessFileMode.valueOf(StandardOpenOption.SYNC));
    }

    @Test
    public void testValueOfOpenOptions_8() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_ALL, RandomAccessFileMode.valueOf(StandardOpenOption.READ, StandardOpenOption.SYNC));
    }

    @Test
    public void testValueOfOpenOptions_9() {
        assertEquals(RandomAccessFileMode.READ_WRITE_SYNC_ALL, RandomAccessFileMode.valueOf(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.SYNC));
    }
}
