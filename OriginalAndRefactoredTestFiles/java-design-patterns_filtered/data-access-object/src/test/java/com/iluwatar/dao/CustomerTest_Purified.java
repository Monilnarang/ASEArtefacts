package com.iluwatar.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest_Purified {

    private Customer customer;

    private static final int ID = 1;

    private static final String FIRSTNAME = "Winston";

    private static final String LASTNAME = "Churchill";

    @BeforeEach
    void setUp() {
        customer = new Customer(ID, FIRSTNAME, LASTNAME);
    }

    @Test
    void equalsWithSameObjects_1() {
        assertEquals(customer, customer);
    }

    @Test
    void equalsWithSameObjects_2() {
        assertEquals(customer.hashCode(), customer.hashCode());
    }
}
