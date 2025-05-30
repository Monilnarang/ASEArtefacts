package org.eclipse.collections.impl.list.mutable;

import java.util.Arrays;
import java.util.LinkedList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListAdapterTest_Purified extends AbstractListTestCase {

    @Override
    protected <T> ListAdapter<T> newWith(T... littleElements) {
        return new ListAdapter<>(new LinkedList<>(FastList.newListWith(littleElements)));
    }

    @Override
    @Test
    public void withMethods_1() {
        Verify.assertContainsAll(this.newWith(1).with(2, 3), 1, 2, 3);
    }

    @Override
    @Test
    public void withMethods_2() {
        Verify.assertContainsAll(this.newWith(1).with(2, 3, 4), 1, 2, 3, 4);
    }

    @Override
    @Test
    public void withMethods_3() {
        Verify.assertContainsAll(this.newWith(1).with(2, 3, 4, 5), 1, 2, 3, 4, 5);
    }
}
