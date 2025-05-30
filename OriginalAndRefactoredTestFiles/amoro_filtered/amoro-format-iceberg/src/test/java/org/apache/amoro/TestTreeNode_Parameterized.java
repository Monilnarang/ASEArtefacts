package org.apache.amoro;

import org.apache.amoro.data.DataTreeNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestTreeNode_Parameterized {

    private void assertParentNode(DataTreeNode node) {
        Assert.assertEquals(node, node.left().parent());
        Assert.assertEquals(node, node.right().parent());
    }

    @ParameterizedTest
    @MethodSource("Provider_testParentNode_1to8")
    public void testParentNode_1to8(int param1, int param2) {
        assertParentNode(DataTreeNode.of(param1, param2));
    }

    static public Stream<Arguments> Provider_testParentNode_1to8() {
        return Stream.of(arguments(0, 0), arguments(1, 0), arguments(1, 0), arguments(3, 3), arguments(255, 0), arguments(255, 126), arguments(255, 245), arguments(255, 255));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNodeId_1to7")
    public void testNodeId_1to7(int param1, int param2, int param3) {
        Assert.assertEquals(param1, DataTreeNode.of(param2, param3).getId());
    }

    static public Stream<Arguments> Provider_testNodeId_1to7() {
        return Stream.of(arguments(1, 0, 0), arguments(2, 1, 0), arguments(3, 1, 1), arguments(4, 3, 0), arguments(7, 3, 3), arguments(13, 7, 5), arguments(11, 7, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNodeId_8to14")
    public void testNodeId_8to14(int param1, int param2, int param3) {
        Assert.assertEquals(DataTreeNode.of(param1, param2), DataTreeNode.ofId(param3));
    }

    static public Stream<Arguments> Provider_testNodeId_8to14() {
        return Stream.of(arguments(0, 0, 1), arguments(1, 0, 2), arguments(1, 1, 3), arguments(3, 0, 4), arguments(3, 3, 7), arguments(7, 5, 13), arguments(7, 3, 11));
    }
}
