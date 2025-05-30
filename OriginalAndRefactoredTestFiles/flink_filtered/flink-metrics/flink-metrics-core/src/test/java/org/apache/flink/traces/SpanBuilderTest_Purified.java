package org.apache.flink.traces;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SpanBuilderTest_Purified {

    @Test
    void testSpanBuilder_1_testMerged_1() {
        Span span1 = new SpanBuilder(SpanBuilderTest.class, "testSpanBuilder").build();
        assertThat(span1.getStartTsMillis()).isGreaterThan(0);
        assertThat(span1.getEndTsMillis()).isGreaterThan(0);
    }

    @Test
    void testSpanBuilder_3_testMerged_2() {
        long startTsMillis = System.currentTimeMillis();
        Span span2 = new SpanBuilder(SpanBuilderTest.class, "testSpanBuilder").setStartTsMillis(startTsMillis).build();
        assertThat(span2.getStartTsMillis()).isEqualTo(startTsMillis);
        assertThat(span2.getEndTsMillis()).isEqualTo(startTsMillis);
        long endTsMillis = System.currentTimeMillis();
        Span span3 = new SpanBuilder(SpanBuilderTest.class, "testSpanBuilder").setEndTsMillis(endTsMillis).build();
        assertThat(span3.getStartTsMillis()).isGreaterThan(0);
        assertThat(span3.getEndTsMillis()).isEqualTo(endTsMillis);
        Span span4 = new SpanBuilder(SpanBuilderTest.class, "testSpanBuilder").setStartTsMillis(startTsMillis).setEndTsMillis(endTsMillis).build();
        assertThat(span4.getStartTsMillis()).isEqualTo(startTsMillis);
        assertThat(span4.getEndTsMillis()).isEqualTo(endTsMillis);
    }
}
