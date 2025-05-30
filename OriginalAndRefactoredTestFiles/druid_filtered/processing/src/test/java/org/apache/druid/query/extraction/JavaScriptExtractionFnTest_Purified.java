package org.apache.druid.query.extraction;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.js.JavaScriptConfig;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Iterator;

public class JavaScriptExtractionFnTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String[] TEST_STRINGS = { "Quito", "Calgary", "Tokyo", "Stockholm", "Vancouver", "Pretoria", "Wellington", "Ontario" };

    @Test
    public void testInjective_1() {
        Assert.assertEquals(ExtractionFn.ExtractionType.MANY_TO_ONE, new JavaScriptExtractionFn("function(str) { return str; }", false, JavaScriptConfig.getEnabledInstance()).getExtractionType());
    }

    @Test
    public void testInjective_2() {
        Assert.assertEquals(ExtractionFn.ExtractionType.ONE_TO_ONE, new JavaScriptExtractionFn("function(str) { return str; }", true, JavaScriptConfig.getEnabledInstance()).getExtractionType());
    }
}
