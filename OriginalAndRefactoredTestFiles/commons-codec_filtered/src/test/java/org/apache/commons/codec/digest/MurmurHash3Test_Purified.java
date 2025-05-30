package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.MurmurHash3.IncrementalHash32;
import org.apache.commons.codec.digest.MurmurHash3.IncrementalHash32x86;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class MurmurHash3Test_Purified {

    private static final String TEST_HASH64 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

    private static final int[] RANDOM_INTS = { 46, 246, 249, 184, 247, 84, 99, 144, 62, 77, 195, 220, 92, 20, 150, 159, 38, 40, 124, 252, 185, 28, 63, 13, 213, 172, 85, 198, 118, 74, 109, 157, 132, 216, 76, 177, 173, 23, 140, 86, 146, 95, 54, 176, 114, 179, 234, 174, 183, 141, 122, 12, 60, 116, 200, 142, 6, 167, 59, 240, 33, 29, 165, 111, 243, 30, 219, 110, 255, 53, 32, 35, 64, 225, 96, 152, 70, 41, 133, 80, 244, 127, 57, 199, 5, 164, 151, 49, 26, 180, 203, 83, 108, 39, 126, 208, 42, 206, 178, 19, 69, 223, 71, 231, 250, 125, 211, 232, 189, 55, 44, 82, 48, 221, 43, 192, 241, 103, 155, 27, 51, 163, 21, 169, 91, 94, 217, 191, 78, 72, 93, 102, 104, 105, 8, 113, 100, 143, 89, 245, 227, 120, 160, 251, 153, 145, 45, 218, 168, 233, 229, 253, 67, 22, 182, 98, 137, 128, 135, 11, 214, 66, 73, 171, 188, 170, 131, 207, 79, 106, 24, 75, 237, 194, 7, 129, 215, 81, 248, 242, 16, 25, 136, 147, 156, 97, 52, 10, 181, 17, 205, 58, 101, 68, 230, 1, 37, 0, 222, 88, 130, 148, 224, 47, 50, 197, 34, 212, 196, 209, 14, 36, 139, 228, 154, 31, 175, 202, 236, 161, 3, 162, 190, 254, 134, 119, 4, 61, 65, 117, 186, 107, 204, 9, 187, 201, 90, 149, 226, 56, 239, 238, 235, 112, 87, 18, 121, 115, 138, 123, 210, 2, 193, 166, 158, 15 };

    private static final byte[] RANDOM_BYTES;

    static {
        RANDOM_BYTES = new byte[RANDOM_INTS.length];
        for (int i = 0; i < RANDOM_BYTES.length; i++) {
            RANDOM_BYTES[i] = (byte) RANDOM_INTS[i];
        }
    }

    private static void assertIncrementalHash32(final byte[] bytes, final int seed, final int... blocks) {
        int offset = 0;
        int total = 0;
        final IncrementalHash32 inc = new IncrementalHash32();
        inc.start(seed);
        for (final int block : blocks) {
            total += block;
            final int h1 = MurmurHash3.hash32(bytes, 0, total, seed);
            inc.add(bytes, offset, block);
            offset += block;
            final int h2 = inc.end();
            assertEquals(h1, h2, "Hashes differ");
            assertEquals(h1, inc.end(), "Hashes differ after no additional data");
        }
    }

    private static void assertIncrementalHash32x86(final byte[] bytes, final int seed, final int... blocks) {
        int offset = 0;
        int total = 0;
        final IncrementalHash32x86 inc = new IncrementalHash32x86();
        inc.start(seed);
        for (final int block : blocks) {
            total += block;
            final int h1 = MurmurHash3.hash32x86(bytes, 0, total, seed);
            inc.add(bytes, offset, block);
            offset += block;
            final int h2 = inc.end();
            assertEquals(h1, h2, "Hashes differ");
            assertEquals(h1, inc.end(), "Hashes differ after no additional data");
        }
    }

    private static long[] createLongTestData() {
        final long[] data = new long[100];
        data[0] = 0;
        data[1] = Long.MIN_VALUE;
        data[2] = Long.MAX_VALUE;
        data[3] = -1L;
        for (int i = 4; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextLong();
        }
        return data;
    }

    private static int[] createRandomBlocks(final int maxLength) {
        final int[] blocks = new int[20];
        int count = 0;
        int length = 0;
        while (count < blocks.length && length < maxLength) {
            final int size = ThreadLocalRandom.current().nextInt(1, 9);
            blocks[count++] = size;
            length += size;
        }
        return Arrays.copyOf(blocks, count);
    }

    private static boolean negativeBytes(final byte[] bytes, final int start, final int length) {
        for (int i = start; i < start + length; i++) {
            if (bytes[i] < 0) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testHash32WithTrailingNegativeSignedBytesIsInvalid_1() {
        assertNotEquals(-43192051, MurmurHash3.hash32(new byte[] { -1 }, 0, 1, 0));
    }

    @Test
    public void testHash32WithTrailingNegativeSignedBytesIsInvalid_2() {
        assertNotEquals(-582037868, MurmurHash3.hash32(new byte[] { 0, -1 }, 0, 2, 0));
    }

    @Test
    public void testHash32WithTrailingNegativeSignedBytesIsInvalid_3() {
        assertNotEquals(922088087, MurmurHash3.hash32(new byte[] { 0, 0, -1 }, 0, 3, 0));
    }

    @Test
    public void testHash32WithTrailingNegativeSignedBytesIsInvalid_4() {
        assertNotEquals(-1309567588, MurmurHash3.hash32(new byte[] { -1, 0 }, 0, 2, 0));
    }

    @Test
    public void testHash32WithTrailingNegativeSignedBytesIsInvalid_5() {
        assertNotEquals(-363779670, MurmurHash3.hash32(new byte[] { -1, 0, 0 }, 0, 3, 0));
    }

    @Test
    public void testHash32WithTrailingNegativeSignedBytesIsInvalid_6() {
        assertNotEquals(-225068062, MurmurHash3.hash32(new byte[] { 0, -1, 0 }, 0, 3, 0));
    }

    @Test
    public void testHash32x86WithTrailingNegativeSignedBytes_1() {
        assertEquals(-43192051, MurmurHash3.hash32x86(new byte[] { -1 }, 0, 1, 0));
    }

    @Test
    public void testHash32x86WithTrailingNegativeSignedBytes_2() {
        assertEquals(-582037868, MurmurHash3.hash32x86(new byte[] { 0, -1 }, 0, 2, 0));
    }

    @Test
    public void testHash32x86WithTrailingNegativeSignedBytes_3() {
        assertEquals(922088087, MurmurHash3.hash32x86(new byte[] { 0, 0, -1 }, 0, 3, 0));
    }

    @Test
    public void testHash32x86WithTrailingNegativeSignedBytes_4() {
        assertEquals(-1309567588, MurmurHash3.hash32x86(new byte[] { -1, 0 }, 0, 2, 0));
    }

    @Test
    public void testHash32x86WithTrailingNegativeSignedBytes_5() {
        assertEquals(-363779670, MurmurHash3.hash32x86(new byte[] { -1, 0, 0 }, 0, 3, 0));
    }

    @Test
    public void testHash32x86WithTrailingNegativeSignedBytes_6() {
        assertEquals(-225068062, MurmurHash3.hash32x86(new byte[] { 0, -1, 0 }, 0, 3, 0));
    }
}
