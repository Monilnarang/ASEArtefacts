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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public final class ZookeeperRegistryCenterQueryWithoutCacheTest_Parameterized {

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
    public void assertGetChildrenKeys_1() {
        assertThat(zkRegCenter.getChildrenKeys("/test"), is(Arrays.asList("deep", "child")));
    }

    @Test
    public void assertGetChildrenKeys_2() {
        assertThat(zkRegCenter.getChildrenKeys("/test/deep"), is(Collections.singletonList("nested")));
    }

    @Test
    public void assertIsExisted_3() {
        assertFalse(zkRegCenter.isExisted("/notExisted"));
    }

    @ParameterizedTest
    @MethodSource("Provider_assertGetFromServer_1to2")
    public void assertGetFromServer_1to2(String param1, String param2) {
        assertThat(zkRegCenter.get(param1), is(param2));
    }

    static public Stream<Arguments> Provider_assertGetFromServer_1to2() {
        return Stream.of(arguments("/test", "test"), arguments("/test/deep/nested", "deepNested"));
    }

    @ParameterizedTest
    @MethodSource("Provider_assertGetChildrenKeys_3to4")
    public void assertGetChildrenKeys_3to4(String param1) {
        assertThat(zkRegCenter.getChildrenKeys(param1), is(Collections.<String>emptyList()));
    }

    static public Stream<Arguments> Provider_assertGetChildrenKeys_3to4() {
        return Stream.of(arguments("/test/child"), arguments("/test/notExisted"));
    }

    @ParameterizedTest
    @MethodSource("Provider_assertGetNumChildren_1to4")
    public void assertGetNumChildren_1to4(String param1, int param2) {
        assertThat(zkRegCenter.getNumChildren(param1), is(param2));
    }

    static public Stream<Arguments> Provider_assertGetNumChildren_1to4() {
        return Stream.of(arguments("/test", 2), arguments("/test/deep", 1), arguments("/test/child", 0), arguments("/test/notExisted", 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_assertIsExisted_1to2")
    public void assertIsExisted_1to2(String param1) {
        assertTrue(zkRegCenter.isExisted(param1));
    }

    static public Stream<Arguments> Provider_assertIsExisted_1to2() {
        return Stream.of(arguments("/test"), arguments("/test/deep/nested"));
    }
}
