package org.apache.hadoop.fs.s3a.select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.Assume;
import org.junit.Test;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.s3a.S3AFileSystem;
import org.apache.hadoop.fs.s3a.S3ATestUtils;
import org.apache.hadoop.fs.s3a.Statistic;
import org.apache.hadoop.fs.s3a.impl.ChangeDetectionPolicy;
import org.apache.hadoop.fs.s3a.impl.ChangeDetectionPolicy.Source;
import org.apache.hadoop.fs.s3a.s3guard.S3GuardTool;
import org.apache.hadoop.util.ExitUtil;
import org.apache.hadoop.util.OperationDuration;
import org.apache.hadoop.util.ToolRunner;
import static org.apache.hadoop.util.Preconditions.checkNotNull;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.disableFilesystemCaching;
import static org.apache.hadoop.fs.s3a.s3guard.S3GuardToolTestHelper.exec;
import static org.apache.hadoop.fs.s3a.select.ITestS3SelectLandsat.SELECT_NOTHING;
import static org.apache.hadoop.fs.s3a.select.ITestS3SelectLandsat.SELECT_SUNNY_ROWS_NO_LIMIT;
import static org.apache.hadoop.fs.s3a.select.SelectConstants.*;
import static org.apache.hadoop.fs.s3a.select.SelectTool.*;
import static org.apache.hadoop.service.launcher.LauncherExitCodes.EXIT_COMMAND_ARGUMENT_ERROR;
import static org.apache.hadoop.service.launcher.LauncherExitCodes.EXIT_NOT_FOUND;
import static org.apache.hadoop.service.launcher.LauncherExitCodes.EXIT_SERVICE_UNAVAILABLE;
import static org.apache.hadoop.service.launcher.LauncherExitCodes.EXIT_SUCCESS;
import static org.apache.hadoop.service.launcher.LauncherExitCodes.EXIT_USAGE;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

public class ITestS3SelectCLI_Purified extends AbstractS3SelectTest {

    public static final int LINE_COUNT = 100;

    public static final String SELECT_EVERYTHING = "SELECT * FROM S3OBJECT s";

    private SelectTool selectTool;

    private Configuration selectConf;

    public static final String D = "-D";

    private File localFile;

    private String landsatSrc;

    @Override
    public void setup() throws Exception {
        super.setup();
        selectTool = new SelectTool(getConfiguration());
        selectConf = new Configuration(getConfiguration());
        localFile = getTempFilename();
        landsatSrc = getLandsatGZ().toString();
        ChangeDetectionPolicy changeDetectionPolicy = getLandsatFS().getChangeDetectionPolicy();
        Assume.assumeFalse("the standard landsat bucket doesn't have versioning", changeDetectionPolicy.getSource() == Source.VersionId && changeDetectionPolicy.isRequireVersion());
    }

    @Override
    public void teardown() throws Exception {
        super.teardown();
        if (localFile != null) {
            localFile.delete();
        }
    }

    protected static String expectSuccess(String message, S3GuardTool tool, String... args) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        exec(EXIT_SUCCESS, message, tool, buf, args);
        return buf.toString();
    }

    protected int run(Configuration conf, S3GuardTool tool, String... args) throws Exception {
        return ToolRunner.run(conf, tool, args);
    }

    protected void runToFailure(int status, Configuration conf, String message, S3GuardTool tool, String... args) throws Exception {
        final ExitUtil.ExitException ex = intercept(ExitUtil.ExitException.class, message, () -> ToolRunner.run(conf, tool, args));
        if (ex.status != status) {
            throw ex;
        }
    }

    private File getTempFilename() throws IOException {
        File dest = File.createTempFile("landat", ".csv");
        dest.delete();
        return dest;
    }

    private static String o(String in) {
        return "-" + in;
    }

    private static String v(String key, String value) {
        return checkNotNull(key) + "=" + checkNotNull(value);
    }

    @Test
    public void testLandsatToConsole_1() throws Throwable {
        run(selectConf, selectTool, D, v(CSV_OUTPUT_QUOTE_CHARACTER, "'"), D, v(CSV_OUTPUT_QUOTE_FIELDS, CSV_OUTPUT_QUOTE_FIELDS_ALWAYS), "select", o(OPT_HEADER), CSV_HEADER_OPT_USE, o(OPT_COMPRESSION), COMPRESSION_OPT_GZIP, o(OPT_LIMIT), Integer.toString(LINE_COUNT), landsatSrc, SELECT_SUNNY_ROWS_NO_LIMIT);
        assertEquals("Lines read and printed to console", LINE_COUNT, selectTool.getLinesRead());
    }

    @Test
    public void testLandsatToConsole_2() throws Throwable {
        S3ATestUtils.MetricDiff readOps = new S3ATestUtils.MetricDiff(getFileSystem(), Statistic.STREAM_READ_OPERATIONS_INCOMPLETE);
        readOps.assertDiffEquals("Read operations are still considered active", 0);
    }
}
