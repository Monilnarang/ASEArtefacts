package org.apache.druid.query;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.java.util.common.HumanReadableBytes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.query.spec.MultipleIntervalSegmentSpec;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.HashMap;
import java.util.Map;

public class QueryContextsTest_Purified {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testCatalogValidationEnabled_1() {
        Assert.assertEquals(QueryContexts.DEFAULT_CATALOG_VALIDATION_ENABLED, QueryContext.empty().isCatalogValidationEnabled());
    }

    @Test
    public void testCatalogValidationEnabled_2() {
        Assert.assertTrue(QueryContext.of(ImmutableMap.of(QueryContexts.CATALOG_VALIDATION_ENABLED, true)).isCatalogValidationEnabled());
    }

    @Test
    public void testCatalogValidationEnabled_3() {
        Assert.assertFalse(QueryContext.of(ImmutableMap.of(QueryContexts.CATALOG_VALIDATION_ENABLED, false)).isCatalogValidationEnabled());
    }

    @Test
    public void testGetEnableJoinLeftScanDirect_1() {
        Assert.assertFalse(QueryContext.empty().getEnableJoinLeftScanDirect());
    }

    @Test
    public void testGetEnableJoinLeftScanDirect_2() {
        Assert.assertTrue(QueryContext.of(ImmutableMap.of(QueryContexts.SQL_JOIN_LEFT_SCAN_DIRECT, true)).getEnableJoinLeftScanDirect());
    }

    @Test
    public void testGetEnableJoinLeftScanDirect_3() {
        Assert.assertFalse(QueryContext.of(ImmutableMap.of(QueryContexts.SQL_JOIN_LEFT_SCAN_DIRECT, false)).getEnableJoinLeftScanDirect());
    }

    @Test
    public void testGetAsHumanReadableBytes_1() {
        Assert.assertEquals(new HumanReadableBytes("500M").getBytes(), QueryContexts.getAsHumanReadableBytes("maxOnDiskStorage", 500_000_000, HumanReadableBytes.ZERO).getBytes());
    }

    @Test
    public void testGetAsHumanReadableBytes_2() {
        Assert.assertEquals(new HumanReadableBytes("500M").getBytes(), QueryContexts.getAsHumanReadableBytes("maxOnDiskStorage", "500000000", HumanReadableBytes.ZERO).getBytes());
    }

    @Test
    public void testGetAsHumanReadableBytes_3() {
        Assert.assertEquals(new HumanReadableBytes("500M").getBytes(), QueryContexts.getAsHumanReadableBytes("maxOnDiskStorage", "500M", HumanReadableBytes.ZERO).getBytes());
    }
}
