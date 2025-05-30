package org.eclipse.collections.impl.bag.mutable.primitive;

import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.iterator.MutableBooleanIterator;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableBooleanBagTest_Purified extends AbstractMutableBooleanBagTestCase {

    private final MutableBooleanBag bag = this.classUnderTest();

    @Override
    protected final UnmodifiableBooleanBag classUnderTest() {
        return new UnmodifiableBooleanBag(BooleanHashBag.newBagWith(true, false, true));
    }

    @Override
    protected UnmodifiableBooleanBag newWith(boolean... elements) {
        return new UnmodifiableBooleanBag(BooleanHashBag.newBagWith(elements));
    }

    @Override
    public void selectUnique() {
        super.selectUnique();
        MutableBooleanBag bag = this.classUnderTest();
        MutableBooleanSet expected = BooleanSets.mutable.with(false);
        MutableBooleanSet actual = bag.selectUnique();
        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void containsAllArray_1_testMerged_1() {
        UnmodifiableBooleanBag collection = this.classUnderTest();
        assertTrue(collection.containsAll(true));
        assertTrue(collection.containsAll(true, false, true));
        assertTrue(collection.containsAll(true, false));
        assertTrue(collection.containsAll(true, true));
        assertTrue(collection.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_6_testMerged_2() {
        UnmodifiableBooleanBag emptyCollection = this.newWith();
        assertFalse(emptyCollection.containsAll(true));
        assertFalse(emptyCollection.containsAll(false));
        assertFalse(emptyCollection.containsAll(false, true, false));
    }

    @Override
    @Test
    public void containsAllArray_9() {
        assertFalse(this.newWith(true, true).containsAll(false, true, false));
    }

    @Override
    @Test
    public void containsAllArray_10() {
        UnmodifiableBooleanBag trueCollection = this.newWith(true, true, true, true);
        assertFalse(trueCollection.containsAll(true, false));
    }

    @Override
    @Test
    public void containsAllArray_11() {
        UnmodifiableBooleanBag falseCollection = this.newWith(false, false, false, false);
        assertFalse(falseCollection.containsAll(true, false));
    }

    @Override
    @Test
    public void containsAllIterable_1_testMerged_1() {
        UnmodifiableBooleanBag emptyCollection = this.newWith();
        assertTrue(emptyCollection.containsAll(new BooleanArrayList()));
        assertFalse(emptyCollection.containsAll(BooleanArrayList.newListWith(true)));
        assertFalse(emptyCollection.containsAll(BooleanArrayList.newListWith(false)));
    }

    @Override
    @Test
    public void containsAllIterable_4_testMerged_2() {
        UnmodifiableBooleanBag collection = this.newWith(true, true, false, false, false);
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(true)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(false)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(true, false)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(true, true)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(false, false)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(true, false, true)));
    }

    @Override
    @Test
    public void containsAllIterable_10() {
        assertFalse(this.newWith(true, true).containsAll(BooleanArrayList.newListWith(false, true, false)));
    }

    @Override
    @Test
    public void containsAllIterable_11() {
        UnmodifiableBooleanBag trueCollection = this.newWith(true, true, true, true);
        assertFalse(trueCollection.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void containsAllIterable_12() {
        UnmodifiableBooleanBag falseCollection = this.newWith(false, false, false, false);
        assertFalse(falseCollection.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void asUnmodifiable_1() {
        assertSame(this.bag, this.bag.asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_2() {
        assertEquals(this.bag, this.bag.asUnmodifiable());
    }
}
