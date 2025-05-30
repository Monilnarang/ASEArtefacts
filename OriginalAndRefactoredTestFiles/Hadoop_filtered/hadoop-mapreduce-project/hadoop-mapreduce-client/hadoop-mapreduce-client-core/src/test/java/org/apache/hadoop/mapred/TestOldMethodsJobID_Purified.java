package org.apache.hadoop.mapred;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.hadoop.mapred.TaskCompletionEvent.Status;
import org.apache.hadoop.mapreduce.TaskType;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestOldMethodsJobID_Purified {

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testJobID_1() throws IOException {
        JobID jid = new JobID("001", 2);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        jid.write(new DataOutputStream(out));
        assertEquals(jid, JobID.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray()))));
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testJobID_2() throws IOException {
        assertEquals("job_001_0001", JobID.getJobIDsPattern("001", 1));
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testTaskAttemptID_1() {
        assertEquals("attempt_001_0002_m_000003_4", TaskAttemptID.getTaskAttemptIDsPattern("001", 2, true, 3, 4));
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testTaskAttemptID_2() {
        TaskAttemptID task = new TaskAttemptID("001", 2, true, 3, 4);
        assertEquals("task_001_0002_m_000003", task.getTaskID().toString());
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testTaskAttemptID_3() {
        assertEquals("attempt_001_0001_r_000002_3", TaskAttemptID.getTaskAttemptIDsPattern("001", 1, TaskType.REDUCE, 2, 3));
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 5000)
    public void testTaskAttemptID_4() {
        assertEquals("001_0001_m_000001_2", TaskAttemptID.getTaskAttemptIDsPatternWOPrefix("001", 1, TaskType.MAP, 1, 2).toString());
    }
}
