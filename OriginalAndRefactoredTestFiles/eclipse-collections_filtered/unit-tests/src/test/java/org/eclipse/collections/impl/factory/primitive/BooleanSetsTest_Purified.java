package org.eclipse.collections.impl.factory.primitive;

import java.util.Set;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.set.primitive.ImmutableBooleanSetFactory;
import org.eclipse.collections.api.factory.set.primitive.MutableBooleanSetFactory;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.api.tuple.primitive.BooleanBooleanPair;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BooleanSetsTest_Purified {

    private void assertImmutableSetFactory(ImmutableBooleanSetFactory setFactory) {
        assertEquals(new BooleanHashSet(), setFactory.with());
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with());
        assertEquals(BooleanHashSet.newSetWith(true), setFactory.with(true));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true));
        assertEquals(BooleanHashSet.newSetWith(true, false), setFactory.with(true, false));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true), setFactory.with(true, false, true));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false), setFactory.with(true, false, true, false));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true), setFactory.with(true, false, true, false, true));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false), setFactory.with(true, false, true, false, true, false));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false, true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true), setFactory.with(true, false, true, false, true, false, true));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true, true), setFactory.with(true, false, true, false, true, false, true, true));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true, true, true), setFactory.with(true, false, true, false, true, false, true, true, true));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true, true, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true, true, true, false), setFactory.with(true, false, true, false, true, false, true, true, true, false));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true, true, true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true), setFactory.withAll(BooleanHashSet.newSetWith(true, false, true)));
        Verify.assertInstanceOf(ImmutableBooleanSet.class, setFactory.withAll(BooleanHashSet.newSetWith(true, false, true)));
    }

    private void assertMutableSetFactory(MutableBooleanSetFactory setFactory) {
        assertEquals(new BooleanHashSet(), setFactory.with());
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with());
        assertEquals(BooleanHashSet.newSetWith(true), setFactory.with(true));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true));
        assertEquals(BooleanHashSet.newSetWith(true, false), setFactory.with(true, false));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true), setFactory.with(true, false, true));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false), setFactory.with(true, false, true, false));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true), setFactory.with(true, false, true, false, true));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false), setFactory.with(true, false, true, false, true, false));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false, true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true), setFactory.with(true, false, true, false, true, false, true));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true, true), setFactory.with(true, false, true, false, true, false, true, true));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true, true, true), setFactory.with(true, false, true, false, true, false, true, true, true));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true, true, true));
        assertEquals(BooleanHashSet.newSetWith(true, false, true, false, true, false, true, true, true, false), setFactory.with(true, false, true, false, true, false, true, true, true, false));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.with(true, false, true, false, true, false, true, true, true, false));
        assertEquals(BooleanHashSet.newSetWith(true, false, true), setFactory.withAll(BooleanHashSet.newSetWith(true, false, true)));
        Verify.assertInstanceOf(MutableBooleanSet.class, setFactory.withAll(BooleanHashSet.newSetWith(true, false, true)));
    }

    @Test
    public void immutables_1() {
        this.assertImmutableSetFactory(BooleanSets.immutable);
    }

    @Test
    public void immutables_2() {
        this.assertImmutableSetFactory(org.eclipse.collections.api.factory.primitive.BooleanSets.immutable);
    }

    @Test
    public void mutables_1() {
        this.assertMutableSetFactory(BooleanSets.mutable);
    }

    @Test
    public void mutables_2() {
        this.assertMutableSetFactory(org.eclipse.collections.api.factory.primitive.BooleanSets.mutable);
    }

    @Test
    public void emptySet_1() {
        Verify.assertEmpty(BooleanSets.immutable.with());
    }

    @Test
    public void emptySet_2() {
        assertSame(BooleanSets.immutable.with(), BooleanSets.immutable.with());
    }

    @Test
    public void emptySet_3() {
        Verify.assertPostSerializedIdentity(BooleanSets.immutable.with());
    }

    @Test
    public void newSetWithWithSet_1() {
        assertEquals(new BooleanHashSet(), BooleanSets.immutable.withAll(new BooleanHashSet()));
    }

    @Test
    public void newSetWithWithSet_2() {
        assertEquals(BooleanHashSet.newSetWith(true), BooleanSets.immutable.withAll(BooleanHashSet.newSetWith(true)));
    }

    @Test
    public void newSetWithWithSet_3() {
        assertEquals(BooleanHashSet.newSetWith(true, false), BooleanSets.immutable.withAll(BooleanHashSet.newSetWith(true, false)));
    }

    @Test
    public void newSetWithWithSet_4() {
        assertEquals(BooleanHashSet.newSetWith(true, false, true), BooleanSets.immutable.withAll(BooleanHashSet.newSetWith(true, false, true)));
    }

    @Test
    public void ofAllBooleanIterable_1() {
        assertEquals(new BooleanHashSet(), BooleanSets.immutable.ofAll(BooleanLists.mutable.empty()));
    }

    @Test
    public void ofAllBooleanIterable_2() {
        assertEquals(BooleanHashSet.newSetWith(true), BooleanSets.immutable.ofAll(BooleanLists.mutable.with(true)));
    }

    @Test
    public void ofAllBooleanIterable_3() {
        assertEquals(BooleanHashSet.newSetWith(true, false), BooleanSets.immutable.ofAll(BooleanLists.mutable.with(true, false)));
    }

    @Test
    public void ofAllBooleanIterable_4() {
        assertEquals(BooleanHashSet.newSetWith(true, false, true), BooleanSets.immutable.ofAll(BooleanLists.mutable.with(true, false, true)));
    }

    @Test
    public void ofAllBooleanIterable_5() {
        assertEquals(new BooleanHashSet(), BooleanSets.mutable.ofAll(BooleanLists.mutable.empty()));
    }

    @Test
    public void ofAllBooleanIterable_6() {
        assertEquals(BooleanHashSet.newSetWith(true), BooleanSets.mutable.ofAll(BooleanLists.mutable.with(true)));
    }

    @Test
    public void ofAllBooleanIterable_7() {
        assertEquals(BooleanHashSet.newSetWith(true, false), BooleanSets.mutable.ofAll(BooleanLists.mutable.with(true, false)));
    }

    @Test
    public void ofAllBooleanIterable_8() {
        assertEquals(BooleanHashSet.newSetWith(true, false, true), BooleanSets.mutable.ofAll(BooleanLists.mutable.with(true, false, true)));
    }

    @Test
    public void ofAllIterable_1() {
        assertEquals(new BooleanHashSet(), BooleanSets.immutable.ofAll(Lists.mutable.empty()));
    }

    @Test
    public void ofAllIterable_2() {
        assertEquals(BooleanHashSet.newSetWith(true), BooleanSets.immutable.ofAll(Lists.mutable.with(true)));
    }

    @Test
    public void ofAllIterable_3() {
        assertEquals(BooleanHashSet.newSetWith(true, false), BooleanSets.immutable.ofAll(Lists.mutable.with(true, false)));
    }

    @Test
    public void ofAllIterable_4() {
        assertEquals(BooleanHashSet.newSetWith(true, false, true), BooleanSets.immutable.ofAll(Lists.mutable.with(true, false, true)));
    }

    @Test
    public void ofAllIterable_5() {
        assertEquals(new BooleanHashSet(), BooleanSets.mutable.ofAll(Lists.mutable.empty()));
    }

    @Test
    public void ofAllIterable_6() {
        assertEquals(BooleanHashSet.newSetWith(true), BooleanSets.mutable.ofAll(Lists.mutable.with(true)));
    }

    @Test
    public void ofAllIterable_7() {
        assertEquals(BooleanHashSet.newSetWith(true, false), BooleanSets.mutable.ofAll(Lists.mutable.with(true, false)));
    }

    @Test
    public void ofAllIterable_8() {
        assertEquals(BooleanHashSet.newSetWith(true, false, true), BooleanSets.mutable.ofAll(Lists.mutable.with(true, false, true)));
    }
}
