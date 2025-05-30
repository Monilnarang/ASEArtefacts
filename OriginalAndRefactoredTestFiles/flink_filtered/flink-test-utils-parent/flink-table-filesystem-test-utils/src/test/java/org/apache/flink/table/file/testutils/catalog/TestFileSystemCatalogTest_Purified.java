package org.apache.flink.table.file.testutils.catalog;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.catalog.CatalogBaseTable;
import org.apache.flink.table.catalog.CatalogDatabase;
import org.apache.flink.table.catalog.CatalogDatabaseImpl;
import org.apache.flink.table.catalog.CatalogMaterializedTable;
import org.apache.flink.table.catalog.CatalogTable;
import org.apache.flink.table.catalog.Column;
import org.apache.flink.table.catalog.IntervalFreshness;
import org.apache.flink.table.catalog.ObjectPath;
import org.apache.flink.table.catalog.ResolvedCatalogMaterializedTable;
import org.apache.flink.table.catalog.ResolvedCatalogTable;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.catalog.TestSchemaResolver;
import org.apache.flink.table.catalog.UniqueConstraint;
import org.apache.flink.table.catalog.exceptions.CatalogException;
import org.apache.flink.table.catalog.exceptions.DatabaseAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotExistException;
import org.apache.flink.table.catalog.exceptions.TableAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.TableNotExistException;
import org.apache.flink.table.refresh.RefreshHandler;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.apache.flink.connector.file.table.FileSystemConnectorOptions.PATH;
import static org.apache.flink.table.file.testutils.catalog.TestFileSystemCatalog.DATA_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFileSystemCatalogTest_Purified extends TestFileSystemCatalogTestBase {

    private static final List<Column> CREATE_COLUMNS = Arrays.asList(Column.physical("id", DataTypes.BIGINT()), Column.physical("name", DataTypes.VARCHAR(20)), Column.physical("age", DataTypes.INT()), Column.physical("tss", DataTypes.TIMESTAMP(3)), Column.physical("partition", DataTypes.VARCHAR(10)));

    private static final UniqueConstraint CONSTRAINTS = UniqueConstraint.primaryKey("primary_constraint", Collections.singletonList("id"));

    private static final List<String> PARTITION_KEYS = Collections.singletonList("partition");

    private static final ResolvedSchema CREATE_RESOLVED_SCHEMA = new ResolvedSchema(CREATE_COLUMNS, Collections.emptyList(), CONSTRAINTS);

    private static final Schema CREATE_SCHEMA = Schema.newBuilder().fromResolvedSchema(CREATE_RESOLVED_SCHEMA).build();

    private static final Map<String, String> EXPECTED_OPTIONS = new HashMap<>();

    static {
        EXPECTED_OPTIONS.put("source.monitor-interval", "5S");
        EXPECTED_OPTIONS.put("auto-compaction", "true");
    }

    private static final ResolvedCatalogTable EXPECTED_CATALOG_TABLE = new ResolvedCatalogTable(CatalogTable.newBuilder().schema(CREATE_SCHEMA).comment("test table").partitionKeys(PARTITION_KEYS).options(EXPECTED_OPTIONS).build(), CREATE_RESOLVED_SCHEMA);

    private static final String DEFINITION_QUERY = "SELECT id, region, county FROM T";

    private static final IntervalFreshness FRESHNESS = IntervalFreshness.ofMinute("3");

    private static final ResolvedCatalogMaterializedTable EXPECTED_CATALOG_MATERIALIZED_TABLE = new ResolvedCatalogMaterializedTable(CatalogMaterializedTable.newBuilder().schema(CREATE_SCHEMA).comment("test materialized table").partitionKeys(PARTITION_KEYS).options(EXPECTED_OPTIONS).definitionQuery(DEFINITION_QUERY).freshness(FRESHNESS).logicalRefreshMode(CatalogMaterializedTable.LogicalRefreshMode.AUTOMATIC).refreshMode(CatalogMaterializedTable.RefreshMode.CONTINUOUS).refreshStatus(CatalogMaterializedTable.RefreshStatus.INITIALIZING).build(), CREATE_RESOLVED_SCHEMA);

    private static final TestRefreshHandler REFRESH_HANDLER = new TestRefreshHandler("jobID: xxx, clusterId: yyy");

    private static class TestRefreshHandler implements RefreshHandler {

        private final String handlerString;

        public TestRefreshHandler(String handlerString) {
            this.handlerString = handlerString;
        }

        @Override
        public String asSummaryString() {
            return "test refresh handler";
        }

        public byte[] toBytes() {
            return handlerString.getBytes();
        }
    }

    @Test
    public void testDatabaseExists_1() {
        assertThat(catalog.databaseExists(TEST_DEFAULT_DATABASE)).isTrue();
    }

    @Test
    public void testDatabaseExists_2() {
        assertThat(catalog.databaseExists(NONE_EXIST_DATABASE)).isFalse();
    }
}
