package org.eclipse.collections.impl.block.function;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Functions0;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Functions0Test_Purified {

    private MyRuntimeException throwMyException(Throwable exception) {
        return new MyRuntimeException(exception);
    }

    private static class MyRuntimeException extends RuntimeException {

        MyRuntimeException(Throwable cause) {
            super(cause);
        }
    }

    @Test
    public void newFastList_1() {
        assertEquals(Lists.mutable.of(), Functions0.newFastList().value());
    }

    @Test
    public void newFastList_2() {
        Verify.assertInstanceOf(FastList.class, Functions0.newFastList().value());
    }

    @Test
    public void newUnifiedSet_1() {
        assertEquals(UnifiedSet.newSet(), Functions0.newUnifiedSet().value());
    }

    @Test
    public void newUnifiedSet_2() {
        Verify.assertInstanceOf(UnifiedSet.class, Functions0.newUnifiedSet().value());
    }

    @Test
    public void newHashBag_1() {
        assertEquals(Bags.mutable.of(), Functions0.newHashBag().value());
    }

    @Test
    public void newHashBag_2() {
        Verify.assertInstanceOf(HashBag.class, Functions0.newHashBag().value());
    }

    @Test
    public void newUnifiedMap_1() {
        assertEquals(UnifiedMap.newMap(), Functions0.newUnifiedMap().value());
    }

    @Test
    public void newUnifiedMap_2() {
        Verify.assertInstanceOf(UnifiedMap.class, Functions0.newUnifiedMap().value());
    }

    @Test
    public void zeroInteger_1() {
        assertEquals(Integer.valueOf(0), Functions0.zeroInteger().value());
    }

    @Test
    public void zeroInteger_2() {
        assertEquals(Integer.valueOf(0), Functions0.value(0).value());
    }

    @Test
    public void zeroAtomicInteger_1() {
        Verify.assertInstanceOf(AtomicInteger.class, Functions0.zeroAtomicInteger().value());
    }

    @Test
    public void zeroAtomicInteger_2() {
        assertEquals(0, Functions0.zeroAtomicInteger().value().get());
    }

    @Test
    public void zeroAtomicLong_1() {
        Verify.assertInstanceOf(AtomicLong.class, Functions0.zeroAtomicLong().value());
    }

    @Test
    public void zeroAtomicLong_2() {
        assertEquals(0, Functions0.zeroAtomicLong().value().get());
    }
}
