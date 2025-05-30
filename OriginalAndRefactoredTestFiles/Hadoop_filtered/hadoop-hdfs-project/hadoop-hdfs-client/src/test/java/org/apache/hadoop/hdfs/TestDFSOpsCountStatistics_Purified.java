package org.apache.hadoop.hdfs;

import org.apache.commons.lang3.RandomUtils;
import org.apache.hadoop.fs.StorageStatistics.LongStatistic;
import org.apache.hadoop.hdfs.DFSOpsCountStatistics.OpType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import static org.apache.hadoop.util.concurrent.HadoopExecutors.newFixedThreadPool;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestDFSOpsCountStatistics_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestDFSOpsCountStatistics.class);

    private static final String NO_SUCH_OP = "no-such-dfs-operation-dude";

    private final DFSOpsCountStatistics statistics = new DFSOpsCountStatistics();

    private final Map<OpType, AtomicLong> expectedOpsCountMap = new HashMap<>();

    @Rule
    public final Timeout globalTimeout = new Timeout(10 * 1000);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        for (OpType opType : OpType.values()) {
            expectedOpsCountMap.put(opType, new AtomicLong());
        }
        incrementOpsCountByRandomNumbers();
    }

    private void incrementOpsCountByRandomNumbers() {
        for (OpType opType : OpType.values()) {
            final Long randomCount = RandomUtils.nextLong(0, 100);
            expectedOpsCountMap.get(opType).addAndGet(randomCount);
            for (long i = 0; i < randomCount; i++) {
                statistics.incrementOpCounter(opType);
            }
        }
    }

    private void verifyStatistics() {
        for (OpType opType : OpType.values()) {
            assertNotNull(expectedOpsCountMap.get(opType));
            assertNotNull(statistics.getLong(opType.getSymbol()));
            assertEquals("Not expected count for operation " + opType.getSymbol(), expectedOpsCountMap.get(opType).longValue(), statistics.getLong(opType.getSymbol()).longValue());
        }
    }

    @Test
    public void testGetLong_1() {
        assertNull(statistics.getLong(null));
    }

    @Test
    public void testGetLong_2() {
        assertNull(statistics.getLong(NO_SUCH_OP));
    }
}
