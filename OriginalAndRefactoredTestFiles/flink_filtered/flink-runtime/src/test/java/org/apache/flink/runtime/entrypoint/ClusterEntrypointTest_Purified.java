package org.apache.flink.runtime.entrypoint;

import org.apache.flink.configuration.ClusterOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.IllegalConfigurationException;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.SchedulerExecutionMode;
import org.apache.flink.runtime.clusterframework.ApplicationStatus;
import org.apache.flink.runtime.clusterframework.types.ResourceID;
import org.apache.flink.runtime.dispatcher.ExecutionGraphInfoStore;
import org.apache.flink.runtime.dispatcher.MemoryExecutionGraphInfoStore;
import org.apache.flink.runtime.dispatcher.PartialDispatcherServices;
import org.apache.flink.runtime.dispatcher.runner.DispatcherRunner;
import org.apache.flink.runtime.dispatcher.runner.DispatcherRunnerFactory;
import org.apache.flink.runtime.dispatcher.runner.TestingDispatcherRunner;
import org.apache.flink.runtime.entrypoint.component.DefaultDispatcherResourceManagerComponentFactory;
import org.apache.flink.runtime.entrypoint.component.DispatcherResourceManagerComponentFactory;
import org.apache.flink.runtime.highavailability.HighAvailabilityServices;
import org.apache.flink.runtime.highavailability.TestingHighAvailabilityServicesBuilder;
import org.apache.flink.runtime.jobmanager.JobPersistenceComponentFactory;
import org.apache.flink.runtime.leaderelection.LeaderElection;
import org.apache.flink.runtime.resourcemanager.ResourceManagerFactory;
import org.apache.flink.runtime.resourcemanager.StandaloneResourceManagerFactory;
import org.apache.flink.runtime.resourcemanager.TestingResourceManagerFactory;
import org.apache.flink.runtime.rest.SessionRestEndpointFactory;
import org.apache.flink.runtime.rpc.FatalErrorHandler;
import org.apache.flink.runtime.rpc.RpcService;
import org.apache.flink.runtime.rpc.RpcSystemUtils;
import org.apache.flink.runtime.security.token.ExceptionThrowingDelegationTokenProvider;
import org.apache.flink.runtime.security.token.ExceptionThrowingDelegationTokenReceiver;
import org.apache.flink.runtime.testutils.TestJvmProcess;
import org.apache.flink.runtime.testutils.TestingClusterEntrypointProcess;
import org.apache.flink.runtime.util.SignalHandler;
import org.apache.flink.testutils.executor.TestExecutorResource;
import org.apache.flink.util.OperatingSystem;
import org.apache.flink.util.TestLogger;
import org.apache.flink.util.concurrent.FutureUtils;
import org.apache.flink.util.concurrent.ScheduledExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

public class ClusterEntrypointTest_Purified extends TestLogger {

    private static final long TIMEOUT_MS = 10000;

    private Configuration flinkConfig;

    @ClassRule
    public static final TestExecutorResource<?> TEST_EXECUTOR_RESOURCE = new TestExecutorResource<>(Executors::newSingleThreadExecutor);

    @ClassRule
    public static final TemporaryFolder TEMPORARY_FOLDER = new TemporaryFolder();

    @Before
    public void before() {
        flinkConfig = new Configuration();
        ExceptionThrowingDelegationTokenProvider.reset();
        ExceptionThrowingDelegationTokenReceiver.reset();
    }

    @After
    public void after() {
        ExceptionThrowingDelegationTokenProvider.reset();
        ExceptionThrowingDelegationTokenReceiver.reset();
    }

    private static void configureWorkingDirectory(Configuration configuration, File workingDirBase, ResourceID resourceId) {
        configuration.set(ClusterOptions.PROCESS_WORKING_DIR_BASE, workingDirBase.getAbsolutePath());
        configuration.set(JobManagerOptions.JOB_MANAGER_RESOURCE_ID, resourceId.toString());
    }

    private CompletableFuture<ApplicationStatus> startClusterEntrypoint(TestingEntryPoint testingEntryPoint) throws Exception {
        testingEntryPoint.startCluster();
        return FutureUtils.supplyAsync(() -> testingEntryPoint.getTerminationFuture().get(), TEST_EXECUTOR_RESOURCE.getExecutor());
    }

    private static class TestingEntryPoint extends ClusterEntrypoint {

        private final HighAvailabilityServices haService;

        private final ResourceManagerFactory<ResourceID> resourceManagerFactory;

        private final DispatcherRunnerFactory dispatcherRunnerFactory;

        private TestingEntryPoint(Configuration configuration, HighAvailabilityServices haService, ResourceManagerFactory<ResourceID> resourceManagerFactory, DispatcherRunnerFactory dispatcherRunnerFactory) {
            super(configuration);
            SignalHandler.register(LOG);
            this.haService = haService;
            this.resourceManagerFactory = resourceManagerFactory;
            this.dispatcherRunnerFactory = dispatcherRunnerFactory;
        }

        @Override
        protected DispatcherResourceManagerComponentFactory createDispatcherResourceManagerComponentFactory(Configuration configuration) throws IOException {
            return new DefaultDispatcherResourceManagerComponentFactory(dispatcherRunnerFactory, resourceManagerFactory, SessionRestEndpointFactory.INSTANCE);
        }

        @Override
        protected ExecutionGraphInfoStore createSerializableExecutionGraphStore(Configuration configuration, ScheduledExecutor scheduledExecutor) {
            return new MemoryExecutionGraphInfoStore();
        }

        @Override
        protected HighAvailabilityServices createHaServices(Configuration configuration, Executor executor, RpcSystemUtils rpcSystemUtils) {
            return haService;
        }

        @Override
        protected boolean supportsReactiveMode() {
            return false;
        }

        public static final class Builder {

            private HighAvailabilityServices haService = new TestingHighAvailabilityServicesBuilder().build();

            private ResourceManagerFactory<ResourceID> resourceManagerFactory = StandaloneResourceManagerFactory.getInstance();

            private DispatcherRunnerFactory dispatcherRunnerFactory = new TestingDispatcherRunnerFactory.Builder().build();

            private Configuration configuration = new Configuration();

            public Builder setHighAvailabilityServices(HighAvailabilityServices haService) {
                this.haService = haService;
                return this;
            }

            public Builder setResourceManagerFactory(ResourceManagerFactory<ResourceID> resourceManagerFactory) {
                this.resourceManagerFactory = resourceManagerFactory;
                return this;
            }

            public Builder setConfiguration(Configuration configuration) {
                this.configuration = configuration;
                return this;
            }

            public Builder setDispatcherRunnerFactory(DispatcherRunnerFactory dispatcherRunnerFactory) {
                this.dispatcherRunnerFactory = dispatcherRunnerFactory;
                return this;
            }

            public TestingEntryPoint build() {
                return new TestingEntryPoint(configuration, haService, resourceManagerFactory, dispatcherRunnerFactory);
            }
        }
    }

    private static class TestingDispatcherRunnerFactory implements DispatcherRunnerFactory {

        private final CompletableFuture<ApplicationStatus> shutDownFuture;

        private TestingDispatcherRunnerFactory(CompletableFuture<ApplicationStatus> shutDownFuture) {
            this.shutDownFuture = shutDownFuture;
        }

        @Override
        public DispatcherRunner createDispatcherRunner(LeaderElection leaderElection, FatalErrorHandler fatalErrorHandler, JobPersistenceComponentFactory jobPersistenceComponentFactory, Executor ioExecutor, RpcService rpcService, PartialDispatcherServices partialDispatcherServices) throws Exception {
            return TestingDispatcherRunner.newBuilder().setShutDownFuture(shutDownFuture).build();
        }

        public static final class Builder {

            private CompletableFuture<ApplicationStatus> shutDownFuture = new CompletableFuture<>();

            public Builder setShutDownFuture(CompletableFuture<ApplicationStatus> shutDownFuture) {
                this.shutDownFuture = shutDownFuture;
                return this;
            }

            public TestingDispatcherRunnerFactory build() {
                return new TestingDispatcherRunnerFactory(shutDownFuture);
            }
        }
    }

    @Test
    public void testClusterStartShouldObtainTokens_1() throws Exception {
        final HighAvailabilityServices testingHaService = new TestingHighAvailabilityServicesBuilder().build();
        final TestingEntryPoint testingEntryPoint = new TestingEntryPoint.Builder().setConfiguration(flinkConfig).setHighAvailabilityServices(testingHaService).build();
        final CompletableFuture<ApplicationStatus> appStatusFuture = startClusterEntrypoint(testingEntryPoint);
        assertThat(appStatusFuture.get(TIMEOUT_MS, TimeUnit.MILLISECONDS), is(ApplicationStatus.UNKNOWN));
    }

    @Test
    public void testClusterStartShouldObtainTokens_2() throws Exception {
        assertThat(ExceptionThrowingDelegationTokenReceiver.onNewTokensObtainedCallCount.get(), is(1));
    }
}
