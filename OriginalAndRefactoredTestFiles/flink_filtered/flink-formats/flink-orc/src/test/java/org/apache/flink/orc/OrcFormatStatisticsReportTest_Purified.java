package org.apache.flink.orc;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.table.plan.stats.ColumnStats;
import org.apache.flink.table.plan.stats.TableStats;
import org.apache.flink.table.planner.utils.StatisticsReportTestBase;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.utils.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class OrcFormatStatisticsReportTest_Purified extends StatisticsReportTestBase {

    private static OrcFileFormatFactory.OrcBulkDecodingFormat orcBulkDecodingFormat;

    @BeforeEach
    public void setup(@TempDir File file) throws Exception {
        super.setup(file);
        createFileSystemSource();
        Configuration configuration = new Configuration();
        orcBulkDecodingFormat = new OrcFileFormatFactory.OrcBulkDecodingFormat(configuration);
    }

    @Override
    protected String[] properties() {
        List<String> ret = new ArrayList<>();
        ret.add("'format'='orc'");
        ret.add("'orc.compress'='snappy'");
        return ret.toArray(new String[0]);
    }

    @Override
    protected Map<String, String> ddlTypesMap() {
        Map<String, String> ddlTypes = super.ddlTypesMap();
        ddlTypes.remove("timestamp with local time zone");
        ddlTypes.remove("binary(1)");
        ddlTypes.remove("varbinary(1)");
        ddlTypes.remove("time");
        return ddlTypes;
    }

    @Override
    protected Map<String, List<Object>> getDataMap() {
        Map<String, List<Object>> dataMap = super.getDataMap();
        dataMap.remove("timestamp with local time zone");
        dataMap.remove("binary(1)");
        dataMap.remove("varbinary(1)");
        dataMap.remove("time");
        return dataMap;
    }

    protected static void assertOrcFormatTableStatsEquals(TableStats tableStats, int expectedRowCount, long nullCount) {
        Map<String, ColumnStats> expectedColumnStatsMap = new HashMap<>();
        expectedColumnStatsMap.put("f_boolean", new ColumnStats.Builder().setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_tinyint", new ColumnStats.Builder().setMax(3L).setMin(1L).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_smallint", new ColumnStats.Builder().setMax(128L).setMin(100L).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_int", new ColumnStats.Builder().setMax(45536L).setMin(31000L).setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_bigint", new ColumnStats.Builder().setMax(1238123899121L).setMin(1238123899000L).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_float", new ColumnStats.Builder().setMax(33.33300018310547D).setMin(33.31100082397461D).setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_double", new ColumnStats.Builder().setMax(10.1D).setMin(1.1D).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_string", new ColumnStats.Builder().setMax("def").setMin("abcd").setNullCount(0L).build());
        expectedColumnStatsMap.put("f_decimal5", new ColumnStats.Builder().setMax(new BigDecimal("223.45")).setMin(new BigDecimal("123.45")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_decimal14", new ColumnStats.Builder().setMax(new BigDecimal("123333333355.33")).setMin(new BigDecimal("123333333333.33")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_decimal38", new ColumnStats.Builder().setMax(new BigDecimal("123433343334333433343334333433343334.34")).setMin(new BigDecimal("123433343334333433343334333433343334.33")).setNullCount(nullCount).build());
        expectedColumnStatsMap.put("f_date", new ColumnStats.Builder().setMax(Date.valueOf("1990-10-16")).setMin(Date.valueOf("1990-10-14")).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp3", new ColumnStats.Builder().setMax(DateTimeUtils.parseTimestampData("1990-10-16 12:12:43.123", 3).toTimestamp()).setMin(DateTimeUtils.parseTimestampData("1990-10-14 12:12:43.123", 3).toTimestamp()).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp9", new ColumnStats.Builder().setMax(DateTimeUtils.parseTimestampData("1990-10-16 12:12:43.123", 3).toTimestamp()).setMin(DateTimeUtils.parseTimestampData("1990-10-14 12:12:43.123", 3).toTimestamp()).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_timestamp_wtz", new ColumnStats.Builder().setMax(DateTimeUtils.parseTimestampData("1990-10-16 12:12:43.123", 3).toTimestamp()).setMin(DateTimeUtils.parseTimestampData("1990-10-14 12:12:43.123", 3).toTimestamp()).setNullCount(0L).build());
        expectedColumnStatsMap.put("f_row", null);
        expectedColumnStatsMap.put("f_array", null);
        expectedColumnStatsMap.put("f_map", null);
        assertThat(tableStats).isEqualTo(new TableStats(expectedRowCount, expectedColumnStatsMap));
    }

    @Test
    public void testOrcFormatStatsReportWithSingleFile_1() throws Exception {
        assertThat(folder.listFiles()).hasSize(1);
    }

    @Test
    public void testOrcFormatStatsReportWithSingleFile_2_testMerged_2() throws Exception {
        DataType dataType = tEnv.from("sourceTable").getResolvedSchema().toPhysicalRowDataType();
        tEnv.fromValues(dataType, getData()).executeInsert("sourceTable").await();
        File[] files = folder.listFiles();
        assertThat(files).isNotNull();
        TableStats tableStats = orcBulkDecodingFormat.reportStatistics(Collections.singletonList(new Path(files[0].toURI().toString())), dataType);
        assertOrcFormatTableStatsEquals(tableStats, 3, 1L);
    }
}
