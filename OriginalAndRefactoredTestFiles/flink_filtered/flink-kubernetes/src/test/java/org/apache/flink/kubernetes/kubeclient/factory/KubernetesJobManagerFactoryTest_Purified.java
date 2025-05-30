package org.apache.flink.kubernetes.kubeclient.factory;

import org.apache.flink.client.cli.ArtifactFetchOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.HighAvailabilityOptions;
import org.apache.flink.configuration.SecurityOptions;
import org.apache.flink.kubernetes.KubernetesTestUtils;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptionsInternal;
import org.apache.flink.kubernetes.configuration.KubernetesDeploymentTarget;
import org.apache.flink.kubernetes.entrypoint.KubernetesSessionClusterEntrypoint;
import org.apache.flink.kubernetes.kubeclient.FlinkPod;
import org.apache.flink.kubernetes.kubeclient.KubernetesJobManagerSpecification;
import org.apache.flink.kubernetes.kubeclient.KubernetesJobManagerTestBase;
import org.apache.flink.kubernetes.kubeclient.decorators.ExternalServiceDecorator;
import org.apache.flink.kubernetes.kubeclient.decorators.FlinkConfMountDecorator;
import org.apache.flink.kubernetes.kubeclient.decorators.HadoopConfMountDecorator;
import org.apache.flink.kubernetes.kubeclient.decorators.InternalServiceDecorator;
import org.apache.flink.kubernetes.kubeclient.decorators.KerberosMountDecorator;
import org.apache.flink.kubernetes.kubeclient.services.HeadlessClusterIPService;
import org.apache.flink.kubernetes.utils.Constants;
import org.apache.flink.kubernetes.utils.KubernetesUtils;
import org.apache.flink.runtime.jobmanager.HighAvailabilityMode;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.apache.flink.configuration.GlobalConfiguration.FLINK_CONF_FILENAME;
import static org.apache.flink.kubernetes.utils.Constants.CONFIG_FILE_LOG4J_NAME;
import static org.apache.flink.kubernetes.utils.Constants.CONFIG_FILE_LOGBACK_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class KubernetesJobManagerFactoryTest_Purified extends KubernetesJobManagerTestBase {

    private static final String SERVICE_ACCOUNT_NAME = "service-test";

    private static final String ENTRY_POINT_CLASS = KubernetesSessionClusterEntrypoint.class.getCanonicalName();

    private static final String EXISTING_HADOOP_CONF_CONFIG_MAP = "hadoop-conf";

    private static final String OWNER_REFERENCE_STRING = "apiVersion:cloudflow.io/v1beta1,blockOwnerDeletion:true," + "controller:true,kind:FlinkApplication,name:testapp,uid:e3c9aa3f-cc42-4178-814a-64aa15c82373";

    private static final List<OwnerReference> OWNER_REFERENCES = Collections.singletonList(new OwnerReference("cloudflow.io/v1beta1", true, true, "FlinkApplication", "testapp", "e3c9aa3f-cc42-4178-814a-64aa15c82373"));

    private static final int JOBMANAGER_REPLICAS = 2;

    private final FlinkPod flinkPod = new FlinkPod.Builder().build();

    protected KubernetesJobManagerSpecification kubernetesJobManagerSpecification;

    @Override
    protected void setupFlinkConfig() {
        super.setupFlinkConfig();
        flinkConfig.set(DeploymentOptions.TARGET, KubernetesDeploymentTarget.SESSION.getName());
        flinkConfig.set(KubernetesConfigOptionsInternal.ENTRY_POINT_CLASS, ENTRY_POINT_CLASS);
        flinkConfig.set(KubernetesConfigOptions.JOB_MANAGER_SERVICE_ACCOUNT, SERVICE_ACCOUNT_NAME);
        flinkConfig.set(SecurityOptions.KERBEROS_LOGIN_KEYTAB, kerberosDir.toString() + "/" + KEYTAB_FILE);
        flinkConfig.set(SecurityOptions.KERBEROS_LOGIN_PRINCIPAL, "test");
        flinkConfig.set(SecurityOptions.KERBEROS_KRB5_PATH, kerberosDir.toString() + "/" + KRB5_CONF_FILE);
        flinkConfig.setString(KubernetesConfigOptions.JOB_MANAGER_OWNER_REFERENCE.key(), OWNER_REFERENCE_STRING);
    }

    @Override
    protected void onSetup() throws Exception {
        super.onSetup();
        KubernetesTestUtils.createTemporyFile("some data", flinkConfDir, CONFIG_FILE_LOGBACK_NAME);
        KubernetesTestUtils.createTemporyFile("some data", flinkConfDir, CONFIG_FILE_LOG4J_NAME);
        generateKerberosFileItems();
    }

    private List<HasMetadata> getConfigMapList(String configMapName) {
        return kubernetesJobManagerSpecification.getAccompanyingResources().stream().filter(x -> x instanceof ConfigMap && x.getMetadata().getName().equals(configMapName)).collect(Collectors.toList());
    }

    @Test
    void testHadoopDecoratorsCanBeTurnedOff_1() throws Exception {
        assertThat(getConfigMapList(HadoopConfMountDecorator.getHadoopConfConfigMapName(CLUSTER_ID))).isEmpty();
    }

    @Test
    void testHadoopDecoratorsCanBeTurnedOff_2() throws Exception {
        assertThat(getConfigMapList(KerberosMountDecorator.getKerberosKrb5confConfigMapName(CLUSTER_ID))).isEmpty();
    }

    @Test
    void testHadoopDecoratorsCanBeTurnedOff_3() throws Exception {
        assertThat(getConfigMapList(KerberosMountDecorator.getKerberosKeytabSecretName(CLUSTER_ID))).isEmpty();
    }
}
