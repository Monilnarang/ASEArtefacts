package org.apache.druid.math.expr;

import com.google.common.collect.ImmutableMap;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class OutputTypeTest_Purified extends InitializedNullHandlingTest {

    private final Expr.InputBindingInspector inspector = inspectorFromMap(ImmutableMap.<String, ExpressionType>builder().put("x", ExpressionType.STRING).put("x_", ExpressionType.STRING).put("y", ExpressionType.LONG).put("y_", ExpressionType.LONG).put("z", ExpressionType.DOUBLE).put("z_", ExpressionType.DOUBLE).put("a", ExpressionType.STRING_ARRAY).put("a_", ExpressionType.STRING_ARRAY).put("b", ExpressionType.LONG_ARRAY).put("b_", ExpressionType.LONG_ARRAY).put("c", ExpressionType.DOUBLE_ARRAY).put("c_", ExpressionType.DOUBLE_ARRAY).build());

    private void assertOutputType(String expression, Expr.InputBindingInspector inspector, ExpressionType outputType) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil(), false);
        Assert.assertEquals(outputType, expr.getOutputType(inspector));
    }

    Expr.InputBindingInspector inspectorFromMap(Map<String, ExpressionType> types) {
        return key -> types.get(key);
    }

    @Test
    public void testConstantsAndIdentifiers_1() {
        assertOutputType("'hello'", inspector, ExpressionType.STRING);
    }

    @Test
    public void testConstantsAndIdentifiers_2() {
        assertOutputType("23", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConstantsAndIdentifiers_3() {
        assertOutputType("3.2", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConstantsAndIdentifiers_4() {
        assertOutputType("['a', 'b']", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testConstantsAndIdentifiers_5() {
        assertOutputType("[1,2,3]", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testConstantsAndIdentifiers_6() {
        assertOutputType("[1.0]", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testConstantsAndIdentifiers_7() {
        assertOutputType("x", inspector, ExpressionType.STRING);
    }

    @Test
    public void testConstantsAndIdentifiers_8() {
        assertOutputType("y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConstantsAndIdentifiers_9() {
        assertOutputType("z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConstantsAndIdentifiers_10() {
        assertOutputType("a", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testConstantsAndIdentifiers_11() {
        assertOutputType("b", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testConstantsAndIdentifiers_12() {
        assertOutputType("c", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testUnaryOperators_1() {
        assertOutputType("-1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_2() {
        assertOutputType("-1.1", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testUnaryOperators_3() {
        assertOutputType("-y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_4() {
        assertOutputType("-z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testUnaryOperators_5() {
        assertOutputType("!'true'", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_6() {
        assertOutputType("!1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_7() {
        assertOutputType("!x", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_8() {
        assertOutputType("!y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_9() {
        assertOutputType("!1.1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnaryOperators_10() {
        assertOutputType("!z", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_1() {
        assertOutputType("1+1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_2() {
        assertOutputType("1-1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_3() {
        assertOutputType("1*1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_4() {
        assertOutputType("1/1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_5() {
        assertOutputType("1^1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_6() {
        assertOutputType("1%1", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_7() {
        assertOutputType("y+y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_8() {
        assertOutputType("y-y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_9() {
        assertOutputType("y*y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_10() {
        assertOutputType("y/y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_11() {
        assertOutputType("y^y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_12() {
        assertOutputType("y%y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_13() {
        assertOutputType("y+z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_14() {
        assertOutputType("y-z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_15() {
        assertOutputType("y*z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_16() {
        assertOutputType("y/z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_17() {
        assertOutputType("y^z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_18() {
        assertOutputType("y%z", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_19() {
        assertOutputType("z+z_", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_20() {
        assertOutputType("z-z_", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_21() {
        assertOutputType("z*z_", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_22() {
        assertOutputType("z/z_", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_23() {
        assertOutputType("z^z_", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_24() {
        assertOutputType("z%z_", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBinaryMathOperators_25() {
        assertOutputType("y>y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_26() {
        assertOutputType("y_<y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_27() {
        assertOutputType("y_<=y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_28() {
        assertOutputType("y_>=y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_29() {
        assertOutputType("y_==y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_30() {
        assertOutputType("y_!=y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_31() {
        assertOutputType("y_ && y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_32() {
        assertOutputType("y_ || y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_33() {
        assertOutputType("z>y_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_34() {
        assertOutputType("z<y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_35() {
        assertOutputType("z<=y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_36() {
        assertOutputType("y>=z", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_37() {
        assertOutputType("z==y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_38() {
        assertOutputType("z!=y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_39() {
        assertOutputType("z && y", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_40() {
        assertOutputType("y || z", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_41() {
        assertOutputType("z>z_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_42() {
        assertOutputType("z<z_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_43() {
        assertOutputType("z<=z_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_44() {
        assertOutputType("z_>=z", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_45() {
        assertOutputType("z==z_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_46() {
        assertOutputType("z!=z_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_47() {
        assertOutputType("z && z_", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_48() {
        assertOutputType("z_ || z", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBinaryMathOperators_49() {
        assertOutputType("1*(2 + 3.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testUnivariateMathFunctions_1() {
        assertOutputType("pi()", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testUnivariateMathFunctions_2() {
        assertOutputType("abs(x)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testUnivariateMathFunctions_3() {
        assertOutputType("abs(y)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testUnivariateMathFunctions_4() {
        assertOutputType("abs(z)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testUnivariateMathFunctions_5() {
        assertOutputType("cos(y)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testUnivariateMathFunctions_6() {
        assertOutputType("cos(z)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_1() {
        assertOutputType("div(y,y_)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBivariateMathFunctions_2() {
        assertOutputType("div(y,z_)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBivariateMathFunctions_3() {
        assertOutputType("div(z,z_)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBivariateMathFunctions_4() {
        assertOutputType("max(y,y_)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBivariateMathFunctions_5() {
        assertOutputType("max(y,z_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_6() {
        assertOutputType("max(z,z_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_7() {
        assertOutputType("hypot(y,y_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_8() {
        assertOutputType("hypot(y,z_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_9() {
        assertOutputType("hypot(z,z_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_10() {
        assertOutputType("safe_divide(y,y_)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testBivariateMathFunctions_11() {
        assertOutputType("safe_divide(y,z_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testBivariateMathFunctions_12() {
        assertOutputType("safe_divide(z,z_)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_1() {
        assertOutputType("if(y, 'foo', 'bar')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testConditionalFunctions_2() {
        assertOutputType("if(y,2,3)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_3() {
        assertOutputType("if(y,2,3.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_4() {
        assertOutputType("case_simple(x,'baz','is baz','foo','is foo','is other')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testConditionalFunctions_5() {
        assertOutputType("case_simple(y,2,2,3,3,4)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_6() {
        assertOutputType("case_simple(z,2.0,2.0,3.0,3.0,4.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_7() {
        assertOutputType("case_simple(y,2,2,3,3.0,4)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_8() {
        assertOutputType("case_simple(z,2.0,2.0,3.0,3.0,null)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_9() {
        assertOutputType("case_searched(x=='baz','is baz',x=='foo','is foo','is other')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testConditionalFunctions_10() {
        assertOutputType("case_searched(y==1,1,y==2,2,0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_11() {
        assertOutputType("case_searched(z==1.0,1.0,z==2.0,2.0,0.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_12() {
        assertOutputType("case_searched(y==1,1,y==2,2.0,0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_13() {
        assertOutputType("case_searched(z==1.0,1,z==2.0,2,null)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_14() {
        assertOutputType("case_searched(z==1.0,1.0,z==2.0,2.0,null)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_15() {
        assertOutputType("nvl(x, 'foo')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testConditionalFunctions_16() {
        assertOutputType("nvl(y, 1)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_17() {
        assertOutputType("nvl(y, 1.1)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_18() {
        assertOutputType("nvl(z, 2.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_19() {
        assertOutputType("nvl(y, 2.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testConditionalFunctions_20() {
        assertOutputType("isnull(x)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_21() {
        assertOutputType("isnull(y)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_22() {
        assertOutputType("isnull(z)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_23() {
        assertOutputType("notnull(x)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_24() {
        assertOutputType("notnull(y)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testConditionalFunctions_25() {
        assertOutputType("notnull(z)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testStringFunctions_1() {
        assertOutputType("concat(x, 'foo')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_2() {
        assertOutputType("concat(y, 'foo')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_3() {
        assertOutputType("concat(z, 'foo')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_4() {
        assertOutputType("strlen(x)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testStringFunctions_5() {
        assertOutputType("format('%s', x)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_6() {
        assertOutputType("format('%s', y)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_7() {
        assertOutputType("format('%s', z)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_8() {
        assertOutputType("strpos(x, x_)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testStringFunctions_9() {
        assertOutputType("strpos(x, y)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testStringFunctions_10() {
        assertOutputType("strpos(x, z)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testStringFunctions_11() {
        assertOutputType("substring(x, 1, 2)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_12() {
        assertOutputType("left(x, 1)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_13() {
        assertOutputType("right(x, 1)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_14() {
        assertOutputType("replace(x, 'foo', '')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_15() {
        assertOutputType("lower(x)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_16() {
        assertOutputType("upper(x)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_17() {
        assertOutputType("reverse(x)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testStringFunctions_18() {
        assertOutputType("repeat(x, 4)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testArrayFunctions_1() {
        assertOutputType("array(1, 2, 3)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_2() {
        assertOutputType("array(1, 2, 3.0)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_3() {
        assertOutputType("array(a, b)", inspector, ExpressionTypeFactory.getInstance().ofArray(ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testArrayFunctions_4() {
        assertOutputType("array_length(a)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_5() {
        assertOutputType("array_length(b)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_6() {
        assertOutputType("array_length(c)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_7() {
        assertOutputType("string_to_array(x, ',')", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_8() {
        assertOutputType("array_to_string(a, ',')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testArrayFunctions_9() {
        assertOutputType("array_to_string(b, ',')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testArrayFunctions_10() {
        assertOutputType("array_to_string(c, ',')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testArrayFunctions_11() {
        assertOutputType("array_offset(a, 1)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testArrayFunctions_12() {
        assertOutputType("array_offset(b, 1)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_13() {
        assertOutputType("array_offset(c, 1)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testArrayFunctions_14() {
        assertOutputType("array_ordinal(a, 1)", inspector, ExpressionType.STRING);
    }

    @Test
    public void testArrayFunctions_15() {
        assertOutputType("array_ordinal(b, 1)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_16() {
        assertOutputType("array_ordinal(c, 1)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testArrayFunctions_17() {
        assertOutputType("array_offset_of(a, 'a')", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_18() {
        assertOutputType("array_offset_of(b, 1)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_19() {
        assertOutputType("array_offset_of(c, 1.0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_20() {
        assertOutputType("array_ordinal_of(a, 'a')", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_21() {
        assertOutputType("array_ordinal_of(b, 1)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_22() {
        assertOutputType("array_ordinal_of(c, 1.0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_23() {
        assertOutputType("array_append(x, x_)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_24() {
        assertOutputType("array_append(a, x_)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_25() {
        assertOutputType("array_append(y, y_)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_26() {
        assertOutputType("array_append(b, y_)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_27() {
        assertOutputType("array_append(z, z_)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_28() {
        assertOutputType("array_append(c, z_)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_29() {
        assertOutputType("array_concat(x, a)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_30() {
        assertOutputType("array_concat(a, a)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_31() {
        assertOutputType("array_concat(y, b)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_32() {
        assertOutputType("array_concat(b, b)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_33() {
        assertOutputType("array_concat(z, c)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_34() {
        assertOutputType("array_concat(c, c)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_35() {
        assertOutputType("array_contains(a, 'a')", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_36() {
        assertOutputType("array_contains(b, 1)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_37() {
        assertOutputType("array_contains(c, 2.0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_38() {
        assertOutputType("array_overlap(a, a)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_39() {
        assertOutputType("array_overlap(b, b)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_40() {
        assertOutputType("array_overlap(c, c)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testArrayFunctions_41() {
        assertOutputType("array_slice(a, 1, 2)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_42() {
        assertOutputType("array_slice(b, 1, 2)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_43() {
        assertOutputType("array_slice(c, 1, 2)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_44() {
        assertOutputType("array_prepend(x, a)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_45() {
        assertOutputType("array_prepend(x, x_)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testArrayFunctions_46() {
        assertOutputType("array_prepend(y, b)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_47() {
        assertOutputType("array_prepend(y, y_)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testArrayFunctions_48() {
        assertOutputType("array_prepend(z, c)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testArrayFunctions_49() {
        assertOutputType("array_prepend(z, z_)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testReduceFunctions_1() {
        assertOutputType("greatest('B', x, 'A')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testReduceFunctions_2() {
        assertOutputType("greatest(y, 0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testReduceFunctions_3() {
        assertOutputType("greatest(34.0, z, 5.0, 767.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testReduceFunctions_4() {
        assertOutputType("least('B', x, 'A')", inspector, ExpressionType.STRING);
    }

    @Test
    public void testReduceFunctions_5() {
        assertOutputType("least(y, 0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testReduceFunctions_6() {
        assertOutputType("least(34.0, z, 5.0, 767.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testApplyFunctions_1() {
        assertOutputType("map((x) -> concat(x, 'foo'), x)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testApplyFunctions_2() {
        assertOutputType("map((x) -> x + x, y)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testApplyFunctions_3() {
        assertOutputType("map((x) -> x + x, z)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testApplyFunctions_4() {
        assertOutputType("map((x) -> concat(x, 'foo'), a)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testApplyFunctions_5() {
        assertOutputType("map((x) -> x + x, b)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testApplyFunctions_6() {
        assertOutputType("map((x) -> x + x, c)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testApplyFunctions_7() {
        assertOutputType("cartesian_map((x, y) -> concat(x, y), ['foo', 'bar', 'baz', 'foobar'], ['bar', 'baz'])", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testApplyFunctions_8() {
        assertOutputType("fold((x, acc) -> x + acc, y, 0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_9() {
        assertOutputType("fold((x, acc) -> x + acc, y, y)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_10() {
        assertOutputType("fold((x, acc) -> x + acc, y, 1.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testApplyFunctions_11() {
        assertOutputType("fold((x, acc) -> x + acc, y, z)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testApplyFunctions_12() {
        assertOutputType("cartesian_fold((x, y, acc) -> x + y + acc, y, z, 0)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_13() {
        assertOutputType("cartesian_fold((x, y, acc) -> x + y + acc, y, z, y)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_14() {
        assertOutputType("cartesian_fold((x, y, acc) -> x + y + acc, y, z, 1.0)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testApplyFunctions_15() {
        assertOutputType("cartesian_fold((x, y, acc) -> x + y + acc, y, z, z)", inspector, ExpressionType.DOUBLE);
    }

    @Test
    public void testApplyFunctions_16() {
        assertOutputType("filter((x) -> x == 'foo', a)", inspector, ExpressionType.STRING_ARRAY);
    }

    @Test
    public void testApplyFunctions_17() {
        assertOutputType("filter((x) -> x > 1, b)", inspector, ExpressionType.LONG_ARRAY);
    }

    @Test
    public void testApplyFunctions_18() {
        assertOutputType("filter((x) -> x > 1, c)", inspector, ExpressionType.DOUBLE_ARRAY);
    }

    @Test
    public void testApplyFunctions_19() {
        assertOutputType("any((x) -> x == 'foo', a)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_20() {
        assertOutputType("any((x) -> x > 1, b)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_21() {
        assertOutputType("any((x) -> x > 1.2, c)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_22() {
        assertOutputType("all((x) -> x == 'foo', a)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_23() {
        assertOutputType("all((x) -> x > 1, b)", inspector, ExpressionType.LONG);
    }

    @Test
    public void testApplyFunctions_24() {
        assertOutputType("all((x) -> x > 1.2, c)", inspector, ExpressionType.LONG);
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
}
