package org.eclipse.collections.impl.bag.immutable.primitive;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.block.function.primitive.BooleanToObjectFunction;
import org.eclipse.collections.api.collection.primitive.ImmutableBooleanCollection;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.collection.immutable.primitive.AbstractImmutableBooleanCollectionTestCase;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableBooleanBagTestCase_Purified extends AbstractImmutableBooleanCollectionTestCase {

    @Override
    protected abstract ImmutableBooleanBag classUnderTest();

    @Override
    protected ImmutableBooleanBag newWith(boolean... elements) {
        return BooleanBags.immutable.with(elements);
    }

    @Override
    protected MutableBooleanBag newMutableCollectionWith(boolean... elements) {
        return BooleanHashBag.newBagWith(elements);
    }

    @Override
    protected MutableBag<Object> newObjectCollectionWith(Object... elements) {
        return HashBag.newBagWith(elements);
    }

    @Test
    public void sizeDistinct_1() {
        assertEquals(0L, this.newWith().sizeDistinct());
    }

    @Test
    public void sizeDistinct_2() {
        assertEquals(1L, this.newWith(true).sizeDistinct());
    }

    @Test
    public void sizeDistinct_3() {
        assertEquals(1L, this.newWith(true, true, true).sizeDistinct());
    }

    @Test
    public void sizeDistinct_4() {
        assertEquals(2L, this.newWith(true, false, true, false, true).sizeDistinct());
    }

    @Override
    @Test
    public void testEquals_1() {
        ImmutableBooleanCollection collection1 = this.newWith(true, false, true, false);
        ImmutableBooleanCollection collection2 = this.newWith(true, false, false, true);
        assertEquals(collection1, collection2);
    }

    @Override
    @Test
    public void testEquals_2() {
        Verify.assertPostSerializedIdentity(this.newWith());
    }

    @Override
    @Test
    public void testEquals_3_testMerged_3() {
        ImmutableBooleanCollection collection3 = this.newWith(true, false);
        ImmutableBooleanCollection collection4 = this.newWith(true, true, false);
        assertNotEquals(collection3, collection4);
        assertNotEquals(collection3, BooleanArrayList.newListWith(true, false));
    }

    @Override
    @Test
    public void testEquals_5() {
        assertNotEquals(this.newWith(true), BooleanArrayList.newListWith(true));
    }

    @Override
    @Test
    public void testEquals_6() {
        assertNotEquals(this.newWith(), BooleanArrayList.newListWith());
    }

    @Override
    @Test
    public void testHashCode_1() {
        ImmutableBooleanCollection collection1 = this.newWith(true, false, true, false);
        ImmutableBooleanCollection collection2 = this.newWith(true, false, false, true);
        Verify.assertEqualsAndHashCode(collection1, collection2);
    }

    @Override
    @Test
    public void testHashCode_2() {
        ImmutableBooleanCollection collection3 = this.newWith(true, false);
        ImmutableBooleanCollection collection4 = this.newWith(true, true, false);
        assertNotEquals(collection3.hashCode(), collection4.hashCode());
    }

    @Override
    @Test
    public void appendString_1() {
        StringBuilder appendable1 = new StringBuilder();
        this.newWith(true, true, true).appendString(appendable1);
        assertEquals("true, true, true", appendable1.toString());
    }

    @Override
    @Test
    public void appendString_2() {
        StringBuilder appendable2 = new StringBuilder();
        bag1.appendString(appendable2);
        assertTrue("false, false, true".equals(appendable2.toString()) || "true, false, false".equals(appendable2.toString()) || "false, true, false".equals(appendable2.toString()), appendable2.toString());
    }

    @Test
    public void toImmutable_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().toImmutable());
    }

    @Test
    public void toImmutable_2() {
        ImmutableBooleanBag expected = this.classUnderTest();
        assertSame(expected, expected.toImmutable());
    }
}
