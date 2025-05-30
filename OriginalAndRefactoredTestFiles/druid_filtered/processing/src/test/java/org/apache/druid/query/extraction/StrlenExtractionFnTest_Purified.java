package org.apache.druid.query.extraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class StrlenExtractionFnTest_Purified {

    @Test
    public void testApply_1() {
        Assert.assertNull(StrlenExtractionFn.instance().apply(null));
    }

    @Test
    public void testApply_2() {
        Assert.assertEquals("0", StrlenExtractionFn.instance().apply(""));
    }

    @Test
    public void testApply_3() {
        Assert.assertEquals("1", StrlenExtractionFn.instance().apply("x"));
    }

    @Test
    public void testApply_4() {
        Assert.assertEquals("3", StrlenExtractionFn.instance().apply("foo"));
    }

    @Test
    public void testApply_5() {
        Assert.assertEquals("3", StrlenExtractionFn.instance().apply("föo"));
    }

    @Test
    public void testApply_6() {
        Assert.assertEquals("2", StrlenExtractionFn.instance().apply("\uD83D\uDE02"));
    }

    @Test
    public void testApply_7() {
        Assert.assertEquals("1", StrlenExtractionFn.instance().apply(1));
    }

    @Test
    public void testApply_8() {
        Assert.assertEquals("2", StrlenExtractionFn.instance().apply(-1));
    }
}
