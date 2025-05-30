package org.eclipse.collections.impl.list.mutable.primitive;

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

public class MutableEmptyPrimitiveTest_Purified {

    @Test
    public void isEmptyMutable_1() {
        Verify.assertEmpty(BooleanLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_2() {
        Verify.assertEmpty(BooleanLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_3() {
        Verify.assertEmpty(BooleanLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_4() {
        Verify.assertEmpty(ByteLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_5() {
        Verify.assertEmpty(ByteLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_6() {
        Verify.assertEmpty(ByteLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_7() {
        Verify.assertEmpty(CharLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_8() {
        Verify.assertEmpty(CharLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_9() {
        Verify.assertEmpty(CharLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_10() {
        Verify.assertEmpty(DoubleLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_11() {
        Verify.assertEmpty(DoubleLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_12() {
        Verify.assertEmpty(DoubleLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_13() {
        Verify.assertEmpty(FloatLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_14() {
        Verify.assertEmpty(FloatLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_15() {
        Verify.assertEmpty(FloatLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_16() {
        Verify.assertEmpty(IntLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_17() {
        Verify.assertEmpty(IntLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_18() {
        Verify.assertEmpty(IntLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_19() {
        Verify.assertEmpty(LongLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_20() {
        Verify.assertEmpty(LongLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_21() {
        Verify.assertEmpty(LongLists.mutable.with());
    }

    @Test
    public void isEmptyMutable_22() {
        Verify.assertEmpty(ShortLists.mutable.empty());
    }

    @Test
    public void isEmptyMutable_23() {
        Verify.assertEmpty(ShortLists.mutable.of());
    }

    @Test
    public void isEmptyMutable_24() {
        Verify.assertEmpty(ShortLists.mutable.with());
    }
}
