package org.eclipse.collections.impl.list.mutable;

import java.util.ListIterator;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.list.fixed.UnmodifiableMemoryEfficientListTestCase;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class UnmodifiableMutableListTestCase_Purified extends UnmodifiableMemoryEfficientListTestCase<Integer> {

    @Test
    public void testClone_1() {
        assertEquals(this.getCollection(), this.getCollection().clone());
    }

    @Test
    public void testClone_2() {
        assertNotSame(this.getCollection(), this.getCollection().clone());
    }
}
