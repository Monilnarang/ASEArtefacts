package org.eclipse.collections.impl.list.mutable.primitive;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.tuple.primitive.BooleanIntPair;
import org.eclipse.collections.impl.collection.mutable.primitive.AbstractMutableBooleanCollectionTestCase;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.math.MutableInteger;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractBooleanListTestCase_Purified extends AbstractMutableBooleanCollectionTestCase {

    @Override
    protected abstract MutableBooleanList classUnderTest();

    @Override
    protected abstract MutableBooleanList newWith(boolean... elements);

    @Override
    protected MutableBooleanList newMutableCollectionWith(boolean... elements) {
        return BooleanArrayList.newListWith(elements);
    }

    @Override
    protected MutableList<Object> newObjectCollectionWith(Object... elements) {
        return FastList.newListWith(elements);
    }

    @Test
    public void distinct_1() {
        assertEquals(BooleanArrayList.newListWith(true, false), this.newWith(true, true, false, false).distinct());
    }

    @Test
    public void distinct_2() {
        assertEquals(BooleanArrayList.newListWith(false, true), this.newWith(false, false, true, true).distinct());
    }

    @Test
    public void distinct_3() {
        assertEquals(BooleanArrayList.newListWith(false), this.newWith(false).distinct());
    }

    @Test
    public void distinct_4() {
        assertEquals(BooleanArrayList.newListWith(true), this.newWith(true).distinct());
    }

    @Override
    @Test
    public void testToString_1() {
        assertEquals("[true, false, true]", this.classUnderTest().toString());
    }

    @Override
    @Test
    public void testToString_2() {
        assertEquals("[]", this.newWith().toString());
    }

    @Override
    @Test
    public void makeString_1() {
        assertEquals("true, false, true", this.classUnderTest().makeString());
    }

    @Override
    @Test
    public void makeString_2() {
        assertEquals("true", this.newWith(true).makeString("/"));
    }

    @Override
    @Test
    public void makeString_3() {
        assertEquals("true/false/true", this.classUnderTest().makeString("/"));
    }

    @Override
    @Test
    public void makeString_4() {
        assertEquals(this.classUnderTest().toString(), this.classUnderTest().makeString("[", ", ", "]"));
    }

    @Override
    @Test
    public void makeString_5() {
        assertEquals("", this.newWith().makeString());
    }

    @Test
    public void newWithNValues_1() {
        assertEquals(this.newWith(true, true, true), BooleanArrayList.newWithNValues(3, true));
    }

    @Test
    public void newWithNValues_2() {
        assertEquals(this.newWith(false, false), BooleanArrayList.newWithNValues(2, false));
    }

    @Test
    public void newWithNValues_3() {
        assertEquals(this.newWith(), BooleanArrayList.newWithNValues(0, false));
    }

    @Test
    public void newWithNValues_4() {
        assertEquals(this.newWith(), BooleanArrayList.newWithNValues(0, true));
    }

    @Override
    @Test
    public void appendString_1() {
        StringBuilder appendable = new StringBuilder();
        this.newWith().appendString(appendable);
        assertEquals("", appendable.toString());
    }

    @Override
    @Test
    public void appendString_2() {
        StringBuilder appendable2 = new StringBuilder();
        this.classUnderTest().appendString(appendable2);
        assertEquals("true, false, true", appendable2.toString());
    }

    @Override
    @Test
    public void appendString_3() {
        StringBuilder appendable3 = new StringBuilder();
        this.classUnderTest().appendString(appendable3, "/");
        assertEquals("true/false/true", appendable3.toString());
    }

    @Override
    @Test
    public void appendString_4() {
        StringBuilder appendable4 = new StringBuilder();
        this.classUnderTest().appendString(appendable4, "[", ", ", "]");
        assertEquals(this.classUnderTest().toString(), appendable4.toString());
    }
}
