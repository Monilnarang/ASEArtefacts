package org.apache.druid.data.input.thrift;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.druid.data.input.InputRow;
import org.apache.druid.data.input.impl.DimensionsSpec;
import org.apache.druid.data.input.impl.JSONParseSpec;
import org.apache.druid.data.input.impl.JavaScriptParseSpec;
import org.apache.druid.data.input.impl.ParseSpec;
import org.apache.druid.data.input.impl.StringDimensionSchema;
import org.apache.druid.data.input.impl.TimestampSpec;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.parsers.JSONPathFieldSpec;
import org.apache.druid.java.util.common.parsers.JSONPathFieldType;
import org.apache.druid.java.util.common.parsers.JSONPathSpec;
import org.apache.druid.js.JavaScriptConfig;
import org.apache.hadoop.io.BytesWritable;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.nio.ByteBuffer;

public class ThriftInputRowParserTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ParseSpec parseSpec;

    @Before
    public void setUp() {
        parseSpec = new JSONParseSpec(new TimestampSpec("date", "auto", null), new DimensionsSpec(Lists.newArrayList(new StringDimensionSchema("title"), new StringDimensionSchema("lastName"))), new JSONPathSpec(true, Lists.newArrayList(new JSONPathFieldSpec(JSONPathFieldType.ROOT, "title", "title"), new JSONPathFieldSpec(JSONPathFieldType.PATH, "lastName", "$.author.lastName"))), null, null);
    }

    private void serializationAndTest(ThriftInputRowParser parser, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        InputRow row1 = parser.parseBatch(buffer).get(0);
        Assert.assertEquals("title", row1.getDimension("title").get(0));
        InputRow row2 = parser.parseBatch(new BytesWritable(bytes)).get(0);
        Assert.assertEquals("last", row2.getDimension("lastName").get(0));
    }

    @Test
    public void testGetThriftClass_1() throws Exception {
        ThriftInputRowParser parser1 = new ThriftInputRowParser(parseSpec, "example/book.jar", "org.apache.druid.data.input.thrift.Book");
        Assert.assertEquals("org.apache.druid.data.input.thrift.Book", parser1.getThriftClass().getName());
    }

    @Test
    public void testGetThriftClass_2() throws Exception {
        ThriftInputRowParser parser2 = new ThriftInputRowParser(parseSpec, null, "org.apache.druid.data.input.thrift.Book");
        Assert.assertEquals("org.apache.druid.data.input.thrift.Book", parser2.getThriftClass().getName());
    }
}
