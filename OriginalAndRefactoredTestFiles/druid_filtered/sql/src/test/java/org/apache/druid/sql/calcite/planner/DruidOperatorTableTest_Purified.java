package org.apache.druid.sql.calcite.planner;

import com.google.common.collect.ImmutableSet;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.SqlSyntax;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.type.SqlTypeFamily;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.sql.calcite.expression.DirectOperatorConversion;
import org.apache.druid.sql.calcite.expression.OperatorConversions;
import org.apache.druid.sql.calcite.expression.SqlOperatorConversion;
import org.apache.druid.sql.calcite.rel.Windowing;
import org.apache.druid.sql.calcite.table.RowSignatures;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.assertFalse;

public class DruidOperatorTableTest_Purified {

    @Test
    public void testIsFunctionSyntax_1() {
        Assert.assertTrue(DruidOperatorTable.isFunctionSyntax(SqlSyntax.FUNCTION));
    }

    @Test
    public void testIsFunctionSyntax_2() {
        Assert.assertTrue(DruidOperatorTable.isFunctionSyntax(SqlSyntax.FUNCTION_STAR));
    }

    @Test
    public void testIsFunctionSyntax_3() {
        Assert.assertTrue(DruidOperatorTable.isFunctionSyntax(SqlSyntax.FUNCTION_ID));
    }

    @Test
    public void testIsFunctionSyntax_4() {
        Assert.assertTrue(DruidOperatorTable.isFunctionSyntax(SqlSyntax.SPECIAL));
    }

    @Test
    public void testIsFunctionSyntax_5() {
        Assert.assertTrue(DruidOperatorTable.isFunctionSyntax(SqlSyntax.INTERNAL));
    }

    @Test
    public void testIsFunctionSyntax_6() {
        Assert.assertFalse(DruidOperatorTable.isFunctionSyntax(SqlSyntax.BINARY));
    }

    @Test
    public void testIsFunctionSyntax_7() {
        Assert.assertFalse(DruidOperatorTable.isFunctionSyntax(SqlSyntax.PREFIX));
    }

    @Test
    public void testIsFunctionSyntax_8() {
        Assert.assertFalse(DruidOperatorTable.isFunctionSyntax(SqlSyntax.POSTFIX));
    }
}
