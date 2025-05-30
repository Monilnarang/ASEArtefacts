package org.apache.hadoop.fs.s3a.audit;

import java.nio.file.AccessDeniedException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.s3a.S3AFileSystem;
import org.apache.hadoop.fs.s3a.WriteOperationHelper;
import org.apache.hadoop.fs.s3a.performance.AbstractS3ACostTest;
import org.apache.hadoop.fs.statistics.IOStatistics;
import static org.apache.hadoop.fs.s3a.Statistic.AUDIT_FAILURE;
import static org.apache.hadoop.fs.s3a.Statistic.AUDIT_REQUEST_EXECUTION;
import static org.apache.hadoop.fs.s3a.audit.AuditTestSupport.enableLoggingAuditor;
import static org.apache.hadoop.fs.s3a.audit.AuditTestSupport.resetAuditOptions;
import static org.apache.hadoop.fs.s3a.audit.S3AAuditConstants.AUDIT_REQUEST_HANDLERS;
import static org.apache.hadoop.fs.s3a.audit.S3AAuditConstants.UNAUDITED_OPERATION;
import static org.apache.hadoop.fs.statistics.IOStatisticAssertions.assertThatStatisticCounter;
import static org.apache.hadoop.fs.statistics.IOStatisticAssertions.lookupCounterStatistic;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

public class ITestAuditManager_Purified extends AbstractS3ACostTest {

    public ITestAuditManager() {
        super(true);
    }

    @Override
    public Configuration createConfiguration() {
        Configuration conf = super.createConfiguration();
        resetAuditOptions(conf);
        enableLoggingAuditor(conf);
        conf.set(AUDIT_REQUEST_HANDLERS, SimpleAWSRequestHandler.CLASS);
        return conf;
    }

    private IOStatistics iostats() {
        return getFileSystem().getIOStatistics();
    }

    @Test
    public void testRequestHandlerBinding_1() throws Throwable {
        final long baseCount = SimpleAWSRequestHandler.getInvocationCount();
        isGreaterThan(baseCount);
    }

    @Test
    public void testRequestHandlerBinding_2() throws Throwable {
        final long exec0 = lookupCounterStatistic(iostats(), AUDIT_REQUEST_EXECUTION.getSymbol());
        assertThatStatisticCounter(iostats(), AUDIT_REQUEST_EXECUTION.getSymbol()).isGreaterThan(exec0);
    }

    @Test
    public void testRequestHandlerBinding_3() throws Throwable {
        assertThatStatisticCounter(iostats(), AUDIT_FAILURE.getSymbol()).isZero();
    }
}
