package org.apache.hadoop.yarn.nodelabels;

import org.apache.hadoop.thirdparty.com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.yarn.api.records.NodeAttribute;
import org.apache.hadoop.yarn.api.records.NodeAttributeType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestNodeLabelUtil_Purified {

    @Test
    void testIsNodeAttributesEquals_1() {
        assertTrue(NodeLabelUtil.isNodeAttributesEquals(null, null));
    }

    @Test
    void testIsNodeAttributesEquals_2() {
        assertTrue(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(), ImmutableSet.of()));
    }

    @Test
    void testIsNodeAttributesEquals_3_testMerged_3() {
        NodeAttribute nodeAttributeCK1V1 = NodeAttribute.newInstance(NodeAttribute.PREFIX_CENTRALIZED, "K1", NodeAttributeType.STRING, "V1");
        NodeAttribute nodeAttributeCK1V1Copy = NodeAttribute.newInstance(NodeAttribute.PREFIX_CENTRALIZED, "K1", NodeAttributeType.STRING, "V1");
        NodeAttribute nodeAttributeDK1V1 = NodeAttribute.newInstance(NodeAttribute.PREFIX_DISTRIBUTED, "K1", NodeAttributeType.STRING, "V1");
        NodeAttribute nodeAttributeDK1V1Copy = NodeAttribute.newInstance(NodeAttribute.PREFIX_DISTRIBUTED, "K1", NodeAttributeType.STRING, "V1");
        NodeAttribute nodeAttributeDK2V1 = NodeAttribute.newInstance(NodeAttribute.PREFIX_DISTRIBUTED, "K2", NodeAttributeType.STRING, "V1");
        NodeAttribute nodeAttributeDK2V2 = NodeAttribute.newInstance(NodeAttribute.PREFIX_DISTRIBUTED, "K2", NodeAttributeType.STRING, "V2");
        assertTrue(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeCK1V1), ImmutableSet.of(nodeAttributeCK1V1Copy)));
        assertTrue(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeDK1V1), ImmutableSet.of(nodeAttributeDK1V1Copy)));
        assertTrue(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeCK1V1, nodeAttributeDK1V1), ImmutableSet.of(nodeAttributeCK1V1Copy, nodeAttributeDK1V1Copy)));
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeCK1V1), ImmutableSet.of(nodeAttributeDK1V1)));
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeDK1V1), ImmutableSet.of(nodeAttributeDK2V1)));
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeDK2V1), ImmutableSet.of(nodeAttributeDK2V2)));
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeCK1V1), ImmutableSet.of()));
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeCK1V1), ImmutableSet.of(nodeAttributeCK1V1, nodeAttributeDK1V1)));
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(nodeAttributeCK1V1, nodeAttributeDK1V1), ImmutableSet.of(nodeAttributeDK1V1)));
    }

    @Test
    void testIsNodeAttributesEquals_6() {
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(null, ImmutableSet.of()));
    }

    @Test
    void testIsNodeAttributesEquals_7() {
        assertFalse(NodeLabelUtil.isNodeAttributesEquals(ImmutableSet.of(), null));
    }
}
