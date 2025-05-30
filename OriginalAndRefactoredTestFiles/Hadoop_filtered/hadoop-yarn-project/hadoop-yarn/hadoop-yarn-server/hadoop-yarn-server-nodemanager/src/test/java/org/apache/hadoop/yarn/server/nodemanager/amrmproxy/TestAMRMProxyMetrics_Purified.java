package org.apache.hadoop.yarn.server.nodemanager.amrmproxy;

import org.apache.hadoop.yarn.api.protocolrecords.AllocateResponse;
import org.apache.hadoop.yarn.api.protocolrecords.FinishApplicationMasterResponse;
import org.apache.hadoop.yarn.api.protocolrecords.RegisterApplicationMasterResponse;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAMRMProxyMetrics_Purified extends BaseAMRMProxyTest {

    public static final Logger LOG = LoggerFactory.getLogger(TestAMRMProxyMetrics.class);

    private static AMRMProxyMetrics metrics;

    @BeforeClass
    public static void init() {
        metrics = AMRMProxyMetrics.getMetrics();
        LOG.info("Test: aggregate metrics are initialized correctly");
        Assert.assertEquals(0, metrics.getFailedAppStartRequests());
        Assert.assertEquals(0, metrics.getFailedRegisterAMRequests());
        Assert.assertEquals(0, metrics.getFailedFinishAMRequests());
        Assert.assertEquals(0, metrics.getFailedAllocateRequests());
        Assert.assertEquals(0, metrics.getFailedAppRecoveryCount());
        Assert.assertEquals(0, metrics.getFailedAppStopRequests());
        Assert.assertEquals(0, metrics.getFailedUpdateAMRMTokenRequests());
        Assert.assertEquals(0, metrics.getAllocateCount());
        Assert.assertEquals(0, metrics.getRequestCount());
        Assert.assertEquals(0, metrics.getNumSucceededAppStartRequests());
        Assert.assertEquals(0, metrics.getNumSucceededRegisterAMRequests());
        Assert.assertEquals(0, metrics.getNumSucceededFinishAMRequests());
        Assert.assertEquals(0, metrics.getNumSucceededAllocateRequests());
        Assert.assertEquals(0, metrics.getNumSucceededRecoverRequests());
        Assert.assertEquals(0, metrics.getNumSucceededAppStopRequests());
        Assert.assertEquals(0, metrics.getNumSucceededUpdateAMRMTokenRequests());
        LOG.info("Test: aggregate metrics are updated correctly");
    }

    @Test
    public void testAllocateRequestWithNullValues_1_testMerged_1() throws Exception {
        int testAppId = 1;
        RegisterApplicationMasterResponse registerResponse = registerApplicationMaster(testAppId);
        Assert.assertNotNull(registerResponse);
        Assert.assertEquals(Integer.toString(testAppId), registerResponse.getQueue());
        AllocateResponse allocateResponse = allocate(testAppId);
        Assert.assertNotNull(allocateResponse);
        FinishApplicationMasterResponse finishResponse = finishApplicationMaster(testAppId, FinalApplicationStatus.SUCCEEDED);
        Assert.assertNotNull(finishResponse);
        Assert.assertEquals(true, finishResponse.getIsUnregistered());
    }

    @Test
    public void testAllocateRequestWithNullValues_6_testMerged_2() throws Exception {
        long failedAppStartRequests = metrics.getFailedAppStartRequests();
        long failedRegisterAMRequests = metrics.getFailedRegisterAMRequests();
        long failedFinishAMRequests = metrics.getFailedFinishAMRequests();
        long failedAllocateRequests = metrics.getFailedAllocateRequests();
        long failedAppRecoveryRequests = metrics.getFailedAppRecoveryCount();
        long failedAppStopRequests = metrics.getFailedAppStopRequests();
        long failedUpdateAMRMTokenRequests = metrics.getFailedUpdateAMRMTokenRequests();
        long succeededAppStartRequests = metrics.getNumSucceededAppStartRequests();
        long succeededRegisterAMRequests = metrics.getNumSucceededRegisterAMRequests();
        long succeededFinishAMRequests = metrics.getNumSucceededFinishAMRequests();
        long succeededAllocateRequests = metrics.getNumSucceededAllocateRequests();
        Assert.assertEquals(failedAppStartRequests, metrics.getFailedAppStartRequests());
        Assert.assertEquals(failedRegisterAMRequests, metrics.getFailedRegisterAMRequests());
        Assert.assertEquals(failedFinishAMRequests, metrics.getFailedFinishAMRequests());
        Assert.assertEquals(failedAllocateRequests, metrics.getFailedAllocateRequests());
        Assert.assertEquals(failedAppRecoveryRequests, metrics.getFailedAppRecoveryCount());
        Assert.assertEquals(failedAppStopRequests, metrics.getFailedAppStopRequests());
        Assert.assertEquals(failedUpdateAMRMTokenRequests, metrics.getFailedUpdateAMRMTokenRequests());
        Assert.assertEquals(succeededAppStartRequests, metrics.getNumSucceededAppStartRequests());
        Assert.assertEquals(1 + succeededRegisterAMRequests, metrics.getNumSucceededRegisterAMRequests());
        Assert.assertEquals(1 + succeededFinishAMRequests, metrics.getNumSucceededFinishAMRequests());
        Assert.assertEquals(1 + succeededAllocateRequests, metrics.getNumSucceededAllocateRequests());
    }
}
