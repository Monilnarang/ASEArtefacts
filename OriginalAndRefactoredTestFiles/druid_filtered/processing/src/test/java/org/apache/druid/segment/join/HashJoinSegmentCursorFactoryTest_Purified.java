package org.apache.druid.segment.join;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.query.filter.ExpressionDimFilter;
import org.apache.druid.query.filter.Filter;
import org.apache.druid.query.filter.InDimFilter;
import org.apache.druid.query.filter.OrDimFilter;
import org.apache.druid.query.filter.SelectorDimFilter;
import org.apache.druid.segment.CursorBuildSpec;
import org.apache.druid.segment.CursorFactory;
import org.apache.druid.segment.ReferenceCountingSegment;
import org.apache.druid.segment.TopNOptimizationInspector;
import org.apache.druid.segment.VirtualColumns;
import org.apache.druid.segment.column.ColumnCapabilities;
import org.apache.druid.segment.column.ValueType;
import org.apache.druid.segment.filter.Filters;
import org.apache.druid.segment.filter.SelectorFilter;
import org.apache.druid.segment.join.filter.JoinFilterPreAnalysis;
import org.apache.druid.segment.join.lookup.LookupJoinable;
import org.apache.druid.segment.join.table.IndexedTableJoinable;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HashJoinSegmentCursorFactoryTest_Purified extends BaseHashJoinSegmentCursorFactoryTest {

    @Test
    public void test_hasBuiltInFiltersForSingleJoinableClauseWithVariousJoinTypes_1() {
        Assert.assertFalse(makeFactToCountrySegment(JoinType.INNER).as(TopNOptimizationInspector.class).areAllDictionaryIdsPresent());
    }

    @Test
    public void test_hasBuiltInFiltersForSingleJoinableClauseWithVariousJoinTypes_2() {
        Assert.assertTrue(makeFactToCountrySegment(JoinType.LEFT).as(TopNOptimizationInspector.class).areAllDictionaryIdsPresent());
    }

    @Test
    public void test_hasBuiltInFiltersForSingleJoinableClauseWithVariousJoinTypes_3() {
        Assert.assertFalse(makeFactToCountrySegment(JoinType.RIGHT).as(TopNOptimizationInspector.class).areAllDictionaryIdsPresent());
    }

    @Test
    public void test_hasBuiltInFiltersForSingleJoinableClauseWithVariousJoinTypes_4() {
        Assert.assertTrue(makeFactToCountrySegment(JoinType.FULL).as(TopNOptimizationInspector.class).areAllDictionaryIdsPresent());
    }

    @Test
    public void test_hasBuiltInFiltersForSingleJoinableClauseWithVariousJoinTypes_5() {
        HashJoinSegment segment = new HashJoinSegment(ReferenceCountingSegment.wrapRootGenerationSegment(factSegment), null, ImmutableList.of(new JoinableClause(FACT_TO_COUNTRY_ON_ISO_CODE_PREFIX, new IndexedTableJoinable(countriesTable), JoinType.INNER, JoinConditionAnalysis.forExpression("'true'", FACT_TO_COUNTRY_ON_ISO_CODE_PREFIX, ExprMacroTable.nil()))), null);
        TopNOptimizationInspector inspector = segment.as(TopNOptimizationInspector.class);
        Assert.assertTrue(inspector.areAllDictionaryIdsPresent());
    }
}
