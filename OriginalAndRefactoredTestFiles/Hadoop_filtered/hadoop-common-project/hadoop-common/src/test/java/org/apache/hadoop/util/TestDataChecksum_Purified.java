package org.apache.hadoop.util;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.fs.ChecksumException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDataChecksum_Purified {

    private static final int SUMS_OFFSET_IN_BUFFER = 3;

    private static final int DATA_OFFSET_IN_BUFFER = 3;

    private static final int DATA_TRAILER_IN_BUFFER = 3;

    private static final int BYTES_PER_CHUNK = 512;

    private static final DataChecksum.Type[] CHECKSUM_TYPES = { DataChecksum.Type.CRC32, DataChecksum.Type.CRC32C };

    private static class Harness {

        final DataChecksum checksum;

        final int dataLength, sumsLength, numSums;

        ByteBuffer dataBuf, checksumBuf;

        Harness(DataChecksum checksum, int dataLength, boolean useDirect) {
            this.checksum = checksum;
            this.dataLength = dataLength;
            numSums = (dataLength - 1) / checksum.getBytesPerChecksum() + 1;
            sumsLength = numSums * checksum.getChecksumSize();
            byte[] data = new byte[dataLength + DATA_OFFSET_IN_BUFFER + DATA_TRAILER_IN_BUFFER];
            new Random().nextBytes(data);
            dataBuf = ByteBuffer.wrap(data, DATA_OFFSET_IN_BUFFER, dataLength);
            byte[] checksums = new byte[SUMS_OFFSET_IN_BUFFER + sumsLength];
            checksumBuf = ByteBuffer.wrap(checksums, SUMS_OFFSET_IN_BUFFER, sumsLength);
            if (useDirect) {
                dataBuf = directify(dataBuf);
                checksumBuf = directify(checksumBuf);
            }
        }

        void testCorrectness() throws ChecksumException {
            checksum.calculateChunkedSums(dataBuf, checksumBuf);
            checksum.verifyChunkedSums(dataBuf, checksumBuf, "fake file", 0);
            corruptBufferOffset(checksumBuf, 0);
            checksum.verifyChunkedSums(dataBuf, checksumBuf, "fake file", 0);
            corruptBufferOffset(dataBuf, 0);
            dataBuf.limit(dataBuf.limit() + 1);
            corruptBufferOffset(dataBuf, dataLength + DATA_OFFSET_IN_BUFFER);
            dataBuf.limit(dataBuf.limit() - 1);
            checksum.verifyChunkedSums(dataBuf, checksumBuf, "fake file", 0);
            corruptBufferOffset(checksumBuf, SUMS_OFFSET_IN_BUFFER);
            try {
                checksum.verifyChunkedSums(dataBuf, checksumBuf, "fake file", 0);
                fail("Did not throw on bad checksums");
            } catch (ChecksumException ce) {
                assertEquals(0, ce.getPos());
            }
            uncorruptBufferOffset(checksumBuf, SUMS_OFFSET_IN_BUFFER);
            corruptBufferOffset(checksumBuf, SUMS_OFFSET_IN_BUFFER + sumsLength - 1);
            try {
                checksum.verifyChunkedSums(dataBuf, checksumBuf, "fake file", 0);
                fail("Did not throw on bad checksums");
            } catch (ChecksumException ce) {
                int expectedPos = checksum.getBytesPerChecksum() * (numSums - 1);
                assertEquals(expectedPos, ce.getPos());
                assertTrue(ce.getMessage().contains("fake file"));
            }
        }
    }

    private void doBulkTest(DataChecksum checksum, int dataLength, boolean useDirect) throws Exception {
        System.err.println("Testing bulk checksums of length " + dataLength + " with " + (useDirect ? "direct" : "array-backed") + " buffers");
        new Harness(checksum, dataLength, useDirect).testCorrectness();
    }

    private static void corruptBufferOffset(ByteBuffer buf, int offset) {
        buf.put(offset, (byte) (buf.get(offset) + 1));
    }

    private static void uncorruptBufferOffset(ByteBuffer buf, int offset) {
        buf.put(offset, (byte) (buf.get(offset) - 1));
    }

    private static ByteBuffer directify(ByteBuffer dataBuf) {
        ByteBuffer newBuf = ByteBuffer.allocateDirect(dataBuf.capacity());
        newBuf.position(dataBuf.position());
        newBuf.mark();
        newBuf.put(dataBuf);
        newBuf.reset();
        newBuf.limit(dataBuf.limit());
        return newBuf;
    }

    @Test
    public void testEquality_1() {
        assertEquals(DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 512), DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 512));
    }

    @Test
    public void testEquality_2() {
        assertFalse(DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 512).equals(DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 1024)));
    }

    @Test
    public void testEquality_3() {
        assertFalse(DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 512).equals(DataChecksum.newDataChecksum(DataChecksum.Type.CRC32C, 512)));
    }
}
