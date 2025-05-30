package org.apache.flink.runtime.jobgraph.topology;

import org.apache.flink.runtime.jobgraph.IntermediateDataSet;
import org.apache.flink.runtime.jobgraph.IntermediateDataSetID;
import org.apache.flink.runtime.jobgraph.JobEdge;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.util.IterableUtils;
import org.apache.flink.util.TestLogger;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import static org.apache.flink.runtime.executiongraph.ExecutionGraphTestUtils.createNoOpVertex;
import static org.apache.flink.runtime.io.network.partition.ResultPartitionType.PIPELINED;
import static org.apache.flink.runtime.jobgraph.DistributionPattern.ALL_TO_ALL;
import static org.apache.flink.runtime.jobgraph.topology.DefaultLogicalResultTest.assertResultsEquals;
import static org.apache.flink.runtime.util.JobVertexConnectionUtils.connectNewDataSetAsInput;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DefaultLogicalVertexTest_Purified extends TestLogger {

    private JobVertex upstreamJobVertex;

    private DefaultLogicalVertex upstreamLogicalVertex;

    private JobVertex downstreamJobVertex;

    private DefaultLogicalVertex downstreamLogicalVertex;

    private Map<IntermediateDataSetID, IntermediateDataSet> resultMap;

    private Set<IntermediateDataSet> results;

    @Before
    public void setUp() throws Exception {
        buildVerticesAndResults();
        upstreamLogicalVertex = new DefaultLogicalVertex(upstreamJobVertex, rid -> new DefaultLogicalResult(resultMap.get(rid), vid -> null));
        downstreamLogicalVertex = new DefaultLogicalVertex(downstreamJobVertex, rid -> new DefaultLogicalResult(resultMap.get(rid), vid -> null));
    }

    private void buildVerticesAndResults() {
        resultMap = new HashMap<>();
        results = new HashSet<>();
        final int parallelism = 3;
        upstreamJobVertex = createNoOpVertex(parallelism);
        downstreamJobVertex = createNoOpVertex(parallelism);
        for (int i = 0; i < 5; i++) {
            final JobEdge edge = connectNewDataSetAsInput(downstreamJobVertex, upstreamJobVertex, ALL_TO_ALL, PIPELINED);
            final IntermediateDataSet consumedDataSet = edge.getSource();
            results.add(consumedDataSet);
            resultMap.put(consumedDataSet.getId(), consumedDataSet);
        }
    }

    static void assertVerticesEquals(final Iterable<JobVertex> jobVertices, final Iterable<DefaultLogicalVertex> logicalVertices) {
        final Map<JobVertexID, DefaultLogicalVertex> logicalVertextMap = IterableUtils.toStream(logicalVertices).collect(Collectors.toMap(DefaultLogicalVertex::getId, Function.identity()));
        for (JobVertex jobVertex : jobVertices) {
            final DefaultLogicalVertex logicalVertex = logicalVertextMap.remove(jobVertex.getID());
            assertNotNull(logicalVertex);
            assertVertexInfoEquals(jobVertex, logicalVertex);
        }
        assertEquals(0, logicalVertextMap.size());
    }

    static void assertVertexInfoEquals(final JobVertex jobVertex, final DefaultLogicalVertex logicalVertex) {
        assertEquals(jobVertex.getID(), logicalVertex.getId());
    }

    @Test
    public void testConstructor_1() {
        assertVertexInfoEquals(upstreamJobVertex, upstreamLogicalVertex);
    }

    @Test
    public void testConstructor_2() {
        assertVertexInfoEquals(downstreamJobVertex, downstreamLogicalVertex);
    }
}
