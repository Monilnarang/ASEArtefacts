package org.apache.druid.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.java.util.common.ISE;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CollectionUtilsTest_Purified {

    Set<String> empty = ImmutableSet.of();

    Set<String> abc = ImmutableSet.of("a", "b", "c");

    Set<String> bcd = ImmutableSet.of("b", "c", "d");

    Set<String> efg = ImmutableSet.of("e", "f", "g");

    @Test
    public void testSubtract_1() {
        Assert.assertEquals(empty, CollectionUtils.subtract(empty, empty));
    }

    @Test
    public void testSubtract_2() {
        Assert.assertEquals(abc, CollectionUtils.subtract(abc, empty));
    }

    @Test
    public void testSubtract_3() {
        Assert.assertEquals(empty, CollectionUtils.subtract(abc, abc));
    }

    @Test
    public void testSubtract_4() {
        Assert.assertEquals(abc, CollectionUtils.subtract(abc, efg));
    }

    @Test
    public void testSubtract_5() {
        Assert.assertEquals(ImmutableSet.of("a"), CollectionUtils.subtract(abc, bcd));
    }

    @Test
    public void testIntersect_1() {
        Assert.assertEquals(empty, CollectionUtils.intersect(empty, empty));
    }

    @Test
    public void testIntersect_2() {
        Assert.assertEquals(abc, CollectionUtils.intersect(abc, abc));
    }

    @Test
    public void testIntersect_3() {
        Assert.assertEquals(empty, CollectionUtils.intersect(abc, efg));
    }

    @Test
    public void testIntersect_4() {
        Assert.assertEquals(ImmutableSet.of("b", "c"), CollectionUtils.intersect(abc, bcd));
    }

    @Test
    public void testUnion_1() {
        Assert.assertEquals(empty, CollectionUtils.union(empty, empty));
    }

    @Test
    public void testUnion_2() {
        Assert.assertEquals(abc, CollectionUtils.union(abc, abc));
    }

    @Test
    public void testUnion_3() {
        Assert.assertEquals(ImmutableSet.of("a", "b", "c", "e", "f", "g"), CollectionUtils.union(abc, efg));
    }

    @Test
    public void testUnion_4() {
        Assert.assertEquals(ImmutableSet.of("a", "b", "c", "d"), CollectionUtils.union(abc, bcd));
    }
}
