package org.eclipse.collections.impl.list.mutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomAccessListAdapterTest_Purified extends AbstractListTestCase {

    @Override
    protected <T> RandomAccessListAdapter<T> newWith(T... littleElements) {
        return new RandomAccessListAdapter<>(Collections.synchronizedList(new ArrayList<>(FastList.newListWith(littleElements))));
    }

    @Test
    public void testWithMethods_1() {
        Verify.assertContainsAll(this.newWith(1), 1);
    }

    @Test
    public void testWithMethods_2() {
        Verify.assertContainsAll(this.newWith(1).with(2), 1, 2);
    }

    @Test
    public void testWithMethods_3() {
        Verify.assertContainsAll(this.newWith(1).with(2, 3), 1, 2, 3);
    }

    @Test
    public void testWithMethods_4() {
        Verify.assertContainsAll(this.newWith(1).with(2, 3, 4), 1, 2, 3, 4);
    }

    @Test
    public void testWithMethods_5() {
        Verify.assertContainsAll(this.newWith(1).with(2, 3, 4, 5), 1, 2, 3, 4, 5);
    }
}
