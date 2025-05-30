package org.apache.druid.query;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.apache.druid.java.util.common.HumanReadableBytes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.Granularity;
import org.apache.druid.query.aggregation.CountAggregatorFactory;
import org.apache.druid.query.filter.DimFilter;
import org.apache.druid.query.spec.QuerySegmentSpec;
import org.apache.druid.segment.DimensionHandlerUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Test;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class QueryContextTest_Purified {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private static Long getContextLong(String value) {
        return QueryContexts.getAsLong("dummy", value);
    }

    private static Long getJsonLong(String value) throws JsonProcessingException {
        return JSON_MAPPER.readValue(value, Long.class);
    }

    private static Long getDimensionLong(String value) {
        return DimensionHandlerUtils.getExactLongFromDecimalString(value);
    }

    public static class LegacyContextQuery implements Query<Integer> {

        private final Map<String, Object> context;

        public LegacyContextQuery(Map<String, Object> context) {
            this.context = context;
        }

        @Override
        public DataSource getDataSource() {
            return new TableDataSource("fake");
        }

        @Override
        public boolean hasFilters() {
            return false;
        }

        @Override
        public DimFilter getFilter() {
            return null;
        }

        @Override
        public String getType() {
            return "legacy-context-query";
        }

        @Override
        public QueryRunner<Integer> getRunner(QuerySegmentWalker walker) {
            return new NoopQueryRunner<>();
        }

        @Override
        public List<Interval> getIntervals() {
            return Collections.singletonList(Intervals.ETERNITY);
        }

        @Override
        public Duration getDuration() {
            return getIntervals().get(0).toDuration();
        }

        @Override
        public Granularity getGranularity() {
            return Granularities.ALL;
        }

        @Override
        public DateTimeZone getTimezone() {
            return DateTimeZone.UTC;
        }

        @Override
        public Map<String, Object> getContext() {
            return context;
        }

        @Override
        public Ordering<Integer> getResultOrdering() {
            return Ordering.natural();
        }

        @Override
        public Query<Integer> withQuerySegmentSpec(QuerySegmentSpec spec) {
            return new LegacyContextQuery(context);
        }

        @Override
        public Query<Integer> withId(String id) {
            context.put(BaseQuery.QUERY_ID, id);
            return this;
        }

        @Nullable
        @Override
        public String getId() {
            return (String) context.get(BaseQuery.QUERY_ID);
        }

        @Override
        public Query<Integer> withSubQueryId(String subQueryId) {
            context.put(BaseQuery.SUB_QUERY_ID, subQueryId);
            return this;
        }

        @Nullable
        @Override
        public String getSubQueryId() {
            return (String) context.get(BaseQuery.SUB_QUERY_ID);
        }

        @Override
        public Query<Integer> withDataSource(DataSource dataSource) {
            return this;
        }

        @Override
        public Query<Integer> withOverriddenContext(Map<String, Object> contextOverride) {
            return new LegacyContextQuery(contextOverride);
        }
    }

    @Test
    public void testIsEmpty_1() {
        assertTrue(QueryContext.empty().isEmpty());
    }

    @Test
    public void testIsEmpty_2() {
        assertFalse(QueryContext.of(ImmutableMap.of("k", "v")).isEmpty());
    }

    @Test
    public void testDefaultEnableQueryDebugging_1() {
        assertFalse(QueryContext.empty().isDebug());
    }

    @Test
    public void testDefaultEnableQueryDebugging_2() {
        assertTrue(QueryContext.of(ImmutableMap.of(QueryContexts.ENABLE_DEBUG, true)).isDebug());
    }

    @Test
    public void testIsDecoupled_1() {
        assertFalse(QueryContext.empty().isDecoupledMode());
    }

    @Test
    public void testIsDecoupled_2() {
        assertTrue(QueryContext.of(ImmutableMap.of(QueryContexts.CTX_NATIVE_QUERY_SQL_PLANNING_MODE, QueryContexts.NATIVE_QUERY_SQL_PLANNING_MODE_DECOUPLED)).isDecoupledMode());
    }

    @Test
    public void testIsDecoupled_3() {
        assertFalse(QueryContext.of(ImmutableMap.of(QueryContexts.CTX_NATIVE_QUERY_SQL_PLANNING_MODE, "garbage")).isDecoupledMode());
    }

    @Test
    public void testExtendedFilteredSumRewrite_1() {
        assertTrue(QueryContext.empty().isExtendedFilteredSumRewrite());
    }

    @Test
    public void testExtendedFilteredSumRewrite_2() {
        assertFalse(QueryContext.of(ImmutableMap.of(QueryContexts.EXTENDED_FILTERED_SUM_REWRITE_ENABLED, false)).isExtendedFilteredSumRewrite());
    }
}
