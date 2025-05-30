package org.apache.druid.query.groupby.having;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.query.extraction.ExtractionFn;
import org.apache.druid.query.extraction.RegexDimExtractionFn;
import org.apache.druid.query.groupby.ResultRow;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class DimensionSelectorHavingSpecTest_Purified {

    private ResultRow getTestRow(Object dimensionValue) {
        return ResultRow.of(dimensionValue);
    }

    @Test
    public void testEquals_1_testMerged_1() {
        ExtractionFn extractionFn1 = new RegexDimExtractionFn("^([^,]*),", false, "");
        ExtractionFn extractionFn2 = new RegexDimExtractionFn(",(.*)", false, "");
        ExtractionFn extractionFn3 = new RegexDimExtractionFn("^([^,]*),", false, "");
        HavingSpec dimHavingSpec1 = new DimensionSelectorHavingSpec("dim", "v", extractionFn1);
        HavingSpec dimHavingSpec2 = new DimensionSelectorHavingSpec("dim", "v", extractionFn3);
        HavingSpec dimHavingSpec13 = new DimensionSelectorHavingSpec("dim", "value", extractionFn1);
        HavingSpec dimHavingSpec14 = new DimensionSelectorHavingSpec("dim", "value", extractionFn2);
        Assert.assertEquals(dimHavingSpec1, dimHavingSpec2);
        Assert.assertNotEquals(dimHavingSpec13, dimHavingSpec14);
    }

    @Test
    public void testEquals_2() {
        HavingSpec dimHavingSpec3 = new DimensionSelectorHavingSpec("dim1", "v", null);
        HavingSpec dimHavingSpec4 = new DimensionSelectorHavingSpec("dim2", "v", null);
        Assert.assertNotEquals(dimHavingSpec3, dimHavingSpec4);
    }

    @Test
    public void testEquals_3() {
        HavingSpec dimHavingSpec5 = new DimensionSelectorHavingSpec("dim", "v1", null);
        HavingSpec dimHavingSpec6 = new DimensionSelectorHavingSpec("dim", "v2", null);
        Assert.assertNotEquals(dimHavingSpec5, dimHavingSpec6);
    }

    @Test
    public void testEquals_4() {
        HavingSpec dimHavingSpec7 = new DimensionSelectorHavingSpec("dim", null, null);
        HavingSpec dimHavingSpec8 = new DimensionSelectorHavingSpec("dim", null, null);
        Assert.assertEquals(dimHavingSpec7, dimHavingSpec8);
    }

    @Test
    public void testEquals_5() {
        HavingSpec dimHavingSpec9 = new DimensionSelectorHavingSpec("dim1", null, null);
        HavingSpec dimHavingSpec10 = new DimensionSelectorHavingSpec("dim2", null, null);
        Assert.assertNotEquals(dimHavingSpec9, dimHavingSpec10);
    }

    @Test
    public void testEquals_6() {
        HavingSpec dimHavingSpec11 = new DimensionSelectorHavingSpec("dim1", "v", null);
        HavingSpec dimHavingSpec12 = new DimensionSelectorHavingSpec("dim2", null, null);
        Assert.assertNotEquals(dimHavingSpec11, dimHavingSpec12);
    }
}
