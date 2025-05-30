package org.apache.druid.data.input.kafkainput;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.data.input.kafka.KafkaRecordEntity;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Pair;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Assert;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class KafkaStringHeaderFormatTest_Purified {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final KafkaStringHeaderFormat KAFKAHEADERNOENCODE = new KafkaStringHeaderFormat(null);

    private static final Iterable<Header> SAMPLE_HEADERS = ImmutableList.of(new Header() {

        @Override
        public String key() {
            return "encoding";
        }

        @Override
        public byte[] value() {
            return "application/json".getBytes(StandardCharsets.UTF_8);
        }
    }, new Header() {

        @Override
        public String key() {
            return "kafkapkc";
        }

        @Override
        public byte[] value() {
            return "pkc-bar".getBytes(StandardCharsets.UTF_8);
        }
    });

    private KafkaRecordEntity inputEntity;

    private final long timestamp = DateTimes.of("2021-06-24T00:00:00.000Z").getMillis();

    @Test
    public void testSerde_1() throws JsonProcessingException {
        Assert.assertEquals(KAFKAHEADERNOENCODE, MAPPER.readValue(MAPPER.writeValueAsString(KAFKAHEADERNOENCODE), KafkaStringHeaderFormat.class));
    }

    @Test
    public void testSerde_2() throws JsonProcessingException {
        final KafkaStringHeaderFormat kafkaAsciiHeader = new KafkaStringHeaderFormat("US-ASCII");
        Assert.assertNotEquals(KAFKAHEADERNOENCODE, kafkaAsciiHeader);
    }
}
