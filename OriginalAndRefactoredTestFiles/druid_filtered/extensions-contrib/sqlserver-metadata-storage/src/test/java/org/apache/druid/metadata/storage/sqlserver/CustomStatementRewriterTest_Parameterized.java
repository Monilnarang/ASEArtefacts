package org.apache.druid.metadata.storage.sqlserver;

import junit.framework.Assert;
import org.apache.druid.metadata.storage.sqlserver.SQLServerConnector.CustomStatementRewriter;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Binding;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.exceptions.UnableToCreateStatementException;
import org.skife.jdbi.v2.tweak.RewrittenStatement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("nls")
public class CustomStatementRewriterTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testExactPatternReplacement_1to7")
    public void testExactPatternReplacement_1to7(String param1, String param2) {
        Assert.assertEquals(param1, rewrite(param2));
    }

    static public Stream<Arguments> Provider_testExactPatternReplacement_1to7() {
        return Stream.of(arguments("BIT NOT NULL DEFAULT (0)", "BOOLEAN NOT NULL DEFAULT FALSE"), arguments("BIT NOT NULL DEFAULT (1)", "BOOLEAN NOT NULL DEFAULT TRUE"), arguments("BIT NOT NULL DEFAULT (0)", "BOOLEAN DEFAULT FALSE"), arguments("BIT NOT NULL DEFAULT (1)", "BOOLEAN DEFAULT TRUE"), arguments("BIT", "BOOLEAN"), arguments(1, true), arguments(0, false));
    }
}
