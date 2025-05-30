package org.apache.druid.query.aggregation.variance;

import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.query.Druids;
import org.apache.druid.query.aggregation.CountAggregatorFactory;
import org.apache.druid.query.aggregation.post.FieldAccessPostAggregator;
import org.apache.druid.query.aggregation.post.FinalizingFieldAccessPostAggregator;
import org.apache.druid.query.timeseries.TimeseriesQuery;
import org.apache.druid.query.timeseries.TimeseriesQueryQueryToolChest;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;

public class VarianceAggregatorFactoryTest_Purified extends InitializedNullHandlingTest {

    @Test
    public void testWithName_1_testMerged_1() {
        VarianceAggregatorFactory varianceAggregatorFactory = new VarianceAggregatorFactory("variance", "col");
        Assert.assertEquals(varianceAggregatorFactory, varianceAggregatorFactory.withName("variance"));
        Assert.assertEquals("newTest", varianceAggregatorFactory.withName("newTest").getName());
    }

    @Test
    public void testWithName_3_testMerged_2() {
        VarianceFoldingAggregatorFactory varianceFoldingAggregatorFactory = new VarianceFoldingAggregatorFactory("varianceFold", "col", null);
        Assert.assertEquals(varianceFoldingAggregatorFactory, varianceFoldingAggregatorFactory.withName("varianceFold"));
        Assert.assertEquals("newTest", varianceFoldingAggregatorFactory.withName("newTest").getName());
    }
}
