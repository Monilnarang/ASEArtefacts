package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class FileUtilsWaitForTest_Purified {

    private final File NOSUCHFILE = new File("a.b.c.d." + System.currentTimeMillis());

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    public void testWaitFor5Absent_1() {
        assertFalse(FileUtils.waitFor(NOSUCHFILE, 2));
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    public void testWaitFor5Absent_2() {
        final long start = System.currentTimeMillis();
        final long elapsed = System.currentTimeMillis() - start;
        assertTrue(elapsed >= 2000, "Must reach timeout - expected 2000, actual: " + elapsed);
    }
}
