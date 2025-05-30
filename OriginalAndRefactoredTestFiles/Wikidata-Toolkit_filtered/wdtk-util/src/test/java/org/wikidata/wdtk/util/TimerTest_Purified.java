package org.wikidata.wdtk.util;

import static org.junit.Assert.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;
import org.junit.Test;

public class TimerTest_Purified {

    static final int TIME_TOLERANCE = 200000;

    void doDummyComputation() {
        long dummyValue = 0;
        Random rand = new Random();
        int seed = rand.nextInt(10) + 1;
        for (int i = 0; i < 10000000; i++) {
            dummyValue = 10 + ((31 * (dummyValue + seed)) % 1234567);
        }
        if (dummyValue < 10) {
            throw new RuntimeException("This never happens, but let's pretend the value matters to avoid this being complied away.");
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void timerStopReturnValues_1_testMerged_1() {
        Timer timer1 = new Timer("stop test timer", Timer.RECORD_ALL);
        timer1.start();
        long cpuTime1 = timer1.stop();
        assertEquals(cpuTime1, timer1.getTotalCpuTime());
        long cpuTime3 = timer1.stop();
        assertEquals(cpuTime3, -1);
    }

    @Test
    public void timerStopReturnValues_2() {
        Timer timer2 = new Timer("stop test timer wall", Timer.RECORD_WALLTIME);
        timer2.start();
        long cpuTime2 = timer2.stop();
        assertEquals(cpuTime2, -1);
    }
}
