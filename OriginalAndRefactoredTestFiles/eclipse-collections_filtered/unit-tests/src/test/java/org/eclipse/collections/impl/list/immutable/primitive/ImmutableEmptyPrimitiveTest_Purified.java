package org.eclipse.collections.impl.list.immutable.primitive;

import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class ImmutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyImmutable_1() {
        Verify.assertEmpty(BooleanLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_2() {
        Verify.assertEmpty(BooleanLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_3() {
        Verify.assertEmpty(BooleanLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_4() {
        Verify.assertEmpty(ByteLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_5() {
        Verify.assertEmpty(ByteLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_6() {
        Verify.assertEmpty(ByteLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_7() {
        Verify.assertEmpty(CharLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_8() {
        Verify.assertEmpty(CharLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_9() {
        Verify.assertEmpty(CharLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_10() {
        Verify.assertEmpty(DoubleLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_11() {
        Verify.assertEmpty(DoubleLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_12() {
        Verify.assertEmpty(DoubleLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_13() {
        Verify.assertEmpty(FloatLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_14() {
        Verify.assertEmpty(FloatLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_15() {
        Verify.assertEmpty(FloatLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_16() {
        Verify.assertEmpty(IntLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_17() {
        Verify.assertEmpty(IntLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_18() {
        Verify.assertEmpty(IntLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_19() {
        Verify.assertEmpty(LongLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_20() {
        Verify.assertEmpty(LongLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_21() {
        Verify.assertEmpty(LongLists.immutable.with());
    }

    @Test
    public void isEmptyImmutable_22() {
        Verify.assertEmpty(ShortLists.immutable.empty());
    }

    @Test
    public void isEmptyImmutable_23() {
        Verify.assertEmpty(ShortLists.immutable.of());
    }

    @Test
    public void isEmptyImmutable_24() {
        Verify.assertEmpty(ShortLists.immutable.with());
    }
}
