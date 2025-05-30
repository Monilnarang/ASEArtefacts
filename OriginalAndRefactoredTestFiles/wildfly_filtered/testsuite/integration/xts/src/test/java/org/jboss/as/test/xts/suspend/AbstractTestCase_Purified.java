package org.jboss.as.test.xts.suspend;

import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.SocketPermission;
import java.net.URL;
import java.util.List;
import static org.jboss.as.test.shared.PermissionUtils.createPermissionsXmlAsset;
import static org.jboss.as.test.xts.suspend.Helpers.getExecutorService;
import static org.jboss.as.test.xts.suspend.Helpers.getRemoteService;

public abstract class AbstractTestCase_Purified {

    protected static final String EXECUTOR_SERVICE_CONTAINER = "default-server";

    protected static final String REMOTE_SERVICE_CONTAINER = "alternative-server";

    protected static final String EXECUTOR_SERVICE_ARCHIVE_NAME = "executorService";

    protected static final String REMOTE_SERVICE_ARCHIVE_NAME = "remoteService";

    @ArquillianResource
    @OperateOnDeployment(EXECUTOR_SERVICE_ARCHIVE_NAME)
    private URL executorServiceUrl;

    @ArquillianResource
    @OperateOnDeployment(REMOTE_SERVICE_ARCHIVE_NAME)
    private URL remoteServiceUrl;

    @ArquillianResource
    @OperateOnDeployment(REMOTE_SERVICE_ARCHIVE_NAME)
    private ManagementClient remoteServiceContainerManager;

    private ExecutorService executorService;

    private RemoteService remoteService;

    protected static WebArchive getExecutorServiceArchiveBase() {
        return ShrinkWrap.create(WebArchive.class, EXECUTOR_SERVICE_ARCHIVE_NAME + ".war").addClasses(ExecutorService.class, RemoteService.class, Helpers.class).addAsResource("context-handlers.xml", "context-handlers.xml").addAsManifestResource(new StringAsset("Dependencies: org.jboss.xts, org.jboss.jts"), "MANIFEST.MF").addAsManifestResource(createPermissionsXmlAsset(new SocketPermission("127.0.0.1:8180", "connect,resolve"), new RuntimePermission("org.apache.cxf.permission", "resolveUri"), new FilePermission(System.getProperty("java.home") + File.separator + "lib" + File.separator + "wsdl.properties", "read")), "permissions.xml");
    }

    protected static WebArchive getRemoteServiceArchiveBase() {
        return ShrinkWrap.create(WebArchive.class, REMOTE_SERVICE_ARCHIVE_NAME + ".war").addClasses(RemoteService.class).addAsResource("context-handlers.xml", "context-handlers.xml").addAsManifestResource(new StringAsset("Dependencies: org.jboss.xts, org.jboss.jts"), "MANIFEST.MF");
    }

    protected abstract void assertParticipantInvocations(List<String> invocations);

    @Before
    public void before() throws Exception {
        resumeServer();
        executorService = getExecutorService(executorServiceUrl);
        executorService.init(remoteServiceContainerManager.getWebUri().toString() + "/ws-c11/ActivationService", remoteServiceUrl.toString());
        executorService.reset();
        remoteService = getRemoteService(remoteServiceUrl);
        remoteService.reset();
    }

    @After
    public void after() throws IOException {
        resumeServer();
        try {
            executorService.rollback();
        } catch (Throwable ignored) {
        }
    }

    private void suspendServer() throws IOException {
        ModelNode suspendOperation = new ModelNode();
        suspendOperation.get(ModelDescriptionConstants.OP).set("suspend");
        remoteServiceContainerManager.getControllerClient().execute(suspendOperation);
    }

    private void resumeServer() throws IOException {
        ModelNode suspendOperation = new ModelNode();
        suspendOperation.get(ModelDescriptionConstants.OP).set("resume");
        remoteServiceContainerManager.getControllerClient().execute(suspendOperation);
    }

    @Test
    public void testRemoteServiceAfterSuspend_1() throws Exception {
        executorService.begin();
        executorService.enlistParticipant();
        executorService.execute();
        executorService.commit();
        assertParticipantInvocations(executorService.getParticipantInvocations());
    }

    @Test
    public void testRemoteServiceAfterSuspend_2() throws Exception {
        assertParticipantInvocations(remoteService.getParticipantInvocations());
    }
}
