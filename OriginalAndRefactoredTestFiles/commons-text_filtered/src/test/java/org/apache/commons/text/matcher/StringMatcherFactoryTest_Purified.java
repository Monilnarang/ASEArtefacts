package org.apache.commons.text.matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class StringMatcherFactoryTest_Purified {

    private static final class StringMatcherDefaults implements StringMatcher {

        @Override
        public int isMatch(final char[] buffer, final int start, final int bufferStart, final int bufferEnd) {
            return 2;
        }
    }

    @Test
    public void test_andMatcher_1() {
        assertNotNull(StringMatcherFactory.INSTANCE.andMatcher(StringMatcherFactory.INSTANCE.charMatcher('1'), StringMatcherFactory.INSTANCE.stringMatcher("2")));
    }

    @Test
    public void test_andMatcher_2() {
        assertNotNull(StringMatcherFactory.INSTANCE.andMatcher(null, StringMatcherFactory.INSTANCE.stringMatcher("2")));
    }

    @Test
    public void test_andMatcher_3() {
        assertNotNull(StringMatcherFactory.INSTANCE.andMatcher(null, null));
    }

    @Test
    public void test_andMatcher_4_testMerged_4() {
        StringMatcher andMatcher = StringMatcherFactory.INSTANCE.andMatcher();
        assertNotNull(andMatcher);
        assertEquals(0, andMatcher.size());
        andMatcher = StringMatcherFactory.INSTANCE.andMatcher(StringMatcherFactory.INSTANCE.charMatcher('1'));
        assertEquals(1, andMatcher.size());
    }
}
