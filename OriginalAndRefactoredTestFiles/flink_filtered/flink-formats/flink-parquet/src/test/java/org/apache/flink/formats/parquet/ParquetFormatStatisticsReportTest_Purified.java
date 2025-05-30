package org.apache.flink.formats.parquet;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.table.plan.stats.ColumnStats;
import org.apache.flink.table.plan.stats.TableStats;
import org.apache.flink.table.planner.utils.StatisticsReportTestBase;
import org.apache.flink.table.types.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ParquetFormatStatisticsReportTest_Purified extends StatisticsReportTestBase {

    private static ParquetFileFormatFactory.ParquetBulkDecodingFormat parquetBulkDecodingFormat;

    @BeforeEach
    public void setup(@TempDir File file) throws Exception {
        super.setup(file);
        createFileSystemSource();
        Configuration configuration = new Configuration();
        parquetBulkDecodingFormat = new ParquetFileFormatFactory.ParquetBulkDecodingFormat(configuration);
    }

    @Override
    protected String[] properties() {
        List<String> ret = new ArrayList<>();
        ret.add("'format'='parquet'");
        ret.add("'parquet.utc-timezone'='true'");
        ret.add("'parquet.compression'='gzip'");
        return ret.toArray(new String[0]);
    }

    protected static void assertParquetFormatTableStatsEquals(TableStats tableStats, int expectedRowCount, long nullCount) {
        Map<String, ColumnStats> expectedColumnStatsMap = new HashMap<>();
        expectedColumnStatsMap.put("f_boolean", new ColumnStats.Builder().setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_tinyint", new ColumnStats.Builder().setMax(3).setMin(1).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_smallint", new ColumnStats.Builder().setMax(128).setMin(100).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_int", new ColumnStats.Builder().setMax(45536).setMin(31000).setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_bigint", new ColumnStats.Builder().setMax(1238123899121L).setMin(1238123899000L).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_float", new ColumnStats.Builder().setMax(33.333F).setMin(33.311F).setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_double", new ColumnStats.Builder().setMax(10.1D).setMin(1.1D).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_string", new ColumnStats.Builder().setMax("def").setMin("abcd").setNullCount(0L).build());
        expectedColumnStatsMap.put("f_decimal5", new ColumnStats.Builder().setMax(new BigDecimal("223.45")).setMin(new BigDecimal("123.45")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_decimal14", new ColumnStats.Builder().setMax(new BigDecimal("123333333355.33")).setMin(new BigDecimal("123333333333.33")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_decimal38", new ColumnStats.Builder().setMax(new BigDecimal("123433343334333433343334333433343334.34")).setMin(new BigDecimal("123433343334333433343334333433343334.33")).setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_date", new ColumnStats.Builder().setMax(Date.valueOf("1990-10-16")).setMin(Date.valueOf("1990-10-14")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp3", new ColumnStats.Builder().setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp9", new ColumnStats.Builder().setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp_wtz", new ColumnStats.Builder().setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp_ltz", new ColumnStats.Builder().setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_binary", new ColumnStats.Builder().setNullCount(0L).build());
        expectedColumnStatsMap.put("f_varbinary", new ColumnStats.Builder().setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_time", new ColumnStats.Builder().setMax(Time.valueOf("12:12:45")).setMin(Time.valueOf("12:12:43")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_row", null);
        expectedColumnStatsMap.put("f_array", null);
        expectedColumnStatsMap.put("f_map", null);
        assertThat(tableStats).isEqualTo(new TableStats(expectedRowCount, expectedColumnStatsMap));
    }

    @Test
    public void testParquetFormatStatsReportWithSingleFile_1() throws Exception {
        assertThat(folder.listFiles()).hasSize(1);
    }

    @Test
    public void testParquetFormatStatsReportWithSingleFile_2() throws Exception {
        DataType dataType = tEnv.from("sourceTable").getResolvedSchema().toPhysicalRowDataType();
        tEnv.fromValues(dataType, getData()).executeInsert("sourceTable").await();
        File[] files = folder.listFiles();
        assert files != null;
        TableStats tableStats = parquetBulkDecodingFormat.reportStatistics(Collections.singletonList(new Path(files[0].toURI().toString())), dataType);
        assertParquetFormatTableStatsEquals(tableStats, 3, 1L);
    }
}
