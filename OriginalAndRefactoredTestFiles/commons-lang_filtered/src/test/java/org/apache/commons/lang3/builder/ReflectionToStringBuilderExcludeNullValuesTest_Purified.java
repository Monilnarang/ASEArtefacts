package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class ReflectionToStringBuilderExcludeNullValuesTest_Purified extends AbstractLangTest {

    static class TestFixture {

        @SuppressWarnings("unused")
        private final Integer testIntegerField;

        @SuppressWarnings("unused")
        private final String testStringField;

        TestFixture(final Integer a, final String b) {
            this.testIntegerField = a;
            this.testStringField = b;
        }
    }

    private static final String INTEGER_FIELD_NAME = "testIntegerField";

    private static final String STRING_FIELD_NAME = "testStringField";

    private final TestFixture BOTH_NON_NULL = new TestFixture(0, "str");

    private final TestFixture FIRST_NULL = new TestFixture(null, "str");

    private final TestFixture SECOND_NULL = new TestFixture(0, null);

    private final TestFixture BOTH_NULL = new TestFixture(null, null);

    @Test
    public void test_ConstructorOption_ExcludeNull_1_testMerged_1() {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(BOTH_NULL, null, null, null, false, false, false);
        builder.setExcludeNullValues(true);
        assertTrue(builder.isExcludeNullValues());
        String toString = builder.toString();
        assertFalse(toString.contains(STRING_FIELD_NAME));
        assertFalse(toString.contains(INTEGER_FIELD_NAME));
    }

    @Test
    public void test_ConstructorOption_ExcludeNull_6() {
        final ReflectionToStringBuilder oldBuilder = new ReflectionToStringBuilder(BOTH_NULL);
        oldBuilder.setExcludeNullValues(true);
        assertTrue(oldBuilder.isExcludeNullValues());
    }
}
