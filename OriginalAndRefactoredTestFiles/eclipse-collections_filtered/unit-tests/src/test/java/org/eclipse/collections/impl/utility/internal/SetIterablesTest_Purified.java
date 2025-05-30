package org.eclipse.collections.impl.utility.internal;

import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.SetIterable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetIterablesTest_Purified {

    private void assertUnion(SetIterable<? extends Number> set1, SetIterable<? extends Number> set2, SetIterable<? extends Number> expected) {
        SetIterable<? extends Number> actual1 = SetIterables.union(set1, set2);
        SetIterable<? extends Number> actual2 = SetIterables.union(set2, set1);
        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
    }

    private void assertIntersect(SetIterable<? extends Number> set1, SetIterable<? extends Number> set2, SetIterable<? extends Number> expected) {
        SetIterable<? extends Number> actual1 = SetIterables.intersect(set1, set2);
        SetIterable<? extends Number> actual2 = SetIterables.intersect(set2, set1);
        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
    }

    private void assertDifference(SetIterable<? extends Number> set1, SetIterable<? extends Number> set2, SetIterable<? extends Number> expected) {
        SetIterable<? extends Number> actual = SetIterables.difference(set1, set2);
        assertEquals(expected, actual);
    }

    @Test
    public void union_1_testMerged_1() {
        this.assertUnion(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3, 4, 5), Sets.mutable.with(1, 2, 3, 4, 5));
    }

    @Test
    public void union_2() {
        this.assertUnion(Sets.mutable.with(1, 2L, 3), Sets.immutable.with(3, 4L, 5), Sets.mutable.with(1, 2L, 3, 4L, 5));
    }

    @Test
    public void union_3() {
        this.assertUnion(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3L, 4L, 5L), Sets.mutable.<Number>with(1, 2, 3, 3L, 4L, 5L));
    }

    @Test
    public void union_5() {
        this.assertUnion(Sets.mutable.with(1, 2, 3, 6), Sets.immutable.with(3, 4, 5), Sets.mutable.with(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void union_6() {
        this.assertUnion(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3, 4, 5, 6), Sets.mutable.with(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void union_7() {
        this.assertUnion(Sets.mutable.empty(), Sets.immutable.empty(), Sets.mutable.empty());
    }

    @Test
    public void union_8() {
        this.assertUnion(Sets.mutable.empty(), Sets.immutable.with(3, 4, 5), Sets.mutable.with(3, 4, 5));
    }

    @Test
    public void union_9() {
        this.assertUnion(Sets.mutable.with(1, 2, 3), Sets.immutable.empty(), Sets.mutable.with(1, 2, 3));
    }

    @Test
    public void intersect_1() {
        this.assertIntersect(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3, 4, 5), Sets.mutable.with(3));
    }

    @Test
    public void intersect_2() {
        this.assertIntersect(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3L, 4L, 5L), Sets.mutable.empty());
    }

    @Test
    public void intersect_3() {
        this.assertIntersect(Sets.mutable.with(1, 2, 3, 6), Sets.immutable.with(3, 4, 5), Sets.mutable.with(3));
    }

    @Test
    public void intersect_4() {
        this.assertIntersect(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3, 4, 5, 6), Sets.mutable.with(3));
    }

    @Test
    public void intersect_5() {
        this.assertIntersect(Sets.mutable.empty(), Sets.immutable.empty(), Sets.mutable.empty());
    }

    @Test
    public void intersect_6() {
        this.assertIntersect(Sets.mutable.empty(), Sets.immutable.with(3, 4, 5), Sets.mutable.empty());
    }

    @Test
    public void intersect_7() {
        this.assertIntersect(Sets.mutable.with(1, 2, 3), Sets.immutable.empty(), Sets.mutable.empty());
    }

    @Test
    public void difference_1() {
        this.assertDifference(Sets.mutable.with(1, 2, 3), Sets.immutable.with(3, 4, 5), Sets.mutable.with(1, 2));
    }

    @Test
    public void difference_2() {
        this.assertDifference(Sets.mutable.with(1, 2, 3), Sets.immutable.with(1, 2, 3), Sets.mutable.empty());
    }

    @Test
    public void difference_3() {
        this.assertDifference(Sets.mutable.empty(), Sets.immutable.with(), Sets.mutable.empty());
    }

    @Test
    public void difference_4() {
        this.assertDifference(Sets.immutable.empty(), Sets.mutable.with(3, 4, 5), Sets.mutable.empty());
    }

    @Test
    public void difference_5() {
        this.assertDifference(Sets.immutable.with(1, 2, 3), Sets.mutable.empty(), Sets.mutable.with(1, 2, 3));
    }
}
