package org.eclipse.collections.impl.set.immutable.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.collection.primitive.ImmutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.collection.immutable.primitive.AbstractImmutableBooleanCollectionTestCase;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableBooleanEmptySetTest_Purified extends AbstractImmutableBooleanCollectionTestCase {

    @Override
    protected ImmutableBooleanCollection newWith(boolean... elements) {
        return BooleanSets.immutable.with(elements);
    }

    @Override
    protected MutableBooleanCollection newMutableCollectionWith(boolean... elements) {
        return BooleanSets.mutable.with(elements);
    }

    @Override
    protected RichIterable<Object> newObjectCollectionWith(Object... elements) {
        return Sets.immutable.with(elements);
    }

    @Override
    protected final ImmutableBooleanSet classUnderTest() {
        return BooleanSets.immutable.empty();
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

    @Override
    @Test
    public void count_1() {
        assertEquals(0, this.classUnderTest().count(BooleanPredicates.alwaysTrue()));
    }

    @Override
    @Test
    public void count_2() {
        assertEquals(0, this.classUnderTest().count(BooleanPredicates.alwaysFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_1() {
        assertTrue(this.classUnderTest().noneSatisfy(BooleanPredicates.alwaysTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_2() {
        assertTrue(this.classUnderTest().noneSatisfy(BooleanPredicates.alwaysFalse()));
    }

    @Test
    public void cartesianProduct_1() {
        assertEquals(Sets.immutable.empty(), this.classUnderTest().cartesianProduct(BooleanSets.immutable.with(true)).toSet());
    }

    @Test
    public void cartesianProduct_2() {
        assertEquals(Sets.immutable.empty(), this.classUnderTest().cartesianProduct(BooleanSets.immutable.with(false)).toSet());
    }

    @Test
    public void cartesianProduct_3() {
        assertEquals(Sets.immutable.empty(), this.classUnderTest().cartesianProduct(BooleanSets.immutable.with(true, false)).toSet());
    }

    @Test
    public void cartesianProduct_4() {
        assertEquals(Sets.immutable.empty(), this.classUnderTest().cartesianProduct(BooleanSets.immutable.empty()).toSet());
    }
}
