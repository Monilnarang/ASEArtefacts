package org.graylog.plugins.views.search.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ListOfStringsComparatorTest_Parameterized {

    ListOfStringsComparator toTest;

    @BeforeEach
    void setUp() {
        toTest = new ListOfStringsComparator();
    }

    @Test
    void testEmptyListIsSmallerThanListWithElements_1() {
        assertTrue(toTest.compare(List.of(), List.of("mum")) < 0);
    }

    @Test
    void testEmptyListIsSmallerThanListWithElements_2() {
        assertTrue(toTest.compare(List.of("mum", "Dad"), List.of()) > 0);
    }

    @Test
    void testListOfSameSizeAreOrderedBasedOnTheirFirstNonEqualElement_1() {
        assertTrue(toTest.compare(List.of("mum", "dad"), List.of("mum", "plumber")) < 0);
    }

    @Test
    void testListOfSameSizeAreOrderedBasedOnTheirFirstNonEqualElement_2() {
        assertTrue(toTest.compare(List.of("mummy", "dad", ""), List.of("mum", "dad", "whatever")) > 0);
    }

    @ParameterizedTest
    @MethodSource("Provider_testListsWithTheSameElementsAreEqual_1_1")
    void testListsWithTheSameElementsAreEqual_1_1(int param1, String param2, String param3) {
        assertEquals(param1, toTest.compare(List.of(param2), List.of(param3)));
    }

    static public Stream<Arguments> Provider_testListsWithTheSameElementsAreEqual_1_1() {
        return Stream.of(arguments(0, "mum", "mum"), arguments(0, "Mum", "mum"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testListsWithTheSameElementsAreEqual_2_2")
    void testListsWithTheSameElementsAreEqual_2_2(int param1, String param2, String param3, String param4, String param5) {
        assertEquals(param1, toTest.compare(List.of(param2, param3), List.of(param4, param5)));
    }

    static public Stream<Arguments> Provider_testListsWithTheSameElementsAreEqual_2_2() {
        return Stream.of(arguments(0, "mum", "dad", "mum", "dad"), arguments(0, "mum", "Dad", "mum", "dad"));
    }
}
