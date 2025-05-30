package org.apache.druid.segment.filter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.java.util.common.ISE;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.query.dimension.DimensionSpec;
import org.apache.druid.query.filter.Filter;
import org.apache.druid.segment.ColumnSelectorFactory;
import org.apache.druid.segment.ColumnValueSelector;
import org.apache.druid.segment.DimensionSelector;
import org.apache.druid.segment.column.ColumnCapabilities;
import org.apache.druid.segment.filter.cnf.CNFFilterExplosionException;
import org.apache.druid.segment.filter.cnf.CalciteCnfHelper;
import org.apache.druid.segment.filter.cnf.HiveCnfHelper;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class FilterCnfConversionTest_Purified {

    private void assertFilter(Filter original, Filter expectedConverted, Filter actualConverted) {
        assertEquivalent(original, expectedConverted);
        Assert.assertEquals(expectedConverted, actualConverted);
    }

    private void assertEquivalent(Filter f1, Filter f2) {
        final Set<SelectorFilter> s1 = searchForSelectors(f1);
        final Set<SelectorFilter> s2 = searchForSelectors(f2);
        Assert.assertEquals(s1, s2);
        final List<SelectorFilter> selectorFilters = new ArrayList<>(s1);
        List<Map<SelectorFilter, Boolean>> truthValues = populate(selectorFilters, selectorFilters.size() - 1);
        for (Map<SelectorFilter, Boolean> truthValue : truthValues) {
            Assert.assertEquals(evaluateFilterWith(f1, truthValue), evaluateFilterWith(f2, truthValue));
        }
    }

    private List<Map<SelectorFilter, Boolean>> populate(List<SelectorFilter> selectorFilters, int cursor) {
        final List<Map<SelectorFilter, Boolean>> populateFalse;
        final List<Map<SelectorFilter, Boolean>> populateTrue;
        if (cursor == 0) {
            Map<SelectorFilter, Boolean> mapForFalse = new HashMap<>();
            Map<SelectorFilter, Boolean> mapForTrue = new HashMap<>();
            for (SelectorFilter eachFilter : selectorFilters) {
                mapForFalse.put(eachFilter, false);
                mapForTrue.put(eachFilter, false);
            }
            populateFalse = new ArrayList<>();
            populateFalse.add(mapForFalse);
            populateTrue = new ArrayList<>();
            populateTrue.add(mapForTrue);
        } else {
            final List<Map<SelectorFilter, Boolean>> populated = populate(selectorFilters, cursor - 1);
            populateFalse = new ArrayList<>(populated.size());
            populateTrue = new ArrayList<>(populated.size());
            for (Map<SelectorFilter, Boolean> eachMap : populated) {
                populateFalse.add(new HashMap<>(eachMap));
                populateTrue.add(new HashMap<>(eachMap));
            }
        }
        for (Map<SelectorFilter, Boolean> eachMap : populateTrue) {
            eachMap.put(selectorFilters.get(cursor), true);
        }
        final List<Map<SelectorFilter, Boolean>> allPopulated = new ArrayList<>(populateFalse);
        allPopulated.addAll(populateTrue);
        return allPopulated;
    }

    private Set<SelectorFilter> searchForSelectors(Filter filter) {
        Set<SelectorFilter> found = new HashSet<>();
        visitSelectorFilters(filter, selectorFilter -> {
            found.add(selectorFilter);
            return selectorFilter;
        });
        return found;
    }

    private boolean evaluateFilterWith(Filter filter, Map<SelectorFilter, Boolean> values) {
        Filter rewrittenFilter = visitSelectorFilters(filter, selectorFilter -> {
            Boolean truth = values.get(selectorFilter);
            if (truth == null) {
                throw new ISE("Can't find truth value for selectorFilter[%s]", selectorFilter);
            }
            return truth ? TrueFilter.instance() : FalseFilter.instance();
        });
        return rewrittenFilter.makeMatcher(new ColumnSelectorFactory() {

            @Override
            public DimensionSelector makeDimensionSelector(DimensionSpec dimensionSpec) {
                return null;
            }

            @Override
            public ColumnValueSelector makeColumnValueSelector(String columnName) {
                return null;
            }

            @Nullable
            @Override
            public ColumnCapabilities getColumnCapabilities(String column) {
                return null;
            }
        }).matches(false);
    }

    private Filter visitSelectorFilters(Filter filter, Function<SelectorFilter, Filter> visitAction) {
        if (filter instanceof AndFilter) {
            List<Filter> newChildren = new ArrayList<>();
            for (Filter child : ((AndFilter) filter).getFilters()) {
                newChildren.add(visitSelectorFilters(child, visitAction));
            }
            return new AndFilter(newChildren);
        } else if (filter instanceof OrFilter) {
            List<Filter> newChildren = new ArrayList<>();
            for (Filter child : ((OrFilter) filter).getFilters()) {
                newChildren.add(visitSelectorFilters(child, visitAction));
            }
            return new OrFilter(newChildren);
        } else if (filter instanceof NotFilter) {
            Filter child = ((NotFilter) filter).getBaseFilter();
            return new NotFilter(visitSelectorFilters(child, visitAction));
        } else if (filter instanceof SelectorFilter) {
            return visitAction.apply((SelectorFilter) filter);
        }
        return filter;
    }

    @Test
    public void testTrueFalseFilterRequiredColumnRewrite_1() {
        Assert.assertTrue(TrueFilter.instance().supportsRequiredColumnRewrite());
    }

    @Test
    public void testTrueFalseFilterRequiredColumnRewrite_2() {
        Assert.assertTrue(FalseFilter.instance().supportsRequiredColumnRewrite());
    }

    @Test
    public void testTrueFalseFilterRequiredColumnRewrite_3() {
        Assert.assertEquals(TrueFilter.instance(), TrueFilter.instance().rewriteRequiredColumns(ImmutableMap.of()));
    }

    @Test
    public void testTrueFalseFilterRequiredColumnRewrite_4() {
        Assert.assertEquals(FalseFilter.instance(), FalseFilter.instance().rewriteRequiredColumns(ImmutableMap.of()));
    }
}
