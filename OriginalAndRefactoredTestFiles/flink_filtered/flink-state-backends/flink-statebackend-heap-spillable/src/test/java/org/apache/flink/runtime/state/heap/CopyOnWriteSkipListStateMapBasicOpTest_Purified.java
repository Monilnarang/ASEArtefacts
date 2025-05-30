package org.apache.flink.runtime.state.heap;

import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.state.StateEntry;
import org.apache.flink.runtime.state.StateTransformationFunction;
import org.apache.flink.runtime.state.heap.space.Allocator;
import org.apache.flink.runtime.state.heap.space.Chunk;
import org.apache.flink.runtime.state.internal.InternalKvState;
import org.apache.flink.util.FlinkRuntimeException;
import org.apache.flink.util.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import static org.apache.flink.runtime.state.heap.CopyOnWriteSkipListStateMapTestUtils.addToReferenceState;
import static org.apache.flink.runtime.state.heap.CopyOnWriteSkipListStateMapTestUtils.createEmptyStateMap;
import static org.apache.flink.runtime.state.heap.CopyOnWriteSkipListStateMapTestUtils.removeFromReferenceState;
import static org.apache.flink.runtime.state.heap.CopyOnWriteSkipListStateMapTestUtils.verifyState;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.Matchers.contains;

class CopyOnWriteSkipListStateMapBasicOpTest_Purified {

    private final long namespace = 1L;

    private TestAllocator allocator;

    private CopyOnWriteSkipListStateMap<Integer, Long, String> stateMap;

    @BeforeEach
    void setUp() {
        allocator = new TestAllocator(256);
        stateMap = createEmptyStateMap(0, 0.0f, allocator);
    }

    @AfterEach
    void tearDown() {
        stateMap.close();
        IOUtils.closeQuietly(allocator);
    }

    private int putAndVerify(int key, String state, int initTotalSize, boolean isNewKey) {
        stateMap.put(key, namespace, state);
        int totalSize = isNewKey ? initTotalSize + 1 : initTotalSize;
        assertThat(stateMap.get(key, namespace)).isEqualTo(state);
        assertThat(stateMap.size()).isEqualTo(totalSize);
        assertThat(stateMap.totalSize()).isEqualTo(totalSize);
        assertThat(allocator.getTotalSpaceNumber()).isEqualTo(totalSize * 2);
        return totalSize;
    }

    private int putAndGetOldVerify(int key, String state, int initTotalSize) {
        int totalSize = initTotalSize + 1;
        String oldState = stateMap.get(key, namespace);
        assertThat(stateMap.putAndGetOld(key, namespace, state)).isEqualTo(oldState);
        assertThat(stateMap.get(key, namespace)).isEqualTo(state);
        assertThat(stateMap.size()).isEqualTo(totalSize);
        assertThat(stateMap.totalSize()).isEqualTo(totalSize);
        assertThat(allocator.getTotalSpaceNumber()).isEqualTo(totalSize * 2);
        return totalSize;
    }

    private int removeAndVerify(int key, int initTotalSize, boolean keyExists) {
        stateMap.remove(key, namespace);
        int totalSize = keyExists ? initTotalSize - 1 : initTotalSize;
        assertThat(stateMap.get(key, namespace)).isNull();
        assertThat(stateMap.size()).isEqualTo(totalSize);
        assertThat(stateMap.totalSize()).isEqualTo(totalSize);
        assertThat(allocator.getTotalSpaceNumber()).isEqualTo(totalSize * 2);
        return totalSize;
    }

    private int removeAndGetOldVerify(int key, int initTotalSize) {
        int totalSize = initTotalSize - 1;
        String oldState = stateMap.get(key, namespace);
        assertThat(stateMap.removeAndGetOld(key, namespace)).isEqualTo(oldState);
        assertThat(stateMap.get(key, namespace)).isNull();
        assertThat(stateMap.size()).isEqualTo(totalSize);
        assertThat(stateMap.totalSize()).isEqualTo(totalSize);
        assertThat(allocator.getTotalSpaceNumber()).isEqualTo(totalSize * 2);
        return totalSize;
    }

    @Test
    void testInitStateMap_1() {
        assertThat(stateMap.isEmpty()).isTrue();
    }

    @Test
    void testInitStateMap_2() {
        assertThat(stateMap.size()).isEqualTo(0);
    }

    @Test
    void testInitStateMap_3() {
        assertThat(stateMap.totalSize()).isEqualTo(0);
    }

    @Test
    void testInitStateMap_4() {
        assertThat(stateMap.getRequestCount()).isEqualTo(0);
    }

    @Test
    void testInitStateMap_5() {
        assertThat(stateMap.getLogicallyRemovedNodes()).isEmpty();
    }

    @Test
    void testInitStateMap_6() {
        assertThat(stateMap.getHighestRequiredSnapshotVersionPlusOne()).isEqualTo(0);
    }

    @Test
    void testInitStateMap_7() {
        assertThat(stateMap.getHighestFinishedSnapshotVersion()).isEqualTo(0);
    }

    @Test
    void testInitStateMap_8() {
        assertThat(stateMap.getSnapshotVersions()).isEmpty();
    }

    @Test
    void testInitStateMap_9() {
        assertThat(stateMap.getPruningValueNodes()).isEmpty();
    }

    @Test
    void testInitStateMap_10() {
        assertThat(stateMap.getResourceGuard().getLeaseCount()).isEqualTo(0);
    }

    @Test
    void testInitStateMap_11() {
        assertThat(stateMap.getResourceGuard().isClosed()).isFalse();
    }

    @Test
    void testInitStateMap_12() {
        assertThat(stateMap.isClosed()).isFalse();
    }

    @Test
    void testInitStateMap_13() {
        assertThat(stateMap.get(0, 0L)).isNull();
    }

    @Test
    void testInitStateMap_14() {
        assertThat(stateMap.containsKey(1, 2L)).isFalse();
    }

    @Test
    void testInitStateMap_15() {
        assertThat(stateMap.removeAndGetOld(3, 4L)).isNull();
    }

    @Test
    void testInitStateMap_16() {
        assertThat(stateMap.getKeys(-92L).iterator().hasNext()).isFalse();
    }

    @Test
    void testInitStateMap_17() {
        assertThat(stateMap.sizeOfNamespace(8L)).isEqualTo(0);
    }

    @Test
    void testInitStateMap_18() {
        assertThat(stateMap.iterator().hasNext()).isFalse();
    }

    @Test
    void testInitStateMap_19() {
        assertThat(stateMap.getStateIncrementalVisitor(100).hasNext()).isFalse();
    }
}
