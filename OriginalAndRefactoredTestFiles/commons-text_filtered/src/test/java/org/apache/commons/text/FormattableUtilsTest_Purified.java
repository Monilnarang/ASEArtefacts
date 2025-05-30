package org.apache.commons.text;

import static java.util.FormattableFlags.LEFT_JUSTIFY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.util.Formattable;
import java.util.Formatter;
import org.junit.jupiter.api.Test;

public class FormattableUtilsTest_Purified {

    static class SimplestFormattable implements Formattable {

        private final String text;

        SimplestFormattable(final String text) {
            this.text = text;
        }

        @Override
        public void formatTo(final Formatter formatter, final int flags, final int width, final int precision) {
            formatter.format(text);
        }
    }

    private Formatter createFormatter() {
        return new Formatter();
    }

    @Test
    public void testAlternatePadCharAndEllipsis_1() {
        assertEquals("foo", FormattableUtils.append("foo", createFormatter(), 0, -1, -1, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_2() {
        assertEquals("f*", FormattableUtils.append("foo", createFormatter(), 0, -1, 2, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_3() {
        assertEquals("_foo", FormattableUtils.append("foo", createFormatter(), 0, 4, -1, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_4() {
        assertEquals("___foo", FormattableUtils.append("foo", createFormatter(), 0, 6, -1, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_5() {
        assertEquals("_f*", FormattableUtils.append("foo", createFormatter(), 0, 3, 2, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_6() {
        assertEquals("___f*", FormattableUtils.append("foo", createFormatter(), 0, 5, 2, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_7() {
        assertEquals("foo_", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 4, -1, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_8() {
        assertEquals("foo___", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 6, -1, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_9() {
        assertEquals("f*_", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 3, 2, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_10() {
        assertEquals("f*___", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 5, 2, '_', "*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_11() {
        assertEquals("foo", FormattableUtils.append("foo", createFormatter(), 0, -1, -1, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_12() {
        assertEquals("+*", FormattableUtils.append("foo", createFormatter(), 0, -1, 2, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_13() {
        assertEquals("_foo", FormattableUtils.append("foo", createFormatter(), 0, 4, -1, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_14() {
        assertEquals("___foo", FormattableUtils.append("foo", createFormatter(), 0, 6, -1, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_15() {
        assertEquals("_+*", FormattableUtils.append("foo", createFormatter(), 0, 3, 2, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_16() {
        assertEquals("___+*", FormattableUtils.append("foo", createFormatter(), 0, 5, 2, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_17() {
        assertEquals("foo_", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 4, -1, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_18() {
        assertEquals("foo___", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 6, -1, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_19() {
        assertEquals("+*_", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 3, 2, '_', "+*").toString());
    }

    @Test
    public void testAlternatePadCharAndEllipsis_20() {
        assertEquals("+*___", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 5, 2, '_', "+*").toString());
    }

    @Test
    public void testDefaultAppend_1() {
        assertEquals("foo", FormattableUtils.append("foo", createFormatter(), 0, -1, -1).toString());
    }

    @Test
    public void testDefaultAppend_2() {
        assertEquals("fo", FormattableUtils.append("foo", createFormatter(), 0, -1, 2).toString());
    }

    @Test
    public void testDefaultAppend_3() {
        assertEquals(" foo", FormattableUtils.append("foo", createFormatter(), 0, 4, -1).toString());
    }

    @Test
    public void testDefaultAppend_4() {
        assertEquals("   foo", FormattableUtils.append("foo", createFormatter(), 0, 6, -1).toString());
    }

    @Test
    public void testDefaultAppend_5() {
        assertEquals(" fo", FormattableUtils.append("foo", createFormatter(), 0, 3, 2).toString());
    }

    @Test
    public void testDefaultAppend_6() {
        assertEquals("   fo", FormattableUtils.append("foo", createFormatter(), 0, 5, 2).toString());
    }

    @Test
    public void testDefaultAppend_7() {
        assertEquals("foo ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 4, -1).toString());
    }

    @Test
    public void testDefaultAppend_8() {
        assertEquals("foo   ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 6, -1).toString());
    }

    @Test
    public void testDefaultAppend_9() {
        assertEquals("fo ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 3, 2).toString());
    }

    @Test
    public void testDefaultAppend_10() {
        assertEquals("fo   ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 5, 2).toString());
    }

    @Test
    public void testEllipsis_1() {
        assertEquals("foo", FormattableUtils.append("foo", createFormatter(), 0, -1, -1, "*").toString());
    }

    @Test
    public void testEllipsis_2() {
        assertEquals("f*", FormattableUtils.append("foo", createFormatter(), 0, -1, 2, "*").toString());
    }

    @Test
    public void testEllipsis_3() {
        assertEquals(" foo", FormattableUtils.append("foo", createFormatter(), 0, 4, -1, "*").toString());
    }

    @Test
    public void testEllipsis_4() {
        assertEquals("   foo", FormattableUtils.append("foo", createFormatter(), 0, 6, -1, "*").toString());
    }

    @Test
    public void testEllipsis_5() {
        assertEquals(" f*", FormattableUtils.append("foo", createFormatter(), 0, 3, 2, "*").toString());
    }

    @Test
    public void testEllipsis_6() {
        assertEquals("   f*", FormattableUtils.append("foo", createFormatter(), 0, 5, 2, "*").toString());
    }

    @Test
    public void testEllipsis_7() {
        assertEquals("foo ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 4, -1, "*").toString());
    }

    @Test
    public void testEllipsis_8() {
        assertEquals("foo   ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 6, -1, "*").toString());
    }

    @Test
    public void testEllipsis_9() {
        assertEquals("f* ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 3, 2, "*").toString());
    }

    @Test
    public void testEllipsis_10() {
        assertEquals("f*   ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 5, 2, "*").toString());
    }

    @Test
    public void testEllipsis_11() {
        assertEquals("foo", FormattableUtils.append("foo", createFormatter(), 0, -1, -1, "+*").toString());
    }

    @Test
    public void testEllipsis_12() {
        assertEquals("+*", FormattableUtils.append("foo", createFormatter(), 0, -1, 2, "+*").toString());
    }

    @Test
    public void testEllipsis_13() {
        assertEquals(" foo", FormattableUtils.append("foo", createFormatter(), 0, 4, -1, "+*").toString());
    }

    @Test
    public void testEllipsis_14() {
        assertEquals("   foo", FormattableUtils.append("foo", createFormatter(), 0, 6, -1, "+*").toString());
    }

    @Test
    public void testEllipsis_15() {
        assertEquals(" +*", FormattableUtils.append("foo", createFormatter(), 0, 3, 2, "+*").toString());
    }

    @Test
    public void testEllipsis_16() {
        assertEquals("   +*", FormattableUtils.append("foo", createFormatter(), 0, 5, 2, "+*").toString());
    }

    @Test
    public void testEllipsis_17() {
        assertEquals("foo ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 4, -1, "+*").toString());
    }

    @Test
    public void testEllipsis_18() {
        assertEquals("foo   ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 6, -1, "+*").toString());
    }

    @Test
    public void testEllipsis_19() {
        assertEquals("+* ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 3, 2, "+*").toString());
    }

    @Test
    public void testEllipsis_20() {
        assertEquals("+*   ", FormattableUtils.append("foo", createFormatter(), LEFT_JUSTIFY, 5, 2, "+*").toString());
    }
}
