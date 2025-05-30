package org.eclipse.collections.impl.set.immutable.primitive;

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

public class ImmutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyImmutable_1() {
        Verify.assertEmpty(BooleanSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_2() {
        Verify.assertEmpty(BooleanSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_3() {
        Verify.assertEmpty(BooleanSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_4() {
        Verify.assertEmpty(ByteSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_5() {
        Verify.assertEmpty(ByteSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_6() {
        Verify.assertEmpty(ByteSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_7() {
        Verify.assertEmpty(CharSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_8() {
        Verify.assertEmpty(CharSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_9() {
        Verify.assertEmpty(CharSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_10() {
        Verify.assertEmpty(DoubleSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_11() {
        Verify.assertEmpty(DoubleSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_12() {
        Verify.assertEmpty(DoubleSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_13() {
        Verify.assertEmpty(FloatSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_14() {
        Verify.assertEmpty(FloatSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_15() {
        Verify.assertEmpty(FloatSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_16() {
        Verify.assertEmpty(IntSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_17() {
        Verify.assertEmpty(IntSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_18() {
        Verify.assertEmpty(IntSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_19() {
        Verify.assertEmpty(LongSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_20() {
        Verify.assertEmpty(LongSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_21() {
        Verify.assertEmpty(LongSets.immutable.with());
    }

    @Test
    public void isEmptyImmutable_22() {
        Verify.assertEmpty(ShortSets.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_23() {
        Verify.assertEmpty(ShortSets.immutable.of());
    }

    @Test
    public void isEmptyImmutable_24() {
        Verify.assertEmpty(ShortSets.immutable.with());
    }
}
