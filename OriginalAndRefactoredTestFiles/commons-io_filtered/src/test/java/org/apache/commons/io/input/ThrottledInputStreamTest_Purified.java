package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ThrottledInputStream.Builder;
import org.apache.commons.io.test.CustomIOException;
import org.junit.jupiter.api.Test;

public class ThrottledInputStreamTest_Purified extends ProxyInputStreamTest<ThrottledInputStream> {

    @Override
    @SuppressWarnings({ "resource" })
    protected ThrottledInputStream createFixture() throws IOException {
        return ThrottledInputStream.builder().setInputStream(createOriginInputStream()).get();
    }

    @Test
    public void testCalSleepTimeMs_1() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(0, 1_000, 10_000));
    }

    @Test
    public void testCalSleepTimeMs_2() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(Long.MAX_VALUE, 1_000, 0));
    }

    @Test
    public void testCalSleepTimeMs_3() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(Long.MAX_VALUE, 1_000, -1));
    }

    @Test
    public void testCalSleepTimeMs_4() {
        assertEquals(1500, ThrottledInputStream.toSleepMillis(5, 1_000, 2));
    }

    @Test
    public void testCalSleepTimeMs_5() {
        assertEquals(500, ThrottledInputStream.toSleepMillis(5, 2_000, 2));
    }

    @Test
    public void testCalSleepTimeMs_6() {
        assertEquals(6500, ThrottledInputStream.toSleepMillis(15, 1_000, 2));
    }

    @Test
    public void testCalSleepTimeMs_7() {
        assertEquals(4000, ThrottledInputStream.toSleepMillis(5, 1_000, 1));
    }

    @Test
    public void testCalSleepTimeMs_8() {
        assertEquals(9000, ThrottledInputStream.toSleepMillis(5, 1_000, 0.5));
    }

    @Test
    public void testCalSleepTimeMs_9() {
        assertEquals(99000, ThrottledInputStream.toSleepMillis(5, 1_000, 0.05));
    }

    @Test
    public void testCalSleepTimeMs_10() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(1, 1_000, 2));
    }

    @Test
    public void testCalSleepTimeMs_11() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(2, 2_000, 2));
    }

    @Test
    public void testCalSleepTimeMs_12() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(1, 1_000, 2));
    }

    @Test
    public void testCalSleepTimeMs_13() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(1, 1_000, 2.0));
    }

    @Test
    public void testCalSleepTimeMs_14() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(1, 1_000, 1));
    }

    @Test
    public void testCalSleepTimeMs_15() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(1, 1_000, 1.0));
    }
}
