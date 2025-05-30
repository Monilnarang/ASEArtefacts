package org.apache.flink.runtime.state.metainfo;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StateMetaInfoSnapshotEnumConstantsTest_Purified {

    @Test
    void testFixedBackendStateTypeEnumConstants_1() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.values()).hasSize(5);
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_2() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.KEY_VALUE.ordinal()).isZero();
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_3() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.OPERATOR.ordinal()).isOne();
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_4() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.BROADCAST.ordinal()).isEqualTo(2);
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_5() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.PRIORITY_QUEUE.ordinal()).isEqualTo(3);
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_6() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.KEY_VALUE.toString()).isEqualTo("KEY_VALUE");
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_7() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.KEY_VALUE_V2.toString()).isEqualTo("KEY_VALUE_V2");
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_8() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.OPERATOR.toString()).isEqualTo("OPERATOR");
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_9() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.BROADCAST.toString()).isEqualTo("BROADCAST");
    }

    @Test
    void testFixedBackendStateTypeEnumConstants_10() {
        assertThat(StateMetaInfoSnapshot.BackendStateType.PRIORITY_QUEUE.toString()).isEqualTo("PRIORITY_QUEUE");
    }

    @Test
    void testFixedOptionsEnumConstants_1() {
        assertThat(StateMetaInfoSnapshot.CommonOptionsKeys.values()).hasSize(2);
    }

    @Test
    void testFixedOptionsEnumConstants_2() {
        assertThat(StateMetaInfoSnapshot.CommonOptionsKeys.KEYED_STATE_TYPE.ordinal()).isZero();
    }

    @Test
    void testFixedOptionsEnumConstants_3() {
        assertThat(StateMetaInfoSnapshot.CommonOptionsKeys.OPERATOR_STATE_DISTRIBUTION_MODE.ordinal()).isOne();
    }

    @Test
    void testFixedOptionsEnumConstants_4() {
        assertThat(StateMetaInfoSnapshot.CommonOptionsKeys.KEYED_STATE_TYPE.toString()).isEqualTo("KEYED_STATE_TYPE");
    }

    @Test
    void testFixedOptionsEnumConstants_5() {
        assertThat(StateMetaInfoSnapshot.CommonOptionsKeys.OPERATOR_STATE_DISTRIBUTION_MODE.toString()).isEqualTo("OPERATOR_STATE_DISTRIBUTION_MODE");
    }

    @Test
    void testFixedSerializerEnumConstants_1() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.values()).hasSize(4);
    }

    @Test
    void testFixedSerializerEnumConstants_2() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.KEY_SERIALIZER.ordinal()).isZero();
    }

    @Test
    void testFixedSerializerEnumConstants_3() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.NAMESPACE_SERIALIZER.ordinal()).isOne();
    }

    @Test
    void testFixedSerializerEnumConstants_4() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.VALUE_SERIALIZER.ordinal()).isEqualTo(2);
    }

    @Test
    void testFixedSerializerEnumConstants_5() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.KEY_SERIALIZER.toString()).isEqualTo("KEY_SERIALIZER");
    }

    @Test
    void testFixedSerializerEnumConstants_6() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.NAMESPACE_SERIALIZER.toString()).isEqualTo("NAMESPACE_SERIALIZER");
    }

    @Test
    void testFixedSerializerEnumConstants_7() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.VALUE_SERIALIZER.toString()).isEqualTo("VALUE_SERIALIZER");
    }

    @Test
    void testFixedSerializerEnumConstants_8() {
        assertThat(StateMetaInfoSnapshot.CommonSerializerKeys.USER_KEY_SERIALIZER.toString()).isEqualTo("USER_KEY_SERIALIZER");
    }
}
