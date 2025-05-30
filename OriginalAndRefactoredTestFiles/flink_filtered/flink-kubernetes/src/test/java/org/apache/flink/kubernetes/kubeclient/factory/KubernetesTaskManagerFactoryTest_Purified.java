package org.apache.flink.kubernetes.kubeclient.factory;

import org.apache.flink.configuration.SecurityOptions;
import org.apache.flink.kubernetes.KubernetesTestUtils;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.kubeclient.FlinkPod;
import org.apache.flink.kubernetes.kubeclient.KubernetesTaskManagerTestBase;
import org.apache.flink.kubernetes.utils.Constants;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.Pod;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.apache.flink.kubernetes.utils.Constants.CONFIG_FILE_LOG4J_NAME;
import static org.apache.flink.kubernetes.utils.Constants.CONFIG_FILE_LOGBACK_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class KubernetesTaskManagerFactoryTest_Purified extends KubernetesTaskManagerTestBase {

    private Pod resultPod;

    @Override
    protected void setupFlinkConfig() {
        super.setupFlinkConfig();
        flinkConfig.set(SecurityOptions.KERBEROS_LOGIN_KEYTAB, kerberosDir.toString() + "/" + KEYTAB_FILE);
        flinkConfig.set(SecurityOptions.KERBEROS_LOGIN_PRINCIPAL, "test");
        flinkConfig.set(SecurityOptions.KERBEROS_KRB5_PATH, kerberosDir.toString() + "/" + KRB5_CONF_FILE);
    }

    @Override
    protected void onSetup() throws Exception {
        super.onSetup();
        KubernetesTestUtils.createTemporyFile("some data", flinkConfDir, CONFIG_FILE_LOGBACK_NAME);
        KubernetesTestUtils.createTemporyFile("some data", flinkConfDir, CONFIG_FILE_LOG4J_NAME);
        setHadoopConfDirEnv();
        generateHadoopConfFileItems();
        generateKerberosFileItems();
        this.resultPod = KubernetesTaskManagerFactory.buildTaskManagerKubernetesPod(new FlinkPod.Builder().build(), kubernetesTaskManagerParameters).getInternalResource();
    }

    @Test
    void testPod_1() {
        assertThat(this.resultPod.getMetadata().getName()).isEqualTo(POD_NAME);
    }

    @Test
    void testPod_2() {
        assertThat(this.resultPod.getMetadata().getLabels()).hasSize(5);
    }

    @Test
    void testPod_3() {
        assertThat(this.resultPod.getSpec().getVolumes()).hasSize(4);
    }
}
