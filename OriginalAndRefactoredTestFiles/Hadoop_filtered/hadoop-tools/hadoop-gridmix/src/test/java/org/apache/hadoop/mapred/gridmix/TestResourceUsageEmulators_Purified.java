package org.apache.hadoop.mapred.gridmix;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.StatusReporter;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.TaskInputOutputContext;
import org.apache.hadoop.mapreduce.TaskType;
import org.apache.hadoop.mapreduce.server.tasktracker.TTConfig;
import org.apache.hadoop.mapreduce.task.MapContextImpl;
import org.apache.hadoop.tools.rumen.ResourceUsageMetrics;
import org.apache.hadoop.mapred.gridmix.LoadJob.ResourceUsageMatcherRunner;
import org.apache.hadoop.mapred.gridmix.emulators.resourceusage.CumulativeCpuUsageEmulatorPlugin;
import org.apache.hadoop.mapred.gridmix.emulators.resourceusage.ResourceUsageEmulatorPlugin;
import org.apache.hadoop.mapred.gridmix.emulators.resourceusage.ResourceUsageMatcher;
import org.apache.hadoop.mapred.gridmix.emulators.resourceusage.CumulativeCpuUsageEmulatorPlugin.DefaultCpuUsageEmulator;
import org.apache.hadoop.yarn.util.ResourceCalculatorPlugin;

public class TestResourceUsageEmulators_Purified {

    static class TestResourceUsageEmulatorPlugin implements ResourceUsageEmulatorPlugin {

        static final Path rootTempDir = new Path(System.getProperty("test.build.data", "/tmp"));

        static final Path tempDir = new Path(rootTempDir, "TestResourceUsageEmulatorPlugin");

        static final String DEFAULT_IDENTIFIER = "test";

        private Path touchPath = null;

        private FileSystem fs = null;

        @Override
        public void emulate() throws IOException, InterruptedException {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            try {
                fs.delete(touchPath, false);
                fs.create(touchPath).close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        protected String getIdentifier() {
            return DEFAULT_IDENTIFIER;
        }

        private static Path getFilePath(String id) {
            return new Path(tempDir, id);
        }

        private static Path getInitFilePath(String id) {
            return new Path(tempDir, id + ".init");
        }

        @Override
        public void initialize(Configuration conf, ResourceUsageMetrics metrics, ResourceCalculatorPlugin monitor, Progressive progress) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            try {
                fs = FileSystem.getLocal(conf);
                Path initPath = getInitFilePath(getIdentifier());
                fs.delete(initPath, false);
                fs.create(initPath).close();
                touchPath = getFilePath(getIdentifier());
                fs.delete(touchPath, false);
            } catch (Exception e) {
            } finally {
                if (fs != null) {
                    try {
                        fs.deleteOnExit(tempDir);
                    } catch (IOException ioe) {
                    }
                }
            }
        }

        static long testInitialization(String id, Configuration conf) throws IOException {
            Path testPath = getInitFilePath(id);
            FileSystem fs = FileSystem.getLocal(conf);
            return fs.exists(testPath) ? fs.getFileStatus(testPath).getModificationTime() : 0;
        }

        static long testEmulation(String id, Configuration conf) throws IOException {
            Path testPath = getFilePath(id);
            FileSystem fs = FileSystem.getLocal(conf);
            return fs.exists(testPath) ? fs.getFileStatus(testPath).getModificationTime() : 0;
        }

        @Override
        public float getProgress() {
            try {
                return fs.exists(touchPath) ? 1.0f : 0f;
            } catch (IOException ioe) {
            }
            return 0f;
        }
    }

    static class TestOthers extends TestResourceUsageEmulatorPlugin {

        static final String ID = "others";

        @Override
        protected String getIdentifier() {
            return ID;
        }
    }

    static class TestCpu extends TestResourceUsageEmulatorPlugin {

        static final String ID = "cpu";

        @Override
        protected String getIdentifier() {
            return ID;
        }
    }

    static class FakeResourceUsageMonitor extends DummyResourceCalculatorPlugin {

        private FakeCpuUsageEmulatorCore core;

        public FakeResourceUsageMonitor(FakeCpuUsageEmulatorCore core) {
            this.core = core;
        }

        @Override
        public long getCumulativeCpuTime() {
            return core.getCpuUsage();
        }
    }

    static class FakeProgressive implements Progressive {

        private float progress = 0F;

        @Override
        public float getProgress() {
            return progress;
        }

        void setProgress(float progress) {
            this.progress = progress;
        }
    }

    private static class DummyReporter extends StatusReporter {

        private Progressive progress;

        DummyReporter(Progressive progress) {
            this.progress = progress;
        }

        @Override
        public org.apache.hadoop.mapreduce.Counter getCounter(Enum<?> name) {
            return null;
        }

        @Override
        public org.apache.hadoop.mapreduce.Counter getCounter(String group, String name) {
            return null;
        }

        @Override
        public void progress() {
        }

        @Override
        public float getProgress() {
            return progress.getProgress();
        }

        @Override
        public void setStatus(String status) {
        }
    }

    @SuppressWarnings("unchecked")
    private static class FakeResourceUsageMatcherRunner extends ResourceUsageMatcherRunner {

        FakeResourceUsageMatcherRunner(TaskInputOutputContext context, ResourceUsageMetrics metrics) {
            super(context, metrics);
        }

        void test() throws Exception {
            super.match();
        }
    }

    private static class FakeCpuUsageEmulatorCore extends DefaultCpuUsageEmulator {

        private int numCalls = 0;

        private int unitUsage = 1;

        private int cpuUsage = 0;

        @Override
        protected void performUnitComputation() {
            ++numCalls;
            cpuUsage += unitUsage;
        }

        int getNumCalls() {
            return numCalls;
        }

        int getCpuUsage() {
            return cpuUsage;
        }

        void reset() {
            numCalls = 0;
            cpuUsage = 0;
        }

        void setUnitUsage(int unitUsage) {
            this.unitUsage = unitUsage;
        }
    }

    static ResourceUsageMetrics createMetrics(long target) {
        ResourceUsageMetrics metrics = new ResourceUsageMetrics();
        metrics.setCumulativeCpuUsage(target);
        metrics.setVirtualMemoryUsage(target);
        metrics.setPhysicalMemoryUsage(target);
        metrics.setHeapUsage(target);
        return metrics;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResourceUsageMatcherRunner_1_testMerged_1() throws Exception {
        Configuration conf = new Configuration();
        conf.setClass(TTConfig.TT_RESOURCE_CALCULATOR_PLUGIN, DummyResourceCalculatorPlugin.class, ResourceCalculatorPlugin.class);
        conf.setClass(ResourceUsageMatcher.RESOURCE_USAGE_EMULATION_PLUGINS, TestResourceUsageEmulatorPlugin.class, ResourceUsageEmulatorPlugin.class);
        long currentTime = System.currentTimeMillis();
        String identifier = TestResourceUsageEmulatorPlugin.DEFAULT_IDENTIFIER;
        long initTime = TestResourceUsageEmulatorPlugin.testInitialization(identifier, conf);
        assertTrue("ResourceUsageMatcherRunner failed to initialize the" + " configured plugin", initTime > currentTime);
        currentTime = System.currentTimeMillis();
        long emulateTime = TestResourceUsageEmulatorPlugin.testEmulation(identifier, conf);
        assertTrue("ProgressBasedResourceUsageMatcher failed to load and emulate" + " the configured plugin", emulateTime > currentTime);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResourceUsageMatcherRunner_2() throws Exception {
        FakeProgressive progress = new FakeProgressive();
        assertEquals("Progress mismatch in ResourceUsageMatcherRunner", 0, progress.getProgress(), 0D);
    }
}
