package org.apache.hadoop.tools.contract;

import static org.apache.hadoop.fs.CommonConfigurationKeys.IOSTATISTICS_LOGGING_LEVEL_INFO;
import static org.apache.hadoop.fs.contract.ContractTestUtils.*;
import static org.apache.hadoop.fs.statistics.IOStatisticsLogging.logIOStatisticsAtLevel;
import static org.apache.hadoop.tools.DistCpConstants.CONF_LABEL_DISTCP_JOB_ID;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.contract.AbstractFSContractTestBase;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.tools.CopyListingFileStatus;
import org.apache.hadoop.tools.DistCp;
import org.apache.hadoop.tools.DistCpConstants;
import org.apache.hadoop.tools.DistCpOptions;
import org.apache.hadoop.tools.SimpleCopyListing;
import org.apache.hadoop.tools.mapred.CopyMapper;
import org.apache.hadoop.tools.util.DistCpTestUtils;
import org.apache.hadoop.util.functional.RemoteIterators;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractContractDistCpTest_Purified extends AbstractFSContractTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractContractDistCpTest.class);

    private static final long MODIFICATION_TIME_OFFSET = 10000;

    public static final String SCALE_TEST_DISTCP_FILE_SIZE_KB = "scale.test.distcp.file.size.kb";

    public static final int DEFAULT_DISTCP_SIZE_KB = 1024;

    protected static final int MB = 1024 * 1024;

    protected static final int DEFAULT_DEPTH = 3;

    protected static final int DEFAULT_WIDTH = 2;

    @Rule
    public TestName testName = new TestName();

    protected int getTestTimeoutMillis() {
        return 15 * 60 * 1000;
    }

    private Configuration conf;

    private FileSystem localFS, remoteFS;

    private Path localDir, remoteDir;

    private Path inputDir;

    private Path inputSubDir1;

    private Path inputSubDir2;

    private Path inputSubDir4;

    private Path inputFile1;

    private Path inputFile2;

    private Path inputFile3;

    private Path inputFile4;

    private Path inputFile5;

    private Path outputDir;

    private Path outputSubDir1;

    private Path outputSubDir2;

    private Path outputSubDir4;

    private Path outputFile1;

    private Path outputFile2;

    private Path outputFile3;

    private Path outputFile4;

    private Path outputFile5;

    private Path inputDirUnderOutputDir;

    @Override
    protected Configuration createConfiguration() {
        Configuration newConf = new Configuration();
        newConf.set("mapred.job.tracker", "local");
        return newConf;
    }

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();
        conf = getContract().getConf();
        localFS = FileSystem.getLocal(conf);
        remoteFS = getFileSystem();
        String className = getClass().getSimpleName();
        String testSubDir = className + "/" + testName.getMethodName();
        localDir = localFS.makeQualified(new Path(new Path(GenericTestUtils.getTestDir().toURI()), testSubDir + "/local"));
        localFS.delete(localDir, true);
        mkdirs(localFS, localDir);
        Path testSubPath = path(testSubDir);
        remoteDir = new Path(testSubPath, "remote");
        remoteFS.delete(remoteDir, true);
    }

    @Override
    public void teardown() throws Exception {
        logIOStatisticsAtLevel(LOG, IOSTATISTICS_LOGGING_LEVEL_INFO, getRemoteFS());
        super.teardown();
    }

    protected void initPathFields(final Path src, final Path dest) {
        initInputFields(src);
        initOutputFields(dest);
    }

    protected void initOutputFields(final Path path) {
        outputDir = new Path(path, "outputDir");
        inputDirUnderOutputDir = new Path(outputDir, "inputDir");
        outputFile1 = new Path(inputDirUnderOutputDir, "file1");
        outputSubDir1 = new Path(inputDirUnderOutputDir, "subDir1");
        outputFile2 = new Path(outputSubDir1, "file2");
        outputSubDir2 = new Path(inputDirUnderOutputDir, "subDir2/subDir2");
        outputFile3 = new Path(outputSubDir2, "file3");
        outputSubDir4 = new Path(inputDirUnderOutputDir, "subDir4/subDir4");
        outputFile4 = new Path(outputSubDir4, "file4");
        outputFile5 = new Path(outputSubDir4, "file5");
    }

    protected void initInputFields(final Path srcDir) {
        inputDir = new Path(srcDir, "inputDir");
        inputFile1 = new Path(inputDir, "file1");
        inputSubDir1 = new Path(inputDir, "subDir1");
        inputFile2 = new Path(inputSubDir1, "file2");
        inputSubDir2 = new Path(inputDir, "subDir2/subDir2");
        inputFile3 = new Path(inputSubDir2, "file3");
        inputSubDir4 = new Path(inputDir, "subDir4/subDir4");
        inputFile4 = new Path(inputSubDir4, "file4");
        inputFile5 = new Path(inputSubDir4, "file5");
    }

    protected FileSystem getLocalFS() {
        return localFS;
    }

    protected FileSystem getRemoteFS() {
        return remoteFS;
    }

    protected Path getLocalDir() {
        return localDir;
    }

    protected Path getRemoteDir() {
        return remoteDir;
    }

    void assertCounterInRange(Job job, Enum<?> counter, long min, long max) throws IOException {
        Counter c = job.getCounters().findCounter(counter);
        long value = c.getValue();
        String description = String.format("%s value %s", c.getDisplayName(), value, false);
        if (min >= 0) {
            assertTrue(description + " too below minimum " + min, value >= min);
        }
        if (max >= 0) {
            assertTrue(description + " above maximum " + max, value <= max);
        }
    }

    protected Job distCpUpdateDeepDirectoryStructure(final Path destDir) throws Exception {
        describe("Now do an incremental update with deletion of missing files");
        Path srcDir = inputDir;
        LOG.info("Source directory = {}, dest={}", srcDir, destDir);
        ContractTestUtils.assertPathsExist(localFS, "Paths for test are wrong", inputFile1, inputFile2, inputFile3, inputFile4, inputFile5);
        modifySourceDirectories();
        Job job = distCpUpdate(srcDir, destDir);
        Path outputFileNew1 = new Path(outputSubDir2, "newfile1");
        lsR("Updated Remote", remoteFS, destDir);
        ContractTestUtils.assertPathDoesNotExist(remoteFS, " deleted from " + inputFile1, outputFile1);
        ContractTestUtils.assertIsFile(remoteFS, outputFileNew1);
        ContractTestUtils.assertPathsDoNotExist(remoteFS, "DistCP should have deleted", outputFile3, outputFile4, outputSubDir4);
        assertCounterInRange(job, CopyMapper.Counter.COPY, 1, 1);
        assertCounterInRange(job, CopyMapper.Counter.SKIP, 1, -1);
        return job;
    }

    private Job distCpUpdate(final Path srcDir, final Path destDir) throws Exception {
        describe("\nDistcp -update from " + srcDir + " to " + destDir);
        lsR("Local to update", localFS, srcDir);
        lsR("Remote before update", remoteFS, destDir);
        return runDistCp(buildWithStandardOptions(new DistCpOptions.Builder(Collections.singletonList(srcDir), destDir).withDeleteMissing(true).withSyncFolder(true).withSkipCRC(true).withDirectWrite(shouldUseDirectWrite()).withOverwrite(false)));
    }

    private Job distCpUpdateWithFs(final Path srcDir, final Path destDir, FileSystem sourceFs, FileSystem targetFs) throws Exception {
        describe("\nDistcp -update from " + srcDir + " to " + destDir);
        lsR("Source Fs to update", sourceFs, srcDir);
        lsR("Target Fs before update", targetFs, destDir);
        return runDistCp(buildWithStandardOptions(new DistCpOptions.Builder(Collections.singletonList(srcDir), destDir).withDeleteMissing(true).withSyncFolder(true).withSkipCRC(false).withDirectWrite(shouldUseDirectWrite()).withOverwrite(false)));
    }

    private Path modifySourceDirectories() throws IOException {
        localFS.delete(inputFile1, false);
        localFS.delete(inputFile3, false);
        localFS.delete(inputSubDir4, true);
        Path inputFileNew1 = new Path(inputSubDir2, "newfile1");
        ContractTestUtils.touch(localFS, inputFileNew1);
        return inputFileNew1;
    }

    public void lsR(final String description, final FileSystem fs, final Path dir) throws IOException {
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(dir, true);
        LOG.info("{}: {}:", description, dir);
        StringBuilder sb = new StringBuilder();
        while (files.hasNext()) {
            LocatedFileStatus status = files.next();
            sb.append(String.format("  %s; type=%s; length=%d", status.getPath(), status.isDirectory() ? "dir" : "file", status.getLen()));
        }
        LOG.info("{}", sb);
    }

    private Path distCpDeepDirectoryStructure(FileSystem srcFS, Path srcDir, FileSystem dstFS, Path dstDir) throws Exception {
        initPathFields(srcDir, dstDir);
        mkdirs(srcFS, inputSubDir1);
        mkdirs(srcFS, inputSubDir2);
        byte[] data1 = dataset(100, 33, 43);
        createFile(srcFS, inputFile1, true, data1);
        byte[] data2 = dataset(200, 43, 53);
        createFile(srcFS, inputFile2, true, data2);
        byte[] data3 = dataset(300, 53, 63);
        createFile(srcFS, inputFile3, true, data3);
        createFile(srcFS, inputFile4, true, dataset(400, 53, 63));
        createFile(srcFS, inputFile5, true, dataset(500, 53, 63));
        Path target = new Path(dstDir, "outputDir");
        runDistCp(inputDir, target);
        ContractTestUtils.assertIsDirectory(dstFS, target);
        lsR("Destination tree after distcp", dstFS, target);
        verifyFileContents(dstFS, new Path(target, "inputDir/file1"), data1);
        verifyFileContents(dstFS, new Path(target, "inputDir/subDir1/file2"), data2);
        verifyFileContents(dstFS, new Path(target, "inputDir/subDir2/subDir2/file3"), data3);
        return target;
    }

    private void largeFiles(FileSystem srcFS, Path srcDir, FileSystem dstFS, Path dstDir) throws Exception {
        int fileSizeKb = conf.getInt(SCALE_TEST_DISTCP_FILE_SIZE_KB, getDefaultDistCPSizeKb());
        if (fileSizeKb < 1) {
            skip("File size in " + SCALE_TEST_DISTCP_FILE_SIZE_KB + " is zero");
        }
        initPathFields(srcDir, dstDir);
        Path largeFile1 = new Path(inputDir, "file1");
        Path largeFile2 = new Path(inputDir, "file2");
        Path largeFile3 = new Path(inputDir, "file3");
        int fileSizeMb = fileSizeKb / 1024;
        getLogger().info("{} with file size {}", testName.getMethodName(), fileSizeMb);
        byte[] data1 = dataset((fileSizeMb + 1) * MB, 33, 43);
        createFile(srcFS, largeFile1, true, data1);
        byte[] data2 = dataset((fileSizeMb + 2) * MB, 43, 53);
        createFile(srcFS, largeFile2, true, data2);
        byte[] data3 = dataset((fileSizeMb + 3) * MB, 53, 63);
        createFile(srcFS, largeFile3, true, data3);
        Path target = new Path(dstDir, "outputDir");
        runDistCp(inputDir, target);
        verifyFileContents(dstFS, new Path(target, "inputDir/file1"), data1);
        verifyFileContents(dstFS, new Path(target, "inputDir/file2"), data2);
        verifyFileContents(dstFS, new Path(target, "inputDir/file3"), data3);
    }

    protected int getDefaultDistCPSizeKb() {
        return DEFAULT_DISTCP_SIZE_KB;
    }

    private void runDistCp(Path src, Path dst) throws Exception {
        if (shouldUseDirectWrite()) {
            runDistCpDirectWrite(src, dst);
        } else {
            runDistCpWithRename(src, dst);
        }
    }

    private Job runDistCp(final DistCpOptions options) throws Exception {
        Job job = new DistCp(conf, options).execute();
        assertNotNull("Unexpected null job returned from DistCp execution.", job);
        assertTrue("DistCp job did not complete.", job.isComplete());
        assertTrue("DistCp job did not complete successfully.", job.isSuccessful());
        return job;
    }

    private DistCpOptions buildWithStandardOptions(DistCpOptions.Builder builder) {
        return builder.withNumListstatusThreads(DistCpOptions.MAX_NUM_LISTSTATUS_THREADS).build();
    }

    private static void mkdirs(FileSystem fs, Path dir) throws Exception {
        assertTrue("Failed to mkdir " + dir, fs.mkdirs(dir));
    }

    public int getDepth() {
        return DEFAULT_DEPTH;
    }

    public int getWidth() {
        return DEFAULT_WIDTH;
    }

    private int getTotalFiles() {
        int totalFiles = 0;
        for (int i = 1; i <= getDepth(); i++) {
            totalFiles += Math.pow(getWidth(), i);
        }
        return totalFiles;
    }

    protected boolean shouldUseDirectWrite() {
        return false;
    }

    protected String getDefaultCLIOptions() {
        return shouldUseDirectWrite() ? " -direct " : "";
    }

    protected String getDefaultCLIOptionsOrNull() {
        return shouldUseDirectWrite() ? " -direct " : null;
    }

    private void directWrite(FileSystem srcFS, Path srcDir, FileSystem dstFS, Path dstDir, boolean directWrite) throws Exception {
        initPathFields(srcDir, dstDir);
        mkdirs(srcFS, inputSubDir1);
        byte[] data1 = dataset(64, 33, 43);
        createFile(srcFS, inputFile1, true, data1);
        byte[] data2 = dataset(200, 43, 53);
        createFile(srcFS, inputFile2, true, data2);
        Path target = new Path(dstDir, "outputDir");
        if (directWrite) {
            runDistCpDirectWrite(inputDir, target);
        } else {
            runDistCpWithRename(inputDir, target);
        }
        ContractTestUtils.assertIsDirectory(dstFS, target);
        lsR("Destination tree after distcp", dstFS, target);
        verifyFileContents(dstFS, new Path(target, "inputDir/file1"), data1);
        verifyFileContents(dstFS, new Path(target, "inputDir/subDir1/file2"), data2);
    }

    private Job runDistCpDirectWrite(final Path srcDir, final Path destDir) throws Exception {
        describe("\nDistcp -direct from " + srcDir + " to " + destDir);
        return runDistCp(buildWithStandardOptions(new DistCpOptions.Builder(Collections.singletonList(srcDir), destDir).withDirectWrite(true)));
    }

    private Job runDistCpWithRename(Path srcDir, final Path destDir) throws Exception {
        describe("\nDistcp from " + srcDir + " to " + destDir);
        return runDistCp(buildWithStandardOptions(new DistCpOptions.Builder(Collections.singletonList(srcDir), destDir).withDirectWrite(false)));
    }

    private void verifySkipAndCopyCounter(Job job, int skipExpectedValue, int copyExpectedValue) throws IOException {
        long skipActualValue = job.getCounters().findCounter(CopyMapper.Counter.SKIP).getValue();
        long copyActualValue = job.getCounters().findCounter(CopyMapper.Counter.COPY).getValue();
        assertEquals("Mismatch in COPY counter value", copyExpectedValue, copyActualValue);
        assertEquals("Mismatch in SKIP counter value", skipExpectedValue, skipActualValue);
    }

    @Test
    public void testSetJobId_1() throws Exception {
        remoteFS.create(new Path(remoteDir, "file1")).close();
        DistCpTestUtils.assertRunDistCp(DistCpConstants.SUCCESS, remoteDir.toString(), localDir.toString(), getDefaultCLIOptionsOrNull(), conf);
    }

    @Test
    public void testSetJobId_2() throws Exception {
        assertNotNull("DistCp job id isn't set", conf.get(CONF_LABEL_DISTCP_JOB_ID));
    }
}
