package org.apache.hadoop.yarn.api;

import org.junit.jupiter.api.Test;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestApplicationAttemptId_Purified {

    private ApplicationAttemptId createAppAttemptId(long clusterTimeStamp, int id, int attemptId) {
        ApplicationId appId = ApplicationId.newInstance(clusterTimeStamp, id);
        return ApplicationAttemptId.newInstance(appId, attemptId);
    }

    public static void main(String[] args) throws Exception {
        TestApplicationAttemptId t = new TestApplicationAttemptId();
        t.testApplicationAttemptId();
    }

    @Test
    void testApplicationAttemptId_1_testMerged_1() {
        ApplicationAttemptId a1 = createAppAttemptId(10l, 1, 1);
        ApplicationAttemptId a2 = createAppAttemptId(10l, 1, 2);
        ApplicationAttemptId a3 = createAppAttemptId(10l, 2, 1);
        ApplicationAttemptId a4 = createAppAttemptId(8l, 1, 4);
        ApplicationAttemptId a5 = createAppAttemptId(10l, 1, 1);
        assertEquals(a1, a5);
        assertNotEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);
        assertTrue(a1.compareTo(a5) == 0);
        assertTrue(a1.compareTo(a2) < 0);
        assertTrue(a1.compareTo(a3) < 0);
        assertTrue(a1.compareTo(a4) > 0);
        assertTrue(a1.hashCode() == a5.hashCode());
        assertFalse(a1.hashCode() == a2.hashCode());
        assertFalse(a1.hashCode() == a3.hashCode());
        assertFalse(a1.hashCode() == a4.hashCode());
        assertEquals("appattempt_10_0001_000001", a1.toString());
    }

    @Test
    void testApplicationAttemptId_14() {
        long ts = System.currentTimeMillis();
        ApplicationAttemptId a6 = createAppAttemptId(ts, 543627, 33492611);
        assertEquals("appattempt_" + ts + "_543627_33492611", a6.toString());
    }
}
