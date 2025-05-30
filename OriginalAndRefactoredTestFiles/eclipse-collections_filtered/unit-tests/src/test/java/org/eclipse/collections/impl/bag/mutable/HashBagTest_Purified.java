package org.eclipse.collections.impl.bag.mutable;

import java.util.Collections;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashBagTest_Purified extends MutableBagTestCase {

    @Override
    protected <T> MutableBag<T> newWith(T... littleElements) {
        return HashBag.newBagWith(littleElements);
    }

    @Override
    protected <T> MutableBag<T> newWithOccurrences(ObjectIntPair<T>... elementsWithOccurrences) {
        MutableBag<T> bag = this.newWith();
        for (int i = 0; i < elementsWithOccurrences.length; i++) {
            ObjectIntPair<T> itemToAdd = elementsWithOccurrences[i];
            bag.addOccurrences(itemToAdd.getOne(), itemToAdd.getTwo());
        }
        return bag;
    }

    @Override
    @Test
    public void addAll_1_testMerged_1() {
        MutableBag<Integer> bag1 = this.newWith();
        assertTrue(bag1.addAll(this.newWith(1, 1, 2, 3)));
        Verify.assertContainsAll(bag1, 1, 2, 3);
        assertTrue(bag1.addAll(this.newWith(1, 2, 3)));
        Verify.assertSize(7, bag1);
        assertFalse(bag1.addAll(this.newWith()));
    }

    @Override
    @Test
    public void addAll_7() {
        MutableBag<Integer> bag2 = this.newWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        bag2.addAll(this.newWith(5, 5, 5, 5, 5));
        Verify.assertBagsEqual(this.newWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5), bag2);
    }

    @Override
    @Test
    public void addAll_8() {
        MutableBag<Integer> bag3 = this.newWith(1, 2, 2, 3, 3, 3);
        bag3.addAll(this.newWith(1));
        Verify.assertBagsEqual(this.newWith(1, 1, 2, 2, 3, 3, 3), bag3);
    }
}
