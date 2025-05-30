package com.vmware.admiral.request;

import static com.vmware.admiral.compute.container.CompositeDescriptionCloneService.REVERSE_PARENT_LINKS_PARAM;
import static com.vmware.admiral.request.utils.RequestUtils.FIELD_NAME_ALLOCATION_REQUEST;
import static com.vmware.admiral.request.utils.RequestUtils.FIELD_NAME_CONTEXT_ID_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import org.junit.Test;
import com.vmware.admiral.adapter.common.ContainerOperationType;
import com.vmware.admiral.auth.idm.AuthRole;
import com.vmware.admiral.auth.idm.SecurityContext;
import com.vmware.admiral.common.DeploymentProfileConfig;
import com.vmware.admiral.common.ManagementUriParts;
import com.vmware.admiral.common.test.CommonTestStateFactory;
import com.vmware.admiral.common.util.QueryUtil;
import com.vmware.admiral.common.util.ServiceDocumentQuery;
import com.vmware.admiral.common.util.UriUtilsExtended;
import com.vmware.admiral.compute.ComputeConstants;
import com.vmware.admiral.compute.ContainerHostService;
import com.vmware.admiral.compute.ResourceType;
import com.vmware.admiral.compute.container.CompositeComponentFactoryService;
import com.vmware.admiral.compute.container.CompositeComponentService.CompositeComponent;
import com.vmware.admiral.compute.container.CompositeDescriptionCloneService;
import com.vmware.admiral.compute.container.CompositeDescriptionService;
import com.vmware.admiral.compute.container.CompositeDescriptionService.CompositeDescription;
import com.vmware.admiral.compute.container.ContainerDescriptionService;
import com.vmware.admiral.compute.container.ContainerDescriptionService.ContainerDescription;
import com.vmware.admiral.compute.container.ContainerFactoryService;
import com.vmware.admiral.compute.container.ContainerService.ContainerState;
import com.vmware.admiral.compute.container.GroupResourcePlacementService;
import com.vmware.admiral.compute.container.GroupResourcePlacementService.GroupResourcePlacementState;
import com.vmware.admiral.compute.container.ServiceNetwork;
import com.vmware.admiral.compute.container.network.ContainerNetworkDescriptionService;
import com.vmware.admiral.compute.container.network.ContainerNetworkDescriptionService.ContainerNetworkDescription;
import com.vmware.admiral.compute.container.network.ContainerNetworkService;
import com.vmware.admiral.compute.container.network.ContainerNetworkService.ContainerNetworkState;
import com.vmware.admiral.compute.container.network.Ipam;
import com.vmware.admiral.compute.container.network.IpamConfig;
import com.vmware.admiral.compute.container.network.NetworkUtils;
import com.vmware.admiral.compute.container.volume.ContainerVolumeDescriptionService;
import com.vmware.admiral.compute.container.volume.ContainerVolumeDescriptionService.ContainerVolumeDescription;
import com.vmware.admiral.compute.container.volume.ContainerVolumeService;
import com.vmware.admiral.compute.container.volume.ContainerVolumeService.ContainerVolumeState;
import com.vmware.admiral.compute.container.volume.VolumeUtil;
import com.vmware.admiral.log.EventLogService.EventLogState;
import com.vmware.admiral.log.EventLogService.EventLogState.EventLogType;
import com.vmware.admiral.request.ContainerAllocationTaskService.ContainerAllocationTaskState;
import com.vmware.admiral.request.RequestBrokerService.RequestBrokerState;
import com.vmware.admiral.request.RequestStatusService.RequestStatus;
import com.vmware.admiral.request.ReservationTaskService.ReservationTaskState;
import com.vmware.admiral.request.composition.CompositionSubTaskService;
import com.vmware.admiral.request.compute.ComputeOperationType;
import com.vmware.admiral.request.util.TestRequestStateFactory;
import com.vmware.admiral.service.common.RegistryService;
import com.vmware.admiral.service.test.MockDockerAdapterService;
import com.vmware.admiral.service.test.MockDockerNetworkAdapterService;
import com.vmware.admiral.service.test.MockDockerNetworkToHostService;
import com.vmware.admiral.service.test.MockDockerNetworkToHostService.MockDockerNetworkToHostState;
import com.vmware.admiral.service.test.MockDockerVolumeToHostService;
import com.vmware.admiral.service.test.MockDockerVolumeToHostService.MockDockerVolumeToHostState;
import com.vmware.photon.controller.model.resources.ComputeDescriptionService;
import com.vmware.photon.controller.model.resources.ComputeDescriptionService.ComputeDescription;
import com.vmware.photon.controller.model.resources.ComputeService;
import com.vmware.photon.controller.model.resources.ComputeService.ComputeState;
import com.vmware.photon.controller.model.resources.ComputeService.PowerState;
import com.vmware.photon.controller.model.resources.ResourcePoolService;
import com.vmware.photon.controller.model.resources.ResourcePoolService.ResourcePoolState;
import com.vmware.xenon.common.DeferredResult;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.Service;
import com.vmware.xenon.common.Service.Action;
import com.vmware.xenon.common.ServiceDocumentQueryResult;
import com.vmware.xenon.common.TaskState.TaskStage;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.services.common.AuthCredentialsService;
import com.vmware.xenon.services.common.QueryTask;

public class RequestBrokerServiceTest_Purified extends RequestBaseTest {

    private List<ContainerState> getAllContainers(String computeSelfLink) {
        host.testStart(1);
        List<ContainerState> containerStateList = new ArrayList<>();
        QueryTask.Query.Builder queryBuilder = QueryTask.Query.Builder.create().addKindFieldClause(ContainerState.class).addFieldClause(ContainerState.FIELD_NAME_PARENT_LINK, computeSelfLink);
        QueryTask containerStateQuery = QueryTask.Builder.create().setQuery(queryBuilder.build()).build();
        QueryUtil.addExpandOption(containerStateQuery);
        new ServiceDocumentQuery<>(host, ContainerState.class).query(containerStateQuery, (r) -> {
            if (r.hasException()) {
                host.failIteration(r.getException());
            } else if (r.hasResult()) {
                containerStateList.add(r.getResult());
            } else {
                host.completeIteration();
            }
        });
        host.testWait();
        return containerStateList;
    }

    private CompositeComponent setUpCompositeWithServiceLinks(boolean includeNetwork) throws Throwable {
        ResourcePoolState resourcePool = createResourcePool();
        ComputeDescription dockerHostDesc = createDockerHostDescription();
        delete(computeHost.documentSelfLink);
        computeHost = null;
        ComputeState dockerHost1 = createDockerHost(dockerHostDesc, resourcePool, true);
        addForDeletion(dockerHost1);
        ComputeState dockerHost2 = createDockerHost(dockerHostDesc, resourcePool, true);
        addForDeletion(dockerHost2);
        ContainerDescription container1Desc = TestRequestStateFactory.createContainerDescription("Container1");
        container1Desc.documentSelfLink = UUID.randomUUID().toString();
        container1Desc.portBindings = null;
        ContainerDescription container2Desc = TestRequestStateFactory.createContainerDescription("Container2");
        container2Desc.documentSelfLink = UUID.randomUUID().toString();
        container2Desc.links = new String[] { "Container1:mycontainer" };
        container1Desc.portBindings = null;
        CompositeDescription compositeDesc;
        if (includeNetwork) {
            ContainerNetworkDescription networkDesc = TestRequestStateFactory.createContainerNetworkDescription("TestNet");
            networkDesc.documentSelfLink = UUID.randomUUID().toString();
            container1Desc.networks = Collections.singletonMap(networkDesc.name, new ServiceNetwork());
            container2Desc.networks = Collections.singletonMap(networkDesc.name, new ServiceNetwork());
            compositeDesc = createCompositeDesc(networkDesc, container1Desc, container2Desc);
        } else {
            compositeDesc = createCompositeDesc(container1Desc, container2Desc);
        }
        assertNotNull(compositeDesc);
        GroupResourcePlacementState groupPlacememtState = createGroupResourcePlacement(resourcePool);
        RequestBrokerState request = TestRequestStateFactory.createRequestState(ResourceType.COMPOSITE_COMPONENT_TYPE.getName(), compositeDesc.documentSelfLink);
        request.tenantLinks = groupPlacememtState.tenantLinks;
        host.log("########  Start of request ######## ");
        request = startRequest(request);
        request = waitForRequestToComplete(request);
        return getDocument(CompositeComponent.class, request.resourceLinks.iterator().next());
    }

    private void addNetworkToMockAdapter(String hostLink, String networkId, String networkNames) throws Throwable {
        MockDockerNetworkToHostState mockNetworkToHostState = new MockDockerNetworkToHostState();
        mockNetworkToHostState.documentSelfLink = UriUtils.buildUriPath(MockDockerNetworkToHostService.FACTORY_LINK, UUID.randomUUID().toString());
        mockNetworkToHostState.hostLink = hostLink;
        mockNetworkToHostState.id = networkId;
        mockNetworkToHostState.name = networkNames;
        host.sendRequest(Operation.createPost(host, MockDockerNetworkToHostService.FACTORY_LINK).setBody(mockNetworkToHostState).setReferer(host.getUri()).setCompletion((o, e) -> {
            if (e != null) {
                host.log("Cannot create mock network to host state. Error: %s", e.getMessage());
            }
        }));
        waitFor(() -> {
            getDocument(MockDockerNetworkToHostState.class, mockNetworkToHostState.documentSelfLink);
            return true;
        });
    }

    private void addVolumeToHost(String hostLink, String volumeName, String driver, String scope) throws Throwable {
        MockDockerVolumeToHostState mockVolumeToHostState = new MockDockerVolumeToHostState();
        mockVolumeToHostState.documentSelfLink = UriUtils.buildUriPath(MockDockerVolumeToHostService.FACTORY_LINK, UUID.randomUUID().toString());
        mockVolumeToHostState.name = volumeName;
        mockVolumeToHostState.hostLink = hostLink;
        mockVolumeToHostState.driver = driver;
        mockVolumeToHostState.scope = scope;
        host.sendRequest(Operation.createPost(host, MockDockerVolumeToHostService.FACTORY_LINK).setBody(mockVolumeToHostState).setReferer(host.getUri()).setCompletion((o, e) -> {
            if (e != null) {
                host.log("Cannot create mock volume to host state. Error: %s", e.getMessage());
            }
        }));
        waitFor(() -> {
            getDocument(MockDockerVolumeToHostState.class, mockVolumeToHostState.documentSelfLink);
            return true;
        });
    }

    @Test
    public void testRequestLifeCycleWithCreateTemplateFromContainer_1_testMerged_1() throws Throwable {
        RequestBrokerState request = TestRequestStateFactory.createRequestState();
        request = startRequest(request);
        request = waitForRequestToComplete(request);
        ContainerState containerState = getDocument(ContainerState.class, request.resourceLinks.iterator().next());
        assertNotNull(containerState);
        compositeDesc.name = containerState.names.iterator().next();
        request = TestRequestStateFactory.createRequestState();
        request.resourceLinks.add(containerState.documentSelfLink);
        containerState = searchForDocument(ContainerState.class, request.resourceLinks.iterator().next());
        assertNull(containerState);
    }

    @Test
    public void testRequestLifeCycleWithCreateTemplateFromContainer_3_testMerged_2() throws Throwable {
        ContainerDescription containerDesc = createContainerDescription();
        CompositeDescription compositeDesc = new CompositeDescription();
        compositeDesc.descriptionLinks.add(containerDesc.documentSelfLink);
        compositeDesc = doPost(compositeDesc, CompositeDescriptionService.FACTORY_LINK);
        CompositeDescription clonedCompositeDesc = doPost(compositeDesc, CompositeDescriptionCloneService.SELF_LINK + UriUtils.URI_QUERY_CHAR + UriUtils.buildUriQuery(REVERSE_PARENT_LINKS_PARAM, "true"));
        containerDesc = searchForDocument(ContainerDescription.class, containerDesc.documentSelfLink);
        assertNull(containerDesc);
        clonedCompositeDesc = getDocument(CompositeDescription.class, clonedCompositeDesc.documentSelfLink);
        assertNotNull(clonedCompositeDesc);
    }
}
