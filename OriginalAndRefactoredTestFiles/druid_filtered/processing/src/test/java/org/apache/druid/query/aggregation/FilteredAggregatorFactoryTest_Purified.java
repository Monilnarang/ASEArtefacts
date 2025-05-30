package org.apache.druid.query.aggregation;

import com.google.common.collect.ImmutableList;
import org.apache.druid.query.filter.SelectorDimFilter;
import org.apache.druid.query.filter.TrueDimFilter;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;

public class FilteredAggregatorFactoryTest_Purified extends InitializedNullHandlingTest {

    @Test
    public void testSimpleNaming_1() {
        Assert.assertEquals("overrideName", new FilteredAggregatorFactory(new CountAggregatorFactory("foo"), TrueDimFilter.instance(), "overrideName").getName());
    }

    @Test
    public void testSimpleNaming_2() {
        Assert.assertEquals("delegateName", new FilteredAggregatorFactory(new CountAggregatorFactory("delegateName"), TrueDimFilter.instance(), "").getName());
    }

    @Test
    public void testSimpleNaming_3() {
        Assert.assertEquals("delegateName", new FilteredAggregatorFactory(new CountAggregatorFactory("delegateName"), TrueDimFilter.instance(), null).getName());
    }

    @Test
    public void testNameOfCombiningFactory_1() {
        Assert.assertEquals("overrideName", new FilteredAggregatorFactory(new CountAggregatorFactory("foo"), TrueDimFilter.instance(), "overrideName").getCombiningFactory().getName());
    }

    @Test
    public void testNameOfCombiningFactory_2() {
        Assert.assertEquals("delegateName", new FilteredAggregatorFactory(new CountAggregatorFactory("delegateName"), TrueDimFilter.instance(), "").getCombiningFactory().getName());
    }

    @Test
    public void testNameOfCombiningFactory_3() {
        Assert.assertEquals("delegateName", new FilteredAggregatorFactory(new CountAggregatorFactory("delegateName"), TrueDimFilter.instance(), null).getCombiningFactory().getName());
    }
}
