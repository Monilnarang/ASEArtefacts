package com.iluwatar.publish.subscribe.subscriber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.iluwatar.publish.subscribe.LoggerExtension;
import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import com.iluwatar.publish.subscribe.publisher.Publisher;
import com.iluwatar.publish.subscribe.publisher.PublisherImpl;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriberTest_Purified {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberTest.class);

    @RegisterExtension
    public LoggerExtension loggerExtension = new LoggerExtension();

    private static final String TOPIC_WEATHER = "WEATHER";

    private static final String TOPIC_TEMPERATURE = "TEMPERATURE";

    private static final String TOPIC_CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";

    private String getMessage(int subscriberHash) {
        Optional<String> message = loggerExtension.getFormattedMessages().stream().filter(str -> str.contains(String.valueOf(subscriberHash))).findFirst();
        assertTrue(message.isPresent());
        return message.get();
    }

    private void waitForOutput() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            logger.error("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void testMultipleSubscribersOnSameTopic_1() {
        assertEquals(2, loggerExtension.getFormattedMessages().size());
    }

    @Test
    void testMultipleSubscribersOnSameTopic_2() {
        Subscriber weatherSubscriber1 = new WeatherSubscriber();
        weatherTopic.addSubscriber(weatherSubscriber1);
        assertEquals("Weather Subscriber: " + weatherSubscriber1.hashCode() + " issued message: tornado", getMessage(weatherSubscriber1.hashCode()));
    }

    @Test
    void testMultipleSubscribersOnSameTopic_3() {
        Subscriber weatherSubscriber2 = new WeatherSubscriber();
        weatherTopic.addSubscriber(weatherSubscriber2);
        assertEquals("Weather Subscriber: " + weatherSubscriber2.hashCode() + " issued message: tornado", getMessage(weatherSubscriber2.hashCode()));
    }

    @Test
    void testMultipleSubscribersOnDifferentTopics_1() {
        assertEquals(2, loggerExtension.getFormattedMessages().size());
    }

    @Test
    void testMultipleSubscribersOnDifferentTopics_2() {
        Subscriber weatherSubscriber = new WeatherSubscriber();
        weatherTopic.addSubscriber(weatherSubscriber);
        assertEquals("Weather Subscriber: " + weatherSubscriber.hashCode() + " issued message: flood", getMessage(weatherSubscriber.hashCode()));
    }

    @Test
    void testMultipleSubscribersOnDifferentTopics_3() {
        Subscriber customerSupportSubscriber = new CustomerSupportSubscriber();
        customerSupportTopic.addSubscriber(customerSupportSubscriber);
        assertEquals("Customer Support Subscriber: " + customerSupportSubscriber.hashCode() + " sent the email to: support@test.at", getMessage(customerSupportSubscriber.hashCode()));
    }

    @Test
    void testInvalidContentOnTopics_1() {
        assertTrue(loggerExtension.getFormattedMessages().getFirst().contains("Unknown content type"));
    }

    @Test
    void testInvalidContentOnTopics_2() {
        assertTrue(loggerExtension.getFormattedMessages().get(1).contains("Unknown content type"));
    }

    @Test
    void testUnsubscribe_1() {
        assertEquals(1, loggerExtension.getFormattedMessages().size());
    }

    @Test
    void testUnsubscribe_2() {
        assertTrue(loggerExtension.getFormattedMessages().getFirst().contains("earthquake"));
    }

    @Test
    void testUnsubscribe_3() {
        assertFalse(loggerExtension.getFormattedMessages().getFirst().contains("tornado"));
    }
}
