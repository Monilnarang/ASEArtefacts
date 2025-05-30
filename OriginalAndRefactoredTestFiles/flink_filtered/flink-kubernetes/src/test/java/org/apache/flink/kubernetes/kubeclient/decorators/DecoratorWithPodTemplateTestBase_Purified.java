package org.apache.flink.kubernetes.kubeclient.decorators;

import org.apache.flink.configuration.BlobServerOptions;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.configuration.TaskManagerOptions;
import org.apache.flink.kubernetes.KubernetesPodTemplateTestUtils;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.kubeclient.FlinkPod;
import org.apache.flink.kubernetes.kubeclient.KubernetesPodTestBase;
import org.apache.flink.kubernetes.utils.Constants;
import org.apache.flink.kubernetes.utils.KubernetesUtils;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.api.model.Toleration;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class DecoratorWithPodTemplateTestBase_Purified extends KubernetesPodTestBase {

    private static final String IMAGE = "test-image:v1";

    private static final String IMAGE_PULL_POLICY = "IfNotPresent";

    private static final List<String> IMAGE_PULL_SECRETS = Arrays.asList("s1", "s2", "s3");

    protected static final Map<String, String> ANNOTATIONS = new HashMap<String, String>() {

        private static final long serialVersionUID = 0L;

        {
            put("a1", "v1");
            put("a2", "v2");
        }
    };

    protected static final String TOLERATION_STRING = "key:key1,operator:Equal,value:value1,effect:NoSchedule";

    private static final String TESTING_SERVICE_ACCOUNT = "testing-service-account";

    protected static final double RESOURCE_CPU = 1.5;

    protected static final int RESOURCE_MEMORY = 1456;

    protected FlinkPod resultPod;

    @Override
    protected void setupFlinkConfig() {
        super.setupFlinkConfig();
        this.flinkConfig.set(KubernetesConfigOptions.CONTAINER_IMAGE, IMAGE);
        this.flinkConfig.set(KubernetesConfigOptions.CONTAINER_IMAGE_PULL_POLICY, KubernetesConfigOptions.ImagePullPolicy.valueOf(IMAGE_PULL_POLICY));
        this.flinkConfig.set(KubernetesConfigOptions.CONTAINER_IMAGE_PULL_SECRETS, IMAGE_PULL_SECRETS);
        this.flinkConfig.set(KubernetesConfigOptions.KUBERNETES_SERVICE_ACCOUNT, TESTING_SERVICE_ACCOUNT);
        flinkConfig.set(RestOptions.PORT, Constants.REST_PORT);
        flinkConfig.set(BlobServerOptions.PORT, Integer.toString(Constants.BLOB_SERVER_PORT));
        flinkConfig.set(TaskManagerOptions.RPC_PORT, String.valueOf(Constants.TASK_MANAGER_RPC_PORT));
        flinkConfig.set(JobManagerOptions.TOTAL_PROCESS_MEMORY, MemorySize.ofMebiBytes(RESOURCE_MEMORY));
        flinkConfig.set(KubernetesConfigOptions.JOB_MANAGER_CPU, RESOURCE_CPU);
        flinkConfig.set(TaskManagerOptions.TOTAL_PROCESS_MEMORY, MemorySize.ofMebiBytes(RESOURCE_MEMORY));
        flinkConfig.set(TaskManagerOptions.CPU_CORES, RESOURCE_CPU);
    }

    @Override
    public final void onSetup() throws Exception {
        final FlinkPod podTemplate = KubernetesUtils.loadPodFromTemplateFile(flinkKubeClient, KubernetesPodTemplateTestUtils.getPodTemplateFile(), KubernetesPodTemplateTestUtils.TESTING_MAIN_CONTAINER_NAME);
        this.resultPod = getResultPod(podTemplate);
    }

    protected abstract FlinkPod getResultPod(FlinkPod podTemplate) throws Exception;

    @Test
    void testServiceAccountOverwritten_1() {
        assertThat(this.resultPod.getPodWithoutMainContainer().getSpec().getServiceAccountName()).isEqualTo(TESTING_SERVICE_ACCOUNT);
    }

    @Test
    void testServiceAccountOverwritten_2() {
        assertThat(this.resultPod.getPodWithoutMainContainer().getSpec().getServiceAccount()).isEqualTo(TESTING_SERVICE_ACCOUNT);
    }
}
