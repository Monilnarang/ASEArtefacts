package org.jboss.as.test.integration.domain.management.cli;

import static org.jboss.as.test.shared.PermissionUtils.createPermissionsXmlAsset;
import java.io.File;
import java.net.SocketPermission;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jboss.as.test.integration.common.HttpRequest;
import org.jboss.as.test.integration.domain.management.util.DomainTestSupport;
import org.jboss.as.test.integration.domain.management.util.RolloutPlanBuilder;
import org.jboss.as.test.integration.domain.suites.CLITestSuite;
import org.jboss.as.test.integration.management.base.AbstractCliTestBase;
import org.jboss.as.test.integration.management.util.CLIOpResult;
import org.jboss.as.test.shared.RetryTaskExecutor;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RolloutPlanTestCase_Purified extends AbstractCliTestBase {

    private static File warFile;

    private static final int TEST_PORT = 8081;

    private static final String[] serverGroups = new String[] { "main-server-group", "other-server-group", "test-server-group" };

    @BeforeClass
    public static void before() throws Exception {
        CLITestSuite.createSupport(RolloutPlanTestCase.class.getSimpleName());
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "RolloutPlanTestCase.war");
        war.addClass(RolloutPlanTestServlet.class);
        war.addAsManifestResource(createPermissionsXmlAsset(new SocketPermission("localhost:" + TEST_PORT, "listen"), new SocketPermission("localhost:" + (TEST_PORT + 350), "listen")), "permissions.xml");
        String tempDir = System.getProperty("java.io.tmpdir");
        warFile = new File(tempDir + File.separator + "RolloutPlanTestCase.war");
        new ZipExporterImpl(war).exportTo(warFile, true);
        AbstractCliTestBase.initCLI(DomainTestSupport.primaryAddress);
        cli.sendLine("/server-group=test-server-group:add(profile=default,socket-binding-group=standard-sockets)");
        cli.sendLine("/server-group=test-server-group/jvm=default:add");
        cli.sendLine("/host=primary/server-config=test-one:add(group=test-server-group,socket-binding-port-offset=700");
        cli.sendLine("/host=primary/server-config=test-one/interface=public:add(inet-address=" + CLITestSuite.hostAddresses.get("primary") + ")");
        CLITestSuite.addServer("test-one", "primary", "test-server-group", "default", 700, true);
        cli.sendLine("/host=primary/server-config=main-two:start(blocking=true)");
        CLIOpResult res = cli.readAllAsOpResult();
        Assert.assertTrue(res.isIsOutcomeSuccess());
        waitUntilState("main-two", "STARTED");
        cli.sendLine("/host=primary/server-config=test-one:start(blocking=true)");
        res = cli.readAllAsOpResult();
        Assert.assertTrue(res.isIsOutcomeSuccess());
        waitUntilState("test-one", "STARTED");
    }

    @AfterClass
    public static void after() throws Exception {
        if (warFile.exists()) {
            warFile.delete();
        }
        cli.sendLine("/host=primary/server-config=test-one:stop(blocking=true)");
        CLIOpResult res = cli.readAllAsOpResult();
        Assert.assertTrue(res.isIsOutcomeSuccess());
        waitUntilState("test-one", "STOPPED");
        cli.sendLine("/host=primary/server-config=main-two:stop(blocking=true)");
        res = cli.readAllAsOpResult();
        Assert.assertTrue(res.isIsOutcomeSuccess());
        waitUntilState("main-two", "DISABLED");
        AbstractCliTestBase.closeCLI();
        CLITestSuite.stopSupport();
    }

    @After
    public void afterTest() {
        cli.sendLine("undeploy RolloutPlanTestCase.war --all-relevant-server-groups", true);
        cli.sendLine("/socket-binding-group=standard-sockets/socket-binding=test-binding:remove(){allow-resource-service-restart=true}", true);
    }

    @SuppressWarnings({ "rawtypes", "RedundantExplicitVariableType" })
    private boolean getServerStatus(String serverName, CLIOpResult result) throws Exception {
        Map groups = (Map) result.getServerGroups();
        for (Object group : groups.values()) {
            Map hosts = (Map) ((Map) group).get("host");
            if (hosts != null) {
                for (Object value : hosts.values()) {
                    Map serverResults = (Map) value;
                    Map serverResult = (Map) serverResults.get(serverName);
                    if (serverResult != null) {
                        Map serverResponse = (Map) serverResult.get("response");
                        String serverOutcome = (String) serverResponse.get("outcome");
                        return "success".equals(serverOutcome);
                    }
                }
            }
        }
        throw new NoResponseException(serverName);
    }

    private static String checkURL(String server) throws Exception {
        return checkURL(server, "/RolloutPlanTestCase/RolloutServlet");
    }

    private static String checkURL(String server, String path) throws Exception {
        String address = CLITestSuite.hostAddresses.get(getServerHost(server));
        Integer portOffset = CLITestSuite.portOffsets.get(server);
        URL url = new URL("http", address, 8080 + portOffset, path);
        String response;
        try {
            response = HttpRequest.get(url.toString(), 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new Exception("Http request failed.", e);
        }
        return response;
    }

    private static String getServerHost(String server) {
        for (Entry<String, String[]> hostEntry : CLITestSuite.hostServers.entrySet()) {
            for (String hostServer : hostEntry.getValue()) if (hostServer.equals(server))
                return hostEntry.getKey();
        }
        return null;
    }

    private static void waitUntilState(final String serverName, final String state) throws TimeoutException {
        final String serverHost = CLITestSuite.getServerHost(serverName);
        RetryTaskExecutor<Void> taskExecutor = new RetryTaskExecutor<>();
        taskExecutor.retryTask(new Callable<>() {

            public Void call() throws Exception {
                cli.sendLine("/host=" + serverHost + "/server-config=" + serverName + ":read-attribute(name=status)");
                CLIOpResult res = cli.readAllAsOpResult();
                if (!res.getResult().equals(state))
                    throw new Exception("Server not in state.");
                return null;
            }
        });
    }

    private static class NoResponseException extends Exception {

        private static final long serialVersionUID = 1L;

        private NoResponseException(String serverName) {
            super("Status of the server " + serverName + " not found in operation result.");
        }
    }

    @Test
    public void testInSeriesRolloutPlan_1_testMerged_1() throws Exception {
        RolloutPlanBuilder planBuilder = new RolloutPlanBuilder();
        planBuilder.addGroup(serverGroups[0], new RolloutPlanBuilder.RolloutPolicy(true, null, null));
        planBuilder.addGroup(serverGroups[1], new RolloutPlanBuilder.RolloutPolicy(true, null, null));
        planBuilder.addGroup(serverGroups[2], new RolloutPlanBuilder.RolloutPolicy(true, null, null));
        String rolloutPlan = planBuilder.buildAsString();
        cli.sendLine("rollout-plan add --name=testPlan --content=" + rolloutPlan);
        planBuilder = new RolloutPlanBuilder();
        rolloutPlan = planBuilder.buildAsString();
        cli.sendLine("rollout-plan add --name=testPlan2 --content=" + rolloutPlan);
        cli.sendLine("cd /management-client-content=rollout-plans/rollout-plan");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        Assert.assertTrue(ls.contains("testPlan"));
        Assert.assertTrue(ls.contains("testPlan2"));
        cli.sendLine("deploy " + warFile.getAbsolutePath() + " --all-server-groups --headers={rollout id=testPlan}");
        cli.sendLine("undeploy RolloutPlanTestCase.war --all-relevant-server-groups");
        cli.sendLine("deploy " + warFile.getAbsolutePath() + " --all-server-groups --headers={rollout id=testPlan2}");
        cli.sendLine("rollout-plan remove --name=testPlan");
        cli.sendLine("rollout-plan remove --name=testPlan2");
        cli.sendLine("cd /management-client-content=rollout-plans");
        ls = cli.readOutput();
        Assert.assertFalse(ls.contains("testPlan"));
        Assert.assertFalse(ls.contains("testPlan2"));
    }

    @Test
    public void testInSeriesRolloutPlan_3_testMerged_2() throws Exception {
        long mainOneTime = Long.parseLong(checkURL("main-one"));
        long mainTwoTime = Long.parseLong(checkURL("main-two"));
        long mainThreeTime = Long.parseLong(checkURL("main-three"));
        long otherTwoTime = Long.parseLong(checkURL("other-two"));
        long testOneTime = Long.parseLong(checkURL("test-one"));
        Assert.assertTrue(mainOneTime < otherTwoTime);
        Assert.assertTrue(mainTwoTime < otherTwoTime);
        Assert.assertTrue(mainThreeTime < otherTwoTime);
        Assert.assertTrue(otherTwoTime < testOneTime);
        mainOneTime = Long.parseLong(checkURL("main-one"));
        mainTwoTime = Long.parseLong(checkURL("main-two"));
        mainThreeTime = Long.parseLong(checkURL("main-three"));
        otherTwoTime = Long.parseLong(checkURL("other-two"));
        testOneTime = Long.parseLong(checkURL("test-one"));
        Assert.assertTrue(mainOneTime > otherTwoTime);
        Assert.assertTrue(mainTwoTime > otherTwoTime);
        Assert.assertTrue(mainThreeTime > otherTwoTime);
        Assert.assertTrue(otherTwoTime > testOneTime);
    }
}
