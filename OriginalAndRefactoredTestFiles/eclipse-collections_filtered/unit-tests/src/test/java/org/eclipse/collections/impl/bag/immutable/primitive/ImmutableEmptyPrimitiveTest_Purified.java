package org.eclipse.collections.impl.bag.immutable.primitive;

import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.ByteBags;
import org.eclipse.collections.impl.factory.primitive.CharBags;
import org.eclipse.collections.impl.factory.primitive.DoubleBags;
import org.eclipse.collections.impl.factory.primitive.FloatBags;
import org.eclipse.collections.impl.factory.primitive.IntBags;
import org.eclipse.collections.impl.factory.primitive.LongBags;
import org.eclipse.collections.impl.factory.primitive.ShortBags;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class ImmutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyImmutable_1() {
        Verify.assertEmpty(BooleanBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_2() {
        Verify.assertEmpty(BooleanBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_3() {
        Verify.assertEmpty(BooleanBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_4() {
        Verify.assertEmpty(ByteBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_5() {
        Verify.assertEmpty(ByteBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_6() {
        Verify.assertEmpty(ByteBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_7() {
        Verify.assertEmpty(CharBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_8() {
        Verify.assertEmpty(CharBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_9() {
        Verify.assertEmpty(CharBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_10() {
        Verify.assertEmpty(DoubleBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_11() {
        Verify.assertEmpty(DoubleBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_12() {
        Verify.assertEmpty(DoubleBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_13() {
        Verify.assertEmpty(FloatBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_14() {
        Verify.assertEmpty(FloatBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_15() {
        Verify.assertEmpty(FloatBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_16() {
        Verify.assertEmpty(IntBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_17() {
        Verify.assertEmpty(IntBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_18() {
        Verify.assertEmpty(IntBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_19() {
        Verify.assertEmpty(LongBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_20() {
        Verify.assertEmpty(LongBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_21() {
        Verify.assertEmpty(LongBags.immutable.with());
    }

    @Test
    public void isEmptyImmutable_22() {
        Verify.assertEmpty(ShortBags.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_23() {
        Verify.assertEmpty(ShortBags.immutable.of());
    }

    @Test
    public void isEmptyImmutable_24() {
        Verify.assertEmpty(ShortBags.immutable.with());
    }
}
