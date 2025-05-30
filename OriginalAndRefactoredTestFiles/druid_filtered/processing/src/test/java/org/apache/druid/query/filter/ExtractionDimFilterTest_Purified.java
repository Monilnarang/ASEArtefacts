package org.apache.druid.query.filter;

import org.apache.druid.query.extraction.IdentityExtractionFn;
import org.apache.druid.query.extraction.RegexDimExtractionFn;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class ExtractionDimFilterTest_Purified {

    @Test
    public void testGetCacheKey_1_testMerged_1() {
        ExtractionDimFilter extractionDimFilter = new ExtractionDimFilter("abc", "d", IdentityExtractionFn.getInstance(), null);
        ExtractionDimFilter extractionDimFilter2 = new ExtractionDimFilter("ab", "cd", IdentityExtractionFn.getInstance(), null);
        Assert.assertFalse(Arrays.equals(extractionDimFilter.getCacheKey(), extractionDimFilter2.getCacheKey()));
        ExtractionDimFilter extractionDimFilter3 = new ExtractionDimFilter("ab", "cd", new RegexDimExtractionFn("xx", null, null), null);
        Assert.assertFalse(Arrays.equals(extractionDimFilter2.getCacheKey(), extractionDimFilter3.getCacheKey()));
    }

    @Test
    public void testGetCacheKey_3() {
        Assert.assertNotNull(new ExtractionDimFilter("foo", null, new RegexDimExtractionFn("xx", null, null), null).getCacheKey());
    }
}
