package com.vmware.admiral.adapter.kubernetes;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.vmware.admiral.compute.content.kubernetes.KubernetesUtil;

public class KubernetesApiEndpointsUtilTest_Purified {

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
    public void testGetEntityEndpoint_8() {
        assertEquals("/cronjobs", KubernetesApiEndpointsUtil.getEntityEndpoint("CronJob"));
    }

    @Test
    public void testGetEntityEndpoint_9() {
        assertEquals("/ingresses", KubernetesApiEndpointsUtil.getEntityEndpoint("Ingress"));
    }

    @Test
    public void testGetEntityEndpoint_10() {
        assertEquals("/podsecuritypolicies", KubernetesApiEndpointsUtil.getEntityEndpoint("PodSecurityPolicy"));
    }

    @Test
    public void testGetEntityEndpoint_11() {
        assertEquals("/configmaps", KubernetesApiEndpointsUtil.getEntityEndpoint("ConfigMap"));
    }

    @Test
    public void testGetEntityEndpoint_12() {
        assertEquals("/secrets", KubernetesApiEndpointsUtil.getEntityEndpoint("Secret"));
    }

    @Test
    public void testGetEntityEndpoint_13() {
        assertEquals("/persistentvolumeclaims", KubernetesApiEndpointsUtil.getEntityEndpoint("PersistentVolumeClaim"));
    }

    @Test
    public void testGetEntityEndpoint_14() {
        assertEquals("/endpoints", KubernetesApiEndpointsUtil.getEntityEndpoint(KubernetesUtil.ENDPOINTS_TYPE));
    }
}
