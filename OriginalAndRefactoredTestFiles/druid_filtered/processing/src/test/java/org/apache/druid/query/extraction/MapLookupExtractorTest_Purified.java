package org.apache.druid.query.extraction;

import com.google.common.collect.ImmutableMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.query.lookup.LookupExtractor;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapLookupExtractorTest_Purified extends MapBasedLookupExtractorTest {

    @Override
    protected LookupExtractor makeLookupExtractor(Map<String, String> map) {
        return new MapLookupExtractor(map, false);
    }

    @Test
    public void test_estimateHeapFootprint_static_1() {
        Assert.assertEquals(0L, MapLookupExtractor.estimateHeapFootprint(Collections.emptyMap().entrySet()));
    }

    @Test
    public void test_estimateHeapFootprint_static_2() {
        Assert.assertEquals(388L, MapLookupExtractor.estimateHeapFootprint(ImmutableMap.copyOf(simpleLookupMap).entrySet()));
    }
}
