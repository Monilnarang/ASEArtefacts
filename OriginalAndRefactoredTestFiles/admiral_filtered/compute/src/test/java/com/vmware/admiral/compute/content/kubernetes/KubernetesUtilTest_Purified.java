package com.vmware.admiral.compute.content.kubernetes;

import static com.vmware.admiral.adapter.pks.PKSConstants.PKS_ENDPOINT_PROP_NAME;
import static com.vmware.admiral.common.util.FileUtil.switchToUnixLineEnds;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.deserializeCompositeTemplate;
import static com.vmware.admiral.compute.content.CompositeTemplateUtilTest.getContent;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.fromCompositeProtocolToKubernetesProtocol;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.fromContainerDescriptionHealthConfigToPodContainerProbe;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.fromPodContainerCommandToContainerDescriptionCommand;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.fromPodContainerProbeToContainerDescriptionHealthConfig;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.parsePodContainerCpuShares;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.parsePodContainerMemoryLimit;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.setContainerDescriptionResourcesToPodContainerResources;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesConverter.setPodContainerResourcesToContainerDescriptionResources;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.DEPLOYMENT_TYPE;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.KUBERNETES_LABEL_APP_ID;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.SERVICE_TYPE;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.fromCompositeTemplateToKubernetesTemplate;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.fromResourceStateToBaseKubernetesState;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.getStateTypeFromSelfLink;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.serializeKubernetesEntity;
import static com.vmware.admiral.compute.content.kubernetes.KubernetesUtil.serializeKubernetesTemplate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.compute.container.CompositeComponentRegistry;
import com.vmware.admiral.compute.container.ContainerDescriptionService.ContainerDescription;
import com.vmware.admiral.compute.container.ContainerService.ContainerState;
import com.vmware.admiral.compute.container.HealthChecker.HealthConfig;
import com.vmware.admiral.compute.container.HealthChecker.HealthConfig.HttpVersion;
import com.vmware.admiral.compute.container.HealthChecker.HealthConfig.RequestProtocol;
import com.vmware.admiral.compute.content.CompositeTemplate;
import com.vmware.admiral.compute.kubernetes.entities.common.BaseKubernetesObject;
import com.vmware.admiral.compute.kubernetes.entities.common.ResourceRequirements;
import com.vmware.admiral.compute.kubernetes.entities.config.KubeConfig;
import com.vmware.admiral.compute.kubernetes.entities.deployments.Deployment;
import com.vmware.admiral.compute.kubernetes.entities.pods.Container;
import com.vmware.admiral.compute.kubernetes.entities.pods.ExecAction;
import com.vmware.admiral.compute.kubernetes.entities.pods.HTTPGetAction;
import com.vmware.admiral.compute.kubernetes.entities.pods.Pod;
import com.vmware.admiral.compute.kubernetes.entities.pods.PodTemplate;
import com.vmware.admiral.compute.kubernetes.entities.pods.Probe;
import com.vmware.admiral.compute.kubernetes.entities.pods.TCPSocketAction;
import com.vmware.admiral.compute.kubernetes.entities.replicaset.ReplicaSet;
import com.vmware.admiral.compute.kubernetes.entities.replicationcontrollers.ReplicationController;
import com.vmware.admiral.compute.kubernetes.entities.services.Service;
import com.vmware.admiral.compute.kubernetes.service.BaseKubernetesState;
import com.vmware.admiral.compute.kubernetes.service.DeploymentService.DeploymentState;
import com.vmware.admiral.compute.kubernetes.service.GenericKubernetesEntityService.GenericKubernetesEntityState;
import com.vmware.admiral.compute.kubernetes.service.KubernetesDescriptionService.KubernetesDescription;
import com.vmware.admiral.compute.kubernetes.service.PodFactoryService;
import com.vmware.admiral.compute.kubernetes.service.PodService.PodState;
import com.vmware.admiral.compute.kubernetes.service.ReplicaSetService.ReplicaSetState;
import com.vmware.admiral.compute.kubernetes.service.ReplicationControllerService.ReplicationControllerState;
import com.vmware.admiral.compute.kubernetes.service.ServiceEntityHandler.ServiceState;
import com.vmware.admiral.host.HostInitComputeServicesConfig;
import com.vmware.photon.controller.model.resources.ComputeService.ComputeState;
import com.vmware.photon.controller.model.resources.ResourceState;
import com.vmware.photon.controller.model.security.util.AuthCredentialsType;
import com.vmware.xenon.common.LocalizableValidationException;
import com.vmware.xenon.common.Service.Action;
import com.vmware.xenon.services.common.AuthCredentialsService.AuthCredentialsServiceState;

public class KubernetesUtilTest_Purified {

    private static final String deploymentYaml = "apiVersion: extensions/v1beta1\n" + "kind: Deployment\n" + "metadata:\n" + "  name: wordpress-mysql\n" + "  labels:\n" + "    app: wordpress\n" + "spec:\n" + "  strategy:\n" + "    type: Recreate\n" + "  template:\n" + "    metadata:\n" + "      labels:\n" + "        app: wordpress\n" + "        tier: mysql\n" + "    spec:\n" + "      containers:\n" + "      - image: mysql:5.6\n" + "        name: mysql\n" + "        env:\n" + "        - name: MYSQL_ROOT_PASSWORD\n" + "          value: pass@word01\n" + "        ports:\n" + "        - containerPort: 3306\n" + "          name: mysql";

    private static final String podYaml = "apiVersion: v1\n" + "kind: Pod\n" + "metadata:\n" + "  name: some-pod\n" + "spec:\n" + "  containers:\n" + "  - name: some-test-pod\n" + "    image: alpine\n";

    private static final String podTemplateYaml = "apiVersion: v1\n" + "kind: PodTemplate\n" + "metadata:\n" + "  name: pod-template-test\n" + "template:\n" + "  metadata:\n" + "    name: some-test-pod-from-template\n" + "  spec:\n" + "    containers:\n" + "    - name: some-test-alpine\n" + "      image: alpine\n";

    private static final String replicationControllerYaml = "apiVersion: v1\n" + "kind: ReplicationController\n" + "metadata:\n" + "  name: replication-controller-test\n" + "spec:\n" + "  replicas: 2\n" + "  selector:\n" + "    app: replication-controller-test\n" + "  template:\n" + "    metadata:\n" + "      name: replication-controller-test\n" + "      labels:\n" + "        app: replication-controller-test\n" + "    spec:\n" + "      containers:\n" + "      - name: nginx\n" + "        image: nginx\n" + "        ports:\n" + "        - containerPort: 80\n";

    private static final String replicaSetYaml = "apiVersion: apps/v1\n" + "kind: ReplicaSet\n" + "metadata:\n" + "  name: replicaset-test\n" + "spec:\n" + "  replicas: 2\n" + "  selector:\n" + "    matchLabels:\n" + "      app: replicaset-test\n" + "  template:\n" + "    metadata:\n" + "      labels:\n" + "        app: replicaset-test\n" + "    spec:\n" + "      containers:\n" + "      - name: some-alpine\n" + "        image: alpine\n";

    private static final String invalidDeploymentYaml = "apiVersion: extensions/v1beta1\n" + "kind: Deployment\n";

    private static final String deploymentWithoutSpecYaml = "apiVersion: extensions/v1beta1\n" + "kind: Deployment\n" + "metadata:\n" + "  name: wordpress-mysql\n" + "  labels:\n" + "    app: wordpress\n";

    private static final String serviceYamlFormat = "---\n" + "apiVersion: \"v1\"\n" + "kind: \"Service\"\n" + "metadata:\n" + "  name: \"db_sufix\"\n" + "  labels:\n" + "    app: \"my-app\"\n" + "spec:\n" + "  ports:\n" + "  - name: \"3306\"\n" + "    port: 3306\n" + "    protocol: \"TCP\"\n" + "    targetPort: 3306\n" + "  selector:\n" + "    app: \"my-app\"\n" + "    tier: \"db\"\n";

    private static final String secretYaml = "apiVersion: v1\n" + "kind: Secret\n" + "metadata:\n" + "  name: some-secret\n" + "type: Opaque\n" + "data:\n" + "  key: value\n";

    @Before
    public void beforeForKubernetesUtilTest() throws Throwable {
        HostInitComputeServicesConfig.initCompositeComponentRegistry();
    }

    @Test
    public void testDeserializeKubernetesEntityHasCorrectClass_1() throws IOException {
        assertEquals(Pod.class, KubernetesUtil.deserializeKubernetesEntity(podYaml).getClass());
    }

    @Test
    public void testDeserializeKubernetesEntityHasCorrectClass_2() throws IOException {
        assertEquals(PodTemplate.class, KubernetesUtil.deserializeKubernetesEntity(podTemplateYaml).getClass());
    }

    @Test
    public void testDeserializeKubernetesEntityHasCorrectClass_3() throws IOException {
        assertEquals(ReplicationController.class, KubernetesUtil.deserializeKubernetesEntity(replicationControllerYaml).getClass());
    }

    @Test
    public void testDeserializeKubernetesEntityHasCorrectClass_4() throws IOException {
        assertEquals(Deployment.class, KubernetesUtil.deserializeKubernetesEntity(deploymentYaml).getClass());
    }

    @Test
    public void testDeserializeKubernetesEntityHasCorrectClass_5() throws IOException {
        assertEquals(Service.class, KubernetesUtil.deserializeKubernetesEntity(serviceYamlFormat).getClass());
    }

    @Test
    public void testDeserializeKubernetesEntityHasCorrectClass_6() throws IOException {
        assertEquals(BaseKubernetesObject.class, KubernetesUtil.deserializeKubernetesEntity(secretYaml).getClass());
    }

    @Test
    public void testCreateKubernetesEntityStateCreatesInstancessOfCorrectClass_1() {
        assertEquals(PodState.class, KubernetesUtil.createKubernetesEntityState(KubernetesUtil.POD_TYPE).getClass());
    }

    @Test
    public void testCreateKubernetesEntityStateCreatesInstancessOfCorrectClass_2() {
        assertEquals(ServiceState.class, KubernetesUtil.createKubernetesEntityState(KubernetesUtil.SERVICE_TYPE).getClass());
    }

    @Test
    public void testCreateKubernetesEntityStateCreatesInstancessOfCorrectClass_3() {
        assertEquals(DeploymentState.class, KubernetesUtil.createKubernetesEntityState(KubernetesUtil.DEPLOYMENT_TYPE).getClass());
    }

    @Test
    public void testCreateKubernetesEntityStateCreatesInstancessOfCorrectClass_4() {
        assertEquals(ReplicationControllerState.class, KubernetesUtil.createKubernetesEntityState(KubernetesUtil.REPLICATION_CONTROLLER_TYPE).getClass());
    }

    @Test
    public void testCreateKubernetesEntityStateCreatesInstancessOfCorrectClass_5() {
        assertEquals(ReplicaSetState.class, KubernetesUtil.createKubernetesEntityState(KubernetesUtil.REPLICA_SET_TYPE).getClass());
    }

    @Test
    public void testCreateKubernetesEntityStateCreatesInstancessOfCorrectClass_6() {
        assertEquals(GenericKubernetesEntityState.class, KubernetesUtil.createKubernetesEntityState("any-other-kind").getClass());
    }

    @Test
    public void testParseBytes_1() {
        assertEquals(new Double(624.2), KubernetesUtil.parseBytes("624.2"));
    }

    @Test
    public void testParseBytes_2() {
        assertEquals(new Double(624), KubernetesUtil.parseBytes("624"));
    }

    @Test
    public void testParseBytes_3() {
        assertEquals(new Double(638976), KubernetesUtil.parseBytes("624Ki"));
    }

    @Test
    public void testParseBytes_4() {
        assertEquals(new Double(2.34881024E8), KubernetesUtil.parseBytes("224Mi"));
    }

    @Test
    public void testIsPKSManagedHost_1() {
        assertFalse(KubernetesUtil.isPKSManagedHost(null));
    }

    @Test
    public void testIsPKSManagedHost_2() {
        assertFalse(KubernetesUtil.isPKSManagedHost(new ComputeState()));
    }

    @Test
    public void testIsPKSManagedHost_3() {
        ComputeState noProperties = new ComputeState();
        assertFalse(KubernetesUtil.isPKSManagedHost(noProperties));
    }

    @Test
    public void testIsPKSManagedHost_4() {
        ComputeState pksManagedHost = new ComputeState();
        pksManagedHost.customProperties.put(PKS_ENDPOINT_PROP_NAME, "/resources/pks/endpoints/8d50dc9a46ed487556f736eb0c8f8");
        assertTrue(KubernetesUtil.isPKSManagedHost(pksManagedHost));
    }
}
