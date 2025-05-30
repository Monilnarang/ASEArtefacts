package org.eclipse.collections.impl.stack.mutable.primitive;

import org.eclipse.collections.impl.factory.primitive.BooleanStacks;
import org.eclipse.collections.impl.factory.primitive.ByteStacks;
import org.eclipse.collections.impl.factory.primitive.CharStacks;
import org.eclipse.collections.impl.factory.primitive.DoubleStacks;
import org.eclipse.collections.impl.factory.primitive.FloatStacks;
import org.eclipse.collections.impl.factory.primitive.IntStacks;
import org.eclipse.collections.impl.factory.primitive.LongStacks;
import org.eclipse.collections.impl.factory.primitive.ShortStacks;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class MutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyMutable_1() {
        Verify.assertEmpty(BooleanStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_2() {
        Verify.assertEmpty(BooleanStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_3() {
        Verify.assertEmpty(BooleanStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_4() {
        Verify.assertEmpty(ByteStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_5() {
        Verify.assertEmpty(ByteStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_6() {
        Verify.assertEmpty(ByteStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_7() {
        Verify.assertEmpty(CharStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_8() {
        Verify.assertEmpty(CharStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_9() {
        Verify.assertEmpty(CharStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_10() {
        Verify.assertEmpty(DoubleStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_11() {
        Verify.assertEmpty(DoubleStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_12() {
        Verify.assertEmpty(DoubleStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_13() {
        Verify.assertEmpty(FloatStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_14() {
        Verify.assertEmpty(FloatStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_15() {
        Verify.assertEmpty(FloatStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_16() {
        Verify.assertEmpty(IntStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_17() {
        Verify.assertEmpty(IntStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_18() {
        Verify.assertEmpty(IntStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_19() {
        Verify.assertEmpty(LongStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_20() {
        Verify.assertEmpty(LongStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_21() {
        Verify.assertEmpty(LongStacks.mutable.with());
    }

    @Test
    public void isEmptyMutable_22() {
        Verify.assertEmpty(ShortStacks.mutable.empty());
    }

    @Test
    public void isEmptyMutable_23() {
        Verify.assertEmpty(ShortStacks.mutable.of());
    }

    @Test
    public void isEmptyMutable_24() {
        Verify.assertEmpty(ShortStacks.mutable.with());
    }
}
