package org.apache.druid.math.expr;

import com.google.common.collect.ImmutableMap;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OutputTypeTest_Parameterized extends InitializedNullHandlingTest {

    private final Expr.InputBindingInspector inspector = inspectorFromMap(ImmutableMap.<String, ExpressionType>builder().put("x", ExpressionType.STRING).put("x_", ExpressionType.STRING).put("y", ExpressionType.LONG).put("y_", ExpressionType.LONG).put("z", ExpressionType.DOUBLE).put("z_", ExpressionType.DOUBLE).put("a", ExpressionType.STRING_ARRAY).put("a_", ExpressionType.STRING_ARRAY).put("b", ExpressionType.LONG_ARRAY).put("b_", ExpressionType.LONG_ARRAY).put("c", ExpressionType.DOUBLE_ARRAY).put("c_", ExpressionType.DOUBLE_ARRAY).build());

    private void assertOutputType(String expression, Expr.InputBindingInspector inspector, ExpressionType outputType) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil(), false);
        Assert.assertEquals(outputType, expr.getOutputType(inspector));
    }

    Expr.InputBindingInspector inspectorFromMap(Map<String, ExpressionType> types) {
        return key -> types.get(key);
    }

    @Test
    public void testArrayFunctions_3() {
        assertOutputType("array(a, b)", inspector, ExpressionTypeFactory.getInstance().ofArray(ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_1() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.operator(ExpressionType.LONG, null));
    }

    @Test
    public void testOperatorAutoConversion_2() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.operator(null, ExpressionType.LONG));
    }

    @Test
    public void testOperatorAutoConversion_3() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.DOUBLE, null));
    }

    @Test
    public void testOperatorAutoConversion_4() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(null, ExpressionType.DOUBLE));
    }

    @Test
    public void testOperatorAutoConversion_5() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.operator(ExpressionType.STRING, null));
    }

    @Test
    public void testOperatorAutoConversion_6() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.operator(null, ExpressionType.STRING));
    }

    @Test
    public void testOperatorAutoConversion_7() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.operator(ExpressionType.LONG, ExpressionType.LONG));
    }

    @Test
    public void testOperatorAutoConversion_8() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.operator(ExpressionType.STRING, ExpressionType.STRING));
    }

    @Test
    public void testOperatorAutoConversion_9() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.LONG, ExpressionType.DOUBLE));
    }

    @Test
    public void testOperatorAutoConversion_10() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.DOUBLE, ExpressionType.LONG));
    }

    @Test
    public void testOperatorAutoConversion_11() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.DOUBLE, ExpressionType.DOUBLE));
    }

    @Test
    public void testOperatorAutoConversion_12() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.DOUBLE, ExpressionType.STRING));
    }

    @Test
    public void testOperatorAutoConversion_13() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.STRING, ExpressionType.DOUBLE));
    }

    @Test
    public void testOperatorAutoConversion_14() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.STRING, ExpressionType.LONG));
    }

    @Test
    public void testOperatorAutoConversion_15() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.operator(ExpressionType.LONG, ExpressionType.STRING));
    }

    @Test
    public void testOperatorAutoConversion_16() {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, ExpressionTypeConversion.operator(ExpressionType.LONG_ARRAY, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_17() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.operator(ExpressionType.DOUBLE_ARRAY, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_18() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.operator(ExpressionType.STRING_ARRAY, ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_19() {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, ExpressionTypeConversion.operator(ExpressionType.LONG_ARRAY, ExpressionType.LONG));
    }

    @Test
    public void testOperatorAutoConversion_20() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.operator(ExpressionType.STRING, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_21() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.operator(ExpressionType.LONG_ARRAY, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_22() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.operator(ExpressionType.LONG, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_23() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.operator(ExpressionType.LONG_ARRAY, ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_24() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.operator(ExpressionType.STRING_ARRAY, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testOperatorAutoConversion_25_testMerged_25() {
        ExpressionType nested = ExpressionType.fromColumnType(ColumnType.NESTED_DATA);
        Assert.assertEquals(nested, ExpressionTypeConversion.operator(nested, nested));
        Assert.assertEquals(nested, ExpressionTypeConversion.operator(nested, ExpressionType.UNKNOWN_COMPLEX));
        Assert.assertEquals(nested, ExpressionTypeConversion.operator(ExpressionType.UNKNOWN_COMPLEX, nested));
    }

    @Test
    public void testFunctionAutoConversion_1() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.function(ExpressionType.LONG, null));
    }

    @Test
    public void testFunctionAutoConversion_2() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.function(null, ExpressionType.LONG));
    }

    @Test
    public void testFunctionAutoConversion_3() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.function(ExpressionType.DOUBLE, null));
    }

    @Test
    public void testFunctionAutoConversion_4() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.function(null, ExpressionType.DOUBLE));
    }

    @Test
    public void testFunctionAutoConversion_5() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(ExpressionType.STRING, null));
    }

    @Test
    public void testFunctionAutoConversion_6() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(null, ExpressionType.STRING));
    }

    @Test
    public void testFunctionAutoConversion_7() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.function(ExpressionType.LONG, ExpressionType.LONG));
    }

    @Test
    public void testFunctionAutoConversion_8() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.function(ExpressionType.LONG, ExpressionType.DOUBLE));
    }

    @Test
    public void testFunctionAutoConversion_9() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.function(ExpressionType.DOUBLE, ExpressionType.LONG));
    }

    @Test
    public void testFunctionAutoConversion_10() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionTypeConversion.function(ExpressionType.DOUBLE, ExpressionType.DOUBLE));
    }

    @Test
    public void testFunctionAutoConversion_11() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(ExpressionType.LONG, ExpressionType.STRING));
    }

    @Test
    public void testFunctionAutoConversion_12() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(ExpressionType.STRING, ExpressionType.LONG));
    }

    @Test
    public void testFunctionAutoConversion_13() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(ExpressionType.DOUBLE, ExpressionType.STRING));
    }

    @Test
    public void testFunctionAutoConversion_14() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(ExpressionType.STRING, ExpressionType.DOUBLE));
    }

    @Test
    public void testFunctionAutoConversion_15() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.function(ExpressionType.STRING, ExpressionType.STRING));
    }

    @Test
    public void testFunctionAutoConversion_16() {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, ExpressionTypeConversion.function(ExpressionType.LONG_ARRAY, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_17() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.function(ExpressionType.DOUBLE_ARRAY, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_18() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.function(ExpressionType.STRING_ARRAY, ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_19() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.function(ExpressionType.DOUBLE_ARRAY, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_20() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.function(ExpressionType.DOUBLE_ARRAY, ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_21() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.function(ExpressionType.STRING_ARRAY, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_22() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.function(ExpressionType.STRING, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_23() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.function(ExpressionType.LONG_ARRAY, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_24() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.function(ExpressionType.LONG, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testFunctionAutoConversion_25_testMerged_25() {
        ExpressionType nested = ExpressionType.fromColumnType(ColumnType.NESTED_DATA);
        Assert.assertEquals(nested, ExpressionTypeConversion.function(nested, nested));
        Assert.assertEquals(nested, ExpressionTypeConversion.function(nested, ExpressionType.UNKNOWN_COMPLEX));
        Assert.assertEquals(nested, ExpressionTypeConversion.function(ExpressionType.UNKNOWN_COMPLEX, nested));
    }

    @Test
    public void testIntegerFunctionAutoConversion_1() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(ExpressionType.LONG, null));
    }

    @Test
    public void testIntegerFunctionAutoConversion_2() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(null, ExpressionType.LONG));
    }

    @Test
    public void testIntegerFunctionAutoConversion_3() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(ExpressionType.DOUBLE, null));
    }

    @Test
    public void testIntegerFunctionAutoConversion_4() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(null, ExpressionType.DOUBLE));
    }

    @Test
    public void testIntegerFunctionAutoConversion_5() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(ExpressionType.STRING, null));
    }

    @Test
    public void testIntegerFunctionAutoConversion_6() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(null, ExpressionType.STRING));
    }

    @Test
    public void testIntegerFunctionAutoConversion_7() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(ExpressionType.LONG, ExpressionType.LONG));
    }

    @Test
    public void testIntegerFunctionAutoConversion_8() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(ExpressionType.LONG, ExpressionType.DOUBLE));
    }

    @Test
    public void testIntegerFunctionAutoConversion_9() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(ExpressionType.DOUBLE, ExpressionType.LONG));
    }

    @Test
    public void testIntegerFunctionAutoConversion_10() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionTypeConversion.integerMathFunction(ExpressionType.DOUBLE, ExpressionType.DOUBLE));
    }

    @Test
    public void testIntegerFunctionAutoConversion_11() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(ExpressionType.LONG, ExpressionType.STRING));
    }

    @Test
    public void testIntegerFunctionAutoConversion_12() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(ExpressionType.STRING, ExpressionType.LONG));
    }

    @Test
    public void testIntegerFunctionAutoConversion_13() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(ExpressionType.DOUBLE, ExpressionType.STRING));
    }

    @Test
    public void testIntegerFunctionAutoConversion_14() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(ExpressionType.STRING, ExpressionType.DOUBLE));
    }

    @Test
    public void testIntegerFunctionAutoConversion_15() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionTypeConversion.integerMathFunction(ExpressionType.STRING, ExpressionType.STRING));
    }

    @Test
    public void testIntegerFunctionAutoConversion_16() {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, ExpressionTypeConversion.integerMathFunction(ExpressionType.LONG_ARRAY, ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testIntegerFunctionAutoConversion_17() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionTypeConversion.integerMathFunction(ExpressionType.DOUBLE_ARRAY, ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testIntegerFunctionAutoConversion_18() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionTypeConversion.integerMathFunction(ExpressionType.STRING_ARRAY, ExpressionType.STRING_ARRAY));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstantsAndIdentifiers_1_1_1_1to2_2to4_4to7_7to9_9to11_11to14_14to15_15to18")
    public void testConstantsAndIdentifiers_1_1_1_1to2_2to4_4to7_7to9_9to11_11to14_14to15_15to18(String param1) {
        assertOutputType(param1, inspector, ExpressionType.STRING);
    }

    static public Stream<Arguments> Provider_testConstantsAndIdentifiers_1_1_1_1to2_2to4_4to7_7to9_9to11_11to14_14to15_15to18() {
        return Stream.of(arguments("'hello'"), arguments("x"), arguments("abs(x)"), arguments("if(y, 'foo', 'bar')"), arguments("case_simple(x,'baz','is baz','foo','is foo','is other')"), arguments("case_searched(x=='baz','is baz',x=='foo','is foo','is other')"), arguments("nvl(x, 'foo')"), arguments("concat(x, 'foo')"), arguments("concat(y, 'foo')"), arguments("concat(z, 'foo')"), arguments("format('%s', x)"), arguments("format('%s', y)"), arguments("format('%s', z)"), arguments("substring(x, 1, 2)"), arguments("left(x, 1)"), arguments("right(x, 1)"), arguments("replace(x, 'foo', '')"), arguments("lower(x)"), arguments("upper(x)"), arguments("reverse(x)"), arguments("repeat(x, 4)"), arguments("array_to_string(a, ',')"), arguments("array_to_string(b, ',')"), arguments("array_to_string(c, ',')"), arguments("array_offset(a, 1)"), arguments("array_ordinal(a, 1)"), arguments("greatest('B', x, 'A')"), arguments("least('B', x, 'A')"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstantsAndIdentifiers_1_1_1to2_2_2_2_2to3_3_3_3to4_4_4_4to5_5_5_5_5to6_6_6to7_7to8_8_8_8_8to9_9_9_9to10_10_10_10_10to12_12_12to13_13_15to19_19to20_20_20to21_21_21to22_22_22to23_23to24_24to25_25to35_35to36_36to37_37to38_38to39_39to40_40to48")
    public void testConstantsAndIdentifiers_1_1_1to2_2_2_2_2to3_3_3_3to4_4_4_4to5_5_5_5_5to6_6_6to7_7to8_8_8_8_8to9_9_9_9to10_10_10_10_10to12_12_12to13_13_15to19_19to20_20_20to21_21_21to22_22_22to23_23to24_24to25_25to35_35to36_36to37_37to38_38to39_39to40_40to48(int param1) {
        assertOutputType(param1, inspector, ExpressionType.LONG);
    }

    static public Stream<Arguments> Provider_testConstantsAndIdentifiers_1_1_1to2_2_2_2_2to3_3_3_3to4_4_4_4to5_5_5_5_5to6_6_6to7_7to8_8_8_8_8to9_9_9_9to10_10_10_10_10to12_12_12to13_13_15to19_19to20_20_20to21_21_21to22_22_22to23_23to24_24to25_25to35_35to36_36to37_37to38_38to39_39to40_40to48() {
        return Stream.of(arguments(23), arguments("y"), arguments(-1), arguments("-y"), arguments("!'true'"), arguments("!1"), arguments("!x"), arguments("!y"), arguments("!1.1"), arguments("!z"), arguments("1+1"), arguments("1-1"), arguments("1*1"), arguments("1/1"), arguments("1^1"), arguments("1%1"), arguments("y+y_"), arguments("y-y_"), arguments("y*y_"), arguments("y/y_"), arguments("y^y_"), arguments("y%y_"), arguments("y>y_"), arguments("y_<y"), arguments("y_<=y"), arguments("y_>=y"), arguments("y_==y"), arguments("y_!=y"), arguments("y_ && y"), arguments("y_ || y"), arguments("z>y_"), arguments("z<y"), arguments("z<=y"), arguments("y>=z"), arguments("z==y"), arguments("z!=y"), arguments("z && y"), arguments("y || z"), arguments("z>z_"), arguments("z<z_"), arguments("z<=z_"), arguments("z_>=z"), arguments("z==z_"), arguments("z!=z_"), arguments("z && z_"), arguments("z_ || z"), arguments("abs(y)"), arguments("div(y,y_)"), arguments("div(y,z_)"), arguments("div(z,z_)"), arguments("max(y,y_)"), arguments("safe_divide(y,y_)"), arguments("if(y,2,3)"), arguments("case_simple(y,2,2,3,3,4)"), arguments("case_searched(y==1,1,y==2,2,0)"), arguments("case_searched(z==1.0,1,z==2.0,2,null)"), arguments("nvl(y, 1)"), arguments("isnull(x)"), arguments("isnull(y)"), arguments("isnull(z)"), arguments("notnull(x)"), arguments("notnull(y)"), arguments("notnull(z)"), arguments("strlen(x)"), arguments("strpos(x, x_)"), arguments("strpos(x, y)"), arguments("strpos(x, z)"), arguments("array_length(a)"), arguments("array_length(b)"), arguments("array_length(c)"), arguments("array_offset(b, 1)"), arguments("array_ordinal(b, 1)"), arguments("array_offset_of(a, 'a')"), arguments("array_offset_of(b, 1)"), arguments("array_offset_of(c, 1.0)"), arguments("array_ordinal_of(a, 'a')"), arguments("array_ordinal_of(b, 1)"), arguments("array_ordinal_of(c, 1.0)"), arguments("array_contains(a, 'a')"), arguments("array_contains(b, 1)"), arguments("array_contains(c, 2.0)"), arguments("array_overlap(a, a)"), arguments("array_overlap(b, b)"), arguments("array_overlap(c, c)"), arguments("greatest(y, 0)"), arguments("least(y, 0)"), arguments("fold((x, acc) -> x + acc, y, 0)"), arguments("fold((x, acc) -> x + acc, y, y)"), arguments("cartesian_fold((x, y, acc) -> x + y + acc, y, z, 0)"), arguments("cartesian_fold((x, y, acc) -> x + y + acc, y, z, y)"), arguments("any((x) -> x == 'foo', a)"), arguments("any((x) -> x > 1, b)"), arguments("any((x) -> x > 1.2, c)"), arguments("all((x) -> x == 'foo', a)"), arguments("all((x) -> x > 1, b)"), arguments("all((x) -> x > 1.2, c)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstantsAndIdentifiers_1to3_3_3to4_4to5_5to6_6_6_6to7_7to8_8to9_9to11_11_11to12_12to13_13to14_14_14to15_15to16_16to17_17to18_18to19_19to24_49")
    public void testConstantsAndIdentifiers_1to3_3_3to4_4to5_5to6_6_6_6to7_7to8_8to9_9to11_11_11to12_12to13_13to14_14_14to15_15to16_16to17_17to18_18to19_19to24_49(double param1) {
        assertOutputType(param1, inspector, ExpressionType.DOUBLE);
    }

    static public Stream<Arguments> Provider_testConstantsAndIdentifiers_1to3_3_3to4_4to5_5to6_6_6_6to7_7to8_8to9_9to11_11_11to12_12to13_13to14_14_14to15_15to16_16to17_17to18_18to19_19to24_49() {
        return Stream.of(arguments(3.2), arguments("z"), arguments(-1.1), arguments("-z"), arguments("y+z"), arguments("y-z"), arguments("y*z"), arguments("y/z"), arguments("y^z"), arguments("y%z"), arguments("z+z_"), arguments("z-z_"), arguments("z*z_"), arguments("z/z_"), arguments("z^z_"), arguments("z%z_"), arguments("1*(2 + 3.0)"), arguments("pi()"), arguments("abs(z)"), arguments("cos(y)"), arguments("cos(z)"), arguments("max(y,z_)"), arguments("max(z,z_)"), arguments("hypot(y,y_)"), arguments("hypot(y,z_)"), arguments("hypot(z,z_)"), arguments("safe_divide(y,z_)"), arguments("safe_divide(z,z_)"), arguments("if(y,2,3.0)"), arguments("case_simple(z,2.0,2.0,3.0,3.0,4.0)"), arguments("case_simple(y,2,2,3,3.0,4)"), arguments("case_simple(z,2.0,2.0,3.0,3.0,null)"), arguments("case_searched(z==1.0,1.0,z==2.0,2.0,0.0)"), arguments("case_searched(y==1,1,y==2,2.0,0)"), arguments("case_searched(z==1.0,1.0,z==2.0,2.0,null)"), arguments("nvl(y, 1.1)"), arguments("nvl(z, 2.0)"), arguments("nvl(y, 2.0)"), arguments("array_offset(c, 1)"), arguments("array_ordinal(c, 1)"), arguments("greatest(34.0, z, 5.0, 767.0)"), arguments("least(34.0, z, 5.0, 767.0)"), arguments("fold((x, acc) -> x + acc, y, 1.0)"), arguments("fold((x, acc) -> x + acc, y, z)"), arguments("cartesian_fold((x, y, acc) -> x + y + acc, y, z, 1.0)"), arguments("cartesian_fold((x, y, acc) -> x + y + acc, y, z, z)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstantsAndIdentifiers_1_4_4_7_7_10_16_23to24_29to30_41_44to45")
    public void testConstantsAndIdentifiers_1_4_4_7_7_10_16_23to24_29to30_41_44to45(String param1) {
        assertOutputType(param1, inspector, ExpressionType.STRING_ARRAY);
    }

    static public Stream<Arguments> Provider_testConstantsAndIdentifiers_1_4_4_7_7_10_16_23to24_29to30_41_44to45() {
        return Stream.of(arguments("['a', 'b']"), arguments("a"), arguments("string_to_array(x, ',')"), arguments("array_append(x, x_)"), arguments("array_append(a, x_)"), arguments("array_concat(x, a)"), arguments("array_concat(a, a)"), arguments("array_slice(a, 1, 2)"), arguments("array_prepend(x, a)"), arguments("array_prepend(x, x_)"), arguments("map((x) -> concat(x, 'foo'), x)"), arguments("map((x) -> concat(x, 'foo'), a)"), arguments("cartesian_map((x, y) -> concat(x, y), ['foo', 'bar', 'baz', 'foobar'], ['bar', 'baz'])"), arguments("filter((x) -> x == 'foo', a)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstantsAndIdentifiers_1to2_5_5_11_17_25to26_31to32_42_46to47")
    public void testConstantsAndIdentifiers_1to2_5_5_11_17_25to26_31to32_42_46to47(String param1) {
        assertOutputType(param1, inspector, ExpressionType.LONG_ARRAY);
    }

    static public Stream<Arguments> Provider_testConstantsAndIdentifiers_1to2_5_5_11_17_25to26_31to32_42_46to47() {
        return Stream.of(arguments("[1,2,3]"), arguments("b"), arguments("array(1, 2, 3)"), arguments("array_append(y, y_)"), arguments("array_append(b, y_)"), arguments("array_concat(y, b)"), arguments("array_concat(b, b)"), arguments("array_slice(b, 1, 2)"), arguments("array_prepend(y, b)"), arguments("array_prepend(y, y_)"), arguments("map((x) -> x + x, y)"), arguments("map((x) -> x + x, b)"), arguments("filter((x) -> x > 1, b)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstantsAndIdentifiers_2to3_6_6_12_18_27to28_33to34_43_48to49")
    public void testConstantsAndIdentifiers_2to3_6_6_12_18_27to28_33to34_43_48to49(String param1) {
        assertOutputType(param1, inspector, ExpressionType.DOUBLE_ARRAY);
    }

    static public Stream<Arguments> Provider_testConstantsAndIdentifiers_2to3_6_6_12_18_27to28_33to34_43_48to49() {
        return Stream.of(arguments("[1.0]"), arguments("c"), arguments("array(1, 2, 3.0)"), arguments("array_append(z, z_)"), arguments("array_append(c, z_)"), arguments("array_concat(z, c)"), arguments("array_concat(c, c)"), arguments("array_slice(c, 1, 2)"), arguments("array_prepend(z, c)"), arguments("array_prepend(z, z_)"), arguments("map((x) -> x + x, z)"), arguments("map((x) -> x + x, c)"), arguments("filter((x) -> x > 1, c)"));
    }
}
