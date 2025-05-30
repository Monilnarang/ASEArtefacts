package org.apache.hadoop.hdfs.server.federation.store.records;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.server.federation.resolver.RemoteLocation;
import org.apache.hadoop.hdfs.server.federation.resolver.order.DestinationOrder;
import org.apache.hadoop.hdfs.server.federation.router.RouterQuotaUsage;
import org.apache.hadoop.hdfs.server.federation.store.driver.StateStoreSerializer;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Test;

public class TestMountTable_Purified {

    private static final String SRC = "/test";

    private static final String DST_NS_0 = "ns0";

    private static final String DST_NS_1 = "ns1";

    private static final String DST_PATH_0 = "/path1";

    private static final String DST_PATH_1 = "/path/path2";

    private static final List<RemoteLocation> DST = new LinkedList<>();

    static {
        DST.add(new RemoteLocation(DST_NS_0, DST_PATH_0, SRC));
        DST.add(new RemoteLocation(DST_NS_1, DST_PATH_1, SRC));
    }

    private static final Map<String, String> DST_MAP = new LinkedHashMap<>();

    static {
        DST_MAP.put(DST_NS_0, DST_PATH_0);
        DST_MAP.put(DST_NS_1, DST_PATH_1);
    }

    private static final long DATE_CREATED = 100;

    private static final long DATE_MOD = 200;

    private static final long NS_COUNT = 1;

    private static final long NS_QUOTA = 5;

    private static final long SS_COUNT = 10;

    private static final long SS_QUOTA = 100;

    private static final RouterQuotaUsage QUOTA = new RouterQuotaUsage.Builder().fileAndDirectoryCount(NS_COUNT).quota(NS_QUOTA).spaceConsumed(SS_COUNT).spaceQuota(SS_QUOTA).build();

    private void validateDestinations(MountTable record) {
        assertEquals(SRC, record.getSourcePath());
        assertEquals(2, record.getDestinations().size());
        RemoteLocation location1 = record.getDestinations().get(0);
        assertEquals(DST_NS_0, location1.getNameserviceId());
        assertEquals(DST_PATH_0, location1.getDest());
        RemoteLocation location2 = record.getDestinations().get(1);
        assertEquals(DST_NS_1, location2.getNameserviceId());
        assertEquals(DST_PATH_1, location2.getDest());
    }

    @Test
    public void testGetterSetter_1_testMerged_1() throws IOException {
        MountTable record = MountTable.newInstance(SRC, DST_MAP);
        validateDestinations(record);
        assertEquals(SRC, record.getSourcePath());
        assertEquals(DST, record.getDestinations());
        RouterQuotaUsage quota = record.getQuota();
        assertEquals(0, quota.getFileAndDirectoryCount());
        assertEquals(HdfsConstants.QUOTA_RESET, quota.getQuota());
        assertEquals(0, quota.getSpaceConsumed());
        assertEquals(HdfsConstants.QUOTA_RESET, quota.getSpaceQuota());
        MountTable record2 = MountTable.newInstance(SRC, DST_MAP, DATE_CREATED, DATE_MOD);
        validateDestinations(record2);
        assertEquals(SRC, record2.getSourcePath());
        assertEquals(DST, record2.getDestinations());
        assertEquals(DATE_CREATED, record2.getDateCreated());
        assertEquals(DATE_MOD, record2.getDateModified());
        assertFalse(record.isReadOnly());
        assertEquals(DestinationOrder.HASH, record.getDestOrder());
    }

    @Test
    public void testGetterSetter_3() throws IOException {
        assertTrue(DATE_CREATED > 0);
    }

    @Test
    public void testGetterSetter_4() throws IOException {
        assertTrue(DATE_MOD > 0);
    }

    @Test
    public void testQuota_1_testMerged_1() throws IOException {
        MountTable record = MountTable.newInstance(SRC, DST_MAP);
        record.setQuota(QUOTA);
        validateDestinations(record);
        assertEquals(SRC, record.getSourcePath());
        assertEquals(DST, record.getDestinations());
        RouterQuotaUsage quotaGet = record.getQuota();
        assertEquals(NS_COUNT, quotaGet.getFileAndDirectoryCount());
        assertEquals(NS_QUOTA, quotaGet.getQuota());
        assertEquals(SS_COUNT, quotaGet.getSpaceConsumed());
        assertEquals(SS_QUOTA, quotaGet.getSpaceQuota());
    }

    @Test
    public void testQuota_3() throws IOException {
        assertTrue(DATE_CREATED > 0);
    }

    @Test
    public void testQuota_4() throws IOException {
        assertTrue(DATE_MOD > 0);
    }
}
