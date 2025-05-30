package org.apache.hadoop.fs.contract.router;

import static org.apache.hadoop.fs.contract.router.SecurityConfUtil.initSecurity;
import static org.apache.hadoop.hdfs.server.federation.metrics.TestRBFMetrics.ROUTER_BEAN;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.contract.AbstractFSContract;
import org.apache.hadoop.fs.contract.AbstractFSContractTestBase;
import org.apache.hadoop.hdfs.security.token.delegation.DelegationTokenIdentifier;
import org.apache.hadoop.hdfs.server.federation.FederationTestUtils;
import org.apache.hadoop.hdfs.server.federation.metrics.RouterMBean;
import org.apache.hadoop.security.token.SecretManager;
import org.apache.hadoop.security.token.Token;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestRouterHDFSContractDelegationToken_Purified extends AbstractFSContractTestBase {

    @BeforeClass
    public static void createCluster() throws Exception {
        RouterHDFSContract.createCluster(false, 1, true);
    }

    @AfterClass
    public static void teardownCluster() throws IOException {
        RouterHDFSContract.destroyCluster();
    }

    @Override
    protected AbstractFSContract createContract(Configuration conf) {
        return new RouterHDFSContract(conf);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testRouterDelegationToken_1_testMerged_1() throws Exception {
        RouterMBean bean = FederationTestUtils.getBean(ROUTER_BEAN, RouterMBean.class);
        assertEquals(0, bean.getCurrentTokensCount());
        assertEquals(1, bean.getCurrentTokensCount());
    }

    @Test
    public void testRouterDelegationToken_2_testMerged_2() throws Exception {
        Token<DelegationTokenIdentifier> token = (Token<DelegationTokenIdentifier>) getFileSystem().getDelegationToken("router");
        assertNotNull(token);
        assertEquals("HDFS_DELEGATION_TOKEN", token.getKind().toString());
        DelegationTokenIdentifier identifier = token.decodeIdentifier();
        assertNotNull(identifier);
        String owner = identifier.getOwner().toString();
        String host = Path.WINDOWS ? "127.0.0.1" : "localhost";
        String expectedOwner = "router/" + host + "@EXAMPLE.COM";
        assertEquals(expectedOwner, owner);
        assertEquals("router", identifier.getRenewer().toString());
        int masterKeyId = identifier.getMasterKeyId();
        assertTrue(masterKeyId > 0);
        int sequenceNumber = identifier.getSequenceNumber();
        assertTrue(sequenceNumber > 0);
        long existingMaxTime = token.decodeIdentifier().getMaxDate();
        assertTrue(identifier.getMaxDate() >= identifier.getIssueDate());
        long expiryTime = token.renew(initSecurity());
        assertEquals(existingMaxTime, token.decodeIdentifier().getMaxDate());
        assertTrue(expiryTime <= existingMaxTime);
        identifier = token.decodeIdentifier();
        assertEquals(identifier.getMasterKeyId(), masterKeyId);
        assertEquals(identifier.getSequenceNumber(), sequenceNumber);
    }
}
