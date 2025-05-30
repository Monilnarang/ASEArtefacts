package org.apache.flink.runtime.throwable;

import org.apache.flink.runtime.execution.SuppressRestartsException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ThrowableClassifierTest_Purified {

    @ThrowableAnnotation(ThrowableType.PartitionDataMissingError)
    private static class TestPartitionDataMissingErrorException extends Exception {
    }

    @ThrowableAnnotation(ThrowableType.EnvironmentError)
    private static class TestEnvironmentErrorException extends Exception {
    }

    @ThrowableAnnotation(ThrowableType.RecoverableError)
    private static class TestRecoverableErrorException extends Exception {
    }

    private static class TestPartitionDataMissingErrorSubException extends TestPartitionDataMissingErrorException {
    }

    private static class TestRecoverableFailureSubException extends TestRecoverableErrorException {
    }

    @Test
    void testThrowableType_Recoverable_1() {
        assertThat(ThrowableClassifier.getThrowableType(new Exception(""))).isEqualTo(ThrowableType.RecoverableError);
    }

    @Test
    void testThrowableType_Recoverable_2() {
        assertThat(ThrowableClassifier.getThrowableType(new TestRecoverableErrorException())).isEqualTo(ThrowableType.RecoverableError);
    }

    @Test
    void testFindThrowableOfThrowableType_1() {
        assertThat(ThrowableClassifier.findThrowableOfThrowableType(new Exception(), ThrowableType.RecoverableError)).isNotPresent();
    }

    @Test
    void testFindThrowableOfThrowableType_2() {
        assertThat(ThrowableClassifier.findThrowableOfThrowableType(new TestPartitionDataMissingErrorException(), ThrowableType.RecoverableError)).isNotPresent();
    }

    @Test
    void testFindThrowableOfThrowableType_3() {
        assertThat(ThrowableClassifier.findThrowableOfThrowableType(new TestRecoverableErrorException(), ThrowableType.RecoverableError)).isPresent();
    }

    @Test
    void testFindThrowableOfThrowableType_4() {
        assertThat(ThrowableClassifier.findThrowableOfThrowableType(new Exception(new TestRecoverableErrorException()), ThrowableType.RecoverableError)).isPresent();
    }

    @Test
    void testFindThrowableOfThrowableType_5() {
        assertThat(ThrowableClassifier.findThrowableOfThrowableType(new TestRecoverableFailureSubException(), ThrowableType.RecoverableError)).isPresent();
    }
}
