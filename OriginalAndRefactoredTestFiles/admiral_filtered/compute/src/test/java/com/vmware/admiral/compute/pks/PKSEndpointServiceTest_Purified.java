package com.vmware.admiral.compute.pks;

import static com.vmware.admiral.compute.ContainerHostService.CONTAINER_HOST_TYPE_PROP_NAME;
import static com.vmware.admiral.compute.ContainerHostService.HOST_DOCKER_ADAPTER_TYPE_PROP_NAME;
import static com.vmware.admiral.compute.cluster.ClusterService.CLUSTER_NAME_CUSTOM_PROP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.adapter.pks.PKSConstants;
import com.vmware.admiral.common.ManagementUriParts;
import com.vmware.admiral.common.test.CommonTestStateFactory;
import com.vmware.admiral.common.util.CertificateUtilExtended;
import com.vmware.admiral.common.util.OperationUtil;
import com.vmware.admiral.common.util.QueryUtil;
import com.vmware.admiral.compute.ContainerHostService.ContainerHostSpec;
import com.vmware.admiral.compute.ContainerHostService.DockerAdapterType;
import com.vmware.admiral.compute.cluster.ClusterService;
import com.vmware.admiral.compute.cluster.ClusterService.ClusterDto;
import com.vmware.admiral.compute.container.ComputeBaseTest;
import com.vmware.admiral.compute.pks.PKSEndpointService.Endpoint;
import com.vmware.admiral.compute.pks.PKSEndpointService.Endpoint.PlanSet;
import com.vmware.admiral.service.common.SslTrustCertificateService;
import com.vmware.admiral.service.common.SslTrustCertificateService.SslTrustCertificateState;
import com.vmware.admiral.service.test.MockKubernetesHostAdapterService;
import com.vmware.photon.controller.model.resources.ComputeService;
import com.vmware.xenon.common.LocalizableValidationException;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceDocumentQueryResult;
import com.vmware.xenon.common.ServiceErrorResponse;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.Utils;
import com.vmware.xenon.common.test.TestRequestSender;

public class PKSEndpointServiceTest_Purified extends ComputeBaseTest {

    private TestRequestSender sender;

    @Before
    public void setUp() throws Throwable {
        waitForServiceAvailability(PKSEndpointFactoryService.SELF_LINK);
        sender = host.getTestRequestSender();
        host.startService(Operation.createPost(UriUtils.buildUri(host, MockKubernetesHostAdapterService.class)), new MockKubernetesHostAdapterService());
        waitForServiceAvailability(MockKubernetesHostAdapterService.SELF_LINK);
    }

    private List<Endpoint> listEndpoints(String projectHeader) {
        URI uri = UriUtils.extendUriWithQuery(UriUtils.buildUri(host, PKSEndpointFactoryService.SELF_LINK), UriUtils.URI_PARAM_ODATA_EXPAND, Boolean.toString(true));
        Operation get = Operation.createGet(uri).setReferer("/");
        if (projectHeader != null && !projectHeader.isEmpty()) {
            get.addRequestHeader(OperationUtil.PROJECT_ADMIRAL_HEADER, projectHeader);
        }
        ServiceDocumentQueryResult result = sender.sendAndWait(get, ServiceDocumentQueryResult.class);
        assertNotNull(result);
        assertNotNull(result.documents);
        return result.documents.values().stream().map(o -> Utils.fromJson(Utils.toJson(o), Endpoint.class)).collect(Collectors.toList());
    }

    private void assertListConsistsOfEndpointsByName(List<Endpoint> endpoints, String... endpointNames) {
        if (endpoints == null || endpoints.isEmpty()) {
            assertTrue("list of endpoints is null or empty but list of expected endpoint names is not empty", endpointNames == null || endpointNames.length == 0);
            return;
        }
        assertNotNull("list of endpoint names is null but list of endpoints is not", endpointNames);
        assertEquals("number of endpoints does not match number of expected endpoint names", endpointNames.length, endpoints.size());
        for (String name : endpointNames) {
            assertTrue("list of endpoints does not contain an endpoint with name " + name, endpoints.stream().anyMatch(ep -> name.equals(ep.name)));
        }
    }

    private Endpoint createEndpoint(Endpoint endpoint) {
        Operation o = Operation.createPost(host, PKSEndpointFactoryService.SELF_LINK).setBodyNoCloning(endpoint);
        Endpoint result = sender.sendAndWait(o, Endpoint.class);
        assertNotNull(result);
        assertNotNull(result.documentSelfLink);
        assertEquals(endpoint.uaaEndpoint, result.uaaEndpoint);
        assertEquals(endpoint.apiEndpoint, result.apiEndpoint);
        return result;
    }

    private void createEndpointExpectFailure(Endpoint e, Consumer<ServiceErrorResponse> consumer) {
        Operation o = Operation.createPost(host, PKSEndpointFactoryService.SELF_LINK).setBodyNoCloning(e);
        TestRequestSender.FailureResponse failure = sender.sendAndWaitFailure(o);
        assertTrue(failure.failure instanceof LocalizableValidationException);
        ServiceErrorResponse errorResponse = failure.op.getBody(ServiceErrorResponse.class);
        assertNotNull(errorResponse);
        consumer.accept(errorResponse);
    }

    private Endpoint updateEndpoint(Endpoint patch, BiConsumer<Operation, Endpoint> consumer) {
        Operation o = Operation.createPatch(host, patch.documentSelfLink).setBodyNoCloning(patch);
        o = sender.sendAndWait(o);
        assertNotNull(o);
        Operation get = Operation.createGet(host, patch.documentSelfLink);
        Endpoint e = sender.sendAndWait(get, Endpoint.class);
        assertNotNull(e);
        if (consumer != null) {
            consumer.accept(o, e);
        }
        return e;
    }

    private void assertPlanAssignmentEntryEquals(Set<String> expectedPlans, PlanSet actualPlans) {
        if (expectedPlans == null || expectedPlans.isEmpty()) {
            assertTrue("there are no expected plans but some plans were actually returned", actualPlans == null || actualPlans.plans == null || actualPlans.plans.isEmpty());
        }
        assertNotNull("actualPlans are null but plans are expected", actualPlans);
        assertNotNull("actualPlans.plans are null but plans are expected", actualPlans.plans);
        assertEquals("unexpected number of plans", expectedPlans.size(), actualPlans.plans.size());
        expectedPlans.forEach(expectedPlan -> {
            assertTrue("expected plan was not found: " + expectedPlan, actualPlans.plans.stream().anyMatch(plan -> expectedPlan.equals(plan)));
        });
    }

    private ContainerHostSpec createContainerHostSpec(final String endpointLink) {
        ContainerHostSpec clusterSpec = new ContainerHostSpec();
        ComputeService.ComputeState computeState = new ComputeService.ComputeState();
        computeState.tenantLinks = Collections.singletonList(UriUtils.buildUriPath(ManagementUriParts.PROJECTS, "test"));
        computeState.customProperties = new HashMap<>();
        computeState.customProperties.put(CLUSTER_NAME_CUSTOM_PROP, "test-cluster");
        computeState.customProperties.put(CONTAINER_HOST_TYPE_PROP_NAME, ClusterService.ClusterType.KUBERNETES.name());
        computeState.customProperties.put(HOST_DOCKER_ADAPTER_TYPE_PROP_NAME, DockerAdapterType.API.name());
        computeState.customProperties.put(ClusterService.CREATE_EMPTY_CLUSTER_PROP, "true");
        computeState.customProperties.put(ClusterService.ENFORCED_CLUSTER_STATUS_PROP, ClusterService.ClusterStatus.PROVISIONING.name());
        computeState.customProperties.put(PKSConstants.PKS_ENDPOINT_PROP_NAME, endpointLink);
        clusterSpec.hostState = computeState;
        return clusterSpec;
    }

    private ClusterDto createCluster(String endpointLink) {
        ContainerHostSpec hostSpec = createContainerHostSpec(endpointLink);
        ArrayList<ClusterDto> result = new ArrayList<>(1);
        Operation create = Operation.createPost(host, ClusterService.SELF_LINK).setReferer(host.getUri()).setBody(hostSpec).setCompletion((o, ex) -> {
            if (ex != null) {
                host.log(Level.SEVERE, "Failed to create cluster: %s", Utils.toString(ex));
                host.failIteration(ex);
            } else {
                try {
                    result.add(o.getBody(ClusterDto.class));
                    host.completeIteration();
                } catch (Throwable er) {
                    host.log(Level.SEVERE, "Failed to retrieve created cluster DTO from response: %s", Utils.toString(er));
                    host.failIteration(er);
                }
            }
        });
        host.testStart(1);
        host.send(create);
        host.testWait();
        assertEquals(1, result.size());
        ClusterDto dto = result.iterator().next();
        assertNotNull(dto);
        return dto;
    }

    private SslTrustCertificateState createSslTrustCert() throws Throwable {
        String sslTrustPem = CommonTestStateFactory.getFileContent("certs/ca.pem").trim();
        SslTrustCertificateState sslTrustCert = new SslTrustCertificateState();
        sslTrustCert.certificate = sslTrustPem;
        sslTrustCert = doPost(sslTrustCert, SslTrustCertificateService.FACTORY_LINK);
        return sslTrustCert;
    }

    private long getTrustCertsCount() throws Throwable {
        Operation get = Operation.createGet(host, SslTrustCertificateService.FACTORY_LINK).setReferer(host.getUri());
        return host.sendWithDeferredResult(get, ServiceDocumentQueryResult.class).toCompletionStage().toCompletableFuture().get().documentCount;
    }

    @Test
    public void testListFilterByProjectHeader_1_testMerged_1() {
        final String epName1 = "ep-in-project-1";
        final String projectLink1 = QueryUtil.PROJECT_IDENTIFIER + "project-1";
        final String epName2 = "ep-in-project-2";
        final String projectLink2 = QueryUtil.PROJECT_IDENTIFIER + "project-2";
        final String epName3 = "ep-no-project";
        endpoint1.tenantLinks = Collections.singletonList(projectLink1);
        endpoint2.tenantLinks = Collections.singletonList(projectLink2);
        assertListConsistsOfEndpointsByName(listEndpoints(null), epName1, epName2, epName3);
        assertListConsistsOfEndpointsByName(listEndpoints(projectLink1), epName1);
        assertListConsistsOfEndpointsByName(listEndpoints(projectLink2), epName2);
    }

    @Test
    public void testListFilterByProjectHeader_4() {
        assertListConsistsOfEndpointsByName(listEndpoints(QueryUtil.PROJECT_IDENTIFIER + "wrong-project"), (String[]) null);
    }
}
