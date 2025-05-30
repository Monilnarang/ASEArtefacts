package org.apache.druid.client.cache;

import com.google.common.primitives.Ints;
import org.apache.druid.java.util.common.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapCacheTest_Purified extends CacheTestBase<MapCache> {

    private static final byte[] HI = StringUtils.toUtf8("hi");

    private static final byte[] HO = StringUtils.toUtf8("ho");

    private ByteCountingLRUMap baseMap;

    @Before
    public void setUp() {
        baseMap = new ByteCountingLRUMap(1024 * 1024);
        cache = new MapCache(baseMap);
    }

    public void put(Cache cache, String namespace, byte[] key, Integer value) {
        cache.put(new Cache.NamedKey(namespace, key), Ints.toByteArray(value));
    }

    public int get(Cache cache, String namespace, byte[] key) {
        return Ints.fromByteArray(cache.get(new Cache.NamedKey(namespace, key)));
    }

    @Test
    public void testSanity_1() {
        Assert.assertNull(cache.get(new Cache.NamedKey("a", HI)));
    }

    @Test
    public void testSanity_2_testMerged_2() {
        Assert.assertEquals(0, baseMap.size());
    }

    @Test
    public void testSanity_3_testMerged_3() {
        put(cache, "a", HI, 1);
        Assert.assertEquals(1, baseMap.size());
        Assert.assertEquals(1, get(cache, "a", HI));
        Assert.assertNull(cache.get(new Cache.NamedKey("the", HI)));
        put(cache, "the", HI, 2);
        Assert.assertEquals(2, baseMap.size());
        Assert.assertEquals(2, get(cache, "the", HI));
        put(cache, "the", HO, 10);
        Assert.assertEquals(3, baseMap.size());
        Assert.assertNull(cache.get(new Cache.NamedKey("a", HO)));
        Assert.assertEquals(10, get(cache, "the", HO));
    }
}
