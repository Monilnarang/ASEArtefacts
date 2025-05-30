package org.apache.baremaps.maplibre.expression;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.baremaps.maplibre.expression.Expressions.*;
import org.apache.baremaps.maplibre.vectortile.Feature;
import org.junit.jupiter.api.Test;

class ExpressionsTest_Purified {

    @Test
    void literal_1() {
        assertEquals(1, new Literal(1).evaluate(null));
    }

    @Test
    void literal_2() {
        assertEquals("value", new Literal("value").evaluate(null));
    }

    @Test
    void get_1() {
        assertEquals("value", new Get("key").evaluate(new Feature(0L, Map.of("key", "value"), null)));
    }

    @Test
    void get_2() {
        assertEquals(null, new Get("key").evaluate(new Feature(0L, Map.of(), null)));
    }

    @Test
    void has_1() {
        assertEquals(true, new Has("key").evaluate(new Feature(0L, Map.of("key", "value"), null)));
    }

    @Test
    void has_2() {
        assertEquals(false, new Has("key").evaluate(new Feature(0L, Map.of(), null)));
    }

    @Test
    void not_1() throws IOException {
        assertEquals(true, Expressions.read("[\"!\", false]").evaluate(null));
    }

    @Test
    void not_2() throws IOException {
        assertEquals(false, Expressions.read("[\"!\", true]").evaluate(null));
    }

    @Test
    void notEqual_1() throws IOException {
        assertEquals(true, Expressions.read("[\"!=\", 1, 2]").evaluate(null));
    }

    @Test
    void notEqual_2() throws IOException {
        assertEquals(false, Expressions.read("[\"!=\", 1, 1]").evaluate(null));
    }

    @Test
    void less_1() throws IOException {
        assertEquals(true, Expressions.read("[\"<\", 1, 2]").evaluate(null));
    }

    @Test
    void less_2() throws IOException {
        assertEquals(false, Expressions.read("[\"<\", 1, 1]").evaluate(null));
    }

    @Test
    void less_3() throws IOException {
        assertEquals(false, Expressions.read("[\"<\", 1, 0]").evaluate(null));
    }

    @Test
    void lessOrEqual_1() throws IOException {
        assertEquals(true, Expressions.read("[\"<=\", 1, 2]").evaluate(null));
    }

    @Test
    void lessOrEqual_2() throws IOException {
        assertEquals(true, Expressions.read("[\"<=\", 1, 1]").evaluate(null));
    }

    @Test
    void lessOrEqual_3() throws IOException {
        assertEquals(false, Expressions.read("[\"<=\", 1, 0]").evaluate(null));
    }

    @Test
    void equal_1() throws IOException {
        assertEquals(true, Expressions.read("[\"==\", 1, 1]").evaluate(null));
    }

    @Test
    void equal_2() throws IOException {
        assertEquals(false, Expressions.read("[\"==\", 1, 2]").evaluate(null));
    }

    @Test
    void greater_1() throws IOException {
        assertEquals(true, Expressions.read("[\">\", 1, 0]").evaluate(null));
    }

    @Test
    void greater_2() throws IOException {
        assertEquals(false, Expressions.read("[\">\", 1, 1]").evaluate(null));
    }

    @Test
    void greater_3() throws IOException {
        assertEquals(false, Expressions.read("[\">\", 1, 2]").evaluate(null));
    }

    @Test
    void greaterOrEqual_1() throws IOException {
        assertEquals(true, Expressions.read("[\">=\", 1, 0]").evaluate(null));
    }

    @Test
    void greaterOrEqual_2() throws IOException {
        assertEquals(true, Expressions.read("[\">=\", 1, 1]").evaluate(null));
    }

    @Test
    void greaterOrEqual_3() throws IOException {
        assertEquals(false, Expressions.read("[\">=\", 1, 2]").evaluate(null));
    }

    @Test
    void all_1() {
        assertEquals(true, new All(List.of(new Literal(true), new Literal(true))).evaluate(null));
    }

    @Test
    void all_2() {
        assertEquals(false, new All(List.of(new Literal(true), new Literal(false))).evaluate(null));
    }

    @Test
    void all_3() {
        assertEquals(false, new All(List.of(new Literal(false), new Literal(false))).evaluate(null));
    }

    @Test
    void all_4() {
        assertEquals(true, new All(List.of()).evaluate(null));
    }

    @Test
    void any_1() {
        assertEquals(true, new Any(List.of(new Literal(true), new Literal(true))).evaluate(null));
    }

    @Test
    void any_2() {
        assertEquals(true, new Any(List.of(new Literal(true), new Literal(false))).evaluate(null));
    }

    @Test
    void any_3() {
        assertEquals(false, new Any(List.of(new Literal(false), new Literal(false))).evaluate(null));
    }

    @Test
    void any_4() {
        assertEquals(false, new Any(List.of()).evaluate(null));
    }

    @Test
    void caseExpression_1() {
        assertEquals("a", new Case(new Literal(true), new Literal("a"), new Literal("b")).evaluate(null));
    }

    @Test
    void caseExpression_2() {
        assertEquals("b", new Case(new Literal(false), new Literal("a"), new Literal("b")).evaluate(null));
    }

    @Test
    void coalesce_1() {
        assertEquals("a", new Coalesce(List.of(new Literal(null), new Literal("a"), new Literal("b"))).evaluate(null));
    }

    @Test
    void coalesce_2() {
        assertEquals("b", new Coalesce(List.of(new Literal(null), new Literal("b"), new Literal("a"))).evaluate(null));
    }

    @Test
    void coalesce_3() {
        assertEquals(null, new Coalesce(List.of(new Literal(null))).evaluate(null));
    }

    @Test
    void coalesce_4() {
        assertEquals(null, new Coalesce(List.of()).evaluate(null));
    }

    @Test
    void match_1() throws IOException {
        assertEquals("foo", Expressions.read("[\"match\", \"foo\", \"foo\", \"foo\", \"bar\", \"bar\", \"baz\"]").evaluate(null));
    }

    @Test
    void match_2() throws IOException {
        assertEquals("bar", Expressions.read("[\"match\", \"bar\", \"foo\", \"foo\", \"bar\", \"bar\", \"baz\"]").evaluate(null));
    }

    @Test
    void match_3() throws IOException {
        assertEquals("baz", Expressions.read("[\"match\", \"baz\", \"foo\", \"foo\", \"bar\", \"bar\", \"baz\"]").evaluate(null));
    }
}
