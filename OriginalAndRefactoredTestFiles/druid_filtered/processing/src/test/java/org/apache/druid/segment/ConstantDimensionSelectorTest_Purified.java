package org.apache.druid.segment;

import org.apache.druid.query.extraction.StringFormatExtractionFn;
import org.apache.druid.query.extraction.SubstringDimExtractionFn;
import org.apache.druid.query.filter.DruidObjectPredicate;
import org.apache.druid.query.filter.DruidPredicateFactory;
import org.apache.druid.query.filter.DruidPredicateMatch;
import org.apache.druid.query.filter.StringPredicateDruidPredicateFactory;
import org.apache.druid.query.filter.ValueMatcher;
import org.apache.druid.segment.data.IndexedInts;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;

public class ConstantDimensionSelectorTest_Purified extends InitializedNullHandlingTest {

    private final DimensionSelector NULL_SELECTOR = DimensionSelector.constant(null);

    private final DimensionSelector CONST_SELECTOR = DimensionSelector.constant("billy");

    private final DimensionSelector NULL_EXTRACTION_SELECTOR = DimensionSelector.constant(null, new StringFormatExtractionFn("billy"));

    private final DimensionSelector CONST_EXTRACTION_SELECTOR = DimensionSelector.constant("billybilly", new SubstringDimExtractionFn(0, 5));

    @Test
    public void testGetValueCardinality_1() {
        Assert.assertEquals(1, NULL_SELECTOR.getValueCardinality());
    }

    @Test
    public void testGetValueCardinality_2() {
        Assert.assertEquals(1, CONST_SELECTOR.getValueCardinality());
    }

    @Test
    public void testGetValueCardinality_3() {
        Assert.assertEquals(1, NULL_EXTRACTION_SELECTOR.getValueCardinality());
    }

    @Test
    public void testGetValueCardinality_4() {
        Assert.assertEquals(1, CONST_EXTRACTION_SELECTOR.getValueCardinality());
    }

    @Test
    public void testLookupName_1() {
        Assert.assertEquals(null, NULL_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_2() {
        Assert.assertEquals("billy", CONST_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_3() {
        Assert.assertEquals("billy", NULL_EXTRACTION_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupName_4() {
        Assert.assertEquals("billy", CONST_EXTRACTION_SELECTOR.lookupName(0));
    }

    @Test
    public void testLookupId_1() {
        Assert.assertEquals(0, NULL_SELECTOR.idLookup().lookupId(null));
    }

    @Test
    public void testLookupId_2() {
        Assert.assertEquals(-1, NULL_SELECTOR.idLookup().lookupId(""));
    }

    @Test
    public void testLookupId_3() {
        Assert.assertEquals(-1, NULL_SELECTOR.idLookup().lookupId("billy"));
    }

    @Test
    public void testLookupId_4() {
        Assert.assertEquals(-1, NULL_SELECTOR.idLookup().lookupId("bob"));
    }

    @Test
    public void testLookupId_5() {
        Assert.assertEquals(-1, CONST_SELECTOR.idLookup().lookupId(null));
    }

    @Test
    public void testLookupId_6() {
        Assert.assertEquals(-1, CONST_SELECTOR.idLookup().lookupId(""));
    }

    @Test
    public void testLookupId_7() {
        Assert.assertEquals(0, CONST_SELECTOR.idLookup().lookupId("billy"));
    }

    @Test
    public void testLookupId_8() {
        Assert.assertEquals(-1, CONST_SELECTOR.idLookup().lookupId("bob"));
    }

    @Test
    public void testLookupId_9() {
        Assert.assertEquals(-1, NULL_EXTRACTION_SELECTOR.idLookup().lookupId(null));
    }

    @Test
    public void testLookupId_10() {
        Assert.assertEquals(-1, NULL_EXTRACTION_SELECTOR.idLookup().lookupId(""));
    }

    @Test
    public void testLookupId_11() {
        Assert.assertEquals(0, NULL_EXTRACTION_SELECTOR.idLookup().lookupId("billy"));
    }

    @Test
    public void testLookupId_12() {
        Assert.assertEquals(-1, NULL_EXTRACTION_SELECTOR.idLookup().lookupId("bob"));
    }

    @Test
    public void testLookupId_13() {
        Assert.assertEquals(-1, CONST_EXTRACTION_SELECTOR.idLookup().lookupId(null));
    }

    @Test
    public void testLookupId_14() {
        Assert.assertEquals(-1, CONST_EXTRACTION_SELECTOR.idLookup().lookupId(""));
    }

    @Test
    public void testLookupId_15() {
        Assert.assertEquals(0, CONST_EXTRACTION_SELECTOR.idLookup().lookupId("billy"));
    }

    @Test
    public void testLookupId_16() {
        Assert.assertEquals(-1, CONST_EXTRACTION_SELECTOR.idLookup().lookupId("bob"));
    }
}
