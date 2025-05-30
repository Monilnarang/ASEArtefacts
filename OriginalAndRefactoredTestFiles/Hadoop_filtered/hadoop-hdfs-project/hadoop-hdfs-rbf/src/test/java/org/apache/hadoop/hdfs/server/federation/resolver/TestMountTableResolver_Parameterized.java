package org.apache.hadoop.hdfs.server.federation.resolver;

import static org.apache.hadoop.hdfs.server.federation.router.RBFConfigKeys.FEDERATION_MOUNT_TABLE_CACHE_ENABLE;
import static org.apache.hadoop.hdfs.server.federation.router.RBFConfigKeys.FEDERATION_MOUNT_TABLE_MAX_CACHE_SIZE;
import static org.apache.hadoop.hdfs.server.federation.router.RBFConfigKeys.DFS_ROUTER_DEFAULT_NAMESERVICE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.federation.router.Router;
import org.apache.hadoop.hdfs.server.federation.store.MountTableStore;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestMountTableResolver_Parameterized {

    private static final Logger LOG = LoggerFactory.getLogger(TestMountTableResolver.class);

    private static final int TEST_MAX_CACHE_SIZE = 10;

    private MountTableResolver mountTable;

    private Map<String, String> getMountTableEntry(String subcluster, String path) {
        Map<String, String> ret = new HashMap<>();
        ret.put(subcluster, path);
        return ret;
    }

    private void setupMountTable() throws IOException {
        Configuration conf = new Configuration();
        conf.setInt(FEDERATION_MOUNT_TABLE_MAX_CACHE_SIZE, TEST_MAX_CACHE_SIZE);
        conf.setStrings(DFS_ROUTER_DEFAULT_NAMESERVICE, "0");
        mountTable = new MountTableResolver(conf);
        Map<String, String> map = getMountTableEntry("1", "/");
        mountTable.addEntry(MountTable.newInstance("/", map));
        map = getMountTableEntry("2", "/");
        mountTable.addEntry(MountTable.newInstance("/tmp", map));
        map = getMountTableEntry("3", "/user");
        mountTable.addEntry(MountTable.newInstance("/user", map));
        map = getMountTableEntry("2", "/bin");
        mountTable.addEntry(MountTable.newInstance("/usr/bin", map));
        map = getMountTableEntry("2", "/user/test");
        mountTable.addEntry(MountTable.newInstance("/user/a", map));
        map = getMountTableEntry("4", "/user/file1.txt");
        mountTable.addEntry(MountTable.newInstance("/user/b/file1.txt", map));
        map = getMountTableEntry("1", "/user/test");
        mountTable.addEntry(MountTable.newInstance("/user/a/demo/test/a", map));
        map = getMountTableEntry("3", "/user/test");
        mountTable.addEntry(MountTable.newInstance("/user/a/demo/test/b", map));
        map = getMountTableEntry("2", "/tmp");
        MountTable readOnlyEntry = MountTable.newInstance("/readonly", map);
        readOnlyEntry.setReadOnly(true);
        mountTable.addEntry(readOnlyEntry);
        map = getMountTableEntry("5", "/dest1");
        map.put("6", "/dest2");
        MountTable multiEntry = MountTable.newInstance("/multi", map);
        mountTable.addEntry(multiEntry);
    }

    @Before
    public void setup() throws IOException {
        setupMountTable();
    }

    private void compareLists(List<String> list1, String[] list2) {
        assertEquals(list1.size(), list2.length);
        for (String item : list2) {
            assertTrue(list1.contains(item));
        }
    }

    private void getMountPoints(boolean trailingSlash) throws IOException {
        List<String> mounts = mountTable.getMountPoints("/");
        assertEquals(5, mounts.size());
        compareLists(mounts, new String[] { "tmp", "user", "usr", "readonly", "multi" });
        String path = trailingSlash ? "/user/" : "/user";
        mounts = mountTable.getMountPoints(path);
        assertEquals(2, mounts.size());
        compareLists(mounts, new String[] { "a", "b" });
        path = trailingSlash ? "/user/a/" : "/user/a";
        mounts = mountTable.getMountPoints(path);
        assertEquals(1, mounts.size());
        compareLists(mounts, new String[] { "demo" });
        path = trailingSlash ? "/user/a/demo/" : "/user/a/demo";
        mounts = mountTable.getMountPoints(path);
        assertEquals(1, mounts.size());
        compareLists(mounts, new String[] { "test" });
        path = trailingSlash ? "/user/a/demo/test/" : "/user/a/demo/test";
        mounts = mountTable.getMountPoints(path);
        assertEquals(2, mounts.size());
        compareLists(mounts, new String[] { "a", "b" });
        path = trailingSlash ? "/tmp/" : "/tmp";
        mounts = mountTable.getMountPoints(path);
        assertEquals(0, mounts.size());
        path = trailingSlash ? "/t/" : "/t";
        mounts = mountTable.getMountPoints(path);
        assertNull(mounts);
        path = trailingSlash ? "/unknownpath/" : "/unknownpath";
        mounts = mountTable.getMountPoints(path);
        assertNull(mounts);
        path = trailingSlash ? "/multi/" : "/multi";
        mounts = mountTable.getMountPoints(path);
        assertEquals(0, mounts.size());
    }

    private void compareRecords(List<MountTable> list1, String[] list2) {
        assertEquals(list1.size(), list2.length);
        for (String item : list2) {
            for (MountTable record : list1) {
                if (record.getSourcePath().equals(item)) {
                    return;
                }
            }
        }
        fail();
    }

    @ParameterizedTest
    @MethodSource("Provider_testDestination_1_1to2_2to3_3to4_4to12")
    public void testDestination_1_1to2_2to3_3to4_4to12(String param1, String param2) throws IOException {
        assertEquals(param1, mountTable.getDestinationForPath(param2).toString());
    }

    static public Stream<Arguments> Provider_testDestination_1_1to2_2to3_3to4_4to12() {
        return Stream.of(arguments("1->/tesfile1.txt", "/tesfile1.txt"), arguments("3->/user/testfile2.txt", "/user/testfile2.txt"), arguments("2->/user/test/testfile3.txt", "/user/a/testfile3.txt"), arguments("3->/user/b/testfile4.txt", "/user/b/testfile4.txt"), arguments("1->/share/file5.txt", "/share/file5.txt"), arguments("2->/bin/file7.txt", "/usr/bin/file7.txt"), arguments("1->/usr/file8.txt", "/usr/file8.txt"), arguments("2->/user/test/demo/file9.txt", "/user/a/demo/file9.txt"), arguments("3->/user/testfolder", "/user/testfolder"), arguments("2->/user/test/b", "/user/a/b"), arguments("3->/user/test/a", "/user/test/a"), arguments("2->/tmp/tesfile1.txt", "/readonly/tesfile1.txt"), arguments("1->/tesfile1.txt", "//tesfile1.txt///"), arguments("3->/user/testfile2.txt", "/user///testfile2.txt"), arguments("2->/user/test/testfile3.txt", "///user/a/testfile3.txt"), arguments("3->/user/b/testfile4.txt", "/user/b/testfile4.txt//"));
    }
}
