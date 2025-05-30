package org.apache.druid.security.ranger.authorizer;

import org.apache.druid.server.security.Action;
import org.apache.druid.server.security.AuthenticationResult;
import org.apache.druid.server.security.Resource;
import org.apache.druid.server.security.ResourceType;
import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RangerAuthorizerTest_Purified {

    static RangerAuthorizer rangerAuthorizer = null;

    private static final AuthenticationResult alice = new AuthenticationResult("alice", null, null, null);

    private static final AuthenticationResult bob = new AuthenticationResult("bob", null, null, null);

    private static final Resource aliceDatasource = new Resource("alice-datasource", ResourceType.DATASOURCE);

    private static final Resource aliceConfig = new Resource("config", ResourceType.CONFIG);

    private static final Resource aliceState = new Resource("state", ResourceType.STATE);

    @BeforeClass
    public static void setupBeforeClass() {
        rangerAuthorizer = new RangerAuthorizer(null, null, false, new Configuration());
    }

    @Test
    public void testOperations_1_testMerged_1() {
        Assert.assertTrue(rangerAuthorizer.authorize(alice, aliceDatasource, Action.READ).isAllowed());
    }

    @Test
    public void testOperations_3() {
        Assert.assertTrue(rangerAuthorizer.authorize(alice, aliceConfig, Action.READ).isAllowed());
    }

    @Test
    public void testOperations_4() {
        Assert.assertTrue(rangerAuthorizer.authorize(alice, aliceConfig, Action.WRITE).isAllowed());
    }

    @Test
    public void testOperations_5() {
        Assert.assertTrue(rangerAuthorizer.authorize(alice, aliceState, Action.READ).isAllowed());
    }

    @Test
    public void testOperations_6() {
        Assert.assertTrue(rangerAuthorizer.authorize(alice, aliceState, Action.WRITE).isAllowed());
    }

    @Test
    public void testOperations_7() {
        Assert.assertFalse(rangerAuthorizer.authorize(bob, aliceDatasource, Action.READ).isAllowed());
    }
}
