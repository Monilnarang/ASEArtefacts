package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.apache.commons.codec.binary.BaseNCodec.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BaseNCodecTest_Parameterized {

    private static final class NoOpBaseNCodec extends BaseNCodec {

        NoOpBaseNCodec() {
            super(0, 0, 0, 0);
        }

        @Override
        void decode(final byte[] pArray, final int i, final int length, final Context context) {
        }

        @Override
        void encode(final byte[] pArray, final int i, final int length, final Context context) {
        }

        @Override
        protected boolean isInAlphabet(final byte value) {
            return false;
        }
    }

    private static void assertEnsureBufferSizeExpandsToMaxBufferSize(final boolean exceedMaxBufferSize) {
        final int length = 0;
        final long presumableFreeMemory = getPresumableFreeMemory();
        final long estimatedMemory = (1L << 31) + 32 * 1024 + length;
        assumeTrue(presumableFreeMemory > estimatedMemory, "Not enough free memory for the test");
        final int max = Integer.MAX_VALUE - 8;
        if (exceedMaxBufferSize) {
            assumeCanAllocateBufferSize(max + 1);
            System.gc();
        }
        final BaseNCodec ncodec = new NoOpBaseNCodec();
        final Context context = new Context();
        context.buffer = new byte[length];
        context.pos = length;
        int extra = max - length;
        if (exceedMaxBufferSize) {
            extra++;
        }
        ncodec.ensureBufferSize(extra, context);
        assertTrue(context.buffer.length >= length + extra);
    }

    private static void assumeCanAllocateBufferSize(final int size) {
        byte[] bytes = null;
        try {
            bytes = new byte[size];
        } catch (final OutOfMemoryError ignore) {
        }
        assumeTrue(bytes != null, "Cannot allocate array of size: " + size);
    }

    static long getPresumableFreeMemory() {
        System.gc();
        final long allocatedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return Runtime.getRuntime().maxMemory() - allocatedMemory;
    }

    BaseNCodec codec;

    @BeforeEach
    public void setUp() {
        codec = new BaseNCodec(0, 0, 0, 0) {

            @Override
            void decode(final byte[] pArray, final int i, final int length, final Context context) {
            }

            @Override
            void encode(final byte[] pArray, final int i, final int length, final Context context) {
            }

            @Override
            protected boolean isInAlphabet(final byte b) {
                return b == 'O' || b == 'K';
            }
        };
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsInAlphabetByte_1to2")
    public void testIsInAlphabetByte_1to2(int param1) {
        assertFalse(codec.isInAlphabet((byte) param1));
    }

    static public Stream<Arguments> Provider_testIsInAlphabetByte_1to2() {
        return Stream.of(arguments(0), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsInAlphabetByte_3to4")
    public void testIsInAlphabetByte_3to4(String param1) {
        assertTrue(codec.isInAlphabet((byte) param1));
    }

    static public Stream<Arguments> Provider_testIsInAlphabetByte_3to4() {
        return Stream.of(arguments("O"), arguments("K"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsInAlphabetString_1to2")
    public void testIsInAlphabetString_1to2(String param1) {
        assertTrue(codec.isInAlphabet(param1));
    }

    static public Stream<Arguments> Provider_testIsInAlphabetString_1to2() {
        return Stream.of(arguments("OK"), arguments("O=K= \t\n\r"));
    }
}
