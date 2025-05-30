package org.apache.flink.yarn;

import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.CoreOptions;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.IllegalConfigurationException;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.configuration.ResourceManagerOptions;
import org.apache.flink.core.testutils.CommonTestUtils;
import org.apache.flink.runtime.jobmanager.JobManagerProcessSpec;
import org.apache.flink.runtime.jobmanager.JobManagerProcessUtils;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.flink.yarn.configuration.YarnConfigOptionsInternal;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.flink.yarn.configuration.YarnLogConfigUtil;
import org.apache.flink.yarn.token.TestYarnAMDelegationTokenProvider;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.LogAggregationContext;
import org.apache.hadoop.yarn.api.records.impl.pb.ApplicationSubmissionContextPBImpl;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.util.Records;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import static org.apache.flink.core.testutils.CommonTestUtils.assertThrows;
import static org.apache.flink.runtime.jobmanager.JobManagerProcessUtils.createDefaultJobManagerProcessSpec;
import static org.apache.flink.yarn.Utils.getPathFromLocalFile;
import static org.apache.flink.yarn.configuration.YarnConfigOptions.APP_MASTER_TOKEN_SERVICES;
import static org.apache.flink.yarn.configuration.YarnConfigOptions.CLASSPATH_INCLUDE_USER_JAR;
import static org.apache.flink.yarn.configuration.YarnConfigOptions.YARN_CONTAINER_START_COMMAND_TEMPLATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class YarnClusterDescriptorTest_Purified {

    private static final int YARN_MAX_VCORES = 16;

    private static YarnConfiguration yarnConfiguration;

    private static YarnClient yarnClient;

    private final ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder().setSlotsPerTaskManager(Integer.MAX_VALUE).createClusterSpecification();

    private final ApplicationConfiguration appConfig = new ApplicationConfiguration(new String[0], null);

    @TempDir
    java.nio.file.Path temporaryFolder;

    private File flinkJar;

    @BeforeAll
    static void setupClass() {
        yarnConfiguration = new YarnConfiguration();
        yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfiguration);
        yarnClient.start();
    }

    @BeforeEach
    void beforeTest() throws IOException {
        flinkJar = Files.createTempFile(temporaryFolder, "flink", ".jar").toFile();
    }

    @AfterAll
    static void tearDownClass() {
        yarnClient.stop();
    }

    private YarnClusterDescriptor createYarnClusterDescriptor() {
        return createYarnClusterDescriptor(new Configuration());
    }

    private YarnClusterDescriptor createYarnClusterDescriptor(Configuration configuration) {
        YarnTestUtils.configureLogFile(configuration, temporaryFolder.toFile().getAbsolutePath());
        return this.createYarnClusterDescriptor(configuration, yarnConfiguration);
    }

    private YarnClusterDescriptor createYarnClusterDescriptor(Configuration configuration, YarnConfiguration yarnConfiguration) {
        YarnTestUtils.configureLogFile(configuration, temporaryFolder.toFile().getAbsolutePath());
        return YarnClusterDescriptorBuilder.newBuilder(yarnClient, true).setFlinkConfiguration(configuration).setYarnConfiguration(yarnConfiguration).setYarnClusterInformationRetriever(() -> YARN_MAX_VCORES).build();
    }

    private Map<String, String> getTestMasterEnv(Configuration flinkConfig, File flinkHomeDir, String fakeClassPath, String fakeLocalFlinkJar, ApplicationId appId) throws IOException {
        try (final YarnClusterDescriptor yarnClusterDescriptor = createYarnClusterDescriptor(flinkConfig)) {
            final YarnApplicationFileUploader yarnApplicationFileUploader = YarnApplicationFileUploader.from(FileSystem.get(new YarnConfiguration()), new Path(flinkHomeDir.getPath()), new ArrayList<>(), appId, DFSConfigKeys.DFS_REPLICATION_DEFAULT);
            return yarnClusterDescriptor.generateApplicationMasterEnv(yarnApplicationFileUploader, fakeClassPath, fakeLocalFlinkJar, appId.toString());
        }
    }

    private static class TestApplicationSubmissionContext extends ApplicationSubmissionContextPBImpl {

        private LogAggregationContext logAggregationContext = null;

        @Override
        public void setLogAggregationContext(LogAggregationContext logAggregationContext) {
            this.logAggregationContext = logAggregationContext;
        }
    }

    @Test
    void testYarnClientShutDown_1() {
        assertThat(yarnClient.isInState(Service.STATE.STARTED)).isTrue();
    }

    @Test
    void testYarnClientShutDown_2() {
        final YarnClient closableYarnClient = YarnClient.createYarnClient();
        closableYarnClient.init(yarnConfiguration);
        closableYarnClient.start();
        yarnClusterDescriptor = YarnTestUtils.createClusterDescriptorWithLogging(temporaryFolder.toFile().getAbsolutePath(), new Configuration(), yarnConfiguration, closableYarnClient, false);
        assertThat(closableYarnClient.isInState(Service.STATE.STOPPED)).isTrue();
    }
}
