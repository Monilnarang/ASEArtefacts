package org.apache.druid.query.extraction;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.commons.compress.utils.Lists;
import org.apache.druid.query.lookup.ImmutableLookupMap;
import org.apache.druid.query.lookup.LookupExtractor;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapBasedLookupExtractorTest_Purified {

    protected final Map<String, String> simpleLookupMap = ImmutableMap.of("foo", "bar", "null", "", "empty String", "", "", "empty_string");

    protected abstract LookupExtractor makeLookupExtractor(Map<String, String> map);

    protected List<String> unapply(final LookupExtractor lookup, @Nullable final String s) {
        return Lists.newArrayList(lookup.unapplyAll(Collections.singleton(s)));
    }

    @Test
    public void test_estimateHeapFootprint_1() {
        Assert.assertEquals(0L, makeLookupExtractor(Collections.emptyMap()).estimateHeapFootprint());
    }

    @Test
    public void test_estimateHeapFootprint_2() {
        Assert.assertEquals(388L, makeLookupExtractor(simpleLookupMap).estimateHeapFootprint());
    }
}
