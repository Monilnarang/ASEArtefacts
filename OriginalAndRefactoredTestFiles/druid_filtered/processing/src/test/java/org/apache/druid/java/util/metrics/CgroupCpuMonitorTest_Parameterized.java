package org.apache.druid.java.util.metrics;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.java.util.common.FileUtils;
import org.apache.druid.java.util.emitter.core.Event;
import org.apache.druid.java.util.metrics.cgroups.CgroupDiscoverer;
import org.apache.druid.java.util.metrics.cgroups.ProcCgroupDiscoverer;
import org.apache.druid.java.util.metrics.cgroups.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CgroupCpuMonitorTest_Parameterized {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File procDir;

    private File cgroupDir;

    private File statFile;

    private CgroupDiscoverer discoverer;

    @Before
    public void setUp() throws IOException {
        cgroupDir = temporaryFolder.newFolder();
        procDir = temporaryFolder.newFolder();
        discoverer = new ProcCgroupDiscoverer(procDir.toPath());
        TestUtils.setUpCgroups(procDir, cgroupDir);
        final File cpuDir = new File(cgroupDir, "cpu,cpuacct/system.slice/some.service/f12ba7e0-fa16-462e-bb9d-652ccc27f0ee");
        FileUtils.mkdirp(cpuDir);
        statFile = new File(cpuDir, "cpuacct.stat");
        TestUtils.copyOrReplaceResource("/cpu.shares", new File(cpuDir, "cpu.shares"));
        TestUtils.copyOrReplaceResource("/cpu.cfs_quota_us", new File(cpuDir, "cpu.cfs_quota_us"));
        TestUtils.copyOrReplaceResource("/cpu.cfs_period_us", new File(cpuDir, "cpu.cfs_period_us"));
        TestUtils.copyOrReplaceResource("/cpuacct.stat", statFile);
    }

    @Test
    public void testQuotaCompute_1() {
        Assert.assertEquals(-1, CgroupCpuMonitor.computeProcessorQuota(-1, 100000), 0);
    }

    @Test
    public void testQuotaCompute_3() {
        Assert.assertEquals(-1, CgroupCpuMonitor.computeProcessorQuota(100000, 0), 0);
    }

    @ParameterizedTest
    @MethodSource("Provider_testQuotaCompute_2_4to5")
    public void testQuotaCompute_2_4to5(int param1, int param2, int param3, int param4) {
        Assert.assertEquals(param1, CgroupCpuMonitor.computeProcessorQuota(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testQuotaCompute_2_4to5() {
        return Stream.of(arguments(0, 0, 0, 100000), arguments(2.0D, 0, 200000, 100000), arguments(0.5D, 0, 50000, 100000));
    }
}
