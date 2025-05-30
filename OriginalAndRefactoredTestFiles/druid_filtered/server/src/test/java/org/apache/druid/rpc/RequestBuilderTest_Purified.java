package org.apache.druid.rpc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteStreams;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.http.client.Request;
import org.apache.druid.segment.TestHelper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import java.net.URI;

public class RequestBuilderTest_Purified {

    @Test
    public void test_timeout_1() {
        Assert.assertEquals(RequestBuilder.DEFAULT_TIMEOUT, new RequestBuilder(HttpMethod.GET, "/q").getTimeout());
    }

    @Test
    public void test_timeout_2() {
        Assert.assertEquals(Duration.standardSeconds(1), new RequestBuilder(HttpMethod.GET, "/q").timeout(Duration.standardSeconds(1)).getTimeout());
    }

    @Test
    public void test_timeout_3() {
        Assert.assertEquals(Duration.ZERO, new RequestBuilder(HttpMethod.GET, "/q").timeout(Duration.ZERO).getTimeout());
    }
}
