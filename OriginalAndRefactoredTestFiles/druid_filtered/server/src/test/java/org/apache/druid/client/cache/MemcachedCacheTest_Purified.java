package org.apache.druid.client.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import net.spy.memcached.BroadcastOpFactory;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.CachedData;
import net.spy.memcached.ClientMode;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.MemcachedClientIF;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.NodeLocator;
import net.spy.memcached.internal.BulkFuture;
import net.spy.memcached.internal.BulkGetCompletionListener;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.transcoders.SerializingTranscoder;
import net.spy.memcached.transcoders.Transcoder;
import org.apache.druid.collections.StupidResourceHolder;
import org.apache.druid.guice.GuiceInjectors;
import org.apache.druid.guice.JsonConfigProvider;
import org.apache.druid.guice.ManageLifecycle;
import org.apache.druid.initialization.Initialization;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.lifecycle.Lifecycle;
import org.apache.druid.java.util.common.logger.Logger;
import org.apache.druid.java.util.emitter.core.Event;
import org.apache.druid.java.util.emitter.service.ServiceEmitter;
import org.apache.druid.java.util.metrics.AbstractMonitor;
import org.apache.druid.java.util.metrics.StubServiceEmitter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MemcachedCacheTest_Purified extends CacheTestBase<MemcachedCache> {

    private static final Logger log = new Logger(MemcachedCacheTest.class);

    private static final byte[] HI = StringUtils.toUtf8("hiiiiiiiiiiiiiiiiiii");

    private static final byte[] HO = StringUtils.toUtf8("hooooooooooooooooooo");

    protected static final AbstractMonitor NOOP_MONITOR = new AbstractMonitor() {

        @Override
        public boolean doMonitor(ServiceEmitter emitter) {
            return false;
        }
    };

    private final MemcachedCacheConfig memcachedCacheConfig = new MemcachedCacheConfig() {

        @Override
        public String getMemcachedPrefix() {
            return "druid-memcached-test";
        }

        @Override
        public int getTimeout() {
            return 10;
        }

        @Override
        public int getExpiration() {
            return 3600;
        }

        @Override
        public String getHosts() {
            return "localhost:9999";
        }
    };

    @Before
    public void setUp() {
        cache = new MemcachedCache(Suppliers.ofInstance(StupidResourceHolder.create(new MockMemcachedClient())), memcachedCacheConfig, NOOP_MONITOR);
    }

    public void put(Cache cache, String namespace, byte[] key, Integer value) {
        cache.put(new Cache.NamedKey(namespace, key), Ints.toByteArray(value));
    }

    public int get(Cache cache, String namespace, byte[] key) {
        return Ints.fromByteArray(cache.get(new Cache.NamedKey(namespace, key)));
    }

    @Test
    public void testSanity_1() {
        Assert.assertNull(cache.get(new Cache.NamedKey("a", HI)));
    }

    @Test
    public void testSanity_2_testMerged_2() {
        put(cache, "a", HI, 1);
        Assert.assertEquals(1, get(cache, "a", HI));
        Assert.assertNull(cache.get(new Cache.NamedKey("the", HI)));
        put(cache, "the", HI, 2);
        Assert.assertEquals(2, get(cache, "the", HI));
        put(cache, "the", HO, 10);
        Assert.assertNull(cache.get(new Cache.NamedKey("a", HO)));
        Assert.assertEquals(10, get(cache, "the", HO));
    }
}
