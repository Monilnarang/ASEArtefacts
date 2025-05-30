package org.eclipse.collections.impl.set.fixed;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class QuadrupletonSetTest_Purified extends AbstractMemoryEfficientMutableSetTestCase {

    private QuadrupletonSet<String> set;

    @BeforeEach
    public void setUp() {
        this.set = new QuadrupletonSet<>("1", "2", "3", "4");
    }

    @Override
    protected MutableSet<String> classUnderTest() {
        return new QuadrupletonSet<>("1", "2", "3", "4");
    }

    @Override
    protected MutableSet<String> classUnderTestWithNull() {
        return new QuadrupletonSet<>(null, "2", "3", "4");
    }

    private void assertUnchanged() {
        Verify.assertSize(4, this.set);
        Verify.assertContainsAll(this.set, "1", "2", "3", "4");
        Verify.assertNotContains("5", this.set);
    }

    @Test
    public void contains_1() {
        Verify.assertContains("1", this.set);
    }

    @Test
    public void contains_2() {
        Verify.assertContainsAll(this.set, "2", "3", "4");
    }

    @Test
    public void contains_3() {
        Verify.assertNotContains("5", this.set);
    }
}
