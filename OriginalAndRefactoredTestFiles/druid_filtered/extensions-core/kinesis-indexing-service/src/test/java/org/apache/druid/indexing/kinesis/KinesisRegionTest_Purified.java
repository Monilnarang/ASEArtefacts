package org.apache.druid.indexing.kinesis;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

public class KinesisRegionTest_Purified {

    private ObjectMapper mapper;

    @Before
    public void setupTest() {
        mapper = new DefaultObjectMapper();
    }

    @Test
    public void testSerde_1() throws IOException {
        KinesisRegion kinesisRegionUs1 = KinesisRegion.US_EAST_1;
        Assert.assertEquals("\"us-east-1\"", mapper.writeValueAsString(kinesisRegionUs1));
    }

    @Test
    public void testSerde_2() throws IOException {
        KinesisRegion kinesisRegionAp1 = KinesisRegion.AP_NORTHEAST_1;
        Assert.assertEquals("\"ap-northeast-1\"", mapper.writeValueAsString(kinesisRegionAp1));
    }

    @Test
    public void testSerde_3() throws IOException {
        KinesisRegion kinesisRegion = mapper.readValue(mapper.writeValueAsString(mapper.readValue("\"us-east-1\"", KinesisRegion.class)), KinesisRegion.class);
        Assert.assertEquals(kinesisRegion, KinesisRegion.US_EAST_1);
    }

    @Test
    public void testGetEndpoint_1() {
        Assert.assertEquals("kinesis.cn-north-1.amazonaws.com.cn", KinesisRegion.CN_NORTH_1.getEndpoint());
    }

    @Test
    public void testGetEndpoint_2() {
        Assert.assertEquals("kinesis.us-east-1.amazonaws.com", KinesisRegion.US_EAST_1.getEndpoint());
    }
}
