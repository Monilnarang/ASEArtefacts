package org.apache.flink.table.planner.expressions;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.TableConfig;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.planner.utils.StreamTableTestUtil;
import org.apache.flink.table.planner.utils.TableTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

class ColumnReferenceFinderTest_Purified extends TableTestBase {

    private final StreamTableTestUtil util = streamTestUtil(TableConfig.getDefault());

    private ResolvedSchema resolvedSchema;

    @BeforeEach
    void beforeEach() {
        resolvedSchema = util.testingTableEnv().getCatalogManager().getSchemaResolver().resolve(Schema.newBuilder().columnByExpression("a", "b || '_001'").column("b", DataTypes.STRING()).columnByExpression("c", "d * e + 2").column("d", DataTypes.DOUBLE()).columnByMetadata("e", DataTypes.INT(), null, true).column("tuple", DataTypes.ROW(DataTypes.TIMESTAMP(3), DataTypes.INT())).column("g", DataTypes.TIMESTAMP(3)).columnByExpression("ts", "tuple.f0").watermark("ts", "g - interval '5' day").build());
    }

    @Test
    void testFindReferencedColumn_1() {
        assertThat(ColumnReferenceFinder.findReferencedColumn("b", resolvedSchema)).isEqualTo(Collections.emptySet());
    }

    @Test
    void testFindReferencedColumn_2() {
        assertThat(ColumnReferenceFinder.findReferencedColumn("a", resolvedSchema)).containsExactlyInAnyOrder("b");
    }

    @Test
    void testFindReferencedColumn_3() {
        assertThat(ColumnReferenceFinder.findReferencedColumn("c", resolvedSchema)).containsExactlyInAnyOrder("d", "e");
    }

    @Test
    void testFindReferencedColumn_4() {
        assertThat(ColumnReferenceFinder.findReferencedColumn("ts", resolvedSchema)).containsExactlyInAnyOrder("tuple");
    }

    @Test
    void testFindReferencedColumn_5() {
        assertThat(ColumnReferenceFinder.findWatermarkReferencedColumn(resolvedSchema)).containsExactlyInAnyOrder("ts", "g");
    }
}
