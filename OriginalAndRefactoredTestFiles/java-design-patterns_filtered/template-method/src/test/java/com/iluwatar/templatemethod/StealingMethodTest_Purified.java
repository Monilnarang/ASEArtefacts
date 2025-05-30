package com.iluwatar.templatemethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

public abstract class StealingMethodTest_Purified<M extends StealingMethod> {

    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        appender = new InMemoryAppender();
    }

    @AfterEach
    void tearDown() {
        appender.stop();
    }

    private final M method;

    private final String expectedTarget;

    private final String expectedTargetResult;

    private final String expectedConfuseMethod;

    private final String expectedStealMethod;

    public StealingMethodTest(final M method, String expectedTarget, final String expectedTargetResult, final String expectedConfuseMethod, final String expectedStealMethod) {
        this.method = method;
        this.expectedTarget = expectedTarget;
        this.expectedTargetResult = expectedTargetResult;
        this.expectedConfuseMethod = expectedConfuseMethod;
        this.expectedStealMethod = expectedStealMethod;
    }

    private static class InMemoryAppender extends AppenderBase<ILoggingEvent> {

        private final List<ILoggingEvent> log = new LinkedList<>();

        public InMemoryAppender() {
            ((Logger) LoggerFactory.getLogger("root")).addAppender(this);
            start();
        }

        @Override
        protected void append(ILoggingEvent eventObject) {
            log.add(eventObject);
        }

        public int getLogSize() {
            return log.size();
        }

        public String getLastMessage() {
            return log.get(log.size() - 1).getFormattedMessage();
        }

        public boolean logContains(String message) {
            return log.stream().anyMatch(event -> event.getFormattedMessage().equals(message));
        }
    }

    @Test
    void testConfuseTarget_1() {
        assertEquals(0, appender.getLogSize());
    }

    @Test
    void testConfuseTarget_2() {
        assertEquals(this.expectedConfuseMethod, appender.getLastMessage());
    }

    @Test
    void testConfuseTarget_3() {
        assertEquals(1, appender.getLogSize());
    }

    @Test
    void testStealTheItem_1() {
        assertEquals(0, appender.getLogSize());
    }

    @Test
    void testStealTheItem_2() {
        assertEquals(this.expectedStealMethod, appender.getLastMessage());
    }

    @Test
    void testStealTheItem_3() {
        assertEquals(1, appender.getLogSize());
    }

    @Test
    void testSteal_1() {
        assertTrue(appender.logContains(this.expectedTargetResult));
    }

    @Test
    void testSteal_2() {
        assertTrue(appender.logContains(this.expectedConfuseMethod));
    }

    @Test
    void testSteal_3() {
        assertTrue(appender.logContains(this.expectedStealMethod));
    }

    @Test
    void testSteal_4() {
        assertEquals(3, appender.getLogSize());
    }
}
