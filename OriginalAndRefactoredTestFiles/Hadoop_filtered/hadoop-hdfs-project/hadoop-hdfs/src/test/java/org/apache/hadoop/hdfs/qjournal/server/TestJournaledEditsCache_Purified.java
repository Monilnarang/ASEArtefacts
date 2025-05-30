package org.apache.hadoop.hdfs.qjournal.server;

import org.apache.hadoop.thirdparty.com.google.common.primitives.Bytes;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.server.namenode.EditLogFileOutputStream;
import org.apache.hadoop.hdfs.server.namenode.NameNodeLayoutVersion;
import org.apache.hadoop.test.GenericTestUtils.LogCapturer;
import org.apache.hadoop.test.PathUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.hadoop.hdfs.qjournal.QJMTestUtil.createGabageTxns;
import static org.apache.hadoop.hdfs.qjournal.QJMTestUtil.createTxnData;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestJournaledEditsCache_Purified {

    private static final int EDITS_CAPACITY = 100;

    private static final File TEST_DIR = PathUtils.getTestDir(TestJournaledEditsCache.class, false);

    private JournaledEditsCache cache;

    @Before
    public void setup() throws Exception {
        Configuration conf = new Configuration();
        conf.setInt(DFSConfigKeys.DFS_JOURNALNODE_EDIT_CACHE_SIZE_KEY, createTxnData(1, 1).length * EDITS_CAPACITY);
        cache = new JournaledEditsCache(conf);
        TEST_DIR.mkdirs();
    }

    @After
    public void cleanup() throws Exception {
        FileUtils.deleteQuietly(TEST_DIR);
    }

    private void storeEdits(int startTxn, int endTxn) throws Exception {
        cache.storeEdits(createTxnData(startTxn, endTxn - startTxn + 1), startTxn, endTxn, NameNodeLayoutVersion.CURRENT_LAYOUT_VERSION);
    }

    private void assertTxnCountAndContents(int startTxn, int requestedMaxTxns, int expectedEndTxn) throws Exception {
        List<ByteBuffer> buffers = new ArrayList<>();
        int expectedTxnCount = expectedEndTxn - startTxn + 1;
        assertEquals(expectedTxnCount, cache.retrieveEdits(startTxn, requestedMaxTxns, buffers));
        byte[] expectedBytes = Bytes.concat(getHeaderForLayoutVersion(NameNodeLayoutVersion.CURRENT_LAYOUT_VERSION), createTxnData(startTxn, expectedTxnCount));
        byte[] actualBytes = new byte[buffers.stream().mapToInt(ByteBuffer::remaining).sum()];
        int pos = 0;
        for (ByteBuffer buf : buffers) {
            System.arraycopy(buf.array(), buf.position(), actualBytes, pos, buf.remaining());
            pos += buf.remaining();
        }
        assertArrayEquals(expectedBytes, actualBytes);
    }

    private static byte[] getHeaderForLayoutVersion(int version) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EditLogFileOutputStream.writeHeader(version, new DataOutputStream(baos));
        return baos.toByteArray();
    }

    @Test
    public void testCacheSingleSegment_1() throws Exception {
        assertTxnCountAndContents(1, 5, 5);
    }

    @Test
    public void testCacheSingleSegment_2() throws Exception {
        assertTxnCountAndContents(1, 20, 20);
    }

    @Test
    public void testCacheSingleSegment_3() throws Exception {
        assertTxnCountAndContents(1, 40, 20);
    }

    @Test
    public void testCacheSingleSegment_4() throws Exception {
        assertTxnCountAndContents(10, 11, 20);
    }

    @Test
    public void testCacheSingleSegment_5() throws Exception {
        assertTxnCountAndContents(10, 20, 20);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_1() throws Exception {
        assertTxnCountAndContents(1, 3, 3);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_2() throws Exception {
        assertTxnCountAndContents(6, 10, 15);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_3() throws Exception {
        assertTxnCountAndContents(1, 7, 7);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_4() throws Exception {
        assertTxnCountAndContents(1, 25, 25);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_5() throws Exception {
        assertTxnCountAndContents(6, 20, 25);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_6() throws Exception {
        assertTxnCountAndContents(6, 50, 30);
    }

    @Test
    public void testCacheBelowCapacityRequestOnBoundary_7() throws Exception {
        assertTxnCountAndContents(21, 20, 30);
    }
}
