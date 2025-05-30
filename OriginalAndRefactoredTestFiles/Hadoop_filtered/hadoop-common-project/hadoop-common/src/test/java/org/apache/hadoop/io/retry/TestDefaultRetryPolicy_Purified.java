package org.apache.hadoop.io.retry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.RetriableException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

public class TestDefaultRetryPolicy_Purified {

    @Rule
    public Timeout timeout = new Timeout(30000, TimeUnit.MILLISECONDS);

    @Test
    public void testRetryDecisionOrdering_1() throws Exception {
        Assert.assertTrue(RetryPolicy.RetryAction.RetryDecision.FAIL.compareTo(RetryPolicy.RetryAction.RetryDecision.RETRY) < 0);
    }

    @Test
    public void testRetryDecisionOrdering_2() throws Exception {
        Assert.assertTrue(RetryPolicy.RetryAction.RetryDecision.RETRY.compareTo(RetryPolicy.RetryAction.RetryDecision.FAILOVER_AND_RETRY) < 0);
    }

    @Test
    public void testRetryDecisionOrdering_3() throws Exception {
        Assert.assertTrue(RetryPolicy.RetryAction.RetryDecision.FAIL.compareTo(RetryPolicy.RetryAction.RetryDecision.FAILOVER_AND_RETRY) < 0);
    }
}
