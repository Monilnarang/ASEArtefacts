package org.graylog2.indexer.indices.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberBasedIndexNameComparatorTest_Purified {

    private NumberBasedIndexNameComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new NumberBasedIndexNameComparator("_");
    }

    @Test
    void indexPrefixIsMoreImportantThanNumberWhileSorting_1() {
        assertTrue(comparator.compare("abc_5", "bcd_3") < 0);
    }

    @Test
    void indexPrefixIsMoreImportantThanNumberWhileSorting_2() {
        assertTrue(comparator.compare("abc", "bcd_3") < 0);
    }

    @Test
    void indexPrefixIsMoreImportantThanNumberWhileSorting_3() {
        assertTrue(comparator.compare("zzz_1", "aaa") > 0);
    }

    @Test
    void indexPrefixIsMoreImportantThanNumberWhileSorting_4() {
        assertTrue(comparator.compare("zzz", "aaa") > 0);
    }

    @Test
    void comparesDescByNumberAfterLastSeparatorOccurrence_1() {
        assertTrue(comparator.compare("lalala_5", "lalala_3") < 0);
    }

    @Test
    void comparesDescByNumberAfterLastSeparatorOccurrence_2() {
        assertTrue(comparator.compare("lalala_3", "lalala_5") > 0);
    }

    @Test
    void comparesDescByNumberAfterLastSeparatorOccurrence_3() {
        assertTrue(comparator.compare("lalala_1_5", "lalala_1_3") < 0);
    }

    @Test
    void comparesDescByNumberAfterLastSeparatorOccurrence_4() {
        assertTrue(comparator.compare("lalala_1_5", "lalala_1_0") < 0);
    }

    @Test
    void indexNameWithNoSeparatorGoesLast_1() {
        assertTrue(comparator.compare("lalala", "lalala_3") > 0);
    }

    @Test
    void indexNameWithNoSeparatorGoesLast_2() {
        assertTrue(comparator.compare("lalala_3", "lalala") < 0);
    }

    @Test
    void indexNameWithNoSeparatorGoesLast_3() {
        assertTrue(comparator.compare("lalala", "lalala_42") > 0);
    }

    @Test
    void indexNameWithNoSeparatorGoesLast_4() {
        assertTrue(comparator.compare("lalala_42", "lalala") < 0);
    }

    @Test
    void isImmuneToWrongNumbersWhichGoLast_1() {
        assertTrue(comparator.compare("lalala_1!1", "lalala_3") > 0);
    }

    @Test
    void isImmuneToWrongNumbersWhichGoLast_2() {
        assertTrue(comparator.compare("lalala_3", "lalala_1!1") < 0);
    }

    @Test
    void isImmuneToMissingNumbersWhichGoLast_1() {
        assertTrue(comparator.compare("lalala_", "lalala_3") > 0);
    }

    @Test
    void isImmuneToMissingNumbersWhichGoLast_2() {
        assertTrue(comparator.compare("lalala_3", "lalala_") < 0);
    }
}
