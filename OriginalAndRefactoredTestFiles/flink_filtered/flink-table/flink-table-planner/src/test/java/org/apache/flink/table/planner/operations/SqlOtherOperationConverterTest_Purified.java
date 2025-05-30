package org.apache.flink.table.planner.operations;

import org.apache.flink.table.api.SqlParserException;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.catalog.UnresolvedIdentifier;
import org.apache.flink.table.operations.DescribeCatalogOperation;
import org.apache.flink.table.operations.DescribeFunctionOperation;
import org.apache.flink.table.operations.LoadModuleOperation;
import org.apache.flink.table.operations.Operation;
import org.apache.flink.table.operations.ShowCatalogsOperation;
import org.apache.flink.table.operations.ShowCreateCatalogOperation;
import org.apache.flink.table.operations.ShowDatabasesOperation;
import org.apache.flink.table.operations.ShowFunctionsOperation;
import org.apache.flink.table.operations.ShowFunctionsOperation.FunctionScope;
import org.apache.flink.table.operations.ShowModulesOperation;
import org.apache.flink.table.operations.ShowPartitionsOperation;
import org.apache.flink.table.operations.ShowProceduresOperation;
import org.apache.flink.table.operations.ShowTablesOperation;
import org.apache.flink.table.operations.ShowViewsOperation;
import org.apache.flink.table.operations.UnloadModuleOperation;
import org.apache.flink.table.operations.UseCatalogOperation;
import org.apache.flink.table.operations.UseDatabaseOperation;
import org.apache.flink.table.operations.UseModulesOperation;
import org.apache.flink.table.operations.command.AddJarOperation;
import org.apache.flink.table.operations.command.ClearOperation;
import org.apache.flink.table.operations.command.HelpOperation;
import org.apache.flink.table.operations.command.QuitOperation;
import org.apache.flink.table.operations.command.RemoveJarOperation;
import org.apache.flink.table.operations.command.ResetOperation;
import org.apache.flink.table.operations.command.SetOperation;
import org.apache.flink.table.operations.command.ShowJarsOperation;
import org.apache.flink.table.operations.utils.LikeType;
import org.apache.flink.table.operations.utils.ShowLikeOperator;
import org.apache.flink.table.planner.parse.ExtendedParser;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SqlOtherOperationConverterTest_Purified extends SqlNodeToOperationConversionTestBase {

    private static Stream<Arguments> inputForShowCatalogsTest() {
        return Stream.of(Arguments.of("show catalogs", new ShowCatalogsOperation(null), "SHOW CATALOGS"), Arguments.of("show catalogs like 'c%'", new ShowCatalogsOperation(ShowLikeOperator.of(LikeType.LIKE, "c%")), "SHOW CATALOGS LIKE 'c%'"), Arguments.of("show catalogs not like 'c%'", new ShowCatalogsOperation(ShowLikeOperator.of(LikeType.NOT_LIKE, "c%")), "SHOW CATALOGS NOT LIKE 'c%'"), Arguments.of("show catalogs ilike 'c%'", new ShowCatalogsOperation(ShowLikeOperator.of(LikeType.ILIKE, "c%")), "SHOW CATALOGS ILIKE 'c%'"), Arguments.of("show catalogs not ilike 'c%'", new ShowCatalogsOperation(ShowLikeOperator.of(LikeType.NOT_ILIKE, "c%")), "SHOW CATALOGS NOT ILIKE 'c%'"));
    }

    private static Stream<Arguments> inputForShowTablesTest() {
        return Stream.of(Arguments.of("SHOW TABLES from cat1.db1 not like 't%'", new ShowTablesOperation("cat1", "db1", "FROM", ShowLikeOperator.of(LikeType.NOT_LIKE, "t%")), "SHOW TABLES FROM cat1.db1 NOT LIKE 't%'"), Arguments.of("SHOW TABLES in db2", new ShowTablesOperation("builtin", "db2", "IN", null), "SHOW TABLES IN builtin.db2"), Arguments.of("SHOW TABLES", new ShowTablesOperation("builtin", "default", null, null), "SHOW TABLES"));
    }

    private static Stream<Arguments> inputForShowViewsTest() {
        return Stream.of(Arguments.of("SHOW VIEWS from cat1.db1 not like 't%'", new ShowViewsOperation("cat1", "db1", "FROM", ShowLikeOperator.of(LikeType.NOT_LIKE, "t%")), "SHOW VIEWS FROM cat1.db1 NOT LIKE 't%'"), Arguments.of("SHOW VIEWS in db2", new ShowViewsOperation("builtin", "db2", "IN", null), "SHOW VIEWS IN builtin.db2"), Arguments.of("SHOW VIEWS", new ShowViewsOperation("builtin", "default", null, null), "SHOW VIEWS"));
    }

    private static Stream<Arguments> inputForShowFunctionsTest() {
        return Stream.of(Arguments.of("show functions", new ShowFunctionsOperation(FunctionScope.ALL, null, "builtin", "default", null), "SHOW FUNCTIONS"), Arguments.of("show user functions", new ShowFunctionsOperation(FunctionScope.USER, null, "builtin", "default", null), "SHOW USER FUNCTIONS"), Arguments.of("show functions from cat1.db1 not like 'f%'", new ShowFunctionsOperation(FunctionScope.ALL, "FROM", "cat1", "db1", ShowLikeOperator.of(LikeType.NOT_LIKE, "f%")), "SHOW FUNCTIONS FROM cat1.db1 NOT LIKE 'f%'"), Arguments.of("show user functions in cat1.db1 ilike 'f%'", new ShowFunctionsOperation(FunctionScope.USER, "IN", "cat1", "db1", ShowLikeOperator.of(LikeType.ILIKE, "f%")), "SHOW USER FUNCTIONS IN cat1.db1 ILIKE 'f%'"), Arguments.of("show functions in db1", new ShowFunctionsOperation(FunctionScope.ALL, "IN", "builtin", "db1", null), "SHOW FUNCTIONS IN builtin.db1"));
    }

    private static Stream<Arguments> inputForShowDatabasesTest() {
        return Stream.of(Arguments.of("SHOW DATABASES", new ShowDatabasesOperation("builtin", null, null), "SHOW DATABASES"), Arguments.of("show databases from cat1 not like 'f%'", new ShowDatabasesOperation("cat1", "FROM", ShowLikeOperator.of(LikeType.NOT_LIKE, "f%")), "SHOW DATABASES FROM cat1 NOT LIKE 'f%'"), Arguments.of("show databases from cat1 not ilike 'f%'", new ShowDatabasesOperation("cat1", "FROM", ShowLikeOperator.of(LikeType.NOT_ILIKE, "f%")), "SHOW DATABASES FROM cat1 NOT ILIKE 'f%'"), Arguments.of("show databases from cat1 like 'f%'", new ShowDatabasesOperation("cat1", "FROM", ShowLikeOperator.of(LikeType.LIKE, "f%")), "SHOW DATABASES FROM cat1 LIKE 'f%'"), Arguments.of("show databases in cat1 ilike 'f%'", new ShowDatabasesOperation("cat1", "IN", ShowLikeOperator.of(LikeType.ILIKE, "f%")), "SHOW DATABASES IN cat1 ILIKE 'f%'"), Arguments.of("show databases in cat1", new ShowDatabasesOperation("cat1", "IN", null), "SHOW DATABASES IN cat1"));
    }

    private static Stream<Arguments> inputForShowProceduresTest() {
        return Stream.of(Arguments.of("SHOW procedures from cat1.db1 not like 't%'", new ShowProceduresOperation("cat1", "db1", "FROM", ShowLikeOperator.of(LikeType.NOT_LIKE, "t%"))), Arguments.of("SHOW procedures from cat1.db1 ilike 't%'", new ShowProceduresOperation("cat1", "db1", "FROM", ShowLikeOperator.of(LikeType.ILIKE, "t%"))), Arguments.of("SHOW procedures in db1", new ShowProceduresOperation("builtin", "db1", "IN", null)), Arguments.of("SHOW procedures", new ShowProceduresOperation("builtin", "default", null, null)));
    }

    private static Stream<Arguments> argsForTestShowFailedCase() {
        return Stream.of(Arguments.of("SHOW procedures in cat.db.t", "SHOW PROCEDURES from/in identifier [ cat.db.t ] format error," + " it should be [catalog_name.]database_name."), Arguments.of("SHOW Views in cat.db1.t2", "SHOW VIEWS from/in identifier [ cat.db1.t2 ] format error," + " it should be [catalog_name.]database_name."), Arguments.of("SHOW functions in cat.db3.t5", "SHOW FUNCTIONS from/in identifier [ cat.db3.t5 ] format error," + " it should be [catalog_name.]database_name."), Arguments.of("SHOW tables in cat1.db3.t2", "SHOW TABLES from/in identifier [ cat1.db3.t2 ] format error," + " it should be [catalog_name.]database_name."));
    }

    @Test
    void testUseDatabase_1_testMerged_1() {
        final String sql1 = "USE db1";
        Operation operation1 = parse(sql1);
        assertThat(operation1).isInstanceOf(UseDatabaseOperation.class);
        assertThat(((UseDatabaseOperation) operation1).getCatalogName()).isEqualTo("builtin");
        assertThat(((UseDatabaseOperation) operation1).getDatabaseName()).isEqualTo("db1");
    }

    @Test
    void testUseDatabase_4_testMerged_2() {
        final String sql2 = "USE cat1.db1";
        Operation operation2 = parse(sql2);
        assertThat(operation2).isInstanceOf(UseDatabaseOperation.class);
        assertThat(((UseDatabaseOperation) operation2).getCatalogName()).isEqualTo("cat1");
        assertThat(((UseDatabaseOperation) operation2).getDatabaseName()).isEqualTo("db1");
    }

    @Test
    void testReset_1_testMerged_1() {
        Operation operation1 = parse("RESET");
        assertThat(operation1).isInstanceOf(ResetOperation.class);
        assertThat(((ResetOperation) operation1).getKey()).isNotPresent();
    }

    @Test
    void testReset_3_testMerged_2() {
        Operation operation2 = parse("RESET 'test-key'");
        assertThat(operation2).isInstanceOf(ResetOperation.class);
        assertThat(((ResetOperation) operation2).getKey()).isPresent();
        assertThat(((ResetOperation) operation2).getKey()).hasValue("test-key");
    }
}
