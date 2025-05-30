package org.jboss.as.test.integration.domain.management.cli;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.HOST;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.INCLUDE_RUNTIME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_RESOURCE_OPERATION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SERVER;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SOCKET_BINDING;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SOCKET_BINDING_GROUP;
import static org.jboss.as.test.integration.domain.management.util.DomainTestSupport.validateResponse;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.controller.client.helpers.domain.DomainClient;
import org.jboss.as.network.NetworkUtils;
import org.jboss.as.test.integration.common.HttpRequest;
import org.jboss.as.test.integration.domain.management.util.DomainTestSupport;
import org.jboss.as.test.integration.domain.suites.CLITestSuite;
import org.jboss.as.test.integration.management.util.CLITestUtil;
import org.jboss.as.test.integration.management.util.SimpleServlet;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DomainDeploymentOverlayTestCase_Purified {

    private static final String SOCKET_BINDING_GROUP_NAME = "standard-sockets";

    private static File war1;

    private static File war2;

    private static File war3;

    private static File webXml;

    private static File overrideXml;

    private static DomainTestSupport testSupport;

    private CommandContext ctx;

    private DomainClient client;

    @BeforeClass
    public static void before() throws Exception {
        String tempDir = System.getProperty("java.io.tmpdir");
        WebArchive war;
        war = ShrinkWrap.create(WebArchive.class, "deployment0.war");
        war.addClass(SimpleServlet.class);
        war.addAsWebInfResource("cli/deployment-overlay/web.xml", "web.xml");
        war1 = new File(tempDir + File.separator + war.getName());
        new ZipExporterImpl(war).exportTo(war1, true);
        war = ShrinkWrap.create(WebArchive.class, "deployment1.war");
        war.addClass(SimpleServlet.class);
        war.addAsWebInfResource("cli/deployment-overlay/web.xml", "web.xml");
        war2 = new File(tempDir + File.separator + war.getName());
        new ZipExporterImpl(war).exportTo(war2, true);
        war = ShrinkWrap.create(WebArchive.class, "another.war");
        war.addClass(SimpleServlet.class);
        war.addAsWebInfResource("cli/deployment-overlay/web.xml", "web.xml");
        war3 = new File(tempDir + File.separator + war.getName());
        new ZipExporterImpl(war).exportTo(war3, true);
        final URL overrideXmlUrl = DomainDeploymentOverlayTestCase.class.getClassLoader().getResource("cli/deployment-overlay/override.xml");
        if (overrideXmlUrl == null) {
            Assert.fail("Failed to locate cli/deployment-overlay/override.xml");
        }
        overrideXml = new File(overrideXmlUrl.toURI());
        if (!overrideXml.exists()) {
            Assert.fail("Failed to locate cli/deployment-overlay/override.xml");
        }
        final URL webXmlUrl = DomainDeploymentOverlayTestCase.class.getClassLoader().getResource("cli/deployment-overlay/web.xml");
        if (webXmlUrl == null) {
            Assert.fail("Failed to locate cli/deployment-overlay/web.xml");
        }
        webXml = new File(webXmlUrl.toURI());
        if (!webXml.exists()) {
            Assert.fail("Failed to locate cli/deployment-overlay/web.xml");
        }
        testSupport = CLITestSuite.createSupport(DomainDeploymentOverlayTestCase.class.getSimpleName());
    }

    @AfterClass
    public static void after() throws Exception {
        try {
            CLITestSuite.stopSupport();
            testSupport = null;
        } finally {
            war1.delete();
            war2.delete();
            war3.delete();
        }
    }

    @Before
    public void setUp() throws Exception {
        client = testSupport.getDomainPrimaryLifecycleUtil().createDomainClient();
        ctx = CLITestUtil.getCommandContext(testSupport);
        ctx.connectController();
    }

    @After
    public void tearDown() throws Exception {
        if (ctx != null) {
            ctx.handleSafe("undeploy --all-relevant-server-groups " + war1.getName());
            ctx.handleSafe("undeploy --all-relevant-server-groups " + war2.getName());
            ctx.handleSafe("undeploy --all-relevant-server-groups " + war3.getName());
            ctx.handleSafe("deployment-overlay remove --name=overlay-test");
            ctx.terminateSession();
            ctx = null;
        }
        client.close();
        client = null;
    }

    private String performHttpCall(String host, String server, String deployment) throws Exception {
        ModelNode op = new ModelNode();
        op.get(OP).set(READ_RESOURCE_OPERATION);
        op.get(OP_ADDR).add(HOST, host).add(SERVER, server).add(SOCKET_BINDING_GROUP, SOCKET_BINDING_GROUP_NAME).add(SOCKET_BINDING, "http");
        op.get(INCLUDE_RUNTIME).set(true);
        ModelNode socketBinding = validateResponse(client.execute(op));
        URL url = new URL("http", NetworkUtils.formatAddress(InetAddress.getByName(socketBinding.get("bound-address").asString())), socketBinding.get("bound-port").asInt(), "/" + deployment + "/SimpleServlet?env-entry=overlay-test");
        return HttpRequest.get(url.toExternalForm(), 10, TimeUnit.SECONDS).trim();
    }

    @Test
    public void testSimpleOverride_1() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testSimpleOverride_2() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testSimpleOverride_3() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testSimpleOverride_4() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testSimpleOverride_5() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testSimpleOverride_6() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testSimpleOverride_7() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testSimpleOverride_8() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testSimpleOverrideWithRedeployAffected_1() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testSimpleOverrideWithRedeployAffected_2() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testSimpleOverrideWithRedeployAffected_3() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testSimpleOverrideWithRedeployAffected_4() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testWildcardOverride_1() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testWildcardOverride_2() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testWildcardOverride_3() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testWildcardOverride_4() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testWildcardOverride_5() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testWildcardOverride_6() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testWildcardOverrideWithRedeployAffected_1() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testWildcardOverrideWithRedeployAffected_2() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testWildcardOverrideWithRedeployAffected_3() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testWildcardOverrideWithRedeployAffected_4() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testWildcardOverrideWithRedeployAffected_5() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testWildcardOverrideWithRedeployAffected_6() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_1() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_2() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_3() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_4() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_5() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_6() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_7() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_8() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_9() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_10() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_11() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_12() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_13() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_14() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_15() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_16() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_17() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_18() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_19() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_20() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_21() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_22() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_23() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_24() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_25() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_26() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_27() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_28() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_29() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_30() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_31() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_32() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_33() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_34() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_35() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_36() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_37() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_38() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_39() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_40() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_41() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_42() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testMultipleLinks_43() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testMultipleLinks_44() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testMultipleLinks_45() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testMultipleLinks_46() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testMultipleLinks_47() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testMultipleLinks_48() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testRedeployAffected_1() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testRedeployAffected_2() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testRedeployAffected_3() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testRedeployAffected_4() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testRedeployAffected_5() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testRedeployAffected_6() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }

    @Test
    public void testRedeployAffected_7() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "deployment0"));
    }

    @Test
    public void testRedeployAffected_8() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("primary", "main-one", "deployment1"));
    }

    @Test
    public void testRedeployAffected_9() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("primary", "main-one", "another"));
    }

    @Test
    public void testRedeployAffected_10() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment0"));
    }

    @Test
    public void testRedeployAffected_11() throws Exception {
        assertEquals("NON OVERRIDDEN", performHttpCall("secondary", "main-three", "deployment1"));
    }

    @Test
    public void testRedeployAffected_12() throws Exception {
        assertEquals("OVERRIDDEN", performHttpCall("secondary", "main-three", "another"));
    }
}
