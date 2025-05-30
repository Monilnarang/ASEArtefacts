package org.apache.flink.table.functions;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.catalog.CatalogFunction;
import org.apache.flink.table.catalog.FunctionLanguage;
import org.apache.flink.table.resource.ResourceUri;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import static org.apache.flink.core.testutils.FlinkAssertions.anyCauseMatches;
import static org.apache.flink.table.functions.UserDefinedFunctionHelper.instantiateFunction;
import static org.apache.flink.table.functions.UserDefinedFunctionHelper.isClassNameSerializable;
import static org.apache.flink.table.functions.UserDefinedFunctionHelper.prepareInstance;
import static org.apache.flink.table.functions.UserDefinedFunctionHelper.validateClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("unused")
class UserDefinedFunctionHelperTest_Purified {

    private static class TestSpec {

        @Nullable
        final Class<? extends UserDefinedFunction> functionClass;

        @Nullable
        final UserDefinedFunction functionInstance;

        @Nullable
        final CatalogFunction catalogFunction;

        @Nullable
        String expectedErrorMessage;

        TestSpec(@Nullable Class<? extends UserDefinedFunction> functionClass, @Nullable UserDefinedFunction functionInstance, @Nullable CatalogFunction catalogFunction) {
            this.functionClass = functionClass;
            this.functionInstance = functionInstance;
            this.catalogFunction = catalogFunction;
        }

        static TestSpec forClass(Class<? extends UserDefinedFunction> function) {
            return new TestSpec(function, null, null);
        }

        static TestSpec forInstance(UserDefinedFunction function) {
            return new TestSpec(null, function, null);
        }

        static TestSpec forCatalogFunction(String className) {
            return new TestSpec(null, null, new CatalogFunctionMock(className));
        }

        TestSpec expectErrorMessage(String expectedErrorMessage) {
            this.expectedErrorMessage = expectedErrorMessage;
            return this;
        }

        TestSpec expectSuccess() {
            this.expectedErrorMessage = null;
            return this;
        }
    }

    private static class CatalogFunctionMock implements CatalogFunction {

        private final String className;

        CatalogFunctionMock(String className) {
            this.className = className;
        }

        @Override
        public String getClassName() {
            return className;
        }

        @Override
        public CatalogFunction copy() {
            return null;
        }

        @Override
        public Optional<String> getDescription() {
            return Optional.empty();
        }

        @Override
        public Optional<String> getDetailedDescription() {
            return Optional.empty();
        }

        @Override
        public FunctionLanguage getFunctionLanguage() {
            return FunctionLanguage.JAVA;
        }

        @Override
        public List<ResourceUri> getFunctionResources() {
            return Collections.emptyList();
        }
    }

    public static class ValidScalarFunction extends ScalarFunction {

        public String eval(int i) {
            return null;
        }
    }

    private static class PrivateScalarFunction extends ScalarFunction {

        public String eval(int i) {
            return null;
        }
    }

    public static class MissingImplementationScalarFunction extends ScalarFunction {
    }

    public static class PrivateMethodScalarFunction extends ScalarFunction {

        private String eval(int i) {
            return null;
        }
    }

    public static class ValidAsyncScalarFunction extends AsyncScalarFunction {

        public void eval(CompletableFuture<Integer> future, int i) {
        }
    }

    private static class PrivateAsyncScalarFunction extends AsyncScalarFunction {

        public void eval(CompletableFuture<Integer> future, int i) {
        }
    }

    public static class MissingImplementationAsyncScalarFunction extends AsyncScalarFunction {
    }

    public static class PrivateMethodAsyncScalarFunction extends AsyncScalarFunction {

        private void eval(CompletableFuture<Integer> future, int i) {
        }
    }

    public static class NonVoidAsyncScalarFunction extends AsyncScalarFunction {

        public String eval(CompletableFuture<Integer> future, int i) {
            return "";
        }
    }

    public static class NoFutureAsyncScalarFunction extends AsyncScalarFunction {

        public void eval(int i) {
        }
    }

    public static class ValidTableAggregateFunction extends TableAggregateFunction<String, String> {

        public void accumulate(String acc, String in) {
        }

        public void emitValue(String acc, Collector<String> out) {
        }

        @Override
        public String createAccumulator() {
            return null;
        }
    }

    public static class MissingEmitTableAggregateFunction extends TableAggregateFunction<String, String> {

        public void accumulate(String acc, String in) {
        }

        @Override
        public String createAccumulator() {
            return null;
        }
    }

    public static class ValidTableFunction extends TableFunction<String> {

        public void eval(String i) {
        }
    }

    public static class ParameterizedTableFunction extends TableFunction<String> {

        public ParameterizedTableFunction(int param) {
        }

        public void eval(String i) {
        }
    }

    private abstract static class AbstractTableAggregateFunction extends TableAggregateFunction<String, String> {

        public void accumulate(String acc, String in) {
        }
    }

    public static class HierarchicalTableAggregateFunction extends AbstractTableAggregateFunction {

        public void emitValue(String acc, Collector<String> out) {
        }

        @Override
        public String createAccumulator() {
            return null;
        }
    }

    public static class StatefulScalarFunction extends ScalarFunction {

        public String state;

        public String eval() {
            return state;
        }
    }

    @Test
    void testSerialization_1() {
        assertThat(isClassNameSerializable(new ValidTableFunction())).isTrue();
    }

    @Test
    void testSerialization_2() {
        assertThat(isClassNameSerializable(new ValidScalarFunction())).isTrue();
    }

    @Test
    void testSerialization_3() {
        assertThat(isClassNameSerializable(new ParameterizedTableFunction(12))).isFalse();
    }

    @Test
    void testSerialization_4() {
        assertThat(isClassNameSerializable(new StatefulScalarFunction())).isFalse();
    }
}
