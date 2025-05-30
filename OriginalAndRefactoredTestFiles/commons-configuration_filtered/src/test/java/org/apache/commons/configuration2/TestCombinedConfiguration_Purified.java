package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.configuration2.SynchronizerTestImpl.Methods;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConfigurationRuntimeException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.configuration2.sync.LockMode;
import org.apache.commons.configuration2.sync.ReadWriteSynchronizer;
import org.apache.commons.configuration2.sync.Synchronizer;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeCombiner;
import org.apache.commons.configuration2.tree.NodeModel;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.apache.commons.configuration2.tree.UnionCombiner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCombinedConfiguration_Purified {

    private static final class CombinedListener implements EventListener<ConfigurationEvent> {

        private int invalidateEvents;

        private int otherEvents;

        public void checkEvent(final int expectedInvalidate, final int expectedOthers) {
            assertEquals(expectedInvalidate, invalidateEvents);
            assertEquals(expectedOthers, otherEvents);
        }

        @Override
        public void onEvent(final ConfigurationEvent event) {
            if (event.getEventType() == CombinedConfiguration.COMBINED_INVALIDATE) {
                invalidateEvents++;
            } else {
                otherEvents++;
            }
        }
    }

    private static final class ReadThread extends Thread {

        private final Configuration config;

        private final CountDownLatch startLatch;

        private final AtomicInteger errorCount;

        private final int numberOfReads;

        public ReadThread(final Configuration readConfig, final CountDownLatch latch, final AtomicInteger errCnt, final int readCount) {
            config = readConfig;
            startLatch = latch;
            errorCount = errCnt;
            numberOfReads = readCount;
        }

        private void readConfiguration() {
            final List<Object> values = config.getList(KEY_CONCURRENT);
            if (values.size() < 1 || values.size() > 2) {
                errorCount.incrementAndGet();
            } else {
                boolean ok = true;
                for (final Object value : values) {
                    if (!TEST_NAME.equals(value)) {
                        ok = false;
                    }
                }
                if (!ok) {
                    errorCount.incrementAndGet();
                }
            }
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                for (int i = 0; i < numberOfReads; i++) {
                    readConfiguration();
                }
            } catch (final Exception e) {
                errorCount.incrementAndGet();
            }
        }
    }

    private static final class WriteThread extends Thread {

        private final List<Configuration> testConfigs;

        private final CountDownLatch startLatch;

        private final AtomicInteger errorCount;

        private final int numberOfWrites;

        private int currentChildConfigIdx;

        public WriteThread(final CombinedConfiguration cc, final CountDownLatch latch, final AtomicInteger errCnt, final int writeCount) {
            testConfigs = cc.getConfigurations();
            startLatch = latch;
            errorCount = errCnt;
            numberOfWrites = writeCount;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                for (int i = 0; i < numberOfWrites; i++) {
                    updateConfigurations();
                }
            } catch (final InterruptedException e) {
                errorCount.incrementAndGet();
            }
        }

        private void updateConfigurations() {
            final int newIdx = (currentChildConfigIdx + 1) % testConfigs.size();
            testConfigs.get(newIdx).addProperty(KEY_CONCURRENT, TEST_NAME);
            testConfigs.get(currentChildConfigIdx).clearProperty(KEY_CONCURRENT);
            currentChildConfigIdx = newIdx;
        }
    }

    private static final String TEST_NAME = "SUBCONFIG";

    private static final String TEST_KEY = "test.value";

    private static final String KEY_CONCURRENT = "concurrent.access.test";

    private static final String CHILD1 = TEST_NAME + "1";

    private static final String CHILD2 = TEST_NAME + "2";

    private static final String SUB_KEY = "test.sub.config";

    private static AbstractConfiguration setUpTestConfiguration() {
        final BaseHierarchicalConfiguration config = new BaseHierarchicalConfiguration();
        config.addProperty(TEST_KEY, Boolean.TRUE);
        config.addProperty("test.comment", "This is a test");
        return config;
    }

    private CombinedConfiguration config;

    private CombinedListener listener;

    private void checkAddConfig(final AbstractConfiguration c) {
        final Collection<EventListener<? super ConfigurationEvent>> listeners = c.getEventListeners(ConfigurationEvent.ANY);
        assertEquals(1, listeners.size());
        assertTrue(listeners.contains(config));
    }

    private void checkCombinedRootNotConstructed() {
        assertTrue(config.getModel().getNodeHandler().getRootNode().getChildren().isEmpty());
    }

    private void checkConfigurationsAt(final boolean withUpdates) {
        setUpSubConfigTest();
        final List<HierarchicalConfiguration<ImmutableNode>> subs = config.configurationsAt(SUB_KEY, withUpdates);
        assertEquals(1, subs.size());
        assertTrue(subs.get(0).getBoolean(TEST_KEY));
    }

    private void checkRemoveConfig(final AbstractConfiguration c) {
        assertTrue(c.getEventListeners(ConfigurationEvent.ANY).isEmpty());
        assertEquals(0, config.getNumberOfConfigurations());
        assertTrue(config.getConfigurationNames().isEmpty());
        listener.checkEvent(2, 0);
    }

    @BeforeEach
    public void setUp() throws Exception {
        config = new CombinedConfiguration();
        listener = new CombinedListener();
        config.addEventListener(ConfigurationEvent.ANY, listener);
    }

    private void setUpSourceTest() {
        final BaseHierarchicalConfiguration c1 = new BaseHierarchicalConfiguration();
        final PropertiesConfiguration c2 = new PropertiesConfiguration();
        c1.addProperty(TEST_KEY, TEST_NAME);
        c2.addProperty("another.key", "test");
        config.addConfiguration(c1, CHILD1);
        config.addConfiguration(c2, CHILD2);
    }

    private AbstractConfiguration setUpSubConfigTest() {
        final AbstractConfiguration srcConfig = setUpTestConfiguration();
        config.addConfiguration(srcConfig, "source", SUB_KEY);
        config.addConfiguration(setUpTestConfiguration());
        config.addConfiguration(setUpTestConfiguration(), "otherTest", "other.prefix");
        return srcConfig;
    }

    private SynchronizerTestImpl setUpSynchronizerTest() {
        setUpSourceTest();
        final SynchronizerTestImpl sync = new SynchronizerTestImpl();
        config.setSynchronizer(sync);
        return sync;
    }

    @Test
    public void testAccessPropertyEmpty_1() {
        assertFalse(config.containsKey(TEST_KEY));
    }

    @Test
    public void testAccessPropertyEmpty_2() {
        assertNull(config.getString("test.comment"));
    }

    @Test
    public void testAccessPropertyEmpty_3() {
        assertTrue(config.isEmpty());
    }

    @Test
    public void testInit_1() {
        assertEquals(0, config.getNumberOfConfigurations());
    }

    @Test
    public void testInit_2() {
        assertTrue(config.getConfigurationNames().isEmpty());
    }

    @Test
    public void testInit_3() {
        assertInstanceOf(UnionCombiner.class, config.getNodeCombiner());
    }

    @Test
    public void testInit_4() {
        assertNull(config.getConfiguration(TEST_NAME));
    }
}
