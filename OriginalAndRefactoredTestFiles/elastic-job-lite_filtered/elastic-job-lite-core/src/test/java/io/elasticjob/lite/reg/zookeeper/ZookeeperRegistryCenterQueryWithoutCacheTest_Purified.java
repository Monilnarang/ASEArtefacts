package io.elasticjob.lite.reg.zookeeper;

import io.elasticjob.lite.fixture.EmbedTestingServer;
import io.elasticjob.lite.reg.zookeeper.util.ZookeeperRegistryCenterTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class ZookeeperRegistryCenterQueryWithoutCacheTest_Purified {

    private static final ZookeeperConfiguration ZOOKEEPER_CONFIGURATION = new ZookeeperConfiguration(EmbedTestingServer.getConnectionString(), ZookeeperRegistryCenterQueryWithoutCacheTest.class.getName());

    private static ZookeeperRegistryCenter zkRegCenter;

    @BeforeClass
    public static void setUp() {
        EmbedTestingServer.start();
        ZOOKEEPER_CONFIGURATION.setConnectionTimeoutMilliseconds(30000);
        zkRegCenter = new ZookeeperRegistryCenter(ZOOKEEPER_CONFIGURATION);
        zkRegCenter.init();
        ZookeeperRegistryCenterTestUtil.persist(zkRegCenter);
        zkRegCenter.addCacheData("/other");
    }

    @AfterClass
    public static void tearDown() {
        zkRegCenter.close();
    }

    @Test
    public void assertGetFromServer_1() {
        assertThat(zkRegCenter.get("/test"), is("test"));
    }

    @Test
    public void assertGetFromServer_2() {
        assertThat(zkRegCenter.get("/test/deep/nested"), is("deepNested"));
    }

    @Test
    public void assertGetChildrenKeys_1() {
        assertThat(zkRegCenter.getChildrenKeys("/test"), is(Arrays.asList("deep", "child")));
    }

    @Test
    public void assertGetChildrenKeys_2() {
        assertThat(zkRegCenter.getChildrenKeys("/test/deep"), is(Collections.singletonList("nested")));
    }

    @Test
    public void assertGetChildrenKeys_3() {
        assertThat(zkRegCenter.getChildrenKeys("/test/child"), is(Collections.<String>emptyList()));
    }

    @Test
    public void assertGetChildrenKeys_4() {
        assertThat(zkRegCenter.getChildrenKeys("/test/notExisted"), is(Collections.<String>emptyList()));
    }

    @Test
    public void assertGetNumChildren_1() {
        assertThat(zkRegCenter.getNumChildren("/test"), is(2));
    }

    @Test
    public void assertGetNumChildren_2() {
        assertThat(zkRegCenter.getNumChildren("/test/deep"), is(1));
    }

    @Test
    public void assertGetNumChildren_3() {
        assertThat(zkRegCenter.getNumChildren("/test/child"), is(0));
    }

    @Test
    public void assertGetNumChildren_4() {
        assertThat(zkRegCenter.getNumChildren("/test/notExisted"), is(0));
    }

    @Test
    public void assertIsExisted_1() {
        assertTrue(zkRegCenter.isExisted("/test"));
    }

    @Test
    public void assertIsExisted_2() {
        assertTrue(zkRegCenter.isExisted("/test/deep/nested"));
    }

    @Test
    public void assertIsExisted_3() {
        assertFalse(zkRegCenter.isExisted("/notExisted"));
    }
}
