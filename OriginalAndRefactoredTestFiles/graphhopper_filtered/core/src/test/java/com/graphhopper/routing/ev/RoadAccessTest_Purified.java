package com.graphhopper.routing.ev;

import org.junit.jupiter.api.Test;
import static com.graphhopper.routing.ev.RoadAccess.NO;
import static com.graphhopper.routing.ev.RoadAccess.YES;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoadAccessTest_Purified {

    @Test
    public void testBasics_1() {
        assertEquals(YES, RoadAccess.find("unknown"));
    }

    @Test
    public void testBasics_2() {
        assertEquals(NO, RoadAccess.find("no"));
    }
}
