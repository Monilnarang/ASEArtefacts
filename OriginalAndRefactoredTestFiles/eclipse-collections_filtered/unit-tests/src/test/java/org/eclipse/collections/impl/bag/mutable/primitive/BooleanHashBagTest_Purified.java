package org.eclipse.collections.impl.bag.mutable.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanHashBagTest_Purified extends AbstractMutableBooleanBagTestCase {

    @Override
    protected BooleanHashBag classUnderTest() {
        return BooleanHashBag.newBagWith(true, false, true);
    }

    @Override
    protected BooleanHashBag newWith(boolean... elements) {
        return BooleanHashBag.newBagWith(elements);
    }

    @Override
    @Test
    public void size_1() {
        Verify.assertSize(3, BooleanHashBag.newBagWith(true, false, true));
    }

    @Override
    @Test
    public void size_2() {
        Verify.assertSize(3, new BooleanHashBag(BooleanHashBag.newBagWith(true, false, true)));
    }

    @Override
    @Test
    public void size_3() {
        Verify.assertSize(3, new BooleanHashBag(BooleanArrayList.newListWith(true, false, true)));
    }

    @Override
    @Test
    public void with_1_testMerged_1() {
        BooleanHashBag emptyBag = new BooleanHashBag();
        BooleanHashBag hashBag0 = emptyBag.with(true, false);
        assertSame(emptyBag, hashBag0);
        assertEquals(BooleanHashBag.newBagWith(true, false), hashBag0);
    }

    @Override
    @Test
    public void with_2() {
        BooleanHashBag hashBag = new BooleanHashBag().with(true);
        assertEquals(BooleanHashBag.newBagWith(true), hashBag);
    }

    @Override
    @Test
    public void with_4() {
        BooleanHashBag hashBag1 = new BooleanHashBag().with(true, false, true);
        assertEquals(BooleanHashBag.newBagWith(true, false, true), hashBag1);
    }

    @Override
    @Test
    public void with_5() {
        BooleanHashBag hashBag2 = new BooleanHashBag().with(true).with(false).with(true).with(false);
        assertEquals(BooleanHashBag.newBagWith(true, false, true, false), hashBag2);
    }

    @Override
    @Test
    public void with_6() {
        BooleanHashBag hashBag3 = new BooleanHashBag().with(true).with(false).with(true).with(false).with(true);
        assertEquals(BooleanHashBag.newBagWith(true, false, true, false, true), hashBag3);
    }
}
