package org.apache.hadoop.yarn.server.metrics;

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
import org.apache.hadoop.yarn.api.records.Container;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.ContainerStatus;
import org.apache.hadoop.yarn.api.records.ContainerUpdateType;
import org.apache.hadoop.yarn.api.records.ExecutionType;
import org.apache.hadoop.yarn.api.records.ExecutionTypeRequest;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.ResourceBlacklistRequest;
import org.apache.hadoop.yarn.api.records.ResourceRequest;
import org.apache.hadoop.yarn.api.records.UpdateContainerRequest;
import org.apache.hadoop.yarn.api.records.UpdatedContainer;
import org.apache.hadoop.yarn.exceptions.ApplicationMasterNotRegisteredException;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.server.AMRMClientRelayer;
import org.apache.hadoop.yarn.server.metrics.AMRMClientRelayerMetrics.RequestType;
import org.apache.hadoop.yarn.util.Records;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestAMRMClientRelayerMetrics_Purified {

    public static class MockApplicationMasterService implements ApplicationMasterProtocol {

        private boolean failover = false;

        private boolean exception = false;

        private List<ResourceRequest> lastAsk;

        private List<ContainerId> lastRelease;

        private List<UpdateContainerRequest> lastUpdates;

        private List<String> lastBlacklistAdditions;

        private List<String> lastBlacklistRemovals;

        private AllocateResponse response = AllocateResponse.newInstance(0, null, null, new ArrayList<NodeReport>(), Resource.newInstance(0, 0), null, 0, null, null);

        @Override
        public RegisterApplicationMasterResponse registerApplicationMaster(RegisterApplicationMasterRequest request) throws YarnException, IOException {
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
            if (this.exception) {
                this.exception = false;
                throw new YarnException("Mock RM encountered exception");
            }
            this.lastAsk = request.getAskList();
            this.lastRelease = request.getReleaseList();
            this.lastUpdates = request.getUpdateRequests();
            this.lastBlacklistAdditions = request.getResourceBlacklistRequest().getBlacklistAdditions();
            this.lastBlacklistRemovals = request.getResourceBlacklistRequest().getBlacklistRemovals();
            return response;
        }

        public void setFailoverFlag() {
            this.failover = true;
        }
    }

    private Configuration conf;

    private MockApplicationMasterService mockAMS;

    private String homeID = "home";

    private AMRMClientRelayer homeRelayer;

    private String uamID = "uam";

    private AMRMClientRelayer uamRelayer;

    private List<ResourceRequest> asks = new ArrayList<>();

    private List<ContainerId> releases = new ArrayList<>();

    private List<UpdateContainerRequest> updates = new ArrayList<>();

    private List<String> blacklistAdditions = new ArrayList<>();

    private List<String> blacklistRemoval = new ArrayList<>();

    @Before
    public void setup() throws YarnException, IOException {
        this.conf = new Configuration();
        this.mockAMS = new MockApplicationMasterService();
        this.homeRelayer = new AMRMClientRelayer(this.mockAMS, ApplicationId.newInstance(0, 0), this.homeID);
        this.homeRelayer.registerApplicationMaster(RegisterApplicationMasterRequest.newInstance("", 0, ""));
        this.uamRelayer = new AMRMClientRelayer(this.mockAMS, ApplicationId.newInstance(0, 0), this.uamID);
        this.uamRelayer.registerApplicationMaster(RegisterApplicationMasterRequest.newInstance("", 0, ""));
        clearAllocateRequestLists();
        AMRMClientRelayerMetrics.getInstance().setClientPending(homeID, RequestType.Guaranteed, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(homeID, RequestType.Opportunistic, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(homeID, RequestType.Promote, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(homeID, RequestType.Demote, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(uamID, RequestType.Guaranteed, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(uamID, RequestType.Opportunistic, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(uamID, RequestType.Promote, 0);
        AMRMClientRelayerMetrics.getInstance().setClientPending(uamID, RequestType.Demote, 0);
    }

    private AllocateRequest getAllocateRequest() {
        return AllocateRequest.newBuilder().responseId(0).progress(0).askList(asks).releaseList(new ArrayList<>(this.releases)).resourceBlacklistRequest(ResourceBlacklistRequest.newInstance(new ArrayList<>(this.blacklistAdditions), new ArrayList<>(this.blacklistRemoval))).updateRequests(new ArrayList<>(this.updates)).build();
    }

    private void clearAllocateRequestLists() {
        this.asks.clear();
        this.releases.clear();
        this.updates.clear();
        this.blacklistAdditions.clear();
        this.blacklistRemoval.clear();
    }

    private static UpdateContainerRequest createPromote(int id) {
        return UpdateContainerRequest.newInstance(0, createContainerId(id), ContainerUpdateType.PROMOTE_EXECUTION_TYPE, Resource.newInstance(0, 0), ExecutionType.GUARANTEED);
    }

    private static UpdateContainerRequest createDemote(int id) {
        return UpdateContainerRequest.newInstance(0, createContainerId(id), ContainerUpdateType.DEMOTE_EXECUTION_TYPE, Resource.newInstance(0, 0), ExecutionType.OPPORTUNISTIC);
    }

    private static ContainerId createContainerId(int id) {
        return ContainerId.newContainerId(ApplicationAttemptId.newInstance(ApplicationId.newInstance(1, 1), 1), id);
    }

    public ResourceRequest createResourceRequest(long id, String resource, int memory, int vCores, int priority, ExecutionType execType, int containers) {
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
    public void testGPending_1_testMerged_1() throws YarnException, IOException {
        Assert.assertEquals(2, AMRMClientRelayerMetrics.getInstance().getPendingMetric(homeID, RequestType.Guaranteed).value());
    }

    @Test
    public void testGPending_2() throws YarnException, IOException {
        Assert.assertEquals(0, AMRMClientRelayerMetrics.getInstance().getPendingMetric(uamID, RequestType.Guaranteed).value());
    }

    @Test
    public void testGPending_4_testMerged_3() throws YarnException, IOException {
        Assert.assertEquals(2, AMRMClientRelayerMetrics.getInstance().getPendingMetric(uamID, RequestType.Guaranteed).value());
    }

    @Test
    public void testGPending_5() throws YarnException, IOException {
        Assert.assertEquals(3, AMRMClientRelayerMetrics.getInstance().getPendingMetric(homeID, RequestType.Guaranteed).value());
    }

    @Test
    public void testCleanUpOnFinish_1() throws YarnException, IOException {
        Assert.assertEquals(0, AMRMClientRelayerMetrics.getInstance().getPendingMetric(homeID, RequestType.Guaranteed).value());
    }

    @Test
    public void testCleanUpOnFinish_2() throws YarnException, IOException {
        Assert.assertEquals(0, AMRMClientRelayerMetrics.getInstance().getPendingMetric(homeID, RequestType.Promote).value());
    }

    @Test
    public void testNewEmptyRequest_1() throws YarnException, IOException {
        Assert.assertEquals(0, AMRMClientRelayerMetrics.getInstance().getPendingMetric(homeID, RequestType.Guaranteed).value());
    }

    @Test
    public void testNewEmptyRequest_2() throws YarnException, IOException {
        Assert.assertEquals(0, AMRMClientRelayerMetrics.getInstance().getPendingMetric(uamID, RequestType.Guaranteed).value());
    }
}
