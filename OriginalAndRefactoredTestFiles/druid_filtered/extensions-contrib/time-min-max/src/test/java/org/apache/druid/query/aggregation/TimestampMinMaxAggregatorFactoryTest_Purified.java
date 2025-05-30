package org.apache.druid.query.aggregation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.query.Druids;
import org.apache.druid.query.aggregation.post.FieldAccessPostAggregator;
import org.apache.druid.query.aggregation.post.FinalizingFieldAccessPostAggregator;
import org.apache.druid.query.timeseries.TimeseriesQuery;
import org.apache.druid.query.timeseries.TimeseriesQueryQueryToolChest;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.junit.Assert;
import org.junit.Test;

public class TimestampMinMaxAggregatorFactoryTest_Purified {

    private static final ObjectMapper JSON_MAPPER = TestHelper.makeJsonMapper();

    @Test
    public void testSerde_1_testMerged_1() throws JsonProcessingException {
        TimestampMaxAggregatorFactory maxAgg = new TimestampMaxAggregatorFactory("timeMax", "__time", null);
        Assert.assertEquals(maxAgg, JSON_MAPPER.readValue(JSON_MAPPER.writeValueAsString(maxAgg), TimestampMaxAggregatorFactory.class));
        Assert.assertEquals(maxAgg.getCombiningFactory(), JSON_MAPPER.readValue(JSON_MAPPER.writeValueAsString(maxAgg.getCombiningFactory()), TimestampMaxAggregatorFactory.class));
    }

    @Test
    public void testSerde_3_testMerged_2() throws JsonProcessingException {
        TimestampMinAggregatorFactory minAgg = new TimestampMinAggregatorFactory("timeMin", "__time", null);
        Assert.assertEquals(minAgg, JSON_MAPPER.readValue(JSON_MAPPER.writeValueAsString(minAgg), TimestampMinAggregatorFactory.class));
        Assert.assertEquals(minAgg.getCombiningFactory(), JSON_MAPPER.readValue(JSON_MAPPER.writeValueAsString(minAgg.getCombiningFactory()), TimestampMinAggregatorFactory.class));
    }

    @Test
    public void testWithName_1_testMerged_1() {
        TimestampMaxAggregatorFactory maxAgg = new TimestampMaxAggregatorFactory("timeMax", "__time", null);
        Assert.assertEquals(maxAgg, maxAgg.withName("timeMax"));
        Assert.assertEquals("newTest", maxAgg.withName("newTest").getName());
    }

    @Test
    public void testWithName_3_testMerged_2() {
        TimestampMinAggregatorFactory minAgg = new TimestampMinAggregatorFactory("timeMin", "__time", null);
        Assert.assertEquals(minAgg, minAgg.withName("timeMin"));
        Assert.assertEquals("newTest", minAgg.withName("newTest").getName());
    }
}
