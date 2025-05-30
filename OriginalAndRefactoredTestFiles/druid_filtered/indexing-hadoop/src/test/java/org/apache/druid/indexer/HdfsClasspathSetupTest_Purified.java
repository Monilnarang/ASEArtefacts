package org.apache.druid.indexer;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.io.IOUtils;
import org.apache.druid.common.utils.UUIDUtils;
import org.apache.druid.java.util.common.FileUtils;
import org.apache.druid.java.util.common.IOE;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.concurrent.Execs;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HdfsClasspathSetupTest_Purified {

    private static FileSystem localFS;

    private static File hdfsTmpDir;

    private static Configuration conf;

    private static String dummyJarString = "This is a test jar file.";

    private File dummyJarFile;

    private Path finalClasspath;

    private Path intermediatePath;

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setupStatic() throws IOException {
        hdfsTmpDir = File.createTempFile("hdfsClasspathSetupTest", "dir");
        if (!hdfsTmpDir.delete()) {
            throw new IOE("Unable to delete hdfsTmpDir [%s]", hdfsTmpDir.getAbsolutePath());
        }
        conf = new Configuration(true);
        localFS = new LocalFileSystem();
        localFS.initialize(hdfsTmpDir.toURI(), conf);
        localFS.setWorkingDirectory(new Path(hdfsTmpDir.toURI()));
    }

    @Before
    public void setUp() throws IOException {
        intermediatePath = new Path(StringUtils.format("tmp/classpath/%s", UUIDUtils.generateUuid()));
        finalClasspath = new Path(StringUtils.format("tmp/intermediate/%s", UUIDUtils.generateUuid()));
        dummyJarFile = tempFolder.newFile("dummy-test.jar");
        Files.copy(new ByteArrayInputStream(StringUtils.toUtf8(dummyJarString)), dummyJarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterClass
    public static void tearDownStatic() throws IOException {
        FileUtils.deleteDirectory(hdfsTmpDir);
    }

    @After
    public void tearDown() throws IOException {
        dummyJarFile.delete();
        Assert.assertFalse(dummyJarFile.exists());
        localFS.delete(finalClasspath, true);
        Assert.assertFalse(localFS.exists(finalClasspath));
        localFS.delete(intermediatePath, true);
        Assert.assertFalse(localFS.exists(intermediatePath));
    }

    @Test
    public void testIsSnapshot_1() {
        Assert.assertTrue(JobHelper.isSnapshot(new File("test-SNAPSHOT.jar")));
    }

    @Test
    public void testIsSnapshot_2() {
        Assert.assertTrue(JobHelper.isSnapshot(new File("test-SNAPSHOT-selfcontained.jar")));
    }

    @Test
    public void testIsSnapshot_3() {
        Assert.assertFalse(JobHelper.isSnapshot(new File("test.jar")));
    }

    @Test
    public void testIsSnapshot_4() {
        Assert.assertFalse(JobHelper.isSnapshot(new File("test-selfcontained.jar")));
    }

    @Test
    public void testIsSnapshot_5() {
        Assert.assertFalse(JobHelper.isSnapshot(new File("iAmNotSNAPSHOT.jar")));
    }

    @Test
    public void testIsSnapshot_6() {
        Assert.assertFalse(JobHelper.isSnapshot(new File("iAmNotSNAPSHOT-selfcontained.jar")));
    }
}
