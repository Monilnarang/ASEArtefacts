package org.apache.flink.table.planner.operations;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.catalog.CatalogMaterializedTable;
import org.apache.flink.table.catalog.CatalogTable;
import org.apache.flink.table.catalog.Column;
import org.apache.flink.table.catalog.IntervalFreshness;
import org.apache.flink.table.catalog.ObjectIdentifier;
import org.apache.flink.table.catalog.ObjectPath;
import org.apache.flink.table.catalog.ResolvedCatalogMaterializedTable;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.catalog.TableChange;
import org.apache.flink.table.catalog.UnresolvedIdentifier;
import org.apache.flink.table.catalog.exceptions.DatabaseNotExistException;
import org.apache.flink.table.catalog.exceptions.TableAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.TableNotExistException;
import org.apache.flink.table.operations.Operation;
import org.apache.flink.table.operations.materializedtable.AlterMaterializedTableAsQueryOperation;
import org.apache.flink.table.operations.materializedtable.AlterMaterializedTableRefreshOperation;
import org.apache.flink.table.operations.materializedtable.AlterMaterializedTableResumeOperation;
import org.apache.flink.table.operations.materializedtable.AlterMaterializedTableSuspendOperation;
import org.apache.flink.table.operations.materializedtable.CreateMaterializedTableOperation;
import org.apache.flink.table.operations.materializedtable.DropMaterializedTableOperation;
import org.apache.flink.table.planner.utils.TableFunc0;
import org.apache.flink.shaded.guava33.com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SqlMaterializedTableNodeToOperationConverterTest_Purified extends SqlNodeToOperationConversionTestBase {

    @BeforeEach
    public void before() throws TableAlreadyExistException, DatabaseNotExistException {
        super.before();
        final ObjectPath path3 = new ObjectPath(catalogManager.getCurrentDatabase(), "t3");
        final Schema tableSchema = Schema.newBuilder().fromResolvedSchema(ResolvedSchema.of(Column.physical("a", DataTypes.BIGINT().notNull()), Column.physical("b", DataTypes.VARCHAR(Integer.MAX_VALUE)), Column.physical("c", DataTypes.INT()), Column.physical("d", DataTypes.VARCHAR(Integer.MAX_VALUE)))).build();
        Map<String, String> options = new HashMap<>();
        options.put("connector", "COLLECTION");
        final CatalogTable catalogTable = CatalogTable.newBuilder().schema(tableSchema).comment("").partitionKeys(Arrays.asList("b", "c")).options(options).build();
        catalog.createTable(path3, catalogTable, true);
        final String sql = "CREATE MATERIALIZED TABLE base_mtbl (\n" + "   CONSTRAINT ct1 PRIMARY KEY(a) NOT ENFORCED" + ")\n" + "COMMENT 'materialized table comment'\n" + "PARTITIONED BY (a, d)\n" + "WITH (\n" + "  'connector' = 'filesystem', \n" + "  'format' = 'json'\n" + ")\n" + "FRESHNESS = INTERVAL '30' SECOND\n" + "REFRESH_MODE = FULL\n" + "AS SELECT * FROM t1";
        final ObjectPath path4 = new ObjectPath(catalogManager.getCurrentDatabase(), "base_mtbl");
        CreateMaterializedTableOperation operation = (CreateMaterializedTableOperation) parse(sql);
        catalog.createTable(path4, operation.getCatalogMaterializedTable(), true);
    }

    @Test
    void testContinuousRefreshMode_1_testMerged_1() {
        final String sql = "CREATE MATERIALIZED TABLE mtbl1\n" + "FRESHNESS = INTERVAL '30' SECOND\n" + "AS SELECT * FROM t1";
        Operation operation = parse(sql);
        assertThat(operation).isInstanceOf(CreateMaterializedTableOperation.class);
        CreateMaterializedTableOperation op = (CreateMaterializedTableOperation) operation;
        CatalogMaterializedTable materializedTable = op.getCatalogMaterializedTable();
        assertThat(materializedTable).isInstanceOf(ResolvedCatalogMaterializedTable.class);
        assertThat(materializedTable.getLogicalRefreshMode()).isEqualTo(CatalogMaterializedTable.LogicalRefreshMode.AUTOMATIC);
        assertThat(materializedTable.getRefreshMode()).isEqualTo(CatalogMaterializedTable.RefreshMode.CONTINUOUS);
    }

    @Test
    void testContinuousRefreshMode_5_testMerged_2() {
        final String sql2 = "CREATE MATERIALIZED TABLE mtbl1\n" + "FRESHNESS = INTERVAL '30' DAY\n" + "REFRESH_MODE = CONTINUOUS\n" + "AS SELECT * FROM t1";
        Operation operation2 = parse(sql2);
        assertThat(operation2).isInstanceOf(CreateMaterializedTableOperation.class);
        CreateMaterializedTableOperation op2 = (CreateMaterializedTableOperation) operation2;
        CatalogMaterializedTable materializedTable2 = op2.getCatalogMaterializedTable();
        assertThat(materializedTable2).isInstanceOf(ResolvedCatalogMaterializedTable.class);
        assertThat(materializedTable2.getLogicalRefreshMode()).isEqualTo(CatalogMaterializedTable.LogicalRefreshMode.CONTINUOUS);
        assertThat(materializedTable2.getRefreshMode()).isEqualTo(CatalogMaterializedTable.RefreshMode.CONTINUOUS);
    }

    @Test
    void testAlterMaterializedTableResume_1_testMerged_1() {
        final String sql1 = "ALTER MATERIALIZED TABLE mtbl1 RESUME";
        Operation operation = parse(sql1);
        assertThat(operation).isInstanceOf(AlterMaterializedTableResumeOperation.class);
        assertThat(operation.asSummaryString()).isEqualTo("ALTER MATERIALIZED TABLE builtin.default.mtbl1 RESUME");
    }

    @Test
    void testAlterMaterializedTableResume_3_testMerged_2() {
        final String sql2 = "ALTER MATERIALIZED TABLE mtbl1 RESUME WITH ('k1' = 'v1')";
        Operation operation2 = parse(sql2);
        assertThat(operation2).isInstanceOf(AlterMaterializedTableResumeOperation.class);
        assertThat(((AlterMaterializedTableResumeOperation) operation2).getDynamicOptions()).containsEntry("k1", "v1");
        assertThat(operation2.asSummaryString()).isEqualTo("ALTER MATERIALIZED TABLE builtin.default.mtbl1 RESUME WITH (k1: [v1])");
    }

    @Test
    void testDropMaterializedTable_1_testMerged_1() {
        final String sql = "DROP MATERIALIZED TABLE mtbl1";
        Operation operation = parse(sql);
        assertThat(operation).isInstanceOf(DropMaterializedTableOperation.class);
        assertThat(((DropMaterializedTableOperation) operation).isIfExists()).isFalse();
        assertThat(operation.asSummaryString()).isEqualTo("DROP MATERIALIZED TABLE: (identifier: [`builtin`.`default`.`mtbl1`], IfExists: [false])");
    }

    @Test
    void testDropMaterializedTable_4_testMerged_2() {
        final String sql2 = "DROP MATERIALIZED TABLE IF EXISTS mtbl1";
        Operation operation2 = parse(sql2);
        assertThat(operation2).isInstanceOf(DropMaterializedTableOperation.class);
        assertThat(((DropMaterializedTableOperation) operation2).isIfExists()).isTrue();
        assertThat(operation2.asSummaryString()).isEqualTo("DROP MATERIALIZED TABLE: (identifier: [`builtin`.`default`.`mtbl1`], IfExists: [true])");
    }
}
