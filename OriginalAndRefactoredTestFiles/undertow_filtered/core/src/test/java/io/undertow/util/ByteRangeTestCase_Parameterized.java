package io.undertow.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ByteRangeTestCase_Parameterized {

    @Test
    public void testParse_1() {
        Assert.assertNull(ByteRange.parse(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParse_2to6")
    public void testParse_2to6(String param1) {
        Assert.assertNull(ByteRange.parse(param1));
    }

    static public Stream<Arguments> Provider_testParse_2to6() {
        return Stream.of(arguments("foo"), arguments("bytes=1"), arguments("bytes=a-"), arguments("foobarbaz"), arguments("bytes=--1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParse_7to8")
    public void testParse_7to8(int param1, String param2) {
        Assert.assertEquals(param1, ByteRange.parse(param2).getRanges());
    }

    static public Stream<Arguments> Provider_testParse_7to8() {
        return Stream.of(arguments(1, "bytes=2-"), arguments(1, "bytes=-20"));
    }
}
