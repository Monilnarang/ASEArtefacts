package org.eclipse.collections.impl.set.mutable.primitive;

import org.eclipse.collections.api.block.function.primitive.ByteToObjectFunction;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.MutableByteSet;
import org.eclipse.collections.impl.block.factory.primitive.BytePredicates;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ByteHashSetTest_Purified extends AbstractByteSetTestCase {

    @Override
    protected final ByteHashSet classUnderTest() {
        return ByteHashSet.newSetWith((byte) 1, (byte) 2, (byte) 3);
    }

    @Override
    protected ByteHashSet newWith(byte... elements) {
        return ByteHashSet.newSetWith(elements);
    }

    private void addAndRemoveData(ByteHashSet hashSet) {
        for (byte i = (byte) 100; i < (byte) 200; i++) {
            assertFalse(hashSet.contains(i));
            assertTrue(hashSet.add(i));
            assertTrue(hashSet.remove(i));
        }
    }

    @Override
    @Test
    public void max_1() {
        assertEquals(9L, this.newWith((byte) -1, (byte) -2, (byte) 9).max());
    }

    @Override
    @Test
    public void max_2() {
        assertEquals(127L, this.newWith((byte) -1, (byte) -2, (byte) 9, (byte) -65, (byte) -127, (byte) 65, (byte) 127).max());
    }

    @Override
    @Test
    public void max_3() {
        assertEquals(-1L, this.newWith((byte) -1, (byte) -2, (byte) -9, (byte) -65, (byte) -127).max());
    }

    @Override
    @Test
    public void max_4() {
        assertEquals(-65L, this.newWith((byte) -65, (byte) -87, (byte) -127).max());
    }
}
