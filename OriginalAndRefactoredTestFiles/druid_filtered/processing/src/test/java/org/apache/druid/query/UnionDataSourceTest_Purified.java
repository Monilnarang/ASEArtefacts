package org.apache.druid.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.join.NoopDataSource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Collections;
import java.util.List;

public class UnionDataSourceTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final UnionDataSource unionDataSource = new UnionDataSource(ImmutableList.of(new TableDataSource("foo"), new TableDataSource("bar")));

    private final UnionDataSource unionDataSourceWithDuplicates = new UnionDataSource(ImmutableList.of(new TableDataSource("bar"), new TableDataSource("foo"), new TableDataSource("bar")));

    private final UnionDataSource tableAndInlineUniondataSource = new UnionDataSource(ImmutableList.of(new TableDataSource("foo"), InlineDataSource.fromIterable(Collections.emptyList(), RowSignature.empty())));

    @Test
    public void test_isCompatible_1() {
        TableDataSource tableDataSource = new TableDataSource("foo");
        Assert.assertTrue(UnionDataSource.isCompatibleDataSource(tableDataSource));
    }

    @Test
    public void test_isCompatible_2() {
        InlineDataSource inlineDataSource = InlineDataSource.fromIterable(Collections.emptyList(), RowSignature.empty());
        Assert.assertTrue(UnionDataSource.isCompatibleDataSource(inlineDataSource));
    }

    @Test
    public void test_isCompatible_3() {
        Assert.assertFalse(UnionDataSource.isCompatibleDataSource(new NoopDataSource()));
    }

    @Test
    public void test_isTableBased_1() {
        Assert.assertTrue(unionDataSource.isTableBased());
    }

    @Test
    public void test_isTableBased_2() {
        Assert.assertTrue(unionDataSourceWithDuplicates.isTableBased());
    }

    @Test
    public void test_isTableBased_3() {
        Assert.assertFalse(tableAndInlineUniondataSource.isTableBased());
    }

    @Test
    public void test_isCacheable_1() {
        Assert.assertFalse(unionDataSource.isCacheable(true));
    }

    @Test
    public void test_isCacheable_2() {
        Assert.assertFalse(unionDataSource.isCacheable(false));
    }
}
