package org.apache.seata.core.protocol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeartbeatMessageTest_Purified {

    @Test
    void getTypeCode_1() {
        Assertions.assertEquals(MessageType.TYPE_HEARTBEAT_MSG, HeartbeatMessage.PING.getTypeCode());
    }

    @Test
    void getTypeCode_2() {
        Assertions.assertEquals(MessageType.TYPE_HEARTBEAT_MSG, HeartbeatMessage.PONG.getTypeCode());
    }

    @Test
    void testToString_1() {
        Assertions.assertEquals("services ping", HeartbeatMessage.PING.toString());
    }

    @Test
    void testToString_2() {
        Assertions.assertEquals("services pong", HeartbeatMessage.PONG.toString());
    }
}
