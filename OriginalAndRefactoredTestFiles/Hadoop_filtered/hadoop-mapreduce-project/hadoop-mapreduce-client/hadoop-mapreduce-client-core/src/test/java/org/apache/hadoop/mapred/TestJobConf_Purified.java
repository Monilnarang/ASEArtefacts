package org.apache.hadoop.mapred;

import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.junit.Assert;
import org.junit.Test;

public class TestJobConf_Purified {

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testJobConf_49() {
        assertEquals("The variable key is no longer used.", JobConf.deprecatedString("key"));
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testJobConf_1_testMerged_2() {
        JobConf conf = new JobConf();
        Pattern pattern = conf.getJarUnpackPattern();
        assertEquals(Pattern.compile("(?:classes/|lib/).*").toString(), pattern.toString());
        assertFalse(conf.getKeepFailedTaskFiles());
        conf.setKeepFailedTaskFiles(true);
        assertTrue(conf.getKeepFailedTaskFiles());
        assertNull(conf.getKeepTaskFilesPattern());
        conf.setKeepTaskFilesPattern("123454");
        assertEquals("123454", conf.getKeepTaskFilesPattern());
        assertNotNull(conf.getWorkingDirectory());
        conf.setWorkingDirectory(new Path("test"));
        assertTrue(conf.getWorkingDirectory().toString().endsWith("test"));
        assertEquals(1, conf.getNumTasksToExecutePerJvm());
        assertNull(conf.getKeyFieldComparatorOption());
        conf.setKeyFieldComparatorOptions("keySpec");
        assertEquals("keySpec", conf.getKeyFieldComparatorOption());
        assertFalse(conf.getUseNewReducer());
        conf.setUseNewReducer(true);
        assertTrue(conf.getUseNewReducer());
        assertTrue(conf.getMapSpeculativeExecution());
        assertTrue(conf.getReduceSpeculativeExecution());
        assertTrue(conf.getSpeculativeExecution());
        conf.setReduceSpeculativeExecution(false);
        conf.setMapSpeculativeExecution(false);
        assertFalse(conf.getSpeculativeExecution());
        assertFalse(conf.getMapSpeculativeExecution());
        assertFalse(conf.getReduceSpeculativeExecution());
        conf.setSessionId("ses");
        assertEquals("ses", conf.getSessionId());
        assertEquals(3, conf.getMaxTaskFailuresPerTracker());
        conf.setMaxTaskFailuresPerTracker(2);
        assertEquals(2, conf.getMaxTaskFailuresPerTracker());
        assertEquals(0, conf.getMaxMapTaskFailuresPercent());
        conf.setMaxMapTaskFailuresPercent(50);
        assertEquals(50, conf.getMaxMapTaskFailuresPercent());
        assertEquals(0, conf.getMaxReduceTaskFailuresPercent());
        conf.setMaxReduceTaskFailuresPercent(70);
        assertEquals(70, conf.getMaxReduceTaskFailuresPercent());
        assertThat(conf.getJobPriority()).isEqualTo(JobPriority.DEFAULT);
        conf.setJobPriority(JobPriority.HIGH);
        assertThat(conf.getJobPriority()).isEqualTo(JobPriority.HIGH);
        assertNull(conf.getJobSubmitHostName());
        conf.setJobSubmitHostName("hostname");
        assertEquals("hostname", conf.getJobSubmitHostName());
        assertNull(conf.getJobSubmitHostAddress());
        conf.setJobSubmitHostAddress("ww");
        assertEquals("ww", conf.getJobSubmitHostAddress());
        assertFalse(conf.getProfileEnabled());
        conf.setProfileEnabled(true);
        assertTrue(conf.getProfileEnabled());
        assertEquals(conf.getProfileTaskRange(true).toString(), "0-2");
        assertEquals(conf.getProfileTaskRange(false).toString(), "0-2");
        conf.setProfileTaskRange(true, "0-3");
        assertEquals(conf.getProfileTaskRange(true).toString(), "0-3");
        assertNull(conf.getMapDebugScript());
        conf.setMapDebugScript("mDbgScript");
        assertEquals("mDbgScript", conf.getMapDebugScript());
        assertNull(conf.getReduceDebugScript());
        conf.setReduceDebugScript("rDbgScript");
        assertEquals("rDbgScript", conf.getReduceDebugScript());
        assertNull(conf.getJobLocalDir());
        assertEquals("default", conf.getQueueName());
        conf.setQueueName("qname");
        assertEquals("qname", conf.getQueueName());
        conf.setMemoryForMapTask(100 * 1000);
        assertEquals(100 * 1000, conf.getMemoryForMapTask());
        conf.setMemoryForReduceTask(1000 * 1000);
        assertEquals(1000 * 1000, conf.getMemoryForReduceTask());
        assertEquals(-1, conf.getMaxPhysicalMemoryForTask());
        assertNull("mapreduce.map.java.opts should not be set by default", conf.get(JobConf.MAPRED_MAP_TASK_JAVA_OPTS));
        assertNull("mapreduce.reduce.java.opts should not be set by default", conf.get(JobConf.MAPRED_REDUCE_TASK_JAVA_OPTS));
    }

    @Test
    public void testParseMaximumHeapSizeMB_1() {
        Assert.assertEquals(4096, JobConf.parseMaximumHeapSizeMB("-Xmx4294967296"));
    }

    @Test
    public void testParseMaximumHeapSizeMB_2() {
        Assert.assertEquals(4096, JobConf.parseMaximumHeapSizeMB("-Xmx4194304k"));
    }

    @Test
    public void testParseMaximumHeapSizeMB_3() {
        Assert.assertEquals(4096, JobConf.parseMaximumHeapSizeMB("-Xmx4096m"));
    }

    @Test
    public void testParseMaximumHeapSizeMB_4() {
        Assert.assertEquals(4096, JobConf.parseMaximumHeapSizeMB("-Xmx4g"));
    }

    @Test
    public void testParseMaximumHeapSizeMB_5() {
        Assert.assertEquals(-1, JobConf.parseMaximumHeapSizeMB("-Xmx4?"));
    }

    @Test
    public void testParseMaximumHeapSizeMB_6() {
        Assert.assertEquals(-1, JobConf.parseMaximumHeapSizeMB(""));
    }
}
