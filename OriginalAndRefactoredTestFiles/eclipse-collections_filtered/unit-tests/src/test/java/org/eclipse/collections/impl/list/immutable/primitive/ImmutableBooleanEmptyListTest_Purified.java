package org.eclipse.collections.impl.list.immutable.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.list.primitive.ImmutableBooleanList;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmutableBooleanEmptyListTest_Purified extends AbstractImmutableBooleanListTestCase {

    @Override
    protected ImmutableBooleanList classUnderTest() {
        return ImmutableBooleanEmptyList.INSTANCE;
    }

    @Override
    @Test
    public void indexOf_1() {
        assertEquals(-1L, this.classUnderTest().indexOf(true));
    }

    @Override
    @Test
    public void indexOf_2() {
        assertEquals(-1L, this.classUnderTest().indexOf(false));
    }

    @Override
    @Test
    public void lastIndexOf_1() {
        assertEquals(-1L, this.classUnderTest().lastIndexOf(true));
    }

    @Override
    @Test
    public void lastIndexOf_2() {
        assertEquals(-1L, this.classUnderTest().lastIndexOf(false));
    }

    @Override
    @Test
    public void testEquals_1() {
        Verify.assertEqualsAndHashCode(this.newMutableCollectionWith(), this.classUnderTest());
    }

    @Override
    @Test
    public void testEquals_2() {
        Verify.assertPostSerializedIdentity(this.newWith());
    }

    @Override
    @Test
    public void testEquals_3() {
        assertNotEquals(this.classUnderTest(), this.newWith(false, false, false, true));
    }

    @Override
    @Test
    public void testEquals_4() {
        assertNotEquals(this.classUnderTest(), this.newWith(true));
    }
}
