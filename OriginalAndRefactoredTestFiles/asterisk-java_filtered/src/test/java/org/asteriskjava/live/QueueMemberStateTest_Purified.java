package org.asteriskjava.live;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueMemberStateTest_Purified {

    @Test
    void testValueOf_1() {
        assertEquals(QueueMemberState.DEVICE_INUSE, QueueMemberState.valueOf("DEVICE_INUSE"));
    }

    @Test
    void testValueOf_2() {
        assertEquals(QueueMemberState.DEVICE_INUSE, QueueMemberState.valueOf(2));
    }
}
