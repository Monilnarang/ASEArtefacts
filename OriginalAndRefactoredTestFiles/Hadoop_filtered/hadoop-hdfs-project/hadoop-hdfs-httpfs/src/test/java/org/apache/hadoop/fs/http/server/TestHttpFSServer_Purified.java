package org.apache.hadoop.fs.http.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.protocol.BlockStoragePolicy;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.StoragePolicySatisfierMode;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.hdfs.protocol.SnapshotDiffReport;
import org.apache.hadoop.hdfs.protocol.SnapshottableDirectoryStatus;
import org.apache.hadoop.hdfs.protocol.SnapshotStatus;
import org.apache.hadoop.hdfs.protocol.SystemErasureCodingPolicies;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants;
import org.apache.hadoop.hdfs.web.JsonUtil;
import org.apache.hadoop.hdfs.web.JsonUtilClient;
import org.apache.hadoop.lib.service.FileSystemAccess;
import org.apache.hadoop.security.authentication.util.SignerSecretProvider;
import org.apache.hadoop.security.authentication.util.StringSignerSecretProviderCreator;
import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenIdentifier;
import org.apache.hadoop.security.token.delegation.web.DelegationTokenAuthenticator;
import org.apache.hadoop.security.token.delegation.web.KerberosDelegationTokenAuthenticationHandler;
import org.apache.hadoop.util.JsonSerialization;
import org.json.simple.JSONArray;
import org.junit.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsServerDefaults;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.XAttrCodec;
import org.apache.hadoop.fs.http.client.HttpFSUtils;
import org.apache.hadoop.fs.http.client.HttpFSFileSystem.Operation;
import org.apache.hadoop.fs.http.server.HttpFSParametersProvider.DataParam;
import org.apache.hadoop.fs.http.server.HttpFSParametersProvider.NoRedirectParam;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.AclEntryScope;
import org.apache.hadoop.fs.permission.AclEntryType;
import org.apache.hadoop.fs.permission.AclStatus;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.hdfs.web.WebHdfsConstants;
import org.apache.hadoop.hdfs.web.WebHdfsFileSystem;
import org.apache.hadoop.lib.server.Service;
import org.apache.hadoop.lib.server.ServiceException;
import org.apache.hadoop.lib.service.Groups;
import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.security.authentication.server.AuthenticationToken;
import org.apache.hadoop.security.authentication.util.Signer;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.test.HFSTestCase;
import org.apache.hadoop.test.HadoopUsersConfTestHelper;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.test.TestDir;
import org.apache.hadoop.test.TestDirHelper;
import org.apache.hadoop.test.TestHdfs;
import org.apache.hadoop.test.TestHdfsHelper;
import org.apache.hadoop.test.TestJetty;
import org.apache.hadoop.test.TestJettyHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.apache.hadoop.thirdparty.com.google.common.collect.Maps;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import org.apache.hadoop.security.authentication.server.AuthenticationFilter;

public class TestHttpFSServer_Purified extends HFSTestCase {

    private static Callable<Long> defaultEntryMetricGetter = () -> 0L;

    private static Callable<Long> defaultExitMetricGetter = () -> 1L;

    private static HashMap<String, Callable<Long>> metricsGetter = new HashMap<String, Callable<Long>>() {

        {
            put("LISTSTATUS", () -> HttpFSServerWebApp.get().getMetrics().getOpsListing());
            put("MKDIRS", () -> HttpFSServerWebApp.get().getMetrics().getOpsMkdir());
            put("GETFILESTATUS", () -> HttpFSServerWebApp.get().getMetrics().getOpsStat());
        }
    };

    public static class MockGroups implements Service, Groups {

        @Override
        public void init(org.apache.hadoop.lib.server.Server server) throws ServiceException {
        }

        @Override
        public void postInit() throws ServiceException {
        }

        @Override
        public void destroy() {
        }

        @Override
        public Class[] getServiceDependencies() {
            return new Class[0];
        }

        @Override
        public Class getInterface() {
            return Groups.class;
        }

        @Override
        public void serverStatusChange(org.apache.hadoop.lib.server.Server.Status oldStatus, org.apache.hadoop.lib.server.Server.Status newStatus) throws ServiceException {
        }

        @Override
        public List<String> getGroups(String user) throws IOException {
            return Arrays.asList(HadoopUsersConfTestHelper.getHadoopUserGroups(user));
        }

        @Override
        public Set<String> getGroupsSet(String user) throws IOException {
            return new HashSet<>(getGroups(user));
        }
    }

    private Configuration createHttpFSConf(boolean addDelegationTokenAuthHandler, boolean sslEnabled) throws Exception {
        File homeDir = TestDirHelper.getTestDir();
        Assert.assertTrue(new File(homeDir, "conf").mkdir());
        Assert.assertTrue(new File(homeDir, "log").mkdir());
        Assert.assertTrue(new File(homeDir, "temp").mkdir());
        HttpFSServerWebApp.setHomeDirForCurrentThread(homeDir.getAbsolutePath());
        File secretFile = new File(new File(homeDir, "conf"), "secret");
        Writer w = new FileWriter(secretFile);
        w.write("secret");
        w.close();
        File hadoopConfDir = new File(new File(homeDir, "conf"), "hadoop-conf");
        hadoopConfDir.mkdirs();
        Configuration hdfsConf = TestHdfsHelper.getHdfsConf();
        Configuration conf = new Configuration(hdfsConf);
        conf.setBoolean(DFSConfigKeys.DFS_NAMENODE_ACLS_ENABLED_KEY, true);
        conf.setBoolean(DFSConfigKeys.DFS_NAMENODE_XATTRS_ENABLED_KEY, true);
        conf.set(DFSConfigKeys.DFS_STORAGE_POLICY_SATISFIER_MODE_KEY, StoragePolicySatisfierMode.EXTERNAL.toString());
        File hdfsSite = new File(hadoopConfDir, "hdfs-site.xml");
        OutputStream os = new FileOutputStream(hdfsSite);
        conf.writeXml(os);
        os.close();
        conf = new Configuration(false);
        if (addDelegationTokenAuthHandler) {
            conf.set(HttpFSAuthenticationFilter.HADOOP_HTTP_CONF_PREFIX + AuthenticationFilter.AUTH_TYPE, HttpFSKerberosAuthenticationHandlerForTesting.class.getName());
        }
        conf.set("httpfs.services.ext", MockGroups.class.getName());
        conf.set("httpfs.admin.group", HadoopUsersConfTestHelper.getHadoopUserGroups(HadoopUsersConfTestHelper.getHadoopUsers()[0])[0]);
        conf.set("httpfs.proxyuser." + HadoopUsersConfTestHelper.getHadoopProxyUser() + ".groups", HadoopUsersConfTestHelper.getHadoopProxyUserGroups());
        conf.set("httpfs.proxyuser." + HadoopUsersConfTestHelper.getHadoopProxyUser() + ".hosts", HadoopUsersConfTestHelper.getHadoopProxyUserHosts());
        conf.set(HttpFSAuthenticationFilter.HADOOP_HTTP_CONF_PREFIX + AuthenticationFilter.SIGNATURE_SECRET_FILE, secretFile.getAbsolutePath());
        conf.set("httpfs.hadoop.config.dir", hadoopConfDir.toString());
        if (sslEnabled) {
            conf.set("httpfs.ssl.enabled", "true");
        }
        File httpfsSite = new File(new File(homeDir, "conf"), "httpfs-site.xml");
        os = new FileOutputStream(httpfsSite);
        conf.writeXml(os);
        os.close();
        return conf;
    }

    private void writeConf(Configuration conf, String sitename) throws Exception {
        File homeDir = TestDirHelper.getTestDir();
        File hadoopConfDir = new File(new File(homeDir, "conf"), "hadoop-conf");
        Assert.assertTrue(hadoopConfDir.exists());
        File siteFile = new File(hadoopConfDir, sitename);
        OutputStream os = new FileOutputStream(siteFile);
        conf.writeXml(os);
        os.close();
    }

    private Server createHttpFSServer(boolean addDelegationTokenAuthHandler, boolean sslEnabled) throws Exception {
        Configuration conf = createHttpFSConf(addDelegationTokenAuthHandler, sslEnabled);
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource("webapp");
        WebAppContext context = new WebAppContext(url.getPath(), "/webhdfs");
        Server server = TestJettyHelper.getJettyServer();
        server.setHandler(context);
        server.start();
        if (addDelegationTokenAuthHandler) {
            HttpFSServerWebApp.get().setAuthority(TestJettyHelper.getAuthority());
        }
        return server;
    }

    private String getSignedTokenString() throws Exception {
        AuthenticationToken token = new AuthenticationToken("u", "p", new KerberosDelegationTokenAuthenticationHandler().getType());
        token.setExpires(System.currentTimeMillis() + 100000000);
        SignerSecretProvider secretProvider = StringSignerSecretProviderCreator.newStringSignerSecretProvider();
        Properties secretProviderProps = new Properties();
        secretProviderProps.setProperty(AuthenticationFilter.SIGNATURE_SECRET, "secret");
        secretProvider.init(secretProviderProps, null, -1);
        Signer signer = new Signer(secretProvider);
        return signer.sign(token.toString());
    }

    private void delegationTokenCommonTests(boolean sslEnabled) throws Exception {
        URL url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=GETHOMEDIRECTORY");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, conn.getResponseCode());
        String tokenSigned = getSignedTokenString();
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=GETDELEGATIONTOKEN");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", AuthenticatedURL.AUTH_COOKIE + "=" + tokenSigned);
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
        JSONObject json = (JSONObject) new JSONParser().parse(new InputStreamReader(conn.getInputStream()));
        json = (JSONObject) json.get(DelegationTokenAuthenticator.DELEGATION_TOKEN_JSON);
        String tokenStr = (String) json.get(DelegationTokenAuthenticator.DELEGATION_TOKEN_URL_STRING_JSON);
        Token<AbstractDelegationTokenIdentifier> dToken = new Token<AbstractDelegationTokenIdentifier>();
        dToken.decodeFromUrlString(tokenStr);
        Assert.assertEquals(sslEnabled ? WebHdfsConstants.SWEBHDFS_TOKEN_KIND : WebHdfsConstants.WEBHDFS_TOKEN_KIND, dToken.getKind());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=GETHOMEDIRECTORY&delegation=" + tokenStr);
        conn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=RENEWDELEGATIONTOKEN&token=" + tokenStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        Assert.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, conn.getResponseCode());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=RENEWDELEGATIONTOKEN&token=" + tokenStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Cookie", AuthenticatedURL.AUTH_COOKIE + "=" + tokenSigned);
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=CANCELDELEGATIONTOKEN&token=" + tokenStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=GETHOMEDIRECTORY&delegation=" + tokenStr);
        conn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, conn.getResponseCode());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=GETTRASHROOT&delegation=" + tokenStr);
        conn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, conn.getResponseCode());
        url = new URL(TestJettyHelper.getJettyURL(), "/webhdfs/v1/?op=GETTRASHROOT");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", AuthenticatedURL.AUTH_COOKIE + "=" + tokenSigned);
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
    }

    private void createWithHttp(String filename, String perms) throws Exception {
        createWithHttp(filename, perms, null);
    }

    private void createWithHttp(String filename, String perms, String unmaskedPerms) throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        if (filename.charAt(0) == '/') {
            filename = filename.substring(1);
        }
        String pathOps;
        if (perms == null) {
            pathOps = MessageFormat.format("/webhdfs/v1/{0}?user.name={1}&op=CREATE", filename, user);
        } else {
            pathOps = MessageFormat.format("/webhdfs/v1/{0}?user.name={1}&permission={2}&op=CREATE", filename, user, perms);
        }
        if (unmaskedPerms != null) {
            pathOps = pathOps + "&unmaskedpermission=" + unmaskedPerms;
        }
        URL url = new URL(TestJettyHelper.getJettyURL(), pathOps);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestMethod("PUT");
        conn.connect();
        Assert.assertEquals(HttpURLConnection.HTTP_CREATED, conn.getResponseCode());
    }

    private void createDirWithHttp(String dirname, String perms, String unmaskedPerms) throws Exception {
        long oldOpsMkdir = metricsGetter.get("MKDIRS").call();
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        if (dirname.charAt(0) == '/') {
            dirname = dirname.substring(1);
        }
        String pathOps;
        if (perms == null) {
            pathOps = MessageFormat.format("/webhdfs/v1/{0}?user.name={1}&op=MKDIRS", dirname, user);
        } else {
            pathOps = MessageFormat.format("/webhdfs/v1/{0}?user.name={1}&permission={2}&op=MKDIRS", dirname, user, perms);
        }
        if (unmaskedPerms != null) {
            pathOps = pathOps + "&unmaskedpermission=" + unmaskedPerms;
        }
        URL url = new URL(TestJettyHelper.getJettyURL(), pathOps);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.connect();
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
        Assert.assertEquals(1 + oldOpsMkdir, (long) metricsGetter.get("MKDIRS").call());
    }

    private String getStatus(String filename, String command) throws Exception {
        long oldOpsStat = metricsGetter.getOrDefault(command, defaultEntryMetricGetter).call();
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        if (filename.charAt(0) == '/') {
            filename = filename.substring(1);
        }
        String pathOps = MessageFormat.format("/webhdfs/v1/{0}?user.name={1}&op={2}", filename, user, command);
        URL url = new URL(TestJettyHelper.getJettyURL(), pathOps);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        long opsStat = metricsGetter.getOrDefault(command, defaultExitMetricGetter).call();
        Assert.assertEquals(oldOpsStat + 1L, opsStat);
        return reader.readLine();
    }

    private void putCmd(String filename, String command, String params) throws Exception {
        Assert.assertEquals(HttpURLConnection.HTTP_OK, putCmdWithReturn(filename, command, params).getResponseCode());
    }

    private HttpURLConnection putCmdWithReturn(String filename, String command, String params) throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        if (filename.charAt(0) == '/') {
            filename = filename.substring(1);
        }
        String pathOps = MessageFormat.format("/webhdfs/v1/{0}?user.name={1}{2}{3}&op={4}", filename, user, (params == null) ? "" : "&", (params == null) ? "" : params, command);
        URL url = new URL(TestJettyHelper.getJettyURL(), pathOps);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.connect();
        return conn;
    }

    private String getPerms(String statusJson) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(statusJson);
        JSONObject details = (JSONObject) jsonObject.get("FileStatus");
        return (String) details.get("permission");
    }

    private String getPath(String statusJson) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject details = (JSONObject) parser.parse(statusJson);
        return (String) details.get("Path");
    }

    private List<String> getAclEntries(String statusJson) throws Exception {
        List<String> entries = new ArrayList<String>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(statusJson);
        JSONObject details = (JSONObject) jsonObject.get("AclStatus");
        JSONArray jsonEntries = (JSONArray) details.get("entries");
        if (jsonEntries != null) {
            for (Object e : jsonEntries) {
                entries.add(e.toString());
            }
        }
        return entries;
    }

    private Map<String, byte[]> getXAttrs(String statusJson) throws Exception {
        Map<String, byte[]> xAttrs = Maps.newHashMap();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(statusJson);
        JSONArray jsonXAttrs = (JSONArray) jsonObject.get("XAttrs");
        if (jsonXAttrs != null) {
            for (Object a : jsonXAttrs) {
                String name = (String) ((JSONObject) a).get("name");
                String value = (String) ((JSONObject) a).get("value");
                xAttrs.put(name, decodeXAttrValue(value));
            }
        }
        return xAttrs;
    }

    private byte[] decodeXAttrValue(String value) throws IOException {
        if (value != null) {
            return XAttrCodec.decodeValue(value);
        } else {
            return new byte[0];
        }
    }

    private AclEntry findAclWithName(AclStatus stat, String name) throws IOException {
        AclEntry relevantAcl = null;
        Iterator<AclEntry> it = stat.getEntries().iterator();
        while (it.hasNext()) {
            AclEntry e = it.next();
            if (e.getName().equals(name)) {
                relevantAcl = e;
                break;
            }
        }
        return relevantAcl;
    }

    public static String setXAttrParam(String name, byte[] value) throws IOException {
        return "xattr.name=" + name + "&xattr.value=" + XAttrCodec.encodeValue(value, XAttrCodec.HEX) + "&encoding=hex&flag=create";
    }

    private HttpURLConnection snapshotTestPreconditions(String httpMethod, String snapOperation, String additionalParams) throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        URL url = new URL(TestJettyHelper.getJettyURL(), MessageFormat.format("/webhdfs/v1/tmp/tmp-snap-test/subdir?user.name={0}&op=MKDIRS", user));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.connect();
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
        Path snapshottablePath = new Path("/tmp/tmp-snap-test");
        DistributedFileSystem dfs = (DistributedFileSystem) FileSystem.get(snapshottablePath.toUri(), TestHdfsHelper.getHdfsConf());
        dfs.allowSnapshot(snapshottablePath);
        url = new URL(TestJettyHelper.getJettyURL(), MessageFormat.format("/webhdfs/v1/tmp/tmp-snap-test?user.name={0}&op={1}&{2}", user, snapOperation, additionalParams));
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(httpMethod);
        conn.connect();
        return conn;
    }

    private HttpURLConnection sendRequestToHttpFSServer(String path, String op, String additionalParams) throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        URL url = new URL(TestJettyHelper.getJettyURL(), MessageFormat.format("/webhdfs/v1{0}?user.name={1}&op={2}&{3}", path, user, op, additionalParams));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        return conn;
    }

    private HttpURLConnection sendRequestGetSnapshotDiff(String path, String oldsnapshotname, String snapshotname) throws Exception {
        return sendRequestToHttpFSServer(path, "GETSNAPSHOTDIFF", MessageFormat.format("oldsnapshotname={0}&snapshotname={1}", oldsnapshotname, snapshotname));
    }

    private void verifyGetSnapshottableDirectoryList(DistributedFileSystem dfs) throws Exception {
        HttpURLConnection conn = sendRequestToHttpFSServer("/", "GETSNAPSHOTTABLEDIRECTORYLIST", "");
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String dirLst = reader.readLine();
        SnapshottableDirectoryStatus[] dfsDirLst = dfs.getSnapshottableDirListing();
        Assert.assertEquals(dirLst, JsonUtil.toJsonString(dfsDirLst));
    }

    private void verifyGetSnapshotList(DistributedFileSystem dfs, Path path) throws Exception {
        HttpURLConnection conn = sendRequestToHttpFSServer(path.toString(), "GETSNAPSHOTLIST", "");
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String dirLst = reader.readLine();
        SnapshotStatus[] dfsDirLst = dfs.getSnapshotListing(path);
        Assert.assertEquals(dirLst, JsonUtil.toJsonString(dfsDirLst));
    }

    private void verifyGetServerDefaults(DistributedFileSystem dfs) throws Exception {
        HttpURLConnection conn = sendRequestToHttpFSServer("/", "GETSERVERDEFAULTS", "");
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String dirLst = reader.readLine();
        FsServerDefaults dfsDirLst = dfs.getServerDefaults();
        Assert.assertNotNull(dfsDirLst);
        Assert.assertEquals(dirLst, JsonUtil.toJsonString(dfsDirLst));
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testHdfsAccess_1() throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        URL url = new URL(TestJettyHelper.getJettyURL(), MessageFormat.format("/webhdfs/v1/?user.name={0}&op=liststatus", user));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testHdfsAccess_2() throws Exception {
        long oldOpsListStatus = metricsGetter.get("LISTSTATUS").call();
        Assert.assertEquals(1 + oldOpsListStatus, (long) metricsGetter.get("LISTSTATUS").call());
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testMkdirs_1() throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        URL url = new URL(TestJettyHelper.getJettyURL(), MessageFormat.format("/webhdfs/v1/tmp/sub-tmp?user.name={0}&op=MKDIRS", user));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.connect();
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testMkdirs_2() throws Exception {
        long oldMkdirOpsStat = metricsGetter.get("MKDIRS").call();
        long opsStat = metricsGetter.get("MKDIRS").call();
        Assert.assertEquals(1 + oldMkdirOpsStat, opsStat);
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testGlobFilter_1() throws Exception {
        String user = HadoopUsersConfTestHelper.getHadoopUsers()[0];
        URL url = new URL(TestJettyHelper.getJettyURL(), MessageFormat.format("/webhdfs/v1/tmp?user.name={0}&op=liststatus&filter=f*", user));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testGlobFilter_2() throws Exception {
        long oldOpsListStatus = metricsGetter.get("LISTSTATUS").call();
        Assert.assertEquals(1 + oldOpsListStatus, (long) metricsGetter.get("LISTSTATUS").call());
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testRenameSnapshot_1_testMerged_1() throws Exception {
        HttpURLConnection conn = snapshotTestPreconditions("PUT", "CREATESNAPSHOT", "snapshotname=snap-to-rename");
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testRenameSnapshot_3_testMerged_2() throws Exception {
        String result = getStatus("/tmp/tmp-snap-test/.snapshot", "LISTSTATUS");
        Assert.assertTrue(result.contains("snap-renamed"));
        Assert.assertFalse(result.contains("snap-to-rename"));
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testDeleteSnapshot_1_testMerged_1() throws Exception {
        HttpURLConnection conn = snapshotTestPreconditions("PUT", "CREATESNAPSHOT", "snapshotname=snap-to-delete");
        Assert.assertEquals(conn.getResponseCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    @TestDir
    @TestJetty
    @TestHdfs
    public void testDeleteSnapshot_3() throws Exception {
        String result = getStatus("/tmp/tmp-snap-test/.snapshot", "LISTSTATUS");
        Assert.assertFalse(result.contains("snap-to-delete"));
    }
}
