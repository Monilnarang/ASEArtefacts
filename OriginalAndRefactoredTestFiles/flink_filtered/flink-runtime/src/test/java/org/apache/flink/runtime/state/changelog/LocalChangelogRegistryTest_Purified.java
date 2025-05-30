package org.apache.flink.runtime.state.changelog;

import org.apache.flink.runtime.state.TestingStreamStateHandle;
import org.apache.flink.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LocalChangelogRegistryTest_Purified {

    @Test
    void testRegistryNormal_1() {
        TestingStreamStateHandle handle1 = new TestingStreamStateHandle();
        localStateRegistry.register(handle1, 1);
        assertThat(handle1.isDisposed()).isTrue();
    }

    @Test
    void testRegistryNormal_2_testMerged_2() {
        TestingStreamStateHandle handle2 = new TestingStreamStateHandle();
        localStateRegistry.register(handle2, 1);
        localStateRegistry.register(handle2, 2);
        assertThat(handle2.isDisposed()).isFalse();
        assertThat(handle2.isDisposed()).isTrue();
    }

    @Test
    void testRegistryNormal_4() {
        TestingStreamStateHandle handle3 = new TestingStreamStateHandle();
        localStateRegistry.register(handle3, 2);
        assertThat(handle3.isDisposed()).isTrue();
    }
}
