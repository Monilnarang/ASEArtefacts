package org.apache.druid.indexer.path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.druid.indexer.HadoopDruidIndexerConfig;
import org.apache.druid.indexer.HadoopIOConfig;
import org.apache.druid.indexer.HadoopIngestionSpec;
import org.apache.druid.indexer.HadoopTuningConfig;
import org.apache.druid.indexer.granularity.UniformGranularitySpec;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.Granularity;
import org.apache.druid.java.util.common.granularity.PeriodGranularity;
import org.apache.druid.segment.indexing.DataSchema;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.security.UserGroupInformation;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.IOException;
import java.util.Arrays;

public class GranularityPathSpecTest_Purified {

    private static final HadoopTuningConfig DEFAULT_TUNING_CONFIG = new HadoopTuningConfig(null, null, null, null, null, null, null, null, null, false, false, false, false, null, false, false, null, null, false, false, null, null, null, null, null, 1);

    private GranularityPathSpec granularityPathSpec;

    private final String TEST_STRING_PATH = "TEST";

    private final String TEST_STRING_PATTERN = "*.TEST";

    private final String TEST_STRING_FORMAT = "F_TEST";

    private final ObjectMapper jsonMapper = new DefaultObjectMapper();

    @Rule
    public final TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        granularityPathSpec = new GranularityPathSpec();
    }

    @After
    public void tearDown() {
        granularityPathSpec = null;
    }

    private void createFile(TemporaryFolder folder, String... files) throws IOException {
        for (String file : files) {
            String[] split = file.split("/");
            Assert.assertTrue(split.length > 1);
            folder.newFolder(Arrays.copyOfRange(split, 0, split.length - 1));
            folder.newFile(file);
        }
    }

    @Test
    public void testBackwardCompatiblePeriodSegmentGranularitySerialization_1() throws JsonProcessingException {
        final PeriodGranularity pt2S = new PeriodGranularity(new Period("PT2S"), null, DateTimeZone.UTC);
        Assert.assertNotEquals("\"SECOND\"", jsonMapper.writeValueAsString(pt2S));
    }

    @Test
    public void testBackwardCompatiblePeriodSegmentGranularitySerialization_2() throws JsonProcessingException {
        final Granularity pt1S = Granularities.SECOND;
        Assert.assertEquals("\"SECOND\"", jsonMapper.writeValueAsString(pt1S));
    }
}
