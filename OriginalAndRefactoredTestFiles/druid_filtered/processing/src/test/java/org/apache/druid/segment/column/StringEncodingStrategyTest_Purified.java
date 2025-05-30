package org.apache.druid.segment.column;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.segment.data.FrontCodedIndexed;
import org.junit.Assert;
import org.junit.Test;

public class StringEncodingStrategyTest_Purified {

    private static final ObjectMapper JSON_MAPPER = new DefaultObjectMapper();

    @Test
    public void testFrontCodedDefaultSerde_1_testMerged_1() throws JsonProcessingException {
        StringEncodingStrategy frontCoded = new StringEncodingStrategy.FrontCoded(null, null);
        String there = JSON_MAPPER.writeValueAsString(frontCoded);
        StringEncodingStrategy andBackAgain = JSON_MAPPER.readValue(there, StringEncodingStrategy.class);
        Assert.assertEquals(frontCoded, andBackAgain);
        Assert.assertEquals(FrontCodedIndexed.DEFAULT_BUCKET_SIZE, ((StringEncodingStrategy.FrontCoded) andBackAgain).getBucketSize());
        Assert.assertEquals(FrontCodedIndexed.DEFAULT_VERSION, ((StringEncodingStrategy.FrontCoded) andBackAgain).getFormatVersion());
    }

    @Test
    public void testFrontCodedDefaultSerde_4() throws JsonProcessingException {
        Assert.assertEquals(FrontCodedIndexed.V0, FrontCodedIndexed.DEFAULT_VERSION);
    }
}
