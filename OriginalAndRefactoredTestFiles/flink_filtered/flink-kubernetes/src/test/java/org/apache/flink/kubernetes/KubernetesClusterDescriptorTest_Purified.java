package org.apache.flink.kubernetes;

import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.configuration.BlobServerOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.HighAvailabilityOptions;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.configuration.TaskManagerOptions;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.configuration.KubernetesDeploymentTarget;
import org.apache.flink.kubernetes.kubeclient.Fabric8FlinkKubeClient;
import org.apache.flink.kubernetes.kubeclient.FlinkKubeClient;
import org.apache.flink.kubernetes.kubeclient.FlinkKubeClientFactory;
import org.apache.flink.kubernetes.kubeclient.decorators.InternalServiceDecorator;
import org.apache.flink.kubernetes.utils.Constants;
import org.apache.flink.runtime.jobmanager.HighAvailabilityMode;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executors;
import static org.apache.flink.kubernetes.utils.Constants.ENV_FLINK_POD_IP_ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KubernetesClusterDescriptorTest_Purified extends KubernetesClientTestBase {

    private static final String MOCK_SERVICE_HOST_NAME = "mock-host-name-of-service";

    private static final String MOCK_SERVICE_IP = "192.168.0.1";

    private final ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder().createClusterSpecification();

    private final Service loadBalancerSvc = buildExternalServiceWithLoadBalancer(MOCK_SERVICE_HOST_NAME, MOCK_SERVICE_IP);

    private final ApplicationConfiguration appConfig = new ApplicationConfiguration(new String[0], null);

    private KubernetesClusterDescriptor descriptor;

    @Override
    protected void onSetup() throws Exception {
        super.onSetup();
        descriptor = new KubernetesClusterDescriptor(flinkConfig, new FlinkKubeClientFactory() {

            @Override
            public FlinkKubeClient fromConfiguration(Configuration flinkConfig, String useCase) {
                return new Fabric8FlinkKubeClient(flinkConfig, server.createClient().inNamespace(NAMESPACE), Executors.newSingleThreadScheduledExecutor());
            }
        }, config -> {
        });
    }

    private ClusterClientProvider<String> deploySessionCluster() throws ClusterDeploymentException {
        mockExpectedServiceFromServerSide(loadBalancerSvc);
        return descriptor.deploySessionCluster(clusterSpecification);
    }

    private void checkClusterClient(ClusterClient<String> clusterClient) {
        assertThat(clusterClient.getClusterId()).isEqualTo(CLUSTER_ID);
        assertThat(clusterClient.getWebInterfaceURL()).isEqualTo(String.format("http://%s:%d", MOCK_SERVICE_IP, REST_PORT));
    }

    private void checkUpdatedConfigAndResourceSetting() {
        assertThat(flinkConfig.get(BlobServerOptions.PORT)).isEqualTo(String.valueOf(Constants.BLOB_SERVER_PORT));
        assertThat(flinkConfig.get(TaskManagerOptions.RPC_PORT)).isEqualTo(String.valueOf(Constants.TASK_MANAGER_RPC_PORT));
        assertThat(flinkConfig.get(JobManagerOptions.ADDRESS)).isEqualTo(InternalServiceDecorator.getNamespacedInternalServiceName(CLUSTER_ID, NAMESPACE));
        final Deployment jmDeployment = kubeClient.apps().deployments().list().getItems().get(0);
        final Container jmContainer = jmDeployment.getSpec().getTemplate().getSpec().getContainers().get(0);
        assertThat(jmContainer.getResources().getRequests().get(Constants.RESOURCE_NAME_MEMORY).getAmount()).isEqualTo(String.valueOf(clusterSpecification.getMasterMemoryMB()));
        assertThat(jmContainer.getResources().getLimits().get(Constants.RESOURCE_NAME_MEMORY).getAmount()).isEqualTo(String.valueOf(clusterSpecification.getMasterMemoryMB()));
    }

    @Test
    void testKillCluster_1() throws Exception {
        assertThat(kubeClient.services().list().getItems()).hasSize(2);
    }

    @Test
    void testKillCluster_2() throws Exception {
        assertThat(kubeClient.apps().deployments().list().getItems()).isEmpty();
    }

    @Test
    void testKillCluster_3() throws Exception {
        assertThat(kubeClient.services().list().getItems()).hasSize(2);
    }

    @Test
    void testKillCluster_4() throws Exception {
        assertThat(kubeClient.configMaps().list().getItems()).hasSize(1);
    }
}
