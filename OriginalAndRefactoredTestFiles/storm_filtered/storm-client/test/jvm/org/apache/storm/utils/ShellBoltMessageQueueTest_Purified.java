package org.apache.storm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.storm.multilang.BoltMsg;
import org.apache.storm.shade.com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShellBoltMessageQueueTest_Purified {

    @Test
    public void testPollWhileThereAreNoDataAvailable_1() throws InterruptedException {
        ShellBoltMessageQueue queue = new ShellBoltMessageQueue();
        Object msg = queue.poll(1, TimeUnit.SECONDS);
        assertNull(msg);
    }

    @Test
    public void testPollWhileThereAreNoDataAvailable_2() throws InterruptedException {
        long start = System.currentTimeMillis();
        long finish = System.currentTimeMillis();
        long waitDuration = finish - start;
        assertTrue(waitDuration >= 1000, "wait duration should be equal or greater than 1000, current: " + waitDuration);
    }
}
