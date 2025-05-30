package org.apache.druid.query.rowsandcols.semantic;

import org.junit.Test;
import static org.apache.druid.query.rowsandcols.semantic.DefaultFramedOnHeapAggregatable.invertedOrderForLastK;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DefaultFramedOnHeapAggregatableTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testInvertedOrderForLastK_1_1_1to2_2_2to3_3_3")
    public void testInvertedOrderForLastK_1_1_1to2_2_2to3_3_3(int param1, int param2, int param3, int param4) {
        assertEquals(param1, invertedOrderForLastK(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testInvertedOrderForLastK_1_1_1to2_2_2to3_3_3() {
        return Stream.of(arguments(0, 0, 3, 1), arguments(1, 1, 3, 1), arguments(2, 2, 3, 1), arguments(0, 0, 3, 2), arguments(2, 1, 3, 2), arguments(1, 2, 3, 2), arguments(2, 0, 3, 3), arguments(1, 1, 3, 3), arguments(0, 2, 3, 3));
    }
}
