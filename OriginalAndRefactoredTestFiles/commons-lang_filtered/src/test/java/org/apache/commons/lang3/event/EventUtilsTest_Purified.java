package org.apache.commons.lang3.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.naming.event.ObjectChangeListener;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class EventUtilsTest_Purified extends AbstractLangTest {

    public static class EventCounter {

        private int count;

        public void eventOccurred() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    public static class EventCounterWithEvent {

        private int count;

        public void eventOccurred(final PropertyChangeEvent e) {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    private static final class EventCountingInvocationHandler implements InvocationHandler {

        private final Map<String, Integer> eventCounts = new TreeMap<>();

        public <L> L createListener(final Class<L> listenerType) {
            return listenerType.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { listenerType }, this));
        }

        public int getEventCount(final String eventName) {
            final Integer count = eventCounts.get(eventName);
            return count == null ? 0 : count.intValue();
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) {
            final Integer count = eventCounts.get(method.getName());
            if (count == null) {
                eventCounts.put(method.getName(), Integer.valueOf(1));
            } else {
                eventCounts.put(method.getName(), Integer.valueOf(count.intValue() + 1));
            }
            return null;
        }
    }

    public static class ExceptionEventSource {

        public void addPropertyChangeListener(final PropertyChangeListener listener) {
            throw new RuntimeException();
        }
    }

    public interface MultipleEventListener {

        void event1(PropertyChangeEvent e);

        void event2(PropertyChangeEvent e);
    }

    public static class MultipleEventSource {

        private final EventListenerSupport<MultipleEventListener> listeners = EventListenerSupport.create(MultipleEventListener.class);

        public void addMultipleEventListener(final MultipleEventListener listener) {
            listeners.addListener(listener);
        }
    }

    public static class PropertyChangeSource {

        private final EventListenerSupport<PropertyChangeListener> listeners = EventListenerSupport.create(PropertyChangeListener.class);

        private String property;

        public void addPropertyChangeListener(final PropertyChangeListener listener) {
            listeners.addListener(listener);
        }

        protected void addVetoableChangeListener(final VetoableChangeListener listener) {
        }

        public void removePropertyChangeListener(final PropertyChangeListener listener) {
            listeners.removeListener(listener);
        }

        public void setProperty(final String property) {
            final String oldValue = this.property;
            this.property = property;
            listeners.fire().propertyChange(new PropertyChangeEvent(this, "property", oldValue, property));
        }
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new EventUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = EventUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(EventUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(EventUtils.class.getModifiers()));
    }
}
