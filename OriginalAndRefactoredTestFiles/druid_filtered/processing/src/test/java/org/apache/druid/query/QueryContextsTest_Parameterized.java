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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class QueryContextsTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testGetAsHumanReadableBytes_1to3")
    public void testGetAsHumanReadableBytes_1to3(String param1, String param2, String param3) {
        Assert.assertEquals(new HumanReadableBytes(param1).getBytes(), QueryContexts.getAsHumanReadableBytes(param2, param3, HumanReadableBytes.ZERO).getBytes());
    }

    static public Stream<Arguments> Provider_testGetAsHumanReadableBytes_1to3() {
        return Stream.of(arguments("500M", "maxOnDiskStorage", "500_000_000"), arguments("500M", "maxOnDiskStorage", 500000000), arguments("500M", "maxOnDiskStorage", "500M"));
    }
}
