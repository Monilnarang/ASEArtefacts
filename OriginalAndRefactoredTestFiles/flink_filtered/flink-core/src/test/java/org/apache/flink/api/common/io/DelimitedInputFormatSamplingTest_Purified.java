package org.apache.flink.api.common.io;

import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.OptimizerOptions;
import org.apache.flink.testutils.TestConfigUtils;
import org.apache.flink.testutils.TestFileSystem;
import org.apache.flink.testutils.TestFileUtils;
import org.apache.flink.testutils.junit.utils.TempDirUtils;
import org.apache.flink.types.IntValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

class DelimitedInputFormatSamplingTest_Purified {

    private static final String TEST_DATA1 = "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n" + "123456789\n";

    private static final String TEST_DATA2 = "12345\n" + "12345\n" + "12345\n" + "12345\n" + "12345\n" + "12345\n" + "12345\n" + "12345\n" + "12345\n" + "12345\n";

    private static final int TEST_DATA_1_LINES = TEST_DATA1.split("\n").length;

    private static final int TEST_DATA_1_LINEWIDTH = TEST_DATA1.split("\n")[0].length();

    private static final int TEST_DATA_2_LINEWIDTH = TEST_DATA2.split("\n")[0].length();

    private static final int TOTAL_SIZE = TEST_DATA1.length() + TEST_DATA2.length();

    private static final int DEFAULT_NUM_SAMPLES = 4;

    private static Configuration CONFIG;

    @TempDir
    private static Path tempDir;

    private static File testTempFolder;

    @BeforeAll
    static void initialize() {
        try {
            testTempFolder = TempDirUtils.newFolder(tempDir);
            CONFIG = TestConfigUtils.loadGlobalConf(new String[] { OptimizerOptions.DELIMITED_FORMAT_MIN_LINE_SAMPLES.key(), OptimizerOptions.DELIMITED_FORMAT_MAX_LINE_SAMPLES.key() }, new String[] { "4", "4" }, testTempFolder);
        } catch (Throwable t) {
            fail("Could not load the global configuration.");
        }
    }

    private static final class TestDelimitedInputFormat extends DelimitedInputFormat<IntValue> {

        private static final long serialVersionUID = 1L;

        TestDelimitedInputFormat(Configuration configuration) {
            super(null, configuration);
        }

        @Override
        public IntValue readRecord(IntValue reuse, byte[] bytes, int offset, int numBytes) {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    void testCachedStatistics_1_testMerged_1() throws IOException {
        isEqualTo(DEFAULT_NUM_SAMPLES);
        TestFileSystem.resetStreamOpenCounter();
    }

    @Test
    void testCachedStatistics_3() throws IOException {
        final String tempFile = TestFileUtils.createTempFile(TEST_DATA1);
        final Configuration conf = new Configuration();
        final TestDelimitedInputFormat format = new TestDelimitedInputFormat(CONFIG);
        format.setFilePath("test://" + tempFile);
        format.configure(conf);
        BaseStatistics stats = format.getStatistics(null);
        final TestDelimitedInputFormat format2 = new TestDelimitedInputFormat(CONFIG);
        format2.setFilePath("test://" + tempFile);
        format2.configure(conf);
        BaseStatistics stats2 = format2.getStatistics(stats);
        isSameAs(stats);
    }
}
