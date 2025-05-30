package org.apache.druid.query.aggregation.momentsketch.aggregator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.query.Druids;
import org.apache.druid.query.aggregation.CountAggregatorFactory;
import org.apache.druid.query.aggregation.post.FieldAccessPostAggregator;
import org.apache.druid.query.aggregation.post.FinalizingFieldAccessPostAggregator;
import org.apache.druid.query.timeseries.TimeseriesQuery;
import org.apache.druid.query.timeseries.TimeseriesQueryQueryToolChest;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.junit.Assert;
import org.junit.Test;

public class MomentSketchAggregatorFactoryTest_Purified {

    @Test
    public void testWithName_1_testMerged_1() {
        MomentSketchAggregatorFactory sketchAggFactory = new MomentSketchAggregatorFactory("name", "fieldName", 128, true);
        Assert.assertEquals(sketchAggFactory, sketchAggFactory.withName("name"));
        Assert.assertEquals("newTest", sketchAggFactory.withName("newTest").getName());
    }

    @Test
    public void testWithName_3_testMerged_2() {
        MomentSketchMergeAggregatorFactory sketchMergeAggregatorFactory = new MomentSketchMergeAggregatorFactory("name", 128, true);
        Assert.assertEquals(sketchMergeAggregatorFactory, sketchMergeAggregatorFactory.withName("name"));
        Assert.assertEquals("newTest", sketchMergeAggregatorFactory.withName("newTest").getName());
    }
}
