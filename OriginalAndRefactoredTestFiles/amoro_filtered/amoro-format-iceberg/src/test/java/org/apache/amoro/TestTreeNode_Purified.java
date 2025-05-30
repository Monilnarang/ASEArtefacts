package org.apache.amoro;

import org.apache.amoro.data.DataTreeNode;
import org.junit.Assert;
import org.junit.Test;

public class TestTreeNode_Purified {

    private void assertParentNode(DataTreeNode node) {
        Assert.assertEquals(node, node.left().parent());
        Assert.assertEquals(node, node.right().parent());
    }

    @Test
    public void testParentNode_1() {
        assertParentNode(DataTreeNode.of(0, 0));
    }

    @Test
    public void testParentNode_2() {
        assertParentNode(DataTreeNode.of(1, 0));
    }

    @Test
    public void testParentNode_3() {
        assertParentNode(DataTreeNode.of(1, 0));
    }

    @Test
    public void testParentNode_4() {
        assertParentNode(DataTreeNode.of(3, 3));
    }

    @Test
    public void testParentNode_5() {
        assertParentNode(DataTreeNode.of(255, 0));
    }

    @Test
    public void testParentNode_6() {
        assertParentNode(DataTreeNode.of(255, 126));
    }

    @Test
    public void testParentNode_7() {
        assertParentNode(DataTreeNode.of(255, 245));
    }

    @Test
    public void testParentNode_8() {
        assertParentNode(DataTreeNode.of(255, 255));
    }

    @Test
    public void testNodeId_1() {
        Assert.assertEquals(1, DataTreeNode.of(0, 0).getId());
    }

    @Test
    public void testNodeId_2() {
        Assert.assertEquals(2, DataTreeNode.of(1, 0).getId());
    }

    @Test
    public void testNodeId_3() {
        Assert.assertEquals(3, DataTreeNode.of(1, 1).getId());
    }

    @Test
    public void testNodeId_4() {
        Assert.assertEquals(4, DataTreeNode.of(3, 0).getId());
    }

    @Test
    public void testNodeId_5() {
        Assert.assertEquals(7, DataTreeNode.of(3, 3).getId());
    }

    @Test
    public void testNodeId_6() {
        Assert.assertEquals(13, DataTreeNode.of(7, 5).getId());
    }

    @Test
    public void testNodeId_7() {
        Assert.assertEquals(11, DataTreeNode.of(7, 3).getId());
    }

    @Test
    public void testNodeId_8() {
        Assert.assertEquals(DataTreeNode.of(0, 0), DataTreeNode.ofId(1));
    }

    @Test
    public void testNodeId_9() {
        Assert.assertEquals(DataTreeNode.of(1, 0), DataTreeNode.ofId(2));
    }

    @Test
    public void testNodeId_10() {
        Assert.assertEquals(DataTreeNode.of(1, 1), DataTreeNode.ofId(3));
    }

    @Test
    public void testNodeId_11() {
        Assert.assertEquals(DataTreeNode.of(3, 0), DataTreeNode.ofId(4));
    }

    @Test
    public void testNodeId_12() {
        Assert.assertEquals(DataTreeNode.of(3, 3), DataTreeNode.ofId(7));
    }

    @Test
    public void testNodeId_13() {
        Assert.assertEquals(DataTreeNode.of(7, 5), DataTreeNode.ofId(13));
    }

    @Test
    public void testNodeId_14() {
        Assert.assertEquals(DataTreeNode.of(7, 3), DataTreeNode.ofId(11));
    }
}
