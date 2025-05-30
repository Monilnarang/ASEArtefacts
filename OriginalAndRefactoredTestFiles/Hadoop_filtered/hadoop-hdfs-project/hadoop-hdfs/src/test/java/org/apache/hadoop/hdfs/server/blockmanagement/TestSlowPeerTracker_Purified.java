package org.apache.hadoop.hdfs.server.blockmanagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.server.protocol.OutlierMetrics;
import org.apache.hadoop.util.FakeTimer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Set;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestSlowPeerTracker_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestSlowPeerTracker.class);

    @Rule
    public Timeout testTimeout = new Timeout(300_000);

    private Configuration conf;

    private SlowPeerTracker tracker;

    private FakeTimer timer;

    private long reportValidityMs;

    private static final ObjectReader READER = new ObjectMapper().readerFor(new TypeReference<Set<SlowPeerJsonReport>>() {
    });

    @Before
    public void setup() {
        conf = new HdfsConfiguration();
        timer = new FakeTimer();
        tracker = new SlowPeerTracker(conf, timer);
        reportValidityMs = tracker.getReportValidityMs();
    }

    private boolean isNodeInReports(Set<SlowPeerJsonReport> reports, String node) {
        for (SlowPeerJsonReport report : reports) {
            if (report.getSlowNode().equalsIgnoreCase(node)) {
                return true;
            }
        }
        return false;
    }

    private Set<SlowPeerJsonReport> getAndDeserializeJson() throws IOException {
        final String json = tracker.getJson();
        LOG.info("Got JSON: {}", json);
        return READER.readValue(json);
    }

    @Test
    public void testEmptyReports_1() {
        assertTrue(tracker.getReportsForAllDataNodes().isEmpty());
    }

    @Test
    public void testEmptyReports_2() {
        assertTrue(tracker.getReportsForNode("noSuchNode").isEmpty());
    }
}
