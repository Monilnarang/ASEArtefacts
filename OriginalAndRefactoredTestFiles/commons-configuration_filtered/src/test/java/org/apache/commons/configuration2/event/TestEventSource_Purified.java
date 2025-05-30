package org.apache.commons.configuration2.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestEventSource_Purified {

    private static final class CountingEventSource extends BaseEventSource implements Cloneable {

        private int eventCount;

        private int errorCount;

        @Override
        protected ConfigurationErrorEvent createErrorEvent(final EventType<? extends ConfigurationErrorEvent> type, final EventType<?> opType, final String propName, final Object propValue, final Throwable ex) {
            errorCount++;
            return super.createErrorEvent(type, opType, propName, propValue, ex);
        }

        @Override
        protected <T extends ConfigurationEvent> ConfigurationEvent createEvent(final EventType<T> eventType, final String propName, final Object propValue, final boolean before) {
            eventCount++;
            return super.createEvent(eventType, propName, propValue, before);
        }
    }

    private static final Object TEST_PROPVALUE = "a test property value";

    private static final String TEST_PROPNAME = "test.property.name";

    private CountingEventSource source;

    @BeforeEach
    public void setUp() throws Exception {
        source = new CountingEventSource();
    }

    @Test
    public void testInit_1() {
        assertTrue(source.getEventListenerRegistrations().isEmpty());
    }

    @Test
    public void testInit_2() {
        assertFalse(source.removeEventListener(ConfigurationEvent.ANY, new EventListenerTestImpl(null)));
    }

    @Test
    public void testInit_3() {
        assertFalse(source.isDetailEvents());
    }
}
