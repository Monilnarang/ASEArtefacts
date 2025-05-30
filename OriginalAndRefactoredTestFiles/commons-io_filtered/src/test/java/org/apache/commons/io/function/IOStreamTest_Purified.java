package org.apache.commons.io.function;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

public class IOStreamTest_Purified {

    private static final boolean AT_LEAST_JAVA_11 = SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_11);

    private static final boolean AT_LEAST_JAVA_17 = SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_17);

    private void compareAndSetIO(final AtomicReference<String> ref, final String expected, final String update) throws IOException {
        TestUtils.compareAndSetThrowsIO(ref, expected, update);
    }

    private void compareAndSetRE(final AtomicReference<String> ref, final String expected, final String update) {
        TestUtils.compareAndSetThrowsRE(ref, expected, update);
    }

    private void ioExceptionOnNull(final Object test) throws IOException {
        if (test == null) {
            throw new IOException("Unexpected");
        }
    }

    @SuppressWarnings("resource")
    @Test
    public void testAdapt_1() {
        assertEquals(0, IOStream.adapt((Stream<?>) null).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testAdapt_2() {
        assertEquals(0, IOStream.adapt(Stream.empty()).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testAdapt_3() {
        assertEquals(1, IOStream.adapt(Stream.of("A")).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testAdaptAsParallel_1() {
        assertEquals(0, IOStream.adapt((Stream<?>) null).parallel().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testAdaptAsParallel_2() {
        assertEquals(0, IOStream.adapt(Stream.empty()).parallel().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testAdaptAsParallel_3() {
        assertEquals(1, IOStream.adapt(Stream.of("A")).parallel().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testCount_1() {
        assertEquals(0, IOStream.of().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testCount_2() {
        assertEquals(1, IOStream.of("A").count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testCount_3() {
        assertEquals(2, IOStream.of("A", "B").count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testCount_4() {
        assertEquals(3, IOStream.of("A", "B", "C").count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testCount_5() {
        assertEquals(3, IOStream.of("A", "A", "A").count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testDistinct_1() {
        assertEquals(0, IOStream.of().distinct().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testDistinct_2() {
        assertEquals(1, IOStream.of("A").distinct().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testDistinct_3() {
        assertEquals(2, IOStream.of("A", "B").distinct().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testDistinct_4() {
        assertEquals(3, IOStream.of("A", "B", "C").distinct().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testDistinct_5() {
        assertEquals(1, IOStream.of("A", "A", "A").distinct().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testEmpty_1() throws IOException {
        assertEquals(0, Stream.empty().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testEmpty_2() throws IOException {
        assertEquals(0, IOStream.empty().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_1() {
        assertFalse(IOStream.of("A", "B").isParallel());
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_2() {
        assertTrue(IOStream.of("A", "B").parallel().isParallel());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfArray_1() {
        assertEquals(0, IOStream.of((String[]) null).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfArray_2() {
        assertEquals(0, IOStream.of().count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfArray_3() {
        assertEquals(2, IOStream.of("A", "B").count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfIterable_1() {
        assertEquals(0, IOStream.of((Iterable<?>) null).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfIterable_2() {
        assertEquals(0, IOStream.of(Collections.emptyList()).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfIterable_3() {
        assertEquals(0, IOStream.of(Collections.emptySet()).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfIterable_4() {
        assertEquals(0, IOStream.of(Collections.emptySortedSet()).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfIterable_5() {
        assertEquals(1, IOStream.of(Arrays.asList("a")).count());
    }

    @SuppressWarnings("resource")
    @Test
    public void testOfIterable_6() {
        assertEquals(2, IOStream.of(Arrays.asList("a", "b")).count());
    }
}
