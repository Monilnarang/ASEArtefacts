package org.apache.druid.segment.data;

import com.google.common.primitives.Longs;
import it.unimi.dsi.fastutil.ints.IntArrays;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.io.Closer;
import org.apache.druid.segment.CompressedPools;
import org.apache.druid.utils.CloseableUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CompressedColumnarIntsSupplierTest_Purified extends CompressionStrategyTest {

    public CompressedColumnarIntsSupplierTest(CompressionStrategy compressionStrategy) {
        super(compressionStrategy);
    }

    private Closer closer;

    private ColumnarInts columnarInts;

    private CompressedColumnarIntsSupplier supplier;

    private int[] vals;

    @Before
    public void setUp() {
        closer = Closer.create();
        CloseableUtils.closeAndWrapExceptions(columnarInts);
        columnarInts = null;
        supplier = null;
        vals = null;
    }

    @After
    public void tearDown() throws Exception {
        closer.close();
        CloseableUtils.closeAndWrapExceptions(columnarInts);
    }

    private void setupSimple(final int chunkSize) {
        CloseableUtils.closeAndWrapExceptions(columnarInts);
        vals = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16 };
        supplier = CompressedColumnarIntsSupplier.fromIntBuffer(IntBuffer.wrap(vals), chunkSize, ByteOrder.nativeOrder(), compressionStrategy, closer);
        columnarInts = supplier.get();
    }

    private void setupSimpleWithSerde(final int chunkSize) throws IOException {
        vals = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16 };
        makeWithSerde(chunkSize);
    }

    private void makeWithSerde(final int chunkSize) throws IOException {
        CloseableUtils.closeAndWrapExceptions(columnarInts);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final CompressedColumnarIntsSupplier theSupplier = CompressedColumnarIntsSupplier.fromIntBuffer(IntBuffer.wrap(vals), chunkSize, ByteOrder.nativeOrder(), compressionStrategy, closer);
        theSupplier.writeTo(Channels.newChannel(baos), null);
        final byte[] bytes = baos.toByteArray();
        Assert.assertEquals(theSupplier.getSerializedSize(), bytes.length);
        supplier = CompressedColumnarIntsSupplier.fromByteBuffer(ByteBuffer.wrap(bytes), ByteOrder.nativeOrder(), null);
        columnarInts = supplier.get();
    }

    private void setupLargeChunks(final int chunkSize, final int totalSize) throws IOException {
        vals = new int[totalSize];
        Random rand = new Random(0);
        for (int i = 0; i < vals.length; ++i) {
            vals[i] = rand.nextInt();
        }
        makeWithSerde(chunkSize);
    }

    private void assertIndexMatchesVals() {
        Assert.assertEquals(vals.length, columnarInts.size());
        int[] indices = new int[vals.length];
        for (int i = 0, size = columnarInts.size(); i < size; ++i) {
            Assert.assertEquals(vals[i], columnarInts.get(i), 0.0);
            indices[i] = i;
        }
        int[] offsetValues = new int[columnarInts.size() + 1];
        columnarInts.get(offsetValues, 1, 0, columnarInts.size());
        Assert.assertEquals(0, offsetValues[0]);
        Assert.assertArrayEquals(vals, Arrays.copyOfRange(offsetValues, 1, offsetValues.length));
        IntArrays.shuffle(indices, ThreadLocalRandom.current());
        final int limit = Math.min(columnarInts.size(), 1000);
        for (int i = 0; i < limit; ++i) {
            int k = indices[i];
            Assert.assertEquals(vals[k], columnarInts.get(k), 0.0);
        }
    }

    @Test
    public void testSanity_1_testMerged_1() {
        Assert.assertEquals(4, supplier.getBaseIntBuffers().size());
    }

    @Test
    public void testSanity_2() {
        assertIndexMatchesVals();
    }

    @Test
    public void testSanity_4() {
        assertIndexMatchesVals();
    }

    @Test
    public void testSanity_5() {
        Assert.assertEquals(1, supplier.getBaseIntBuffers().size());
    }

    @Test
    public void testSanity_6() {
        assertIndexMatchesVals();
    }

    @Test
    public void testLargeChunks_1() throws Exception {
        Assert.assertEquals(10, supplier.getBaseIntBuffers().size());
    }

    @Test
    public void testLargeChunks_2() throws Exception {
        assertIndexMatchesVals();
    }

    @Test
    public void testLargeChunks_3_testMerged_3() throws Exception {
        Assert.assertEquals(11, supplier.getBaseIntBuffers().size());
    }

    @Test
    public void testLargeChunks_4() throws Exception {
        assertIndexMatchesVals();
    }

    @Test
    public void testLargeChunks_6() throws Exception {
        assertIndexMatchesVals();
    }

    @Test
    public void testSanityWithSerde_1() throws Exception {
        Assert.assertEquals(4, supplier.getBaseIntBuffers().size());
    }

    @Test
    public void testSanityWithSerde_2() throws Exception {
        assertIndexMatchesVals();
    }
}
