package org.apache.flink.connector.base.sink.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class DequeRequestBufferTest_Purified {

    private DequeRequestBuffer<String> bufferWrapper;

    @BeforeEach
    void setUp() {
        bufferWrapper = new DequeRequestBuffer<>();
    }

    @Test
    void shouldHandleEmptyBufferCorrectly_1() {
        assertThat(bufferWrapper.isEmpty()).isTrue();
    }

    @Test
    void shouldHandleEmptyBufferCorrectly_2() {
        assertThat(bufferWrapper.poll()).isNull();
    }

    @Test
    void shouldHandleEmptyBufferCorrectly_3() {
        assertThat(bufferWrapper.peek()).isNull();
    }

    @Test
    void shouldHandleEmptyBufferCorrectly_4() {
        assertThat(bufferWrapper.totalSizeInBytes()).isEqualTo(0);
    }
}
