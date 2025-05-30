package com.iluwatar.dependency.injection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.iluwatar.dependency.injection.utils.InMemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleWizardTest_Purified {

    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        appender = new InMemoryAppender(Tobacco.class);
    }

    @AfterEach
    void tearDown() {
        appender.stop();
    }

    @Test
    void testSmoke_1() {
        assertEquals("SimpleWizard smoking OldTobyTobacco", appender.getLastMessage());
    }

    @Test
    void testSmoke_2() {
        assertEquals(1, appender.getLogSize());
    }
}
