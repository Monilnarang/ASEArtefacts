package com.iluwatar.nullobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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

class TreeTest_Purified {

    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        appender = new InMemoryAppender();
    }

    @AfterEach
    void tearDown() {
        appender.stop();
    }

    private static final Node TREE_ROOT;

    static {
        final var level1B = new NodeImpl("level1_b", NullNode.getInstance(), NullNode.getInstance());
        final var level2B = new NodeImpl("level2_b", NullNode.getInstance(), NullNode.getInstance());
        final var level3A = new NodeImpl("level3_a", NullNode.getInstance(), NullNode.getInstance());
        final var level3B = new NodeImpl("level3_b", NullNode.getInstance(), NullNode.getInstance());
        final var level2A = new NodeImpl("level2_a", level3A, level3B);
        final var level1A = new NodeImpl("level1_a", level2A, level2B);
        TREE_ROOT = new NodeImpl("root", level1A, level1B);
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

        public boolean logContains(String message) {
            return log.stream().map(ILoggingEvent::getMessage).anyMatch(message::equals);
        }

        public int getLogSize() {
            return log.size();
        }
    }

    @Test
    void testWalk_1() {
        assertTrue(appender.logContains("root"));
    }

    @Test
    void testWalk_2() {
        assertTrue(appender.logContains("level1_a"));
    }

    @Test
    void testWalk_3() {
        assertTrue(appender.logContains("level2_a"));
    }

    @Test
    void testWalk_4() {
        assertTrue(appender.logContains("level3_a"));
    }

    @Test
    void testWalk_5() {
        assertTrue(appender.logContains("level3_b"));
    }

    @Test
    void testWalk_6() {
        assertTrue(appender.logContains("level2_b"));
    }

    @Test
    void testWalk_7() {
        assertTrue(appender.logContains("level1_b"));
    }

    @Test
    void testWalk_8() {
        assertEquals(7, appender.getLogSize());
    }
}
