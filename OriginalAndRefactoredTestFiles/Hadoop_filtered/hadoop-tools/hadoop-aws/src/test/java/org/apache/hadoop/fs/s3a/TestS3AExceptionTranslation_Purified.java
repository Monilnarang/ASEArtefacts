package org.apache.hadoop.fs.s3a;

import static org.apache.hadoop.fs.s3a.Constants.*;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.*;
import static org.apache.hadoop.fs.s3a.S3AUtils.*;
import static org.apache.hadoop.fs.s3a.impl.InternalConstants.SC_404;
import static org.junit.Assert.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.junit.Test;
import org.apache.hadoop.fs.s3a.impl.ErrorTranslation;
import static org.apache.hadoop.test.GenericTestUtils.assertExceptionContains;

@SuppressWarnings("ThrowableNotThrown")
public class TestS3AExceptionTranslation_Purified {

    private static final org.apache.http.conn.ConnectTimeoutException HTTP_CONNECTION_TIMEOUT_EX = new org.apache.http.conn.ConnectTimeoutException("apache");

    private static final SocketTimeoutException SOCKET_TIMEOUT_EX = new SocketTimeoutException("socket");

    protected void assertContained(String text, String contained) {
        assertTrue("string \"" + contained + "\" not found in \"" + text + "\"", text != null && text.contains(contained));
    }

    protected <E extends Throwable> void verifyTranslated(int status, Class<E> expected) throws Exception {
        verifyTranslated(expected, createS3Exception(status));
    }

    protected void assertStatusCode(int expected, AWSServiceIOException ex) {
        assertNotNull("Null exception", ex);
        if (expected != ex.getStatusCode()) {
            throw new AssertionError("Expected status code " + expected + "but got " + ex.getStatusCode(), ex);
        }
    }

    private static AmazonS3Exception createS3Exception(int code) {
        return createS3Exception("", code, null);
    }

    private static AmazonS3Exception createS3Exception(String message, int code, Map<String, String> additionalDetails) {
        AmazonS3Exception source = new AmazonS3Exception(message);
        source.setStatusCode(code);
        source.setAdditionalDetails(additionalDetails);
        return source;
    }

    private static <E extends Throwable> E verifyTranslated(Class<E> clazz, AmazonClientException exception) throws Exception {
        IOException ioe = translateException("test", "/", exception);
        assertExceptionContains(exception.getMessage(), ioe, "Translated Exception should contain the error message of the " + "actual exception");
        return verifyExceptionClass(clazz, ioe);
    }

    private void assertContainsInterrupted(boolean expected, Throwable thrown) throws Throwable {
        boolean wasInterrupted = containsInterruptedException(thrown) != null;
        if (wasInterrupted != expected) {
            throw thrown;
        }
    }

    @Test
    public void testInterruptExceptionDetecting_1_testMerged_1() throws Throwable {
        InterruptedException interrupted = new InterruptedException("irq");
        assertContainsInterrupted(true, interrupted);
        IOException ioe = new IOException("ioe");
        assertContainsInterrupted(false, ioe);
        assertContainsInterrupted(true, ioe.initCause(interrupted));
    }

    @Test
    public void testInterruptExceptionDetecting_4() throws Throwable {
        assertContainsInterrupted(true, new InterruptedIOException("ioirq"));
    }
}
