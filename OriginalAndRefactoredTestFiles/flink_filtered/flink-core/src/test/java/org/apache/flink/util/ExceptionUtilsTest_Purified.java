package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionUtilsTest_Purified {

    @Test
    void testJvmFatalError_1() {
        assertThat(ExceptionUtils.isJvmFatalError(new Error())).isFalse();
    }

    @Test
    void testJvmFatalError_2() {
        assertThat(ExceptionUtils.isJvmFatalError(new LinkageError())).isFalse();
    }

    @Test
    void testJvmFatalError_3() {
        assertThat(ExceptionUtils.isJvmFatalError(new InternalError())).isTrue();
    }

    @Test
    void testJvmFatalError_4() {
        assertThat(ExceptionUtils.isJvmFatalError(new UnknownError())).isTrue();
    }
}
