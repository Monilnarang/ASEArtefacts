package se.michaelthelin.spotify;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.utils.DateUtils;
import org.apache.hc.core5.http.ConnectionClosedException;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.message.BasicHttpResponse;
import org.apache.hc.core5.util.TimeValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TestDefaultHttpRequestRetryStrategy_Purified {

    private SpotifyHttpRequestRetryStrategy retryStrategy;

    @BeforeEach
    public void setup() {
        this.retryStrategy = new SpotifyHttpRequestRetryStrategy(3, TimeValue.ofMilliseconds(1234L));
    }

    @Test
    public void testBasics_1_testMerged_1() {
        final HttpResponse response1 = new BasicHttpResponse(503, "Oopsie");
        Assertions.assertTrue(this.retryStrategy.retryRequest(response1, 1, null));
        Assertions.assertTrue(this.retryStrategy.retryRequest(response1, 2, null));
        Assertions.assertTrue(this.retryStrategy.retryRequest(response1, 3, null));
        Assertions.assertFalse(this.retryStrategy.retryRequest(response1, 4, null));
        Assertions.assertEquals(TimeValue.ofMilliseconds(1234L), this.retryStrategy.getRetryInterval(response1, 1, null));
    }

    @Test
    public void testBasics_5() {
        final HttpResponse response2 = new BasicHttpResponse(500, "Big Time Oopsie");
        Assertions.assertFalse(this.retryStrategy.retryRequest(response2, 1, null));
    }

    @Test
    public void testBasics_6_testMerged_3() {
        final HttpResponse response3 = new BasicHttpResponse(429, "Oopsie");
        Assertions.assertFalse(this.retryStrategy.retryRequest(response3, 1, null));
        Assertions.assertFalse(this.retryStrategy.retryRequest(response3, 2, null));
        Assertions.assertFalse(this.retryStrategy.retryRequest(response3, 3, null));
        Assertions.assertFalse(this.retryStrategy.retryRequest(response3, 4, null));
    }
}
