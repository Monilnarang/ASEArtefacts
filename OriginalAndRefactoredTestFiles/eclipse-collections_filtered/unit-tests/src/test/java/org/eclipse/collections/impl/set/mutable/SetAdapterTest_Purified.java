package org.eclipse.collections.impl.set.mutable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetAdapterTest_Purified extends AbstractMutableSetTestCase {

    @Override
    protected <T> SetAdapter<T> newWith(T... littleElements) {
        return new SetAdapter<>(new HashSet<>(UnifiedSet.newSetWith(littleElements)));
    }

    @Override
    @Test
    public void select_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3)), 1, 2);
    }

    @Override
    @Test
    public void select_2() {
        Verify.assertContainsAll(this.newWith(-1, 2, 3, 4, 5).select(Predicates.lessThan(3), FastList.newList()), -1, 2);
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3), FastList.newList()), 3, 4);
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNotNull(this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertNull(this.newWith().getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNotNull(this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNull(this.newWith().getLast());
    }

    @Test
    public void withMethods_1() {
        Verify.assertContainsAll(this.newWith().with(1), 1);
    }

    @Test
    public void withMethods_2() {
        Verify.assertContainsAll(this.newWith().with(1, 2), 1, 2);
    }

    @Test
    public void withMethods_3() {
        Verify.assertContainsAll(this.newWith().with(1, 2, 3), 1, 2, 3);
    }

    @Test
    public void withMethods_4() {
        Verify.assertContainsAll(this.newWith().with(1, 2, 3, 4), 1, 2, 3, 4);
    }
}
