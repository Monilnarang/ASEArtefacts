package org.apache.druid.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.IAE;
import org.apache.druid.query.filter.TrueDimFilter;
import org.apache.druid.query.policy.NoRestrictionPolicy;
import org.apache.druid.query.policy.RowFilterPolicy;
import org.apache.druid.segment.TestHelper;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;

public class RestrictedDataSourceTest_Purified {

    private final TableDataSource fooDataSource = new TableDataSource("foo");

    private final TableDataSource barDataSource = new TableDataSource("bar");

    private final RestrictedDataSource restrictedFooDataSource = RestrictedDataSource.create(fooDataSource, RowFilterPolicy.from(TrueDimFilter.instance()));

    private final RestrictedDataSource restrictedBarDataSource = RestrictedDataSource.create(barDataSource, NoRestrictionPolicy.instance());

    @Test
    public void test_getTableNames_1() {
        Assert.assertEquals(Collections.singleton("foo"), restrictedFooDataSource.getTableNames());
    }

    @Test
    public void test_getTableNames_2() {
        Assert.assertEquals(Collections.singleton("bar"), restrictedBarDataSource.getTableNames());
    }

    @Test
    public void test_getChildren_1() {
        Assert.assertEquals(Collections.singletonList(fooDataSource), restrictedFooDataSource.getChildren());
    }

    @Test
    public void test_getChildren_2() {
        Assert.assertEquals(Collections.singletonList(barDataSource), restrictedBarDataSource.getChildren());
    }
}
