package com.graphhopper.util;

import com.graphhopper.coll.GHIntLongHashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GHUtilityTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testEdgeStuff_1to2")
    public void testEdgeStuff_1to2(int param1, int param2) {
        assertEquals(param1, GHUtility.createEdgeKey(param2, false));
    }

    static public Stream<Arguments> Provider_testEdgeStuff_1to2() {
        return Stream.of(arguments(2, 1), arguments(3, 1));
    }
}
