package org.apache.hadoop.yarn.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.hadoop.test.PlatformAssumptions.assumeWindows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWindowsBasedProcessTree_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestWindowsBasedProcessTree.class);

    class WindowsBasedProcessTreeTester extends WindowsBasedProcessTree {

        String infoStr = null;

        public WindowsBasedProcessTreeTester(String pid, Clock clock) {
            super(pid, clock);
        }

        @Override
        String getAllProcessInfoFromShell() {
            return infoStr;
        }
    }

    @Test
    @Timeout(30000)
    @SuppressWarnings("deprecation")
    void tree_1() {
        assertTrue(WindowsBasedProcessTree.isAvailable(), "WindowsBasedProcessTree should be available on Windows");
    }

    @Test
    @Timeout(30000)
    @SuppressWarnings("deprecation")
    void tree_2_testMerged_2() {
        ControlledClock testClock = new ControlledClock();
        long elapsedTimeBetweenUpdatesMsec = 0;
        testClock.setTime(elapsedTimeBetweenUpdatesMsec);
        WindowsBasedProcessTreeTester pTree = new WindowsBasedProcessTreeTester("-1", testClock);
        pTree.updateProcessTree();
        assertTrue(pTree.getVirtualMemorySize() == 2048);
        assertTrue(pTree.getVirtualMemorySize(0) == 2048);
        assertTrue(pTree.getRssMemorySize() == 2048);
        assertTrue(pTree.getRssMemorySize(0) == 2048);
        assertTrue(pTree.getCumulativeCpuTime() == 1000);
        assertTrue(pTree.getCpuUsagePercent() == ResourceCalculatorProcessTree.UNAVAILABLE);
        assertTrue(pTree.getVirtualMemorySize() == 3072);
        assertTrue(pTree.getVirtualMemorySize(1) == 2048);
        assertTrue(pTree.getRssMemorySize() == 3072);
        assertTrue(pTree.getRssMemorySize(1) == 2048);
        assertTrue(pTree.getCumulativeCpuTime() == 3000);
        assertTrue(pTree.getCpuUsagePercent() == 200);
        assertEquals(pTree.getCpuUsagePercent(), 200, 0.01, "Percent CPU time is not correct");
        assertTrue(pTree.getVirtualMemorySize(2) == 2048);
        assertTrue(pTree.getRssMemorySize(2) == 2048);
        assertTrue(pTree.getCumulativeCpuTime() == 4000);
        assertEquals(pTree.getCpuUsagePercent(), 0, 0.01, "Percent CPU time is not correct");
    }
}
