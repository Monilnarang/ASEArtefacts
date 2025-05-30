package org.apache.flink.streaming.runtime.streamrecord;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StreamRecordTest_Purified {

    @Test
    void testAllowedTimestampRange_1() {
        assertThat(new StreamRecord<>("test", 0).getTimestamp()).isZero();
    }

    @Test
    void testAllowedTimestampRange_2() {
        assertThat(new StreamRecord<>("test", -1).getTimestamp()).isEqualTo(-1L);
    }

    @Test
    void testAllowedTimestampRange_3() {
        assertThat(new StreamRecord<>("test", 1).getTimestamp()).isOne();
    }

    @Test
    void testAllowedTimestampRange_4() {
        assertThat(new StreamRecord<>("test", Long.MIN_VALUE).getTimestamp()).isEqualTo(Long.MIN_VALUE);
    }

    @Test
    void testAllowedTimestampRange_5() {
        assertThat(new StreamRecord<>("test", Long.MAX_VALUE).getTimestamp()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void testReplacePreservesTimestamp_1() {
        StreamRecord<String> recNoTimestamp = new StreamRecord<>("o sole mio");
        StreamRecord<Integer> newRecNoTimestamp = recNoTimestamp.replace(17);
        assertThat(newRecNoTimestamp.hasTimestamp()).isFalse();
    }

    @Test
    void testReplacePreservesTimestamp_2_testMerged_2() {
        StreamRecord<String> recWithTimestamp = new StreamRecord<>("la dolce vita", 99);
        StreamRecord<Integer> newRecWithTimestamp = recWithTimestamp.replace(17);
        assertThat(newRecWithTimestamp.hasTimestamp()).isTrue();
        assertThat(newRecWithTimestamp.getTimestamp()).isEqualTo(99L);
    }

    @Test
    void testCopy_1() {
        StreamRecord<String> recNoTimestamp = new StreamRecord<>("test");
        StreamRecord<String> recNoTimestampCopy = recNoTimestamp.copy("test");
        assertThat(recNoTimestampCopy).isEqualTo(recNoTimestamp);
    }

    @Test
    void testCopy_2() {
        StreamRecord<String> recWithTimestamp = new StreamRecord<>("test", 99);
        StreamRecord<String> recWithTimestampCopy = recWithTimestamp.copy("test");
        assertThat(recWithTimestampCopy).isEqualTo(recWithTimestamp);
    }

    @Test
    void testCopyTo_1() {
        StreamRecord<String> recNoTimestamp = new StreamRecord<>("test");
        StreamRecord<String> recNoTimestampCopy = new StreamRecord<>(null);
        recNoTimestamp.copyTo("test", recNoTimestampCopy);
        assertThat(recNoTimestampCopy).isEqualTo(recNoTimestamp);
    }

    @Test
    void testCopyTo_2() {
        StreamRecord<String> recWithTimestamp = new StreamRecord<>("test", 99);
        StreamRecord<String> recWithTimestampCopy = new StreamRecord<>(null);
        recWithTimestamp.copyTo("test", recWithTimestampCopy);
        assertThat(recWithTimestampCopy).isEqualTo(recWithTimestamp);
    }
}
