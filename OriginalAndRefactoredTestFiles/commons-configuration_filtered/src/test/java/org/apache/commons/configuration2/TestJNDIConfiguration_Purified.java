package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.commons.configuration2.event.ConfigurationErrorEvent;
import org.apache.commons.configuration2.event.ErrorListenerTestImpl;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.event.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJNDIConfiguration_Purified {

    public static class PotentialErrorJNDIConfiguration extends JNDIConfiguration {

        private NamingException exception;

        public PotentialErrorJNDIConfiguration(final Context ctx) {
            super(ctx);
        }

        @Override
        public Context getBaseContext() throws NamingException {
            if (exception != null) {
                throw exception;
            }
            return super.getBaseContext();
        }

        public void installException() {
            installException(new NamingException("Simulated JNDI exception!"));
        }

        public void installException(final NamingException nex) {
            exception = nex;
        }
    }

    public static final String CONTEXT_FACTORY = MockInitialContextFactory.class.getName();

    private PotentialErrorJNDIConfiguration conf;

    private NonStringTestHolder nonStringTestHolder;

    private ErrorListenerTestImpl listener;

    private void checkErrorListener(final EventType<? extends ConfigurationErrorEvent> type, final EventType<?> opEventType, final String propName, final Object propValue) {
        final Throwable exception = listener.checkEvent(type, opEventType, propName, propValue);
        assertInstanceOf(NamingException.class, exception);
        listener = null;
    }

    @BeforeEach
    public void setUp() throws Exception {
        System.setProperty("java.naming.factory.initial", CONTEXT_FACTORY);
        final Properties props = new Properties();
        props.put("java.naming.factory.initial", CONTEXT_FACTORY);
        final Context ctx = new InitialContext(props);
        conf = new PotentialErrorJNDIConfiguration(ctx);
        nonStringTestHolder = new NonStringTestHolder();
        nonStringTestHolder.setConfiguration(conf);
        listener = new ErrorListenerTestImpl(conf);
        conf.addEventListener(ConfigurationErrorEvent.ANY, listener);
    }

    private PotentialErrorJNDIConfiguration setUpErrorConfig() {
        conf.installException();
        final Iterator<EventListener<? super ConfigurationErrorEvent>> iterator = conf.getEventListeners(ConfigurationErrorEvent.ANY).iterator();
        conf.removeEventListener(ConfigurationErrorEvent.ANY, iterator.next());
        return conf;
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (listener != null) {
            listener.done();
        }
    }

    @Test
    public void testChangePrefix_1() {
        assertEquals("true", conf.getString("test.boolean"));
    }

    @Test
    public void testChangePrefix_2() {
        assertNull(conf.getString("boolean"));
    }

    @Test
    public void testChangePrefix_3_testMerged_3() {
        conf.setPrefix("test");
        assertNull(conf.getString("test.boolean"));
        assertEquals("true", conf.getString("boolean"));
    }

    @Test
    public void testResetRemovedProperties_1() throws Exception {
        assertEquals("true", conf.getString("test.boolean"));
    }

    @Test
    public void testResetRemovedProperties_2() throws Exception {
        conf.clearProperty("test.boolean");
        assertNull(conf.getString("test.boolean"));
    }

    @Test
    public void testResetRemovedProperties_3() throws Exception {
        assertEquals("true", conf.getString("test.boolean"));
    }
}
