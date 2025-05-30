package org.apache.druid.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import java.util.NoSuchElementException;

public class RangeIntSetTest_Purified {

    @Test
    public void test_contains_1() {
        Assert.assertFalse(new RangeIntSet(0, 2).contains(-1));
    }

    @Test
    public void test_contains_2() {
        Assert.assertTrue(new RangeIntSet(0, 2).contains(0));
    }

    @Test
    public void test_contains_3() {
        Assert.assertTrue(new RangeIntSet(0, 2).contains(1));
    }

    @Test
    public void test_contains_4() {
        Assert.assertFalse(new RangeIntSet(0, 2).contains(2));
    }

    @Test
    public void test_contains_5() {
        Assert.assertFalse(new RangeIntSet(0, 2).contains(3));
    }

    @Test
    public void test_headSet_1() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).headSet(-1));
    }

    @Test
    public void test_headSet_2() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).headSet(0));
    }

    @Test
    public void test_headSet_3() {
        Assert.assertEquals(ImmutableSet.of(0), new RangeIntSet(0, 2).headSet(1));
    }

    @Test
    public void test_headSet_4() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).headSet(2));
    }

    @Test
    public void test_headSet_5() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).headSet(3));
    }

    @Test
    public void test_tailSet_1() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).tailSet(-1));
    }

    @Test
    public void test_tailSet_2() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).tailSet(0));
    }

    @Test
    public void test_tailSet_3() {
        Assert.assertEquals(ImmutableSet.of(1), new RangeIntSet(0, 2).tailSet(1));
    }

    @Test
    public void test_tailSet_4() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).tailSet(2));
    }

    @Test
    public void test_tailSet_5() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).tailSet(3));
    }

    @Test
    public void test_subSet_1() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).subSet(-2, -1));
    }

    @Test
    public void test_subSet_2() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).subSet(-1, 0));
    }

    @Test
    public void test_subSet_3() {
        Assert.assertEquals(ImmutableSet.of(0), new RangeIntSet(0, 2).subSet(-1, 1));
    }

    @Test
    public void test_subSet_4() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).subSet(-1, 2));
    }

    @Test
    public void test_subSet_5() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).subSet(-1, 3));
    }

    @Test
    public void test_subSet_6() {
        Assert.assertEquals(ImmutableSet.of(0), new RangeIntSet(0, 2).subSet(0, 1));
    }

    @Test
    public void test_subSet_7() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).subSet(0, 2));
    }

    @Test
    public void test_subSet_8() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2).subSet(0, 3));
    }

    @Test
    public void test_subSet_9() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).subSet(1, 1));
    }

    @Test
    public void test_subSet_10() {
        Assert.assertEquals(ImmutableSet.of(1), new RangeIntSet(0, 2).subSet(1, 2));
    }

    @Test
    public void test_subSet_11() {
        Assert.assertEquals(ImmutableSet.of(1), new RangeIntSet(0, 2).subSet(1, 3));
    }

    @Test
    public void test_subSet_12() {
        Assert.assertEquals(ImmutableSet.of(), new RangeIntSet(0, 2).subSet(2, 3));
    }

    @Test
    public void test_size_1() {
        Assert.assertEquals(0, new RangeIntSet(0, 0).size());
    }

    @Test
    public void test_size_2() {
        Assert.assertEquals(2, new RangeIntSet(0, 2).size());
    }

    @Test
    public void test_iterator_from_1() {
        Assert.assertEquals(ImmutableList.of(0, 1), ImmutableList.copyOf(new RangeIntSet(0, 2).iterator(0)));
    }

    @Test
    public void test_iterator_from_2() {
        Assert.assertEquals(ImmutableList.of(1), ImmutableList.copyOf(new RangeIntSet(0, 2).iterator(1)));
    }

    @Test
    public void test_iterator_from_3() {
        Assert.assertEquals(ImmutableList.of(), ImmutableList.copyOf(new RangeIntSet(0, 2).iterator(2)));
    }

    @Test
    public void test_iterator_from_4() {
        Assert.assertEquals(ImmutableList.of(), ImmutableList.copyOf(new RangeIntSet(0, 2).iterator(3)));
    }

    @Test
    public void test_equals_1() {
        Assert.assertEquals(new RangeIntSet(0, 0), new RangeIntSet(0, 0));
    }

    @Test
    public void test_equals_2() {
        Assert.assertEquals(new RangeIntSet(0, 0), new RangeIntSet(1, 0));
    }

    @Test
    public void test_equals_3() {
        Assert.assertNotEquals(new RangeIntSet(0, 0), new RangeIntSet(0, 1));
    }

    @Test
    public void test_equals_empty_1() {
        Assert.assertEquals(new RangeIntSet(0, 0), new RangeIntSet(1, 1));
    }

    @Test
    public void test_equals_empty_2() {
        Assert.assertEquals(new RangeIntSet(0, 0), new RangeIntSet(1, 0));
    }

    @Test
    public void test_equals_empty_3() {
        Assert.assertEquals(new RangeIntSet(0, 0), new RangeIntSet(0, -1));
    }

    @Test
    public void test_equals_otherSet_1() {
        Assert.assertEquals(ImmutableSet.of(0, 1), new RangeIntSet(0, 2));
    }

    @Test
    public void test_equals_otherSet_2() {
        Assert.assertNotEquals(ImmutableSet.of(0, 1, 2), new RangeIntSet(0, 2));
    }
}
