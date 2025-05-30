package org.graylog.plugins.views.search.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListOfStringsComparatorTest_Purified {

    ListOfStringsComparator toTest;

    @BeforeEach
    void setUp() {
        toTest = new ListOfStringsComparator();
    }

    @Test
    void testListsWithTheSameElementsAreEqual_1() {
        assertEquals(0, toTest.compare(List.of("mum"), List.of("mum")));
    }

    @Test
    void testListsWithTheSameElementsAreEqual_2() {
        assertEquals(0, toTest.compare(List.of("mum", "dad"), List.of("mum", "dad")));
    }

    @Test
    void testListsWithSameElementsIgnoringCaseAreEqual_1() {
        assertEquals(0, toTest.compare(List.of("Mum"), List.of("mum")));
    }

    @Test
    void testListsWithSameElementsIgnoringCaseAreEqual_2() {
        assertEquals(0, toTest.compare(List.of("mum", "Dad"), List.of("mum", "dad")));
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
}
