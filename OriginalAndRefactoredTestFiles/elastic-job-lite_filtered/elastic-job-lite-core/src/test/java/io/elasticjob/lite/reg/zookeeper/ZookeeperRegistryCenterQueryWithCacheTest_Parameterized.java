package io.elasticjob.lite.reg.zookeeper;

import io.elasticjob.lite.fixture.EmbedTestingServer;
import io.elasticjob.lite.reg.zookeeper.util.ZookeeperRegistryCenterTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public final class ZookeeperRegistryCenterQueryWithCacheTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_assertGetFromCache_1to2")
    public void assertGetFromCache_1to2(String param1, String param2) {
        assertThat(zkRegCenter.get(param1), is(param2));
    }

    static public Stream<Arguments> Provider_assertGetFromCache_1to2() {
        return Stream.of(arguments("/test", "test"), arguments("/test/deep/nested", "deepNested"));
    }
}
