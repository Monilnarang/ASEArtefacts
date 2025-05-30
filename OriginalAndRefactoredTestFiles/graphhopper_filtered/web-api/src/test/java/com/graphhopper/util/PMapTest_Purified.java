package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PMapTest_Purified {

    @Test
    public void empty_1() {
        assertTrue(new PMap("").toMap().isEmpty());
    }

    @Test
    public void empty_2() {
        assertTrue(new PMap("name").toMap().isEmpty());
    }
}
