package org.apache.druid.segment.filter;

import com.google.common.collect.ImmutableMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.query.filter.ExpressionDimFilter;
import org.apache.druid.query.filter.Filter;
import org.junit.Assert;
import org.junit.Test;

public class NotFilterTest_Purified {

    @Test
    public void testRequiredColumnRewrite_1_testMerged_1() {
        Filter filter = new NotFilter(new SelectorFilter("dim0", "B"));
        Filter filter2 = new NotFilter(new SelectorFilter("dim1", "B"));
        Assert.assertTrue(filter.supportsRequiredColumnRewrite());
        Assert.assertTrue(filter2.supportsRequiredColumnRewrite());
        Filter rewrittenFilter = filter.rewriteRequiredColumns(ImmutableMap.of("dim0", "dim1"));
        Assert.assertEquals(filter2, rewrittenFilter);
    }

    @Test
    public void testRequiredColumnRewrite_4() {
        Filter filter3 = new NotFilter(new ExpressionDimFilter("dim0 == 'B'", ExprMacroTable.nil()).toFilter());
        Assert.assertFalse(filter3.supportsRequiredColumnRewrite());
    }
}
