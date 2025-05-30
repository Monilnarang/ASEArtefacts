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
public class CanonicalPathUtilsTestCase_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testCanonicalization_1_1to2_2to3_3")
    public void testCanonicalization_1_1to2_2to3_3(String param1, String param2) {
        Assert.assertSame(param1, CanonicalPathUtils.canonicalize(param2));
    }

    static public Stream<Arguments> Provider_testCanonicalization_1_1to2_2to3_3() {
        return Stream.of(arguments("a/b/c", "a/b/c"), arguments("a/b/c/", "a/b/c/"), arguments("aaaaa", "aaaaa"), arguments("a\\b\\c", "a\\b\\c"), arguments("a\\b\\c\\", "a\\b\\c\\"), arguments("aaaaa", "aaaaa"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCanonicalization_4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to13_13to14_14to15_15to16_16to17_17_21_21to22_22to23_23to24_24to25_25to26_26to30")
    public void testCanonicalization_4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to13_13to14_14to15_15to16_16to17_17_21_21to22_22to23_23to24_24to25_25to26_26to30(String param1, String param2) {
        Assert.assertEquals(param1, CanonicalPathUtils.canonicalize(param2));
    }

    static public Stream<Arguments> Provider_testCanonicalization_4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to13_13to14_14to15_15to16_16to17_17_21_21to22_22to23_23to24_24to25_25to26_26to30() {
        return Stream.of(arguments("a./b", "a./b"), arguments("a./.b", "a./.b"), arguments("a/b", "a//b"), arguments("a/b", "a///b"), arguments("a/b", "a////b"), arguments("a/b", "a/./b"), arguments("a/b", "a/././b"), arguments("a/b/c", "a/./b/./c"), arguments("a/b", "a/./././b"), arguments("a/b/", "a/./././b/./"), arguments("a/b", "a/./././b/."), arguments("/b", "/a/../b"), arguments("/b", "/a/../c/../e/../b"), arguments("/b", "/a/c/../../b"), arguments("/", "/a/../.."), arguments("/foo", "/a/../../foo"), arguments("/a/", "/a/"), arguments("/", "/"), arguments("/bbb/a", "/cc/../bbb/a/."), arguments("/aaa/bbb/", "/aaa/bbb//////"), arguments("a.\\b", "a.\\b"), arguments("a.\\.b", "a.\\.b"), arguments("a\\b", "a\\\\b"), arguments("a\\b", "a\\\\\\b"), arguments("a\\b", "a\\\\\\\\b"), arguments("a\\b", "a\\.\\b"), arguments("a\\b", "a\\.\\.\\b"), arguments("a\\b\\c", "a\\.\\b\\.\\c"), arguments("a\\b", "a\\.\\.\\.\\b"), arguments("a\\b\\", "a\\.\\.\\.\\b\\.\\"), arguments("a\\b", "a\\.\\.\\.\\b\\."), arguments("\\b", "\\a\\..\\b"), arguments("\\b", "\\a\\..\\c\\..\\e\\..\\b"), arguments("\\b", "\\a\\c\\..\\..\\b"), arguments("/", "\\a\\..\\.."), arguments("\\foo", "\\a\\..\\..\\foo"), arguments("\\a\\", "\\a\\"), arguments("\\", "\\"), arguments("\\bbb\\a", "\\cc\\..\\bbb\\a\\."), arguments("\\aaa\\bbb\\", "\\aaa\\bbb\\\\\\\\\\\\"), arguments("/", "\\a/..\\./"), arguments("\\a/", "\\a\\b\\..\\./"), arguments("/a/b/c../d..\\", "/a/b/c../d..\\"), arguments("/a/d\\", "/a/b/c/..\\../d\\.\\"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCanonicalization_18_18to19_19to20_20")
    public void testCanonicalization_18_18to19_19to20_20(String param1) {
        Assert.assertNull(CanonicalPathUtils.canonicalize(param1, true));
    }

    static public Stream<Arguments> Provider_testCanonicalization_18_18to19_19to20_20() {
        return Stream.of(arguments("/a/../.."), arguments("/a/../../foo"), arguments("/../../a/b/bar"), arguments("\\a\\..\\.."), arguments("\\a\\..\\..\\foo"), arguments("\\..\\..\\a\\b\\bar"));
    }
}
