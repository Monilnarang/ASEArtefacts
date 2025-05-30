package org.eclipse.collections.impl.list.fixed;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmptyListTest_Purified {

    @Test
    public void contains_1() {
        assertFalse(new EmptyList<>().contains(null));
    }

    @Test
    public void contains_2() {
        assertFalse(new EmptyList<>().contains(new Object()));
    }

    @Test
    public void empty_1() {
        Verify.assertEmpty(new EmptyList<>());
    }

    @Test
    public void empty_2() {
        assertFalse(new EmptyList<>().notEmpty());
    }

    @Test
    public void empty_3() {
        Verify.assertEmpty(Lists.fixedSize.of());
    }

    @Test
    public void empty_4() {
        assertFalse(Lists.fixedSize.of().notEmpty());
    }

    @Test
    public void getFirstLast_1() {
        assertNull(new EmptyList<>().getFirst());
    }

    @Test
    public void getFirstLast_2() {
        assertNull(new EmptyList<>().getLast());
    }

    @Test
    public void readResolve_1() {
        Verify.assertInstanceOf(EmptyList.class, Lists.fixedSize.of());
    }

    @Test
    public void readResolve_2() {
        Verify.assertPostSerializedIdentity(Lists.fixedSize.of());
    }
}
