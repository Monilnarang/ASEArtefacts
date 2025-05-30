package org.apache.seata.sqlparser.struct;

import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.seata.common.exception.NotSupportYetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableMetaTest_Purified {

    private TableMeta tableMeta;

    private TableMeta tableMeta2;

    @BeforeEach
    public void setUp() {
        tableMeta = new TableMeta();
        tableMeta.setTableName("tableName");
        ColumnMeta col1 = new ColumnMeta();
        col1.setColumnName("col1");
        col1.setOnUpdate(true);
        tableMeta.getAllColumns().put("col1", col1);
        ColumnMeta col2 = new ColumnMeta();
        col2.setColumnName("col2");
        tableMeta.getAllColumns().put("col2", col2);
        IndexMeta primaryIndexMeta = new IndexMeta();
        primaryIndexMeta.setIndextype(IndexType.PRIMARY);
        primaryIndexMeta.setValues(Arrays.asList(col1, col2));
        tableMeta.getAllIndexes().put("primary", primaryIndexMeta);
        tableMeta2 = new TableMeta();
        tableMeta2.setTableName("tableName");
        tableMeta2.getAllColumns().put("col1", col1);
        tableMeta2.getAllColumns().put("col2", col2);
        tableMeta2.getAllIndexes().put("primary", primaryIndexMeta);
    }

    @Test
    public void testEquals_1() {
        assertTrue(tableMeta.equals(tableMeta2));
    }

    @Test
    public void testEquals_2() {
        tableMeta2.setTableName("different_table");
        assertFalse(tableMeta.equals(tableMeta2));
    }

    @Test
    public void testHashCode_1() {
        assertEquals(tableMeta.hashCode(), tableMeta2.hashCode());
    }

    @Test
    public void testHashCode_2() {
        tableMeta2.setTableName("different_table");
        assertNotEquals(tableMeta.hashCode(), tableMeta2.hashCode());
    }
}
