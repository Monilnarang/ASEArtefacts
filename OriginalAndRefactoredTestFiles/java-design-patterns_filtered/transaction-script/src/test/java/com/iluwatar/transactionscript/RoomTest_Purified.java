package com.iluwatar.transactionscript;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTest_Purified {

    private Room room;

    private static final int ID = 1;

    private static final String ROOMTYPE = "Single";

    private static final int PRICE = 50;

    private static final boolean BOOKED = false;

    @BeforeEach
    void setUp() {
        room = new Room(ID, ROOMTYPE, PRICE, BOOKED);
    }

    @Test
    void equalsWithSameObjects_1() {
        assertEquals(room, room);
    }

    @Test
    void equalsWithSameObjects_2() {
        assertEquals(room.hashCode(), room.hashCode());
    }
}
