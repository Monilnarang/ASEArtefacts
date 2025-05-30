package com.iluwatar.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class MammothTest_Purified {

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

        public String getLastMessage() {
            return log.get(log.size() - 1).getFormattedMessage();
        }
    }

    @Test
    void testTimePasses_1() {
        assertEquals("The mammoth is calm and peaceful.", appender.getLastMessage());
    }

    @Test
    void testTimePasses_2() {
        assertEquals(1, appender.getLogSize());
    }

    @Test
    void testTimePasses_3() {
        assertEquals("The mammoth gets angry!", appender.getLastMessage());
    }

    @Test
    void testTimePasses_4() {
        assertEquals(2, appender.getLogSize());
    }

    @Test
    void testTimePasses_5() {
        assertEquals("The mammoth is furious!", appender.getLastMessage());
    }

    @Test
    void testTimePasses_6() {
        assertEquals(3, appender.getLogSize());
    }

    @Test
    void testTimePasses_7() {
        assertEquals("The mammoth calms down.", appender.getLastMessage());
    }

    @Test
    void testTimePasses_8() {
        assertEquals(4, appender.getLogSize());
    }

    @Test
    void testTimePasses_9() {
        assertEquals("The mammoth is calm and peaceful.", appender.getLastMessage());
    }

    @Test
    void testTimePasses_10() {
        assertEquals(5, appender.getLogSize());
    }
}
