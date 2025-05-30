package org.apache.hadoop.fs.s3a.audit;

import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.transfer.internal.TransferStateChangeListener;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.s3a.audit.impl.LoggingAuditor;
import org.apache.hadoop.fs.store.audit.AuditSpan;
import static org.apache.hadoop.fs.s3a.audit.AuditTestSupport.loggingAuditConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class TestLoggingAuditor_Purified extends AbstractAuditingTest {

    private static final Logger LOG = LoggerFactory.getLogger(TestLoggingAuditor.class);

    private LoggingAuditor auditor;

    @Before
    public void setup() throws Exception {
        super.setup();
        auditor = (LoggingAuditor) getManager().getAuditor();
    }

    protected Configuration createConfig() {
        return loggingAuditConfig();
    }

    @Test
    public void testLoggingSpan_1_testMerged_1() throws Throwable {
        AuditSpan span = span();
        assertActiveSpan(span);
    }

    @Test
    public void testLoggingSpan_2() throws Throwable {
        assertHeadUnaudited();
    }

    @Test
    public void testLoggingSpan_4() throws Throwable {
        assertHeadUnaudited();
    }
}
