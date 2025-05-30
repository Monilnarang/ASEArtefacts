package org.apache.seata.metrics.exporter.prometheus;

import org.apache.seata.metrics.registry.compact.TimerValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class TimerValueTest_Purified {

    private TimerValue timerValue;

    @BeforeEach
    public void setUp() {
        timerValue = new TimerValue();
    }

    @Test
    public void testGetCount_1() {
        assertEquals(0, timerValue.getCount());
    }

    @Test
    public void testGetCount_2() {
        timerValue.record(1, TimeUnit.SECONDS);
        assertEquals(1, timerValue.getCount());
    }

    @Test
    public void testGetTotal_1() {
        assertEquals(0, timerValue.getTotal());
    }

    @Test
    public void testGetTotal_2() {
        timerValue.record(1, TimeUnit.SECONDS);
        assertEquals(TimeUnit.MICROSECONDS.convert(1, TimeUnit.SECONDS), timerValue.getTotal());
    }

    @Test
    public void testGetMax_1() {
        assertEquals(0, timerValue.getMax());
    }

    @Test
    public void testGetMax_2() {
        timerValue.record(1, TimeUnit.SECONDS);
        assertEquals(TimeUnit.MICROSECONDS.convert(1, TimeUnit.SECONDS), timerValue.getMax());
    }

    @Test
    public void testGetAverage_1() {
        assertEquals(0, timerValue.getAverage());
    }

    @Test
    public void testGetAverage_2() {
        timerValue.record(1, TimeUnit.SECONDS);
        assertEquals(1000000, timerValue.getAverage());
    }
}
