package org.apache.druid.sql.calcite.planner;

import com.google.common.collect.ImmutableSortedSet;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.sql.type.SqlTypeFamily;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.druid.query.ordering.StringComparators;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.sql.calcite.table.RowSignatures;
import org.apache.druid.sql.calcite.util.CalciteTestBase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcitesTest_Purified extends CalciteTestBase {

    @Test
    public void testEscapeStringLiteral_1() {
        assertEquals("''", Calcites.escapeStringLiteral(""));
    }

    @Test
    public void testEscapeStringLiteral_2() {
        assertEquals("'foo'", Calcites.escapeStringLiteral("foo"));
    }

    @Test
    public void testEscapeStringLiteral_3() {
        assertEquals("'foo bar'", Calcites.escapeStringLiteral("foo bar"));
    }

    @Test
    public void testEscapeStringLiteral_4() {
        assertEquals("U&'foö bar'", Calcites.escapeStringLiteral("foö bar"));
    }

    @Test
    public void testEscapeStringLiteral_5() {
        assertEquals("U&'foo \\0026\\0026 bar'", Calcites.escapeStringLiteral("foo && bar"));
    }

    @Test
    public void testEscapeStringLiteral_6() {
        assertEquals("U&'foo \\005C bar'", Calcites.escapeStringLiteral("foo \\ bar"));
    }

    @Test
    public void testEscapeStringLiteral_7() {
        assertEquals("U&'foo\\0027s bar'", Calcites.escapeStringLiteral("foo's bar"));
    }

    @Test
    public void testEscapeStringLiteral_8() {
        assertEquals("U&'друид'", Calcites.escapeStringLiteral("друид"));
    }

    @Test
    public void testFindUnusedPrefix_1() {
        assertEquals("x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "bar")));
    }

    @Test
    public void testFindUnusedPrefix_2() {
        assertEquals("x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "bar", "x")));
    }

    @Test
    public void testFindUnusedPrefix_3() {
        assertEquals("_x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "bar", "x0")));
    }

    @Test
    public void testFindUnusedPrefix_4() {
        assertEquals("_x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "bar", "x4")));
    }

    @Test
    public void testFindUnusedPrefix_5() {
        assertEquals("__x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "xa", "_x2xx", "x0")));
    }

    @Test
    public void testFindUnusedPrefix_6() {
        assertEquals("x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "xa", "_x2xx", " x")));
    }

    @Test
    public void testFindUnusedPrefix_7() {
        assertEquals("x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "_xbxx")));
    }

    @Test
    public void testFindUnusedPrefix_8() {
        assertEquals("x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "xa", "_x")));
    }

    @Test
    public void testFindUnusedPrefix_9() {
        assertEquals("__x", Calcites.findUnusedPrefixForDigits("x", ImmutableSortedSet.of("foo", "x1a", "_x90")));
    }

    @Test
    public void testGetStringComparatorForColumnType_1() {
        assertEquals(StringComparators.LEXICOGRAPHIC, Calcites.getStringComparatorForValueType(ColumnType.STRING));
    }

    @Test
    public void testGetStringComparatorForColumnType_2() {
        assertEquals(StringComparators.NUMERIC, Calcites.getStringComparatorForValueType(ColumnType.LONG));
    }

    @Test
    public void testGetStringComparatorForColumnType_3() {
        assertEquals(StringComparators.NUMERIC, Calcites.getStringComparatorForValueType(ColumnType.FLOAT));
    }

    @Test
    public void testGetStringComparatorForColumnType_4() {
        assertEquals(StringComparators.NUMERIC, Calcites.getStringComparatorForValueType(ColumnType.DOUBLE));
    }

    @Test
    public void testGetStringComparatorForColumnType_5() {
        assertEquals(StringComparators.NATURAL, Calcites.getStringComparatorForValueType(ColumnType.STRING_ARRAY));
    }

    @Test
    public void testGetStringComparatorForColumnType_6() {
        assertEquals(StringComparators.NATURAL, Calcites.getStringComparatorForValueType(ColumnType.LONG_ARRAY));
    }

    @Test
    public void testGetStringComparatorForColumnType_7() {
        assertEquals(StringComparators.NATURAL, Calcites.getStringComparatorForValueType(ColumnType.DOUBLE_ARRAY));
    }

    @Test
    public void testGetStringComparatorForColumnType_8() {
        assertEquals(StringComparators.NATURAL, Calcites.getStringComparatorForValueType(ColumnType.NESTED_DATA));
    }

    @Test
    public void testGetStringComparatorForColumnType_9() {
        assertEquals(StringComparators.NATURAL, Calcites.getStringComparatorForValueType(ColumnType.UNKNOWN_COMPLEX));
    }
}
