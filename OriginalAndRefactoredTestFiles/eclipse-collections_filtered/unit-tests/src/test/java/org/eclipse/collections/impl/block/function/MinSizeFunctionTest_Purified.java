package org.eclipse.collections.impl.block.function;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinSizeFunctionTest_Purified {

    @Test
    public void minSizeCollection_1() {
        assertEquals(Integer.valueOf(2), MinSizeFunction.COLLECTION.value(2, FastList.newListWith(1, 2, 3)));
    }

    @Test
    public void minSizeCollection_2() {
        assertEquals(Integer.valueOf(2), MinSizeFunction.COLLECTION.value(3, FastList.newListWith(1, 2)));
    }

    @Test
    public void minSizeMap_1() {
        assertEquals(Integer.valueOf(2), MinSizeFunction.MAP.value(2, Maps.mutable.of(1, 1, 2, 2, 3, 3)));
    }

    @Test
    public void minSizeMap_2() {
        assertEquals(Integer.valueOf(2), MinSizeFunction.MAP.value(3, Maps.mutable.of(1, 1, 2, 2)));
    }
}
