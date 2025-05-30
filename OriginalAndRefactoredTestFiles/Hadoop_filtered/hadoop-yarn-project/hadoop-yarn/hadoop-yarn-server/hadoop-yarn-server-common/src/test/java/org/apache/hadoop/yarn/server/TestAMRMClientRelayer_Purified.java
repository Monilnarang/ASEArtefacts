package org.apache.hadoop.yarn.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.ApplicationMasterProtocol;
import org.apache.hadoop.yarn.api.protocolrecords.AllocateRequest;
import org.apache.hadoop.yarn.api.protocolrecords.AllocateResponse;
import org.apache.hadoop.yarn.api.protocolrecords.FinishApplicationMasterRequest;
import org.apache.hadoop.yarn.api.protocolrecords.FinishApplicationMasterResponse;
import org.apache.hadoop.yarn.api.protocolrecords.RegisterApplicationMasterRequest;
import org.apache.hadoop.yarn.api.protocolrecords.RegisterApplicationMasterResponse;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.ExecutionType;
import org.apache.hadoop.yarn.api.records.ExecutionTypeRequest;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.ResourceBlacklistRequest;
import org.apache.hadoop.yarn.api.records.ResourceRequest;
import org.apache.hadoop.yarn.client.AMRMClientUtils;
import org.apache.hadoop.yarn.exceptions.ApplicationMasterNotRegisteredException;
import org.apache.hadoop.yarn.exceptions.InvalidApplicationMasterRequestException;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.server.scheduler.ResourceRequestSet;
import org.apache.hadoop.yarn.util.Records;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestAMRMClientRelayer_Purified {

    public static class MockApplicationMasterService implements ApplicationMasterProtocol {

        private boolean failover = false;

        private boolean throwAlreadyRegister = false;

        private int responseIdReset = -1;

        private List<ResourceRequest> lastAsk;

        private List<ContainerId> lastRelease;

        private List<String> lastBlacklistAdditions;

        private List<String> lastBlacklistRemovals;

        @Override
        public RegisterApplicationMasterResponse registerApplicationMaster(RegisterApplicationMasterRequest request) throws YarnException, IOException {
            if (this.throwAlreadyRegister) {
                this.throwAlreadyRegister = false;
                throw new InvalidApplicationMasterRequestException(AMRMClientUtils.APP_ALREADY_REGISTERED_MESSAGE + "appId");
            }
            return null;
        }

        @Override
        public FinishApplicationMasterResponse finishApplicationMaster(FinishApplicationMasterRequest request) throws YarnException, IOException {
            if (this.failover) {
                this.failover = false;
                throw new ApplicationMasterNotRegisteredException("Mock RM restarted");
            }
            return null;
        }

        @Override
        public AllocateResponse allocate(AllocateRequest request) throws YarnException, IOException {
            if (this.failover) {
                this.failover = false;
                throw new ApplicationMasterNotRegisteredException("Mock RM restarted");
            }
            if (this.responseIdReset != -1) {
                String errorMessage = AMRMClientUtils.assembleInvalidResponseIdExceptionMessage(null, this.responseIdReset, request.getResponseId());
                this.responseIdReset = -1;
                throw new InvalidApplicationMasterRequestException(errorMessage);
            }
            this.lastAsk = request.getAskList();
            this.lastRelease = request.getReleaseList();
            this.lastBlacklistAdditions = request.getResourceBlacklistRequest().getBlacklistAdditions();
            this.lastBlacklistRemovals = request.getResourceBlacklistRequest().getBlacklistRemovals();
            return AllocateResponse.newInstance(request.getResponseId() + 1, null, null, new ArrayList<NodeReport>(), Resource.newInstance(0, 0), null, 0, null, null);
        }

        public void setFailoverFlag() {
            this.failover = true;
        }

        public void setThrowAlreadyRegister() {
            this.throwAlreadyRegister = true;
        }

        public void setResponseIdReset(int expectedResponseId) {
            this.responseIdReset = expectedResponseId;
        }
    }

    private Configuration conf;

    private MockApplicationMasterService mockAMS;

    private AMRMClientRelayer relayer;

    private int responseId = 0;

    private List<ResourceRequest> asks = new ArrayList<>();

    private List<ContainerId> releases = new ArrayList<>();

    private List<String> blacklistAdditions = new ArrayList<>();

    private List<String> blacklistRemoval = new ArrayList<>();

    @Before
    public void setup() throws YarnException, IOException {
        this.conf = new Configuration();
        this.mockAMS = new MockApplicationMasterService();
        this.relayer = new AMRMClientRelayer(this.mockAMS, null, "TEST");
        this.relayer.registerApplicationMaster(RegisterApplicationMasterRequest.newInstance("", 0, ""));
        clearAllocateRequestLists();
    }

    @After
    public void cleanup() {
        this.relayer.shutdown();
    }

    private void assertAsksAndReleases(int expectedAsk, int expectedRelease) {
        Assert.assertEquals(expectedAsk, this.mockAMS.lastAsk.size());
        Assert.assertEquals(expectedRelease, this.mockAMS.lastRelease.size());
    }

    private void assertBlacklistAdditionsAndRemovals(int expectedAdditions, int expectedRemovals) {
        Assert.assertEquals(expectedAdditions, this.mockAMS.lastBlacklistAdditions.size());
        Assert.assertEquals(expectedRemovals, this.mockAMS.lastBlacklistRemovals.size());
    }

    private AllocateRequest getAllocateRequest() {
        return AllocateRequest.newInstance(responseId, 0, asks, releases, ResourceBlacklistRequest.newInstance(blacklistAdditions, blacklistRemoval));
    }

    private void clearAllocateRequestLists() {
        this.asks.clear();
        this.releases.clear();
        this.blacklistAdditions.clear();
        this.blacklistRemoval.clear();
    }

    private static ContainerId createContainerId(int id) {
        return ContainerId.newContainerId(ApplicationAttemptId.newInstance(ApplicationId.newInstance(1, 1), 1), id);
    }

    protected ResourceRequest createResourceRequest(long id, String resource, int memory, int vCores, int priority, ExecutionType execType, int containers) {
        ResourceRequest req = Records.newRecord(ResourceRequest.class);
        req.setAllocationRequestId(id);
        req.setResourceName(resource);
        req.setCapability(Resource.newInstance(memory, vCores));
        req.setPriority(Priority.newInstance(priority));
        req.setExecutionTypeRequest(ExecutionTypeRequest.newInstance(execType));
        req.setNumContainers(containers);
        return req;
    }

    @Test
    public void testResendRequestsOnRMRestart_1() throws YarnException, IOException {
        assertAsksAndReleases(3, 1);
    }

    @Test
    public void testResendRequestsOnRMRestart_2() throws YarnException, IOException {
        assertBlacklistAdditionsAndRemovals(1, 1);
    }

    @Test
    public void testResendRequestsOnRMRestart_3() throws YarnException, IOException {
        assertAsksAndReleases(0, 0);
    }

    @Test
    public void testResendRequestsOnRMRestart_4() throws YarnException, IOException {
        assertBlacklistAdditionsAndRemovals(0, 0);
    }

    @Test
    public void testResendRequestsOnRMRestart_5() throws YarnException, IOException {
        assertAsksAndReleases(3, 2);
    }

    @Test
    public void testResendRequestsOnRMRestart_6() throws YarnException, IOException {
        assertBlacklistAdditionsAndRemovals(2, 0);
    }
}
