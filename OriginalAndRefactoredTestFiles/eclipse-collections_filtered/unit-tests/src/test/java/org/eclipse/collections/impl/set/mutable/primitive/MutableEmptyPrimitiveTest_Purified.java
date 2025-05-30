package org.eclipse.collections.impl.set.mutable.primitive;

import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.factory.primitive.ByteSets;
import org.eclipse.collections.impl.factory.primitive.CharSets;
import org.eclipse.collections.impl.factory.primitive.DoubleSets;
import org.eclipse.collections.impl.factory.primitive.FloatSets;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.eclipse.collections.impl.factory.primitive.LongSets;
import org.eclipse.collections.impl.factory.primitive.ShortSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class MutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyMutable_1() {
        Verify.assertEmpty(BooleanSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_2() {
        Verify.assertEmpty(BooleanSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_3() {
        Verify.assertEmpty(BooleanSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_4() {
        Verify.assertEmpty(ByteSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_5() {
        Verify.assertEmpty(ByteSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_6() {
        Verify.assertEmpty(ByteSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_7() {
        Verify.assertEmpty(CharSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_8() {
        Verify.assertEmpty(CharSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_9() {
        Verify.assertEmpty(CharSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_10() {
        Verify.assertEmpty(DoubleSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_11() {
        Verify.assertEmpty(DoubleSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_12() {
        Verify.assertEmpty(DoubleSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_13() {
        Verify.assertEmpty(FloatSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_14() {
        Verify.assertEmpty(FloatSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_15() {
        Verify.assertEmpty(FloatSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_16() {
        Verify.assertEmpty(IntSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_17() {
        Verify.assertEmpty(IntSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_18() {
        Verify.assertEmpty(IntSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_19() {
        Verify.assertEmpty(LongSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_20() {
        Verify.assertEmpty(LongSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_21() {
        Verify.assertEmpty(LongSets.mutable.with());
    }

    @Test
    public void isEmptyMutable_22() {
        Verify.assertEmpty(ShortSets.mutable.empty());
    }

    @Test
    public void isEmptyMutable_23() {
        Verify.assertEmpty(ShortSets.mutable.of());
    }

    @Test
    public void isEmptyMutable_24() {
        Verify.assertEmpty(ShortSets.mutable.with());
    }
}
