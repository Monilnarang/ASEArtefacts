package org.apache.druid.indexer;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.indexer.granularity.UniformGranularitySpec;
import org.apache.druid.indexer.partitions.HashedPartitionsSpec;
import org.apache.druid.indexer.partitions.PartitionsSpec;
import org.apache.druid.indexer.partitions.SingleDimensionPartitionsSpec;
import org.apache.druid.indexer.updater.MetadataStorageUpdaterJobSpec;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.PeriodGranularity;
import org.apache.druid.metadata.MetadataStorageConnectorConfig;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;

public class HadoopIngestionSpecTest_Purified {

    private static final ObjectMapper JSON_MAPPER;

    static {
        JSON_MAPPER = new DefaultObjectMapper();
        JSON_MAPPER.setInjectableValues(new InjectableValues.Std().addValue(ObjectMapper.class, JSON_MAPPER));
    }

    private static <T> T jsonReadWriteRead(String s, Class<T> klass) {
        try {
            return JSON_MAPPER.readValue(JSON_MAPPER.writeValueAsBytes(JSON_MAPPER.readValue(s, klass)), klass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUniqueId_1() {
        final HadoopIngestionSpec schema = jsonReadWriteRead("{\"uniqueId\" : \"test_unique_id\"}", HadoopIngestionSpec.class);
        Assert.assertEquals("test_unique_id", schema.getUniqueId());
    }

    @Test
    public void testUniqueId_2() {
        final String id1 = jsonReadWriteRead("{}", HadoopIngestionSpec.class).getUniqueId();
        final String id2 = jsonReadWriteRead("{}", HadoopIngestionSpec.class).getUniqueId();
        Assert.assertNotEquals(id1, id2);
    }

    @Test
    public void testContext_1() {
        final HadoopIngestionSpec schemaWithContext = jsonReadWriteRead("{\"context\" : { \"userid\" : 12345, \"cluster\": \"prod\" } }", HadoopIngestionSpec.class);
        Assert.assertEquals(ImmutableMap.of("userid", 12345, "cluster", "prod"), schemaWithContext.getContext());
    }

    @Test
    public void testContext_2() {
        final HadoopIngestionSpec schemaWithoutContext = jsonReadWriteRead("{\n" + "    \"dataSchema\": {\n" + "     \"dataSource\": \"foo\",\n" + "     \"metricsSpec\": [],\n" + "        \"granularitySpec\": {\n" + "                \"type\": \"uniform\",\n" + "                \"segmentGranularity\": \"hour\",\n" + "                \"intervals\": [\"2012-01-01/P1D\"]\n" + "        }\n" + "    }\n" + "}", HadoopIngestionSpec.class);
        Assert.assertEquals(ImmutableMap.of(), schemaWithoutContext.getContext());
    }
}
