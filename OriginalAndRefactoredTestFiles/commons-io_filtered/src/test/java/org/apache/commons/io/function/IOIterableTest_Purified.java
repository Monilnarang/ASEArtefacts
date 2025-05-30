package org.apache.commons.io.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOIterableTest_Purified {

    private static class Fixture implements IOIterable<Path> {

        List<Path> list = Arrays.asList(Paths.get("a"), Paths.get("b"));

        @Override
        public IOIterator<Path> iterator() {
            return IOIterator.adapt(list);
        }

        @Override
        public Iterable<Path> unwrap() {
            return list;
        }
    }

    private IOIterable<Path> iterable;

    private Fixture fixture;

    @BeforeEach
    public void beforeEach() {
        fixture = new Fixture();
        iterable = fixture;
    }

    @Test
    public void testUnrwap_1() throws IOException {
        assertSame(fixture.list, iterable.unwrap());
    }

    @Test
    public void testUnrwap_2() throws IOException {
        assertSame(fixture.unwrap(), iterable.unwrap());
    }
}
