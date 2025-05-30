package org.eclipse.collections.impl.list.immutable.primitive;

import org.eclipse.collections.api.list.primitive.ImmutableBooleanList;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmutableBooleanArrayListTest_Purified extends AbstractImmutableBooleanListTestCase {

    @Override
    protected ImmutableBooleanList classUnderTest() {
        return ImmutableBooleanArrayList.newListWith(true, false, true);
    }

    @Override
    @Test
    public void size_1() {
        Verify.assertSize(3, ImmutableBooleanArrayList.newList(BooleanArrayList.newListWith(true, false, true)));
    }

    @Override
    @Test
    public void size_2() {
        Verify.assertSize(3, BooleanLists.immutable.ofAll(ImmutableBooleanArrayList.newList(BooleanArrayList.newListWith(true, false, true))));
    }
}
