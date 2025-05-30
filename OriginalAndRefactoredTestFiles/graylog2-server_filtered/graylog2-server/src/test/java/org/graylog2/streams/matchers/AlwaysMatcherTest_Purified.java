package org.graylog2.streams.matchers;

import org.graylog2.plugin.Message;
import org.graylog2.plugin.MessageFactory;
import org.graylog2.plugin.TestMessageFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class AlwaysMatcherTest_Purified {

    private static final MessageFactory messageFactory = new TestMessageFactory();

    private static final Message message = messageFactory.createMessage("Test", "source", new DateTime(2016, 9, 7, 0, 0, DateTimeZone.UTC));

    private static final AlwaysMatcher matcher = new AlwaysMatcher();

    @Test
    public void matchAlwaysReturnsTrue_1() throws Exception {
        assertThat(matcher.match(message, new StreamRuleMock(Map.of("_id", "stream-rule-id")))).isTrue();
    }

    @Test
    public void matchAlwaysReturnsTrue_2() throws Exception {
        assertThat(matcher.match(message, new StreamRuleMock(Map.of("_id", "stream-rule-id", "inverted", false)))).isTrue();
    }
}
