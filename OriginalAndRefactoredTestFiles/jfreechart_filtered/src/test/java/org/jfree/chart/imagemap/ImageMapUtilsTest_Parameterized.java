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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ImageMapUtilsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testHTMLEscape_1to9")
    public void testHTMLEscape_1to9(String param1, String param2) {
        assertEquals(param1, ImageMapUtils.htmlEscape(param2));
    }

    static public Stream<Arguments> Provider_testHTMLEscape_1to9() {
        return Stream.of(arguments("", ""), arguments("abc", "abc"), arguments("&amp;", "&"), arguments("&quot;", "\""), arguments("&lt;", "<"), arguments("&gt;", ">"), arguments("&#39;", "\'"), arguments("&#092;abc", "\\abc"), arguments("abc\n", "abc\n"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testJavascriptEscape_1to5")
    public void testJavascriptEscape_1to5(String param1, String param2) {
        assertEquals(param1, ImageMapUtils.javascriptEscape(param2));
    }

    static public Stream<Arguments> Provider_testJavascriptEscape_1to5() {
        return Stream.of(arguments("", ""), arguments("abc", "abc"), arguments("\\\'", "\'"), arguments("\\\"", "\""), arguments("\\\\", "\\"));
    }
}
