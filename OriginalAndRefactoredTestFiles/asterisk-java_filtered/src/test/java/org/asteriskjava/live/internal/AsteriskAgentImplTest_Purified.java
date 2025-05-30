package org.asteriskjava.live.internal;

import org.asteriskjava.live.AgentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AsteriskAgentImplTest_Purified {

    private AsteriskAgentImpl agent;

    private int numberOfChanges;

    @BeforeEach
    void setUp() {
        AsteriskServerImpl server = new AsteriskServerImpl();
        agent = new AsteriskAgentImpl(server, "Testagent", "Agent/999", AgentState.AGENT_IDLE);
        numberOfChanges = 0;
    }

    @Test
    void testUpdateStatus_1() {
        assertEquals(AgentState.AGENT_IDLE, agent.getState());
    }

    @Test
    void testUpdateStatus_2_testMerged_2() {
        assertEquals("state", evt.getPropertyName(), "wrong propertyName");
        assertEquals(AgentState.AGENT_IDLE, evt.getOldValue(), "wrong oldValue");
        assertEquals(AgentState.AGENT_RINGING, evt.getNewValue(), "wrong newValue");
        assertEquals(agent, evt.getSource(), "wrong queue");
        numberOfChanges++;
        agent.updateState(AgentState.AGENT_RINGING);
        assertEquals(1, numberOfChanges, "wrong number of propagated changes");
    }
}
