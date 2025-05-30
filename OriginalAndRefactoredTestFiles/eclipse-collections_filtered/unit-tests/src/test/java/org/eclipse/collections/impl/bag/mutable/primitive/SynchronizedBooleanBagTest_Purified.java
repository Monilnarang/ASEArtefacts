package org.eclipse.collections.impl.bag.mutable.primitive;

import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SynchronizedBooleanBagTest_Purified extends AbstractMutableBooleanBagTestCase {

    @Override
    protected final SynchronizedBooleanBag classUnderTest() {
        return new SynchronizedBooleanBag(BooleanHashBag.newBagWith(true, false, true));
    }

    @Override
    protected SynchronizedBooleanBag newWith(boolean... elements) {
        return new SynchronizedBooleanBag(BooleanHashBag.newBagWith(elements));
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
    public void asSynchronized_1_testMerged_1() {
        MutableBooleanBag bagWithLockObject = new SynchronizedBooleanBag(BooleanHashBag.newBagWith(true, false, true), new Object());
        assertSame(bagWithLockObject, bagWithLockObject.asSynchronized());
        assertEquals(bagWithLockObject, bagWithLockObject.asSynchronized());
    }

    @Override
    @Test
    public void asSynchronized_3_testMerged_2() {
        MutableBooleanBag bag = this.classUnderTest();
        assertSame(bag, bag.asSynchronized());
        assertEquals(bag, bag.asSynchronized());
    }
}
