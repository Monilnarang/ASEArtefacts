package org.apache.druid.query.aggregation;

import com.google.common.collect.ImmutableList;
import org.apache.druid.query.filter.SelectorDimFilter;
import org.apache.druid.query.filter.TrueDimFilter;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FilteredAggregatorFactoryTest_Parameterized extends InitializedNullHandlingTest {

    @Test
    public void testSimpleNaming_3() {
        Assert.assertEquals("delegateName", new FilteredAggregatorFactory(new CountAggregatorFactory("delegateName"), TrueDimFilter.instance(), null).getName());
    }

    @Test
    public void testNameOfCombiningFactory_3() {
        Assert.assertEquals("delegateName", new FilteredAggregatorFactory(new CountAggregatorFactory("delegateName"), TrueDimFilter.instance(), null).getCombiningFactory().getName());
    }

    @ParameterizedTest
    @MethodSource("Provider_testSimpleNaming_1to2")
    public void testSimpleNaming_1to2(String param1, String param2, String param3) {
        Assert.assertEquals(param1, new FilteredAggregatorFactory(new CountAggregatorFactory(param3), TrueDimFilter.instance(), param2).getName());
    }

    static public Stream<Arguments> Provider_testSimpleNaming_1to2() {
        return Stream.of(arguments("overrideName", "overrideName", "foo"), arguments("delegateName", "", "delegateName"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNameOfCombiningFactory_1to2")
    public void testNameOfCombiningFactory_1to2(String param1, String param2, String param3) {
        Assert.assertEquals(param1, new FilteredAggregatorFactory(new CountAggregatorFactory(param3), TrueDimFilter.instance(), param2).getCombiningFactory().getName());
    }

    static public Stream<Arguments> Provider_testNameOfCombiningFactory_1to2() {
        return Stream.of(arguments("overrideName", "overrideName", "foo"), arguments("delegateName", "", "delegateName"));
    }
}
