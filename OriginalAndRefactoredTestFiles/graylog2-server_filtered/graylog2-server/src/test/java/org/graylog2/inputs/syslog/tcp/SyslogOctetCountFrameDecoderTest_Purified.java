package org.graylog2.inputs.syslog.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderException;
import org.junit.Before;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SyslogOctetCountFrameDecoderTest_Purified {

    private EmbeddedChannel channel;

    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedChannel(new SyslogOctetCountFrameDecoder());
    }

    @Test
    public void testIncompleteFrameLengthValue_1_testMerged_1() throws Exception {
        final ByteBuf buf1 = Unpooled.copiedBuffer("12", StandardCharsets.US_ASCII);
        final ByteBuf buf2 = Unpooled.copiedBuffer("3 <45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", StandardCharsets.US_ASCII);
        assertFalse(channel.writeInbound(buf1));
        assertTrue(channel.writeInbound(buf2));
        final ByteBuf actual = channel.readInbound();
        assertEquals("<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", actual.toString(StandardCharsets.US_ASCII));
    }

    @Test
    public void testIncompleteFrameLengthValue_2() throws Exception {
        assertNull(channel.readInbound());
    }

    @Test
    public void testIncompleteFrames_1_testMerged_1() throws Exception {
        final ByteBuf buf1 = Unpooled.copiedBuffer("123 <45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - ", StandardCharsets.US_ASCII);
        final ByteBuf buf2 = Unpooled.copiedBuffer("[meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", StandardCharsets.US_ASCII);
        assertFalse(channel.writeInbound(buf1));
        assertTrue(channel.writeInbound(buf2));
        final ByteBuf actual = channel.readInbound();
        assertEquals("<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", actual.toString(StandardCharsets.US_ASCII));
    }

    @Test
    public void testIncompleteFrames_2() throws Exception {
        assertNull(channel.readInbound());
    }

    @Test
    public void testIncompleteByteBufByteBufFramesAndSmallBuffer_1_testMerged_1() throws Exception {
        final ByteBuf messagePart1 = Unpooled.copiedBuffer("123 <45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.", StandardCharsets.US_ASCII);
        final ByteBuf messagePart2 = Unpooled.copiedBuffer("3'\n", StandardCharsets.US_ASCII);
        assertFalse(channel.writeInbound(messagePart1));
        assertTrue(channel.writeInbound(messagePart2));
        final ByteBuf actual = channel.readInbound();
        assertEquals("<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", actual.toString(StandardCharsets.US_ASCII));
    }

    @Test
    public void testIncompleteByteBufByteBufFramesAndSmallBuffer_2() throws Exception {
        assertNull(channel.readInbound());
    }
}
