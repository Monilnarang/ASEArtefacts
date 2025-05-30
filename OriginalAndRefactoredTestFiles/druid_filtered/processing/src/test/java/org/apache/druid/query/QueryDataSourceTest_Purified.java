package org.apache.druid.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.error.DruidException;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.query.groupby.GroupByQuery;
import org.apache.druid.query.timeseries.TimeseriesQuery;
import org.apache.druid.segment.TestHelper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryDataSourceTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final TimeseriesQuery queryOnTable = Druids.newTimeseriesQueryBuilder().dataSource("foo").intervals("2000/3000").granularity(Granularities.ALL).build();

    private final TimeseriesQuery queryOnLookup = Druids.newTimeseriesQueryBuilder().dataSource(new LookupDataSource("lookyloo")).intervals("2000/3000").granularity(Granularities.ALL).build();

    private final QueryDataSource queryOnTableDataSource = new QueryDataSource(queryOnTable);

    private final QueryDataSource queryOnLookupDataSource = new QueryDataSource(queryOnLookup);

    private final GroupByQuery groupByQuery = new GroupByQuery.Builder().setDataSource(queryOnTableDataSource).setGranularity(Granularities.ALL).setInterval("2000/3000").build();

    private final QueryDataSource queryDataSource = new QueryDataSource(groupByQuery);

    @Test
    public void test_isCacheable_table_1() {
        Assert.assertFalse(queryOnTableDataSource.isCacheable(true));
    }

    @Test
    public void test_isCacheable_table_2() {
        Assert.assertFalse(queryOnTableDataSource.isCacheable(false));
    }

    @Test
    public void test_isCacheable_lookup_1() {
        Assert.assertFalse(queryOnLookupDataSource.isCacheable(true));
    }

    @Test
    public void test_isCacheable_lookup_2() {
        Assert.assertFalse(queryOnLookupDataSource.isCacheable(false));
    }
}
