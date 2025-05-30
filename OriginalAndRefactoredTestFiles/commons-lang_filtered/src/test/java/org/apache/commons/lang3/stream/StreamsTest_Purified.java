package org.apache.commons.lang3.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailablePredicate;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.xml.sax.SAXException;

public class StreamsTest_Purified extends AbstractLangTest {

    protected <T extends Throwable> FailableConsumer<String, T> asIntConsumer(final T throwable) {
        return s -> {
            final int i = Integer.parseInt(s);
            if (i == 4) {
                throw throwable;
            }
        };
    }

    protected <T extends Throwable> FailablePredicate<Integer, T> asIntPredicate(final T throwable) {
        return i -> {
            if (i.intValue() == 5 && throwable != null) {
                throw throwable;
            }
            return i % 2 == 0;
        };
    }

    private void assertEvenNumbers(final List<Integer> output) {
        assertEquals(3, output.size());
        for (int i = 0; i < 3; i++) {
            assertEquals((i + 1) * 2, output.get(i).intValue());
        }
    }

    @TestFactory
    public Stream<DynamicTest> simpleStreamFilterFailing() {
        final List<String> input = Arrays.asList("1", "2", "3", "4", "5", "6");
        final List<Integer> output = Failable.stream(input).map(Integer::valueOf).filter(asIntPredicate(null)).collect(Collectors.toList());
        assertEvenNumbers(output);
        return Stream.of(dynamicTest("IllegalArgumentException", () -> {
            final IllegalArgumentException iae = new IllegalArgumentException("Invalid argument: " + 5);
            final Executable testMethod = () -> Failable.stream(input).map(Integer::valueOf).filter(asIntPredicate(iae)).collect(Collectors.toList());
            final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, testMethod);
            assertEquals("Invalid argument: " + 5, thrown.getMessage());
        }), dynamicTest("OutOfMemoryError", () -> {
            final OutOfMemoryError oome = new OutOfMemoryError();
            final Executable testMethod = () -> Failable.stream(input).map(Integer::valueOf).filter(asIntPredicate(oome)).collect(Collectors.toList());
            final OutOfMemoryError thrown = assertThrows(OutOfMemoryError.class, testMethod);
            assertNull(thrown.getMessage());
        }), dynamicTest("SAXException", () -> {
            final SAXException se = new SAXException();
            final Executable testMethod = () -> Failable.stream(input).map(Integer::valueOf).filter(asIntPredicate(se)).collect(Collectors.toList());
            final UndeclaredThrowableException thrown = assertThrows(UndeclaredThrowableException.class, testMethod);
            assertNull(thrown.getMessage());
            assertEquals(se, thrown.getCause());
        }));
    }

    @TestFactory
    public Stream<DynamicTest> simpleStreamForEachFailing() {
        final List<String> input = Arrays.asList("1", "2", "3", "4", "5", "6");
        return Stream.of(dynamicTest("IllegalArgumentException", () -> {
            final IllegalArgumentException ise = new IllegalArgumentException();
            final Executable testMethod = () -> Failable.stream(input).forEach(asIntConsumer(ise));
            final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, testMethod);
            assertNull(thrown.getMessage());
        }), dynamicTest("OutOfMemoryError", () -> {
            final OutOfMemoryError oome = new OutOfMemoryError();
            final Executable oomeTestMethod = () -> Failable.stream(input).forEach(asIntConsumer(oome));
            final OutOfMemoryError oomeThrown = assertThrows(OutOfMemoryError.class, oomeTestMethod);
            assertNull(oomeThrown.getMessage());
        }), dynamicTest("SAXException", () -> {
            final SAXException se = new SAXException();
            final Executable seTestMethod = () -> Failable.stream(input).forEach(asIntConsumer(se));
            final UndeclaredThrowableException seThrown = assertThrows(UndeclaredThrowableException.class, seTestMethod);
            assertNull(seThrown.getMessage());
            assertEquals(se, seThrown.getCause());
        }));
    }

    @Test
    public void testNonNull_1() {
        assertEquals(0, Streams.nonNull().collect(Collectors.toList()).size());
    }

    @Test
    public void testNonNull_2() {
        assertEquals(1, Streams.nonNull("A").collect(Collectors.toList()).size());
    }

    @Test
    public void testNonNull_3() {
        assertEquals(1, Streams.nonNull("A", null).collect(Collectors.toList()).size());
    }

    @Test
    public void testNonNull_4() {
        assertEquals(1, Streams.nonNull(null, "A").collect(Collectors.toList()).size());
    }

    @Test
    public void testNullSafeStreamNotNull_1() {
        assertEquals(2, Streams.nonNull(Arrays.asList("A", "B")).collect(Collectors.toList()).size());
    }

    @Test
    public void testNullSafeStreamNotNull_2() {
        assertEquals(2, Streams.nonNull(Arrays.asList(null, "A", null, "B", null)).collect(Collectors.toList()).size());
    }

    @Test
    public void testNullSafeStreamNotNull_3() {
        assertEquals(0, Streams.nonNull(Arrays.asList(null, null)).collect(Collectors.toList()).size());
    }

    @Test
    public void testOfArray_1() {
        assertEquals(0, Streams.of((Object[]) null).count());
    }

    @Test
    public void testOfArray_2() {
        assertEquals(1, Streams.of("foo").count());
    }

    @Test
    public void testOfArray_3() {
        assertEquals(2, Streams.of("foo", "bar").count());
    }
}
