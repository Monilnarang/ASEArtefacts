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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuadrupletonListTest_Purified extends AbstractMemoryEfficientMutableListTestCase {

    @Override
    protected int getSize() {
        return 4;
    }

    @Override
    protected Class<?> getListType() {
        return QuadrupletonList.class;
    }

    private void assertUnchanged() {
        Verify.assertSize(4, this.list);
        Verify.assertNotContains("5", this.list);
        assertEquals(FastList.newListWith("1", "2", "3", "4"), this.list);
    }

    @Test
    public void testContains_1() {
        Verify.assertContains("1", this.list);
    }

    @Test
    public void testContains_2() {
        Verify.assertContains("2", this.list);
    }

    @Test
    public void testContains_3() {
        Verify.assertContains("3", this.list);
    }

    @Test
    public void testContains_4() {
        Verify.assertContains("4", this.list);
    }

    @Test
    public void testContains_5() {
        Verify.assertNotContains("5", this.list);
    }
}
