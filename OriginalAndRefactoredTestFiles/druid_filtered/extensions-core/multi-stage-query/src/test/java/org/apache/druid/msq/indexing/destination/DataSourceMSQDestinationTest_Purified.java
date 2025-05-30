package org.apache.druid.msq.indexing.destination;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.data.input.impl.AggregateProjectionSpec;
import org.apache.druid.data.input.impl.DimensionSchema;
import org.apache.druid.data.input.impl.StringDimensionSchema;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.query.aggregation.AggregatorFactory;
import org.apache.druid.query.aggregation.CountAggregatorFactory;
import org.apache.druid.query.aggregation.LongSumAggregatorFactory;
import org.apache.druid.segment.VirtualColumns;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Map;

public class DataSourceMSQDestinationTest_Purified {

    @Test
    public void testBackwardCompatibility_1() throws JsonProcessingException {
        DataSourceMSQDestination destination = new DataSourceMSQDestination("foo1", Granularities.ALL, null, null, null, null, null);
        Assert.assertEquals(SegmentGenerationStageSpec.instance(), destination.getTerminalStageSpec());
    }

    @Test
    public void testBackwardCompatibility_2() throws JsonProcessingException {
        DataSourceMSQDestination dataSourceMSQDestination = new DefaultObjectMapper().readValue("{\"type\":\"dataSource\",\"dataSource\":\"datasource1\",\"segmentGranularity\":\"DAY\",\"rowsInTaskReport\":0,\"destinationResource\":{\"empty\":false,\"present\":true}}", DataSourceMSQDestination.class);
        Assert.assertEquals(SegmentGenerationStageSpec.instance(), dataSourceMSQDestination.getTerminalStageSpec());
    }
}
