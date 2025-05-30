package io.elasticjob.lite.event.rdb;

import io.elasticjob.lite.context.ExecutionType;
import io.elasticjob.lite.event.type.JobExecutionEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent.Source;
import io.elasticjob.lite.event.type.JobStatusTraceEvent.State;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class JobEventRdbStorageTest_Purified {

    private JobEventRdbStorage storage;

    @Before
    public void setup() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(org.h2.Driver.class.getName());
        dataSource.setUrl("jdbc:h2:mem:job_event_storage");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        storage = new JobEventRdbStorage(dataSource);
    }

    @Test
    public void assertAddJobStatusTraceEventWhenFailoverWithTaskStagingState_1() throws SQLException {
        assertThat(storage.getJobStatusTraceEvents("fake_failover_task_id").size(), is(0));
    }

    @Test
    public void assertAddJobStatusTraceEventWhenFailoverWithTaskStagingState_2() throws SQLException {
        JobStatusTraceEvent jobStatusTraceEvent = new JobStatusTraceEvent("test_job", "fake_failover_task_id", "fake_slave_id", Source.LITE_EXECUTOR, ExecutionType.FAILOVER, "0", State.TASK_STAGING, "message is empty.");
        jobStatusTraceEvent.setOriginalTaskId("original_fake_failover_task_id");
        storage.addJobStatusTraceEvent(jobStatusTraceEvent);
        assertThat(storage.getJobStatusTraceEvents("fake_failover_task_id").size(), is(1));
    }
}
