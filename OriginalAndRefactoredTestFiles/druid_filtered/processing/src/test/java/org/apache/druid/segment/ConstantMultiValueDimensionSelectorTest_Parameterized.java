package org.apache.druid.segment;

import com.google.common.collect.ImmutableList;
import org.apache.druid.query.extraction.StringFormatExtractionFn;
import org.apache.druid.query.extraction.SubstringDimExtractionFn;
import org.apache.druid.query.filter.DruidObjectPredicate;
import org.apache.druid.query.filter.StringPredicateDruidPredicateFactory;
import org.apache.druid.segment.data.IndexedInts;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConstantMultiValueDimensionSelectorTest_Parameterized extends InitializedNullHandlingTest {

    private final DimensionSelector NULL_SELECTOR = DimensionSelector.multiConstant(null);

    private final DimensionSelector EMPTY_SELECTOR = DimensionSelector.multiConstant(Collections.emptyList());

    private final DimensionSelector SINGLE_SELECTOR = DimensionSelector.multiConstant(ImmutableList.of("billy"));

    private final DimensionSelector CONST_SELECTOR = DimensionSelector.multiConstant(ImmutableList.of("billy", "douglas"));

    private final DimensionSelector NULL_EXTRACTION_SELECTOR = DimensionSelector.multiConstant(null, new StringFormatExtractionFn("billy"));

    private final DimensionSelector CONST_EXTRACTION_SELECTOR = DimensionSelector.multiConstant(ImmutableList.of("billy", "douglas", "billy"), new SubstringDimExtractionFn(0, 4));

    @Test
    public void testLookupName_5() {
        Assert.assertEquals("billy", NULL_EXTRACTION_SELECTOR.lookupName(0));
    }

    @Test
    public void testGetObject_3() {
        Assert.assertEquals("billy", SINGLE_SELECTOR.getObject());
    }

    @Test
    public void testGetObject_4() {
        Assert.assertEquals(ImmutableList.of("billy", "douglas"), CONST_SELECTOR.getObject());
    }

    @Test
    public void testGetObject_5() {
        Assert.assertEquals("billy", NULL_EXTRACTION_SELECTOR.getObject());
    }

    @Test
    public void testGetObject_6() {
        Assert.assertEquals(ImmutableList.of("bill", "doug", "bill"), CONST_EXTRACTION_SELECTOR.getObject());
    }

    @Test
    public void testCoverage_1() {
        Assert.assertEquals(DimensionDictionarySelector.CARDINALITY_UNKNOWN, CONST_SELECTOR.getValueCardinality());
    }

    @Test
    public void testCoverage_2() {
        Assert.assertNull(CONST_SELECTOR.idLookup());
    }

    @Test
    public void testCoverage_3() {
        Assert.assertEquals(Object.class, CONST_SELECTOR.classOfObject());
    }

    @Test
    public void testCoverage_4() {
        Assert.assertTrue(CONST_SELECTOR.nameLookupPossibleInAdvance());
    }

    @Test
    public void testValueMatcher_1() {
        Assert.assertTrue(NULL_SELECTOR.makeValueMatcher((String) null).matches(false));
    }

    @Test
    public void testValueMatcher_2() {
        Assert.assertFalse(NULL_SELECTOR.makeValueMatcher("douglas").matches(false));
    }

    @Test
    public void testValueMatcher_3() {
        Assert.assertTrue(EMPTY_SELECTOR.makeValueMatcher((String) null).matches(false));
    }

    @Test
    public void testValueMatcher_4() {
        Assert.assertFalse(EMPTY_SELECTOR.makeValueMatcher("douglas").matches(false));
    }

    @Test
    public void testValueMatcher_7() {
        Assert.assertFalse(CONST_SELECTOR.makeValueMatcher("debbie").matches(false));
    }

    @Test
    public void testValueMatcher_8() {
        Assert.assertTrue(NULL_EXTRACTION_SELECTOR.makeValueMatcher("billy").matches(false));
    }

    @Test
    public void testValueMatcher_9() {
        Assert.assertFalse(NULL_EXTRACTION_SELECTOR.makeValueMatcher((String) null).matches(false));
    }

    @Test
    public void testValueMatcher_12() {
        Assert.assertFalse(CONST_EXTRACTION_SELECTOR.makeValueMatcher("billy").matches(false));
    }

    @Test
    public void testValueMatcher_13() {
        Assert.assertTrue(NULL_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.of(DruidObjectPredicate.isNull())).matches(false));
    }

    @Test
    public void testValueMatcher_14() {
        Assert.assertFalse(NULL_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("billy")).matches(false));
    }

    @Test
    public void testValueMatcher_15() {
        Assert.assertTrue(EMPTY_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo(null)).matches(false));
    }

    @Test
    public void testValueMatcher_16() {
        Assert.assertFalse(EMPTY_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("douglas")).matches(false));
    }

    @Test
    public void testValueMatcher_19() {
        Assert.assertFalse(CONST_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("debbie")).matches(false));
    }

    @Test
    public void testValueMatcher_20() {
        Assert.assertTrue(NULL_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("billy")).matches(false));
    }

    @Test
    public void testValueMatcher_21() {
        Assert.assertFalse(NULL_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo(null)).matches(false));
    }

    @Test
    public void testValueMatcher_24() {
        Assert.assertFalse(CONST_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("billy")).matches(false));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLookupName_1_1")
    public void testLookupName_1_1(int param1) {
        Assert.assertNull(NULL_SELECTOR.lookupName(param1));
    }

    static public Stream<Arguments> Provider_testLookupName_1_1() {
        return Stream.of(arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLookupName_2_2")
    public void testLookupName_2_2(int param1) {
        Assert.assertNull(EMPTY_SELECTOR.lookupName(param1));
    }

    static public Stream<Arguments> Provider_testLookupName_2_2() {
        return Stream.of(arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLookupName_3to4")
    public void testLookupName_3to4(String param1, int param2) {
        Assert.assertEquals(param1, CONST_SELECTOR.lookupName(param2));
    }

    static public Stream<Arguments> Provider_testLookupName_3to4() {
        return Stream.of(arguments("billy", 0), arguments("douglas", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLookupName_6to8")
    public void testLookupName_6to8(String param1, int param2) {
        Assert.assertEquals(param1, CONST_EXTRACTION_SELECTOR.lookupName(param2));
    }

    static public Stream<Arguments> Provider_testLookupName_6to8() {
        return Stream.of(arguments("bill", 0), arguments("doug", 1), arguments("bill", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueMatcher_5to6")
    public void testValueMatcher_5to6(String param1) {
        Assert.assertTrue(CONST_SELECTOR.makeValueMatcher("billy").matches(param1));
    }

    static public Stream<Arguments> Provider_testValueMatcher_5to6() {
        return Stream.of(arguments("billy"), arguments("douglas"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueMatcher_10to11")
    public void testValueMatcher_10to11(String param1) {
        Assert.assertTrue(CONST_EXTRACTION_SELECTOR.makeValueMatcher("bill").matches(param1));
    }

    static public Stream<Arguments> Provider_testValueMatcher_10to11() {
        return Stream.of(arguments("bill"), arguments("doug"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueMatcher_17to18")
    public void testValueMatcher_17to18(String param1) {
        Assert.assertTrue(CONST_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("billy")).matches(param1));
    }

    static public Stream<Arguments> Provider_testValueMatcher_17to18() {
        return Stream.of(arguments("billy"), arguments("douglas"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueMatcher_22to23")
    public void testValueMatcher_22to23(String param1) {
        Assert.assertTrue(CONST_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("bill")).matches(param1));
    }

    static public Stream<Arguments> Provider_testValueMatcher_22to23() {
        return Stream.of(arguments("bill"), arguments("doug"));
    }
}
