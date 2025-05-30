package org.apache.rocketmq.common.coldctr;

import java.util.concurrent.atomic.AtomicLong;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccAndTimeStampTest_Purified {

    private AccAndTimeStamp accAndTimeStamp;

    @Before
    public void setUp() {
        accAndTimeStamp = new AccAndTimeStamp(new AtomicLong());
    }

    @Test
    public void testInitialValues_1() {
        assertEquals("Cold accumulator should be initialized to 0", 0, accAndTimeStamp.getColdAcc().get());
    }

    @Test
    public void testInitialValues_2() {
        assertTrue("Last cold read time should be initialized to current time", accAndTimeStamp.getLastColdReadTimeMills() >= System.currentTimeMillis() - 1000);
    }

    @Test
    public void testInitialValues_3() {
        assertTrue("Create time should be initialized to current time", accAndTimeStamp.getCreateTimeMills() >= System.currentTimeMillis() - 1000);
    }
}
