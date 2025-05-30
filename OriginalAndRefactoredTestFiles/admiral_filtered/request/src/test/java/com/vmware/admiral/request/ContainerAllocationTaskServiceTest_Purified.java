package com.vmware.admiral.request;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.internal.ComparisonCriteria;
import com.vmware.admiral.adapter.common.ContainerOperationType;
import com.vmware.admiral.adapter.docker.util.DockerPortMapping;
import com.vmware.admiral.compute.ContainerHostService;
import com.vmware.admiral.compute.ResourceType;
import com.vmware.admiral.compute.container.ContainerDescriptionService;
import com.vmware.admiral.compute.container.ContainerDescriptionService.ContainerDescription;
import com.vmware.admiral.compute.container.ContainerService.ContainerState;
import com.vmware.admiral.compute.container.ContainerService.ContainerState.PowerState;
import com.vmware.admiral.compute.container.HostPortProfileService;
import com.vmware.admiral.compute.container.PortBinding;
import com.vmware.admiral.compute.container.ServiceNetwork;
import com.vmware.admiral.compute.container.SystemContainerDescriptions;
import com.vmware.admiral.request.ContainerAllocationTaskService.AllocationExtensibilityCallbackResponse;
import com.vmware.admiral.request.ContainerAllocationTaskService.ContainerAllocationTaskState;
import com.vmware.admiral.request.ContainerAllocationTaskService.ContainerAllocationTaskState.SubStage;
import com.vmware.admiral.request.RequestBrokerService.RequestBrokerState;
import com.vmware.admiral.request.allocation.filter.HostSelectionFilter.HostSelection;
import com.vmware.admiral.request.util.TestRequestStateFactory;
import com.vmware.admiral.request.utils.RequestUtils;
import com.vmware.admiral.service.common.ServiceTaskCallback;
import com.vmware.admiral.service.test.MockDockerAdapterService;
import com.vmware.photon.controller.model.resources.ComputeDescriptionService;
import com.vmware.photon.controller.model.resources.ComputeDescriptionService.ComputeDescription;
import com.vmware.photon.controller.model.resources.ComputeDescriptionService.ComputeDescription.ComputeType;
import com.vmware.photon.controller.model.resources.ComputeService.ComputeState;
import com.vmware.xenon.common.Service.Action;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.TestContext;

public class ContainerAllocationTaskServiceTest_Purified extends RequestBaseTest {

    private void validatePorts(ContainerDescription containerDescription, ContainerState containerState) throws Throwable {
        hostPortProfileState = getDocument(HostPortProfileService.HostPortProfileState.class, hostPortProfileState.documentSelfLink);
        Set<String> reservedPorts = hostPortProfileState.reservedPorts == null ? new HashSet<>() : hostPortProfileState.reservedPorts.entrySet().stream().filter(p -> containerState.documentSelfLink.equals(p.getValue())).map(p -> p.getKey().toString()).collect(Collectors.toSet());
        assertEquals(containerDescription.portBindings.length, reservedPorts.size());
        assertPortBindingsEquals(containerDescription.portBindings, containerState.ports);
        for (PortBinding requestedPort : containerState.ports) {
            assertTrue(reservedPorts.contains(requestedPort.hostPort));
        }
    }

    private ContainerAllocationTaskState allocate(ContainerAllocationTaskState allocationTask) throws Throwable {
        allocationTask = startAllocationTask(allocationTask);
        host.log("Start allocation test: " + allocationTask.documentSelfLink);
        allocationTask = waitForTaskSuccess(allocationTask.documentSelfLink, ContainerAllocationTaskState.class);
        assertNotNull("ResourceLinks null for allocation: " + allocationTask.documentSelfLink, allocationTask.resourceLinks);
        assertEquals("Resource count not equal for: " + allocationTask.documentSelfLink, allocationTask.resourceCount, Long.valueOf(allocationTask.resourceLinks.size()));
        host.log("Finished allocation test: " + allocationTask.documentSelfLink);
        return allocationTask;
    }

    private ContainerAllocationTaskState createContainerAllocationTask() {
        return createContainerAllocationTask(containerDesc.documentSelfLink, 1);
    }

    private ContainerAllocationTaskState createContainerAllocationTask(String containerDescLink, long resourceCount) {
        ContainerAllocationTaskState allocationTask = new ContainerAllocationTaskState();
        allocationTask.resourceDescriptionLink = containerDescLink;
        allocationTask.groupResourcePlacementLink = groupPlacementState.documentSelfLink;
        allocationTask.resourceType = ResourceType.CONTAINER_TYPE.getName();
        allocationTask.resourceCount = resourceCount;
        allocationTask.serviceTaskCallback = ServiceTaskCallback.createEmpty();
        allocationTask.customProperties = new HashMap<>();
        return allocationTask;
    }

    private ContainerAllocationTaskState startAllocationTask(ContainerAllocationTaskState allocationTask) throws Throwable {
        ContainerAllocationTaskState outAllocationTask = doPost(allocationTask, ContainerAllocationTaskFactoryService.SELF_LINK);
        assertNotNull(outAllocationTask);
        return outAllocationTask;
    }

    private void assertPortBindingsEquals(PortBinding[] expecteds, List<PortBinding> actuals) {
        assertPortBindingsEquals(Arrays.asList(expecteds), actuals);
    }

    private void assertPortBindingsEquals(List<PortBinding> expecteds, List<PortBinding> actuals) {
        new ComparisonCriteria() {

            @Override
            protected void assertElementsEqual(Object expected, Object actual) {
                PortBinding expectedMapping = (PortBinding) expected;
                PortBinding actualMapping = (PortBinding) actual;
                assertEquals("protocol", expectedMapping.protocol, actualMapping.protocol);
                assertEquals("container port", expectedMapping.containerPort, actualMapping.containerPort);
                assertEquals("host ip", expectedMapping.hostIp, actualMapping.hostIp);
                String expectedHostPort = expectedMapping.hostPort;
                if (expectedHostPort != null && !expectedHostPort.isEmpty()) {
                    assertEquals("host port", expectedHostPort, actualMapping.hostPort);
                }
            }
        }.arrayEquals(null, expecteds.toArray(), actuals.toArray());
    }

    private void assertContainerStateAfterAllocation(ContainerAllocationTaskState allocationTask) throws Throwable {
        assertContainerStateAfterAllocation(allocationTask, null);
    }

    private void assertContainerStateAfterAllocation(ContainerAllocationTaskState allocationTask, ContainerDescription containerDesc) throws Throwable {
        ContainerState containerState = getDocument(ContainerState.class, allocationTask.resourceLinks.iterator().next());
        if (containerDesc == null) {
            containerDesc = this.containerDesc;
        }
        assertTrue(containerState.names.get(0).startsWith(containerDesc.name));
        assertNull(containerState.id);
        assertTrue(containerState.documentSelfLink.contains(containerDesc.name));
        assertEquals(containerDesc.documentSelfLink, containerState.descriptionLink);
        assertNull(containerState.created);
        assertEquals(allocationTask.hostSelections.get(0).hostLink, containerState.parentLink);
        assertEquals(groupPlacementState.documentSelfLink, containerState.groupResourcePlacementLink);
        assertEquals(allocationTask.tenantLinks, containerState.tenantLinks);
        assertEquals(containerDesc.instanceAdapterReference.getPath(), containerState.adapterManagementReference.getPath());
        assertEquals(ContainerState.CONTAINER_ALLOCATION_STATUS, containerState.status);
        assertArrayEquals(containerDesc.command, containerState.command);
        assertEquals(containerDesc.image, containerState.image);
        assertTrue(containerState.documentExpirationTimeMicros > 0);
        waitForContainerPowerState(PowerState.PROVISIONING, containerState.documentSelfLink);
        Set<ContainerState> containerStates = getExistingContainersInAdapter();
        for (ContainerState containerInAdapterState : containerStates) {
            if (containerInAdapterState.documentSelfLink.endsWith(containerState.documentSelfLink)) {
                fail("Container State not removed with link: " + containerState.documentSelfLink);
            }
        }
    }

    @Test
    public void testContainerAllocationWithFollowingProvisioningRequest_1() throws Throwable {
        ContainerAllocationTaskState allocationTask = createContainerAllocationTask();
        allocationTask.customProperties.put(RequestUtils.FIELD_NAME_ALLOCATION_REQUEST, Boolean.TRUE.toString());
        allocationTask = allocate(allocationTask);
        assertContainerStateAfterAllocation(allocationTask);
    }

    @Test
    public void testContainerAllocationWithFollowingProvisioningRequest_2() throws Throwable {
        RequestBrokerState provisioningRequest = new RequestBrokerState();
        provisioningRequest = doPost(provisioningRequest, RequestBrokerFactoryService.SELF_LINK);
        assertNotNull(provisioningRequest);
    }

    @Test
    public void testContainerAllocationWithFollowingProvisioningRequestWithHeathCheckIncludedShouldFail_1() throws Throwable {
        ContainerAllocationTaskState allocationTask = createContainerAllocationTask();
        allocationTask.customProperties.put(RequestUtils.FIELD_NAME_ALLOCATION_REQUEST, Boolean.TRUE.toString());
        allocationTask = allocate(allocationTask);
        assertContainerStateAfterAllocation(allocationTask);
    }

    @Test
    public void testContainerAllocationWithFollowingProvisioningRequestWithHeathCheckIncludedShouldFail_2() throws Throwable {
        RequestBrokerState provisioningRequest = new RequestBrokerState();
        provisioningRequest = doPost(provisioningRequest, RequestBrokerFactoryService.SELF_LINK);
        assertNotNull(provisioningRequest);
    }

    @Test
    public void testContainerAllocationWithFollowingProvisioningRequestWithHeathCheckIncludedWhichFailsContinueProvisioning_1() throws Throwable {
        ContainerAllocationTaskState allocationTask = createContainerAllocationTask();
        allocationTask.customProperties.put(RequestUtils.FIELD_NAME_ALLOCATION_REQUEST, Boolean.TRUE.toString());
        allocationTask = allocate(allocationTask);
        assertContainerStateAfterAllocation(allocationTask);
    }

    @Test
    public void testContainerAllocationWithFollowingProvisioningRequestWithHeathCheckIncludedWhichFailsContinueProvisioning_2() throws Throwable {
        RequestBrokerState provisioningRequest = new RequestBrokerState();
        provisioningRequest = doPost(provisioningRequest, RequestBrokerFactoryService.SELF_LINK);
        assertNotNull(provisioningRequest);
    }

    @Test
    public void testClusteredContainerAllocationWithFollowingProvisioningRequestWithHeathCheckIncludedShoudFail_1() throws Throwable {
        final int clusterSize = 5;
        doOperation(containerDesc, UriUtils.buildUri(host, containerDesc.documentSelfLink), false, Action.PUT);
        ContainerAllocationTaskState allocationTask = createContainerAllocationTask(containerDesc.documentSelfLink, clusterSize);
        allocationTask.customProperties.put(RequestUtils.FIELD_NAME_ALLOCATION_REQUEST, Boolean.TRUE.toString());
        allocationTask = allocate(allocationTask);
        assertContainerStateAfterAllocation(allocationTask);
    }

    @Test
    public void testClusteredContainerAllocationWithFollowingProvisioningRequestWithHeathCheckIncludedShoudFail_2() throws Throwable {
        RequestBrokerState provisioningRequest = new RequestBrokerState();
        provisioningRequest = doPost(provisioningRequest, RequestBrokerFactoryService.SELF_LINK);
        assertNotNull(provisioningRequest);
    }

    @Test
    public void testGetMin_1() {
        assertEquals(1, ContainerAllocationTaskService.getMinParam(0, 1L).longValue());
    }

    @Test
    public void testGetMin_2() {
        assertEquals(1, ContainerAllocationTaskService.getMinParam(1, null).longValue());
    }

    @Test
    public void testGetMin_3() {
        assertEquals(1, ContainerAllocationTaskService.getMinParam(2, 1L).longValue());
    }

    @Test
    public void testGetMin_4() {
        assertEquals(1, ContainerAllocationTaskService.getMinParam(1, 2L).longValue());
    }

    @Test
    public void testGetMin_5() {
        assertEquals(0, ContainerAllocationTaskService.getMinParam(0, null).longValue());
    }

    @Test
    public void testGetMin_6() {
        assertEquals(1, ContainerAllocationTaskService.getMinParam(1, 0L).longValue());
    }
}
