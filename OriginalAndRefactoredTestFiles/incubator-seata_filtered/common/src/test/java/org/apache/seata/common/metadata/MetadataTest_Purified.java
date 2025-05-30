package org.apache.seata.common.metadata;

import java.util.ArrayList;
import java.util.List;
import org.apache.seata.common.store.StoreMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MetadataTest_Purified {

    private static Metadata metadata;

    @BeforeAll
    public static void init() {
        metadata = new Metadata();
    }

    @Test
    public void testGetLeader_1() {
        Assertions.assertNull(metadata.getLeader("leader"));
    }

    @Test
    public void testGetLeader_2() {
        Node node = new Node();
        node.setGroup("group");
        metadata.setLeaderNode("leader", node);
        Assertions.assertNotNull(metadata.getLeader("leader"));
    }

    @Test
    public void testGetNodes_1() {
        Assertions.assertEquals(new ArrayList<>(), metadata.getNodes("cluster"));
    }

    @Test
    public void testGetNodes_2() {
        Assertions.assertNull(metadata.getNodes("cluster", "group"));
    }
}
