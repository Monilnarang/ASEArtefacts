package org.apache.flink.core.memory;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ByteArrayInputStreamWithPosTest_Purified {

    private final byte[] data = new byte[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private final ByteArrayInputStreamWithPos stream = new ByteArrayInputStreamWithPos(data);

    private static int drainStream(ByteArrayInputStreamWithPos stream) {
        int skipped = 0;
        while (stream.read() != -1) {
            skipped++;
        }
        return skipped;
    }

    @Test
    void testSetPosition_1() throws Exception {
        assertThat(stream.available()).isEqualTo(data.length);
    }

    @Test
    void testSetPosition_2() throws Exception {
        assertThat(stream.read()).isEqualTo('0');
    }

    @Test
    void testSetPosition_3_testMerged_3() throws Exception {
        stream.setPosition(1);
        assertThat(stream.available()).isEqualTo(data.length - 1);
        assertThat(stream.read()).isEqualTo('1');
        stream.setPosition(3);
        assertThat(stream.available()).isEqualTo(data.length - 3);
        assertThat(stream.read()).isEqualTo('3');
        stream.setPosition(data.length);
        assertThat(stream.available()).isZero();
        assertThat(stream.read()).isEqualTo(-1);
    }
}
