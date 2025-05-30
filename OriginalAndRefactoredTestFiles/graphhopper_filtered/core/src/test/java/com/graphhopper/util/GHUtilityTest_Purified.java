package com.graphhopper.util;

import com.graphhopper.coll.GHIntLongHashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GHUtilityTest_Purified {

    @Test
    public void testEdgeStuff_1() {
        assertEquals(2, GHUtility.createEdgeKey(1, false));
    }

    @Test
    public void testEdgeStuff_2() {
        assertEquals(3, GHUtility.createEdgeKey(1, true));
    }
}
