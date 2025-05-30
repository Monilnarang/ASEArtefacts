package org.apache.flink.kubernetes.kubeclient;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.configuration.BlobServerOptions;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.kubernetes.KubernetesClientTestBase;
import org.apache.flink.kubernetes.KubernetesTestUtils;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptionsInternal;
import org.apache.flink.kubernetes.configuration.KubernetesDeploymentTarget;
import org.apache.flink.kubernetes.entrypoint.KubernetesSessionClusterEntrypoint;
import org.apache.flink.kubernetes.kubeclient.decorators.ExternalServiceDecorator;
import org.apache.flink.kubernetes.kubeclient.decorators.InternalServiceDecorator;
import org.apache.flink.kubernetes.kubeclient.factory.KubernetesJobManagerFactory;
import org.apache.flink.kubernetes.kubeclient.parameters.KubernetesJobManagerParameters;
import org.apache.flink.kubernetes.kubeclient.resources.KubernetesConfigMap;
import org.apache.flink.kubernetes.kubeclient.resources.KubernetesPod;
import org.apache.flink.kubernetes.utils.Constants;
import org.apache.flink.runtime.persistence.PossibleInconsistentStateException;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.Watcher.Action;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import static org.apache.flink.core.testutils.FlinkAssertions.anyCauseMatches;
import static org.apache.flink.core.testutils.FlinkAssertions.assertThatChainOfCauses;
import static org.apache.flink.kubernetes.utils.Constants.CONFIG_FILE_LOG4J_NAME;
import static org.apache.flink.kubernetes.utils.Constants.CONFIG_FILE_LOGBACK_NAME;
import static org.apache.flink.kubernetes.utils.Constants.KUBERNETES_ZERO_RESOURCE_VERSION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

public class Fabric8FlinkKubeClientTest_Purified extends KubernetesClientTestBase {

    private static final int RPC_PORT = 7123;

    private static final int BLOB_SERVER_PORT = 8346;

    private static final double JOB_MANAGER_CPU = 2.0;

    private static final int JOB_MANAGER_MEMORY = 768;

    private static final String SERVICE_ACCOUNT_NAME = "service-test";

    private static final String TASKMANAGER_POD_NAME = "mock-task-manager-pod";

    private static final String TESTING_CONFIG_MAP_NAME = "test-config-map";

    private static final String TESTING_CONFIG_MAP_KEY = "test-config-map-key";

    private static final String TESTING_CONFIG_MAP_VALUE = "test-config-map-value";

    private static final String TESTING_CONFIG_MAP_NEW_VALUE = "test-config-map-new-value";

    private static final Map<String, String> TESTING_LABELS = new HashMap<String, String>() {

        {
            put("label1", "value1");
            put("label2", "value2");
        }
    };

    private static final String ENTRY_POINT_CLASS = KubernetesSessionClusterEntrypoint.class.getCanonicalName();

    private KubernetesJobManagerSpecification kubernetesJobManagerSpecification;

    @Override
    protected void setupFlinkConfig() {
        super.setupFlinkConfig();
        flinkConfig.set(DeploymentOptions.TARGET, KubernetesDeploymentTarget.SESSION.getName());
        flinkConfig.set(KubernetesConfigOptions.CONTAINER_IMAGE_PULL_POLICY, CONTAINER_IMAGE_PULL_POLICY);
        flinkConfig.set(KubernetesConfigOptionsInternal.ENTRY_POINT_CLASS, ENTRY_POINT_CLASS);
        flinkConfig.set(RestOptions.PORT, REST_PORT);
        flinkConfig.set(JobManagerOptions.PORT, RPC_PORT);
        flinkConfig.set(BlobServerOptions.PORT, Integer.toString(BLOB_SERVER_PORT));
        flinkConfig.set(KubernetesConfigOptions.JOB_MANAGER_CPU, JOB_MANAGER_CPU);
        flinkConfig.set(KubernetesConfigOptions.JOB_MANAGER_SERVICE_ACCOUNT, SERVICE_ACCOUNT_NAME);
    }

    @Override
    protected void onSetup() throws Exception {
        super.onSetup();
        KubernetesTestUtils.createTemporyFile("some data", flinkConfDir, CONFIG_FILE_LOGBACK_NAME);
        KubernetesTestUtils.createTemporyFile("some data", flinkConfDir, CONFIG_FILE_LOG4J_NAME);
        final ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder().setMasterMemoryMB(JOB_MANAGER_MEMORY).setTaskManagerMemoryMB(1000).setSlotsPerTaskManager(3).createClusterSpecification();
        final KubernetesJobManagerParameters kubernetesJobManagerParameters = new KubernetesJobManagerParameters(flinkConfig, clusterSpecification);
        this.kubernetesJobManagerSpecification = KubernetesJobManagerFactory.buildKubernetesJobManagerSpecification(new FlinkPod.Builder().build(), kubernetesJobManagerParameters);
    }

    private int getServiceTargetPort(String serviceName, String portName) {
        final List<Integer> ports = kubeClient.services().withName(serviceName).get().getSpec().getPorts().stream().filter(servicePort -> servicePort.getName().equalsIgnoreCase(portName)).map(servicePort -> servicePort.getTargetPort().getIntVal()).collect(Collectors.toList());
        assertThat(ports).hasSize(1);
        return ports.get(0);
    }

    private KubernetesPod buildKubernetesPod(String name) {
        return new KubernetesPod(new PodBuilder().editOrNewMetadata().withName(name).endMetadata().editOrNewSpec().endSpec().build());
    }

    private KubernetesConfigMap buildTestingConfigMap() {
        final Map<String, String> data = new HashMap<>();
        data.put(TESTING_CONFIG_MAP_KEY, TESTING_CONFIG_MAP_VALUE);
        return new KubernetesConfigMap(new ConfigMapBuilder().withNewMetadata().withName(TESTING_CONFIG_MAP_NAME).withLabels(TESTING_LABELS).withNamespace(NAMESPACE).endMetadata().withData(data).build());
    }

    @Test
    void testStopAndCleanupCluster_1() throws Exception {
        assertThat(this.kubeClient.apps().deployments().inNamespace(NAMESPACE).list().getItems().size()).isEqualTo(1);
    }

    @Test
    void testStopAndCleanupCluster_2() throws Exception {
        assertThat(this.kubeClient.configMaps().inNamespace(NAMESPACE).list().getItems().size()).isEqualTo(1);
    }

    @Test
    void testStopAndCleanupCluster_3() throws Exception {
        assertThat(this.kubeClient.services().inNamespace(NAMESPACE).list().getItems()).hasSize(2);
    }

    @Test
    void testStopAndCleanupCluster_4() throws Exception {
        assertThat(this.kubeClient.pods().inNamespace(NAMESPACE).list().getItems()).hasSize(1);
    }

    @Test
    void testStopAndCleanupCluster_5() throws Exception {
        assertThat(this.kubeClient.apps().deployments().inNamespace(NAMESPACE).list().getItems()).isEmpty();
    }

    @Test
    void testDeleteConfigMapByName_1() throws Exception {
        assertThat(this.flinkKubeClient.getConfigMap(TESTING_CONFIG_MAP_NAME)).isPresent();
    }

    @Test
    void testDeleteConfigMapByName_2() throws Exception {
        this.flinkKubeClient.deleteConfigMap(TESTING_CONFIG_MAP_NAME).get();
        assertThat(this.flinkKubeClient.getConfigMap(TESTING_CONFIG_MAP_NAME)).isNotPresent();
    }

    @Test
    void testDeleteNotExistingConfigMapByName_1() throws Exception {
        assertThat(this.flinkKubeClient.getConfigMap(TESTING_CONFIG_MAP_NAME)).isNotPresent();
    }

    @Test
    void testDeleteNotExistingConfigMapByName_2() throws Exception {
        assertThat(this.flinkKubeClient.getConfigMap(TESTING_CONFIG_MAP_NAME)).isNotPresent();
    }
}
