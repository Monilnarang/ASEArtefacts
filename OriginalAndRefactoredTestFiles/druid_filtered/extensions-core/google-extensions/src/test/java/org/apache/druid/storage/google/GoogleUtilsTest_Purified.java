package org.apache.druid.storage.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import com.google.cloud.storage.StorageException;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class GoogleUtilsTest_Purified {

    @Test
    public void test429_1() {
        Assert.assertTrue(GoogleUtils.isRetryable(new GoogleJsonResponseException(new HttpResponseException.Builder(429, "ignored", new HttpHeaders()), null)));
    }

    @Test
    public void test429_2() {
        Assert.assertTrue(GoogleUtils.isRetryable(new HttpResponseException.Builder(429, "ignored", new HttpHeaders()).build()));
    }

    @Test
    public void test429_3() {
        Assert.assertTrue(GoogleUtils.isRetryable(new HttpResponseException.Builder(500, "ignored", new HttpHeaders()).build()));
    }

    @Test
    public void test429_4() {
        Assert.assertTrue(GoogleUtils.isRetryable(new HttpResponseException.Builder(503, "ignored", new HttpHeaders()).build()));
    }

    @Test
    public void test429_5() {
        Assert.assertTrue(GoogleUtils.isRetryable(new HttpResponseException.Builder(599, "ignored", new HttpHeaders()).build()));
    }

    @Test
    public void test429_6() {
        Assert.assertFalse(GoogleUtils.isRetryable(new GoogleJsonResponseException(new HttpResponseException.Builder(404, "ignored", new HttpHeaders()), null)));
    }

    @Test
    public void test429_7() {
        Assert.assertFalse(GoogleUtils.isRetryable(new HttpResponseException.Builder(404, "ignored", new HttpHeaders()).build()));
    }

    @Test
    public void test429_8() {
        Assert.assertTrue(GoogleUtils.isRetryable(new IOException("generic io exception")));
    }

    @Test
    public void test429_9() {
        Assert.assertFalse(GoogleUtils.isRetryable(new StorageException(404, "ignored")));
    }

    @Test
    public void test429_10() {
        Assert.assertTrue(GoogleUtils.isRetryable(new StorageException(429, "ignored")));
    }

    @Test
    public void test429_11() {
        Assert.assertTrue(GoogleUtils.isRetryable(new StorageException(500, "ignored")));
    }

    @Test
    public void test429_12() {
        Assert.assertTrue(GoogleUtils.isRetryable(new StorageException(503, "ignored")));
    }

    @Test
    public void test429_13() {
        Assert.assertFalse(GoogleUtils.isRetryable(new StorageException(599, "ignored")));
    }
}
