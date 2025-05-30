package org.apache.druid.msq.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.error.DruidException;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;

public class MSQTaskQueryMakerUtilsTest_Purified {

    @Test
    public void maskSensitiveJsonKeys_1() {
        String sql1 = "\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"},\\\"secretAccessKey\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"}}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"";
        Assert.assertEquals("\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":<masked>,\\\"secretAccessKey\\\":<masked>}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"", MSQTaskQueryMakerUtils.maskSensitiveJsonKeys(sql1));
    }

    @Test
    public void maskSensitiveJsonKeys_2() {
        String sql2 = "\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\"  :{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"},\\\"secretAccessKey\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"}}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"";
        Assert.assertEquals("\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\"  :<masked>,\\\"secretAccessKey\\\":<masked>}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"", MSQTaskQueryMakerUtils.maskSensitiveJsonKeys(sql2));
    }

    @Test
    public void maskSensitiveJsonKeys_3() {
        String sql3 = "\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":  {\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"},\\\"secretAccessKey\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"}}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"";
        Assert.assertEquals("\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":  <masked>,\\\"secretAccessKey\\\":<masked>}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"", MSQTaskQueryMakerUtils.maskSensitiveJsonKeys(sql3));
    }

    @Test
    public void maskSensitiveJsonKeys_4() {
        String sql4 = "\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":{  \\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"},\\\"secretAccessKey\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"}}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"";
        Assert.assertEquals("\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":<masked>,\\\"secretAccessKey\\\":<masked>}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"", MSQTaskQueryMakerUtils.maskSensitiveJsonKeys(sql4));
    }

    @Test
    public void maskSensitiveJsonKeys_5() {
        String sql5 = "\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"  },\\\"secretAccessKey\\\":{\\\"type\\\":\\\"default\\\",\\\"password\\\":\\\"secret_pass\\\"}}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"";
        Assert.assertEquals("\"REPLACE INTO table " + "OVERWRITE ALL\\n" + "WITH ext AS " + "(SELECT *\\nFROM TABLE(\\n  " + "EXTERN(\\n    '{\\\"type\\\":\\\"s3\\\",\\\"prefixes\\\":[\\\"s3://prefix\\\"],\\\"properties\\\":{\\\"accessKeyId\\\":<masked>,\\\"secretAccessKey\\\":<masked>}}',\\n" + "'{\\\"type\\\":\\\"json\\\"}',\\n" + "'[{\\\"name\\\":\\\"time\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"string\\\"}]'\\n  )\\n))\\n" + "SELECT\\n  TIME_PARSE(\\\"time\\\") AS __time,\\n  name,\\n  country " + "FROM ext\\n" + "PARTITIONED BY DAY\"", MSQTaskQueryMakerUtils.maskSensitiveJsonKeys(sql5));
    }
}
