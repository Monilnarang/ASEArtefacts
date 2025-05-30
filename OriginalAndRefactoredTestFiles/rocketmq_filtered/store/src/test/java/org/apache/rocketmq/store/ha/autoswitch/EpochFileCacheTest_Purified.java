package org.apache.rocketmq.store.ha.autoswitch;

import java.io.File;
import java.nio.file.Paths;
import org.apache.rocketmq.remoting.protocol.EpochEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EpochFileCacheTest_Purified {

    private EpochFileCache epochCache;

    private EpochFileCache epochCache2;

    private String path;

    private String path2;

    @Before
    public void setup() {
        this.path = Paths.get(File.separator + "tmp", "EpochCheckpoint").toString();
        this.epochCache = new EpochFileCache(path);
        assertTrue(this.epochCache.appendEntry(new EpochEntry(1, 100)));
        assertTrue(this.epochCache.appendEntry(new EpochEntry(2, 300)));
        assertTrue(this.epochCache.appendEntry(new EpochEntry(3, 500)));
        final EpochEntry entry = this.epochCache.getEntry(2);
        assertEquals(entry.getEpoch(), 2);
        assertEquals(entry.getStartOffset(), 300);
        assertEquals(entry.getEndOffset(), 500);
    }

    @After
    public void shutdown() {
        new File(this.path).delete();
        if (this.path2 != null) {
            new File(this.path2).delete();
        }
    }

    @Test
    public void testInitFromFile_1() {
        assertTrue(this.epochCache.initCacheFromFile());
    }

    @Test
    public void testInitFromFile_2_testMerged_2() {
        final EpochEntry entry = this.epochCache.getEntry(2);
        assertEquals(entry.getEpoch(), 2);
        assertEquals(entry.getStartOffset(), 300);
        assertEquals(entry.getEndOffset(), 500);
    }

    @Test
    public void testTruncate_1() {
        assertNotNull(this.epochCache.getEntry(1));
    }

    @Test
    public void testTruncate_2() {
        assertNull(this.epochCache.getEntry(2));
    }

    @Test
    public void testFindConsistentPointSample1_1() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(1, 100)));
    }

    @Test
    public void testFindConsistentPointSample1_2() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(2, 300)));
    }

    @Test
    public void testFindConsistentPointSample1_3() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(3, 450)));
    }

    @Test
    public void testFindConsistentPointSample1_4() {
        final long consistentPoint = this.epochCache.findConsistentPoint(this.epochCache2);
        assertEquals(consistentPoint, 450);
    }

    @Test
    public void testFindConsistentPointSample2_1() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(1, 100)));
    }

    @Test
    public void testFindConsistentPointSample2_2() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(2, 300)));
    }

    @Test
    public void testFindConsistentPointSample2_3() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(3, 500)));
    }

    @Test
    public void testFindConsistentPointSample2_4() {
        final long consistentPoint = this.epochCache.findConsistentPoint(this.epochCache2);
        assertEquals(consistentPoint, 600);
    }

    @Test
    public void testFindConsistentPointSample3_1() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(1, 200)));
    }

    @Test
    public void testFindConsistentPointSample3_2() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(2, 500)));
    }

    @Test
    public void testFindConsistentPointSample3_3() {
        final long consistentPoint = this.epochCache.findConsistentPoint(this.epochCache2);
        assertEquals(consistentPoint, -1);
    }

    @Test
    public void testFindConsistentPointSample4_1() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(1, 100)));
    }

    @Test
    public void testFindConsistentPointSample4_2() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(2, 300)));
    }

    @Test
    public void testFindConsistentPointSample4_3() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(3, 500)));
    }

    @Test
    public void testFindConsistentPointSample4_4() {
        assertTrue(this.epochCache2.appendEntry(new EpochEntry(4, 800)));
    }

    @Test
    public void testFindConsistentPointSample4_5() {
        final long consistentPoint = this.epochCache2.findConsistentPoint(this.epochCache);
        assertEquals(consistentPoint, 700);
    }
}
