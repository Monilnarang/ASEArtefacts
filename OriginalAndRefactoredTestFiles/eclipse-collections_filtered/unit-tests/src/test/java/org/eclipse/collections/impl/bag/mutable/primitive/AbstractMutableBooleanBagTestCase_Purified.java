package org.eclipse.collections.impl.bag.mutable.primitive;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.block.function.primitive.BooleanToObjectFunction;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.collection.mutable.primitive.AbstractMutableBooleanCollectionTestCase;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMutableBooleanBagTestCase_Purified extends AbstractMutableBooleanCollectionTestCase {

    @Override
    protected abstract MutableBooleanBag classUnderTest();

    @Override
    protected abstract MutableBooleanBag newWith(boolean... elements);

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

    @Test
    public void removeOccurrences_1_testMerged_1() {
        MutableBooleanBag bag1 = this.newWith();
        assertFalse(bag1.removeOccurrences(true, 5));
        bag1.addOccurrences(true, 5);
        assertTrue(bag1.removeOccurrences(true, 2));
        assertEquals(BooleanHashBag.newBagWith(true, true, true), bag1);
        assertFalse(bag1.removeOccurrences(true, 0));
        assertTrue(bag1.removeOccurrences(true, 5));
        assertEquals(new BooleanHashBag(), bag1);
    }

    @Test
    public void removeOccurrences_10_testMerged_2() {
        MutableBooleanBag bag2 = this.newWith();
        assertFalse(bag2.removeOccurrences(false, 5));
        bag2.addOccurrences(false, 5);
        assertTrue(bag2.removeOccurrences(false, 2));
        assertEquals(BooleanHashBag.newBagWith(false, false, false), bag2);
        assertFalse(bag2.removeOccurrences(false, 0));
        assertTrue(bag2.removeOccurrences(false, 5));
        assertEquals(new BooleanHashBag(), bag2);
    }

    @Override
    @Test
    public void testEquals_1() {
        MutableBooleanCollection collection1 = this.newWith(true, false, true, false);
        MutableBooleanCollection collection2 = this.newWith(true, false, false, true);
        assertEquals(collection1, collection2);
    }

    @Override
    @Test
    public void testEquals_2() {
        MutableBooleanCollection collection3 = this.newWith(true, false);
        MutableBooleanCollection collection4 = this.newWith(true, true, false);
        assertNotEquals(collection3, collection4);
    }

    @Override
    @Test
    public void testHashCode_1() {
        MutableBooleanCollection collection1 = this.newWith(true, false, true, false);
        MutableBooleanCollection collection2 = this.newWith(true, false, false, true);
        Verify.assertEqualsAndHashCode(collection1, collection2);
    }

    @Override
    @Test
    public void testHashCode_2() {
        MutableBooleanCollection collection3 = this.newWith(true, false);
        MutableBooleanCollection collection4 = this.newWith(true, true, false);
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
        assertNotSame(this.classUnderTest(), this.classUnderTest().toImmutable());
    }

    @Test
    public void toImmutable_3() {
        Verify.assertInstanceOf(ImmutableBooleanBag.class, this.classUnderTest().toImmutable());
    }
}
