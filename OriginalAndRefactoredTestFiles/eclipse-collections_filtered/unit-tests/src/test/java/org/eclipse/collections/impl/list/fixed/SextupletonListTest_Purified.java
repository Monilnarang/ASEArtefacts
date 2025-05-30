package org.eclipse.collections.impl.list.fixed;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SextupletonListTest_Purified extends AbstractMemoryEfficientMutableListTestCase {

    @Override
    protected int getSize() {
        return 6;
    }

    @Override
    protected Class<?> getListType() {
        return SextupletonList.class;
    }

    private void assertUnchanged() {
        Verify.assertSize(6, this.list);
        Verify.assertNotContains("7", this.list);
        assertEquals(FastList.newListWith("1", "2", "3", "4", "5", "6"), this.list);
    }

    @Test
    public void testContains_1() {
        assertTrue(this.list.contains("1"));
    }

    @Test
    public void testContains_2() {
        assertTrue(this.list.contains("2"));
    }

    @Test
    public void testContains_3() {
        assertTrue(this.list.contains("3"));
    }

    @Test
    public void testContains_4() {
        assertTrue(this.list.contains("4"));
    }

    @Test
    public void testContains_5() {
        assertTrue(this.list.contains("5"));
    }

    @Test
    public void testContains_6() {
        assertTrue(this.list.contains("6"));
    }

    @Test
    public void testContains_7() {
        assertFalse(this.list.contains("7"));
    }
}
