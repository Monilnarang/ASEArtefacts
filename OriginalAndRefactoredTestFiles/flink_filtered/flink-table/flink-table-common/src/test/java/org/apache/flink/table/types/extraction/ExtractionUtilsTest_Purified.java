package org.apache.flink.table.types.extraction;

import org.apache.flink.table.types.extraction.ExtractionUtils.Autoboxing;
import org.apache.flink.shaded.guava33.com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExtractionUtilsTest_Purified {

    public static class ClassBase<T> {

        public void method(T generic, CompletableFuture<T> genericFuture, List<CompletableFuture<T>> listOfGenericFuture, T[] array) {
        }
    }

    public static class LongClass extends ClassBase<Long> {
    }

    public static class ClassBase2<T> {

        public void method(T generic, List<T> list) {
        }
    }

    public static class FutureClass extends ClassBase2<CompletableFuture<Long>> {
    }

    public static class MultiLocalVariableWithoutInitializationClass extends ClassBase<Long> {

        @Override
        public void method(Long generic, CompletableFuture<Long> genericFuture, List<CompletableFuture<Long>> listOfGenericFuture, Long[] array) {
            String localVariable;
            if (generic == null) {
                localVariable = "null";
            } else if (generic < 0) {
                localVariable = "negative";
            } else if (generic > 0) {
                localVariable = "positive";
            } else {
                localVariable = "zero";
            }
            System.err.println("localVariable: " + localVariable);
        }
    }

    public static class MultiLocalVariableBlocksWithInitializationClass extends ClassBase<Long> {

        @Override
        public void method(Long generic, CompletableFuture<Long> genericFuture, List<CompletableFuture<Long>> listOfGenericFuture, Long[] array) {
            String localVariable = "";
            if (generic == null) {
                localVariable = "null";
            } else if (generic < 0) {
                localVariable = "negative";
            } else if (generic > 0) {
                localVariable = "positive";
            } else {
                localVariable = "zero";
            }
            System.err.println("localVariable: " + localVariable);
        }
    }

    public static class ParameterNameShadowedClass {

        @SuppressWarnings("unused")
        public void method(Long generic, Object result, CompletableFuture<Long> genericFuture, List<CompletableFuture<Long>> listOfGenericFuture, Long[] array) {
        }
    }

    @Test
    void testAutoboxing_1() {
        assertThat(ExtractionUtils.isAssignable(int.class, Integer.class, Autoboxing.STRICT)).isTrue();
    }

    @Test
    void testAutoboxing_2() {
        assertThat(ExtractionUtils.isAssignable(Integer.class, int.class, Autoboxing.STRICT)).isFalse();
    }

    @Test
    void testAutoboxing_3() {
        assertThat(ExtractionUtils.isAssignable(Integer.class, int.class, Autoboxing.JVM)).isTrue();
    }

    @Test
    void testAutoboxing_4() {
        assertThat(ExtractionUtils.isAssignable(Integer.class, Number.class, Autoboxing.STRICT)).isTrue();
    }
}
