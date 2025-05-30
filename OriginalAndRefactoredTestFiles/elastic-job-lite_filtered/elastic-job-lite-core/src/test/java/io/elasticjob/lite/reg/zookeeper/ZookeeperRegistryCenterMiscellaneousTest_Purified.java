package io.elasticjob.lite.reg.zookeeper;

import io.elasticjob.lite.fixture.EmbedTestingServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class ZookeeperRegistryCenterMiscellaneousTest_Purified {

    private static final ZookeeperConfiguration ZOOKEEPER_CONFIGURATION = new ZookeeperConfiguration(EmbedTestingServer.getConnectionString(), ZookeeperRegistryCenterMiscellaneousTest.class.getName());

    private static ZookeeperRegistryCenter zkRegCenter;

    @BeforeClass
    public static void setUp() {
        EmbedTestingServer.start();
        ZOOKEEPER_CONFIGURATION.setConnectionTimeoutMilliseconds(30000);
        zkRegCenter = new ZookeeperRegistryCenter(ZOOKEEPER_CONFIGURATION);
        zkRegCenter.init();
        zkRegCenter.addCacheData("/test");
    }

    @AfterClass
    public static void tearDown() {
        zkRegCenter.close();
    }

    @Test
    public void assertGetRawClient_1() {
        assertThat(zkRegCenter.getRawClient(), instanceOf(CuratorFramework.class));
    }

    @Test
    public void assertGetRawClient_2() {
        assertThat(((CuratorFramework) zkRegCenter.getRawClient()).getNamespace(), is(ZookeeperRegistryCenterMiscellaneousTest.class.getName()));
    }
}
