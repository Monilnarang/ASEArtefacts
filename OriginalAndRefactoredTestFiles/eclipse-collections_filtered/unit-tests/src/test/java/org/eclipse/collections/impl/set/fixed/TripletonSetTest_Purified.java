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

public class TripletonSetTest_Purified extends AbstractMemoryEfficientMutableSetTestCase {

    private TripletonSet<String> set;

    @BeforeEach
    public void setUp() {
        this.set = new TripletonSet<>("1", "2", "3");
    }

    @Override
    protected MutableSet<String> classUnderTest() {
        return new TripletonSet<>("1", "2", "3");
    }

    @Override
    protected MutableSet<String> classUnderTestWithNull() {
        return new TripletonSet<>(null, "2", "3");
    }

    private void assertUnchanged() {
        Verify.assertEqualsAndHashCode(UnifiedSet.newSetWith("1", "2", "3"), this.set);
    }

    @Test
    public void contains_1() {
        Verify.assertContainsAll(this.set, "1", "2", "3");
    }

    @Test
    public void contains_2() {
        Verify.assertNotContains("4", this.set);
    }
}
