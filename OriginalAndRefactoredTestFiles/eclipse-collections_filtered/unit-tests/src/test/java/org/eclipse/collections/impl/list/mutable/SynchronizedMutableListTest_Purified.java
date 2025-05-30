package org.eclipse.collections.impl.list.mutable;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedMutableListTest_Purified extends AbstractListTestCase {

    @Override
    protected <T> MutableList<T> newWith(T... littleElements) {
        return new SynchronizedMutableList<>(FastList.newListWith(littleElements));
    }

    @Override
    @Test
    public void equalsAndHashCode_1() {
        Verify.assertPostSerializedEqualsAndHashCode(this.newWith(1, 2, 3));
    }

    @Override
    @Test
    public void equalsAndHashCode_2() {
        Verify.assertInstanceOf(SynchronizedMutableList.class, this.newWith(1, 2, 3));
    }
}
