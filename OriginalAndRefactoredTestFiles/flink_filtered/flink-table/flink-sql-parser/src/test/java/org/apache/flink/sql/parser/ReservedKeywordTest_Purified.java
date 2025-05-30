package org.apache.flink.sql.parser;

import org.apache.flink.sql.parser.impl.FlinkSqlParserImpl;
import org.apache.calcite.sql.parser.SqlAbstractParserImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import java.io.StringReader;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
class ReservedKeywordTest_Purified {

    private static final SqlAbstractParserImpl.Metadata PARSER_METADATA = FlinkSqlParserImpl.FACTORY.getParser(new StringReader("")).getMetadata();

    @DisplayName("STATEMENT is a reserved keyword")
    @Test
    void testSTATEMENT_1() {
        assertThat(PARSER_METADATA.isKeyword("STATEMENT")).isTrue();
    }

    @DisplayName("STATEMENT is a reserved keyword")
    @Test
    void testSTATEMENT_2() {
        assertThat(PARSER_METADATA.isNonReservedKeyword("STATEMENT")).isFalse();
    }
}
