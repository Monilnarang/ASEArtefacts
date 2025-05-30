package org.apache.flink.kubernetes.kubeclient.decorators;

import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.kubeclient.FlinkPod;
import org.apache.flink.kubernetes.kubeclient.KubernetesJobManagerTestBase;
import org.apache.flink.kubernetes.utils.Constants;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.ContainerPortBuilder;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.api.model.Toleration;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

class InitJobManagerDecoratorTest_Purified extends KubernetesJobManagerTestBase {

    private static final String SERVICE_ACCOUNT_NAME = "service-test";

    private static final List<String> IMAGE_PULL_SECRETS = Arrays.asList("s1", "s2", "s3");

    private static final Map<String, String> ANNOTATIONS = new HashMap<String, String>() {

        {
            put("a1", "v1");
            put("a2", "v2");
        }
    };

    private static final String TOLERATION_STRING = "key:key1,operator:Equal,value:value1,effect:NoSchedule;" + "KEY:key2,operator:Exists,Effect:NoExecute,tolerationSeconds:6000";

    private static final List<Toleration> TOLERATION = Arrays.asList(new Toleration("NoSchedule", "key1", "Equal", null, "value1"), new Toleration("NoExecute", "key2", "Exists", 6000L, null));

    private static final String USER_DEFINED_FLINK_LOG_DIR = "/path/of/flink-log";

    private Pod resultPod;

    private Container resultMainContainer;

    @Override
    protected void setupFlinkConfig() {
        super.setupFlinkConfig();
        this.flinkConfig.set(KubernetesConfigOptions.KUBERNETES_SERVICE_ACCOUNT, SERVICE_ACCOUNT_NAME);
        this.flinkConfig.set(KubernetesConfigOptions.CONTAINER_IMAGE_PULL_SECRETS, IMAGE_PULL_SECRETS);
        this.flinkConfig.set(KubernetesConfigOptions.JOB_MANAGER_ANNOTATIONS, ANNOTATIONS);
        this.flinkConfig.setString(KubernetesConfigOptions.JOB_MANAGER_TOLERATIONS.key(), TOLERATION_STRING);
        this.flinkConfig.set(KubernetesConfigOptions.FLINK_LOG_DIR, USER_DEFINED_FLINK_LOG_DIR);
    }

    @Override
    protected void onSetup() throws Exception {
        super.onSetup();
        final InitJobManagerDecorator initJobManagerDecorator = new InitJobManagerDecorator(this.kubernetesJobManagerParameters);
        final FlinkPod resultFlinkPod = initJobManagerDecorator.decorateFlinkPod(this.baseFlinkPod);
        this.resultPod = resultFlinkPod.getPodWithoutMainContainer();
        this.resultMainContainer = resultFlinkPod.getMainContainer();
    }

    @Test
    void testMainContainerImage_1() {
        assertThat(this.resultMainContainer.getImage()).isEqualTo(CONTAINER_IMAGE);
    }

    @Test
    void testMainContainerImage_2() {
        assertThat(this.resultMainContainer.getImagePullPolicy()).isEqualTo(CONTAINER_IMAGE_PULL_POLICY.name());
    }
}
