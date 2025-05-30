package org.apache.flink.table.catalog;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.catalog.exceptions.CatalogException;
import org.apache.flink.table.catalog.exceptions.DatabaseAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotEmptyException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotExistException;
import org.apache.flink.table.catalog.exceptions.FunctionAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.FunctionNotExistException;
import org.apache.flink.table.catalog.exceptions.ModelAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.ModelNotExistException;
import org.apache.flink.table.catalog.exceptions.PartitionAlreadyExistsException;
import org.apache.flink.table.catalog.exceptions.PartitionNotExistException;
import org.apache.flink.table.catalog.exceptions.PartitionSpecInvalidException;
import org.apache.flink.table.catalog.exceptions.TableAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.TableNotExistException;
import org.apache.flink.table.catalog.exceptions.TableNotPartitionedException;
import org.apache.flink.table.catalog.stats.CatalogColumnStatistics;
import org.apache.flink.table.catalog.stats.CatalogTableStatistics;
import org.apache.flink.table.legacy.api.TableSchema;
import org.apache.flink.util.TestLoggerExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(TestLoggerExtension.class)
public abstract class CatalogTest_Purified {

    protected static final String IS_STREAMING = "is_streaming";

    protected final String db1 = "db1";

    protected final String db2 = "db2";

    protected final String t1 = "t1";

    protected final String t2 = "t2";

    protected final String t3 = "t3";

    protected final String m1 = "m1";

    protected final String m2 = "m2";

    protected final ObjectPath path1 = new ObjectPath(db1, t1);

    protected final ObjectPath path2 = new ObjectPath(db2, t2);

    protected final ObjectPath path3 = new ObjectPath(db1, t2);

    protected final ObjectPath path4 = new ObjectPath(db1, t3);

    protected final ObjectPath modelPath1 = new ObjectPath(db1, m1);

    protected final ObjectPath modelPath2 = new ObjectPath(db1, m2);

    protected final ObjectPath nonExistDbPath = ObjectPath.fromString("non.exist");

    protected final ObjectPath nonExistObjectPath = ObjectPath.fromString("db1.nonexist");

    public static final String TEST_CATALOG_NAME = "test-catalog";

    protected static final String TEST_COMMENT = "test comment";

    protected static Catalog catalog;

    @AfterEach
    void cleanup() throws Exception {
        if (catalog.tableExists(path1)) {
            catalog.dropTable(path1, true);
        }
        if (catalog.tableExists(path2)) {
            catalog.dropTable(path2, true);
        }
        if (catalog.tableExists(path3)) {
            catalog.dropTable(path3, true);
        }
        if (catalog.tableExists(path4)) {
            catalog.dropTable(path4, true);
        }
        if (catalog.functionExists(path1)) {
            catalog.dropFunction(path1, true);
        }
        if (supportsModels()) {
            if (catalog.modelExists(modelPath1)) {
                catalog.dropModel(modelPath1, true);
            }
            if (catalog.modelExists(modelPath2)) {
                catalog.dropModel(modelPath2, true);
            }
        }
        if (catalog.databaseExists(db1)) {
            catalog.dropDatabase(db1, true, false);
        }
        if (catalog.databaseExists(db2)) {
            catalog.dropDatabase(db2, true, false);
        }
    }

    @AfterAll
    static void closeup() {
        if (catalog != null) {
            catalog.close();
        }
    }

    public abstract CatalogDatabase createDb();

    public abstract CatalogDatabase createAnotherDb();

    public abstract CatalogTable createTable();

    public abstract CatalogModel createModel();

    public abstract CatalogTable createAnotherTable();

    public abstract CatalogTable createStreamingTable();

    public abstract CatalogTable createPartitionedTable();

    public abstract CatalogTable createAnotherPartitionedTable();

    public abstract CatalogView createView();

    public abstract CatalogView createAnotherView();

    protected abstract CatalogFunction createFunction();

    protected abstract CatalogFunction createPythonFunction();

    protected abstract CatalogFunction createAnotherFunction();

    public abstract CatalogPartition createPartition();

    protected abstract boolean supportsModels();

    protected ResolvedSchema createSchema() {
        return new ResolvedSchema(Arrays.asList(Column.physical("first", DataTypes.STRING()), Column.physical("second", DataTypes.INT()), Column.physical("third", DataTypes.STRING())), Collections.emptyList(), null);
    }

    protected ResolvedSchema createAnotherSchema() {
        return new ResolvedSchema(Arrays.asList(Column.physical("first", DataTypes.STRING()), Column.physical("second", DataTypes.STRING()), Column.physical("third", DataTypes.STRING())), Collections.emptyList(), null);
    }

    protected List<String> createPartitionKeys() {
        return Arrays.asList("second", "third");
    }

    protected CatalogPartitionSpec createPartitionSpec() {
        return new CatalogPartitionSpec(new HashMap<String, String>() {

            {
                put("third", "2000");
                put("second", "bob");
            }
        });
    }

    protected CatalogPartitionSpec createAnotherPartitionSpec() {
        return new CatalogPartitionSpec(new HashMap<String, String>() {

            {
                put("third", "2010");
                put("second", "bob");
            }
        });
    }

    protected CatalogPartitionSpec createPartitionSpecSubset() {
        return new CatalogPartitionSpec(new HashMap<String, String>() {

            {
                put("second", "bob");
            }
        });
    }

    protected CatalogPartitionSpec createAnotherPartitionSpecSubset() {
        return new CatalogPartitionSpec(new HashMap<String, String>() {

            {
                put("third", "2000");
            }
        });
    }

    protected CatalogPartitionSpec createInvalidPartitionSpecSubset() {
        return new CatalogPartitionSpec(new HashMap<String, String>() {

            {
                put("third", "2010");
            }
        });
    }

    public static class TestView implements ResolvedCatalogBaseTable<CatalogView> {

        @Override
        public TableKind getTableKind() {
            return TableKind.VIEW;
        }

        @Override
        public Map<String, String> getOptions() {
            return null;
        }

        @Override
        public CatalogView getOrigin() {
            return null;
        }

        @Override
        public ResolvedSchema getResolvedSchema() {
            return null;
        }

        @Override
        public TableSchema getSchema() {
            return TableSchema.builder().build();
        }

        @Override
        public String getComment() {
            return null;
        }

        @Override
        public CatalogBaseTable copy() {
            return null;
        }

        @Override
        public Optional<String> getDescription() {
            return Optional.empty();
        }

        @Override
        public Optional<String> getDetailedDescription() {
            return Optional.empty();
        }
    }

    protected void checkEquals(CatalogFunction f1, CatalogFunction f2) {
        assertThat(f2.getClassName()).isEqualTo(f1.getClassName());
        assertThat(f2.getFunctionLanguage()).isEqualTo(f1.getFunctionLanguage());
    }

    protected void checkEquals(CatalogColumnStatistics cs1, CatalogColumnStatistics cs2) {
        CatalogTestUtil.checkEquals(cs1, cs2);
    }

    @Test
    public void testCreateDb_1() throws Exception {
        assertThat(catalog.databaseExists(db1)).isFalse();
    }

    @Test
    public void testCreateDb_2() throws Exception {
        catalog.createDatabase(db1, cd, false);
        assertThat(catalog.databaseExists(db1)).isTrue();
    }

    @Test
    public void testDbExists_1() throws Exception {
        assertThat(catalog.databaseExists("nonexistent")).isFalse();
    }

    @Test
    public void testDbExists_2() throws Exception {
        catalog.createDatabase(db1, createDb(), false);
        assertThat(catalog.databaseExists(db1)).isTrue();
    }
}
