package org.apache.druid.metadata.storage.sqlserver;

import junit.framework.Assert;
import org.apache.druid.metadata.storage.sqlserver.SQLServerConnector.CustomStatementRewriter;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Binding;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.exceptions.UnableToCreateStatementException;
import org.skife.jdbi.v2.tweak.RewrittenStatement;

@SuppressWarnings("nls")
public class CustomStatementRewriterTest_Purified {

    private CustomStatementRewriter customStatementRewriter;

    private Binding params;

    private StatementContext ctx;

    @Before
    public void setUp() {
        customStatementRewriter = new CustomStatementRewriter();
        params = null;
        ctx = null;
    }

    private String rewrite(String sql) {
        RewrittenStatement rewrittenStatement = customStatementRewriter.rewrite(sql, params, ctx);
        return rewrittenStatement.getSql();
    }

    @Test
    public void testExactPatternReplacement_1() {
        Assert.assertEquals("BIT NOT NULL DEFAULT (0)", rewrite("BOOLEAN NOT NULL DEFAULT FALSE"));
    }

    @Test
    public void testExactPatternReplacement_2() {
        Assert.assertEquals("BIT NOT NULL DEFAULT (1)", rewrite("BOOLEAN NOT NULL DEFAULT TRUE"));
    }

    @Test
    public void testExactPatternReplacement_3() {
        Assert.assertEquals("BIT NOT NULL DEFAULT (0)", rewrite("BOOLEAN DEFAULT FALSE"));
    }

    @Test
    public void testExactPatternReplacement_4() {
        Assert.assertEquals("BIT NOT NULL DEFAULT (1)", rewrite("BOOLEAN DEFAULT TRUE"));
    }

    @Test
    public void testExactPatternReplacement_5() {
        Assert.assertEquals("BIT", rewrite("BOOLEAN"));
    }

    @Test
    public void testExactPatternReplacement_6() {
        Assert.assertEquals("1", rewrite("TRUE"));
    }

    @Test
    public void testExactPatternReplacement_7() {
        Assert.assertEquals("0", rewrite("FALSE"));
    }
}
