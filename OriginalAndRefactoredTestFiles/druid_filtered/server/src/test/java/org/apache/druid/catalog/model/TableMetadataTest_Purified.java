package org.apache.druid.catalog.model;

import com.google.common.collect.ImmutableMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.catalog.CatalogTest;
import org.apache.druid.catalog.model.TableMetadata.TableState;
import org.apache.druid.catalog.model.table.DatasourceDefn;
import org.apache.druid.java.util.common.IAE;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

@Category(CatalogTest.class)
public class TableMetadataTest_Purified {

    @Test
    public void testId_1_testMerged_1() {
        TableId id1 = new TableId("schema", "table");
        assertEquals("schema", id1.schema());
        assertEquals("table", id1.name());
        assertEquals("\"schema\".\"table\"", id1.sqlName());
        assertEquals(id1.sqlName(), id1.toString());
    }

    @Test
    public void testId_5_testMerged_2() {
        TableId id2 = TableId.datasource("ds");
        assertEquals(TableId.DRUID_SCHEMA, id2.schema());
        assertEquals("ds", id2.name());
    }
}
