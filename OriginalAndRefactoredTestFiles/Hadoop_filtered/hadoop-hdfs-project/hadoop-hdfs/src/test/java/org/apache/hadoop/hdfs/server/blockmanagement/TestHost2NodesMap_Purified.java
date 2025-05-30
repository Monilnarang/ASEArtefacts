package org.apache.hadoop.hdfs.server.blockmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.junit.Before;
import org.junit.Test;

public class TestHost2NodesMap_Purified {

    private final Host2NodesMap map = new Host2NodesMap();

    private DatanodeDescriptor[] dataNodes;

    @Before
    public void setup() {
        dataNodes = new DatanodeDescriptor[] { DFSTestUtil.getDatanodeDescriptor("1.1.1.1", "/d1/r1"), DFSTestUtil.getDatanodeDescriptor("2.2.2.2", "/d1/r1"), DFSTestUtil.getDatanodeDescriptor("3.3.3.3", "/d1/r2"), DFSTestUtil.getDatanodeDescriptor("3.3.3.3", 5021, "/d1/r2") };
        for (DatanodeDescriptor node : dataNodes) {
            map.add(node);
        }
        map.add(null);
    }

    @Test
    public void testGetDatanodeByHost_1() throws Exception {
        assertEquals(map.getDatanodeByHost("1.1.1.1"), dataNodes[0]);
    }

    @Test
    public void testGetDatanodeByHost_2() throws Exception {
        assertEquals(map.getDatanodeByHost("2.2.2.2"), dataNodes[1]);
    }

    @Test
    public void testGetDatanodeByHost_3_testMerged_3() throws Exception {
        DatanodeDescriptor node = map.getDatanodeByHost("3.3.3.3");
        assertTrue(node == dataNodes[2] || node == dataNodes[3]);
        assertNull(map.getDatanodeByHost("4.4.4.4"));
    }

    @Test
    public void testRemove_1() throws Exception {
        DatanodeDescriptor nodeNotInMap = DFSTestUtil.getDatanodeDescriptor("3.3.3.3", "/d1/r4");
        assertFalse(map.remove(nodeNotInMap));
    }

    @Test
    public void testRemove_2() throws Exception {
        assertTrue(map.remove(dataNodes[0]));
    }

    @Test
    public void testRemove_3() throws Exception {
        assertTrue(map.getDatanodeByHost("1.1.1.1.") == null);
    }

    @Test
    public void testRemove_4() throws Exception {
        assertTrue(map.getDatanodeByHost("2.2.2.2") == dataNodes[1]);
    }

    @Test
    public void testRemove_5_testMerged_5() throws Exception {
        DatanodeDescriptor node = map.getDatanodeByHost("3.3.3.3");
        assertTrue(node == dataNodes[2] || node == dataNodes[3]);
        assertNull(map.getDatanodeByHost("4.4.4.4"));
        assertTrue(map.remove(dataNodes[2]));
        assertNull(map.getDatanodeByHost("1.1.1.1"));
        assertEquals(map.getDatanodeByHost("2.2.2.2"), dataNodes[1]);
        assertEquals(map.getDatanodeByHost("3.3.3.3"), dataNodes[3]);
        assertTrue(map.remove(dataNodes[3]));
        assertNull(map.getDatanodeByHost("3.3.3.3"));
        assertFalse(map.remove(null));
        assertTrue(map.remove(dataNodes[1]));
        assertFalse(map.remove(dataNodes[1]));
    }
}
