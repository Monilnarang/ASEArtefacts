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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HdfsClasspathSetupTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testIsSnapshot_1to2")
    public void testIsSnapshot_1to2(String param1) {
        Assert.assertTrue(JobHelper.isSnapshot(new File(param1)));
    }

    static public Stream<Arguments> Provider_testIsSnapshot_1to2() {
        return Stream.of(arguments("test-SNAPSHOT.jar"), arguments("test-SNAPSHOT-selfcontained.jar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsSnapshot_3to6")
    public void testIsSnapshot_3to6(String param1) {
        Assert.assertFalse(JobHelper.isSnapshot(new File(param1)));
    }

    static public Stream<Arguments> Provider_testIsSnapshot_3to6() {
        return Stream.of(arguments("test.jar"), arguments("test-selfcontained.jar"), arguments("iAmNotSNAPSHOT.jar"), arguments("iAmNotSNAPSHOT-selfcontained.jar"));
    }
}
