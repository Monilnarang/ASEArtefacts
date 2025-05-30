package org.apache.druid.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.segment.RowAdapter;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.ColumnHolder;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.column.ValueType;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InlineDataSourceTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final AtomicLong iterationCounter = new AtomicLong();

    private final List<Object[]> rows = ImmutableList.of(new Object[] { DateTimes.of("2000").getMillis(), "foo", 0d, ImmutableMap.of("n", "0"), ImmutableList.of(1.0, 2.0) }, new Object[] { DateTimes.of("2000").getMillis(), "bar", 1d, ImmutableMap.of("n", "1"), ImmutableList.of(2.0, 4.0) }, new Object[] { DateTimes.of("2000").getMillis(), "baz", 2d, ImmutableMap.of("n", "2"), ImmutableList.of(3.0, 6.0) });

    private final Iterable<Object[]> rowsIterable = () -> {
        iterationCounter.incrementAndGet();
        return rows.iterator();
    };

    private final List<String> expectedColumnNames = ImmutableList.of(ColumnHolder.TIME_COLUMN_NAME, "str", "double", "complex", "double_array");

    private final ColumnType someComplex = new ColumnType(ValueType.COMPLEX, "foo", null);

    private final List<ColumnType> expectedColumnTypes = ImmutableList.of(ColumnType.LONG, ColumnType.STRING, ColumnType.DOUBLE, someComplex, ColumnType.DOUBLE_ARRAY);

    private final RowSignature expectedRowSignature;

    private final InlineDataSource listDataSource;

    private final InlineDataSource iterableDataSource;

    public InlineDataSourceTest() {
        final RowSignature.Builder builder = RowSignature.builder();
        for (int i = 0; i < expectedColumnNames.size(); i++) {
            builder.add(expectedColumnNames.get(i), expectedColumnTypes.get(i));
        }
        expectedRowSignature = builder.build();
        listDataSource = InlineDataSource.fromIterable(rows, expectedRowSignature);
        iterableDataSource = InlineDataSource.fromIterable(rowsIterable, expectedRowSignature);
    }

    private static void assertRowsEqual(final Iterable<Object[]> expectedRows, final Iterable<Object[]> actualRows) {
        if (expectedRows instanceof List && actualRows instanceof List) {
            final List<Object[]> expectedRowsList = (List<Object[]>) expectedRows;
            final List<Object[]> actualRowsList = (List<Object[]>) actualRows;
            final int sz = expectedRowsList.size();
            Assert.assertEquals("number of rows", sz, actualRowsList.size());
            for (int i = 0; i < sz; i++) {
                Assert.assertArrayEquals("row #" + i, expectedRowsList.get(i), actualRowsList.get(i));
            }
        } else {
            Assert.assertEquals("rows", expectedRows, actualRows);
        }
    }

    @Test
    public void test_getTableNames_1() {
        Assert.assertEquals(Collections.emptySet(), listDataSource.getTableNames());
    }

    @Test
    public void test_getTableNames_2() {
        Assert.assertEquals(Collections.emptySet(), iterableDataSource.getTableNames());
    }

    @Test
    public void test_getColumnNames_1() {
        Assert.assertEquals(expectedColumnNames, listDataSource.getColumnNames());
    }

    @Test
    public void test_getColumnNames_2() {
        Assert.assertEquals(expectedColumnNames, iterableDataSource.getColumnNames());
    }

    @Test
    public void test_getColumnTypes_1() {
        Assert.assertEquals(expectedColumnTypes, listDataSource.getColumnTypes());
    }

    @Test
    public void test_getColumnTypes_2() {
        Assert.assertEquals(expectedColumnTypes, iterableDataSource.getColumnTypes());
    }

    @Test
    public void test_getChildren_1() {
        Assert.assertEquals(Collections.emptyList(), listDataSource.getChildren());
    }

    @Test
    public void test_getChildren_2() {
        Assert.assertEquals(Collections.emptyList(), iterableDataSource.getChildren());
    }

    @Test
    public void test_isCacheable_1() {
        Assert.assertFalse(listDataSource.isCacheable(true));
    }

    @Test
    public void test_isCacheable_2() {
        Assert.assertFalse(listDataSource.isCacheable(false));
    }
}
