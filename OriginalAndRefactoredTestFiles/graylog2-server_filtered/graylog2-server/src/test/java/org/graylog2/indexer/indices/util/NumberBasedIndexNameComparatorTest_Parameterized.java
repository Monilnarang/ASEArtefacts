package org.graylog2.indexer.indices.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class NumberBasedIndexNameComparatorTest_Parameterized {

    private NumberBasedIndexNameComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new NumberBasedIndexNameComparator("_");
    }

    @ParameterizedTest
    @MethodSource("Provider_indexPrefixIsMoreImportantThanNumberWhileSorting_1_1_1_1_1to2_2_2_2_2to3_3_3to4_4_4")
    void indexPrefixIsMoreImportantThanNumberWhileSorting_1_1_1_1_1to2_2_2_2_2to3_3_3to4_4_4(int param1, String param2, String param3) {
        assertTrue(comparator.compare(param2, param3) < param1);
    }

    static public Stream<Arguments> Provider_indexPrefixIsMoreImportantThanNumberWhileSorting_1_1_1_1_1to2_2_2_2_2to3_3_3to4_4_4() {
        return Stream.of(arguments(0, "abc_5", "bcd_3"), arguments(0, "abc", "bcd_3"), arguments(0, "zzz_1", "aaa"), arguments(0, "zzz", "aaa"), arguments(0, "lalala_5", "lalala_3"), arguments(0, "lalala_3", "lalala_5"), arguments(0, "lalala_1_5", "lalala_1_3"), arguments(0, "lalala_1_5", "lalala_1_0"), arguments(0, "lalala", "lalala_3"), arguments(0, "lalala_3", "lalala"), arguments(0, "lalala", "lalala_42"), arguments(0, "lalala_42", "lalala"), arguments(0, "lalala_1!1", "lalala_3"), arguments(0, "lalala_3", "lalala_1!1"), arguments(0, "lalala_", "lalala_3"), arguments(0, "lalala_3", "lalala_"));
    }
}
