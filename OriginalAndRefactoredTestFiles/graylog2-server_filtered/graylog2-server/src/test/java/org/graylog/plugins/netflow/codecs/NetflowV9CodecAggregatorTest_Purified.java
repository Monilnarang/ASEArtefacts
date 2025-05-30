package org.graylog.plugins.netflow.codecs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.pkts.Pcap;
import io.pkts.packet.UDPPacket;
import io.pkts.protocol.Protocol;
import org.graylog.plugins.netflow.flows.NetFlowFormatter;
import org.graylog.plugins.netflow.v9.NetFlowV9BaseRecord;
import org.graylog.plugins.netflow.v9.NetFlowV9FieldDef;
import org.graylog.plugins.netflow.v9.NetFlowV9FieldType;
import org.graylog.plugins.netflow.v9.NetFlowV9Packet;
import org.graylog.plugins.netflow.v9.NetFlowV9Record;
import org.graylog.plugins.netflow.v9.NetFlowV9Template;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.MessageFactory;
import org.graylog2.plugin.TestMessageFactory;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.codecs.CodecAggregator;
import org.graylog2.plugin.journal.RawMessage;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class NetflowV9CodecAggregatorTest_Purified {

    private NetFlowCodec codec;

    private NetflowV9CodecAggregator codecAggregator;

    private InetSocketAddress source;

    private final MessageFactory messageFactory = new TestMessageFactory();

    private final NetFlowFormatter netFlowFormatter = new NetFlowFormatter(messageFactory);

    public NetflowV9CodecAggregatorTest() throws IOException {
        source = new InetSocketAddress(InetAddress.getLocalHost(), 12345);
    }

    @Before
    public void setup() throws IOException {
        codecAggregator = new NetflowV9CodecAggregator();
        codec = new NetFlowCodec(Configuration.EMPTY_CONFIGURATION, codecAggregator, netFlowFormatter);
    }

    private RawMessage convertToRawMessage(CodecAggregator.Result result, SocketAddress remoteAddress) {
        final ByteBuf buffer = result.getMessage();
        assertThat(buffer).isNotNull();
        final byte[] payload = ByteBufUtil.getBytes(buffer);
        return new RawMessage(payload, (InetSocketAddress) remoteAddress);
    }

    private Collection<NetFlowV9Packet> parseNetflowPcapStream(String resourceName) throws IOException {
        final List<NetFlowV9Packet> allPackets = Lists.newArrayList();
        try (InputStream inputStream = Resources.getResource(resourceName).openStream()) {
            final Pcap pcap = Pcap.openStream(inputStream);
            pcap.loop(packet -> {
                if (packet.hasProtocol(Protocol.UDP)) {
                    final UDPPacket udp = (UDPPacket) packet.getPacket(Protocol.UDP);
                    final InetSocketAddress source = new InetSocketAddress(udp.getParentPacket().getSourceIP(), udp.getSourcePort());
                    final CodecAggregator.Result result = codecAggregator.addChunk(Unpooled.copiedBuffer(udp.getPayload().getArray()), source);
                    if (result.isValid() && result.getMessage() != null) {
                        final ByteBuf buffer = result.getMessage();
                        buffer.readByte();
                        allPackets.addAll(codec.decodeV9Packets(buffer));
                    }
                }
                return true;
            });
        }
        return allPackets;
    }

    private Collection<Message> decodePcapStream(String resourceName) throws IOException {
        final List<Message> allMessages = Lists.newArrayList();
        try (InputStream inputStream = Resources.getResource(resourceName).openStream()) {
            final Pcap pcap = Pcap.openStream(inputStream);
            pcap.loop(packet -> {
                if (packet.hasProtocol(Protocol.UDP)) {
                    final UDPPacket udp = (UDPPacket) packet.getPacket(Protocol.UDP);
                    final InetSocketAddress source = new InetSocketAddress(udp.getParentPacket().getSourceIP(), udp.getSourcePort());
                    final CodecAggregator.Result result = codecAggregator.addChunk(Unpooled.copiedBuffer(udp.getPayload().getArray()), source);
                    if (result.isValid() && result.getMessage() != null) {
                        final Collection<Message> c = codec.decodeMessages(convertToRawMessage(result, source));
                        if (c != null) {
                            allMessages.addAll(c);
                        }
                    }
                }
                return true;
            });
        }
        return allMessages;
    }

    private CodecAggregator.Result aggregateRawPacket(String resourceName) throws IOException {
        final byte[] bytes = Resources.toByteArray(Resources.getResource(resourceName));
        final ByteBuf channelBuffer = Unpooled.wrappedBuffer(bytes);
        return codecAggregator.addChunk(channelBuffer, source);
    }

    private Collection<Message> decodeResult(CodecAggregator.Result result) {
        if (result.getMessage() == null) {
            return Collections.emptyList();
        }
        return codec.decodeMessages(convertToRawMessage(result, source));
    }

    @Test
    public void decodeMessagesSuccessfullyDecodesNetFlowV9_1() throws Exception {
        final Collection<Message> messages1 = decodeResult(aggregateRawPacket("netflow-data/netflow-v9-2-1.dat"));
        assertThat(messages1).isEmpty();
    }

    @Test
    public void decodeMessagesSuccessfullyDecodesNetFlowV9_2_testMerged_2() throws Exception {
        final Collection<Message> messages2 = decodeResult(aggregateRawPacket("netflow-data/netflow-v9-2-2.dat"));
        final Collection<Message> messages3 = decodeResult(aggregateRawPacket("netflow-data/netflow-v9-2-3.dat"));
        final Message message2 = Iterables.getFirst(messages2, null);
        assertThat(message2).isNotNull();
        assertThat(message2.getMessage()).isEqualTo("NetFlowV9 [192.168.124.1]:3072 <> [239.255.255.250]:1900 proto:17 pkts:8 bytes:2818");
        assertThat(message2.getTimestamp()).isEqualTo(DateTime.parse("2013-05-21T07:51:49.000Z"));
        assertThat(message2.getSource()).isEqualTo(source.getAddress().getHostAddress());
        final Message message3 = Iterables.getFirst(messages3, null);
        assertThat(message3).isNotNull();
        assertThat(message3.getMessage()).isEqualTo("NetFlowV9 [192.168.124.20]:42444 <> [121.161.231.32]:9090 proto:17 pkts:2 bytes:348");
        assertThat(message3.getTimestamp()).isEqualTo(DateTime.parse("2013-05-21T07:52:43.000Z"));
        assertThat(message3.getSource()).isEqualTo(source.getAddress().getHostAddress());
    }
}
