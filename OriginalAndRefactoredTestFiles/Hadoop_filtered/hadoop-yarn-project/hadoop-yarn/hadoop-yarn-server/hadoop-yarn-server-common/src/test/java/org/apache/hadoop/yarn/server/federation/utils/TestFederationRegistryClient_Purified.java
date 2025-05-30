package org.apache.hadoop.yarn.server.federation.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.registry.client.api.RegistryOperations;
import org.apache.hadoop.registry.client.impl.FSRegistryOperationsService;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.security.AMRMTokenIdentifier;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestFederationRegistryClient_Purified {

    private Configuration conf;

    private UserGroupInformation user;

    private RegistryOperations registry;

    private FederationRegistryClient registryClient;

    @Before
    public void setup() throws Exception {
        this.conf = new YarnConfiguration();
        this.registry = new FSRegistryOperationsService();
        this.registry.init(this.conf);
        this.registry.start();
        this.user = UserGroupInformation.getCurrentUser();
        this.registryClient = new FederationRegistryClient(this.conf, this.registry, this.user);
        this.registryClient.cleanAllApplications();
        Assert.assertEquals(0, this.registryClient.getAllApplications().size());
    }

    @After
    public void breakDown() {
        registryClient.cleanAllApplications();
        Assert.assertEquals(0, registryClient.getAllApplications().size());
        registry.stop();
    }

    @Test
    public void testBasicCase_1() {
        Assert.assertEquals(1, this.registryClient.getAllApplications().size());
    }

    @Test
    public void testBasicCase_2_testMerged_2() {
        ApplicationId appId = ApplicationId.newInstance(0, 0);
        this.registryClient.writeAMRMTokenForUAM(appId, scId1, new Token<AMRMTokenIdentifier>());
        this.registryClient.writeAMRMTokenForUAM(appId, scId2, new Token<AMRMTokenIdentifier>());
        Assert.assertEquals(2, this.registryClient.loadStateFromRegistry(appId).size());
        this.registryClient.removeAppFromRegistry(appId);
        Assert.assertEquals(0, this.registryClient.loadStateFromRegistry(appId).size());
    }

    @Test
    public void testBasicCase_3() {
        Assert.assertEquals(0, this.registryClient.getAllApplications().size());
    }
}
