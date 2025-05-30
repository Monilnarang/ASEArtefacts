package org.apache.hadoop.mapreduce.lib.jobcontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.HadoopTestCase;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MapReduceTestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestMapReduceJobControl_Purified extends HadoopTestCase {

    public static final Logger LOG = LoggerFactory.getLogger(TestMapReduceJobControl.class);

    static Path rootDataDir = new Path(System.getProperty("test.build.data", "."), "TestData");

    static Path indir = new Path(rootDataDir, "indir");

    static Path outdir_1 = new Path(rootDataDir, "outdir_1");

    static Path outdir_2 = new Path(rootDataDir, "outdir_2");

    static Path outdir_3 = new Path(rootDataDir, "outdir_3");

    static Path outdir_4 = new Path(rootDataDir, "outdir_4");

    static ControlledJob cjob1 = null;

    static ControlledJob cjob2 = null;

    static ControlledJob cjob3 = null;

    static ControlledJob cjob4 = null;

    public TestMapReduceJobControl() throws IOException {
        super(HadoopTestCase.LOCAL_MR, HadoopTestCase.LOCAL_FS, 2, 2);
    }

    private void cleanupData(Configuration conf) throws Exception {
        FileSystem fs = FileSystem.get(conf);
        MapReduceTestUtil.cleanData(fs, indir);
        MapReduceTestUtil.generateData(fs, indir);
        MapReduceTestUtil.cleanData(fs, outdir_1);
        MapReduceTestUtil.cleanData(fs, outdir_2);
        MapReduceTestUtil.cleanData(fs, outdir_3);
        MapReduceTestUtil.cleanData(fs, outdir_4);
    }

    private JobControl createDependencies(Configuration conf, Job job1) throws Exception {
        List<ControlledJob> dependingJobs = null;
        cjob1 = new ControlledJob(job1, dependingJobs);
        Job job2 = MapReduceTestUtil.createCopyJob(conf, outdir_2, indir);
        cjob2 = new ControlledJob(job2, dependingJobs);
        Job job3 = MapReduceTestUtil.createCopyJob(conf, outdir_3, outdir_1, outdir_2);
        dependingJobs = new ArrayList<ControlledJob>();
        dependingJobs.add(cjob1);
        dependingJobs.add(cjob2);
        cjob3 = new ControlledJob(job3, dependingJobs);
        Job job4 = MapReduceTestUtil.createCopyJob(conf, outdir_4, outdir_3);
        dependingJobs = new ArrayList<ControlledJob>();
        dependingJobs.add(cjob3);
        cjob4 = new ControlledJob(job4, dependingJobs);
        JobControl theControl = new JobControl("Test");
        theControl.addJob(cjob1);
        theControl.addJob(cjob2);
        theControl.addJob(cjob3);
        theControl.addJob(cjob4);
        Thread theController = new Thread(theControl);
        theController.start();
        return theControl;
    }

    private void waitTillAllFinished(JobControl theControl) {
        while (!theControl.allFinished()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    @Test
    public void testJobControlWithFailJob_1() throws Exception {
        assertTrue(cjob1.getJobState() == ControlledJob.State.FAILED);
    }

    @Test
    public void testJobControlWithFailJob_2() throws Exception {
        assertTrue(cjob2.getJobState() == ControlledJob.State.SUCCESS);
    }

    @Test
    public void testJobControlWithFailJob_3() throws Exception {
        assertTrue(cjob3.getJobState() == ControlledJob.State.DEPENDENT_FAILED);
    }

    @Test
    public void testJobControlWithFailJob_4() throws Exception {
        assertTrue(cjob4.getJobState() == ControlledJob.State.DEPENDENT_FAILED);
    }
}
