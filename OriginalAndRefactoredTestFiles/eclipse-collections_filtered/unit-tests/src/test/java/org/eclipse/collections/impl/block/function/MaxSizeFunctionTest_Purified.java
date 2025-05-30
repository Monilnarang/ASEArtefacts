package org.eclipse.collections.impl.block.function;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxSizeFunctionTest_Purified {

    @Test
    public void maxSizeCollection_1() {
        assertEquals(Integer.valueOf(3), MaxSizeFunction.COLLECTION.value(2, FastList.newListWith(1, 2, 3)));
    }

    @Test
    public void maxSizeCollection_2() {
        assertEquals(Integer.valueOf(3), MaxSizeFunction.COLLECTION.value(3, FastList.newListWith(1, 2)));
    }

    @Test
    public void maxSizeMap_1() {
        assertEquals(Integer.valueOf(3), MaxSizeFunction.MAP.value(2, Maps.mutable.of(1, 1, 2, 2, 3, 3)));
    }

    @Test
    public void maxSizeMap_2() {
        assertEquals(Integer.valueOf(3), MaxSizeFunction.MAP.value(3, Maps.mutable.of(1, 1, 2, 2)));
    }
}
