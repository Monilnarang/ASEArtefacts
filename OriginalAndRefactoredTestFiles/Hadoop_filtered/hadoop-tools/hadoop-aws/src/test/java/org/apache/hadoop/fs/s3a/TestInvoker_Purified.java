package org.apache.hadoop.fs.s3a;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkBaseException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.retry.RetryPolicy;
import org.apache.hadoop.net.ConnectTimeoutException;
import static org.apache.hadoop.fs.s3a.Constants.*;
import static org.apache.hadoop.fs.s3a.Invoker.*;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.verifyExceptionClass;
import static org.apache.hadoop.fs.s3a.S3AUtils.*;
import static org.apache.hadoop.test.LambdaTestUtils.*;

@SuppressWarnings("ThrowableNotThrown")
public class TestInvoker_Purified extends Assert {

    private static final Configuration FAST_RETRY_CONF;

    private static final ConnectTimeoutException HADOOP_CONNECTION_TIMEOUT_EX = new ConnectTimeoutException("hadoop");

    private static final Local.ConnectTimeoutException LOCAL_CONNECTION_TIMEOUT_EX = new Local.ConnectTimeoutException("local");

    private static final org.apache.http.conn.ConnectTimeoutException HTTP_CONNECTION_TIMEOUT_EX = new org.apache.http.conn.ConnectTimeoutException("apache");

    private static final SocketTimeoutException SOCKET_TIMEOUT_EX = new SocketTimeoutException("socket");

    private static final int ACTIVE_RETRY_LIMIT = RETRY_LIMIT_DEFAULT;

    private static final int RETRIES_TOO_MANY = ACTIVE_RETRY_LIMIT + 10;

    public static final int SAFE_RETRY_COUNT = 5;

    static {
        FAST_RETRY_CONF = new Configuration();
        String interval = "10ms";
        FAST_RETRY_CONF.set(RETRY_INTERVAL, interval);
        FAST_RETRY_CONF.set(RETRY_THROTTLE_INTERVAL, interval);
        FAST_RETRY_CONF.setInt(RETRY_LIMIT, ACTIVE_RETRY_LIMIT);
        FAST_RETRY_CONF.setInt(RETRY_THROTTLE_LIMIT, ACTIVE_RETRY_LIMIT);
    }

    private static final S3ARetryPolicy RETRY_POLICY = new S3ARetryPolicy(FAST_RETRY_CONF);

    private int retryCount;

    private Invoker invoker = new Invoker(RETRY_POLICY, (text, e, retries, idempotent) -> retryCount++);

    private static final AmazonClientException CLIENT_TIMEOUT_EXCEPTION = new AmazonClientException(new Local.ConnectTimeoutException("timeout"));

    private static final AmazonServiceException BAD_REQUEST = serviceException(AWSBadRequestException.STATUS_CODE, "bad request");

    @Before
    public void setup() {
        resetCounters();
    }

    private static AmazonServiceException serviceException(int code, String text) {
        AmazonServiceException ex = new AmazonServiceException(text);
        ex.setStatusCode(code);
        return ex;
    }

    private static AmazonS3Exception createS3Exception(int code) {
        return createS3Exception(code, "", null);
    }

    private static AmazonS3Exception createS3Exception(int code, String message, Throwable inner) {
        AmazonS3Exception ex = new AmazonS3Exception(message);
        ex.setStatusCode(code);
        ex.initCause(inner);
        return ex;
    }

    protected <E extends Throwable> void verifyTranslated(int status, Class<E> expected) throws Exception {
        verifyTranslated(expected, createS3Exception(status));
    }

    private static <E extends Throwable> E verifyTranslated(Class<E> clazz, SdkBaseException exception) throws Exception {
        return verifyExceptionClass(clazz, translateException("test", "/", exception));
    }

    private void resetCounters() {
        retryCount = 0;
    }

    private void assertRetryAction(String text, S3ARetryPolicy policy, RetryPolicy.RetryAction expected, Exception ex, int retries, boolean idempotent) throws Exception {
        RetryPolicy.RetryAction outcome = policy.shouldRetry(ex, retries, 0, idempotent);
        if (!expected.action.equals(outcome.action)) {
            throw new AssertionError(String.format("%s Expected action %s from shouldRetry(%s, %s, %s), but got" + " %s", text, expected, ex.toString(), retries, idempotent, outcome.action), ex);
        }
    }

    protected AmazonServiceException newThrottledException() {
        return serviceException(AWSServiceThrottledException.STATUS_CODE, "throttled");
    }

    private static class Local {

        private static class ConnectTimeoutException extends InterruptedIOException {

            ConnectTimeoutException(String s) {
                super(s);
            }
        }

        private static class NotAConnectTimeoutException extends InterruptedIOException {
        }
    }

    private static final class CatchCallback implements Retried {

        private IOException lastException;

        @Override
        public void onFailure(String text, IOException exception, int retries, boolean idempotent) {
            lastException = exception;
        }
    }

    @Test
    public void testConnectionRetryPolicyIdempotent_1() throws Throwable {
        assertRetryAction("Expected retry on connection timeout", RETRY_POLICY, RetryPolicy.RetryAction.RETRY, HADOOP_CONNECTION_TIMEOUT_EX, 1, true);
    }

    @Test
    public void testConnectionRetryPolicyIdempotent_2() throws Throwable {
        assertRetryAction("Expected connection timeout failure", RETRY_POLICY, RetryPolicy.RetryAction.FAIL, HADOOP_CONNECTION_TIMEOUT_EX, RETRIES_TOO_MANY, true);
    }

    @Test
    public void testNPEsNotRetried_1() throws Throwable {
        assertRetryAction("Expected NPE trigger failure", RETRY_POLICY, RetryPolicy.RetryAction.FAIL, new NullPointerException("oops"), 1, true);
    }

    @Test
    public void testNPEsNotRetried_2() throws Throwable {
        assertEquals("retry count ", 0, retryCount);
    }
}
