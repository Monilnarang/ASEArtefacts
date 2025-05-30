package com.iluwatar.hexagonal.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class InMemoryBankTest_Purified {

    private final WireTransfers bank = new InMemoryBank();

    @Test
    void testInit_1() {
        assertEquals(0, bank.getFunds("foo"));
    }

    @Test
    void testInit_2_testMerged_2() {
        bank.setFunds("foo", 100);
        assertEquals(100, bank.getFunds("foo"));
        bank.setFunds("bar", 150);
        assertEquals(150, bank.getFunds("bar"));
        assertTrue(bank.transferFunds(50, "bar", "foo"));
        assertEquals(150, bank.getFunds("foo"));
        assertEquals(100, bank.getFunds("bar"));
    }
}
