package org.eclipse.collections.impl.factory.primitive;

import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.factory.bag.primitive.ImmutableBooleanBagFactory;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BooleanBagsTest_Purified {

    @Test
    public void emptyBag_1() {
        Verify.assertEmpty(BooleanBags.immutable.of());
    }

    @Test
    public void emptyBag_2() {
        assertSame(BooleanBags.immutable.of(), BooleanBags.immutable.of());
    }

    @Test
    public void emptyBag_3() {
        Verify.assertPostSerializedIdentity(BooleanBags.immutable.of());
    }

    @Test
    public void newBagWithWithBag_1() {
        assertEquals(new BooleanHashBag(), BooleanBags.immutable.ofAll(new BooleanHashBag()));
    }

    @Test
    public void newBagWithWithBag_2() {
        assertEquals(BooleanHashBag.newBagWith(true), BooleanBags.immutable.ofAll(BooleanHashBag.newBagWith(true)));
    }

    @Test
    public void newBagWithWithBag_3() {
        assertEquals(BooleanHashBag.newBagWith(true, false), BooleanBags.immutable.ofAll(BooleanHashBag.newBagWith(true, false)));
    }

    @Test
    public void newBagWithWithBag_4() {
        assertEquals(BooleanHashBag.newBagWith(true, false, true), BooleanBags.immutable.ofAll(BooleanHashBag.newBagWith(true, false, true)));
    }
}
