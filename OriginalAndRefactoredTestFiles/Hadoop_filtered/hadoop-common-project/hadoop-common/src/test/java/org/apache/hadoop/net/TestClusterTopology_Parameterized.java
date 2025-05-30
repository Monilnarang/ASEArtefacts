package org.apache.hadoop.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestClusterTopology_Parameterized extends Assert {

    public static class NodeElement implements Node {

        private String location;

        private String name;

        private Node parent;

        private int level;

        public NodeElement(String name) {
            this.name = name;
        }

        @Override
        public String getNetworkLocation() {
            return location;
        }

        @Override
        public void setNetworkLocation(String location) {
            this.location = location;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Node getParent() {
            return parent;
        }

        @Override
        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public void setLevel(int i) {
            this.level = i;
        }
    }

    private NodeElement getNewNode(String name, String rackLocation) {
        NodeElement node = new NodeElement(name);
        node.setNetworkLocation(rackLocation);
        return node;
    }

    private NodeElement getNewNode(NetworkTopology cluster, String name, String rackLocation) {
        NodeElement node = getNewNode(name, rackLocation);
        cluster.add(node);
        return node;
    }

    @ParameterizedTest
    @MethodSource("Provider_testNodeBaseNormalizeRemoveLeadingSlash_1to5")
    public void testNodeBaseNormalizeRemoveLeadingSlash_1to5(String param1, String param2) {
        assertEquals(param1, NodeBase.normalize(param2));
    }

    static public Stream<Arguments> Provider_testNodeBaseNormalizeRemoveLeadingSlash_1to5() {
        return Stream.of(arguments("/d1", "/d1///"), arguments("/d1", "/d1/"), arguments("/d1", "/d1"), arguments("", "///"), arguments("", "/"));
    }
}
