package org.apache.flink.runtime.state.heap;

import org.apache.flink.api.common.typeutils.TypeSerializerSnapshot;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.common.typeutils.base.TypeSerializerSingleton;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.junit.jupiter.api.Test;
import javax.annotation.Nonnull;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SkipListKeyComparatorTest_Parameterized {

    private static final SkipListKeySerializer<Long, Integer> skipListKeySerializerForPrimitive = new SkipListKeySerializer<>(LongSerializer.INSTANCE, IntSerializer.INSTANCE);

    private static final SkipListKeySerializer<byte[], byte[]> skipListKeySerializerForByteArray = new SkipListKeySerializer<>(ByteArraySerializer.INSTANCE, ByteArraySerializer.INSTANCE);

    private static final SkipListKeySerializer<byte[], byte[]> skipListKeySerializerForNamespaceCompare = new SkipListKeySerializer<>(ByteArraySerializer.INSTANCE, ByteArraySerializer.INSTANCE);

    private int compareSkipListKeyOfByteArray(String key1, String namespace1, String key2, String namespace2) {
        return compareSkipListKey(skipListKeySerializerForByteArray, convertStringToByteArray(key1), convertStringToByteArray(namespace1), convertStringToByteArray(key2), convertStringToByteArray(namespace2));
    }

    private int compareSkipListKeyOfPrimitive(long key1, int namespace1, long key2, int namespace2) {
        return compareSkipListKey(skipListKeySerializerForPrimitive, key1, namespace1, key2, namespace2);
    }

    private <K, N> int compareSkipListKey(@Nonnull SkipListKeySerializer<K, N> keySerializer, K key1, N namespace1, K key2, N namespace2) {
        MemorySegment b1 = MemorySegmentFactory.wrap(keySerializer.serialize(key1, namespace1));
        MemorySegment b2 = MemorySegmentFactory.wrap(keySerializer.serialize(key2, namespace2));
        return SkipListKeyComparator.compareTo(b1, 0, b2, 0);
    }

    private int compareNamespace(String namespace, String targetNamespace) {
        final byte[] key = convertStringToByteArray("34");
        byte[] n = skipListKeySerializerForNamespaceCompare.serializeNamespace(convertStringToByteArray(namespace));
        byte[] k = skipListKeySerializerForNamespaceCompare.serialize(key, convertStringToByteArray(targetNamespace));
        return SkipListKeyComparator.compareNamespaceAndNode(MemorySegmentFactory.wrap(n), 0, n.length, MemorySegmentFactory.wrap(k), 0);
    }

    private byte[] convertStringToByteArray(@Nonnull String str) {
        String[] subStr = str.split(",");
        byte[] value = new byte[subStr.length];
        for (int i = 0; i < subStr.length; i++) {
            int v = Integer.valueOf(subStr[i]);
            value[i] = (byte) v;
        }
        return value;
    }

    private static class ByteArraySerializer extends TypeSerializerSingleton<byte[]> {

        private static final byte[] EMPTY = new byte[0];

        static final ByteArraySerializer INSTANCE = new ByteArraySerializer();

        @Override
        public boolean isImmutableType() {
            return false;
        }

        @Override
        public byte[] createInstance() {
            return EMPTY;
        }

        @Override
        public byte[] copy(byte[] from) {
            byte[] copy = new byte[from.length];
            System.arraycopy(from, 0, copy, 0, from.length);
            return copy;
        }

        @Override
        public byte[] copy(byte[] from, byte[] reuse) {
            return copy(from);
        }

        @Override
        public int getLength() {
            return -1;
        }

        @Override
        public void serialize(byte[] record, DataOutputView target) throws IOException {
            if (record == null) {
                throw new IllegalArgumentException("The record must not be null.");
            }
            target.write(record);
        }

        @Override
        public byte[] deserialize(DataInputView source) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte[] deserialize(byte[] reuse, DataInputView source) throws IOException {
            return deserialize(source);
        }

        @Override
        public void copy(DataInputView source, DataOutputView target) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TypeSerializerSnapshot<byte[]> snapshotConfiguration() {
            throw new UnsupportedOperationException();
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_testPrimitiveDiffKeyAndEqualNamespace_1_1_1_3")
    void testPrimitiveDiffKeyAndEqualNamespace_1_1_1_3(int param1, long param2, int param3, long param4, int param5) {
        assertThat(compareSkipListKeyOfPrimitive(param2, param3, param4, param5)).isLessThan(param1);
    }

    static public Stream<Arguments> Provider_testPrimitiveDiffKeyAndEqualNamespace_1_1_1_3() {
        return Stream.of(arguments(0, 0L, 5, 1L, 5), arguments(0, 8374L, 2, 8374L, 3), arguments(0, 1L, 2, 3L, 4), arguments(0, 3L, 2, 1L, 4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPrimitiveDiffKeyAndEqualNamespace_2_2_2_4")
    void testPrimitiveDiffKeyAndEqualNamespace_2_2_2_4(int param1, long param2, int param3, long param4, int param5) {
        assertThat(compareSkipListKeyOfPrimitive(param2, param3, param4, param5)).isGreaterThan(param1);
    }

    static public Stream<Arguments> Provider_testPrimitiveDiffKeyAndEqualNamespace_2_2_2_4() {
        return Stream.of(arguments(0, 192L, 90, 87L, 90), arguments(0, 839L, 3, 839L, 2), arguments(0, 1L, 4, 3L, 2), arguments(0, 3L, 4, 1L, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testByteArrayEqualKeyAndLargerNamespace_1_1to2_2to3_3to4_4to5_5")
    void testByteArrayEqualKeyAndLargerNamespace_1_1to2_2to3_3to4_4to5_5(int param1, int param2, int param3, int param4, int param5) {
        assertThat(compareSkipListKeyOfByteArray(param2, param3, param4, param5)).isGreaterThan(param1);
    }

    static public Stream<Arguments> Provider_testByteArrayEqualKeyAndLargerNamespace_1_1to2_2to3_3to4_4to5_5() {
        return Stream.of(arguments(0, 34, 27, 34, 25), arguments(0, 34, 27, 34, "25,34"), arguments(0, 34, "27,28", 34, 25), arguments(0, 34, "27,28", 34, "25,34"), arguments(0, 34, "27,28", 34, "27,3"), arguments(0, 34, 25, 30, 25), arguments(0, 34, 25, "30,38", 25), arguments(0, "34,22", 25, 30, 25), arguments(0, "34,22", 25, "30,38", 25), arguments(0, "34,82", 25, "34,38", 25));
    }

    @ParameterizedTest
    @MethodSource("Provider_testByteArrayEqualKeyAndSmallerNamespace_1_1to2_2to3_3to4_4to5_5")
    void testByteArrayEqualKeyAndSmallerNamespace_1_1to2_2to3_3to4_4to5_5(int param1, int param2, int param3, int param4, int param5) {
        assertThat(compareSkipListKeyOfByteArray(param2, param3, param4, param5)).isLessThan(param1);
    }

    static public Stream<Arguments> Provider_testByteArrayEqualKeyAndSmallerNamespace_1_1to2_2to3_3to4_4to5_5() {
        return Stream.of(arguments(0, 34, 25, 34, 27), arguments(0, 34, 25, 34, "27,34"), arguments(0, 34, "25,28", 34, 27), arguments(0, 34, "25,28", 34, "27,34"), arguments(0, 34, "25,28", 34, "25,34"), arguments(0, 30, 25, 34, 25), arguments(0, "30,38", 25, 34, 25), arguments(0, 30, 25, "34,22", 25), arguments(0, "30,38", 25, "34,22", 25), arguments(0, "30,38", 25, "30,72", 25));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSmallerNamespace_1to5")
    void testSmallerNamespace_1to5(int param1, int param2, int param3) {
        assertThat(compareNamespace(param2, param3)).isLessThan(param1);
    }

    static public Stream<Arguments> Provider_testSmallerNamespace_1to5() {
        return Stream.of(arguments(0, 23, 24), arguments(0, 23, "24,35"), arguments(0, "23,25", 24), arguments(0, "23,20", "24,45"), arguments(0, "23,20", "23,45"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLargerNamespace_1to5")
    void testLargerNamespace_1to5(int param1, int param2, int param3) {
        assertThat(compareNamespace(param2, param3)).isGreaterThan(param1);
    }

    static public Stream<Arguments> Provider_testLargerNamespace_1to5() {
        return Stream.of(arguments(0, 26, 14), arguments(0, 26, "14,73"), arguments(0, "26,25", 14), arguments(0, "26,20", "14,45"), arguments(0, "26,90", "26,45"));
    }
}
