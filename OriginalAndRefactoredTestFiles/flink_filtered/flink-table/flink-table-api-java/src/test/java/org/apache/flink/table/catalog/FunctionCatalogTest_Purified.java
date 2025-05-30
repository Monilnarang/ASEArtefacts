package org.apache.flink.table.catalog;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.functions.FunctionDefinition;
import org.apache.flink.table.functions.FunctionIdentifier;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.table.module.Module;
import org.apache.flink.table.module.ModuleManager;
import org.apache.flink.table.resource.ResourceManager;
import org.apache.flink.table.utils.CatalogManagerMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.apache.flink.core.testutils.FlinkAssertions.anyCauseMatches;
import static org.apache.flink.table.utils.CatalogManagerMocks.DEFAULT_CATALOG;
import static org.apache.flink.table.utils.CatalogManagerMocks.DEFAULT_DATABASE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FunctionCatalogTest_Purified {

    private static final ScalarFunction FUNCTION_1 = new TestFunction1();

    private static final ScalarFunction FUNCTION_2 = new TestFunction2();

    private static final ScalarFunction FUNCTION_3 = new TestFunction3();

    private static final ScalarFunction FUNCTION_4 = new TestFunction4();

    private static final ScalarFunction FUNCTION_INVALID = new InvalidTestFunction();

    private static final TableFunction<?> TABLE_FUNCTION = new TestTableFunction();

    private static final AggregateFunction<?, ?> AGGREGATE_FUNCTION = new TestAggregateFunction();

    private static final String NAME = "test_function";

    private static final ObjectIdentifier IDENTIFIER = ObjectIdentifier.of(DEFAULT_CATALOG, DEFAULT_DATABASE, NAME);

    private static final UnresolvedIdentifier FULL_UNRESOLVED_IDENTIFIER = UnresolvedIdentifier.of(DEFAULT_CATALOG, DEFAULT_DATABASE, NAME);

    private static final UnresolvedIdentifier PARTIAL_UNRESOLVED_IDENTIFIER = UnresolvedIdentifier.of(NAME);

    private ModuleManager moduleManager;

    private FunctionCatalog functionCatalog;

    private Catalog catalog;

    @BeforeEach
    void init() {
        catalog = new GenericInMemoryCatalog(DEFAULT_CATALOG, DEFAULT_DATABASE);
        moduleManager = new ModuleManager();
        Configuration configuration = new Configuration();
        functionCatalog = new FunctionCatalog(configuration, ResourceManager.createResourceManager(new URL[0], FunctionCatalogTest.class.getClassLoader(), configuration), CatalogManagerMocks.preparedCatalogManager().defaultCatalog(DEFAULT_CATALOG, catalog).build(), moduleManager);
    }

    private static class TestModule implements Module {

        @Override
        public Set<String> listFunctions() {
            return new HashSet<String>() {

                {
                    add(NAME);
                }
            };
        }

        @Override
        public Optional<FunctionDefinition> getFunctionDefinition(String name) {
            return Optional.of(FUNCTION_3);
        }
    }

    public static class TestFunction1 extends ScalarFunction {

        public String eval() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o.getClass() == this.getClass();
        }
    }

    public static class TestFunction2 extends ScalarFunction {

        public String eval() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o.getClass() == this.getClass();
        }
    }

    public static class TestFunction3 extends ScalarFunction {

        public String eval() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o.getClass() == this.getClass();
        }
    }

    public static class TestFunction4 extends ScalarFunction {

        public String eval() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o.getClass() == this.getClass();
        }
    }

    public static class InvalidTestFunction extends ScalarFunction {
    }

    @SuppressWarnings("unused")
    public static class TestTableFunction extends TableFunction<String> {

        public void eval(String in) {
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o.getClass() == this.getClass();
        }
    }

    @SuppressWarnings("unused")
    public static class TestAggregateFunction extends AggregateFunction<String, String> {

        @Override
        public String getValue(String accumulator) {
            return null;
        }

        @Override
        public String createAccumulator() {
            return null;
        }

        public void accumulate(String in) {
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o.getClass() == this.getClass();
        }
    }

    @Test
    void testPreciseFunctionReference_1() throws Exception {
        assertThat(functionCatalog.lookupFunction(FULL_UNRESOLVED_IDENTIFIER)).isEmpty();
    }

    @Test
    void testPreciseFunctionReference_2_testMerged_2() throws Exception {
        catalog.createFunction(IDENTIFIER.toObjectPath(), new CatalogFunctionImpl(FUNCTION_1.getClass().getName()), false);
        assertThat(functionCatalog.lookupFunction(FULL_UNRESOLVED_IDENTIFIER)).hasValue(ContextResolvedFunction.permanent(FunctionIdentifier.of(IDENTIFIER), FUNCTION_1));
        functionCatalog.registerTemporaryCatalogFunction(PARTIAL_UNRESOLVED_IDENTIFIER, FUNCTION_2, false);
        assertThat(functionCatalog.lookupFunction(FULL_UNRESOLVED_IDENTIFIER)).hasValue(ContextResolvedFunction.temporary(FunctionIdentifier.of(IDENTIFIER), FUNCTION_2));
    }

    @Test
    void testAmbiguousFunctionReference_1() throws Exception {
        assertThat(functionCatalog.lookupFunction(PARTIAL_UNRESOLVED_IDENTIFIER)).isEmpty();
    }

    @Test
    void testAmbiguousFunctionReference_2_testMerged_2() throws Exception {
        catalog.createFunction(IDENTIFIER.toObjectPath(), new CatalogFunctionImpl(FUNCTION_1.getClass().getName()), false);
        assertThat(functionCatalog.lookupFunction(PARTIAL_UNRESOLVED_IDENTIFIER)).hasValue(ContextResolvedFunction.permanent(FunctionIdentifier.of(IDENTIFIER), FUNCTION_1));
        functionCatalog.registerTemporaryCatalogFunction(PARTIAL_UNRESOLVED_IDENTIFIER, FUNCTION_2, false);
        assertThat(functionCatalog.lookupFunction(PARTIAL_UNRESOLVED_IDENTIFIER)).hasValue(ContextResolvedFunction.temporary(FunctionIdentifier.of(IDENTIFIER), FUNCTION_2));
        assertThat(functionCatalog.lookupFunction(PARTIAL_UNRESOLVED_IDENTIFIER)).hasValue(ContextResolvedFunction.permanent(FunctionIdentifier.of(NAME), FUNCTION_3));
        functionCatalog.registerTemporarySystemFunction(NAME, FUNCTION_4, false);
        assertThat(functionCatalog.lookupFunction(PARTIAL_UNRESOLVED_IDENTIFIER)).hasValue(ContextResolvedFunction.temporary(FunctionIdentifier.of(NAME), FUNCTION_4));
    }
}
