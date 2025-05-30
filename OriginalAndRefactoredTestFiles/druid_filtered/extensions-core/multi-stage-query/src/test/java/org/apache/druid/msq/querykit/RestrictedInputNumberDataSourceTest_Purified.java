package org.apache.druid.msq.querykit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.msq.guice.MSQIndexingModule;
import org.apache.druid.query.DataSource;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.query.filter.TrueDimFilter;
import org.apache.druid.query.policy.NoRestrictionPolicy;
import org.apache.druid.query.policy.RowFilterPolicy;
import org.apache.druid.segment.TestHelper;
import org.junit.Assert;
import org.junit.Test;

public class RestrictedInputNumberDataSourceTest_Purified {

    private final RestrictedInputNumberDataSource restrictedFooDataSource = new RestrictedInputNumberDataSource(0, RowFilterPolicy.from(TrueDimFilter.instance()));

    private final RestrictedInputNumberDataSource restrictedBarDataSource = new RestrictedInputNumberDataSource(1, NoRestrictionPolicy.instance());

    @Test
    public void test_getTableNames_1() {
        Assert.assertTrue(restrictedFooDataSource.getTableNames().isEmpty());
    }

    @Test
    public void test_getTableNames_2() {
        Assert.assertTrue(restrictedBarDataSource.getTableNames().isEmpty());
    }

    @Test
    public void test_getChildren_1() {
        Assert.assertTrue(restrictedFooDataSource.getChildren().isEmpty());
    }

    @Test
    public void test_getChildren_2() {
        Assert.assertTrue(restrictedBarDataSource.getChildren().isEmpty());
    }
}
