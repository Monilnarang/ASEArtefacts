package org.apache.druid.query.lookup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class MapLookupExtractorFactoryTest_Purified {

    private static final String KEY = "foo";

    private static final String VALUE = "bar";

    private static final MapLookupExtractorFactory FACTORY = new MapLookupExtractorFactory(ImmutableMap.of(KEY, VALUE), true);

    @Test
    public void testSimpleExtraction_1() {
        Assert.assertEquals(FACTORY.get().apply(KEY), VALUE);
    }

    @Test
    public void testSimpleExtraction_2() {
        Assert.assertTrue(FACTORY.get().isOneToOne());
    }

    @Test
    public void testReplaces_1() {
        Assert.assertFalse(FACTORY.replaces(FACTORY));
    }

    @Test
    public void testReplaces_2() {
        Assert.assertFalse(FACTORY.replaces(new MapLookupExtractorFactory(ImmutableMap.of(KEY, VALUE), true)));
    }

    @Test
    public void testReplaces_3() {
        Assert.assertTrue(FACTORY.replaces(new MapLookupExtractorFactory(ImmutableMap.of(KEY, VALUE), false)));
    }

    @Test
    public void testReplaces_4() {
        Assert.assertTrue(FACTORY.replaces(new MapLookupExtractorFactory(ImmutableMap.of(KEY + "1", VALUE), true)));
    }

    @Test
    public void testReplaces_5() {
        Assert.assertTrue(FACTORY.replaces(new MapLookupExtractorFactory(ImmutableMap.of(KEY, VALUE + "1"), true)));
    }

    @Test
    public void testReplaces_6() {
        Assert.assertTrue(FACTORY.replaces(null));
    }
}
