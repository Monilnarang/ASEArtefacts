package org.apache.druid.java.util.common;

import com.google.common.collect.ImmutableList;
import org.apache.druid.collections.ResourceHolder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.UnsupportedEncodingException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.List;

public class StringUtilsTest_Purified {

    private static final List<String> COMPARE_TEST_STRINGS = ImmutableList.of("（請參見已被刪除版本）", "請參見已被刪除版本", "שָׁלוֹם", "＋{{[[Template:別名重定向|別名重定向]]}}", "\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDC4D", "\uD83D\uDCA9", "", "f", "fo", "\uD83D\uDE42", "\uD83E\uDEE5", "\uD83E\uDD20", "quick", "brown", "fox");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @SuppressWarnings("MalformedFormatString")
    @Test
    public void testNonStrictFormat_1() {
        Assert.assertEquals("test%d; format", StringUtils.nonStrictFormat("test%d", "format"));
    }

    @SuppressWarnings("MalformedFormatString")
    @Test
    public void testNonStrictFormat_2() {
        Assert.assertEquals("test%s%s; format", StringUtils.nonStrictFormat("test%s%s", "format"));
    }

    @Test
    public void testRemoveChar_1() {
        Assert.assertEquals("123", StringUtils.removeChar("123", ','));
    }

    @Test
    public void testRemoveChar_2() {
        Assert.assertEquals("123", StringUtils.removeChar("123,", ','));
    }

    @Test
    public void testRemoveChar_3() {
        Assert.assertEquals("123", StringUtils.removeChar(",1,,2,3,", ','));
    }

    @Test
    public void testRemoveChar_4() {
        Assert.assertEquals("", StringUtils.removeChar(",,", ','));
    }

    @Test
    public void testReplaceChar_1() {
        Assert.assertEquals("123", StringUtils.replaceChar("123", ',', "x"));
    }

    @Test
    public void testReplaceChar_2() {
        Assert.assertEquals("12345", StringUtils.replaceChar("123,", ',', "45"));
    }

    @Test
    public void testReplaceChar_3() {
        Assert.assertEquals("", StringUtils.replaceChar("", 'a', "bb"));
    }

    @Test
    public void testReplaceChar_4() {
        Assert.assertEquals("bb", StringUtils.replaceChar("a", 'a', "bb"));
    }

    @Test
    public void testReplaceChar_5() {
        Assert.assertEquals("bbbb", StringUtils.replaceChar("aa", 'a', "bb"));
    }

    @Test
    public void testReplace_1() {
        Assert.assertEquals("x1x2x3x", StringUtils.replace("123", "", "x"));
    }

    @Test
    public void testReplace_2() {
        Assert.assertEquals("12345", StringUtils.replace("123,", ",", "45"));
    }

    @Test
    public void testReplace_3() {
        Assert.assertEquals("", StringUtils.replace("", "a", "bb"));
    }

    @Test
    public void testReplace_4() {
        Assert.assertEquals("bb", StringUtils.replace("a", "a", "bb"));
    }

    @Test
    public void testReplace_5() {
        Assert.assertEquals("bba", StringUtils.replace("aaa", "aa", "bb"));
    }

    @Test
    public void testReplace_6() {
        Assert.assertEquals("bcb", StringUtils.replace("aacaa", "aa", "b"));
    }

    @Test
    public void testReplace_7() {
        Assert.assertEquals("bb", StringUtils.replace("aaaa", "aa", "b"));
    }

    @Test
    public void testReplace_8() {
        Assert.assertEquals("", StringUtils.replace("aaaa", "aa", ""));
    }

    @Test
    public void testEncodeForFormat_1() {
        Assert.assertEquals("x %% a %%s", StringUtils.encodeForFormat("x % a %s"));
    }

    @Test
    public void testEncodeForFormat_2() {
        Assert.assertEquals("", StringUtils.encodeForFormat(""));
    }

    @Test
    public void testEncodeForFormat_3() {
        Assert.assertNull(StringUtils.encodeForFormat(null));
    }

    @Test
    public void testRepeat_1() {
        Assert.assertEquals("", StringUtils.repeat("foo", 0));
    }

    @Test
    public void testRepeat_2() {
        Assert.assertEquals("foo", StringUtils.repeat("foo", 1));
    }

    @Test
    public void testRepeat_3() {
        Assert.assertEquals("foofoofoo", StringUtils.repeat("foo", 3));
    }

    @Test
    public void testRepeat_4() {
        Assert.assertEquals("", StringUtils.repeat("", 0));
    }

    @Test
    public void testRepeat_5() {
        Assert.assertEquals("", StringUtils.repeat("", 1));
    }

    @Test
    public void testRepeat_6() {
        Assert.assertEquals("", StringUtils.repeat("", 3));
    }

    @Test
    public void testRepeat_7() {
        Assert.assertEquals("", StringUtils.repeat("foo", -1));
    }

    @Test
    public void testChop_1() {
        Assert.assertEquals("foo", StringUtils.chop("foo", 5));
    }

    @Test
    public void testChop_2() {
        Assert.assertEquals("fo", StringUtils.chop("foo", 2));
    }

    @Test
    public void testChop_3() {
        Assert.assertEquals("", StringUtils.chop("foo", 0));
    }

    @Test
    public void testChop_4() {
        Assert.assertEquals("smile 🙂 for", StringUtils.chop("smile 🙂 for the camera", 14));
    }

    @Test
    public void testChop_5() {
        Assert.assertEquals("smile 🙂", StringUtils.chop("smile 🙂 for the camera", 10));
    }

    @Test
    public void testChop_6() {
        Assert.assertEquals("smile ", StringUtils.chop("smile 🙂 for the camera", 9));
    }

    @Test
    public void testChop_7() {
        Assert.assertEquals("smile ", StringUtils.chop("smile 🙂 for the camera", 8));
    }

    @Test
    public void testChop_8() {
        Assert.assertEquals("smile ", StringUtils.chop("smile 🙂 for the camera", 7));
    }

    @Test
    public void testChop_9() {
        Assert.assertEquals("smile ", StringUtils.chop("smile 🙂 for the camera", 6));
    }

    @Test
    public void testChop_10() {
        Assert.assertEquals("smile", StringUtils.chop("smile 🙂 for the camera", 5));
    }

    @Test
    public void testFastLooseChop_1() {
        Assert.assertEquals("foo", StringUtils.fastLooseChop("foo", 5));
    }

    @Test
    public void testFastLooseChop_2() {
        Assert.assertEquals("fo", StringUtils.fastLooseChop("foo", 2));
    }

    @Test
    public void testFastLooseChop_3() {
        Assert.assertEquals("", StringUtils.fastLooseChop("foo", 0));
    }

    @Test
    public void testFastLooseChop_4() {
        Assert.assertEquals("smile 🙂 for", StringUtils.fastLooseChop("smile 🙂 for the camera", 12));
    }

    @Test
    public void testFastLooseChop_5() {
        Assert.assertEquals("smile 🙂 ", StringUtils.fastLooseChop("smile 🙂 for the camera", 9));
    }

    @Test
    public void testFastLooseChop_6() {
        Assert.assertEquals("smile 🙂", StringUtils.fastLooseChop("smile 🙂 for the camera", 8));
    }

    @Test
    public void testFastLooseChop_7() {
        Assert.assertEquals("smile \uD83D", StringUtils.fastLooseChop("smile 🙂 for the camera", 7));
    }

    @Test
    public void testFastLooseChop_8() {
        Assert.assertEquals("smile ", StringUtils.fastLooseChop("smile 🙂 for the camera", 6));
    }

    @Test
    public void testFastLooseChop_9() {
        Assert.assertEquals("smile", StringUtils.fastLooseChop("smile 🙂 for the camera", 5));
    }
}
