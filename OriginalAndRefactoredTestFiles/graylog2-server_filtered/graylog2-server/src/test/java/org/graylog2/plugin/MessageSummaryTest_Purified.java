package org.graylog2.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MessageSummaryTest_Purified {

    public static final ImmutableList<String> STREAM_IDS = ImmutableList.of("stream1", "stream2");

    public static final String INDEX_NAME = "graylog2_3";

    private Message message;

    private MessageSummary messageSummary;

    private final MessageFactory messageFactory = new TestMessageFactory();

    @Before
    public void setUp() throws Exception {
        message = messageFactory.createMessage("message", "source", DateTime.now(DateTimeZone.UTC));
        message.addField("streams", STREAM_IDS);
        messageSummary = new MessageSummary(INDEX_NAME, message);
    }

    @Test
    public void testGetFields_1() throws Exception {
        assertEquals(new HashMap<String, Object>(), messageSummary.getFields());
    }

    @Test
    public void testGetFields_2() throws Exception {
        assertEquals(ImmutableMap.of("foo", "bar"), messageSummary.getFields());
    }

    @Test
    public void testHasField_1() throws Exception {
        assertFalse(messageSummary.hasField("foo"));
    }

    @Test
    public void testHasField_2() throws Exception {
        assertTrue(messageSummary.hasField("foo"));
    }

    @Test
    public void testGetField_1() throws Exception {
        assertNull(messageSummary.getField("foo"));
    }

    @Test
    public void testGetField_2() throws Exception {
        assertEquals("bar", messageSummary.getField("foo"));
    }
}
