package org.eclipse.collections.impl.factory.primitive;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.list.primitive.ImmutableBooleanListFactory;
import org.eclipse.collections.api.factory.list.primitive.MutableBooleanListFactory;
import org.eclipse.collections.api.list.primitive.ImmutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BooleanListsTest_Purified {

    @Test
    public void emptyList_1() {
        Verify.assertEmpty(BooleanLists.immutable.of());
    }

    @Test
    public void emptyList_2() {
        assertSame(BooleanLists.immutable.of(), BooleanLists.immutable.of());
    }

    @Test
    public void emptyList_3() {
        Verify.assertPostSerializedIdentity(BooleanLists.immutable.of());
    }

    @Test
    public void newListWithWithList_1() {
        assertEquals(new BooleanArrayList(), BooleanLists.immutable.ofAll(new BooleanArrayList()));
    }

    @Test
    public void newListWithWithList_2() {
        assertEquals(BooleanArrayList.newListWith(true), BooleanLists.immutable.ofAll(BooleanArrayList.newListWith(true)));
    }

    @Test
    public void newListWithWithList_3() {
        assertEquals(BooleanArrayList.newListWith(true, false), BooleanLists.immutable.ofAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void newListWithWithList_4() {
        assertEquals(BooleanArrayList.newListWith(true, false, true), BooleanLists.immutable.ofAll(BooleanArrayList.newListWith(true, false, true)));
    }
}
