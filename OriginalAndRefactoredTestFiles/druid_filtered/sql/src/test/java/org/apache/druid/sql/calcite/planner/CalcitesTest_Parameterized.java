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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CalcitesTest_Parameterized extends CalciteTestBase {

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

    @ParameterizedTest
    @MethodSource("Provider_testEscapeStringLiteral_1to8")
    public void testEscapeStringLiteral_1to8(String param1, String param2) {
        assertEquals(param1, Calcites.escapeStringLiteral(param2));
    }

    static public Stream<Arguments> Provider_testEscapeStringLiteral_1to8() {
        return Stream.of(arguments("''", ""), arguments("'foo'", "foo"), arguments("'foo bar'", "foo bar"), arguments("U&'foö bar'", "foö bar"), arguments("U&'foo \\0026\\0026 bar'", "foo && bar"), arguments("U&'foo \\005C bar'", "foo \\ bar"), arguments("U&'foo\\0027s bar'", "foo's bar"), arguments("U&'друид'", "друид"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFindUnusedPrefix_1_7")
    public void testFindUnusedPrefix_1_7(String param1, String param2, String param3, String param4) {
        assertEquals(param1, Calcites.findUnusedPrefixForDigits(param2, ImmutableSortedSet.of(param3, param4)));
    }

    static public Stream<Arguments> Provider_testFindUnusedPrefix_1_7() {
        return Stream.of(arguments("x", "x", "foo", "bar"), arguments("x", "x", "foo", "_xbxx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFindUnusedPrefix_2to4_8to9")
    public void testFindUnusedPrefix_2to4_8to9(String param1, String param2, String param3, String param4, String param5) {
        assertEquals(param1, Calcites.findUnusedPrefixForDigits(param2, ImmutableSortedSet.of(param3, param4, param5)));
    }

    static public Stream<Arguments> Provider_testFindUnusedPrefix_2to4_8to9() {
        return Stream.of(arguments("x", "x", "foo", "bar", "x"), arguments("_x", "x", "foo", "bar", "x0"), arguments("_x", "x", "foo", "bar", "x4"), arguments("x", "x", "foo", "xa", "_x"), arguments("__x", "x", "foo", "x1a", "_x90"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFindUnusedPrefix_5to6")
    public void testFindUnusedPrefix_5to6(String param1, String param2, String param3, String param4, String param5, String param6) {
        assertEquals(param1, Calcites.findUnusedPrefixForDigits(param2, ImmutableSortedSet.of(param3, param4, param5, param6)));
    }

    static public Stream<Arguments> Provider_testFindUnusedPrefix_5to6() {
        return Stream.of(arguments("__x", "x", "foo", "xa", "_x2xx", "x0"), arguments("x", "x", "foo", "xa", "_x2xx", " x"));
    }
}
