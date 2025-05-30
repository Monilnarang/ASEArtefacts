package com.iluwatar.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class SimpleTrollTest_Purified {

    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        appender = new InMemoryAppender(SimpleTroll.class);
    }

    @AfterEach
    void tearDown() {
        appender.stop();
    }

    private static class InMemoryAppender extends AppenderBase<ILoggingEvent> {

        private final List<ILoggingEvent> log = new LinkedList<>();

        InMemoryAppender(Class clazz) {
            ((Logger) LoggerFactory.getLogger(clazz)).addAppender(this);
            start();
        }

        @Override
        protected void append(ILoggingEvent eventObject) {
            log.add(eventObject);
        }

        String getLastMessage() {
            return log.get(log.size() - 1).getMessage();
        }

        int getLogSize() {
            return log.size();
        }
    }

    @Test
    void testTrollActions_1() {
        final var troll = new SimpleTroll();
        assertEquals(10, troll.getAttackPower());
    }

    @Test
    void testTrollActions_2() {
        assertEquals("The troll tries to grab you!", appender.getLastMessage());
    }

    @Test
    void testTrollActions_3() {
        assertEquals("The troll shrieks in horror and runs away!", appender.getLastMessage());
    }

    @Test
    void testTrollActions_4() {
        assertEquals(2, appender.getLogSize());
    }
}
