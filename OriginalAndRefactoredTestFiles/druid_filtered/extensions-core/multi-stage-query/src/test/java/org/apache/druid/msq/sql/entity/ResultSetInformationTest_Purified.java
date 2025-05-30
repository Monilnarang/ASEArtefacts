package org.apache.druid.msq.sql.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.druid.sql.http.ResultFormat;
import org.junit.Assert;
import org.junit.Test;

public class ResultSetInformationTest_Purified {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static final ResultSetInformation RESULTS = new ResultSetInformation(1L, 1L, ResultFormat.OBJECT, "ds", null, ImmutableList.of(new PageInformation(0, null, 1L)));

    public static final ResultSetInformation RESULTS_1 = new ResultSetInformation(1L, 1L, ResultFormat.OBJECT, "ds", ImmutableList.of(new String[] { "1" }, new String[] { "2" }, new String[] { "3" }), ImmutableList.of(new PageInformation(0, 1L, 1L)));

    public static final String JSON_STRING = "{\"numTotalRows\":1,\"totalSizeInBytes\":1,\"resultFormat\":\"object\",\"dataSource\":\"ds\",\"pages\":[{\"id\":0,\"sizeInBytes\":1}]}";

    public static final String JSON_STRING_1 = "{\"numTotalRows\":1,\"totalSizeInBytes\":1,\"resultFormat\":\"object\",\"dataSource\":\"ds\",\"sampleRecords\":[[\"1\"],[\"2\"],[\"3\"]],\"pages\":[{\"id\":0,\"numRows\":1,\"sizeInBytes\":1}]}";

    @Test
    public void sanityTest_1() throws JsonProcessingException {
        Assert.assertEquals(JSON_STRING, MAPPER.writeValueAsString(RESULTS));
    }

    @Test
    public void sanityTest_2() throws JsonProcessingException {
        Assert.assertEquals(RESULTS, MAPPER.readValue(MAPPER.writeValueAsString(RESULTS), ResultSetInformation.class));
    }

    @Test
    public void sanityTest_3() throws JsonProcessingException {
        Assert.assertEquals(RESULTS.hashCode(), MAPPER.readValue(MAPPER.writeValueAsString(RESULTS), ResultSetInformation.class).hashCode());
    }

    @Test
    public void sanityTest_4() throws JsonProcessingException {
        Assert.assertEquals("ResultSetInformation{numTotalRows=1, totalSizeInBytes=1, resultFormat=object, records=null, dataSource='ds', pages=[PageInformation{id=0, numRows=null, sizeInBytes=1, worker=null, partition=null}]}", RESULTS.toString());
    }
}
