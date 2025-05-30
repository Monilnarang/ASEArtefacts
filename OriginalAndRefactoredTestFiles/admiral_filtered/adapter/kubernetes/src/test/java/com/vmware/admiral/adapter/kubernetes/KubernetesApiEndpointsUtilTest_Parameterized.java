package com.vmware.admiral.adapter.kubernetes;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.vmware.admiral.compute.content.kubernetes.KubernetesUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class KubernetesApiEndpointsUtilTest_Parameterized {

    @Test
    public void testGetEntityEndpoint_1() {
        assertEquals("/deployments", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.DEPLOYMENT_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_2() {
        assertEquals("/services", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.SERVICE_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_3() {
        assertEquals("/pods", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.POD_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_4() {
        assertEquals("/replicationcontrollers", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.REPLICATION_CONTROLLER_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_5() {
        assertEquals("/replicasets", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.REPLICA_SET_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_6() {
        assertEquals("/namespaces", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.NAMESPACE_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_7() {
        assertEquals("/nodes", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.NODE_TYPE));
    }

    @Test
    public void testGetEntityEndpoint_14() {
        assertEquals("/endpoints", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.ENDPOINTS_TYPE));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetEntityEndpoint_8to13")
    public void testGetEntityEndpoint_8to13(String param1, String param2) {
        assertEquals(param1, KubernetesApiEndpointsUtil.getEntityEndpoint(param2));
    }

    static public Stream<Arguments> Provider_testGetEntityEndpoint_8to13() {
        return Stream.of(arguments("/cronjobs", "CronJob"), arguments("/ingresses", "Ingress"), arguments("/podsecuritypolicies", "PodSecurityPolicy"), arguments("/configmaps", "ConfigMap"), arguments("/secrets", "Secret"), arguments("/persistentvolumeclaims", "PersistentVolumeClaim"));
    }
}
