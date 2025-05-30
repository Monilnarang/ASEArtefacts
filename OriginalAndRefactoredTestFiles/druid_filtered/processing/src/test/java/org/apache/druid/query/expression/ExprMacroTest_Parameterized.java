package org.apache.druid.query.expression;

import com.google.common.collect.ImmutableMap;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.math.expr.Expr;
import org.apache.druid.math.expr.ExpressionType;
import org.apache.druid.math.expr.InputBindings;
import org.apache.druid.math.expr.Parser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ExprMacroTest_Parameterized {

    private static final String IPV4_STRING = "192.168.0.1";

    private static final long IPV4_LONG = 3232235521L;

    private static final Expr.ObjectBinding BINDINGS = InputBindings.forInputSuppliers(ImmutableMap.<String, InputBindings.InputSupplier<?>>builder().put("t", InputBindings.inputSupplier(ExpressionType.LONG, () -> DateTimes.of("2000-02-03T04:05:06").getMillis())).put("t1", InputBindings.inputSupplier(ExpressionType.LONG, () -> DateTimes.of("2000-02-03T00:00:00").getMillis())).put("tstr", InputBindings.inputSupplier(ExpressionType.STRING, () -> "2000-02-03T04:05:06")).put("tstr_sql", InputBindings.inputSupplier(ExpressionType.STRING, () -> "2000-02-03 04:05:06")).put("x", InputBindings.inputSupplier(ExpressionType.STRING, () -> "foo")).put("y", InputBindings.inputSupplier(ExpressionType.LONG, () -> 2)).put("z", InputBindings.inputSupplier(ExpressionType.DOUBLE, () -> 3.1)).put("CityOfAngels", InputBindings.inputSupplier(ExpressionType.STRING, () -> "America/Los_Angeles")).put("spacey", InputBindings.inputSupplier(ExpressionType.STRING, () -> "  hey there  ")).put("ipv4_string", InputBindings.inputSupplier(ExpressionType.STRING, () -> IPV4_STRING)).put("ipv4_long", InputBindings.inputSupplier(ExpressionType.LONG, () -> IPV4_LONG)).put("ipv4_network", InputBindings.inputSupplier(ExpressionType.STRING, () -> "192.168.0.0")).put("ipv4_broadcast", InputBindings.inputSupplier(ExpressionType.STRING, () -> "192.168.255.255")).build());

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private void assertExpr(final String expression, final Object expectedResult) {
        final Expr expr = Parser.parse(expression, TestExprMacroTable.INSTANCE);
        Assert.assertEquals(expression, expectedResult, expr.eval(BINDINGS).value());
        final Expr exprNotFlattened = Parser.parse(expression, TestExprMacroTable.INSTANCE, false);
        final Expr roundTripNotFlattened = Parser.parse(exprNotFlattened.stringify(), TestExprMacroTable.INSTANCE);
        Assert.assertEquals(exprNotFlattened.stringify(), expectedResult, roundTripNotFlattened.eval(BINDINGS).value());
        final Expr roundTrip = Parser.parse(expr.stringify(), TestExprMacroTable.INSTANCE);
        Assert.assertEquals(exprNotFlattened.stringify(), expectedResult, roundTrip.eval(BINDINGS).value());
    }

    @ParameterizedTest
    @MethodSource("Provider_testLike_1_1_1_1_1to2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3to4_4_4_4_4_4to5_5_5_5to7_7_7_7")
    public void testLike_1_1_1_1_1to2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3to4_4_4_4_4_4to5_5_5_5to7_7_7_7(String param1, long param2) {
        assertExpr(param1, param2);
    }

    static public Stream<Arguments> Provider_testLike_1_1_1_1_1to2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3to4_4_4_4_4_4to5_5_5_5to7_7_7_7() {
        return Stream.of(arguments("like(x, 'f%')", 1L), arguments("like(x, 'f__')", 1L), arguments("like(x, '%o%')", 1L), arguments("like(x, 'b%')", 0L), arguments("like(x, 'b__')", 0L), arguments("like(x, '%x%')", 0L), arguments("like(x, '')", 0L), arguments("regexp_extract(x, 'f(.)')", "fo"), arguments("regexp_extract(x, 'f(.)', 0)", "fo"), arguments("regexp_extract(x, 'f(.)', 1)", "o"), arguments("timestamp_extract(t, 'DAY')", 3L), arguments("timestamp_extract(t, 'HOUR')", 4L), arguments("timestamp_extract(t, 'DAY', 'America/Los_Angeles')", 2L), arguments("timestamp_extract(t, 'HOUR', 'America/Los_Angeles')", 20L), arguments("timestamp_format(t)", "2000-02-03T04:05:06.000Z"), arguments("timestamp_format(t,'yyyy-MM-dd HH:mm:ss')", "2000-02-03 04:05:06"), arguments("timestamp_format(t,'yyyy-MM-dd HH:mm:ss','America/Los_Angeles')", "2000-02-02 20:05:06"), arguments("trim(concat(' ',x,' '))", "foo"), arguments("trim(spacey)", "hey there"), arguments("trim(spacey, '')", "  hey there  "), arguments("trim(spacey, 'he ')", "y ther"), arguments("trim(spacey, substring(spacey, 0, 4))", "y ther"), arguments("ltrim(concat(' ',x,' '))", "foo "), arguments("ltrim(spacey)", "hey there  "), arguments("ltrim(spacey, '')", "  hey there  "), arguments("ltrim(spacey, 'he ')", "y there  "), arguments("ltrim(spacey, substring(spacey, 0, 4))", "y there  "), arguments("rtrim(concat(' ',x,' '))", " foo"), arguments("rtrim(spacey)", "  hey there"), arguments("rtrim(spacey, '')", "  hey there  "), arguments("rtrim(spacey, 'he ')", "  hey ther"), arguments("rtrim(spacey, substring(spacey, 0, 4))", "  hey ther"), arguments("ipv4_match(ipv4_string,    '10.0.0.0/8')", 0L), arguments("ipv4_match(ipv4_string,    '192.168.0.0/16')", 1L), arguments("ipv4_match(ipv4_network,   '192.168.0.0/16')", 1L), arguments("ipv4_match(ipv4_broadcast, '192.168.0.0/16')", 1L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTimestampCeil_1_1_1_1_6to7")
    public void testTimestampCeil_1_1_1_1_6to7(String param1) {
        assertExpr(param1, null);
    }

    static public Stream<Arguments> Provider_testTimestampCeil_1_1_1_1_6to7() {
        return Stream.of(arguments("timestamp_ceil(null, 'P1M')"), arguments("timestamp_floor(null, 'P1M')"), arguments("timestamp_parse(null)"), arguments("timestamp_parse('z2000')"), arguments("ipv4_parse(x)"), arguments("ipv4_stringify(x)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTimestampCeil_1_1to2_2_2_2to3_3_3_3to4_4_4_4to5_5_5_5to6_8to10")
    public void testTimestampCeil_1_1to2_2_2_2to3_3_3_3to4_4_4_4to5_5_5_5to6_8to10(String param1, String param2) {
        assertExpr(param1, DateTimes.of(param2).getMillis());
    }

    static public Stream<Arguments> Provider_testTimestampCeil_1_1to2_2_2_2to3_3_3_3to4_4_4_4to5_5_5_5to6_8to10() {
        return Stream.of(arguments("timestamp_ceil(t, 'P1M')", "2000-03-01"), arguments("timestamp_ceil(t, 'P1D',null,'America/Los_Angeles')", "2000-02-03T08"), arguments("timestamp_ceil(t, 'P1D',null,CityOfAngels)", "2000-02-03T08"), arguments("timestamp_ceil(t, 'P1D','1970-01-01T01','Etc/UTC')", "2000-02-04T01"), arguments("timestamp_ceil(t1, 'P1D')", "2000-02-03"), arguments("timestamp_floor(t, 'P1M')", "2000-02-01"), arguments("timestamp_floor(t, 'P1D',null,'America/Los_Angeles')", "2000-02-02T08"), arguments("timestamp_floor(t, 'P1D',null,CityOfAngels)", "2000-02-02T08"), arguments("timestamp_floor(t, 'P1D','1970-01-01T01','Etc/UTC')", "2000-02-03T01"), arguments("timestamp_shift(t, 'P1D', 2)", "2000-02-05T04:05:06"), arguments("timestamp_shift(t, 'P1D', 2, 'America/Los_Angeles')", "2000-02-05T04:05:06"), arguments("timestamp_shift(t, 'P1D', 2, CityOfAngels)", "2000-02-05T04:05:06"), arguments("timestamp_shift(t, 'P1D', 2, '-08:00')", "2000-02-05T04:05:06"), arguments("timestamp_shift(t, 'P1D', 2, '')", "2000-02-05T04:05:06"), arguments("timestamp_parse(tstr)", "2000-02-03T04:05:06"), arguments("timestamp_parse(tstr_sql)", "2000-02-03T04:05:06"), arguments("timestamp_parse(tstr_sql,null,'America/Los_Angeles')", "2000-02-03T04:05:06-08:00"), arguments("timestamp_parse('2000-02-03')", "2000-02-03"), arguments("timestamp_parse('2000-02')", "2000-02-01"), arguments("timestamp_parse(tstr_sql,'yyyy-MM-dd HH:mm:ss')", "2000-02-03T04:05:06"), arguments("timestamp_parse('02/03/2000','MM/dd/yyyy')", "2000-02-03"), arguments("timestamp_parse(tstr_sql,'yyyy-MM-dd HH:mm:ss','America/Los_Angeles')", "2000-02-03T04:05:06-08:00"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrim_1_testMerged_1_1_1")
    public void testTrim_1_testMerged_1_1_1(String param1, String param2, String param3) {
        String emptyString = param3;
        assertExpr(param1, emptyString);
        assertExpr(param2, emptyString);
    }

    static public Stream<Arguments> Provider_testTrim_1_testMerged_1_1_1() {
        return Stream.of(arguments("", "trim('')", "trim(spacey, spacey)"), arguments("", "ltrim('')", "ltrim(spacey, spacey)"), arguments("", "rtrim('')", "rtrim(spacey, spacey)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIPv4AddressParse_2to4")
    public void testIPv4AddressParse_2to4(String param1) {
        assertExpr(param1, IPV4_LONG);
    }

    static public Stream<Arguments> Provider_testIPv4AddressParse_2to4() {
        return Stream.of(arguments("ipv4_parse(ipv4_string)"), arguments("ipv4_parse(ipv4_long)"), arguments("ipv4_parse(ipv4_stringify(ipv4_long))"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIPv4AddressStringify_2to4")
    public void testIPv4AddressStringify_2to4(String param1) {
        assertExpr(param1, IPV4_STRING);
    }

    static public Stream<Arguments> Provider_testIPv4AddressStringify_2to4() {
        return Stream.of(arguments("ipv4_stringify(ipv4_long)"), arguments("ipv4_stringify(ipv4_string)"), arguments("ipv4_stringify(ipv4_parse(ipv4_string))"));
    }
}
