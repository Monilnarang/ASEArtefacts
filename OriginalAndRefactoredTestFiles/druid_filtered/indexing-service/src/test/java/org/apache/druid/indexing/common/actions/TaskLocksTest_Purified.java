package org.apache.druid.indexing.common.actions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.indexer.TaskStatus;
import org.apache.druid.indexing.common.SegmentLock;
import org.apache.druid.indexing.common.TaskLock;
import org.apache.druid.indexing.common.TaskLockType;
import org.apache.druid.indexing.common.TimeChunkLock;
import org.apache.druid.indexing.common.config.TaskStorageConfig;
import org.apache.druid.indexing.common.task.NoopTask;
import org.apache.druid.indexing.common.task.Task;
import org.apache.druid.indexing.common.task.Tasks;
import org.apache.druid.indexing.overlord.HeapMemoryTaskStorage;
import org.apache.druid.indexing.overlord.LockResult;
import org.apache.druid.indexing.overlord.SpecificSegmentLockRequest;
import org.apache.druid.indexing.overlord.TaskLockbox;
import org.apache.druid.indexing.overlord.TaskStorage;
import org.apache.druid.indexing.overlord.TimeChunkLockRequest;
import org.apache.druid.indexing.test.TestIndexerMetadataStorageCoordinator;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.metadata.ReplaceTaskLock;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.partition.LinearShardSpec;
import org.apache.druid.timeline.partition.NumberedShardSpec;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskLocksTest_Purified {

    private TaskLockbox lockbox;

    private Task task;

    @Before
    public void setup() {
        final TaskStorage taskStorage = new HeapMemoryTaskStorage(new TaskStorageConfig(null));
        lockbox = new TaskLockbox(taskStorage, new TestIndexerMetadataStorageCoordinator());
        task = NoopTask.create();
        taskStorage.insert(task, TaskStatus.running(task.getId()));
        lockbox.add(task);
    }

    private Set<DataSegment> createTimeChunkedSegments() {
        return ImmutableSet.of(new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-01/2017-01-02")).version(DateTimes.nowUtc().toString()).shardSpec(new LinearShardSpec(2)).size(0).build(), new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-02/2017-01-03")).version(DateTimes.nowUtc().toString()).shardSpec(new LinearShardSpec(2)).size(0).build(), new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-03/2017-01-04")).version(DateTimes.nowUtc().toString()).shardSpec(new LinearShardSpec(2)).size(0).build());
    }

    private Set<DataSegment> createNumberedPartitionedSegments() {
        final String version = DateTimes.nowUtc().toString();
        return ImmutableSet.of(new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-01/2017-01-02")).version(version).shardSpec(new NumberedShardSpec(0, 0)).size(0).build(), new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-01/2017-01-02")).version(version).shardSpec(new NumberedShardSpec(1, 0)).size(0).build(), new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-01/2017-01-02")).version(version).shardSpec(new NumberedShardSpec(2, 0)).size(0).build(), new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-01/2017-01-02")).version(version).shardSpec(new NumberedShardSpec(3, 0)).size(0).build(), new DataSegment.Builder().dataSource(task.getDataSource()).interval(Intervals.of("2017-01-01/2017-01-02")).version(version).shardSpec(new NumberedShardSpec(4, 0)).size(0).build());
    }

    private TaskLock tryTimeChunkLock(Task task, Interval interval, TaskLockType lockType) {
        final TaskLock taskLock = lockbox.tryLock(task, new TimeChunkLockRequest(lockType, task, interval, null)).getTaskLock();
        Assert.assertNotNull(taskLock);
        return taskLock;
    }

    private LockResult trySegmentLock(Task task, Interval interval, String version, int partitonId) {
        return lockbox.tryLock(task, new SpecificSegmentLockRequest(TaskLockType.EXCLUSIVE, task, interval, version, partitonId));
    }

    private TimeChunkLock newTimeChunkLock(Interval interval, String version) {
        return new TimeChunkLock(TaskLockType.EXCLUSIVE, task.getGroupId(), task.getDataSource(), interval, version, task.getPriority());
    }

    private SegmentLock newSegmentLock(Interval interval, String version, int partitionId) {
        return new SegmentLock(TaskLockType.EXCLUSIVE, task.getGroupId(), task.getDataSource(), interval, version, partitionId, task.getPriority());
    }

    @Test
    public void testLockTypeForAppendWithLockTypeInContext_1() {
        Assert.assertEquals(TaskLockType.REPLACE, TaskLocks.determineLockTypeForAppend(ImmutableMap.of(Tasks.TASK_LOCK_TYPE, "REPLACE")));
    }

    @Test
    public void testLockTypeForAppendWithLockTypeInContext_2() {
        Assert.assertEquals(TaskLockType.APPEND, TaskLocks.determineLockTypeForAppend(ImmutableMap.of(Tasks.TASK_LOCK_TYPE, "APPEND")));
    }

    @Test
    public void testLockTypeForAppendWithLockTypeInContext_3() {
        Assert.assertEquals(TaskLockType.SHARED, TaskLocks.determineLockTypeForAppend(ImmutableMap.of(Tasks.TASK_LOCK_TYPE, "SHARED")));
    }

    @Test
    public void testLockTypeForAppendWithLockTypeInContext_4() {
        Assert.assertEquals(TaskLockType.EXCLUSIVE, TaskLocks.determineLockTypeForAppend(ImmutableMap.of(Tasks.TASK_LOCK_TYPE, "EXCLUSIVE", Tasks.USE_SHARED_LOCK, true)));
    }

    @Test
    public void testLockTypeForAppendWithNoLockTypeInContext_1() {
        Assert.assertEquals(TaskLockType.EXCLUSIVE, TaskLocks.determineLockTypeForAppend(ImmutableMap.of()));
    }

    @Test
    public void testLockTypeForAppendWithNoLockTypeInContext_2() {
        Assert.assertEquals(TaskLockType.EXCLUSIVE, TaskLocks.determineLockTypeForAppend(ImmutableMap.of(Tasks.USE_SHARED_LOCK, false)));
    }

    @Test
    public void testLockTypeForAppendWithNoLockTypeInContext_3() {
        Assert.assertEquals(TaskLockType.SHARED, TaskLocks.determineLockTypeForAppend(ImmutableMap.of(Tasks.USE_SHARED_LOCK, true)));
    }
}
