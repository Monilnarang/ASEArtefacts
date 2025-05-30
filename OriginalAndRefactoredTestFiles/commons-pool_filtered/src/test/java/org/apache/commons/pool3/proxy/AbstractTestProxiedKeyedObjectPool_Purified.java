package org.apache.commons.pool3.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import org.apache.commons.pool3.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool3.KeyedObjectPool;
import org.apache.commons.pool3.KeyedPooledObjectFactory;
import org.apache.commons.pool3.PooledObject;
import org.apache.commons.pool3.impl.AbandonedConfig;
import org.apache.commons.pool3.impl.DefaultPooledObject;
import org.apache.commons.pool3.impl.GenericKeyedObjectPool;
import org.apache.commons.pool3.impl.GenericKeyedObjectPoolConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractTestProxiedKeyedObjectPool_Purified {

    private static final class TestKeyedObjectFactory extends BaseKeyedPooledObjectFactory<String, TestObject, RuntimeException> {

        @Override
        public TestObject create(final String key) {
            return new TestObjectImpl();
        }

        @Override
        public PooledObject<TestObject> wrap(final TestObject value) {
            return new DefaultPooledObject<>(value);
        }
    }

    protected interface TestObject {

        String getData();

        void setData(String data);
    }

    private static final class TestObjectImpl implements TestObject {

        private String data;

        @Override
        public String getData() {
            return data;
        }

        @Override
        public void setData(final String data) {
            this.data = data;
        }
    }

    private static final String KEY1 = "key1";

    private static final String DATA1 = "data1";

    private static final Duration ABANDONED_TIMEOUT_SECS = Duration.ofSeconds(3);

    private KeyedObjectPool<String, TestObject, RuntimeException> pool;

    private StringWriter log;

    protected abstract ProxySource<TestObject> getProxySource();

    @BeforeEach
    public void setUp() {
        log = new StringWriter();
        final PrintWriter pw = new PrintWriter(log);
        final AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setLogAbandoned(true);
        abandonedConfig.setRemoveAbandonedOnBorrow(true);
        abandonedConfig.setUseUsageTracking(true);
        abandonedConfig.setRemoveAbandonedTimeout(ABANDONED_TIMEOUT_SECS);
        abandonedConfig.setLogWriter(pw);
        final GenericKeyedObjectPoolConfig<TestObject> config = new GenericKeyedObjectPoolConfig<>();
        config.setMaxTotal(3);
        final KeyedPooledObjectFactory<String, TestObject, RuntimeException> factory = new TestKeyedObjectFactory();
        @SuppressWarnings("resource")
        final KeyedObjectPool<String, TestObject, RuntimeException> innerPool = new GenericKeyedObjectPool<>(factory, config, abandonedConfig);
        pool = new ProxiedKeyedObjectPool<>(innerPool, getProxySource());
    }

    @Test
    public void testPassThroughMethods01_1() {
        assertEquals(0, pool.getNumActive());
    }

    @Test
    public void testPassThroughMethods01_2() {
        assertEquals(0, pool.getNumIdle());
    }

    @Test
    public void testPassThroughMethods01_3() {
        assertEquals(0, pool.getNumActive());
    }

    @Test
    public void testPassThroughMethods01_4() {
        pool.addObject(KEY1);
        assertEquals(1, pool.getNumIdle());
    }

    @Test
    public void testPassThroughMethods01_5() {
        assertEquals(0, pool.getNumActive());
    }

    @Test
    public void testPassThroughMethods01_6() {
        assertEquals(0, pool.getNumIdle());
    }
}
