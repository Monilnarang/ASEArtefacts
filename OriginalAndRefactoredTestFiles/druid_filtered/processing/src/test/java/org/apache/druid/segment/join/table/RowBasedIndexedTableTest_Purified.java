package org.apache.druid.segment.join.table;

import com.google.common.collect.ImmutableSet;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.join.JoinTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;
import java.util.Map;

public class RowBasedIndexedTableTest_Purified {

    private static final int INDEX_COUNTRIES_COUNTRY_NUMBER = 0;

    private static final int INDEX_COUNTRIES_COUNTRY_ISO_CODE = 1;

    private static final int INDEX_COUNTRIES_COUNTRY_NAME = 2;

    private static final int INDEX_REGIONS_REGION_ISO_CODE = 0;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public RowBasedIndexedTable<Map<String, Object>> countriesTable;

    public RowBasedIndexedTable<Map<String, Object>> regionsTable;

    @Before
    public void setUp() throws IOException {
        countriesTable = JoinTestHelper.createCountriesIndexedTable();
        regionsTable = JoinTestHelper.createRegionsIndexedTable();
    }

    @Test
    public void testVersion_1() {
        Assert.assertEquals(JoinTestHelper.INDEXED_TABLE_VERSION, countriesTable.version());
    }

    @Test
    public void testVersion_2() {
        Assert.assertEquals(JoinTestHelper.INDEXED_TABLE_VERSION, regionsTable.version());
    }
}
