package org.apache.flink.runtime.state;

import org.apache.flink.api.common.state.StateDescriptor;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.DoubleSerializer;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.core.memory.ByteArrayInputStreamWithPos;
import org.apache.flink.core.memory.ByteArrayOutputStreamWithPos;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.apache.flink.runtime.state.metainfo.StateMetaInfoReader;
import org.apache.flink.runtime.state.metainfo.StateMetaInfoSnapshot;
import org.apache.flink.runtime.state.metainfo.StateMetaInfoSnapshotReadersWriters;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.apache.flink.runtime.state.metainfo.StateMetaInfoSnapshotReadersWriters.CURRENT_STATE_META_INFO_SNAPSHOT_VERSION;
import static org.assertj.core.api.Assertions.assertThat;

class SerializationProxiesTest_Purified {

    private void assertEqualStateMetaInfoSnapshotsLists(List<StateMetaInfoSnapshot> expected, List<StateMetaInfoSnapshot> actual) {
        assertThat(actual).hasSameSizeAs(expected);
        for (int i = 0; i < expected.size(); ++i) {
            assertEqualStateMetaInfoSnapshots(expected.get(i), actual.get(i));
        }
    }

    private void assertEqualStateMetaInfoSnapshots(StateMetaInfoSnapshot expected, StateMetaInfoSnapshot actual) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getBackendStateType()).isEqualTo(expected.getBackendStateType());
        assertThat(actual.getOptionsImmutable()).isEqualTo(expected.getOptionsImmutable());
        assertThat(actual.getSerializerSnapshotsImmutable()).isEqualTo(expected.getSerializerSnapshotsImmutable());
    }

    @Test
    void testFixTypeOrder_1() {
        assertThat(StateDescriptor.Type.values()).hasSize(7);
    }

    @Test
    void testFixTypeOrder_2() {
        assertThat(StateDescriptor.Type.UNKNOWN.ordinal()).isZero();
    }

    @Test
    void testFixTypeOrder_3() {
        assertThat(StateDescriptor.Type.VALUE.ordinal()).isOne();
    }

    @Test
    void testFixTypeOrder_4() {
        assertThat(StateDescriptor.Type.LIST.ordinal()).isEqualTo(2);
    }

    @Test
    void testFixTypeOrder_5() {
        assertThat(StateDescriptor.Type.REDUCING.ordinal()).isEqualTo(3);
    }

    @Test
    void testFixTypeOrder_6() {
        assertThat(StateDescriptor.Type.FOLDING.ordinal()).isEqualTo(4);
    }

    @Test
    void testFixTypeOrder_7() {
        assertThat(StateDescriptor.Type.AGGREGATING.ordinal()).isEqualTo(5);
    }

    @Test
    void testFixTypeOrder_8() {
        assertThat(StateDescriptor.Type.MAP.ordinal()).isEqualTo(6);
    }
}
