package com.iluwatar.privateclassdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.iluwatar.privateclassdata.utils.InMemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImmutableStewTest_Purified {

    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        appender = new InMemoryAppender();
    }

    @AfterEach
    void tearDown() {
        appender.stop();
    }

    @Test
    void testDrink_1() {
        assertEquals("Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers", appender.getLastMessage());
    }

    @Test
    void testDrink_2() {
        assertEquals("Tasting the stew", appender.getLastMessage());
    }

    @Test
    void testDrink_3() {
        assertEquals("Mixing the stew we find: 0 potatoes, 1 carrots, 2 meat and 3 peppers", appender.getLastMessage());
    }
}
