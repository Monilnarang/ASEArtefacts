package org.jivesoftware.openfire;

import org.junit.jupiter.api.Test;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfflineMessageStoreTest_Purified {

    @Test
    public void shouldStoreNormalMessages_1() {
        Message message = new Message();
        message.setType(Message.Type.normal);
        assertTrue(OfflineMessageStore.shouldStoreMessage(message));
    }

    @Test
    public void shouldStoreNormalMessages_2() {
        Message message2 = new Message();
        assertTrue(OfflineMessageStore.shouldStoreMessage(message2));
    }
}
