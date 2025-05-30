package org.apache.commons.io.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class IOPredicateTest_Purified {

    private static final IOPredicate<Path> IS_HIDDEN = Files::isHidden;

    private static final Path PATH_FIXTURE = Paths.get("src/test/resources/org/apache/commons/io/abitmorethan16k.txt");

    private static final Object THROWING_EQUALS = new Object() {

        @Override
        public boolean equals(final Object obj) {
            throw Erase.rethrow(new IOException("Expected"));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    };

    private static final Predicate<Object> THROWING_UNCHECKED_PREDICATE = TestConstants.THROWING_IO_PREDICATE.asPredicate();

    private void assertThrowsChecked(final Executable executable) {
        assertThrows(IOException.class, executable);
    }

    private void assertThrowsUnchecked(final Executable executable) {
        assertThrows(UncheckedIOException.class, executable);
    }

    @Test
    public void testAndChecked_1() throws IOException {
        assertFalse(IS_HIDDEN.and(IS_HIDDEN).test(PATH_FIXTURE));
    }

    @Test
    public void testAndChecked_2() throws IOException {
        assertTrue(IOPredicate.alwaysTrue().and(IOPredicate.alwaysTrue()).test(PATH_FIXTURE));
    }

    @Test
    public void testAndChecked_3() throws IOException {
        assertFalse(IOPredicate.alwaysFalse().and(IOPredicate.alwaysTrue()).test(PATH_FIXTURE));
    }

    @Test
    public void testAndChecked_4() throws IOException {
        assertFalse(IOPredicate.alwaysTrue().and(IOPredicate.alwaysFalse()).test(PATH_FIXTURE));
    }

    @Test
    public void testAndChecked_5() throws IOException {
        assertFalse(IOPredicate.alwaysFalse().and(IOPredicate.alwaysFalse()).test(PATH_FIXTURE));
    }

    @Test
    public void testFalse_1() throws IOException {
        assertFalse(Constants.IO_PREDICATE_FALSE.test("A"));
    }

    @Test
    public void testFalse_2_testMerged_2() throws IOException {
        final IOPredicate<String> alwaysFalse = IOPredicate.alwaysFalse();
        assertFalse(alwaysFalse.test("A"));
        assertEquals(IOPredicate.alwaysFalse(), IOPredicate.alwaysFalse());
        assertSame(IOPredicate.alwaysFalse(), IOPredicate.alwaysFalse());
    }

    @Test
    public void testNegateChecked_1() throws IOException {
        assertTrue(IS_HIDDEN.negate().test(PATH_FIXTURE));
    }

    @Test
    public void testNegateChecked_2() throws IOException {
        assertFalse(IOPredicate.alwaysTrue().negate().test(PATH_FIXTURE));
    }

    @Test
    public void testOrChecked_1() throws IOException {
        assertFalse(IS_HIDDEN.or(IS_HIDDEN).test(PATH_FIXTURE));
    }

    @Test
    public void testOrChecked_2() throws IOException {
        assertTrue(IOPredicate.alwaysTrue().or(IOPredicate.alwaysFalse()).test(PATH_FIXTURE));
    }

    @Test
    public void testOrChecked_3() throws IOException {
        assertTrue(IOPredicate.alwaysFalse().or(IOPredicate.alwaysTrue()).test(PATH_FIXTURE));
    }

    @Test
    public void testTrue_1() throws IOException {
        assertTrue(Constants.IO_PREDICATE_TRUE.test("A"));
    }

    @Test
    public void testTrue_2_testMerged_2() throws IOException {
        final IOPredicate<String> alwaysTrue = IOPredicate.alwaysTrue();
        assertTrue(alwaysTrue.test("A"));
        assertEquals(IOPredicate.alwaysTrue(), IOPredicate.alwaysTrue());
        assertSame(IOPredicate.alwaysTrue(), IOPredicate.alwaysTrue());
    }
}
