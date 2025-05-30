package org.apache.druid.storage.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import com.google.cloud.storage.StorageException;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GoogleUtilsTest_Parameterized {

    @Test
    public void test429_1() {
        Assert.assertTrue(GoogleUtils.isRetryable(new GoogleJsonResponseException(new HttpResponseException.Builder(429, "ignored", new HttpHeaders()), null)));
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

    @ParameterizedTest
    @MethodSource("Provider_test429_2to5")
    public void test429_2to5(int param1, String param2) {
        Assert.assertTrue(GoogleUtils.isRetryable(new HttpResponseException.Builder(param1, param2, new HttpHeaders()).build()));
    }

    static public Stream<Arguments> Provider_test429_2to5() {
        return Stream.of(arguments(429, "ignored"), arguments(500, "ignored"), arguments(503, "ignored"), arguments(599, "ignored"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test429_9_13")
    public void test429_9_13(int param1, String param2) {
        Assert.assertFalse(GoogleUtils.isRetryable(new StorageException(param1, param2)));
    }

    static public Stream<Arguments> Provider_test429_9_13() {
        return Stream.of(arguments(404, "ignored"), arguments(599, "ignored"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test429_10to12")
    public void test429_10to12(int param1, String param2) {
        Assert.assertTrue(GoogleUtils.isRetryable(new StorageException(param1, param2)));
    }

    static public Stream<Arguments> Provider_test429_10to12() {
        return Stream.of(arguments(429, "ignored"), arguments(500, "ignored"), arguments(503, "ignored"));
    }
}
