package org.apache.hadoop.hdfs.server.federation.store.driver;

import static org.apache.hadoop.hdfs.server.federation.store.driver.impl.StateStoreFileBaseImpl.isOldTempRecord;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.util.Time;
import org.junit.Test;

public class TestStateStoreFileBase_Purified {

    @Test
    public void testTempOld_1() {
        assertFalse(isOldTempRecord("test.txt"));
    }

    @Test
    public void testTempOld_2() {
        assertFalse(isOldTempRecord("testfolder/test.txt"));
    }

    @Test
    public void testTempOld_3_testMerged_3() {
        long tnow = Time.now();
        String tmpFile1 = "test." + tnow + ".tmp";
        assertFalse(isOldTempRecord(tmpFile1));
        long told = Time.now() - TimeUnit.MINUTES.toMillis(1);
        String tmpFile2 = "test." + told + ".tmp";
        assertTrue(isOldTempRecord(tmpFile2));
    }
}
