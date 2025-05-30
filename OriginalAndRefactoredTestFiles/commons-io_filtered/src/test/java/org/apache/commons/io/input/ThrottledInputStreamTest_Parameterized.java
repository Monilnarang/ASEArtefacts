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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ThrottledInputStreamTest_Parameterized extends ProxyInputStreamTest<ThrottledInputStream> {

    @Override
    @SuppressWarnings({ "resource" })
    protected ThrottledInputStream createFixture() throws IOException {
        return ThrottledInputStream.builder().setInputStream(createOriginInputStream()).get();
    }

    @Test
    public void testCalSleepTimeMs_2() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(Long.MAX_VALUE, 1_000, 0));
    }

    @Test
    public void testCalSleepTimeMs_3() {
        assertEquals(0, ThrottledInputStream.toSleepMillis(Long.MAX_VALUE, 1_000, -1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCalSleepTimeMs_1_4to15")
    public void testCalSleepTimeMs_1_4to15(int param1, int param2, String param3, String param4) {
        assertEquals(param1, ThrottledInputStream.toSleepMillis(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testCalSleepTimeMs_1_4to15() {
        return Stream.of(arguments(0, 0, "1_000", "10_000"), arguments(1500, 5, "1_000", 2), arguments(500, 5, "2_000", 2), arguments(6500, 15, "1_000", 2), arguments(4000, 5, "1_000", 1), arguments(9000, 5, "1_000", 0.5), arguments(99000, 5, "1_000", 0.05), arguments(0, 1, "1_000", 2), arguments(0, 2, "2_000", 2), arguments(0, 1, "1_000", 2), arguments(0, 1, "1_000", 2.0), arguments(0, 1, "1_000", 1), arguments(0, 1, "1_000", 1.0));
    }
}
