package org.apache.druid.indexing.common.task.concurrent;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.druid.indexing.common.MultipleFileTaskReportFileWriter;
import org.apache.druid.indexing.common.TaskLock;
import org.apache.druid.indexing.common.TaskStorageDirTracker;
import org.apache.druid.indexing.common.TaskToolbox;
import org.apache.druid.indexing.common.TaskToolboxFactory;
import org.apache.druid.indexing.common.actions.RetrieveUsedSegmentsAction;
import org.apache.druid.indexing.common.actions.TaskActionClient;
import org.apache.druid.indexing.common.actions.TaskActionClientFactory;
import org.apache.druid.indexing.common.config.TaskConfig;
import org.apache.druid.indexing.common.config.TaskConfigBuilder;
import org.apache.druid.indexing.common.task.IngestionTestBase;
import org.apache.druid.indexing.common.task.NoopTask;
import org.apache.druid.indexing.common.task.NoopTaskContextEnricher;
import org.apache.druid.indexing.common.task.Task;
import org.apache.druid.indexing.common.task.TestAppenderatorsManager;
import org.apache.druid.indexing.overlord.Segments;
import org.apache.druid.indexing.overlord.TaskQueue;
import org.apache.druid.indexing.overlord.TaskRunner;
import org.apache.druid.indexing.overlord.TestTaskToolboxFactory;
import org.apache.druid.indexing.overlord.ThreadingTaskRunner;
import org.apache.druid.indexing.overlord.config.DefaultTaskConfig;
import org.apache.druid.indexing.overlord.config.TaskLockConfig;
import org.apache.druid.indexing.overlord.config.TaskQueueConfig;
import org.apache.druid.indexing.worker.config.WorkerConfig;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.ISE;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.segment.IndexIO;
import org.apache.druid.segment.TestDataSource;
import org.apache.druid.segment.column.ColumnConfig;
import org.apache.druid.segment.metadata.CentralizedDatasourceSchemaConfig;
import org.apache.druid.segment.realtime.appenderator.SegmentIdWithShardSpec;
import org.apache.druid.server.DruidNode;
import org.apache.druid.server.metrics.NoopServiceEmitter;
import org.apache.druid.tasklogs.NoopTaskLogs;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.SegmentId;
import org.apache.druid.timeline.partition.NumberedShardSpec;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentReplaceAndAppendTest_Purified extends IngestionTestBase {

    private static final String SEGMENT_V0 = DateTimes.EPOCH.toString();

    private static final Interval YEAR_23 = Intervals.of("2023/2024");

    private static final Interval JAN_23 = Intervals.of("2023-01/2023-02");

    private static final Interval DEC_23 = Intervals.of("2023-12/2024-01");

    private static final Interval JAN_FEB_MAR_23 = Intervals.of("2023-01-01/2023-04-01");

    private static final Interval APR_MAY_JUN_23 = Intervals.of("2023-04-01/2023-07-01");

    private static final Interval JUL_AUG_SEP_23 = Intervals.of("2023-07-01/2023-10-01");

    private static final Interval OCT_NOV_DEC_23 = Intervals.of("2023-10-01/2024-01-01");

    private static final Interval FIRST_OF_JAN_23 = Intervals.of("2023-01-01/2023-01-02");

    private TaskQueue taskQueue;

    private TaskActionClientFactory taskActionClientFactory;

    private TaskActionClient dummyTaskActionClient;

    private final List<ActionsTestTask> runningTasks = new ArrayList<>();

    private ActionsTestTask appendTask;

    private ActionsTestTask replaceTask;

    private final AtomicInteger groupId = new AtomicInteger(0);

    @Before
    public void setup() {
        final TaskConfig taskConfig = new TaskConfigBuilder().build();
        taskActionClientFactory = createActionClientFactory();
        dummyTaskActionClient = taskActionClientFactory.create(NoopTask.create());
        final WorkerConfig workerConfig = new WorkerConfig().setCapacity(10);
        TaskRunner taskRunner = new ThreadingTaskRunner(createToolboxFactory(taskConfig, taskActionClientFactory), taskConfig, workerConfig, new NoopTaskLogs(), getObjectMapper(), new TestAppenderatorsManager(), new MultipleFileTaskReportFileWriter(), new DruidNode("middleManager", "host", false, 8091, null, true, false), TaskStorageDirTracker.fromConfigs(workerConfig, taskConfig));
        taskQueue = new TaskQueue(new TaskLockConfig(), new TaskQueueConfig(null, new Period(0L), null, null, null, null), new DefaultTaskConfig(), getTaskStorage(), taskRunner, taskActionClientFactory, getLockbox(), new NoopServiceEmitter(), getObjectMapper(), new NoopTaskContextEnricher());
        runningTasks.clear();
        taskQueue.start();
        groupId.set(0);
        appendTask = createAndStartTask();
        replaceTask = createAndStartTask();
    }

    @After
    public void tearDown() {
        for (ActionsTestTask task : runningTasks) {
            task.finishRunAndGetStatus();
        }
    }

    @Nullable
    private DataSegment findSegmentWith(String version, Map<String, Object> loadSpec, Set<DataSegment> segments) {
        for (DataSegment segment : segments) {
            if (version.equals(segment.getVersion()) && Objects.equals(segment.getLoadSpec(), loadSpec)) {
                return segment;
            }
        }
        return null;
    }

    private static DataSegment asSegment(SegmentIdWithShardSpec pendingSegment) {
        final SegmentId id = pendingSegment.asSegmentId();
        return new DataSegment(id, Collections.singletonMap(id.toString(), id.toString()), Collections.emptyList(), Collections.emptyList(), pendingSegment.getShardSpec(), null, 0, 0);
    }

    private void verifyIntervalHasUsedSegments(Interval interval, DataSegment... expectedSegments) {
        verifySegments(interval, Segments.INCLUDING_OVERSHADOWED, expectedSegments);
    }

    private void verifyIntervalHasVisibleSegments(Interval interval, DataSegment... expectedSegments) {
        verifySegments(interval, Segments.ONLY_VISIBLE, expectedSegments);
    }

    private void verifySegments(Interval interval, Segments visibility, DataSegment... expectedSegments) {
        try {
            Collection<DataSegment> allUsedSegments = dummyTaskActionClient.submit(new RetrieveUsedSegmentsAction(TestDataSource.WIKI, ImmutableList.of(interval), visibility));
            Assert.assertEquals(Sets.newHashSet(expectedSegments), Sets.newHashSet(allUsedSegments));
        } catch (IOException e) {
            throw new ISE(e, "Error while fetching used segments in interval[%s]", interval);
        }
    }

    private void verifyInputSegments(Task task, Interval interval, DataSegment... expectedSegments) {
        try {
            final TaskActionClient taskActionClient = taskActionClientFactory.create(task);
            Collection<DataSegment> allUsedSegments = taskActionClient.submit(new RetrieveUsedSegmentsAction(TestDataSource.WIKI, Collections.singletonList(interval)));
            Assert.assertEquals(Sets.newHashSet(expectedSegments), Sets.newHashSet(allUsedSegments));
        } catch (IOException e) {
            throw new ISE(e, "Error while fetching segments to replace in interval[%s]", interval);
        }
    }

    private TaskToolboxFactory createToolboxFactory(TaskConfig taskConfig, TaskActionClientFactory taskActionClientFactory) {
        CentralizedDatasourceSchemaConfig centralizedDatasourceSchemaConfig = CentralizedDatasourceSchemaConfig.enabled(true);
        TestTaskToolboxFactory.Builder builder = new TestTaskToolboxFactory.Builder().setConfig(taskConfig).setIndexIO(new IndexIO(getObjectMapper(), ColumnConfig.DEFAULT)).setTaskActionClientFactory(taskActionClientFactory).setCentralizedTableSchemaConfig(centralizedDatasourceSchemaConfig);
        return new TestTaskToolboxFactory(builder) {

            @Override
            public TaskToolbox build(TaskConfig config, Task task) {
                return createTaskToolbox(config, task, null);
            }
        };
    }

    private DataSegment createSegment(Interval interval, String version) {
        return DataSegment.builder().dataSource(TestDataSource.WIKI).interval(interval).version(version).size(100).build();
    }

    private ActionsTestTask createAndStartTask() {
        ActionsTestTask task = new ActionsTestTask(TestDataSource.WIKI, "test_" + groupId.incrementAndGet(), taskActionClientFactory);
        taskQueue.add(task);
        runningTasks.add(task);
        return task;
    }

    @Test
    public void testAllocateAppendMonthLockReplaceDay_1_testMerged_1() {
        final SegmentIdWithShardSpec pendingSegment = appendTask.allocateSegmentForTimestamp(JAN_23.getStart(), Granularities.MONTH);
        Assert.assertEquals(JAN_23, pendingSegment.getInterval());
        Assert.assertEquals(SEGMENT_V0, pendingSegment.getVersion());
    }

    @Test
    public void testAllocateAppendMonthLockReplaceDay_3() {
        final TaskLock replaceLock = replaceTask.acquireReplaceLockOn(FIRST_OF_JAN_23);
        Assert.assertNull(replaceLock);
    }
}
