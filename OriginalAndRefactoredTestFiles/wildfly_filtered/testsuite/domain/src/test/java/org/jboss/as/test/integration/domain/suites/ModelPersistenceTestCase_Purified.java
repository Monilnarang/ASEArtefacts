package org.jboss.as.test.integration.domain.suites;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.FAILURE_DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.NAME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OUTCOME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.REMOVE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.RESULT;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUCCESS;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.VALUE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.WRITE_ATTRIBUTE_OPERATION;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.jboss.as.cli.operation.OperationFormatException;
import org.jboss.as.cli.operation.impl.DefaultOperationRequestBuilder;
import org.jboss.as.controller.client.OperationBuilder;
import org.jboss.as.controller.client.helpers.domain.DomainClient;
import org.jboss.as.test.integration.domain.management.util.DomainLifecycleUtil;
import org.jboss.as.test.integration.domain.management.util.DomainTestSupport;
import org.jboss.as.test.integration.management.util.MgmtOperationException;
import org.jboss.as.test.integration.management.util.ModelUtil;
import org.jboss.as.test.integration.management.util.SimpleServlet;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ModelPersistenceTestCase_Purified {

    enum Host {

        PRIMARY, SECONDARY
    }

    private class CfgFileDescription {

        CfgFileDescription(int version, File file, long hash) {
            this.version = version;
            this.file = file;
            this.hash = hash;
        }

        public int version;

        public File file;

        public long hash;
    }

    private static DomainTestSupport domainSupport;

    private static DomainLifecycleUtil domainPrimaryLifecycleUtil;

    private static DomainLifecycleUtil domainSecondaryLifecycleUtil;

    private static final String DOMAIN_HISTORY_DIR = "domain_xml_history";

    private static final String HOST_HISTORY_DIR = "host_xml_history";

    private static final String CONFIG_DIR = "configuration";

    private static final String CURRENT_DIR = "current";

    private static final String PRIMARY_DIR = "primary";

    private static final String SECONDARY_DIR = "secondary";

    private static final String DOMAIN_NAME = "testing-domain-standard";

    private static final String PRIMARY_NAME = "testing-host-primary";

    private static final String SECONDARY_NAME = "testing-host-secondary";

    private static File domainCurrentCfgDir;

    private static File primaryCurrentCfgDir;

    private static File secondaryCurrentCfgDir;

    private static File domainLastCfgFile;

    private static File primaryLastCfgFile;

    private static File secondaryLastCfgFile;

    @BeforeClass
    public static void initDomain() throws Exception {
        domainSupport = DomainTestSuite.createSupport(ModelPersistenceTestCase.class.getSimpleName());
        domainPrimaryLifecycleUtil = domainSupport.getDomainPrimaryLifecycleUtil();
        domainSecondaryLifecycleUtil = domainSupport.getDomainSecondaryLifecycleUtil();
        File primaryDir = new File(domainSupport.getDomainPrimaryConfiguration().getDomainDirectory());
        File secondaryDir = new File(domainSupport.getDomainSecondaryConfiguration().getDomainDirectory());
        domainCurrentCfgDir = new File(primaryDir, CONFIG_DIR + File.separator + DOMAIN_HISTORY_DIR + File.separator + CURRENT_DIR);
        primaryCurrentCfgDir = new File(primaryDir, CONFIG_DIR + File.separator + HOST_HISTORY_DIR + File.separator + CURRENT_DIR);
        secondaryCurrentCfgDir = new File(secondaryDir, CONFIG_DIR + File.separator + HOST_HISTORY_DIR + File.separator + CURRENT_DIR);
        domainLastCfgFile = new File(primaryDir, CONFIG_DIR + File.separator + DOMAIN_HISTORY_DIR + File.separator + DOMAIN_NAME + ".last.xml");
        primaryLastCfgFile = new File(primaryDir, CONFIG_DIR + File.separator + HOST_HISTORY_DIR + File.separator + PRIMARY_NAME + ".last.xml");
        secondaryLastCfgFile = new File(secondaryDir, CONFIG_DIR + File.separator + HOST_HISTORY_DIR + File.separator + SECONDARY_NAME + ".last.xml");
    }

    @AfterClass
    public static void shutdownDomain() {
        domainSupport = null;
        domainPrimaryLifecycleUtil = null;
        domainSecondaryLifecycleUtil = null;
        DomainTestSuite.stopSupport();
    }

    private CfgFileDescription getLatestBackup(File dir) throws IOException {
        int lastVersion = 0;
        File lastFile = null;
        File[] children;
        if (dir.isDirectory() && (children = dir.listFiles()) != null) {
            for (File file : children) {
                String fileName = file.getName();
                String[] nameParts = fileName.split("\\.");
                if (!(nameParts[0].contains(DOMAIN_NAME) || nameParts[0].contains(PRIMARY_NAME) || nameParts[0].contains(SECONDARY_NAME))) {
                    continue;
                }
                if (!nameParts[2].equals("xml")) {
                    continue;
                }
                int version = Integer.valueOf(nameParts[1].substring(1));
                if (version > lastVersion) {
                    lastVersion = version;
                    lastFile = file;
                }
            }
        }
        return new CfgFileDescription(lastVersion, lastFile, (lastFile != null) ? FileUtils.checksumCRC32(lastFile) : 0);
    }

    protected ModelNode executeOperation(DomainClient client, final ModelNode op) throws IOException, MgmtOperationException {
        return executeOperation(client, op, true);
    }

    protected ModelNode executeOperation(DomainClient client, final ModelNode op, boolean unwrapResult) throws IOException, MgmtOperationException {
        ModelNode ret = client.execute(op);
        if (!unwrapResult) {
            return ret;
        }
        if (!SUCCESS.equals(ret.get(OUTCOME).asString())) {
            throw new MgmtOperationException("Management operation failed: " + ret.get(FAILURE_DESCRIPTION), op, ret);
        }
        return ret.get(RESULT);
    }

    protected void executeAndRollbackOperation(DomainClient client, final ModelNode op) throws IOException, OperationFormatException {
        ModelNode addDeploymentOp = ModelUtil.createOpNode("deployment=malformedDeployment.war", "add");
        addDeploymentOp.get("content").get(0).get("input-stream-index").set(0);
        DefaultOperationRequestBuilder builder = new DefaultOperationRequestBuilder();
        builder.setOperationName("deploy");
        builder.addNode("deployment", "malformedDeployment.war");
        ModelNode[] steps = new ModelNode[3];
        steps[0] = op;
        steps[1] = addDeploymentOp;
        steps[2] = builder.buildRequest();
        ModelNode compositeOp = ModelUtil.createCompositeNode(steps);
        OperationBuilder ob = new OperationBuilder(compositeOp, true);
        ob.addInputStream(new FileInputStream(getBrokenWar()));
        ModelNode ret = client.execute(ob.build());
        Assert.assertFalse(SUCCESS.equals(ret.get(OUTCOME).asString()));
    }

    private static File getBrokenWar() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "malformedDeployment.war");
        war.addClass(SimpleServlet.class);
        war.addAsWebInfResource(new StringAsset("Malformed"), "web.xml");
        File brokenWar = new File(System.getProperty("java.io.tmpdir") + File.separator + "malformedDeployment.war");
        brokenWar.deleteOnExit();
        new ZipExporterImpl(war).exportTo(brokenWar, true);
        return brokenWar;
    }

    @Test
    public void testDomainOperationRollback_1() throws Exception {
        CfgFileDescription lastDomainBackupDesc = getLatestBackup(domainCurrentCfgDir);
        CfgFileDescription newDomainBackupDesc = getLatestBackup(domainCurrentCfgDir);
        Assert.assertTrue(lastDomainBackupDesc.version == newDomainBackupDesc.version);
    }

    @Test
    public void testDomainOperationRollback_2() throws Exception {
        CfgFileDescription lastPrimaryBackupDesc = getLatestBackup(primaryCurrentCfgDir);
        CfgFileDescription newPrimaryBackupDesc = getLatestBackup(primaryCurrentCfgDir);
        Assert.assertTrue(lastPrimaryBackupDesc.version == newPrimaryBackupDesc.version);
    }

    @Test
    public void testDomainOperationRollback_3() throws Exception {
        CfgFileDescription lastSecondaryBackupDesc = getLatestBackup(secondaryCurrentCfgDir);
        CfgFileDescription newSecondaryBackupDesc = getLatestBackup(secondaryCurrentCfgDir);
        Assert.assertTrue(lastSecondaryBackupDesc.version == newSecondaryBackupDesc.version);
    }
}
