package org.apache.storm.utils;

import org.apache.storm.utils.Time.SimulatedTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeTest_Purified {

    @Test
    public void secsToMillisLongTest_1() {
        assertEquals(Time.secsToMillisLong(0), 0);
    }

    @Test
    public void secsToMillisLongTest_2() {
        assertEquals(Time.secsToMillisLong(0.002), 2);
    }

    @Test
    public void secsToMillisLongTest_3() {
        assertEquals(Time.secsToMillisLong(1), 1000);
    }

    @Test
    public void secsToMillisLongTest_4() {
        assertEquals(Time.secsToMillisLong(1.08), 1080);
    }

    @Test
    public void secsToMillisLongTest_5() {
        assertEquals(Time.secsToMillisLong(10), 10000);
    }

    @Test
    public void secsToMillisLongTest_6() {
        assertEquals(Time.secsToMillisLong(10.1), 10100);
    }
}
