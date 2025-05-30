package org.apache.hadoop.fs.s3a.select;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.Assume;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSExceptionMessages;
import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FutureDataInputStreamBuilder;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathIOException;
import org.apache.hadoop.fs.Seekable;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.impl.AbstractFSBuilderImpl;
import org.apache.hadoop.fs.s3a.AWSBadRequestException;
import org.apache.hadoop.fs.s3a.AWSServiceIOException;
import org.apache.hadoop.fs.s3a.Constants;
import org.apache.hadoop.fs.s3a.S3AFileSystem;
import org.apache.hadoop.fs.s3a.S3AInputStream;
import org.apache.hadoop.fs.s3a.S3ATestUtils;
import org.apache.hadoop.fs.s3a.Statistic;
import org.apache.hadoop.fs.s3a.statistics.S3AInputStreamStatistics;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.task.JobContextImpl;
import org.apache.hadoop.util.DurationInfo;
import static org.apache.hadoop.fs.Options.OpenFileOptions.FS_OPTION_OPENFILE_READ_POLICY;
import static org.apache.hadoop.fs.Options.OpenFileOptions.FS_OPTION_OPENFILE_READ_POLICY_DEFAULT;
import static org.apache.hadoop.fs.s3a.Constants.READAHEAD_RANGE;
import static org.apache.hadoop.fs.s3a.select.CsvFile.ALL_QUOTES;
import static org.apache.hadoop.fs.s3a.select.SelectBinding.expandBackslashChars;
import static org.apache.hadoop.fs.s3a.select.SelectConstants.*;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;
import static org.apache.hadoop.test.LambdaTestUtils.interceptFuture;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class ITestS3Select_Purified extends AbstractS3SelectTest {

    private static final Logger LOG = LoggerFactory.getLogger(ITestS3Select.class);

    public static final String E_CAST_FAILED = "CastFailed";

    public static final String E_PARSE_INVALID_PATH_COMPONENT = "ParseInvalidPathComponent";

    public static final String E_INVALID_TABLE_ALIAS = "InvalidTableAlias";

    private Configuration selectConf;

    private Path csvPath;

    private Path brokenCSV;

    @Override
    public void setup() throws Exception {
        super.setup();
        csvPath = path(getMethodName() + ".csv");
        Assume.assumeTrue("S3 Select is not enabled", getFileSystem().hasPathCapability(csvPath, S3_SELECT_CAPABILITY));
        selectConf = new Configuration(false);
        selectConf.setBoolean(SELECT_ERRORS_INCLUDE_SQL, true);
        createStandardCsvFile(getFileSystem(), csvPath, ALL_QUOTES);
        brokenCSV = path("testParseBrokenCSVFile");
        createStandardCsvFile(getFileSystem(), brokenCSV, true, ALL_QUOTES, ALL_ROWS_COUNT, ALL_ROWS_COUNT, ",", "\n", "\"", csv -> csv.line("# comment").row(ALL_QUOTES, "bad", "Tuesday", 0, "entry-bad", "yes", false));
    }

    @Override
    public void teardown() throws Exception {
        describe("teardown");
        try {
            if (csvPath != null) {
                getFileSystem().delete(csvPath, false);
            }
            if (brokenCSV != null) {
                getFileSystem().delete(brokenCSV, false);
            }
        } finally {
            super.teardown();
        }
    }

    private void assertPosition(Seekable stream, long pos) throws IOException {
        assertEquals("Wrong stream position in " + stream, pos, stream.getPos());
    }

    private JobConf createJobConf() {
        JobConf conf = new JobConf(getConfiguration());
        enablePassthroughCodec(conf, ".csv");
        return conf;
    }

    protected List<String> expectRecordsRead(final int expected, final JobConf conf, final String sql) throws Exception {
        return verifySelectionCount(expected, sql, readRecords(conf, sql));
    }

    private List<String> readRecords(JobConf conf, String sql) throws Exception {
        return readRecords(conf, csvPath, sql, createLineRecordReader(), ALL_ROWS_COUNT_WITH_HEADER);
    }

    private List<String> readRecordsV1(JobConf conf, String sql) throws Exception {
        inputMust(conf, SELECT_SQL, sql);
        return super.readRecordsV1(conf, createLineRecordReaderV1(conf, csvPath), new LongWritable(), new Text(), ALL_ROWS_COUNT_WITH_HEADER);
    }

    private List<String> expectSelected(final int expected, final Configuration conf, final String header, final String sql, final Object... args) throws Exception {
        conf.set(CSV_INPUT_HEADER, header);
        return verifySelectionCount(expected, sql(sql, args), selectCsvFile(conf, sql, args));
    }

    private List<String> selectCsvFile(final Configuration conf, final String sql, final Object... args) throws Exception {
        return parseToLines(select(getFileSystem(), csvPath, conf, sql, args));
    }

    protected AWSServiceIOException expectSelectFailure(String expectedErrorCode, String sql) throws Exception {
        selectConf.set(CSV_INPUT_HEADER, CSV_HEADER_OPT_USE);
        return verifyErrorCode(expectedErrorCode, intercept(AWSBadRequestException.class, () -> prepareToPrint(parseToLines(select(getFileSystem(), brokenCSV, selectConf, sql)))));
    }

    @Test
    public void testBackslashExpansion_1() throws Throwable {
        assertEquals("\t\r\n", expandBackslashChars("\t\r\n"));
    }

    @Test
    public void testBackslashExpansion_2() throws Throwable {
        assertEquals("\t", expandBackslashChars("\\t"));
    }

    @Test
    public void testBackslashExpansion_3() throws Throwable {
        assertEquals("\r", expandBackslashChars("\\r"));
    }

    @Test
    public void testBackslashExpansion_4() throws Throwable {
        assertEquals("\r \n", expandBackslashChars("\\r \\n"));
    }

    @Test
    public void testBackslashExpansion_5() throws Throwable {
        assertEquals("\\", expandBackslashChars("\\\\"));
    }
}
