package org.apache.druid.data.input.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.net.URI;

public class CloudObjectLocationTest_Purified {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String SCHEME = "s3";

    private static final String BUCKET_NAME = "bucket";

    private static final CloudObjectLocation LOCATION = new CloudObjectLocation(BUCKET_NAME, "path/to/myobject");

    private static final CloudObjectLocation LOCATION_EXTRA_SLASHES = new CloudObjectLocation(BUCKET_NAME + '/', "/path/to/myobject");

    private static final CloudObjectLocation LOCATION_URLENCODE = new CloudObjectLocation(BUCKET_NAME, "path/to/myobject?question");

    private static final CloudObjectLocation LOCATION_NON_ASCII = new CloudObjectLocation(BUCKET_NAME, "pÄth/tø/myøbject");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSerde_1() throws Exception {
        Assert.assertEquals(LOCATION, MAPPER.readValue(MAPPER.writeValueAsString(LOCATION), CloudObjectLocation.class));
    }

    @Test
    public void testSerde_2() throws Exception {
        Assert.assertEquals(LOCATION_EXTRA_SLASHES, MAPPER.readValue(MAPPER.writeValueAsString(LOCATION_EXTRA_SLASHES), CloudObjectLocation.class));
    }

    @Test
    public void testSerde_3() throws Exception {
        Assert.assertEquals(LOCATION_URLENCODE, MAPPER.readValue(MAPPER.writeValueAsString(LOCATION_URLENCODE), CloudObjectLocation.class));
    }

    @Test
    public void testSerde_4() throws Exception {
        Assert.assertEquals(LOCATION_NON_ASCII, MAPPER.readValue(MAPPER.writeValueAsString(LOCATION_NON_ASCII), CloudObjectLocation.class));
    }

    @Test
    public void testToUri_1() {
        Assert.assertEquals(URI.create("s3://bucket/path/to/myobject"), LOCATION.toUri(SCHEME));
    }

    @Test
    public void testToUri_2() {
        Assert.assertEquals(URI.create("s3://bucket/path/to/myobject"), LOCATION_EXTRA_SLASHES.toUri(SCHEME));
    }

    @Test
    public void testToUri_3() {
        Assert.assertEquals(URI.create("s3://bucket/path/to/myobject%3Fquestion"), LOCATION_URLENCODE.toUri(SCHEME));
    }

    @Test
    public void testToUri_4() {
        Assert.assertEquals(URI.create("s3://bucket/p%C3%84th/t%C3%B8/my%C3%B8bject"), LOCATION_NON_ASCII.toUri(SCHEME));
    }

    @Test
    public void testUriRoundTrip_1() {
        Assert.assertEquals(LOCATION, new CloudObjectLocation(LOCATION.toUri(SCHEME)));
    }

    @Test
    public void testUriRoundTrip_2() {
        Assert.assertEquals(LOCATION_EXTRA_SLASHES, new CloudObjectLocation(LOCATION_EXTRA_SLASHES.toUri(SCHEME)));
    }

    @Test
    public void testUriRoundTrip_3() {
        Assert.assertEquals(LOCATION_URLENCODE, new CloudObjectLocation(LOCATION_URLENCODE.toUri(SCHEME)));
    }

    @Test
    public void testUriRoundTrip_4() {
        Assert.assertEquals(LOCATION_NON_ASCII, new CloudObjectLocation(LOCATION_NON_ASCII.toUri(SCHEME)));
    }
}
