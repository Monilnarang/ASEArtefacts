package org.apache.druid.timeline.partition;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.timeline.partition.OvershadowableManager.RootPartitionRange;
import org.apache.druid.timeline.partition.OvershadowableManager.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OvershadowableManagerTest_Purified {

    private static final String MAJOR_VERSION = "version";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OvershadowableManager<OvershadowableInteger> manager;

    private int nextRootPartitionId;

    private int nextNonRootPartitionId;

    private List<PartitionChunk<OvershadowableInteger>> expectedVisibleChunks;

    private List<PartitionChunk<OvershadowableInteger>> expectedOvershadowedChunks;

    private List<PartitionChunk<OvershadowableInteger>> expectedStandbyChunks;

    @Before
    public void setup() {
        manager = new OvershadowableManager<>();
        nextRootPartitionId = PartitionIds.ROOT_GEN_START_PARTITION_ID;
        nextNonRootPartitionId = PartitionIds.NON_ROOT_GEN_START_PARTITION_ID;
        expectedVisibleChunks = new ArrayList<>();
        expectedOvershadowedChunks = new ArrayList<>();
        expectedStandbyChunks = new ArrayList<>();
    }

    private boolean addVisibleToManager(PartitionChunk<OvershadowableInteger> chunk) {
        expectedVisibleChunks.add(chunk);
        return manager.addChunk(chunk);
    }

    private PartitionChunk<OvershadowableInteger> removeVisibleFromManager(PartitionChunk<OvershadowableInteger> chunk) {
        expectedVisibleChunks.remove(chunk);
        return manager.removeChunk(chunk);
    }

    private void assertManagerState() {
        Assert.assertEquals("Mismatched visible chunks", new HashSet<>(expectedVisibleChunks), Sets.newHashSet(manager.visibleChunksIterator()));
        Assert.assertEquals("Mismatched overshadowed chunks", new HashSet<>(expectedOvershadowedChunks), new HashSet<>(manager.getOvershadowedChunks()));
        Assert.assertEquals("Mismatched standby chunks", new HashSet<>(expectedStandbyChunks), new HashSet<>(manager.getStandbyChunks()));
    }

    private List<PartitionChunk<OvershadowableInteger>> newNonRootChunks(int n, int startPartitionId, int endPartitionId, int minorVersion, int atomicUpdateGroupSize) {
        return IntStream.range(0, n).mapToObj(i -> newNonRootChunk(startPartitionId, endPartitionId, minorVersion, atomicUpdateGroupSize)).collect(Collectors.toList());
    }

    private NumberedPartitionChunk<OvershadowableInteger> newRootChunk() {
        final int partitionId = nextRootPartitionId();
        return new NumberedPartitionChunk<>(partitionId, 0, new OvershadowableInteger(MAJOR_VERSION, partitionId, 0));
    }

    private NumberedOverwritingPartitionChunk<OvershadowableInteger> newNonRootChunk(int startRootPartitionId, int endRootPartitionId, int minorVersion, int atomicUpdateGroupSize) {
        final int partitionId = nextNonRootPartitionId();
        return new NumberedOverwritingPartitionChunk<>(partitionId, new OvershadowableInteger(MAJOR_VERSION, partitionId, 0, startRootPartitionId, endRootPartitionId, minorVersion, atomicUpdateGroupSize));
    }

    private int nextRootPartitionId() {
        return nextRootPartitionId++;
    }

    private int nextNonRootPartitionId() {
        return nextNonRootPartitionId++;
    }

    @Test
    public void testAddRootChunkToEmptyManager_1() {
        Assert.assertTrue(manager.isEmpty());
    }

    @Test
    public void testAddRootChunkToEmptyManager_2_testMerged_2() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        Assert.assertFalse(manager.addChunk(chunk));
    }

    @Test
    public void testAddRootChunkToEmptyManager_3() {
        assertManagerState();
    }

    @Test
    public void testAddRootChunkToEmptyManager_4_testMerged_4() {
        Assert.assertTrue(manager.isComplete());
    }

    @Test
    public void testAddRootChunkToEmptyManager_7() {
        assertManagerState();
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_1() {
        Assert.assertTrue(manager.isEmpty());
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_2_testMerged_2() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(10, 12, 1, 3);
        Assert.assertTrue(addVisibleToManager(chunk));
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_3() {
        assertManagerState();
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_4_testMerged_4() {
        Assert.assertFalse(manager.isComplete());
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_6() {
        assertManagerState();
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_9() {
        assertManagerState();
    }

    @Test
    public void testAddNonRootChunkToEmptyManager_10() {
        Assert.assertTrue(manager.isComplete());
    }

    @Test
    public void testRemoveFromEmptyManager_1() {
        Assert.assertTrue(manager.isEmpty());
    }

    @Test
    public void testRemoveFromEmptyManager_2() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertNull(manager.removeChunk(chunk));
    }

    @Test
    public void testAddOvershadowedChunkToCompletePartition_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(0, 3, 1, 2);
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newNonRootChunk(0, 3, 1, 2);
        chunk = newRootChunk();
        Assert.assertTrue(manager.addChunk(chunk));
    }

    @Test
    public void testAddOvershadowedChunkToCompletePartition_2() {
        assertManagerState();
    }

    @Test
    public void testAddOvershadowedChunkToCompletePartition_4() {
        assertManagerState();
    }

    @Test
    public void testAddOvershadowedChunkToCompletePartition_6() {
        assertManagerState();
    }

    @Test
    public void testAddOvershadowedChunkToIncompletePartition_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(0, 3, 1, 2);
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newRootChunk();
        expectedOvershadowedChunks.add(chunk);
        Assert.assertTrue(manager.addChunk(chunk));
    }

    @Test
    public void testAddOvershadowedChunkToIncompletePartition_2() {
        assertManagerState();
    }

    @Test
    public void testAddOvershadowedChunkToIncompletePartition_4() {
        assertManagerState();
    }

    @Test
    public void testAddStandbyChunksToIncompletePartition_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(0, 3, 1, 2);
        Assert.assertTrue(addVisibleToManager(chunk));
    }

    @Test
    public void testAddStandbyChunksToIncompletePartition_2() {
        assertManagerState();
    }

    @Test
    public void testAddStandbyChunksToIncompletePartition_4() {
        assertManagerState();
    }

    @Test
    public void testAddStandbyChunksToIncompletePartition_6() {
        assertManagerState();
    }

    @Test
    public void testRemoveUnknownChunk_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newRootChunk();
        Assert.assertNull(manager.removeChunk(chunk));
    }

    @Test
    public void testRemoveUnknownChunk_2() {
        assertManagerState();
    }

    @Test
    public void testRemoveUnknownChunk_4() {
        assertManagerState();
    }

    @Test
    public void testRemoveVisibleChunkAndFallBackToStandby_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newRootChunk();
        chunk = newNonRootChunk(0, 2, 1, 3);
        expectedStandbyChunks.add(chunk);
        Assert.assertTrue(manager.addChunk(chunk));
        chunk = expectedVisibleChunks.remove(0);
        Assert.assertEquals(chunk, manager.removeChunk(chunk));
    }

    @Test
    public void testRemoveVisibleChunkAndFallBackToStandby_2() {
        assertManagerState();
    }

    @Test
    public void testRemoveVisibleChunkAndFallBackToStandby_4() {
        assertManagerState();
    }

    @Test
    public void testRemoveVisibleChunkAndFallBackToStandby_6() {
        assertManagerState();
    }

    @Test
    public void testRemoveVisibleChunkAndFallBackToStandby_8() {
        assertManagerState();
    }

    @Test
    public void testRemoveVisibleChunkAndFallBackToStandby_10() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToInCompletePartition_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(0, 2, 1, 3);
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newNonRootChunk(0, 2, 1, 3);
        chunk = newRootChunk();
        expectedOvershadowedChunks.add(chunk);
        Assert.assertTrue(manager.addChunk(chunk));
    }

    @Test
    public void testAddCompleteOvershadowedToInCompletePartition_2() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToInCompletePartition_4() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToInCompletePartition_6() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToInCompletePartition_8() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToCompletePartition_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(0, 2, 1, 2);
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newNonRootChunk(0, 2, 1, 2);
        chunk = newRootChunk();
        expectedOvershadowedChunks.add(chunk);
        Assert.assertTrue(manager.addChunk(chunk));
    }

    @Test
    public void testAddCompleteOvershadowedToCompletePartition_2() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToCompletePartition_4() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToCompletePartition_6() {
        assertManagerState();
    }

    @Test
    public void testAddCompleteOvershadowedToCompletePartition_8() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromOvershadowd_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newNonRootChunk(0, 2, 1, 2);
        expectedOvershadowedChunks.add(expectedVisibleChunks.remove(0));
        chunk = expectedOvershadowedChunks.remove(0);
        Assert.assertEquals(chunk, manager.removeChunk(chunk));
    }

    @Test
    public void testRemoveChunkFromOvershadowd_2() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromOvershadowd_4() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromOvershadowd_6() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompleteParition_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newNonRootChunk(0, 2, 1, 2);
        expectedOvershadowedChunks.add(expectedVisibleChunks.remove(0));
        chunk = expectedVisibleChunks.remove(0);
        Assert.assertEquals(chunk, manager.removeChunk(chunk));
    }

    @Test
    public void testRemoveChunkFromCompleteParition_2() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompleteParition_4() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompleteParition_6() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompleteParition_8() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompleteParition_10() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompletePartitionFallBackToOvershadowed_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newRootChunk();
        chunk = newNonRootChunk(0, 2, 1, 2);
        expectedStandbyChunks.add(chunk);
        Assert.assertTrue(manager.addChunk(chunk));
        expectedOvershadowedChunks.addAll(expectedVisibleChunks);
        expectedVisibleChunks.clear();
        expectedVisibleChunks.add(expectedStandbyChunks.remove(0));
        chunk = expectedVisibleChunks.remove(0);
        Assert.assertEquals(chunk, manager.removeChunk(chunk));
    }

    @Test
    public void testRemoveChunkFromCompletePartitionFallBackToOvershadowed_2() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompletePartitionFallBackToOvershadowed_4() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompletePartitionFallBackToOvershadowed_6() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompletePartitionFallBackToOvershadowed_8() {
        assertManagerState();
    }

    @Test
    public void testRemoveChunkFromCompletePartitionFallBackToOvershadowed_10() {
        assertManagerState();
    }

    @Test
    public void testFallBackToStandbyOnRemove_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newRootChunk();
        Assert.assertTrue(addVisibleToManager(chunk));
        chunk = newNonRootChunk(0, 1, 1, 3);
        expectedStandbyChunks.add(chunk);
        Assert.assertTrue(manager.addChunk(chunk));
        chunk = newNonRootChunk(0, 1, 2, 2);
        chunk = expectedVisibleChunks.remove(0);
        Assert.assertEquals(chunk, manager.removeChunk(chunk));
    }

    @Test
    public void testFallBackToStandbyOnRemove_2() {
        assertManagerState();
    }

    @Test
    public void testFallBackToStandbyOnRemove_4() {
        assertManagerState();
    }

    @Test
    public void testFallBackToStandbyOnRemove_6() {
        assertManagerState();
    }

    @Test
    public void testFallBackToStandbyOnRemove_8() {
        assertManagerState();
    }

    @Test
    public void testAddIncompleteAtomicUpdateGroups_1_testMerged_1() {
        PartitionChunk<OvershadowableInteger> chunk = newNonRootChunk(0, 1, 1, 3);
        Assert.assertTrue(addVisibleToManager(chunk));
    }

    @Test
    public void testAddIncompleteAtomicUpdateGroups_2() {
        assertManagerState();
    }

    @Test
    public void testAddIncompleteAtomicUpdateGroups_4() {
        assertManagerState();
    }

    @Test
    public void testAddIncompleteAtomicUpdateGroups_6() {
        assertManagerState();
    }

    @Test
    public void testAddIncompleteAtomicUpdateGroups_8() {
        assertManagerState();
    }
}
