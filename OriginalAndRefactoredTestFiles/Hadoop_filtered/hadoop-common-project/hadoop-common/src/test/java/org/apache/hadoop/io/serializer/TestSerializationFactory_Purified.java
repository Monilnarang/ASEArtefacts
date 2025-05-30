package org.apache.hadoop.io.serializer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.io.Writable;
import org.slf4j.event.Level;

public class TestSerializationFactory_Purified {

    static {
        GenericTestUtils.setLogLevel(SerializationFactory.LOG, Level.TRACE);
    }

    static Configuration conf;

    static SerializationFactory factory;

    @BeforeClass
    public static void setup() throws Exception {
        conf = new Configuration();
        factory = new SerializationFactory(conf);
    }

    @Test
    public void testGetSerializer_1() {
        assertNotNull("A valid class must be returned for default Writable SerDe", factory.getSerializer(Writable.class));
    }

    @Test
    public void testGetSerializer_2() {
        assertNull("A null should be returned if there are no serializers found.", factory.getSerializer(TestSerializationFactory.class));
    }

    @Test
    public void testGetDeserializer_1() {
        assertNotNull("A valid class must be returned for default Writable SerDe", factory.getDeserializer(Writable.class));
    }

    @Test
    public void testGetDeserializer_2() {
        assertNull("A null should be returned if there are no deserializers found", factory.getDeserializer(TestSerializationFactory.class));
    }
}
