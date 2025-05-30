package org.apache.druid.java.util.common.parsers;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import java.util.Collections;
import java.util.List;
import static org.apache.druid.java.util.common.parsers.ParserUtils.findDuplicates;
import static org.apache.druid.java.util.common.parsers.ParserUtils.getTransformationFunction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParserUtilsTest_Purified {

    @Test
    public void testInputWithDelimiterAndParserDisabled_1() {
        assertNull(getTransformationFunction("|", Splitter.on("|"), true).apply(null));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_2() {
        assertEquals("", getTransformationFunction("|", Splitter.on("|"), true).apply(""));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_3() {
        assertEquals(ImmutableList.of("foo", "boo"), getTransformationFunction("|", Splitter.on("|"), false).apply("foo|boo"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_4() {
        assertEquals(ImmutableList.of("1", "2", "3"), getTransformationFunction("|", Splitter.on("|"), false).apply("1|2|3"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_5() {
        assertEquals(ImmutableList.of("1", "-2", "3", "0", "-2"), getTransformationFunction("|", Splitter.on("|"), false).apply("1|-2|3|0|-2"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_6() {
        assertEquals("100", getTransformationFunction("|", Splitter.on("|"), false).apply("100"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_7() {
        assertEquals("1.23", getTransformationFunction("|", Splitter.on("|"), false).apply("1.23"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_8() {
        assertEquals("-2.0", getTransformationFunction("|", Splitter.on("|"), false).apply("-2.0"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_9() {
        assertEquals("1e2", getTransformationFunction("|", Splitter.on("|"), false).apply("1e2"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_10() {
        assertEquals(ImmutableList.of("1", "2", "3"), getTransformationFunction("|", Splitter.on("|"), false).apply("1|2|3"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_11() {
        assertEquals(ImmutableList.of("1", "-2", "3", "0", "-2"), getTransformationFunction("|", Splitter.on("|"), false).apply("1|-2|3|0|-2"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_12() {
        assertEquals(ImmutableList.of("-1.0", "-2.2", "3.1", "0.2", "-2.1"), getTransformationFunction("|", Splitter.on("|"), false).apply("-1.0|-2.2|3.1|0.2|-2.1"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_13() {
        assertEquals(ImmutableList.of("-1.23", "3.13", "23"), getTransformationFunction("|", Splitter.on("|"), false).apply("-1.23|3.13|23"));
    }

    @Test
    public void testInputWithDelimiterAndParserDisabled_14() {
        assertEquals(ImmutableList.of("-1.23", "3.13", "23", "foo", "-9"), getTransformationFunction("|", Splitter.on("|"), false).apply("-1.23|3.13|23|foo|-9"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_1() {
        assertNull(getTransformationFunction("|", Splitter.on("|"), true).apply(null));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_2() {
        assertEquals("", getTransformationFunction("|", Splitter.on("|"), true).apply(""));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_3() {
        assertEquals(ImmutableList.of("foo", "boo"), getTransformationFunction("|", Splitter.on("|"), true).apply("foo|boo"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_4() {
        assertEquals(ImmutableList.of(1L, 2L, 3L), getTransformationFunction("|", Splitter.on("|"), true).apply("1|2|3"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_5() {
        assertEquals(ImmutableList.of(1L, -2L, 3L, 0L, -2L), getTransformationFunction("|", Splitter.on("|"), true).apply("1|-2|3|0|-2"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_6() {
        assertEquals(100L, getTransformationFunction("|", Splitter.on("|"), true).apply("100"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_7() {
        assertEquals(1.23, getTransformationFunction("|", Splitter.on("|"), true).apply("1.23"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_8() {
        assertEquals(-2.0, getTransformationFunction("|", Splitter.on("|"), true).apply("-2.0"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_9() {
        assertEquals(100.0, getTransformationFunction("$", Splitter.on("|"), true).apply("1e2"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_10() {
        assertEquals(ImmutableList.of(1L, 2L, 3L), getTransformationFunction("|", Splitter.on("|"), true).apply("1|2|3"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_11() {
        assertEquals(ImmutableList.of(1L, -2L, 3L, 0L, -2L), getTransformationFunction("|", Splitter.on("|"), true).apply("1|-2|3|0|-2"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_12() {
        assertEquals(ImmutableList.of(-1.0, -2.2, 3.1, 0.2, -2.1), getTransformationFunction("|", Splitter.on("|"), true).apply("-1.0|-2.2|3.1|0.2|-2.1"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_13() {
        assertEquals(ImmutableList.of(-1.23, 3.13, 23L), getTransformationFunction("|", Splitter.on("|"), true).apply("-1.23|3.13|23"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_14() {
        assertEquals(ImmutableList.of(-1.23, 3.13, 23L, "foo", -9L), getTransformationFunction("|", Splitter.on("|"), true).apply("-1.23|3.13|23|foo|-9"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_1() {
        assertNull(getTransformationFunction("|", Splitter.on("$"), false).apply(null));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_2() {
        assertEquals("", getTransformationFunction("|", Splitter.on("$"), false).apply(""));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_3() {
        assertEquals("foo|boo", getTransformationFunction("$", Splitter.on("$"), false).apply("foo|boo"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_4() {
        assertEquals("100", getTransformationFunction("$", Splitter.on("$"), false).apply("100"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_5() {
        assertEquals("1.23", getTransformationFunction("$", Splitter.on("$"), false).apply("1.23"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_6() {
        assertEquals("-2.0", getTransformationFunction("$", Splitter.on("$"), false).apply("-2.0"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_7() {
        assertEquals("1e2", getTransformationFunction("$", Splitter.on("$"), false).apply("1e2"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_8() {
        assertEquals("1|2|3", getTransformationFunction("$", Splitter.on("$"), false).apply("1|2|3"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_9() {
        assertEquals("1|-2|3|0|-2", getTransformationFunction("$", Splitter.on("$"), false).apply("1|-2|3|0|-2"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_10() {
        assertEquals("-1.0|-2.2|3.1|0.2|-2.1", getTransformationFunction("$", Splitter.on("$"), false).apply("-1.0|-2.2|3.1|0.2|-2.1"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_11() {
        assertEquals("-1.23|3.13|23", getTransformationFunction("$", Splitter.on("$"), false).apply("-1.23|3.13|23"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingDisabled_12() {
        assertEquals("-1.23|3.13|23|foo|-9", getTransformationFunction("$", Splitter.on("$"), false).apply("-1.23|3.13|23|foo|-9"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_1() {
        assertNull(getTransformationFunction("$", Splitter.on("$"), true).apply(null));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_2() {
        assertEquals("", getTransformationFunction("$", Splitter.on("$"), true).apply(""));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_3() {
        assertEquals("foo|boo", getTransformationFunction("$", Splitter.on("$"), true).apply("foo|boo"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_4() {
        assertEquals(100L, getTransformationFunction("$", Splitter.on("$"), true).apply("100"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_5() {
        assertEquals(1.23, getTransformationFunction("$", Splitter.on("$"), true).apply("1.23"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_6() {
        assertEquals(-2.0, getTransformationFunction("$", Splitter.on("$"), true).apply("-2.0"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_7() {
        assertEquals(100.0, getTransformationFunction("$", Splitter.on("$"), true).apply("1e2"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_8() {
        assertEquals("1|2|3", getTransformationFunction("$", Splitter.on("$"), true).apply("1|2|3"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_9() {
        assertEquals("1|-2|3|0|-2", getTransformationFunction("$", Splitter.on("$"), true).apply("1|-2|3|0|-2"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_10() {
        assertEquals("-1.0|-2.2|3.1|0.2|-2.1", getTransformationFunction("$", Splitter.on("$"), true).apply("-1.0|-2.2|3.1|0.2|-2.1"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_11() {
        assertEquals("-1.23|3.13|23", getTransformationFunction("$", Splitter.on("$"), true).apply("-1.23|3.13|23"));
    }

    @Test
    public void testInputWithoutDelimiterAndNumberParsingEnabled_12() {
        assertEquals("-1.23|3.13|23|foo|-9", getTransformationFunction("$", Splitter.on("$"), true).apply("-1.23|3.13|23|foo|-9"));
    }
}
