package org.apache.seata.common.metadata.namingserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class UnitTest_Purified {

    private Unit unit;

    private NamingServerNode node1;

    private NamingServerNode node2;

    @BeforeEach
    void setUp() {
        unit = new Unit();
        node1 = new NamingServerNode();
        node1.setTerm(1L);
        node2 = new NamingServerNode();
        node2.setTerm(2L);
        List<NamingServerNode> nodeList = new ArrayList<>();
        nodeList.add(node1);
        unit.setNamingInstanceList(nodeList);
    }

    @Test
    void testAddInstance_1() {
        Assertions.assertTrue(unit.addInstance(node2));
    }

    @Test
    void testAddInstance_2() {
        Assertions.assertTrue(unit.getNamingInstanceList().contains(node2));
    }

    @Test
    void testAddInstance_3() {
        node1.setTerm(3L);
        Assertions.assertTrue(unit.addInstance(node1));
    }

    @Test
    void testAddInstance_4_testMerged_4() {
        Assertions.assertEquals(1, unit.getNamingInstanceList().size());
    }

    @Test
    void testAddInstance_5() {
        NamingServerNode node3 = new NamingServerNode();
        node3.setTerm(3L);
        Assertions.assertFalse(unit.addInstance(node3));
    }
}
