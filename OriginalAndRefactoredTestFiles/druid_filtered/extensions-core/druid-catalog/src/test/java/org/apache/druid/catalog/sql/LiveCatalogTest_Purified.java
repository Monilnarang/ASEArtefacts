package org.apache.druid.catalog.sql;

import org.apache.druid.catalog.CatalogException.DuplicateKeyException;
import org.apache.druid.catalog.MetadataCatalog;
import org.apache.druid.catalog.model.Columns;
import org.apache.druid.catalog.model.TableMetadata;
import org.apache.druid.catalog.model.table.TableBuilder;
import org.apache.druid.catalog.storage.CatalogStorage;
import org.apache.druid.catalog.storage.CatalogTests;
import org.apache.druid.catalog.sync.LocalMetadataCatalog;
import org.apache.druid.metadata.TestDerbyConnector;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.sql.calcite.planner.CatalogResolver;
import org.apache.druid.sql.calcite.table.DatasourceTable.PhysicalDatasourceMetadata;
import org.apache.druid.sql.calcite.table.DruidTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class LiveCatalogTest_Purified {

    @Rule
    public final TestDerbyConnector.DerbyConnectorRule derbyConnectorRule = new TestDerbyConnector.DerbyConnectorRule();

    private CatalogTests.DbFixture dbFixture;

    private CatalogStorage storage;

    private CatalogResolver resolver;

    @Before
    public void setUp() {
        dbFixture = new CatalogTests.DbFixture(derbyConnectorRule);
        storage = dbFixture.storage;
        MetadataCatalog catalog = new LocalMetadataCatalog(storage, storage.schemaRegistry());
        resolver = new LiveCatalogResolver(catalog);
    }

    @After
    public void tearDown() {
        CatalogTests.tearDown(dbFixture);
    }

    private void createTableMetadata(TableMetadata table) {
        try {
            storage.tables().create(table);
        } catch (DuplicateKeyException e) {
            fail(e.getMessage());
        }
    }

    private void populateCatalog(boolean withTimeCol) {
        TableMetadata table = TableBuilder.datasource("trivial", "PT1D").build();
        createTableMetadata(table);
        TableBuilder builder = TableBuilder.datasource("merge", "PT1D");
        if (withTimeCol) {
            builder.timeColumn();
        }
        table = builder.column("dsa", null).column("dsb", Columns.STRING).column("dsc", Columns.LONG).column("dsd", Columns.FLOAT).column("dse", Columns.DOUBLE).column("newa", null).column("newb", Columns.STRING).column("newc", Columns.LONG).column("newd", Columns.FLOAT).column("newe", Columns.DOUBLE).hiddenColumns(Arrays.asList("dsf", "dsg")).build();
        createTableMetadata(table);
    }

    private PhysicalDatasourceMetadata mockDatasource() {
        RowSignature sig = RowSignature.builder().add(Columns.TIME_COLUMN, ColumnType.LONG).add("dsa", ColumnType.DOUBLE).add("dsb", ColumnType.LONG).add("dsc", ColumnType.STRING).add("dsd", ColumnType.LONG).add("dse", ColumnType.FLOAT).add("dsf", ColumnType.STRING).add("dsg", ColumnType.LONG).add("dsh", ColumnType.DOUBLE).build();
        return new PhysicalDatasourceMetadata(new TableDataSource("merge"), sig, true, true);
    }

    private void assertColumnEquals(DruidTable table, int i, String name, ColumnType type) {
        RowSignature sig = table.getRowSignature();
        assertEquals(name, sig.getColumnName(i));
        assertEquals(type, sig.getColumnType(i).get());
    }

    @Test
    public void testUnknownTable_1() {
        assertNull(resolver.resolveDatasource("bogus", null));
    }

    @Test
    public void testUnknownTable_2() {
        PhysicalDatasourceMetadata dsMetadata = mockDatasource();
        DruidTable table = resolver.resolveDatasource("merge", dsMetadata);
        assertSame(dsMetadata.getRowSignature(), table.getRowSignature());
    }
}
