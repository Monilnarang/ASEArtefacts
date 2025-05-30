package io.undertow.util;

import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class CanonicalPathUtilsTestCase_Purified {

    @Test
    public void testCanonicalization_1() {
        Assert.assertSame("a/b/c", CanonicalPathUtils.canonicalize("a/b/c"));
    }

    @Test
    public void testCanonicalization_2() {
        Assert.assertSame("a/b/c/", CanonicalPathUtils.canonicalize("a/b/c/"));
    }

    @Test
    public void testCanonicalization_3() {
        Assert.assertSame("aaaaa", CanonicalPathUtils.canonicalize("aaaaa"));
    }

    @Test
    public void testCanonicalization_4() {
        Assert.assertEquals("a./b", CanonicalPathUtils.canonicalize("a./b"));
    }

    @Test
    public void testCanonicalization_5() {
        Assert.assertEquals("a./.b", CanonicalPathUtils.canonicalize("a./.b"));
    }

    @Test
    public void testCanonicalization_6() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a//b"));
    }

    @Test
    public void testCanonicalization_7() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a///b"));
    }

    @Test
    public void testCanonicalization_8() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a////b"));
    }

    @Test
    public void testCanonicalization_9() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a/./b"));
    }

    @Test
    public void testCanonicalization_10() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a/././b"));
    }

    @Test
    public void testCanonicalization_11() {
        Assert.assertEquals("a/b/c", CanonicalPathUtils.canonicalize("a/./b/./c"));
    }

    @Test
    public void testCanonicalization_12() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a/./././b"));
    }

    @Test
    public void testCanonicalization_13() {
        Assert.assertEquals("a/b/", CanonicalPathUtils.canonicalize("a/./././b/./"));
    }

    @Test
    public void testCanonicalization_14() {
        Assert.assertEquals("a/b", CanonicalPathUtils.canonicalize("a/./././b/."));
    }

    @Test
    public void testCanonicalization_15() {
        Assert.assertEquals("/b", CanonicalPathUtils.canonicalize("/a/../b"));
    }

    @Test
    public void testCanonicalization_16() {
        Assert.assertEquals("/b", CanonicalPathUtils.canonicalize("/a/../c/../e/../b"));
    }

    @Test
    public void testCanonicalization_17() {
        Assert.assertEquals("/b", CanonicalPathUtils.canonicalize("/a/c/../../b"));
    }

    @Test
    public void testCanonicalization_18() {
        Assert.assertNull(CanonicalPathUtils.canonicalize("/a/../..", true));
    }

    @Test
    public void testCanonicalization_19() {
        Assert.assertNull(CanonicalPathUtils.canonicalize("/a/../../foo", true));
    }

    @Test
    public void testCanonicalization_20() {
        Assert.assertNull(CanonicalPathUtils.canonicalize("/../../a/b/bar", true));
    }

    @Test
    public void testCanonicalization_21() {
        Assert.assertEquals("/", CanonicalPathUtils.canonicalize("/a/../.."));
    }

    @Test
    public void testCanonicalization_22() {
        Assert.assertEquals("/foo", CanonicalPathUtils.canonicalize("/a/../../foo"));
    }

    @Test
    public void testCanonicalization_23() {
        Assert.assertEquals("/a/", CanonicalPathUtils.canonicalize("/a/"));
    }

    @Test
    public void testCanonicalization_24() {
        Assert.assertEquals("/", CanonicalPathUtils.canonicalize("/"));
    }

    @Test
    public void testCanonicalization_25() {
        Assert.assertEquals("/bbb/a", CanonicalPathUtils.canonicalize("/cc/../bbb/a/."));
    }

    @Test
    public void testCanonicalization_26() {
        Assert.assertEquals("/aaa/bbb/", CanonicalPathUtils.canonicalize("/aaa/bbb//////"));
    }

    @Test
    public void testCanonicalizationBackslash_1() {
        Assert.assertSame("a\\b\\c", CanonicalPathUtils.canonicalize("a\\b\\c"));
    }

    @Test
    public void testCanonicalizationBackslash_2() {
        Assert.assertSame("a\\b\\c\\", CanonicalPathUtils.canonicalize("a\\b\\c\\"));
    }

    @Test
    public void testCanonicalizationBackslash_3() {
        Assert.assertSame("aaaaa", CanonicalPathUtils.canonicalize("aaaaa"));
    }

    @Test
    public void testCanonicalizationBackslash_4() {
        Assert.assertEquals("a.\\b", CanonicalPathUtils.canonicalize("a.\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_5() {
        Assert.assertEquals("a.\\.b", CanonicalPathUtils.canonicalize("a.\\.b"));
    }

    @Test
    public void testCanonicalizationBackslash_6() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_7() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\\\\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_8() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\\\\\\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_9() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\.\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_10() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\.\\.\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_11() {
        Assert.assertEquals("a\\b\\c", CanonicalPathUtils.canonicalize("a\\.\\b\\.\\c"));
    }

    @Test
    public void testCanonicalizationBackslash_12() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\.\\.\\.\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_13() {
        Assert.assertEquals("a\\b\\", CanonicalPathUtils.canonicalize("a\\.\\.\\.\\b\\.\\"));
    }

    @Test
    public void testCanonicalizationBackslash_14() {
        Assert.assertEquals("a\\b", CanonicalPathUtils.canonicalize("a\\.\\.\\.\\b\\."));
    }

    @Test
    public void testCanonicalizationBackslash_15() {
        Assert.assertEquals("\\b", CanonicalPathUtils.canonicalize("\\a\\..\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_16() {
        Assert.assertEquals("\\b", CanonicalPathUtils.canonicalize("\\a\\..\\c\\..\\e\\..\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_17() {
        Assert.assertEquals("\\b", CanonicalPathUtils.canonicalize("\\a\\c\\..\\..\\b"));
    }

    @Test
    public void testCanonicalizationBackslash_18() {
        Assert.assertNull(CanonicalPathUtils.canonicalize("\\a\\..\\..", true));
    }

    @Test
    public void testCanonicalizationBackslash_19() {
        Assert.assertNull(CanonicalPathUtils.canonicalize("\\a\\..\\..\\foo", true));
    }

    @Test
    public void testCanonicalizationBackslash_20() {
        Assert.assertNull(CanonicalPathUtils.canonicalize("\\..\\..\\a\\b\\bar", true));
    }

    @Test
    public void testCanonicalizationBackslash_21() {
        Assert.assertEquals("/", CanonicalPathUtils.canonicalize("\\a\\..\\.."));
    }

    @Test
    public void testCanonicalizationBackslash_22() {
        Assert.assertEquals("\\foo", CanonicalPathUtils.canonicalize("\\a\\..\\..\\foo"));
    }

    @Test
    public void testCanonicalizationBackslash_23() {
        Assert.assertEquals("\\a\\", CanonicalPathUtils.canonicalize("\\a\\"));
    }

    @Test
    public void testCanonicalizationBackslash_24() {
        Assert.assertEquals("\\", CanonicalPathUtils.canonicalize("\\"));
    }

    @Test
    public void testCanonicalizationBackslash_25() {
        Assert.assertEquals("\\bbb\\a", CanonicalPathUtils.canonicalize("\\cc\\..\\bbb\\a\\."));
    }

    @Test
    public void testCanonicalizationBackslash_26() {
        Assert.assertEquals("\\aaa\\bbb\\", CanonicalPathUtils.canonicalize("\\aaa\\bbb\\\\\\\\\\\\"));
    }

    @Test
    public void testCanonicalizationBackslash_27() {
        Assert.assertEquals("/", CanonicalPathUtils.canonicalize("\\a/..\\./"));
    }

    @Test
    public void testCanonicalizationBackslash_28() {
        Assert.assertEquals("\\a/", CanonicalPathUtils.canonicalize("\\a\\b\\..\\./"));
    }

    @Test
    public void testCanonicalizationBackslash_29() {
        Assert.assertEquals("/a/b/c../d..\\", CanonicalPathUtils.canonicalize("/a/b/c../d..\\"));
    }

    @Test
    public void testCanonicalizationBackslash_30() {
        Assert.assertEquals("/a/d\\", CanonicalPathUtils.canonicalize("/a/b/c/..\\../d\\.\\"));
    }
}
