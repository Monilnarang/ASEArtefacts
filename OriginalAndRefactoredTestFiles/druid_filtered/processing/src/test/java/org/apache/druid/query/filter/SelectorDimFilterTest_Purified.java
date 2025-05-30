package org.apache.druid.query.filter;

import com.google.common.collect.Sets;
import org.apache.druid.query.extraction.RegexDimExtractionFn;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;

public class SelectorDimFilterTest_Purified {

    @Test
    public void testToString_1() {
        SelectorDimFilter selectorDimFilter = new SelectorDimFilter("abc", "d", null);
        Assert.assertEquals("abc = d", selectorDimFilter.toString());
    }

    @Test
    public void testToString_2() {
        RegexDimExtractionFn regexFn = new RegexDimExtractionFn(".*", false, null);
        SelectorDimFilter selectorDimFilter2 = new SelectorDimFilter("abc", "d", regexFn);
        Assert.assertEquals("regex(/.*/, 1)(abc) = d", selectorDimFilter2.toString());
    }
}
