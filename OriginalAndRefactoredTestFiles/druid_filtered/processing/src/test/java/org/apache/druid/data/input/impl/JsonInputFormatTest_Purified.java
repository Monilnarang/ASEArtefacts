package org.apache.druid.data.input.impl;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.data.input.InputFormat;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.parsers.JSONPathFieldSpec;
import org.apache.druid.java.util.common.parsers.JSONPathFieldType;
import org.apache.druid.java.util.common.parsers.JSONPathSpec;
import org.apache.druid.utils.CompressionUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;

public class JsonInputFormatTest_Purified {

    @Test
    public void testWithLineSplittableStatic_1_testMerged_1() {
        final JsonInputFormat format = new JsonInputFormat(new JSONPathSpec(true, ImmutableList.of(new JSONPathFieldSpec(JSONPathFieldType.ROOT, "root_baz", "baz"), new JSONPathFieldSpec(JSONPathFieldType.ROOT, "root_baz2", "baz2"), new JSONPathFieldSpec(JSONPathFieldType.PATH, "path_omg", "$.o.mg"), new JSONPathFieldSpec(JSONPathFieldType.PATH, "path_omg2", "$.o.mg2"), new JSONPathFieldSpec(JSONPathFieldType.JQ, "jq_omg", ".o.mg"), new JSONPathFieldSpec(JSONPathFieldType.JQ, "jq_omg2", ".o.mg2"), new JSONPathFieldSpec(JSONPathFieldType.TREE, "tree_omg", null, Arrays.asList("o", "mg")), new JSONPathFieldSpec(JSONPathFieldType.TREE, "tree_omg2", null, Arrays.asList("o", "mg2")))), ImmutableMap.of(Feature.ALLOW_COMMENTS.name(), true, Feature.ALLOW_UNQUOTED_FIELD_NAMES.name(), false), true, false, false);
        Assert.assertTrue(format.isLineSplittable());
        Assert.assertFalse(((JsonInputFormat) JsonInputFormat.withLineSplittable(format, false)).isLineSplittable());
    }

    @Test
    public void testWithLineSplittableStatic_3() {
        final InputFormat noopInputFormat = JsonInputFormat.withLineSplittable(new NoopInputFormat(), false);
        MatcherAssert.assertThat(noopInputFormat, CoreMatchers.instanceOf(NoopInputFormat.class));
    }
}
