package org.asteriskjava.live.internal;

import org.asteriskjava.live.QueueMemberState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AsteriskQueueMemberImplTest_Purified {

    private AsteriskQueueMemberImpl queueMember;

    private int numberOfChanges;

    private AsteriskServerImpl server;

    private AsteriskQueueImpl queue;

    @BeforeEach
    void setUp() {
        server = new AsteriskServerImpl();
        queue = new AsteriskQueueImpl(server, "test", 25, "RoundRobin", 15, 5, 0, 0, 1, 1, 1, 1.0);
        queueMember = new AsteriskQueueMemberImpl(server, queue, "Agent/777", QueueMemberState.DEVICE_UNKNOWN, false, 10, "dynamic", 3, 6000l);
        numberOfChanges = 0;
    }

    @Test
    void testQueueMemberEvents_1() {
        assertEquals(QueueMemberState.DEVICE_UNKNOWN, queueMember.getState());
    }

    @Test
    void testQueueMemberEvents_2_testMerged_2() {
        assertEquals("state", evt.getPropertyName(), "wrong propertyName");
        assertEquals(QueueMemberState.DEVICE_UNKNOWN, evt.getOldValue(), "wrong oldValue");
        assertEquals(QueueMemberState.DEVICE_BUSY, evt.getNewValue(), "wrong newValue");
        assertEquals(queueMember, evt.getSource(), "wrong queue member");
        numberOfChanges++;
        queueMember.stateChanged(QueueMemberState.DEVICE_BUSY);
        assertEquals(1, numberOfChanges, "wrong number of propagated changes");
    }
}
