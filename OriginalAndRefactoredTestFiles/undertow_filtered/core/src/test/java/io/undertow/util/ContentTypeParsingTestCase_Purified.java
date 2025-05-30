package io.undertow.util;

import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class ContentTypeParsingTestCase_Purified {

    @Test
    public void testCharsetParsing_1() {
        Assert.assertEquals(null, Headers.extractQuotedValueFromHeader("text/html; other-data=\"charset=UTF-8\"", "charset"));
    }

    @Test
    public void testCharsetParsing_2() {
        Assert.assertEquals(null, Headers.extractQuotedValueFromHeader("text/html;", "charset"));
    }

    @Test
    public void testCharsetParsing_3() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html; charset=\"UTF-8\"", "charset"));
    }

    @Test
    public void testCharsetParsing_4() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html; charset=UTF-8", "charset"));
    }

    @Test
    public void testCharsetParsing_5() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html; charset=\"UTF-8\"; foo=bar", "charset"));
    }

    @Test
    public void testCharsetParsing_6() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html; charset=UTF-8 foo=bar", "charset"));
    }

    @Test
    public void testCharsetParsing_7() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html; badcharset=bad charset=UTF-8 foo=bar", "charset"));
    }

    @Test
    public void testCharsetParsing_8() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html;charset=UTF-8", "charset"));
    }

    @Test
    public void testCharsetParsing_9() {
        Assert.assertEquals("UTF-8", Headers.extractQuotedValueFromHeader("text/html;\tcharset=UTF-8", "charset"));
    }
}
