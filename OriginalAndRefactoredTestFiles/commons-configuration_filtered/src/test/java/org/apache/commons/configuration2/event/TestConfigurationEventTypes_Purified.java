package org.apache.commons.configuration2.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TestConfigurationEventTypes_Purified {

    private void checkErrorEvent(final EventType<ConfigurationErrorEvent> type) {
        assertSame(ConfigurationErrorEvent.ANY, type.getSuperType(), "Wrong super type for " + type);
    }

    private void checkHierarchicalEvent(final EventType<ConfigurationEvent> eventType) {
        assertSame(ConfigurationEvent.ANY_HIERARCHICAL, eventType.getSuperType(), "Wrong super type for " + eventType);
    }

    private void checkUpdateEvent(final EventType<ConfigurationEvent> eventType) {
        assertSame(ConfigurationEvent.ANY, eventType.getSuperType(), "Wrong super type for " + eventType);
    }

    @Test
    public void testIsInstanceOfTrue_1() {
        assertTrue(EventType.isInstanceOf(ConfigurationEvent.ADD_NODES, ConfigurationEvent.ANY_HIERARCHICAL));
    }

    @Test
    public void testIsInstanceOfTrue_2() {
        assertTrue(EventType.isInstanceOf(ConfigurationEvent.ADD_NODES, ConfigurationEvent.ANY));
    }

    @Test
    public void testIsInstanceOfTrue_3() {
        assertTrue(EventType.isInstanceOf(ConfigurationEvent.ADD_NODES, Event.ANY));
    }

    @Test
    public void testIsInstanceOfTrue_4() {
        assertTrue(EventType.isInstanceOf(ConfigurationEvent.ADD_NODES, ConfigurationEvent.ADD_NODES));
    }
}
