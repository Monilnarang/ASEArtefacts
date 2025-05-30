package org.graylog.events.conditions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.graylog.testing.jsonpath.JsonPathAssert;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.junit.Test;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Collections;
import java.util.function.Consumer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BooleanNumberConditionsVisitorTest_Parameterized {

    private static ObjectMapper objectMapper = new ObjectMapperProvider().get();

    private void assertJsonPath(Expression<Boolean> expression, Consumer<JsonPathAssert> consumer) throws Exception {
        final DocumentContext context = JsonPath.parse(objectMapper.writeValueAsString(expression));
        final JsonPathAssert jsonPathAssert = JsonPathAssert.assertThat(context);
        consumer.accept(jsonPathAssert);
    }

    private void assertLeftAndRight(JsonPathAssert at, Expr.NumberValue left, Expr.NumberReference right) {
        at.jsonPathAsString("$.left.expr").isEqualTo("number");
        at.jsonPathAsBigDecimal("$.left.value").isEqualTo(BigDecimal.valueOf(left.value()));
        at.jsonPathAsString("$.right.expr").isEqualTo("number-ref");
        at.jsonPathAsString("$.right.ref").isEqualTo(right.ref());
    }

    private Expression<Boolean> loadCondition(String filename) throws IOException {
        final URL resource = Resources.getResource(getClass(), filename);
        return objectMapper.readValue(resource, new TypeReference<Expression<Boolean>>() {
        });
    }

    @Test
    public void testTrue_1() throws Exception {
        assertThat(Expr.True.create().accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testAnd_1_testMerged_1() throws Exception {
        final Expr.Greater trueExpr = Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1));
        final Expr.Greater falseExpr = Expr.Greater.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2));
        assertThat(Expr.And.create(trueExpr, trueExpr).accept(new BooleanNumberConditionsVisitor())).isTrue();
        assertThat(Expr.And.create(trueExpr, falseExpr).accept(new BooleanNumberConditionsVisitor())).isFalse();
        assertThat(Expr.And.create(falseExpr, trueExpr).accept(new BooleanNumberConditionsVisitor())).isFalse();
        assertThat(Expr.And.create(falseExpr, falseExpr).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testOr_1_testMerged_1() throws Exception {
        final Expr.Greater trueExpr = Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1));
        final Expr.Greater falseExpr = Expr.Greater.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2));
        assertThat(Expr.Or.create(trueExpr, trueExpr).accept(new BooleanNumberConditionsVisitor())).isTrue();
        assertThat(Expr.Or.create(trueExpr, falseExpr).accept(new BooleanNumberConditionsVisitor())).isTrue();
        assertThat(Expr.Or.create(falseExpr, trueExpr).accept(new BooleanNumberConditionsVisitor())).isTrue();
        assertThat(Expr.Or.create(falseExpr, falseExpr).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testNot_1_testMerged_1() throws Exception {
        final Expr.Greater trueExpr = Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1));
        final Expr.Greater falseExpr = Expr.Greater.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2));
        assertThat(Expr.Not.create(falseExpr).accept(new BooleanNumberConditionsVisitor())).isTrue();
        assertThat(Expr.Not.create(trueExpr).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testEqual_1() throws Exception {
        assertThat(Expr.Equal.create(Expr.NumberValue.create(2), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreater_1() throws Exception {
        assertThat(Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreaterEqual_3() throws Exception {
        assertThat(Expr.GreaterEqual.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testLesser_1() throws Exception {
        assertThat(Expr.Lesser.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testLesserEqual_3() throws Exception {
        assertThat(Expr.LesserEqual.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrue_2to4_4_4_4_4to5_5")
    public void testTrue_2to4_4_4_4_4to5_5(String param1) throws Exception {
        assertThat(loadCondition(param1).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    static public Stream<Arguments> Provider_testTrue_2to4_4_4_4_4to5_5() {
        return Stream.of(arguments("condition-true.json"), arguments("condition-and.json"), arguments("condition-or.json"), arguments("condition-not.json"), arguments("condition-equal.json"), arguments("condition-greater.json"), arguments("condition-greater-equal.json"), arguments("condition-lesser.json"), arguments("condition-lesser-equal.json"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEqual_2to3")
    public void testEqual_2to3(int param1, int param2) throws Exception {
        assertThat(Expr.Equal.create(Expr.NumberValue.create(param1), Expr.NumberValue.create(param2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    static public Stream<Arguments> Provider_testEqual_2to3() {
        return Stream.of(arguments(1, 2), arguments(2, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGreater_2to3")
    public void testGreater_2to3(int param1, int param2) throws Exception {
        assertThat(Expr.Greater.create(Expr.NumberValue.create(param1), Expr.NumberValue.create(param2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    static public Stream<Arguments> Provider_testGreater_2to3() {
        return Stream.of(arguments(1, 2), arguments(2, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGreaterEqual_1to2")
    public void testGreaterEqual_1to2(int param1, int param2) throws Exception {
        assertThat(Expr.GreaterEqual.create(Expr.NumberValue.create(param1), Expr.NumberValue.create(param2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    static public Stream<Arguments> Provider_testGreaterEqual_1to2() {
        return Stream.of(arguments(2, 1), arguments(2, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLesser_2to3")
    public void testLesser_2to3(int param1, int param2) throws Exception {
        assertThat(Expr.Lesser.create(Expr.NumberValue.create(param1), Expr.NumberValue.create(param2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    static public Stream<Arguments> Provider_testLesser_2to3() {
        return Stream.of(arguments(2, 2), arguments(3, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLesserEqual_1to2")
    public void testLesserEqual_1to2(int param1, int param2) throws Exception {
        assertThat(Expr.LesserEqual.create(Expr.NumberValue.create(param1), Expr.NumberValue.create(param2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    static public Stream<Arguments> Provider_testLesserEqual_1to2() {
        return Stream.of(arguments(1, 2), arguments(2, 2));
    }
}
