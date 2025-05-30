package org.apache.flink.formats.csv;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.table.plan.stats.TableStats;
import org.apache.flink.table.planner.utils.StatisticsReportTestBase;
import org.apache.flink.table.types.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class CsvFormatStatisticsReportTest_Purified extends StatisticsReportTestBase {

    private static CsvFileFormatFactory.CsvBulkDecodingFormat csvBulkDecodingFormat;

    @Override
    @BeforeEach
    public void setup(@TempDir File file) throws Exception {
        super.setup(file);
        createFileSystemSource();
        Configuration configuration = new Configuration();
        csvBulkDecodingFormat = new CsvFileFormatFactory.CsvBulkDecodingFormat(configuration);
    }

    @Override
    protected String[] properties() {
        List<String> ret = new ArrayList<>();
        ret.add("'format' = 'csv'");
        return ret.toArray(new String[0]);
    }

    @Override
    protected Map<String, String> ddlTypesMap() {
        Map<String, String> ddlTypes = super.ddlTypesMap();
        ddlTypes.remove("map<string, int>");
        return ddlTypes;
    }

    @Override
    protected Map<String, List<Object>> getDataMap() {
        Map<String, List<Object>> dataMap = super.getDataMap();
        dataMap.remove("map<string, int>");
        return dataMap;
    }

    private static Path createTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("test_contents", "tmp");
        tempFile.deleteOnExit();
        OutputStreamWriter wrt = new OutputStreamWriter(Files.newOutputStream(tempFile.toPath()), StandardCharsets.UTF_8);
        wrt.write(content);
        wrt.close();
        return new Path(tempFile.toURI().toString());
    }

    @Test
    void testCsvFormatStatsReportWithSingleFile_1() throws Exception {
        assertThat(folder.listFiles()).hasSize(1);
    }

    @Test
    void testCsvFormatStatsReportWithSingleFile_2_testMerged_2() throws Exception {
        File[] files = folder.listFiles();
        assertThat(files).isNotNull();
        TableStats tableStats = csvBulkDecodingFormat.reportStatistics(Collections.singletonList(new Path(files[0].toURI().toString())), null);
        assertThat(tableStats).isEqualTo(new TableStats(3));
    }
}
