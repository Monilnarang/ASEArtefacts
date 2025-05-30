package io.elasticjob.lite.reg.zookeeper;

import io.elasticjob.lite.fixture.EmbedTestingServer;
import io.elasticjob.lite.reg.zookeeper.util.ZookeeperRegistryCenterTestUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class ZookeeperRegistryCenterModifyTest_Purified {

    private static final ZookeeperConfiguration ZOOKEEPER_CONFIGURATION = new ZookeeperConfiguration(EmbedTestingServer.getConnectionString(), ZookeeperRegistryCenterModifyTest.class.getName());

    private static ZookeeperRegistryCenter zkRegCenter;

    @BeforeClass
    public static void setUp() {
        EmbedTestingServer.start();
        zkRegCenter = new ZookeeperRegistryCenter(ZOOKEEPER_CONFIGURATION);
        ZOOKEEPER_CONFIGURATION.setConnectionTimeoutMilliseconds(30000);
        zkRegCenter.init();
        ZookeeperRegistryCenterTestUtil.persist(zkRegCenter);
    }

    @AfterClass
    public static void tearDown() {
        zkRegCenter.close();
    }

    @Test
    public void assertPersistEphemeral_1_testMerged_1() throws Exception {
        zkRegCenter.persist("/persist", "persist_value");
        zkRegCenter.persistEphemeral("/ephemeral", "ephemeral_value");
        assertThat(zkRegCenter.get("/persist"), is("persist_value"));
        assertThat(zkRegCenter.get("/ephemeral"), is("ephemeral_value"));
    }

    @Test
    public void assertPersistEphemeral_3_testMerged_2() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(EmbedTestingServer.getConnectionString(), new RetryOneTime(2000));
        client.start();
        client.blockUntilConnected();
        assertThat(client.getData().forPath("/" + ZookeeperRegistryCenterModifyTest.class.getName() + "/persist"), is("persist_value".getBytes()));
        assertNull(client.checkExists().forPath("/" + ZookeeperRegistryCenterModifyTest.class.getName() + "/ephemeral"));
    }
}
