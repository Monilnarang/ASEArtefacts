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

public class ExprMacroTest_Purified {

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

    @Test
    public void testLike_1() {
        assertExpr("like(x, 'f%')", 1L);
    }

    @Test
    public void testLike_2() {
        assertExpr("like(x, 'f__')", 1L);
    }

    @Test
    public void testLike_3() {
        assertExpr("like(x, '%o%')", 1L);
    }

    @Test
    public void testLike_4() {
        assertExpr("like(x, 'b%')", 0L);
    }

    @Test
    public void testLike_5() {
        assertExpr("like(x, 'b__')", 0L);
    }

    @Test
    public void testLike_6() {
        assertExpr("like(x, '%x%')", 0L);
    }

    @Test
    public void testLike_7() {
        assertExpr("like(x, '')", 0L);
    }

    @Test
    public void testRegexpExtract_1() {
        assertExpr("regexp_extract(x, 'f(.)')", "fo");
    }

    @Test
    public void testRegexpExtract_2() {
        assertExpr("regexp_extract(x, 'f(.)', 0)", "fo");
    }

    @Test
    public void testRegexpExtract_3() {
        assertExpr("regexp_extract(x, 'f(.)', 1)", "o");
    }

    @Test
    public void testTimestampCeil_1() {
        assertExpr("timestamp_ceil(null, 'P1M')", null);
    }

    @Test
    public void testTimestampCeil_2() {
        assertExpr("timestamp_ceil(t, 'P1M')", DateTimes.of("2000-03-01").getMillis());
    }

    @Test
    public void testTimestampCeil_3() {
        assertExpr("timestamp_ceil(t, 'P1D',null,'America/Los_Angeles')", DateTimes.of("2000-02-03T08").getMillis());
    }

    @Test
    public void testTimestampCeil_4() {
        assertExpr("timestamp_ceil(t, 'P1D',null,CityOfAngels)", DateTimes.of("2000-02-03T08").getMillis());
    }

    @Test
    public void testTimestampCeil_5() {
        assertExpr("timestamp_ceil(t, 'P1D','1970-01-01T01','Etc/UTC')", DateTimes.of("2000-02-04T01").getMillis());
    }

    @Test
    public void testTimestampCeil_6() {
        assertExpr("timestamp_ceil(t1, 'P1D')", DateTimes.of("2000-02-03").getMillis());
    }

    @Test
    public void testTimestampFloor_1() {
        assertExpr("timestamp_floor(null, 'P1M')", null);
    }

    @Test
    public void testTimestampFloor_2() {
        assertExpr("timestamp_floor(t, 'P1M')", DateTimes.of("2000-02-01").getMillis());
    }

    @Test
    public void testTimestampFloor_3() {
        assertExpr("timestamp_floor(t, 'P1D',null,'America/Los_Angeles')", DateTimes.of("2000-02-02T08").getMillis());
    }

    @Test
    public void testTimestampFloor_4() {
        assertExpr("timestamp_floor(t, 'P1D',null,CityOfAngels)", DateTimes.of("2000-02-02T08").getMillis());
    }

    @Test
    public void testTimestampFloor_5() {
        assertExpr("timestamp_floor(t, 'P1D','1970-01-01T01','Etc/UTC')", DateTimes.of("2000-02-03T01").getMillis());
    }

    @Test
    public void testTimestampShift_1() {
        assertExpr("timestamp_shift(t, 'P1D', 2)", DateTimes.of("2000-02-05T04:05:06").getMillis());
    }

    @Test
    public void testTimestampShift_2() {
        assertExpr("timestamp_shift(t, 'P1D', 2, 'America/Los_Angeles')", DateTimes.of("2000-02-05T04:05:06").getMillis());
    }

    @Test
    public void testTimestampShift_3() {
        assertExpr("timestamp_shift(t, 'P1D', 2, CityOfAngels)", DateTimes.of("2000-02-05T04:05:06").getMillis());
    }

    @Test
    public void testTimestampShift_4() {
        assertExpr("timestamp_shift(t, 'P1D', 2, '-08:00')", DateTimes.of("2000-02-05T04:05:06").getMillis());
    }

    @Test
    public void testTimestampShift_5() {
        assertExpr("timestamp_shift(t, 'P1D', 2, '')", DateTimes.of("2000-02-05T04:05:06").getMillis());
    }

    @Test
    public void testTimestampExtract_1() {
        assertExpr("timestamp_extract(t, 'DAY')", 3L);
    }

    @Test
    public void testTimestampExtract_2() {
        assertExpr("timestamp_extract(t, 'HOUR')", 4L);
    }

    @Test
    public void testTimestampExtract_3() {
        assertExpr("timestamp_extract(t, 'DAY', 'America/Los_Angeles')", 2L);
    }

    @Test
    public void testTimestampExtract_4() {
        assertExpr("timestamp_extract(t, 'HOUR', 'America/Los_Angeles')", 20L);
    }

    @Test
    public void testTimestampParse_1() {
        assertExpr("timestamp_parse(tstr)", DateTimes.of("2000-02-03T04:05:06").getMillis());
    }

    @Test
    public void testTimestampParse_2() {
        assertExpr("timestamp_parse(tstr_sql)", DateTimes.of("2000-02-03T04:05:06").getMillis());
    }

    @Test
    public void testTimestampParse_3() {
        assertExpr("timestamp_parse(tstr_sql,null,'America/Los_Angeles')", DateTimes.of("2000-02-03T04:05:06-08:00").getMillis());
    }

    @Test
    public void testTimestampParse_4() {
        assertExpr("timestamp_parse('2000-02-03')", DateTimes.of("2000-02-03").getMillis());
    }

    @Test
    public void testTimestampParse_5() {
        assertExpr("timestamp_parse('2000-02')", DateTimes.of("2000-02-01").getMillis());
    }

    @Test
    public void testTimestampParse_6() {
        assertExpr("timestamp_parse(null)", null);
    }

    @Test
    public void testTimestampParse_7() {
        assertExpr("timestamp_parse('z2000')", null);
    }

    @Test
    public void testTimestampParse_8() {
        assertExpr("timestamp_parse(tstr_sql,'yyyy-MM-dd HH:mm:ss')", DateTimes.of("2000-02-03T04:05:06").getMillis());
    }

    @Test
    public void testTimestampParse_9() {
        assertExpr("timestamp_parse('02/03/2000','MM/dd/yyyy')", DateTimes.of("2000-02-03").getMillis());
    }

    @Test
    public void testTimestampParse_10() {
        assertExpr("timestamp_parse(tstr_sql,'yyyy-MM-dd HH:mm:ss','America/Los_Angeles')", DateTimes.of("2000-02-03T04:05:06-08:00").getMillis());
    }

    @Test
    public void testTimestampFormat_1() {
        assertExpr("timestamp_format(t)", "2000-02-03T04:05:06.000Z");
    }

    @Test
    public void testTimestampFormat_2() {
        assertExpr("timestamp_format(t,'yyyy-MM-dd HH:mm:ss')", "2000-02-03 04:05:06");
    }

    @Test
    public void testTimestampFormat_3() {
        assertExpr("timestamp_format(t,'yyyy-MM-dd HH:mm:ss','America/Los_Angeles')", "2000-02-02 20:05:06");
    }

    @Test
    public void testTrim_1_testMerged_1() {
        String emptyString = "";
        assertExpr("trim('')", emptyString);
        assertExpr("trim(spacey, spacey)", emptyString);
    }

    @Test
    public void testTrim_2() {
        assertExpr("trim(concat(' ',x,' '))", "foo");
    }

    @Test
    public void testTrim_3() {
        assertExpr("trim(spacey)", "hey there");
    }

    @Test
    public void testTrim_4() {
        assertExpr("trim(spacey, '')", "  hey there  ");
    }

    @Test
    public void testTrim_5() {
        assertExpr("trim(spacey, 'he ')", "y ther");
    }

    @Test
    public void testTrim_7() {
        assertExpr("trim(spacey, substring(spacey, 0, 4))", "y ther");
    }

    @Test
    public void testLTrim_1_testMerged_1() {
        String emptyString = "";
        assertExpr("ltrim('')", emptyString);
        assertExpr("ltrim(spacey, spacey)", emptyString);
    }

    @Test
    public void testLTrim_2() {
        assertExpr("ltrim(concat(' ',x,' '))", "foo ");
    }

    @Test
    public void testLTrim_3() {
        assertExpr("ltrim(spacey)", "hey there  ");
    }

    @Test
    public void testLTrim_4() {
        assertExpr("ltrim(spacey, '')", "  hey there  ");
    }

    @Test
    public void testLTrim_5() {
        assertExpr("ltrim(spacey, 'he ')", "y there  ");
    }

    @Test
    public void testLTrim_7() {
        assertExpr("ltrim(spacey, substring(spacey, 0, 4))", "y there  ");
    }

    @Test
    public void testRTrim_1_testMerged_1() {
        String emptyString = "";
        assertExpr("rtrim('')", emptyString);
        assertExpr("rtrim(spacey, spacey)", emptyString);
    }

    @Test
    public void testRTrim_2() {
        assertExpr("rtrim(concat(' ',x,' '))", " foo");
    }

    @Test
    public void testRTrim_3() {
        assertExpr("rtrim(spacey)", "  hey there");
    }

    @Test
    public void testRTrim_4() {
        assertExpr("rtrim(spacey, '')", "  hey there  ");
    }

    @Test
    public void testRTrim_5() {
        assertExpr("rtrim(spacey, 'he ')", "  hey ther");
    }

    @Test
    public void testRTrim_7() {
        assertExpr("rtrim(spacey, substring(spacey, 0, 4))", "  hey ther");
    }

    @Test
    public void testIPv4AddressParse_1() {
        assertExpr("ipv4_parse(x)", null);
    }

    @Test
    public void testIPv4AddressParse_2() {
        assertExpr("ipv4_parse(ipv4_string)", IPV4_LONG);
    }

    @Test
    public void testIPv4AddressParse_3() {
        assertExpr("ipv4_parse(ipv4_long)", IPV4_LONG);
    }

    @Test
    public void testIPv4AddressParse_4() {
        assertExpr("ipv4_parse(ipv4_stringify(ipv4_long))", IPV4_LONG);
    }

    @Test
    public void testIPv4AddressStringify_1() {
        assertExpr("ipv4_stringify(x)", null);
    }

    @Test
    public void testIPv4AddressStringify_2() {
        assertExpr("ipv4_stringify(ipv4_long)", IPV4_STRING);
    }

    @Test
    public void testIPv4AddressStringify_3() {
        assertExpr("ipv4_stringify(ipv4_string)", IPV4_STRING);
    }

    @Test
    public void testIPv4AddressStringify_4() {
        assertExpr("ipv4_stringify(ipv4_parse(ipv4_string))", IPV4_STRING);
    }

    @Test
    public void testIPv4AddressMatch_1() {
        assertExpr("ipv4_match(ipv4_string,    '10.0.0.0/8')", 0L);
    }

    @Test
    public void testIPv4AddressMatch_2() {
        assertExpr("ipv4_match(ipv4_string,    '192.168.0.0/16')", 1L);
    }

    @Test
    public void testIPv4AddressMatch_3() {
        assertExpr("ipv4_match(ipv4_network,   '192.168.0.0/16')", 1L);
    }

    @Test
    public void testIPv4AddressMatch_4() {
        assertExpr("ipv4_match(ipv4_broadcast, '192.168.0.0/16')", 1L);
    }
}
