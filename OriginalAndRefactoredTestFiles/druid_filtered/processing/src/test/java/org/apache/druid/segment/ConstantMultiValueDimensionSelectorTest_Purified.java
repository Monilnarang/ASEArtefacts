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

public class ConstantMultiValueDimensionSelectorTest_Purified extends InitializedNullHandlingTest {

    private final DimensionSelector NULL_SELECTOR = DimensionSelector.multiConstant(null);

    private final DimensionSelector EMPTY_SELECTOR = DimensionSelector.multiConstant(Collections.emptyList());

    private final DimensionSelector SINGLE_SELECTOR = DimensionSelector.multiConstant(ImmutableList.of("billy"));

    private final DimensionSelector CONST_SELECTOR = DimensionSelector.multiConstant(ImmutableList.of("billy", "douglas"));

    private final DimensionSelector NULL_EXTRACTION_SELECTOR = DimensionSelector.multiConstant(null, new StringFormatExtractionFn("billy"));

    private final DimensionSelector CONST_EXTRACTION_SELECTOR = DimensionSelector.multiConstant(ImmutableList.of("billy", "douglas", "billy"), new SubstringDimExtractionFn(0, 4));

    @Test
    public void testLookupName_1() {
        Assert.assertNull(NULL_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_2() {
        Assert.assertNull(EMPTY_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_3() {
        Assert.assertEquals("billy", CONST_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_4() {
        Assert.assertEquals("douglas", CONST_SELECTOR.lookupName(1));
    }

    @Test
    public void testLookupName_5() {
        Assert.assertEquals("billy", NULL_EXTRACTION_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_6() {
        Assert.assertEquals("bill", CONST_EXTRACTION_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_7() {
        Assert.assertEquals("doug", CONST_EXTRACTION_SELECTOR.lookupName(1));
    }

    @Test
    public void testLookupName_8() {
        Assert.assertEquals("bill", CONST_EXTRACTION_SELECTOR.lookupName(2));
    }

    @Test
    public void testGetObject_1() {
        Assert.assertNull(NULL_SELECTOR.lookupName(0));
    }

    @Test
    public void testGetObject_2() {
        Assert.assertNull(EMPTY_SELECTOR.lookupName(0));
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
    public void testValueMatcher_5() {
        Assert.assertTrue(CONST_SELECTOR.makeValueMatcher("billy").matches(false));
    }

    @Test
    public void testValueMatcher_6() {
        Assert.assertTrue(CONST_SELECTOR.makeValueMatcher("douglas").matches(false));
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
    public void testValueMatcher_10() {
        Assert.assertTrue(CONST_EXTRACTION_SELECTOR.makeValueMatcher("bill").matches(false));
    }

    @Test
    public void testValueMatcher_11() {
        Assert.assertTrue(CONST_EXTRACTION_SELECTOR.makeValueMatcher("doug").matches(false));
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
    public void testValueMatcher_17() {
        Assert.assertTrue(CONST_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("billy")).matches(false));
    }

    @Test
    public void testValueMatcher_18() {
        Assert.assertTrue(CONST_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("douglas")).matches(false));
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
    public void testValueMatcher_22() {
        Assert.assertTrue(CONST_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("bill")).matches(false));
    }

    @Test
    public void testValueMatcher_23() {
        Assert.assertTrue(CONST_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("doug")).matches(false));
    }

    @Test
    public void testValueMatcher_24() {
        Assert.assertFalse(CONST_EXTRACTION_SELECTOR.makeValueMatcher(StringPredicateDruidPredicateFactory.equalTo("billy")).matches(false));
    }
}
