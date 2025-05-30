package org.eclipse.collections.impl.bag.mutable.primitive;

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

public class MutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyMutable_1() {
        Verify.assertEmpty(BooleanBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_2() {
        Verify.assertEmpty(BooleanBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_3() {
        Verify.assertEmpty(BooleanBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_4() {
        Verify.assertEmpty(ByteBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_5() {
        Verify.assertEmpty(ByteBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_6() {
        Verify.assertEmpty(ByteBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_7() {
        Verify.assertEmpty(CharBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_8() {
        Verify.assertEmpty(CharBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_9() {
        Verify.assertEmpty(CharBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_10() {
        Verify.assertEmpty(DoubleBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_11() {
        Verify.assertEmpty(DoubleBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_12() {
        Verify.assertEmpty(DoubleBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_13() {
        Verify.assertEmpty(FloatBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_14() {
        Verify.assertEmpty(FloatBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_15() {
        Verify.assertEmpty(FloatBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_16() {
        Verify.assertEmpty(IntBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_17() {
        Verify.assertEmpty(IntBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_18() {
        Verify.assertEmpty(IntBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_19() {
        Verify.assertEmpty(LongBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_20() {
        Verify.assertEmpty(LongBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_21() {
        Verify.assertEmpty(LongBags.mutable.with());
    }

    @Test
    public void isEmptyMutable_22() {
        Verify.assertEmpty(ShortBags.mutable.empty());
    }

    @Test
    public void isEmptyMutable_23() {
        Verify.assertEmpty(ShortBags.mutable.of());
    }

    @Test
    public void isEmptyMutable_24() {
        Verify.assertEmpty(ShortBags.mutable.with());
    }
}
