package org.apache.hadoop.hdfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.HadoopTestCase;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Time;
import org.apache.hadoop.util.ToolRunner;
import org.junit.After;
import org.junit.Test;

public class TestNNBench_Purified extends HadoopTestCase {

    private static final String BASE_DIR = new File(System.getProperty("test.build.data", "build/test/data"), "NNBench").getAbsolutePath();

    public TestNNBench() throws IOException {
        super(LOCAL_MR, LOCAL_FS, 1, 1);
    }

    @After
    public void tearDown() throws Exception {
        getFileSystem().delete(new Path(BASE_DIR), true);
        getFileSystem().delete(new Path(NNBench.DEFAULT_RES_FILE_NAME), true);
        super.tearDown();
    }

    private void runNNBench(Configuration conf, String operation, String baseDir) throws Exception {
        String[] genArgs = { "-operation", operation, "-baseDir", baseDir, "-startTime", "" + (Time.now() / 1000 + 3), "-blockSize", "1024" };
        assertEquals(0, ToolRunner.run(conf, new NNBench(), genArgs));
    }

    private void runNNBench(Configuration conf, String operation) throws Exception {
        runNNBench(conf, operation, BASE_DIR);
    }

    @Test(timeout = 30000)
    public void testNNBenchCreateAndRename_1_testMerged_1() throws Exception {
        Path path = new Path(BASE_DIR + "/data/file_0_0");
        assertTrue("create_write should create the file", getFileSystem().exists(path));
        assertFalse("Rename should rename the file", getFileSystem().exists(path));
    }

    @Test(timeout = 30000)
    public void testNNBenchCreateAndRename_3() throws Exception {
        Path renamedPath = new Path(BASE_DIR + "/data/file_0_r_0");
        assertTrue("Rename should rename the file", getFileSystem().exists(renamedPath));
    }
}
