package org.jfree.chart.imagemap;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.util.StringUtils;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;
import java.awt.Shape;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageMapUtilsTest_Purified {

    @Test
    public void testHTMLEscape_1() {
        assertEquals("", ImageMapUtils.htmlEscape(""));
    }

    @Test
    public void testHTMLEscape_2() {
        assertEquals("abc", ImageMapUtils.htmlEscape("abc"));
    }

    @Test
    public void testHTMLEscape_3() {
        assertEquals("&amp;", ImageMapUtils.htmlEscape("&"));
    }

    @Test
    public void testHTMLEscape_4() {
        assertEquals("&quot;", ImageMapUtils.htmlEscape("\""));
    }

    @Test
    public void testHTMLEscape_5() {
        assertEquals("&lt;", ImageMapUtils.htmlEscape("<"));
    }

    @Test
    public void testHTMLEscape_6() {
        assertEquals("&gt;", ImageMapUtils.htmlEscape(">"));
    }

    @Test
    public void testHTMLEscape_7() {
        assertEquals("&#39;", ImageMapUtils.htmlEscape("\'"));
    }

    @Test
    public void testHTMLEscape_8() {
        assertEquals("&#092;abc", ImageMapUtils.htmlEscape("\\abc"));
    }

    @Test
    public void testHTMLEscape_9() {
        assertEquals("abc\n", ImageMapUtils.htmlEscape("abc\n"));
    }

    @Test
    public void testJavascriptEscape_1() {
        assertEquals("", ImageMapUtils.javascriptEscape(""));
    }

    @Test
    public void testJavascriptEscape_2() {
        assertEquals("abc", ImageMapUtils.javascriptEscape("abc"));
    }

    @Test
    public void testJavascriptEscape_3() {
        assertEquals("\\\'", ImageMapUtils.javascriptEscape("\'"));
    }

    @Test
    public void testJavascriptEscape_4() {
        assertEquals("\\\"", ImageMapUtils.javascriptEscape("\""));
    }

    @Test
    public void testJavascriptEscape_5() {
        assertEquals("\\\\", ImageMapUtils.javascriptEscape("\\"));
    }
}
