package org.apache.hadoop.hdfs.server.federation.router;

import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.createMountTableEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.StateStoreDFSCluster;
import org.apache.hadoop.hdfs.server.federation.resolver.order.DestinationOrder;
import org.apache.hadoop.hdfs.web.WebHdfsFileSystem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRouterWebHdfsMethods_Purified {

    static final Logger LOG = LoggerFactory.getLogger(TestRouterWebHdfsMethods.class);

    private static StateStoreDFSCluster cluster;

    private static RouterContext router;

    private static String httpUri;

    @BeforeClass
    public static void globalSetUp() throws Exception {
        cluster = new StateStoreDFSCluster(false, 2);
        Configuration conf = new RouterConfigBuilder().stateStore().rpc().http().admin().build();
        cluster.addRouterOverrides(conf);
        cluster.setIndependentDNs();
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        router = cluster.getRandomRouter();
        httpUri = "http://" + router.getHttpAddress();
    }

    @AfterClass
    public static void tearDown() {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private String getUri(String path) {
        final String user = System.getProperty("user.name");
        final StringBuilder uri = new StringBuilder(httpUri);
        uri.append("/webhdfs/v1").append(path).append("?op=CREATE").append("&user.name=" + user);
        return uri.toString();
    }

    private void verifyFile(String ns, String path, boolean shouldExist) throws Exception {
        FileSystem fs = cluster.getNamenode(ns, null).getFileSystem();
        try {
            fs.getFileStatus(new Path(path));
            if (!shouldExist) {
                fail(path + " should not exist in ns " + ns);
            }
        } catch (FileNotFoundException e) {
            if (shouldExist) {
                fail(path + " should exist in ns " + ns);
            }
        }
    }

    private void assertResponse(String path) throws IOException {
        URL url = new URL(getUri(path));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, conn.getResponseCode());
        Map<?, ?> response = WebHdfsFileSystem.jsonParse(conn, true);
        assertEquals("InvalidPathException", ((LinkedHashMap) response.get("RemoteException")).get("exception"));
        conn.disconnect();
    }

    @Test
    public void testGetNsFromDataNodeNetworkLocation_1() {
        assertEquals("ns0", RouterWebHdfsMethods.getNsFromDataNodeNetworkLocation("/ns0/rack-info1"));
    }

    @Test
    public void testGetNsFromDataNodeNetworkLocation_2() {
        assertEquals("ns0", RouterWebHdfsMethods.getNsFromDataNodeNetworkLocation("/ns0/row1/rack-info1"));
    }

    @Test
    public void testGetNsFromDataNodeNetworkLocation_3() {
        assertEquals("", RouterWebHdfsMethods.getNsFromDataNodeNetworkLocation("/row0"));
    }

    @Test
    public void testGetNsFromDataNodeNetworkLocation_4() {
        assertEquals("", RouterWebHdfsMethods.getNsFromDataNodeNetworkLocation("whatever-rack-info1"));
    }
}
