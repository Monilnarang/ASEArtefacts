package org.eclipse.collections.impl.set.fixed;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.set.FixedSizeSetFactory;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.FixedSizeSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FixedSizeSetFactoryTest_Purified {

    private FixedSizeSetFactory setFactory;

    @BeforeEach
    public void setUp() {
        this.setFactory = FixedSizeSetFactoryImpl.INSTANCE;
    }

    private void assertCreateSet(FixedSizeSet<String> undertest, String... expected) {
        assertEquals(UnifiedSet.newSetWith(expected), undertest);
        Verify.assertInstanceOf(FixedSizeSet.class, undertest);
    }

    @Test
    public void testCreateWith3Args_1() {
        this.assertCreateSet(this.setFactory.of("a", "a"), "a");
    }

    @Test
    public void testCreateWith3Args_2() {
        this.assertCreateSet(this.setFactory.of("a", "a", "c"), "a", "c");
    }

    @Test
    public void testCreateWith3Args_3() {
        this.assertCreateSet(this.setFactory.of("a", "b", "a"), "a", "b");
    }

    @Test
    public void testCreateWith3Args_4() {
        this.assertCreateSet(this.setFactory.of("a", "b", "b"), "a", "b");
    }

    @Test
    public void testCreateWith4Args_1() {
        this.assertCreateSet(this.setFactory.of("a", "a", "c", "d"), "a", "c", "d");
    }

    @Test
    public void testCreateWith4Args_2() {
        this.assertCreateSet(this.setFactory.of("a", "b", "a", "d"), "a", "b", "d");
    }

    @Test
    public void testCreateWith4Args_3() {
        this.assertCreateSet(this.setFactory.of("a", "b", "c", "a"), "a", "b", "c");
    }

    @Test
    public void testCreateWith4Args_4() {
        this.assertCreateSet(this.setFactory.of("a", "b", "b", "d"), "a", "b", "d");
    }

    @Test
    public void testCreateWith4Args_5() {
        this.assertCreateSet(this.setFactory.of("a", "b", "c", "b"), "a", "b", "c");
    }

    @Test
    public void testCreateWith4Args_6() {
        this.assertCreateSet(this.setFactory.of("a", "b", "c", "c"), "a", "b", "c");
    }
}
