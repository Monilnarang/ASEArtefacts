package org.apache.flink.table.planner.plan.stream.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.config.TableConfigOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.apache.flink.table.api.config.TableConfigOptions.ColumnExpansionStrategy.EXCLUDE_ALIASED_VIRTUAL_METADATA_COLUMNS;
import static org.apache.flink.table.api.config.TableConfigOptions.ColumnExpansionStrategy.EXCLUDE_DEFAULT_VIRTUAL_METADATA_COLUMNS;
import static org.apache.flink.table.api.config.TableConfigOptions.TABLE_COLUMN_EXPANSION_STRATEGY;
import static org.assertj.core.api.Assertions.assertThat;

class ColumnExpansionTest_Purified {

    private TableEnvironment tableEnv;

    @BeforeEach
    void before() {
        tableEnv = TableEnvironment.create(EnvironmentSettings.inStreamingMode());
        tableEnv.executeSql("CREATE TABLE t1 (\n" + "  t1_i INT,\n" + "  t1_s STRING,\n" + "  t1_m_virtual INT METADATA VIRTUAL,\n" + "  t1_m_aliased_virtual STRING METADATA FROM 'k1' VIRTUAL,\n" + "  t1_m_default INT METADATA,\n" + "  t1_m_aliased STRING METADATA FROM 'k2'\n" + ") WITH (\n" + " 'connector' = 'values',\n" + " 'readable-metadata' = 't1_m_virtual:INT,k1:STRING,t1_m_default:INT,k2:STRING'\n" + ")");
        tableEnv.executeSql("CREATE TABLE t2 (\n" + "  t2_i INT,\n" + "  t2_s STRING,\n" + "  t2_m_virtual INT METADATA VIRTUAL,\n" + "  t2_m_aliased_virtual STRING METADATA FROM 'k1' VIRTUAL,\n" + "  t2_m_default INT METADATA,\n" + "  t2_m_aliased STRING METADATA FROM 'k2'\n" + ") WITH (\n" + " 'connector' = 'values',\n" + " 'readable-metadata' = 't2_m_virtual:INT,k1:STRING,t2_m_default:INT,k2:STRING'\n" + ")");
        tableEnv.executeSql("CREATE TABLE t3 (\n" + "  t3_s STRING,\n" + "  t3_i INT,\n" + "  t3_m_virtual TIMESTAMP_LTZ(3) METADATA VIRTUAL,\n" + "  WATERMARK FOR t3_m_virtual AS t3_m_virtual - INTERVAL '1' SECOND\n" + ") WITH (\n" + " 'connector' = 'values',\n" + " 'readable-metadata' = 't3_m_virtual:TIMESTAMP_LTZ(3)'\n" + ")");
        tableEnv.getConfig().set(TABLE_COLUMN_EXPANSION_STRATEGY, Collections.emptyList());
    }

    private void assertColumnNames(String sql, String... columnNames) {
        assertThat(tableEnv.sqlQuery(sql).getResolvedSchema().getColumnNames()).containsExactly(columnNames);
    }

    @Test
    void testExcludeDefaultVirtualMetadataColumns_1() {
        assertColumnNames("SELECT * FROM t1", "t1_i", "t1_s", "t1_m_aliased_virtual", "t1_m_default", "t1_m_aliased");
    }

    @Test
    void testExcludeDefaultVirtualMetadataColumns_2() {
        assertColumnNames("SELECT t1_m_virtual, * FROM t1", "t1_m_virtual", "t1_i", "t1_s", "t1_m_aliased_virtual", "t1_m_default", "t1_m_aliased");
    }

    @Test
    void testExcludeDefaultVirtualMetadataColumns_3() {
        assertColumnNames("SELECT * FROM t1, t2", "t1_i", "t1_s", "t1_m_aliased_virtual", "t1_m_default", "t1_m_aliased", "t2_i", "t2_s", "t2_m_aliased_virtual", "t2_m_default", "t2_m_aliased");
    }

    @Test
    void testExcludeDefaultVirtualMetadataColumns_4() {
        assertColumnNames("SELECT t1.*, t2.* FROM t1, t2", "t1_i", "t1_s", "t1_m_aliased_virtual", "t1_m_default", "t1_m_aliased", "t2_i", "t2_s", "t2_m_aliased_virtual", "t2_m_default", "t2_m_aliased");
    }

    @Test
    void testExcludeDefaultVirtualMetadataColumns_5() {
        assertColumnNames("SELECT * FROM (SELECT t1_m_virtual, t2_m_virtual, * FROM t1, t2)", "t1_m_virtual", "t2_m_virtual", "t1_i", "t1_s", "t1_m_aliased_virtual", "t1_m_default", "t1_m_aliased", "t2_i", "t2_s", "t2_m_aliased_virtual", "t2_m_default", "t2_m_aliased");
    }

    @Test
    void testExcludeAliasedVirtualMetadataColumns_1() {
        assertColumnNames("SELECT * FROM t1", "t1_i", "t1_s", "t1_m_virtual", "t1_m_default", "t1_m_aliased");
    }

    @Test
    void testExcludeAliasedVirtualMetadataColumns_2() {
        assertColumnNames("SELECT t1_m_aliased_virtual, * FROM t1", "t1_m_aliased_virtual", "t1_i", "t1_s", "t1_m_virtual", "t1_m_default", "t1_m_aliased");
    }

    @Test
    void testExcludeAliasedVirtualMetadataColumns_3() {
        assertColumnNames("SELECT * FROM t1, t2", "t1_i", "t1_s", "t1_m_virtual", "t1_m_default", "t1_m_aliased", "t2_i", "t2_s", "t2_m_virtual", "t2_m_default", "t2_m_aliased");
    }

    @Test
    void testExcludeAliasedVirtualMetadataColumns_4() {
        assertColumnNames("SELECT t1.*, t2.* FROM t1, t2", "t1_i", "t1_s", "t1_m_virtual", "t1_m_default", "t1_m_aliased", "t2_i", "t2_s", "t2_m_virtual", "t2_m_default", "t2_m_aliased");
    }

    @Test
    void testExcludeAliasedVirtualMetadataColumns_5() {
        assertColumnNames("SELECT * FROM (SELECT t1_m_aliased_virtual, t2_m_aliased_virtual, * FROM t1, t2)", "t1_m_aliased_virtual", "t2_m_aliased_virtual", "t1_i", "t1_s", "t1_m_virtual", "t1_m_default", "t1_m_aliased", "t2_i", "t2_s", "t2_m_virtual", "t2_m_default", "t2_m_aliased");
    }

    @Test
    void testExplicitTableWithinTableFunction_1() {
        assertColumnNames("SELECT * FROM TABLE(TUMBLE(TABLE t3, DESCRIPTOR(t3_m_virtual), INTERVAL '1' MINUTE))", "t3_s", "t3_i", "t3_m_virtual", "window_start", "window_end", "window_time");
    }

    @Test
    void testExplicitTableWithinTableFunction_2() {
        assertColumnNames("SELECT t3_s, SUM(t3_i) AS agg " + "FROM TABLE(TUMBLE(TABLE t3, DESCRIPTOR(t3_m_virtual), INTERVAL '1' MINUTE)) " + "GROUP BY t3_s, window_start, window_end", "t3_s", "agg");
    }

    @Test
    void testSetSemanticsTableWithinTableFunction_1() {
        assertColumnNames("SELECT * FROM TABLE(" + "SESSION(TABLE t3 PARTITION BY t3_s, DESCRIPTOR(t3_m_virtual), INTERVAL '1' MINUTE))", "t3_s", "t3_i", "t3_m_virtual", "window_start", "window_end", "window_time");
    }

    @Test
    void testSetSemanticsTableWithinTableFunction_2() {
        assertColumnNames("SELECT t3_s, SUM(t3_i) AS agg " + "FROM TABLE(SESSION(TABLE t3 PARTITION BY t3_s, DESCRIPTOR(t3_m_virtual), INTERVAL '1' MINUTE))" + "GROUP BY t3_s, window_start, window_end", "t3_s", "agg");
    }

    @Test
    void testExplicitTableWithinTableFunctionWithNamedArgs_1() {
        assertColumnNames("SELECT * FROM TABLE(" + "TUMBLE(DATA => TABLE t3, TIMECOL => DESCRIPTOR(t3_m_virtual), SIZE => INTERVAL '1' MINUTE))", "t3_s", "t3_i", "t3_m_virtual", "window_start", "window_end", "window_time");
    }

    @Test
    void testExplicitTableWithinTableFunctionWithNamedArgs_2() {
        assertColumnNames("SELECT t3_s, SUM(t3_i) AS agg " + "FROM TABLE(TUMBLE(DATA => TABLE t3, TIMECOL => DESCRIPTOR(t3_m_virtual), SIZE => INTERVAL '1' MINUTE)) " + "GROUP BY t3_s, window_start, window_end", "t3_s", "agg");
    }

    @Test
    void testSetSemanticsTableFunctionWithNamedArgs_1() {
        assertColumnNames("SELECT * FROM TABLE(" + "SESSION(DATA => TABLE t3 PARTITION BY t3_s, TIMECOL => DESCRIPTOR(t3_m_virtual), GAP => INTERVAL '1' MINUTE))", "t3_s", "t3_i", "t3_m_virtual", "window_start", "window_end", "window_time");
    }

    @Test
    void testSetSemanticsTableFunctionWithNamedArgs_2() {
        assertColumnNames("SELECT t3_s, SUM(t3_i) AS agg " + "FROM TABLE(SESSION(DATA => TABLE t3 PARTITION BY t3_s, TIMECOL => DESCRIPTOR(t3_m_virtual), GAP => INTERVAL '1' MINUTE))" + "GROUP BY t3_s, window_start, window_end", "t3_s", "agg");
    }
}
