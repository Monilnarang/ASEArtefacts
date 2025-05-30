package io.undertow.util;

import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Category(UnitTest.class)
public class ContentTypeParsingTestCase_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testCharsetParsing_1to2")
    public void testCharsetParsing_1to2(String param1, String param2) {
        Assert.assertEquals(param1, Headers.extractQuotedValueFromHeader(param2, "charset"));
    }

    static public Stream<Arguments> Provider_testCharsetParsing_1to2() {
        return Stream.of(arguments("text/html; other-data=\"charset=UTF-8\"", "charset"), arguments("text/html;", "charset"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCharsetParsing_3to9")
    public void testCharsetParsing_3to9(String param1, String param2, String param3) {
        Assert.assertEquals(param1, Headers.extractQuotedValueFromHeader(param2, param3));
    }

    static public Stream<Arguments> Provider_testCharsetParsing_3to9() {
        return Stream.of(arguments("UTF-8", "text/html; charset=\"UTF-8\"", "charset"), arguments("UTF-8", "text/html; charset=UTF-8", "charset"), arguments("UTF-8", "text/html; charset=\"UTF-8\"; foo=bar", "charset"), arguments("UTF-8", "text/html; charset=UTF-8 foo=bar", "charset"), arguments("UTF-8", "text/html; badcharset=bad charset=UTF-8 foo=bar", "charset"), arguments("UTF-8", "text/html;charset=UTF-8", "charset"), arguments("UTF-8", "text/html;\tcharset=UTF-8", "charset"));
    }
}
