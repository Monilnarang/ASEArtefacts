package org.apache.flink.runtime.state;

import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.core.memory.ByteArrayInputStreamWithPos;
import org.apache.flink.core.memory.ByteArrayOutputStreamWithPos;
import org.apache.flink.core.memory.DataInputDeserializer;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputSerializer;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CompositeKeySerializationUtilsTest_Purified {

    @Test
    void testIsAmbiguousKeyPossible_1() {
        assertThat(CompositeKeySerializationUtils.isAmbiguousKeyPossible(IntSerializer.INSTANCE, IntSerializer.INSTANCE)).isFalse();
    }

    @Test
    void testIsAmbiguousKeyPossible_2() {
        assertThat(CompositeKeySerializationUtils.isAmbiguousKeyPossible(StringSerializer.INSTANCE, StringSerializer.INSTANCE)).isTrue();
    }
}
