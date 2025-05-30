package org.apache.druid.segment.writeout;

import org.apache.druid.java.util.common.io.Closer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.ByteBuffer;

public class LazilyAllocatingHeapWriteOutBytesTest_Purified {

    private LazilyAllocatingHeapWriteOutBytes target;

    private Closer closer;

    private HeapByteBufferWriteOutBytes heapByteBufferWriteOutBytes;

    @Before
    public void setUp() {
        closer = Closer.create();
        heapByteBufferWriteOutBytes = new HeapByteBufferWriteOutBytes();
        target = new LazilyAllocatingHeapWriteOutBytes(() -> heapByteBufferWriteOutBytes, closer);
    }

    @Test
    public void testWritingToBuffer_1_testMerged_1() throws IOException {
        Assert.assertNull(target.getTmpBuffer());
    }

    @Test
    public void testWritingToBuffer_2_testMerged_2() throws IOException {
        target.write(ByteBuffer.allocate(512));
        Assert.assertNotNull(target.getTmpBuffer());
        Assert.assertEquals(4096, target.getTmpBuffer().limit());
        Assert.assertNull(target.getDelegate());
        target.write(ByteBuffer.allocate(16385));
        Assert.assertNotNull(target.getDelegate());
        Assert.assertEquals(16385 + 512, target.getDelegate().size());
    }

    @Test
    public void testClosingWriteOutBytes_1_testMerged_1() throws IOException {
        Assert.assertNull(target.getTmpBuffer());
    }

    @Test
    public void testClosingWriteOutBytes_2_testMerged_2() throws IOException {
        target.writeInt(5);
        Assert.assertNotNull(target.getTmpBuffer());
        Assert.assertEquals(128, target.getTmpBuffer().limit());
        Assert.assertNull(target.getDelegate());
    }
}
