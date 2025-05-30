package org.apache.storm.utils;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.shade.com.google.common.collect.ImmutableList;
import org.apache.storm.shade.com.google.common.collect.ImmutableMap;
import org.apache.storm.shade.com.google.common.collect.ImmutableSet;
import org.apache.storm.testing.TestWordCounter;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.thrift.transport.TTransportException;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.storm.utils.Utils.handleUncaughtException;
import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(UtilsTest.class);

    private Map<String, Object> topologyMockMap(String value) {
        return mockMap(Config.STORM_ZOOKEEPER_TOPOLOGY_AUTH_SCHEME, value);
    }

    private Map<String, Object> mockMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private Map<String, Object> serverMockMap(String value) {
        return mockMap(Config.STORM_ZOOKEEPER_AUTH_SCHEME, value);
    }

    private Map<String, Object> emptyMockMap() {
        return new HashMap<>();
    }

    private void doParseJvmHeapMemByChildOptsTest(String message, String opt, double expected) {
        doParseJvmHeapMemByChildOptsTest(message, Collections.singletonList(opt), expected);
    }

    private void doParseJvmHeapMemByChildOptsTest(String message, List<String> opts, double expected) {
        assertEquals(expected, Utils.parseJvmHeapMemByChildOpts(opts, 123.0), 0, message);
    }

    @Test
    public void isZkAuthenticationConfiguredTopologyTest_1() {
        assertFalse(Utils.isZkAuthenticationConfiguredTopology(null), "Returns null if given null config");
    }

    @Test
    public void isZkAuthenticationConfiguredTopologyTest_2() {
        assertFalse(Utils.isZkAuthenticationConfiguredTopology(emptyMockMap()), "Returns false if scheme key is missing");
    }

    @Test
    public void isZkAuthenticationConfiguredTopologyTest_3() {
        assertFalse(Utils.isZkAuthenticationConfiguredTopology(topologyMockMap(null)), "Returns false if scheme value is null");
    }

    @Test
    public void isZkAuthenticationConfiguredTopologyTest_4() {
        assertTrue(Utils.isZkAuthenticationConfiguredTopology(topologyMockMap("foobar")), "Returns true if scheme value is string");
    }

    @Test
    public void isZkAuthenticationConfiguredStormServerTest_1() {
        assertFalse(Utils.isZkAuthenticationConfiguredStormServer(null), "Returns false if given null config");
    }

    @Test
    public void isZkAuthenticationConfiguredStormServerTest_2() {
        assertFalse(Utils.isZkAuthenticationConfiguredStormServer(emptyMockMap()), "Returns false if scheme key is missing");
    }

    @Test
    public void isZkAuthenticationConfiguredStormServerTest_3() {
        assertFalse(Utils.isZkAuthenticationConfiguredStormServer(serverMockMap(null)), "Returns false if scheme value is null");
    }

    @Test
    public void isZkAuthenticationConfiguredStormServerTest_4() {
        assertTrue(Utils.isZkAuthenticationConfiguredStormServer(serverMockMap("foobar")), "Returns true if scheme value is string");
    }
}
