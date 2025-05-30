package org.apache.hadoop.yarn.server.sharedcachemanager.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.conf.Configuration;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCleanerMetrics_Purified {

    Configuration conf = new Configuration();

    CleanerMetrics cleanerMetrics;

    @BeforeEach
    public void init() {
        cleanerMetrics = CleanerMetrics.getInstance();
    }

    public void simulateACleanerRun() {
        cleanerMetrics.reportCleaningStart();
        cleanerMetrics.reportAFileProcess();
        cleanerMetrics.reportAFileDelete();
        cleanerMetrics.reportAFileProcess();
        cleanerMetrics.reportAFileProcess();
    }

    void assertMetrics(int proc, int totalProc, int del, int totalDel) {
        assertEquals(proc, cleanerMetrics.getProcessedFiles(), "Processed files in the last period are not measured correctly");
        assertEquals(totalProc, cleanerMetrics.getTotalProcessedFiles(), "Total processed files are not measured correctly");
        assertEquals(del, cleanerMetrics.getDeletedFiles(), "Deleted files in the last period are not measured correctly");
        assertEquals(totalDel, cleanerMetrics.getTotalDeletedFiles(), "Total deleted files are not measured correctly");
    }

    @Test
    void testMetricsOverMultiplePeriods_1() {
        assertMetrics(4, 4, 1, 1);
    }

    @Test
    void testMetricsOverMultiplePeriods_2() {
        assertMetrics(4, 8, 1, 2);
    }
}
