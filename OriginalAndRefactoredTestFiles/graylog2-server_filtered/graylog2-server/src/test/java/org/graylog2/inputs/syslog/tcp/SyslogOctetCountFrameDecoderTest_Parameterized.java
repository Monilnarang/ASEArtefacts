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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SyslogOctetCountFrameDecoderTest_Parameterized {

    private EmbeddedChannel channel;

    @Before
    public void setUp() throws Exception {
        channel = new EmbeddedChannel(new SyslogOctetCountFrameDecoder());
    }

    @Test
    public void testIncompleteFrameLengthValue_2() throws Exception {
        assertNull(channel.readInbound());
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

    @ParameterizedTest
    @MethodSource("Provider_testIncompleteFrameLengthValue_1_testMerged_1_1")
    public void testIncompleteFrameLengthValue_1_testMerged_1_1(int param1, String param2, String param3) throws Exception {
        final ByteBuf buf1 = Unpooled.copiedBuffer(param2, StandardCharsets.US_ASCII);
        final ByteBuf buf2 = Unpooled.copiedBuffer(param3, StandardCharsets.US_ASCII);
        assertFalse(channel.writeInbound(buf1));
        assertTrue(channel.writeInbound(buf2));
        final ByteBuf actual = channel.readInbound();
        assertEquals(param1, actual.toString(StandardCharsets.US_ASCII));
    }

    static public Stream<Arguments> Provider_testIncompleteFrameLengthValue_1_testMerged_1_1() {
        return Stream.of(arguments(12, "3 <45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", "<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n"), arguments("123 <45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - ", "[meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n", "<45>1 2014-10-21T10:21:09+00:00 c4dc57ba1ebb syslog-ng 7120 - [meta sequenceId=\"1\"] syslog-ng starting up; version='3.5.3'\n"));
    }
}
