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

public class BooleanNumberConditionsVisitorTest_Purified {

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
    public void testTrue_2() throws Exception {
        assertThat(loadCondition("condition-true.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
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
    public void testAnd_5() throws Exception {
        assertThat(loadCondition("condition-and.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
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
    public void testOr_5() throws Exception {
        assertThat(loadCondition("condition-or.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testNot_1_testMerged_1() throws Exception {
        final Expr.Greater trueExpr = Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1));
        final Expr.Greater falseExpr = Expr.Greater.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2));
        assertThat(Expr.Not.create(falseExpr).accept(new BooleanNumberConditionsVisitor())).isTrue();
        assertThat(Expr.Not.create(trueExpr).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testNot_3() throws Exception {
        assertThat(loadCondition("condition-not.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testEqual_1() throws Exception {
        assertThat(Expr.Equal.create(Expr.NumberValue.create(2), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testEqual_2() throws Exception {
        assertThat(Expr.Equal.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testEqual_3() throws Exception {
        assertThat(Expr.Equal.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testEqual_4() throws Exception {
        assertThat(loadCondition("condition-equal.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreater_1() throws Exception {
        assertThat(Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreater_2() throws Exception {
        assertThat(Expr.Greater.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testGreater_3() throws Exception {
        assertThat(Expr.Greater.create(Expr.NumberValue.create(2), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testGreater_4() throws Exception {
        assertThat(loadCondition("condition-greater.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreaterEqual_1() throws Exception {
        assertThat(Expr.GreaterEqual.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreaterEqual_2() throws Exception {
        assertThat(Expr.GreaterEqual.create(Expr.NumberValue.create(2), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testGreaterEqual_3() throws Exception {
        assertThat(Expr.GreaterEqual.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testGreaterEqual_4() throws Exception {
        assertThat(loadCondition("condition-greater-equal.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testLesser_1() throws Exception {
        assertThat(Expr.Lesser.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testLesser_2() throws Exception {
        assertThat(Expr.Lesser.create(Expr.NumberValue.create(2), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testLesser_3() throws Exception {
        assertThat(Expr.Lesser.create(Expr.NumberValue.create(3), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testLesser_4() throws Exception {
        assertThat(loadCondition("condition-lesser.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testLesserEqual_1() throws Exception {
        assertThat(Expr.LesserEqual.create(Expr.NumberValue.create(1), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testLesserEqual_2() throws Exception {
        assertThat(Expr.LesserEqual.create(Expr.NumberValue.create(2), Expr.NumberValue.create(2)).accept(new BooleanNumberConditionsVisitor())).isTrue();
    }

    @Test
    public void testLesserEqual_3() throws Exception {
        assertThat(Expr.LesserEqual.create(Expr.NumberValue.create(2), Expr.NumberValue.create(1)).accept(new BooleanNumberConditionsVisitor())).isFalse();
    }

    @Test
    public void testLesserEqual_4() throws Exception {
        assertThat(loadCondition("condition-lesser-equal.json").accept(new BooleanNumberConditionsVisitor())).isTrue();
    }
}
