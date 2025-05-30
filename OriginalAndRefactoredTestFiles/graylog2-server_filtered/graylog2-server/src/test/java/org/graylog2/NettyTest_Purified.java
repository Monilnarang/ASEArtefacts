package org.graylog2;

import io.netty.channel.epoll.Epoll;
import io.netty.channel.kqueue.KQueue;
import io.netty.handler.ssl.OpenSsl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NettyTest_Purified {

    @Test
    public void boringSslIsAvailable_1() {
        assertTrue(OpenSsl.isAvailable());
    }

    @Test
    public void boringSslIsAvailable_2() {
        assertEquals("BoringSSL", OpenSsl.versionString());
    }
}
