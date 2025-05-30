package org.apache.seata.sqlparser.druid.sqlserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLDateExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlOrderingExpr;
import org.apache.seata.common.exception.NotSupportYetException;
import org.apache.seata.sqlparser.SQLParsingException;
import org.apache.seata.sqlparser.SQLType;
import org.apache.seata.sqlparser.druid.AbstractRecognizerTest;
import org.apache.seata.sqlparser.util.JdbcConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SqlServerInsertRecognizerTest_Purified extends AbstractRecognizerTest {

    private final int pkIndex = 0;

    @Override
    public String getDbType() {
        return JdbcConstants.SQLSERVER;
    }

    @Test
    public void testGetInsertParamsValue_1() {
        String sql = "INSERT INTO t(a) VALUES (?)";
        SQLStatement ast = getSQLStatement(sql);
        SqlServerInsertRecognizer recognizer = new SqlServerInsertRecognizer(sql, ast);
        Assertions.assertEquals("?", recognizer.getInsertParamsValue().get(0));
    }

    @Test
    public void testGetInsertParamsValue_2() {
        String sql_2 = "INSERT INTO t(a) VALUES ()";
        SQLStatement ast_2 = getSQLStatement(sql_2);
        SqlServerInsertRecognizer recognizer_2 = new SqlServerInsertRecognizer(sql_2, ast_2);
        Assertions.assertEquals("", recognizer_2.getInsertParamsValue().get(0));
    }

    @Test
    public void testGetInsertParamsValue_3() {
        String sql_3 = "INSERT INTO T1 DEFAULT VALUES";
        SQLStatement ast_3 = getSQLStatement(sql_3);
        SqlServerInsertRecognizer recognizer_3 = new SqlServerInsertRecognizer(sql_3, ast_3);
        Assertions.assertTrue(recognizer_3.getInsertParamsValue().isEmpty());
    }
}
