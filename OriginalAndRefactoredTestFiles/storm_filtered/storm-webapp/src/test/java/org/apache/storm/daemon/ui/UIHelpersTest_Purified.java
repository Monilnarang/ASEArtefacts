package org.apache.storm.daemon.ui;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.generated.BoltAggregateStats;
import org.apache.storm.generated.CommonAggregateStats;
import org.apache.storm.generated.ComponentAggregateStats;
import org.apache.storm.generated.ErrorInfo;
import org.apache.storm.generated.SpecificAggregateStats;
import org.apache.storm.generated.SpoutAggregateStats;
import org.apache.storm.generated.TopologyPageInfo;
import org.apache.storm.generated.TopologyStats;
import org.apache.storm.utils.Time;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UIHelpersTest_Purified {

    private static final String TOPOLOGY_ID = "Test-Topology-Id";

    private static final long TOPOLOGY_MESSAGE_TIMEOUT_SECS = 100L;

    private static final String WINDOW = ":all-time";

    private TopologyPageInfo topoPageInfo;

    private Time.SimulatedTime mockTime;

    @BeforeEach
    void setup() {
        final Map<String, Object> topologyConfig = new HashMap<>();
        topologyConfig.put(Config.TOPOLOGY_MESSAGE_TIMEOUT_SECS, TOPOLOGY_MESSAGE_TIMEOUT_SECS);
        final String topoConfigJson = JSONValue.toJSONString(topologyConfig);
        final TopologyStats topologyStats = new TopologyStats();
        topologyStats.set_window_to_emitted(new HashMap<>());
        topologyStats.set_window_to_transferred(new HashMap<>());
        topologyStats.set_window_to_acked(new HashMap<>());
        topologyStats.set_window_to_complete_latencies_ms(new HashMap<>());
        topologyStats.set_window_to_failed(new HashMap<>());
        final Map<String, ComponentAggregateStats> idToSpoutAggStats = new HashMap<>();
        final Map<String, ComponentAggregateStats> idToBoltAggStats = new HashMap<>();
        topoPageInfo = new TopologyPageInfo();
        topoPageInfo.set_topology_conf(topoConfigJson);
        topoPageInfo.set_id(TOPOLOGY_ID);
        topoPageInfo.set_topology_stats(topologyStats);
        topoPageInfo.set_id_to_spout_agg_stats(idToSpoutAggStats);
        topoPageInfo.set_id_to_bolt_agg_stats(idToBoltAggStats);
        mockTime = new Time.SimulatedTime(null);
    }

    @AfterEach
    void cleanup() {
        mockTime.close();
    }

    private void addBoltStats(final String boltId, final ComponentAggregateStats aggregateStats) {
        topoPageInfo.get_id_to_bolt_agg_stats().put(boltId, aggregateStats);
    }

    private void addSpoutStats(final String spoutId, final ComponentAggregateStats aggregateStats) {
        topoPageInfo.get_id_to_spout_agg_stats().put(spoutId, aggregateStats);
    }

    private ComponentAggregateStats buildBoltAggregateStatsBase() {
        final CommonAggregateStats commonStats = new CommonAggregateStats();
        final BoltAggregateStats boltAggregateStats = new BoltAggregateStats();
        final SpecificAggregateStats specificStats = new SpecificAggregateStats();
        specificStats.set_bolt(boltAggregateStats);
        final ComponentAggregateStats aggregateStats = new ComponentAggregateStats();
        aggregateStats.set_common_stats(commonStats);
        aggregateStats.set_specific_stats(specificStats);
        return aggregateStats;
    }

    private ComponentAggregateStats buildSpoutAggregateStatsBase() {
        final CommonAggregateStats commonStats = new CommonAggregateStats();
        final SpoutAggregateStats spoutAggregateStats = new SpoutAggregateStats();
        final SpecificAggregateStats specificStats = new SpecificAggregateStats();
        specificStats.set_spout(spoutAggregateStats);
        final ComponentAggregateStats aggregateStats = new ComponentAggregateStats();
        aggregateStats.set_common_stats(commonStats);
        aggregateStats.set_specific_stats(specificStats);
        return aggregateStats;
    }

    private Map<String, Object> getBoltStatsFromTopologySummaryResult(final Map<String, Object> result, final String boltId) {
        assertNotNull(result.get("bolts"), "Should have non-null 'bolts' property");
        final List<HashMap<String, Object>> bolts = (List<HashMap<String, Object>>) result.get("bolts");
        return bolts.stream().filter((entry) -> boltId.equals(entry.get("boltId"))).findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to find entry for boltId '" + boltId + "'"));
    }

    private Map<String, Object> getSpoutStatsFromTopologySummaryResult(final Map<String, Object> result, final String spoutId) {
        assertNotNull(result.get("spouts"), "Should have non-null 'spouts' property");
        final List<HashMap<String, Object>> bolts = (List<HashMap<String, Object>>) result.get("spouts");
        return bolts.stream().filter((entry) -> spoutId.equals(entry.get("spoutId"))).findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to find entry for spoutId '" + spoutId + "'"));
    }

    @Test
    public void testSanitizeStreamName_1() {
        assertEquals("my-stream_with.all_characterClasses____", UIHelpers.sanitizeStreamName("my-stream:with.all_characterClasses1/\\2"));
    }

    @Test
    public void testSanitizeStreamName_2() {
        assertEquals("_s_foo", UIHelpers.sanitizeStreamName("3foo"));
    }

    @Test
    public void testSanitizeStreamName_3() {
        assertEquals("_s", UIHelpers.sanitizeStreamName(""));
    }
}
