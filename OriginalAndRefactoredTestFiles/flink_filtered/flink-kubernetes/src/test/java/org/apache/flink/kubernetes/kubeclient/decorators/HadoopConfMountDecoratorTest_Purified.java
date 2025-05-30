package org.apache.flink.kubernetes.kubeclient.decorators;

import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.kubeclient.FlinkPod;
import org.apache.flink.kubernetes.kubeclient.KubernetesJobManagerTestBase;
import org.apache.flink.kubernetes.utils.Constants;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapVolumeSource;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.KeyToPath;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeMount;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

class HadoopConfMountDecoratorTest_Purified extends KubernetesJobManagerTestBase {

    private static final String EXISTING_HADOOP_CONF_CONFIG_MAP = "hadoop-conf";

    private HadoopConfMountDecorator hadoopConfMountDecorator;

    @Override
    protected void onSetup() throws Exception {
        super.onSetup();
        this.hadoopConfMountDecorator = new HadoopConfMountDecorator(kubernetesJobManagerParameters);
    }

    @Test
    void testHadoopConfDirectoryUnset_1() throws IOException {
        assertThat(hadoopConfMountDecorator.buildAccompanyingKubernetesResources()).isEmpty();
    }

    @Test
    void testHadoopConfDirectoryUnset_2_testMerged_2() throws IOException {
        final FlinkPod resultFlinkPod = hadoopConfMountDecorator.decorateFlinkPod(baseFlinkPod);
        assertThat(resultFlinkPod.getPodWithoutMainContainer()).isEqualTo(baseFlinkPod.getPodWithoutMainContainer());
        assertThat(resultFlinkPod.getMainContainer()).isEqualTo(baseFlinkPod.getMainContainer());
    }

    @Test
    void testEmptyHadoopConfDirectory_1() throws IOException {
        assertThat(hadoopConfMountDecorator.buildAccompanyingKubernetesResources()).isEmpty();
    }

    @Test
    void testEmptyHadoopConfDirectory_2_testMerged_2() throws IOException {
        final FlinkPod resultFlinkPod = hadoopConfMountDecorator.decorateFlinkPod(baseFlinkPod);
        assertThat(resultFlinkPod.getPodWithoutMainContainer()).isEqualTo(baseFlinkPod.getPodWithoutMainContainer());
        assertThat(resultFlinkPod.getMainContainer()).isEqualTo(baseFlinkPod.getMainContainer());
    }
}
