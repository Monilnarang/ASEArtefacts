package org.apache.hadoop.fs.s3a.scale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FutureDataInputStreamBuilder;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.s3a.S3AFileSystem;
import org.apache.hadoop.fs.s3a.S3AInputPolicy;
import org.apache.hadoop.fs.s3a.S3AInputStream;
import org.apache.hadoop.fs.s3a.S3ATestUtils;
import org.apache.hadoop.fs.s3a.statistics.S3AInputStreamStatistics;
import org.apache.hadoop.fs.statistics.IOStatistics;
import org.apache.hadoop.fs.statistics.IOStatisticsSnapshot;
import org.apache.hadoop.fs.statistics.MeanStatistic;
import org.apache.hadoop.fs.statistics.StreamStatisticNames;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.util.LineReader;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.EOFException;
import java.io.IOException;
import static org.apache.hadoop.fs.Options.OpenFileOptions.FS_OPTION_OPENFILE_BUFFER_SIZE;
import static org.apache.hadoop.fs.Options.OpenFileOptions.FS_OPTION_OPENFILE_LENGTH;
import static org.apache.hadoop.fs.Options.OpenFileOptions.FS_OPTION_OPENFILE_READ_POLICY;
import static org.apache.hadoop.fs.contract.ContractTestUtils.*;
import static org.apache.hadoop.fs.s3a.Constants.*;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.assume;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getInputStreamStatistics;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getS3AInputStream;
import static org.apache.hadoop.fs.statistics.IOStatisticAssertions.assertThatStatisticMinimum;
import static org.apache.hadoop.fs.statistics.IOStatisticAssertions.lookupMaximumStatistic;
import static org.apache.hadoop.fs.statistics.IOStatisticAssertions.lookupMeanStatistic;
import static org.apache.hadoop.fs.statistics.IOStatisticAssertions.verifyStatisticCounterValue;
import static org.apache.hadoop.fs.statistics.IOStatisticsLogging.ioStatisticsToPrettyString;
import static org.apache.hadoop.fs.statistics.IOStatisticsLogging.ioStatisticsToString;
import static org.apache.hadoop.fs.statistics.IOStatisticsSupport.snapshotIOStatistics;
import static org.apache.hadoop.fs.statistics.StoreStatisticNames.ACTION_HTTP_GET_REQUEST;
import static org.apache.hadoop.fs.statistics.StoreStatisticNames.SUFFIX_MAX;
import static org.apache.hadoop.fs.statistics.StoreStatisticNames.SUFFIX_MEAN;
import static org.apache.hadoop.fs.statistics.StoreStatisticNames.SUFFIX_MIN;
import static org.apache.hadoop.util.functional.FutureIO.awaitFuture;

public class ITestS3AInputStreamPerformance_Purified extends S3AScaleTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ITestS3AInputStreamPerformance.class);

    private static final int READAHEAD_128K = 128 * _1KB;

    private S3AFileSystem s3aFS;

    private Path testData;

    private FileStatus testDataStatus;

    private FSDataInputStream in;

    private S3AInputStreamStatistics streamStatistics;

    public static final int BLOCK_SIZE = 32 * 1024;

    public static final int BIG_BLOCK_SIZE = 256 * 1024;

    private static final IOStatisticsSnapshot IOSTATS = snapshotIOStatistics();

    private boolean testDataAvailable = true;

    private String assumptionMessage = "test file";

    @Override
    protected Configuration createScaleConfiguration() {
        Configuration conf = super.createScaleConfiguration();
        S3ATestUtils.removeBaseAndBucketOverrides(conf, PREFETCH_ENABLED_KEY);
        conf.setBoolean(PREFETCH_ENABLED_KEY, false);
        return conf;
    }

    @Before
    public void openFS() throws IOException {
        Configuration conf = getConf();
        conf.setInt(SOCKET_SEND_BUFFER, 16 * 1024);
        conf.setInt(SOCKET_RECV_BUFFER, 16 * 1024);
        String testFile = conf.getTrimmed(KEY_CSVTEST_FILE, DEFAULT_CSVTEST_FILE);
        if (testFile.isEmpty()) {
            assumptionMessage = "Empty test property: " + KEY_CSVTEST_FILE;
            LOG.warn(assumptionMessage);
            testDataAvailable = false;
        } else {
            testData = new Path(testFile);
            LOG.info("Using {} as input stream source", testData);
            Path path = this.testData;
            bindS3aFS(path);
            try {
                testDataStatus = s3aFS.getFileStatus(this.testData);
            } catch (IOException e) {
                LOG.warn("Failed to read file {} specified in {}", testFile, KEY_CSVTEST_FILE, e);
                throw e;
            }
        }
    }

    private void bindS3aFS(Path path) throws IOException {
        s3aFS = (S3AFileSystem) FileSystem.newInstance(path.toUri(), getConf());
    }

    @After
    public void cleanup() {
        describe("cleanup");
        IOUtils.closeStream(in);
        if (in != null) {
            final IOStatistics stats = in.getIOStatistics();
            LOG.info("Stream statistics {}", ioStatisticsToPrettyString(stats));
            IOSTATS.aggregate(stats);
        }
        if (s3aFS != null) {
            final IOStatistics stats = s3aFS.getIOStatistics();
            LOG.info("FileSystem statistics {}", ioStatisticsToPrettyString(stats));
            FILESYSTEM_IOSTATS.aggregate(stats);
            IOUtils.closeStream(s3aFS);
        }
    }

    @AfterClass
    public static void dumpIOStatistics() {
        LOG.info("Aggregate Stream Statistics {}", IOSTATS);
    }

    private void requireCSVTestData() {
        assume(assumptionMessage, testDataAvailable);
    }

    FSDataInputStream openTestFile() throws IOException {
        return openTestFile(S3AInputPolicy.Normal, 0);
    }

    FSDataInputStream openTestFile(S3AInputPolicy inputPolicy, long readahead) throws IOException {
        requireCSVTestData();
        return openDataFile(s3aFS, testData, inputPolicy, readahead, testDataStatus.getLen());
    }

    private FSDataInputStream openDataFile(S3AFileSystem fs, Path path, S3AInputPolicy inputPolicy, long readahead, final long length) throws IOException {
        int bufferSize = getConf().getInt(KEY_READ_BUFFER_SIZE, DEFAULT_READ_BUFFER_SIZE);
        final FutureDataInputStreamBuilder builder = fs.openFile(path).opt(FS_OPTION_OPENFILE_READ_POLICY, inputPolicy.toString()).optLong(FS_OPTION_OPENFILE_LENGTH, length).opt(FS_OPTION_OPENFILE_BUFFER_SIZE, bufferSize).optLong(READAHEAD_RANGE, readahead);
        FSDataInputStream stream = awaitFuture(builder.build());
        streamStatistics = getInputStreamStatistics(stream);
        return stream;
    }

    protected void assertStreamOpenedExactlyOnce() {
        assertOpenOperationCount(1);
    }

    private void assertOpenOperationCount(long expected) {
        assertEquals("open operations in\n" + in, expected, streamStatistics.getOpenOperations());
    }

    protected void logTimePerIOP(String operation, NanoTimer timer, long count) {
        LOG.info("Time per {}: {} nS", operation, toHuman(timer.duration() / count));
    }

    public static double bandwidth(NanoTimer timer, long bytes) {
        return bytes * 1.0e9 / timer.duration();
    }

    private void executeDecompression(long readahead, S3AInputPolicy inputPolicy) throws IOException {
        CompressionCodecFactory factory = new CompressionCodecFactory(getConf());
        CompressionCodec codec = factory.getCodec(testData);
        long bytesRead = 0;
        int lines = 0;
        FSDataInputStream objectIn = openTestFile(inputPolicy, readahead);
        IOStatistics readerStatistics = null;
        ContractTestUtils.NanoTimer timer = new ContractTestUtils.NanoTimer();
        try (LineReader lineReader = new LineReader(codec.createInputStream(objectIn), getConf())) {
            readerStatistics = lineReader.getIOStatistics();
            Text line = new Text();
            int read;
            while ((read = lineReader.readLine(line)) > 0) {
                bytesRead += read;
                lines++;
            }
        } catch (EOFException eof) {
        }
        timer.end("Time to read %d lines [%d bytes expanded, %d raw]" + " with readahead = %d", lines, bytesRead, testDataStatus.getLen(), readahead);
        logTimePerIOP("line read", timer, lines);
        logStreamStatistics();
        assertNotNull("No IOStatistics through line reader", readerStatistics);
        LOG.info("statistics from reader {}", ioStatisticsToString(readerStatistics));
    }

    private void logStreamStatistics() {
        LOG.info(String.format("Stream Statistics%n{}"), streamStatistics);
    }

    protected void executeSeekReadSequence(long blockSize, long readahead, S3AInputPolicy policy) throws IOException {
        in = openTestFile(policy, readahead);
        long len = testDataStatus.getLen();
        NanoTimer timer = new NanoTimer();
        long blockCount = len / blockSize;
        LOG.info("Reading {} blocks, readahead = {}", blockCount, readahead);
        for (long i = 0; i < blockCount; i++) {
            in.seek(in.getPos() + blockSize - 1);
            assertTrue(in.read() >= 0);
        }
        timer.end("Time to execute %d seeks of distance %d with readahead = %d", blockCount, blockSize, readahead);
        logTimePerIOP("seek(pos + " + blockCount + "); read()", timer, blockCount);
        LOG.info("Effective bandwidth {} MB/S", timer.bandwidthDescription(streamStatistics.getBytesRead() - streamStatistics.getBytesSkippedOnSeek()));
        logStreamStatistics();
    }

    public static final int _4K = 4 * 1024;

    public static final int _8K = 8 * 1024;

    public static final int _16K = 16 * 1024;

    public static final int _32K = 32 * 1024;

    public static final int _64K = 64 * 1024;

    public static final int _128K = 128 * 1024;

    public static final int _256K = 256 * 1024;

    public static final int _1MB = 1024 * 1024;

    public static final int _2MB = 2 * _1MB;

    public static final int _10MB = _1MB * 10;

    public static final int _5MB = _1MB * 5;

    private static final int[][] RANDOM_IO_SEQUENCE = { { _2MB, _128K }, { _128K, _128K }, { _5MB, _64K }, { _1MB, _1MB } };

    private ContractTestUtils.NanoTimer executeRandomIO(S3AInputPolicy policy, long expectedOpenCount) throws IOException {
        describe("Random IO with policy \"%s\"", policy);
        byte[] buffer = new byte[_1MB];
        long totalBytesRead = 0;
        in = openTestFile(policy, 0);
        ContractTestUtils.NanoTimer timer = new ContractTestUtils.NanoTimer();
        for (int[] action : RANDOM_IO_SEQUENCE) {
            int position = action[0];
            int range = action[1];
            in.readFully(position, buffer, 0, range);
            totalBytesRead += range;
        }
        int reads = RANDOM_IO_SEQUENCE.length;
        timer.end("Time to execute %d reads of total size %d bytes", reads, totalBytesRead);
        in.close();
        assertOpenOperationCount(expectedOpenCount);
        logTimePerIOP("byte read", timer, totalBytesRead);
        LOG.info("Effective bandwidth {} MB/S", timer.bandwidthDescription(streamStatistics.getBytesRead() - streamStatistics.getBytesSkippedOnSeek()));
        logStreamStatistics();
        IOStatistics iostats = in.getIOStatistics();
        long maxHttpGet = lookupMaximumStatistic(iostats, ACTION_HTTP_GET_REQUEST + SUFFIX_MAX);
        assertThatStatisticMinimum(iostats, ACTION_HTTP_GET_REQUEST + SUFFIX_MIN).isGreaterThan(0).isLessThan(maxHttpGet);
        MeanStatistic getMeanStat = lookupMeanStatistic(iostats, ACTION_HTTP_GET_REQUEST + SUFFIX_MEAN);
        Assertions.assertThat(getMeanStat.getSamples()).describedAs("sample count of %s", getMeanStat).isEqualTo(expectedOpenCount);
        return timer;
    }

    S3AInputStream getS3aStream() {
        return (S3AInputStream) in.getWrappedStream();
    }

    @Test
    public void testRandomIONormalPolicy_1() throws Throwable {
        assertEquals("streams aborted in " + streamStatistics, 1, streamStatistics.getAborted());
    }

    @Test
    public void testRandomIONormalPolicy_2() throws Throwable {
        assertEquals("policy changes in " + streamStatistics, 2, streamStatistics.getPolicySetCount());
    }

    @Test
    public void testRandomIONormalPolicy_3() throws Throwable {
        executeRandomIO(S3AInputPolicy.Normal, expectedOpenCount);
        assertEquals("input policy in " + streamStatistics, S3AInputPolicy.Random.ordinal(), streamStatistics.getInputPolicy());
    }
}
