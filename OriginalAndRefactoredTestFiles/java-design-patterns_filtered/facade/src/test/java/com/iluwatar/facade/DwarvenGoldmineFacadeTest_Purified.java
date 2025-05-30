package com.iluwatar.facade;

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

class DwarvenGoldmineFacadeTest_Purified {

    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        appender = new InMemoryAppender();
    }

    @AfterEach
    void tearDown() {
        appender.stop();
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

        public boolean logContains(String message) {
            return log.stream().map(ILoggingEvent::getFormattedMessage).anyMatch(message::equals);
        }
    }

    @Test
    void testFullWorkDay_1() {
        assertTrue(appender.logContains("Dwarf gold digger wakes up."));
    }

    @Test
    void testFullWorkDay_2() {
        assertTrue(appender.logContains("Dwarf cart operator wakes up."));
    }

    @Test
    void testFullWorkDay_3() {
        assertTrue(appender.logContains("Dwarven tunnel digger wakes up."));
    }

    @Test
    void testFullWorkDay_4() {
        assertTrue(appender.logContains("Dwarf gold digger goes to the mine."));
    }

    @Test
    void testFullWorkDay_5() {
        assertTrue(appender.logContains("Dwarf cart operator goes to the mine."));
    }

    @Test
    void testFullWorkDay_6() {
        assertTrue(appender.logContains("Dwarven tunnel digger goes to the mine."));
    }

    @Test
    void testFullWorkDay_7() {
        assertEquals(6, appender.getLogSize());
    }

    @Test
    void testFullWorkDay_8() {
        assertTrue(appender.logContains("Dwarf gold digger digs for gold."));
    }

    @Test
    void testFullWorkDay_9() {
        assertTrue(appender.logContains("Dwarf cart operator moves gold chunks out of the mine."));
    }

    @Test
    void testFullWorkDay_10() {
        assertTrue(appender.logContains("Dwarven tunnel digger creates another promising tunnel."));
    }

    @Test
    void testFullWorkDay_11() {
        assertEquals(9, appender.getLogSize());
    }

    @Test
    void testFullWorkDay_12() {
        assertTrue(appender.logContains("Dwarf gold digger goes home."));
    }

    @Test
    void testFullWorkDay_13() {
        assertTrue(appender.logContains("Dwarf cart operator goes home."));
    }

    @Test
    void testFullWorkDay_14() {
        assertTrue(appender.logContains("Dwarven tunnel digger goes home."));
    }

    @Test
    void testFullWorkDay_15() {
        assertTrue(appender.logContains("Dwarf gold digger goes to sleep."));
    }

    @Test
    void testFullWorkDay_16() {
        assertTrue(appender.logContains("Dwarf cart operator goes to sleep."));
    }

    @Test
    void testFullWorkDay_17() {
        assertTrue(appender.logContains("Dwarven tunnel digger goes to sleep."));
    }

    @Test
    void testFullWorkDay_18() {
        assertEquals(15, appender.getLogSize());
    }
}
