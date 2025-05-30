package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.junit.jupiter.api.Test;

public class RegexValidatorTest_Purified {

    private static final String REGEX = "^([abc]*)(?:\\-)([DEF]*)(?:\\-)([123]*)$";

    private static final String COMPONENT_1 = "([abc]{3})";

    private static final String COMPONENT_2 = "([DEF]{3})";

    private static final String COMPONENT_3 = "([123]{3})";

    private static final String SEPARATOR_1 = "(?:\\-)";

    private static final String SEPARATOR_2 = "(?:\\s)";

    private static final String REGEX_1 = "^" + COMPONENT_1 + SEPARATOR_1 + COMPONENT_2 + SEPARATOR_1 + COMPONENT_3 + "$";

    private static final String REGEX_2 = "^" + COMPONENT_1 + SEPARATOR_2 + COMPONENT_2 + SEPARATOR_2 + COMPONENT_3 + "$";

    private static final String REGEX_3 = "^" + COMPONENT_1 + COMPONENT_2 + COMPONENT_3 + "$";

    private static final String[] MULTIPLE_REGEX = { REGEX_1, REGEX_2, REGEX_3 };

    private void checkArray(final String label, final String[] expect, final String[] result) {
        if (expect == null || result == null) {
            if (expect == null && result == null) {
                return;
            }
            fail(label + " Null expect=" + expect + " result=" + result);
            return;
        }
        if (expect.length != result.length) {
            fail(label + " Length expect=" + expect.length + " result=" + result.length);
        }
        for (int i = 0; i < expect.length; i++) {
            assertEquals(expect[i], result[i], label + " value[" + i + "]");
        }
    }

    @Test
    public void testToString_1() {
        final RegexValidator single = new RegexValidator(REGEX);
        assertEquals("RegexValidator{" + REGEX + "}", single.toString(), "Single");
    }

    @Test
    public void testToString_2() {
        final RegexValidator multiple = new RegexValidator(REGEX, REGEX);
        assertEquals("RegexValidator{" + REGEX + "," + REGEX + "}", multiple.toString(), "Multiple");
    }
}
