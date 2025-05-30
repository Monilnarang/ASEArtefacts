package org.apache.flink.kubernetes.kubeclient.services;

import org.apache.flink.kubernetes.KubernetesClientTestBase;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ServiceTypeTest_Purified extends KubernetesClientTestBase {

    @Test
    void testServiceClassify_1() {
        assertThat(ServiceType.classify(buildExternalServiceWithClusterIP())).isEqualByComparingTo(KubernetesConfigOptions.ServiceExposedType.ClusterIP);
    }

    @Test
    void testServiceClassify_2() {
        assertThat(ServiceType.classify(buildExternalServiceWithHeadlessClusterIP())).isEqualByComparingTo(KubernetesConfigOptions.ServiceExposedType.Headless_ClusterIP);
    }

    @Test
    void testServiceClassify_3() {
        assertThat(ServiceType.classify(buildExternalServiceWithNodePort())).isEqualByComparingTo(KubernetesConfigOptions.ServiceExposedType.NodePort);
    }

    @Test
    void testServiceClassify_4() {
        assertThat(ServiceType.classify(buildExternalServiceWithLoadBalancer("", ""))).isEqualByComparingTo(KubernetesConfigOptions.ServiceExposedType.LoadBalancer);
    }
}
