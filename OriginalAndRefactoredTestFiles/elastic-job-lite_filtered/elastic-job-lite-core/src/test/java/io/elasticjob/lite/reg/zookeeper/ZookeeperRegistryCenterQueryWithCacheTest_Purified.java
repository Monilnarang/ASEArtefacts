package io.elasticjob.lite.reg.zookeeper;

import io.elasticjob.lite.fixture.EmbedTestingServer;
import io.elasticjob.lite.reg.zookeeper.util.ZookeeperRegistryCenterTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public final class ZookeeperRegistryCenterQueryWithCacheTest_Purified {

    private static final ZookeeperConfiguration ZOOKEEPER_CONFIGURATION = new ZookeeperConfiguration(EmbedTestingServer.getConnectionString(), ZookeeperRegistryCenterQueryWithCacheTest.class.getName());

    private static ZookeeperRegistryCenter zkRegCenter;

    @BeforeClass
    public static void setUp() {
        EmbedTestingServer.start();
        zkRegCenter = new ZookeeperRegistryCenter(ZOOKEEPER_CONFIGURATION);
        ZOOKEEPER_CONFIGURATION.setConnectionTimeoutMilliseconds(30000);
        zkRegCenter.init();
        ZookeeperRegistryCenterTestUtil.persist(zkRegCenter);
        zkRegCenter.addCacheData("/test");
    }

    @AfterClass
    public static void tearDown() {
        zkRegCenter.close();
    }

    @Test
    public void assertGetFromCache_1() {
        assertThat(zkRegCenter.get("/test"), is("test"));
    }

    @Test
    public void assertGetFromCache_2() {
        assertThat(zkRegCenter.get("/test/deep/nested"), is("deepNested"));
    }
}
