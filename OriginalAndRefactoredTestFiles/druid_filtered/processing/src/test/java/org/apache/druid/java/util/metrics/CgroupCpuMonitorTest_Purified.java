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

public class CgroupCpuMonitorTest_Purified {

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
    public void testQuotaCompute_2() {
        Assert.assertEquals(0, CgroupCpuMonitor.computeProcessorQuota(0, 100000), 0);
    }

    @Test
    public void testQuotaCompute_3() {
        Assert.assertEquals(-1, CgroupCpuMonitor.computeProcessorQuota(100000, 0), 0);
    }

    @Test
    public void testQuotaCompute_4() {
        Assert.assertEquals(2.0D, CgroupCpuMonitor.computeProcessorQuota(200000, 100000), 0);
    }

    @Test
    public void testQuotaCompute_5() {
        Assert.assertEquals(0.5D, CgroupCpuMonitor.computeProcessorQuota(50000, 100000), 0);
    }
}
