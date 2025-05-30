package org.apache.hadoop.fs.s3a.audit;

import java.util.List;
import com.amazonaws.handlers.RequestHandler2;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.store.audit.AuditSpan;
import static org.apache.hadoop.fs.s3a.audit.AuditTestSupport.noopAuditConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class TestAuditSpanLifecycle_Purified extends AbstractAuditingTest {

    private AuditSpan resetSpan;

    @Before
    public void setup() throws Exception {
        super.setup();
        resetSpan = getManager().getActiveAuditSpan();
    }

    protected Configuration createConfig() {
        return noopAuditConfig();
    }

    @Test
    public void testSpanActivation_1_testMerged_1() throws Throwable {
        AuditSpan span2 = getManager().createSpan("op2", null, null);
        assertActiveSpan(span2);
    }

    @Test
    public void testSpanActivation_2() throws Throwable {
        AuditSpan span1 = getManager().createSpan("op1", null, null);
        span1.activate();
        assertActiveSpan(span1);
    }

    @Test
    public void testSpanActivation_4() throws Throwable {
        assertActiveSpan(resetSpan);
    }

    @Test
    public void testSpanActivation_5() throws Throwable {
        assertActiveSpan(resetSpan);
    }

    @Test
    public void testSpanDeactivation_1_testMerged_1() throws Throwable {
        AuditSpan span2 = getManager().createSpan("op2", null, null);
        assertActiveSpan(span2);
    }

    @Test
    public void testSpanDeactivation_3() throws Throwable {
        assertActiveSpan(resetSpan);
    }
}
